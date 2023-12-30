package com.github.eterdelta.crittersandcompanions;

import com.github.eterdelta.crittersandcompanions.client.renderer.geo.entity.*;
import com.github.eterdelta.crittersandcompanions.entity.*;
import com.github.eterdelta.crittersandcompanions.handler.LootHandler;
import com.github.eterdelta.crittersandcompanions.handler.SpawnHandler;
import com.github.eterdelta.crittersandcompanions.mixin.CatMixin;
import com.github.eterdelta.crittersandcompanions.registry.CACEntities;
import com.github.eterdelta.crittersandcompanions.registry.CACItems;
import com.github.eterdelta.crittersandcompanions.registry.CACSounds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.network.chat.Component;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import software.bernie.geckolib.GeckoLib;

public class CrittersAndCompanions implements ModInitializer, ClientModInitializer {
    public static final String MODID = "crittersandcompanions";
	private static final CreativeModeTab ITEM_GROUP = FabricItemGroup.builder()
		.icon(() -> new ItemStack(CACItems.PEARL_NECKLACE_1.get()))
		.title(Component.translatable("itemGroup.crittersandcompanions.items"))
        .displayItems((context, entries) -> {
            entries.accept(CACItems.CLAM.get());
            entries.accept(CACItems.PEARL.get());
            entries.accept(CACItems.PEARL_NECKLACE_1.get());
            entries.accept(CACItems.PEARL_NECKLACE_2.get());
            entries.accept(CACItems.PEARL_NECKLACE_3.get());
            entries.accept(CACItems.DUMBO_OCTOPUS_BUCKET.get());
            entries.accept(CACItems.KOI_FISH_BUCKET.get());
            entries.accept(CACItems.SEA_BUNNY_BUCKET.get());
            entries.accept(CACItems.DIAMOND_DRAGONFLY_ARMOR.get());
            entries.accept(CACItems.GOLD_DRAGONFLY_ARMOR.get());
            entries.accept(CACItems.IRON_DRAGONFLY_ARMOR.get());
            entries.accept(CACItems.DRAGONFLY_SPAWN_EGG.get());
            entries.accept(CACItems.FERRET_SPAWN_EGG.get());
            entries.accept(CACItems.DUMBO_OCTOPUS_SPAWN_EGG.get());
            entries.accept(CACItems.KOI_FISH_SPAWN_EGG.get());
            entries.accept(CACItems.LEAF_INSECT_SPAWN_EGG.get());
            entries.accept(CACItems.OTTER_SPAWN_EGG.get());
            entries.accept(CACItems.RED_PANDA_SPAWN_EGG.get());
            entries.accept(CACItems.SEA_BUNNY_SPAWN_EGG.get());
            entries.accept(CACItems.SHIMA_ENAGA_SPAWN_EGG.get());
        })
		.build();

    @Override
    public void onInitializeClient() {
        registerEntityRenderers();
        registerItemProperties();
    }

    @Override
    public void onInitialize() {
        GeckoLib.initialize();
//        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CACEntities.ENTITIES.register();
        CACItems.ITEMS.register();
        CACSounds.register();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(MODID, "items"), ITEM_GROUP);

        // TODO
        SpawnHandler.registerBiomeModifications();
        SpawnHandler.registerSpawnPlacements();
        onAttributeCreation();
//        eventBus.addListener(this::registerEntityRenderers);

        LootHandler.onLootTableLoad();
    }

    private void onAttributeCreation() {
        FabricDefaultAttributeRegistry.register(CACEntities.OTTER.get(), OtterEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CACEntities.KOI_FISH.get(), KoiFishEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CACEntities.DRAGONFLY.get(), DragonflyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CACEntities.SEA_BUNNY.get(), SeaBunnyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CACEntities.SHIMA_ENAGA.get(), ShimaEnagaEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CACEntities.FERRET.get(), FerretEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CACEntities.DUMBO_OCTOPUS.get(), DumboOctopusEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CACEntities.LEAF_INSECT.get(), LeafInsectEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CACEntities.RED_PANDA.get(), RedPandaEntity.createAttributes());
    }

    private void registerEntityRenderers() {
        EntityRendererRegistry.register(CACEntities.OTTER.get(), OtterRenderer::new);
        EntityRendererRegistry.register(CACEntities.KOI_FISH.get(), KoiFishRenderer::new);
        EntityRendererRegistry.register(CACEntities.DRAGONFLY.get(), DragonflyRenderer::new);
        EntityRendererRegistry.register(CACEntities.SEA_BUNNY.get(), SeaBunnyRenderer::new);
        EntityRendererRegistry.register(CACEntities.SHIMA_ENAGA.get(), ShimaEnagaRenderer::new);
        EntityRendererRegistry.register(CACEntities.FERRET.get(), FerretRenderer::new);
        EntityRendererRegistry.register(CACEntities.DUMBO_OCTOPUS.get(), DumboOctopusRenderer::new);
        EntityRendererRegistry.register(CACEntities.LEAF_INSECT.get(), LeafInsectRenderer::new);
        EntityRendererRegistry.register(CACEntities.RED_PANDA.get(), RedPandaRenderer::new);
    }

    private void registerItemProperties() {
        ItemProperties.register(CACItems.DUMBO_OCTOPUS_BUCKET.get(), new ResourceLocation("variant"), (stack, clientLevel, entity, seed) -> {
            if (stack.getTag() != null && stack.getTag().contains("BucketVariant")) {
                return stack.getTag().getInt("BucketVariant");
            } else {
                return 0.0F;
            }
        });
        ItemProperties.register(CACItems.SEA_BUNNY_BUCKET.get(), new ResourceLocation("variant"), (stack, clientLevel, entity, seed) -> {
            if (stack.getTag() != null && stack.getTag().contains("BucketVariant")) {
                return stack.getTag().getInt("BucketVariant");
            } else {
                return 0.0F;
            }
        });
    }

}
