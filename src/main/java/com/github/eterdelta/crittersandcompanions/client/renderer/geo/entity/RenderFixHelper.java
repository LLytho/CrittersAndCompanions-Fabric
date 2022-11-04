package com.github.eterdelta.crittersandcompanions.client.renderer.geo.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderFixHelper<T extends LivingEntity & IAnimatable> extends GeoEntityRenderer<T> {

	private static final Logger LOGGER = LogManager.getLogger();

	public RenderFixHelper(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
		super(renderManager, modelProvider);
	}

	/*
	 * INFO: Helps me to fix my mixin refmap when I need it...
	 */
	//	@Override
//	public ResourceLocation getTextureLocation(T entity) {
//		return this.modelProvider.getTextureLocation(animatable);
//	}
}
