package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SGSoundEvents {
    public static final SoundEvent SNOWBALL_HIT = registerSoundEvent("entity.snowball.hit");

    public static final SoundEvent MILK_BUBBLE = registerSoundEvent("block.milk_cauldron.bubble");
    public static final SoundEvent HOT_COCOA_BUBBLE = registerSoundEvent("block.cocoa_cauldron.bubble");
    public static final SoundEvent DRINKING = registerSoundEvent("subtitles.entity.generic.drink");

    public static final SoundEvent GINGERBREAD_MAN_IDLE = registerSoundEvent("entity.gingerbread_man.idle");
    public static final SoundEvent GINGERBREAD_MAN_HURT = registerSoundEvent("entity.gingerbread_man.hurt");
    public static final SoundEvent GINGERBREAD_MAN_DEATH = registerSoundEvent("entity.gingerbread_man.death");

//    }

    public static void registerSoundEvents() {
    }

    private static SoundEvent registerSoundEvent(String id) {
        ResourceLocation resourceLocation = SeasonsGreetings.id(id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation));
    }
}
