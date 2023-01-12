package com.github.eterdelta.crittersandcompanions.mixin;

import com.github.eterdelta.crittersandcompanions.capability.CACCapabilities;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void defineEntityTag(Level level, BlockPos blockPos, float f, GameProfile gameProfile, ProfilePublicKey profilePublicKey, CallbackInfo ci) {
        ((Player) (Object) this).getEntityData().define(CACCapabilities.BUBBLE_STATE, false);
    }
}
