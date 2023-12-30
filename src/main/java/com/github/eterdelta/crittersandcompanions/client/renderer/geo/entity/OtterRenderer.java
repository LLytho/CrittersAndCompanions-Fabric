package com.github.eterdelta.crittersandcompanions.client.renderer.geo.entity;

import com.github.eterdelta.crittersandcompanions.client.model.geo.OtterModel;
import com.github.eterdelta.crittersandcompanions.entity.OtterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OtterRenderer<E extends OtterEntity> extends GeoEntityRenderer<E> {
    public OtterRenderer(EntityRendererProvider.Context context) {
        super(context, new OtterModel());
    }

    @Override
    public RenderType getRenderType(E animatable, ResourceLocation textureLocation, @Nullable MultiBufferSource renderTypeBuffer, float partialTicks) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(animatable));
    }

//    @Override
//    public void renderRecursively(BakedGeoModel model, PoseStack stack, MultiBufferSource renderTypeBuffer, E animatable, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        if (bone.getName().equals("held_item")) {
//            stack.pushPose();
//            stack.scale(0.5F, 0.5F, 0.5F);
//            stack.translate(0.05D, 0.2D, -0.9D);
//            Quaternionf quaternion = new Quaternionf().rotateXYZ(1.5708F, 1.5708F, 0.0F);
//            stack.mulPose(quaternion);
//            Minecraft.getInstance().getItemRenderer().renderStatic(this.mainHand, ItemDisplayContext.FIXED, packedLightIn, packedOverlayIn, stack, this.rtb, currentOtter.level(), 0);
//            stack.popPose();
//
//            bufferIn = rtb.getBuffer(RenderType.entityCutoutNoCull(this.whTexture));
//        }
//        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//    }
}