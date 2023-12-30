package com.github.eterdelta.crittersandcompanions.registry;

import com.github.eterdelta.crittersandcompanions.CrittersAndCompanions;
import com.github.eterdelta.crittersandcompanions.entity.*;
import com.github.eterdelta.crittersandcompanions.platform.DeferredRegister;
import com.github.eterdelta.crittersandcompanions.platform.RegistryObject;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class CACEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CrittersAndCompanions.MODID);

    public static final RegistryObject<EntityType<DragonflyEntity>> DRAGONFLY = ENTITIES.register("dragonfly", () -> create(DragonflyEntity::new, MobCategory.AMBIENT, 0.8F, 0.2F));
    public static final RegistryObject<EntityType<DumboOctopusEntity>> DUMBO_OCTOPUS = ENTITIES.register("dumbo_octopus", () -> create(DumboOctopusEntity::new, MobCategory.CREATURE, 0.4F, 0.4F));
    public static final RegistryObject<EntityType<FerretEntity>> FERRET = ENTITIES.register("ferret", () -> create(FerretEntity::new, MobCategory.CREATURE, 0.75F, 0.65F));
    public static final RegistryObject<EntityType<KoiFishEntity>> KOI_FISH = ENTITIES.register("koi_fish", () -> create(KoiFishEntity::new, MobCategory.WATER_AMBIENT, 0.6F, 0.3F));
    public static final RegistryObject<EntityType<LeafInsectEntity>> LEAF_INSECT = ENTITIES.register("leaf_insect", () -> create(LeafInsectEntity::new, MobCategory.AMBIENT, 0.4F, 0.3F));
    public static final RegistryObject<EntityType<OtterEntity>> OTTER = ENTITIES.register("otter", () -> create(OtterEntity::new, MobCategory.WATER_CREATURE, 0.8F, 0.4F));
    public static final RegistryObject<EntityType<RedPandaEntity>> RED_PANDA = ENTITIES.register("red_panda", () -> create(RedPandaEntity::new, MobCategory.CREATURE, 0.65F, 0.55F));
    public static final RegistryObject<EntityType<SeaBunnyEntity>> SEA_BUNNY = ENTITIES.register("sea_bunny", () -> create(SeaBunnyEntity::new, MobCategory.WATER_AMBIENT, 0.45F, 0.3F));
    public static final RegistryObject<EntityType<ShimaEnagaEntity>> SHIMA_ENAGA = ENTITIES.register("shima_enaga", () -> create(ShimaEnagaEntity::new, MobCategory.CREATURE, 0.5F, 0.6F));

    private static <E extends Entity> EntityType<E> create(EntityType.EntityFactory<E> entityFactory, MobCategory category, float width, float height) {
        return FabricEntityTypeBuilder.create(category, entityFactory).dimensions(EntityDimensions.scalable(width, height)).build();
    }
}