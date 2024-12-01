package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.block.entity.GiftBoxBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SGBlockEntityType {

    public static final BlockEntityType<GiftBoxBlockEntity> GIFT_BOX = register("gift_box", FabricBlockEntityTypeBuilder.create(GiftBoxBlockEntity::new,
            SGBlocks.WHITE_GIFT_BOX,
            SGBlocks.LIGHT_GRAY_GIFT_BOX,
            SGBlocks.GRAY_GIFT_BOX,
            SGBlocks.BLACK_GIFT_BOX,
            SGBlocks.BROWN_GIFT_BOX,
            SGBlocks.RED_GIFT_BOX,
            SGBlocks.ORANGE_GIFT_BOX,
            SGBlocks.YELLOW_GIFT_BOX,
            SGBlocks.LIME_GIFT_BOX,
            SGBlocks.GREEN_GIFT_BOX,
            SGBlocks.CYAN_GIFT_BOX,
            SGBlocks.LIGHT_BLUE_GIFT_BOX,
            SGBlocks.BLUE_GIFT_BOX,
            SGBlocks.PURPLE_GIFT_BOX,
            SGBlocks.MAGENTA_GIFT_BOX,
            SGBlocks.PINK_GIFT_BOX
    ).build(null));

    public static <T extends BlockEntityType<?>> T register(String name, T blockEntityType) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, SeasonsGreetings.id(name), blockEntityType);
    }

    public static void registerBlockEntities() {

    }
}
