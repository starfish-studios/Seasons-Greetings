package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.block.entity.GiftBoxBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SGBlockEntityType {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SeasonsGreetings.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GiftBoxBlockEntity>> GIFT_BOX = BLOCK_ENTITIES.register("gift_box", () ->
            BlockEntityType.Builder.of(GiftBoxBlockEntity::new,
            SGBlocks.WHITE_GIFT_BOX.get(),
            SGBlocks.LIGHT_GRAY_GIFT_BOX.get(),
            SGBlocks.GRAY_GIFT_BOX.get(),
            SGBlocks.BLACK_GIFT_BOX.get(),
            SGBlocks.BROWN_GIFT_BOX.get(),
            SGBlocks.RED_GIFT_BOX.get(),
            SGBlocks.ORANGE_GIFT_BOX.get(),
            SGBlocks.YELLOW_GIFT_BOX.get(),
            SGBlocks.LIME_GIFT_BOX.get(),
            SGBlocks.GREEN_GIFT_BOX.get(),
            SGBlocks.CYAN_GIFT_BOX.get(),
            SGBlocks.LIGHT_BLUE_GIFT_BOX.get(),
            SGBlocks.BLUE_GIFT_BOX.get(),
            SGBlocks.PURPLE_GIFT_BOX.get(),
            SGBlocks.MAGENTA_GIFT_BOX.get(),
            SGBlocks.PINK_GIFT_BOX.get()
    ).build(null));

    public static void registerBlockEntities(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
