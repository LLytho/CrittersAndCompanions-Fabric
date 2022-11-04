package com.github.eterdelta.crittersandcompanions.platform;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public record RegistryObject<T>(ResourceLocation id, T value) implements Supplier<T> {
    @Override
    public T get() {
        return value;
    }
}
