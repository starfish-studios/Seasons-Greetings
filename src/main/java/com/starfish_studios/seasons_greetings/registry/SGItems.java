package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.item.GiftBoxItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;

public class SGItems {

    public static final Item FRUITCAKE = registerItem("fruitcake", new Item(new Item.Properties()));

//    public static final Item GIFT_BOX = registerItem("gift_box", new BlockItem(SGBlocks.GIFT_BOX, new Item.Properties()));
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


    // Registry

    @SuppressWarnings("all")
    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, SeasonsGreetings.id(name), item);
    }

    public static void registerItems() {}
}
