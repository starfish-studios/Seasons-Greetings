package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.item.GiftBoxItem;
import com.starfish_studios.seasons_greetings.item.HotCocoaBottleItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;

public class SGItems {

    // region Edible Items

    public static final Item FRUITCAKE = registerItem("fruitcake", new Item(new Item.Properties()));

    public static final Item GINGERBREAD_COOKIE = registerItem("gingerbread_cookie", new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
            .build())));
    public static final Item GINGERBREAD_CRUMBS = registerItem("gingerbread_crumbs", new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationModifier(0.1f)
            .build())));

    public static final Item CHOCOLATE = registerItem("chocolate", new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
            .build())));

    public static final Item WARM_MILK_BOTTLE = registerItem("warm_milk_bottle", new HotCocoaBottleItem(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.3f)
            .build())));

    public static  final Item HOT_COCOA_BOTTLE = registerItem("hot_cocoa_bottle", new HotCocoaBottleItem(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.3f)
            .build())));

    // endregion

    public static final Item STRING_LIGHTS = registerItem("string_lights", new BlockItem(SGBlocks.STRING_LIGHTS, new Item.Properties()));
    public static final Item WREATH = registerItem("wreath", new BlockItem(SGBlocks.WREATH, new Item.Properties()));

    // region Gifts

    public static final Item WHITE_GIFT_BOX = registerItem("white_gift_box", new GiftBoxItem(SGBlocks.WHITE_GIFT_BOX, new Item.Properties()));
    public static final Item LIGHT_GRAY_GIFT_BOX = registerItem("light_gray_gift_box", new GiftBoxItem(SGBlocks.LIGHT_GRAY_GIFT_BOX, new Item.Properties()));
    public static final Item GRAY_GIFT_BOX = registerItem("gray_gift_box", new GiftBoxItem(SGBlocks.GRAY_GIFT_BOX, new Item.Properties()));
    public static final Item BLACK_GIFT_BOX = registerItem("black_gift_box", new GiftBoxItem(SGBlocks.BLACK_GIFT_BOX, new Item.Properties()));
    public static final Item BROWN_GIFT_BOX = registerItem("brown_gift_box", new GiftBoxItem(SGBlocks.BROWN_GIFT_BOX, new Item.Properties()));
    public static final Item RED_GIFT_BOX = registerItem("red_gift_box", new GiftBoxItem(SGBlocks.RED_GIFT_BOX, new Item.Properties()));
    public static final Item ORANGE_GIFT_BOX = registerItem("orange_gift_box", new GiftBoxItem(SGBlocks.ORANGE_GIFT_BOX, new Item.Properties()));
    public static final Item YELLOW_GIFT_BOX = registerItem("yellow_gift_box", new GiftBoxItem(SGBlocks.YELLOW_GIFT_BOX, new Item.Properties()));
    public static final Item LIME_GIFT_BOX = registerItem("lime_gift_box", new GiftBoxItem(SGBlocks.LIME_GIFT_BOX, new Item.Properties()));
    public static final Item GREEN_GIFT_BOX = registerItem("green_gift_box", new GiftBoxItem(SGBlocks.GREEN_GIFT_BOX, new Item.Properties()));
    public static final Item CYAN_GIFT_BOX = registerItem("cyan_gift_box", new GiftBoxItem(SGBlocks.CYAN_GIFT_BOX, new Item.Properties()));
    public static final Item LIGHT_BLUE_GIFT_BOX = registerItem("light_blue_gift_box", new GiftBoxItem(SGBlocks.LIGHT_BLUE_GIFT_BOX, new Item.Properties()));
    public static final Item BLUE_GIFT_BOX = registerItem("blue_gift_box", new GiftBoxItem(SGBlocks.BLUE_GIFT_BOX, new Item.Properties()));
    public static final Item PURPLE_GIFT_BOX = registerItem("purple_gift_box", new GiftBoxItem(SGBlocks.PURPLE_GIFT_BOX, new Item.Properties()));
    public static final Item MAGENTA_GIFT_BOX = registerItem("magenta_gift_box", new GiftBoxItem(SGBlocks.MAGENTA_GIFT_BOX, new Item.Properties()));
    public static final Item PINK_GIFT_BOX = registerItem("pink_gift_box", new GiftBoxItem(SGBlocks.PINK_GIFT_BOX, new Item.Properties()));

    // endregion

    // region Gumdrops

    public static final Item RED_GUMDROP = registerItem("red_gumdrop", new BlockItem(SGBlocks.RED_GUMDROP, new Item.Properties()));
    public static final Item ORANGE_GUMDROP = registerItem("orange_gumdrop", new BlockItem(SGBlocks.ORANGE_GUMDROP, new Item.Properties()));
    public static final Item YELLOW_GUMDROP = registerItem("yellow_gumdrop", new BlockItem(SGBlocks.YELLOW_GUMDROP, new Item.Properties()));
    public static final Item GREEN_GUMDROP = registerItem("green_gumdrop", new BlockItem(SGBlocks.GREEN_GUMDROP, new Item.Properties()));
    public static final Item PURPLE_GUMDROP = registerItem("purple_gumdrop", new BlockItem(SGBlocks.PURPLE_GUMDROP, new Item.Properties()));

    // endregion

    // region Gingerbread

    public static final Item GINGERBREAD_BLOCK = registerItem("gingerbread_block", new BlockItem(SGBlocks.GINGERBREAD_BLOCK, new Item.Properties()));
    public static final Item GINGERBREAD_STAIRS = registerItem("gingerbread_stairs", new BlockItem(SGBlocks.GINGERBREAD_STAIRS, new Item.Properties()));
    public static final Item GINGERBREAD_SLAB = registerItem("gingerbread_slab", new BlockItem(SGBlocks.GINGERBREAD_SLAB, new Item.Properties()));
    public static final Item GINGERBREAD_BRICKS = registerItem("gingerbread_bricks", new BlockItem(SGBlocks.GINGERBREAD_BRICKS, new Item.Properties()));
    public static final Item GINGERBREAD_BRICK_STAIRS = registerItem("gingerbread_brick_stairs", new BlockItem(SGBlocks.GINGERBREAD_BRICK_STAIRS, new Item.Properties()));
    public static final Item GINGERBREAD_BRICK_SLAB = registerItem("gingerbread_brick_slab", new BlockItem(SGBlocks.GINGERBREAD_BRICK_SLAB, new Item.Properties()));
    public static final Item GINGERBREAD_SHINGLES = registerItem("gingerbread_shingles", new BlockItem(SGBlocks.GINGERBREAD_SHINGLES, new Item.Properties()));
    public static final Item GINGERBREAD_SHINGLE_STAIRS = registerItem("gingerbread_shingle_stairs", new BlockItem(SGBlocks.GINGERBREAD_SHINGLE_STAIRS, new Item.Properties()));
    public static final Item GINGERBREAD_SHINGLE_SLAB = registerItem("gingerbread_shingle_slab", new BlockItem(SGBlocks.GINGERBREAD_SHINGLE_SLAB, new Item.Properties()));

    // endregion

    public static final Item PEPPERMINT_BLOCK = registerItem("peppermint_block", new BlockItem(SGBlocks.PEPPERMINT_BLOCK, new Item.Properties()));
    public static final Item PEPPERMINT_STAIRS = registerItem("peppermint_stairs", new BlockItem(SGBlocks.PEPPERMINT_STAIRS, new Item.Properties()));
    public static final Item PEPPERMINT_SLAB = registerItem("peppermint_slab", new BlockItem(SGBlocks.PEPPERMINT_SLAB, new Item.Properties()));

    public static final Item CHOCOLATE_BLOCK = registerItem("chocolate_block", new BlockItem(SGBlocks.CHOCOLATE_BLOCK, new Item.Properties()));
    public static final Item CHOCOLATE_STAIRS = registerItem("chocolate_stairs", new BlockItem(SGBlocks.CHOCOLATE_STAIRS, new Item.Properties()));
    public static final Item CHOCOLATE_SLAB = registerItem("chocolate_slab", new BlockItem(SGBlocks.CHOCOLATE_SLAB, new Item.Properties()));

    // Registry

    @SuppressWarnings("all")
    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, SeasonsGreetings.id(name), item);
    }

    public static void registerItems() {}
}
