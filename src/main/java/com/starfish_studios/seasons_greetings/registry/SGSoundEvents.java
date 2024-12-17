package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public class SGSoundEvents {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, SeasonsGreetings.MOD_ID);

    // region Block Sounds

    public static final DeferredHolder<SoundEvent, SoundEvent> MILK_CAULDRON_BUBBLE = registerSoundEvent("block.milk_cauldron.bubble");
    public static final DeferredHolder<SoundEvent, SoundEvent> COCOA_CAULDRON_BUBBLE = registerSoundEvent("block.cocoa_cauldron.bubble");
    public static final DeferredHolder<SoundEvent, SoundEvent> EGGNOG_CAULDRON_BUBBLE = registerSoundEvent("block.eggnog_cauldron.bubble");

    public static final DeferredHolder<SoundEvent, SoundEvent> PACKED_SNOW_BREAK = registerSoundEvent("block.packed_snow.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACKED_SNOW_STEP = registerSoundEvent("block.packed_snow.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACKED_SNOW_PLACE = registerSoundEvent("block.packed_snow.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACKED_SNOW_HIT = registerSoundEvent("block.packed_snow.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACKED_SNOW_FALL = registerSoundEvent("block.packed_snow.fall");

    public static final DeferredHolder<SoundEvent, SoundEvent> CANDY_BLOCK_BREAK = registerSoundEvent("block.candy_block.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> CANDY_BLOCK_STEP = registerSoundEvent("block.candy_block.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> CANDY_BLOCK_PLACE = registerSoundEvent("block.candy_block.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> CANDY_BLOCK_HIT = registerSoundEvent("block.candy_block.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> CANDY_BLOCK_FALL = registerSoundEvent("block.candy_block.fall");

    public static final DeferredHolder<SoundEvent, SoundEvent> GIFT_BOX_BREAK = registerSoundEvent("block.gift_box.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIFT_BOX_STEP = registerSoundEvent("block.gift_box.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIFT_BOX_PLACE = registerSoundEvent("block.gift_box.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIFT_BOX_HIT = registerSoundEvent("block.gift_box.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIFT_BOX_FALL = registerSoundEvent("block.gift_box.fall");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIFT_BOX_OPEN = registerSoundEvent("block.gift_box.open");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIFT_BOX_CLOSE = registerSoundEvent("block.gift_box.close");

    // endregion

    // region Item Sounds

    public static final DeferredHolder<SoundEvent, SoundEvent> DRINK = registerSoundEvent("entity.generic.drink");

    // endregion

    // region Entity Sounds

    public static final DeferredHolder<SoundEvent, SoundEvent> GINGERBREAD_MAN_IDLE = registerSoundEvent("entity.gingerbread_man.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> GINGERBREAD_MAN_HURT = registerSoundEvent("entity.gingerbread_man.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> GINGERBREAD_MAN_DEATH = registerSoundEvent("entity.gingerbread_man.death");

    public static final DeferredHolder<SoundEvent, SoundEvent> SNOWBALL_HIT = registerSoundEvent("entity.snowball.hit");

    // endregion

    // region Block Sound Groups

    public static final DeferredSoundType PACKED_SNOW = new DeferredSoundType(1.0F, 1.0F,
            () -> SoundEvents.SNOW_BREAK, SGSoundEvents.PACKED_SNOW_STEP, () -> SoundEvents.SNOW_PLACE, SGSoundEvents.PACKED_SNOW_HIT, SGSoundEvents.PACKED_SNOW_FALL
    );

    public static final DeferredSoundType CANDY_BLOCK = new DeferredSoundType(1.0F, 1.0F,
            SGSoundEvents.CANDY_BLOCK_BREAK, SGSoundEvents.CANDY_BLOCK_STEP, SGSoundEvents.CANDY_BLOCK_PLACE, SGSoundEvents.CANDY_BLOCK_HIT, SGSoundEvents.CANDY_BLOCK_FALL
    );

    public static final DeferredSoundType GIFT_BOX = new DeferredSoundType(1.0F, 1.0F,
            SGSoundEvents.GIFT_BOX_BREAK, SGSoundEvents.GIFT_BOX_STEP, SGSoundEvents.GIFT_BOX_PLACE, SGSoundEvents.GIFT_BOX_HIT, SGSoundEvents.GIFT_BOX_FALL
    );

    // endregion

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String id) {
        ResourceLocation resourceLocation = SeasonsGreetings.id(id);
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(resourceLocation));
    }

    public static void registerSoundEvents(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}