package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SGSoundEvents {
    public static final SoundEvent SNOWBALL_HIT = registerSoundEvent("entity.snowball.hit");
    public static final SoundEvent HOT_COCOA_BUBBLE = registerSoundEvent("block.cocoa_cauldron.bubble");
    public static final SoundEvent COCOA_DRINK = registerSoundEvent("item.hot_cocoa_bottle.drink");

//    }

    public static void registerSoundEvents() {
    }

    private static SoundEvent registerSoundEvent(String id) {
        ResourceLocation resourceLocation = SeasonsGreetings.id(id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation));
    }
}
