package com.github.eterdelta.crittersandcompanions.client.renderer.geo.entity;

import com.github.eterdelta.crittersandcompanions.client.model.geo.FerretModel;
import com.github.eterdelta.crittersandcompanions.entity.FerretEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FerretRenderer<E extends FerretEntity> extends GeoEntityRenderer<E> {
    public FerretRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FerretModel());
    }

    @Override
    public RenderType getRenderType(E animatable, ResourceLocation textureLocation, @Nullable MultiBufferSource renderTypeBuffer, float partialTicks) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(animatable));
    }
}
