package com.github.eterdelta.crittersandcompanions.config;

import com.github.eterdelta.crittersandcompanions.CrittersAndCompanions;
import com.github.eterdelta.crittersandcompanions.registry.CACEntities;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.*;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class SpawnConfig {
    public static final Logger LOGGER = LogManager.getLogger(CrittersAndCompanions.MODID);
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final String SPAWN_DATA_KEY = "spawn_data";

    public static Multimap<EntityType<?>, SpawnEntry> load() {
        Path directory = FabricLoader.getInstance().getConfigDir().resolve(CrittersAndCompanions.MODID);
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            LOGGER.error("[CaC] Failed to create config directory: " + e.getMessage(), e);
            return ArrayListMultimap.create();
        }

        Multimap<EntityType<?>, SpawnEntry> spawns = ArrayListMultimap.create();

        Multimap<EntityType<?>, SpawnEntry> defaultEntries = getDefaultEntries();
        for (var entry : CACEntities.ENTITIES.getEntries()) {
            Collection<SpawnEntry> defaultSpawnEntries = defaultEntries.get(entry.get());
            if (defaultSpawnEntries.isEmpty()) {
                LOGGER.warn("[CaC] No default spawn entries for entity <" + Registry.ENTITY_TYPE.getKey(entry.get()) + ">");
            }
            var filename = entry.id().getPath() + ".json";
            var filePath = directory.resolve(filename);

            try {
                var entityType = entry.get();
                Collection<SpawnEntry> spawnEntries = loadEntries(filePath);
                spawns.putAll(entityType, spawnEntries);
            } catch (NoSuchFileException e) {
                LOGGER.warn("[CaC] No spawn config found for entity <" + Registry.ENTITY_TYPE.getKey(entry.get()) + ">. Creating default config file.");
                spawns.putAll(entry.get(), defaultSpawnEntries);
                writeDefaultEntries(filePath, defaultSpawnEntries);
            } catch (Exception e) {
                LOGGER.error("[CaC] Failed to load spawn config for entity <" + Registry.ENTITY_TYPE.getKey(entry.get()) + ">: " + e.getMessage());
                spawns.putAll(entry.get(), defaultSpawnEntries);
                writeDefaultEntries(filePath, defaultSpawnEntries);
            }
        }

        return spawns;
    }

    private static void writeDefaultEntries(Path filePath, Collection<SpawnEntry> entries) {
        try {
            JsonObject json = new JsonObject();

            JsonArray array = new JsonArray();
            json.add(SPAWN_DATA_KEY, array);

            for (SpawnEntry entry : entries) {
                JsonObject entryObj = entry.toJson();
                array.add(entryObj);
            }

            String jsonString = GSON.toJson(json);
            Files.writeString(filePath, jsonString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (Exception e) {
            LOGGER.error("[CaC] Failed to write default spawn config to file <" + filePath + ">: " + e.getMessage(), e);
        }
    }

    private static Collection<SpawnEntry> loadEntries(Path filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            List<SpawnEntry> result = new ArrayList<>();
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            JsonArray array = json.getAsJsonArray(SPAWN_DATA_KEY);
            if (array == null) {
                throw new IllegalStateException("Spawn config file does not contain a 'spawn_data' array: " + filePath);
            }

            for (JsonElement element : array) {
                try {
                    var entry = readEntry(filePath, element);
                    if (entry != null) {
                        result.add(entry);
                    }
                } catch (ClassCastException | IllegalStateException | NullPointerException e) {
                    throw new RuntimeException("Invalid spawn entry. Rewrite default spawns");
                }
            }

            return result;
        }
    }

    private static SpawnEntry readEntry(Path filePath, JsonElement element) throws ClassCastException, IllegalStateException, NullPointerException {
        if (!(element instanceof JsonObject entryObj)) {
            LOGGER.warn("[CaC] Invalid spawn entry in file <" + filePath + ">, skipping entry: " + element);
            return null;
        }

        Set<TagKey<Biome>> biomeTags = new HashSet<>();
        Set<ResourceKey<Biome>> biomes = new HashSet<>();
        parseBiomes(entryObj, biomeTags, biomes);
        int weight = entryObj.get("weight").getAsInt();
        int minGroup = entryObj.get("minGroup").getAsInt();
        int maxGroup = entryObj.get("maxGroup").getAsInt();
        return new SpawnEntry(weight, minGroup, maxGroup, biomes, biomeTags);
    }

    private static void parseBiomes(JsonObject entryObj, Set<TagKey<Biome>> biomeTags, Set<ResourceKey<Biome>> biomes) {
        for (JsonElement b : entryObj.getAsJsonArray("biomes")) {
            String unknownString = b.getAsJsonPrimitive().getAsString();
            if (unknownString.startsWith("#")) {
                TagKey<Biome> tag = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(unknownString.substring(1)));
                biomeTags.add(tag);
            } else {
                ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(unknownString));
                biomes.add(key);
            }
        }
    }

    private static Multimap<EntityType<?>, SpawnEntry> getDefaultEntries() {
        Multimap<EntityType<?>, SpawnEntry> defaultEntries = ArrayListMultimap.create();

        defaultEntries.put(CACEntities.DRAGONFLY.get(), SpawnEntry.biomes(6, 1, 1, Biomes.RIVER));

        defaultEntries.put(CACEntities.DUMBO_OCTOPUS.get(), SpawnEntry.biomes(4, 1, 1, Biomes.OCEAN, Biomes.DEEP_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN));
        defaultEntries.put(CACEntities.DUMBO_OCTOPUS.get(), SpawnEntry.biomes(6, 1, 1, Biomes.WARM_OCEAN));

        defaultEntries.put(CACEntities.FERRET.get(), SpawnEntry.tags(3, 2, 3, BiomeTags.IS_FOREST));
        defaultEntries.put(CACEntities.FERRET.get(), SpawnEntry.biomes(4, 2, 3, Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS));

        defaultEntries.put(CACEntities.KOI_FISH.get(), SpawnEntry.biomes(4, 2, 5, Biomes.RIVER));

        defaultEntries.put(CACEntities.LEAF_INSECT.get(), SpawnEntry.tags(12, 1, 1, BiomeTags.IS_FOREST, BiomeTags.IS_JUNGLE));

        defaultEntries.put(CACEntities.OTTER.get(), SpawnEntry.biomes(2, 3, 5, Biomes.RIVER));

        defaultEntries.put(CACEntities.RED_PANDA.get(), SpawnEntry.tags(8, 1, 2, BiomeTags.IS_JUNGLE));

        defaultEntries.put(CACEntities.SEA_BUNNY.get(), SpawnEntry.biomes(32, 1, 2, Biomes.OCEAN, Biomes.DEEP_OCEAN));
        defaultEntries.put(CACEntities.SEA_BUNNY.get(), SpawnEntry.biomes(32, 1, 3, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN));
        defaultEntries.put(CACEntities.SEA_BUNNY.get(), SpawnEntry.biomes(64, 1, 4, Biomes.WARM_OCEAN));

        defaultEntries.put(CACEntities.SHIMA_ENAGA.get(), SpawnEntry.biomes(3, 2, 3, Biomes.SNOWY_PLAINS));

        return defaultEntries;
    }


    public record SpawnEntry(int weight, int minGroup, int maxGroup, Set<ResourceKey<Biome>> biomes,
                             Set<TagKey<Biome>> biomeTags) {

        @SafeVarargs
        static SpawnEntry tags(int weight, int minGroup, int maxGroup, TagKey<Biome>... biomeTags) {
            return new SpawnEntry(weight, minGroup, maxGroup, Set.of(), Set.of(biomeTags));
        }

        @SafeVarargs
        static SpawnEntry biomes(int weight, int minGroup, int maxGroup, ResourceKey<Biome>... biomes) {
            return new SpawnEntry(weight, minGroup, maxGroup, Set.of(biomes), Set.of());
        }

        public void addSpawn(EntityType<?> entity) {
            BiomeModifications.addSpawn(
                    ctx -> {
                        for (TagKey<Biome> tag : biomeTags) {
                            if (ctx.hasTag(tag)) {
                                return true;
                            }
                        }

                        return biomes.contains(ctx.getBiomeKey());
                    },
                    entity.getCategory(),
                    entity,
                    weight,
                    minGroup,
                    maxGroup
            );
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            JsonArray biomesArray = new JsonArray();
            for (ResourceKey<Biome> biome : biomes) {
                biomesArray.add(biome.location().toString());
            }

            for (TagKey<Biome> tag : biomeTags) {
                biomesArray.add("#" + tag.location().toString());
            }
            json.add("biomes", biomesArray);

            json.addProperty("weight", weight);
            json.addProperty("minGroup", minGroup);
            json.addProperty("maxGroup", maxGroup);


            return json;
        }

        @Override
        public String toString() {
            return "{" +
                    "weight=" + weight +
                    ", minGroup=" + minGroup +
                    ", maxGroup=" + maxGroup +
                    ", biomes=[" + biomes.stream().map(ResourceKey::location).map(ResourceLocation::toString).collect(Collectors.joining(", ")) + "]" +
                    ", biomeTags=[" + biomeTags.stream().map(TagKey::location).map(ResourceLocation::toString).collect(Collectors.joining(", ")) + "]" +
                    '}';
        }
    }

}
