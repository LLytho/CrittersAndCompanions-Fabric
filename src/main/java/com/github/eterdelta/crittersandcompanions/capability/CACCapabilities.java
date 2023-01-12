package com.github.eterdelta.crittersandcompanions.capability;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public class CACCapabilities {

    public static final EntityDataAccessor<Boolean> BUBBLE_STATE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
}
