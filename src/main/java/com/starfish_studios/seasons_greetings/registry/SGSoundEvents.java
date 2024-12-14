package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

@SuppressWarnings("unused")
public class SGSoundEvents {

    // region Block Sounds

    public static final SoundEvent MILK_CAULDRON_BUBBLE = registerSoundEvent("block.milk_cauldron.bubble");
    public static final SoundEvent COCOA_CAULDRON_BUBBLE = registerSoundEvent("block.cocoa_cauldron.bubble");

    public static final SoundEvent PACKED_SNOW_BREAK = registerSoundEvent("block.packed_snow.break");
    public static final SoundEvent PACKED_SNOW_STEP = registerSoundEvent("block.packed_snow.step");
    public static final SoundEvent PACKED_SNOW_PLACE = registerSoundEvent("block.packed_snow.place");
    public static final SoundEvent PACKED_SNOW_HIT = registerSoundEvent("block.packed_snow.hit");
    public static final SoundEvent PACKED_SNOW_FALL = registerSoundEvent("block.packed_snow.fall");

    public static final SoundEvent CANDY_BLOCK_BREAK = registerSoundEvent("block.candy_block.break");
    public static final SoundEvent CANDY_BLOCK_STEP = registerSoundEvent("block.candy_block.step");
    public static final SoundEvent CANDY_BLOCK_PLACE = registerSoundEvent("block.candy_block.place");
    public static final SoundEvent CANDY_BLOCK_HIT = registerSoundEvent("block.candy_block.hit");
    public static final SoundEvent CANDY_BLOCK_FALL = registerSoundEvent("block.candy_block.fall");

    public static final SoundEvent GIFT_BOX_BREAK = registerSoundEvent("block.gift_box.break");
    public static final SoundEvent GIFT_BOX_STEP = registerSoundEvent("block.gift_box.step");
    public static final SoundEvent GIFT_BOX_PLACE = registerSoundEvent("block.gift_box.place");
    public static final SoundEvent GIFT_BOX_HIT = registerSoundEvent("block.gift_box.hit");
    public static final SoundEvent GIFT_BOX_FALL = registerSoundEvent("block.gift_box.fall");
    public static final SoundEvent GIFT_BOX_OPEN = registerSoundEvent("block.gift_box.open");
    public static final SoundEvent GIFT_BOX_CLOSE = registerSoundEvent("block.gift_box.close");

    // endregion

    // region Item Sounds

    public static final SoundEvent DRINK = registerSoundEvent("entity.generic.drink");

    // endregion

    // region Entity Sounds

    public static final SoundEvent GINGERBREAD_MAN_IDLE = registerSoundEvent("entity.gingerbread_man.idle");
    public static final SoundEvent GINGERBREAD_MAN_HURT = registerSoundEvent("entity.gingerbread_man.hurt");
    public static final SoundEvent GINGERBREAD_MAN_DEATH = registerSoundEvent("entity.gingerbread_man.death");

    public static final SoundEvent SNOWBALL_HIT = registerSoundEvent("entity.snowball.hit");

    // endregion

    // region Block Sound Groups

    public static final SoundType PACKED_SNOW = new SoundType(1.0F, 1.0F,
        SoundEvents.SNOW_BREAK, SGSoundEvents.PACKED_SNOW_STEP, SoundEvents.SNOW_PLACE, SGSoundEvents.PACKED_SNOW_HIT, SGSoundEvents.PACKED_SNOW_FALL
    );

    public static final SoundType CANDY_BLOCK = new SoundType(1.0F, 1.0F,
        SGSoundEvents.CANDY_BLOCK_BREAK, SGSoundEvents.CANDY_BLOCK_STEP, SGSoundEvents.CANDY_BLOCK_PLACE, SGSoundEvents.CANDY_BLOCK_HIT, SGSoundEvents.CANDY_BLOCK_FALL
    );

    public static final SoundType GIFT_BOX = new SoundType(1.0F, 1.0F,
        SGSoundEvents.GIFT_BOX_BREAK, SGSoundEvents.GIFT_BOX_STEP, SGSoundEvents.GIFT_BOX_PLACE, SGSoundEvents.GIFT_BOX_HIT, SGSoundEvents.GIFT_BOX_FALL
    );

    // endregion

    private static SoundEvent registerSoundEvent(String id) {
        ResourceLocation resourceLocation = SeasonsGreetings.id(id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation));
    }

    public static void registerSoundEvents() {}
}