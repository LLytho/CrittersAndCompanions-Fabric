package com.github.eterdelta.crittersandcompanions.handler;

import com.github.eterdelta.crittersandcompanions.mixin.LootPoolAccessor;
import com.github.eterdelta.crittersandcompanions.mixin.LootTableBuilderAccessor;
import com.github.eterdelta.crittersandcompanions.registry.CACItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import org.apache.commons.lang3.ArrayUtils;

public class LootHandler {

    public static void onLootTableLoad() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, builder, source) -> {
            if (!id.equals(BuiltInLootTables.FISHING_FISH)) {
                return;
            }

            ((LootTableBuilderAccessor) builder)
                    .getPools()
                    .stream()
                    .findFirst()
                    .ifPresent(LootHandler::addPoolEntries);
        });
    }

    private static void addPoolEntries(LootPool mainPool) {
        LootPoolEntryContainer clamEntry = LootItem.lootTableItem(CACItems.CLAM.get()).setWeight(10).when(AlternativeLootItemCondition.alternative(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.RIVER)))).build();
        LootPoolEntryContainer koiFish = LootItem.lootTableItem(CACItems.KOI_FISH.get()).setWeight(5).build();

        LootPoolAccessor mainPoolAccessor = ((LootPoolAccessor) mainPool);
        LootPoolEntryContainer[] addedEntries = ArrayUtils.addAll(mainPoolAccessor.getEntries(), clamEntry, koiFish);
        mainPoolAccessor.setEntries(addedEntries);
    }
}
