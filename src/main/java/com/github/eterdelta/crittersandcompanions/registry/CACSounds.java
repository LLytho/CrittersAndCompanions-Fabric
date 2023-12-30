package com.github.eterdelta.crittersandcompanions.registry;

import com.github.eterdelta.crittersandcompanions.CrittersAndCompanions;
import com.github.eterdelta.crittersandcompanions.platform.DeferredRegister;
import com.github.eterdelta.crittersandcompanions.platform.RegistryObject;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class CACSounds {

    public static final SoundEvent BITE_ATTACK = registerSoundEvent("entity.bite_attack");
    public static final SoundEvent BUBBLE_POP = registerSoundEvent("entity.bubble_pop");
    public static final SoundEvent DRAGONFLY_AMBIENT = registerSoundEvent("entity.dragonfly.ambient");
    public static final SoundEvent FERRET_AMBIENT = registerSoundEvent("entity.ferret.ambient");
    public static final SoundEvent FERRET_DEATH = registerSoundEvent("entity.ferret.death");
    public static final SoundEvent FERRET_HURT = registerSoundEvent("entity.ferret.hurt");
    public static final SoundEvent LEAF_INSECT_DEATH = registerSoundEvent("entity.leaf_insect.death");
    public static final SoundEvent LEAF_INSECT_HURT = registerSoundEvent("entity.leaf_insect.hurt");
    public static final SoundEvent OTTER_AMBIENT = registerSoundEvent("entity.otter.ambient");
    public static final SoundEvent OTTER_DEATH = registerSoundEvent("entity.otter.death");
    public static final SoundEvent OTTER_EAT = registerSoundEvent("entity.otter.eat");
    public static final SoundEvent OTTER_HURT = registerSoundEvent("entity.otter.hurt");
    public static final SoundEvent OTTER_SWIM = registerSoundEvent("entity.otter.swim");
    public static final SoundEvent RED_PANDA_AMBIENT = registerSoundEvent("entity.red_panda.ambient");
    public static final SoundEvent RED_PANDA_DEATH = registerSoundEvent("entity.red_panda.death");
    public static final SoundEvent RED_PANDA_HURT = registerSoundEvent("entity.red_panda.hurt");
    public static final SoundEvent SEA_BUNNY_DEATH = registerSoundEvent("entity.sea_bunny.death");
    public static final SoundEvent SEA_BUNNY_HURT = registerSoundEvent("entity.sea_bunny.hurt");
    public static final SoundEvent SHIMA_ENAGA_AMBIENT = registerSoundEvent("entity.shima_enaga.ambient");

    public static SoundEvent registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(CrittersAndCompanions.MODID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {
        return;
    }

}
