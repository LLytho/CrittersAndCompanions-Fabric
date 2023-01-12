package com.github.eterdelta.crittersandcompanions.mixin;

import com.github.eterdelta.crittersandcompanions.client.renderer.BubbleLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f); // no-op
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addBubbleLayer(EntityRendererProvider.Context context, boolean bl, CallbackInfo ci) {
        try {
            var parent = (RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>) (Object) this;
            ModelPart modelPart = context.bakeLayer(BubbleLayer.LAYER_LOCATION);
            this.addLayer(new BubbleLayer(parent, modelPart));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
