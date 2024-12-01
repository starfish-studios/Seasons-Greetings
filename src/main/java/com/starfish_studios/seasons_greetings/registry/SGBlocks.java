package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.block.GiftBoxBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.PushReaction;

public class SGBlocks {
    // WHITE, LIGHT GRAY, GRAY, BLACK, BROWN, RED, ORANGE, YELLOW, LIME, GREEN, CYAN, LIGHT BLUE, BLUE, PURPLE, MAGENTA, PINK

    public static final Block.Properties giftBoxProperties = Block.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(0F).noOcclusion().forceSolidOn();

    public static final Block WHITE_GIFT_BOX = registerBlock("white_gift_box", new GiftBoxBlock(DyeColor.WHITE, giftBoxProperties));
    public static final Block LIGHT_GRAY_GIFT_BOX = registerBlock("light_gray_gift_box", new GiftBoxBlock(DyeColor.LIGHT_GRAY, giftBoxProperties));
    public static final Block GRAY_GIFT_BOX = registerBlock("gray_gift_box", new GiftBoxBlock(DyeColor.GRAY, giftBoxProperties));
    public static final Block BLACK_GIFT_BOX = registerBlock("black_gift_box", new GiftBoxBlock(DyeColor.BLACK, giftBoxProperties));
    public static final Block BROWN_GIFT_BOX = registerBlock("brown_gift_box", new GiftBoxBlock(DyeColor.BROWN, giftBoxProperties));
    public static final Block RED_GIFT_BOX = registerBlock("red_gift_box", new GiftBoxBlock(DyeColor.RED, giftBoxProperties));
    public static final Block ORANGE_GIFT_BOX = registerBlock("orange_gift_box", new GiftBoxBlock(DyeColor.ORANGE, giftBoxProperties));
    public static final Block YELLOW_GIFT_BOX = registerBlock("yellow_gift_box", new GiftBoxBlock(DyeColor.YELLOW, giftBoxProperties));
    public static final Block LIME_GIFT_BOX = registerBlock("lime_gift_box", new GiftBoxBlock(DyeColor.LIME, giftBoxProperties));
    public static final Block GREEN_GIFT_BOX = registerBlock("green_gift_box", new GiftBoxBlock(DyeColor.GREEN, giftBoxProperties));
    public static final Block CYAN_GIFT_BOX = registerBlock("cyan_gift_box", new GiftBoxBlock(DyeColor.CYAN, giftBoxProperties));
    public static final Block LIGHT_BLUE_GIFT_BOX = registerBlock("light_blue_gift_box", new GiftBoxBlock(DyeColor.LIGHT_BLUE, giftBoxProperties));
    public static final Block BLUE_GIFT_BOX = registerBlock("blue_gift_box", new GiftBoxBlock(DyeColor.BLUE, giftBoxProperties));
    public static final Block PURPLE_GIFT_BOX = registerBlock("purple_gift_box", new GiftBoxBlock(DyeColor.PURPLE, giftBoxProperties));
    public static final Block MAGENTA_GIFT_BOX = registerBlock("magenta_gift_box", new GiftBoxBlock(DyeColor.MAGENTA, giftBoxProperties));
    public static final Block PINK_GIFT_BOX = registerBlock("pink_gift_box", new GiftBoxBlock(DyeColor.PINK, giftBoxProperties));

//    public static final Block GIFT_BOX = registerBlock("gift_box", new GiftBoxBlock(DyeColor.WHITE, Block.Properties.of()));

    // Registry

    @SuppressWarnings("all")
    private static Block registerBlock(String id, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, SeasonsGreetings.id(id), block);
    }

    public static void registerItems() {}
}
