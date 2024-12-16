package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.item.ChristmasHatItem;
import com.starfish_studios.seasons_greetings.common.item.EggnogBucketItem;
import com.starfish_studios.seasons_greetings.common.item.EggnogItem;
import com.starfish_studios.seasons_greetings.common.item.GiftBoxItem;
import com.starfish_studios.seasons_greetings.common.item.GingerbreadManItem;
import com.starfish_studios.seasons_greetings.common.item.HotCocoaBucketItem;
import com.starfish_studios.seasons_greetings.common.item.HotCocoaItem;
import com.starfish_studios.seasons_greetings.common.item.WarmMilkItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SGItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SeasonsGreetings.MOD_ID);

    public static final DeferredItem<Item> CHRISTMAS_HAT = ITEMS.register("christmas_hat", () -> new ChristmasHatItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new Item.Properties()));

    // region Edible Items

    public static final DeferredItem<Item> FRUITCAKE = ITEMS.register("fruitcake", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(10)
                    .saturationModifier(0.3f)
            .build())));

    public static final DeferredItem<Item> GINGERBREAD_COOKIE = ITEMS.register("gingerbread_cookie", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
            .build())));

    public static final DeferredItem<Item> BROKEN_GINGERBREAD_MAN = ITEMS.register("broken_gingerbread_man", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationModifier(0.1f)
            .build())));

    public static final DeferredItem<Item> GINGERBREAD_MAN = ITEMS.register("gingerbread_man", () -> new GingerbreadManItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> GINGERBREAD_CRUMBS = ITEMS.register("gingerbread_crumbs", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationModifier(0.1f)
                    .fast()
            .build())));

    public static final DeferredItem<Item> CHOCOLATE = ITEMS.register("chocolate", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
            .build())));

//    public static final DeferredItem<Item> WARM_MILK_BUCKET = ITEMS.register("warm_milk_bucket", new WarmMilkBucketItem(new Item.Properties()));
    public static final DeferredItem<Item> WARM_MILK = ITEMS.register("warm_milk", () -> new WarmMilkItem(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.3f)
            .build())));

    public static final DeferredItem<Item> HOT_COCOA_BUCKET = ITEMS.register("hot_cocoa_bucket", () -> new HotCocoaBucketItem(new Item.Properties()));
    public static  final DeferredItem<Item> HOT_COCOA = ITEMS.register("hot_cocoa", () -> new HotCocoaItem(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.3f)
            .build())));


    public static final DeferredItem<Item> EGGNOG_BUCKET = ITEMS.register("eggnog_bucket", () -> new EggnogBucketItem(new Item.Properties()));
    public static final DeferredItem<Item> EGGNOG = ITEMS.register("eggnog", () -> new EggnogItem(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.3f)
            .build())));

    // endregion

    public static final DeferredItem<BlockItem> WREATH = ITEMS.registerSimpleBlockItem(SGBlocks.WREATH);

    public static final DeferredItem<BlockItem> PACKED_SNOW = ITEMS.registerSimpleBlockItem(SGBlocks.PACKED_SNOW);
    public static final DeferredItem<BlockItem> SNOW_BRICKS = ITEMS.registerSimpleBlockItem(SGBlocks.SNOW_BRICKS);
    public static final DeferredItem<BlockItem> SNOW_BRICK_STAIRS = ITEMS.registerSimpleBlockItem(SGBlocks.SNOW_BRICK_STAIRS);
    public static final DeferredItem<BlockItem> SNOW_BRICK_SLAB = ITEMS.registerSimpleBlockItem(SGBlocks.SNOW_BRICK_SLAB);
    public static final DeferredItem<BlockItem> CHISELED_SNOW = ITEMS.registerSimpleBlockItem(SGBlocks.CHISELED_SNOW);

    public static final DeferredItem<BlockItem> ICICLE = ITEMS.registerSimpleBlockItem(SGBlocks.ICICLE);

    // region Lights
    public static final DeferredItem<BlockItem> WHITE_LIGHTS = ITEMS.registerSimpleBlockItem(SGBlocks.WHITE_LIGHTS);
    public static final DeferredItem<BlockItem> RED_LIGHTS = ITEMS.registerSimpleBlockItem(SGBlocks.RED_LIGHTS);
    public static final DeferredItem<BlockItem> ORANGE_LIGHTS = ITEMS.registerSimpleBlockItem(SGBlocks.ORANGE_LIGHTS);
    public static final DeferredItem<BlockItem> YELLOW_LIGHTS = ITEMS.registerSimpleBlockItem(SGBlocks.YELLOW_LIGHTS);
    public static final DeferredItem<BlockItem> GREEN_LIGHTS = ITEMS.registerSimpleBlockItem(SGBlocks.GREEN_LIGHTS);
    public static final DeferredItem<BlockItem> BLUE_LIGHTS = ITEMS.registerSimpleBlockItem(SGBlocks.BLUE_LIGHTS);
    public static final DeferredItem<BlockItem> PURPLE_LIGHTS = ITEMS.registerSimpleBlockItem(SGBlocks.PURPLE_LIGHTS);
    public static final DeferredItem<BlockItem> MULTICOLOR_LIGHTS = ITEMS.registerSimpleBlockItem(SGBlocks.MULTICOLOR_LIGHTS);
    // endregion

    // region Gifts

    public static final Item.Properties giftBoxProperties = new Item.Properties().stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

    public static final DeferredItem<GiftBoxItem> WHITE_GIFT_BOX = ITEMS.register("white_gift_box", () -> new GiftBoxItem(SGBlocks.WHITE_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> LIGHT_GRAY_GIFT_BOX = ITEMS.register("light_gray_gift_box", () -> new GiftBoxItem(SGBlocks.LIGHT_GRAY_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> GRAY_GIFT_BOX = ITEMS.register("gray_gift_box", () -> new GiftBoxItem(SGBlocks.GRAY_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> BLACK_GIFT_BOX = ITEMS.register("black_gift_box", () -> new GiftBoxItem(SGBlocks.BLACK_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> BROWN_GIFT_BOX = ITEMS.register("brown_gift_box", () -> new GiftBoxItem(SGBlocks.BROWN_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> RED_GIFT_BOX = ITEMS.register("red_gift_box", () -> new GiftBoxItem(SGBlocks.RED_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> ORANGE_GIFT_BOX = ITEMS.register("orange_gift_box", () -> new GiftBoxItem(SGBlocks.ORANGE_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> YELLOW_GIFT_BOX = ITEMS.register("yellow_gift_box", () -> new GiftBoxItem(SGBlocks.YELLOW_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> LIME_GIFT_BOX = ITEMS.register("lime_gift_box", () -> new GiftBoxItem(SGBlocks.LIME_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> GREEN_GIFT_BOX = ITEMS.register("green_gift_box", () -> new GiftBoxItem(SGBlocks.GREEN_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> CYAN_GIFT_BOX = ITEMS.register("cyan_gift_box", () -> new GiftBoxItem(SGBlocks.CYAN_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> LIGHT_BLUE_GIFT_BOX = ITEMS.register("light_blue_gift_box", () -> new GiftBoxItem(SGBlocks.LIGHT_BLUE_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> BLUE_GIFT_BOX = ITEMS.register("blue_gift_box", () -> new GiftBoxItem(SGBlocks.BLUE_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> PURPLE_GIFT_BOX = ITEMS.register("purple_gift_box", () -> new GiftBoxItem(SGBlocks.PURPLE_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> MAGENTA_GIFT_BOX = ITEMS.register("magenta_gift_box", () -> new GiftBoxItem(SGBlocks.MAGENTA_GIFT_BOX.get(), giftBoxProperties));
    public static final DeferredItem<GiftBoxItem> PINK_GIFT_BOX = ITEMS.register("pink_gift_box", () -> new GiftBoxItem(SGBlocks.PINK_GIFT_BOX.get(), giftBoxProperties));

    // endregion

    // region Gumdrop Blocks

    public static final DeferredItem<BlockItem> RED_GUMDROP_BLOCK = ITEMS.registerSimpleBlockItem(SGBlocks.RED_GUMDROP_BLOCK);
    public static final DeferredItem<BlockItem> ORANGE_GUMDROP_BLOCK = ITEMS.registerSimpleBlockItem(SGBlocks.ORANGE_GUMDROP_BLOCK);
    public static final DeferredItem<BlockItem> YELLOW_GUMDROP_BLOCK = ITEMS.registerSimpleBlockItem(SGBlocks.YELLOW_GUMDROP_BLOCK);
    public static final DeferredItem<BlockItem> GREEN_GUMDROP_BLOCK = ITEMS.registerSimpleBlockItem(SGBlocks.GREEN_GUMDROP_BLOCK);
    public static final DeferredItem<BlockItem> PURPLE_GUMDROP_BLOCK = ITEMS.registerSimpleBlockItem(SGBlocks.PURPLE_GUMDROP_BLOCK);

    public static final DeferredItem<BlockItem> RED_GUMDROP_BUTTON = ITEMS.registerSimpleBlockItem(SGBlocks.RED_GUMDROP_BUTTON);
    public static final DeferredItem<BlockItem> ORANGE_GUMDROP_BUTTON = ITEMS.registerSimpleBlockItem(SGBlocks.ORANGE_GUMDROP_BUTTON);
    public static final DeferredItem<BlockItem> YELLOW_GUMDROP_BUTTON = ITEMS.registerSimpleBlockItem(SGBlocks.YELLOW_GUMDROP_BUTTON);
    public static final DeferredItem<BlockItem> GREEN_GUMDROP_BUTTON = ITEMS.registerSimpleBlockItem(SGBlocks.GREEN_GUMDROP_BUTTON);
    public static final DeferredItem<BlockItem> PURPLE_GUMDROP_BUTTON = ITEMS.registerSimpleBlockItem(SGBlocks.PURPLE_GUMDROP_BUTTON);


    // endregion

    // region Gingerbread

    public static final DeferredItem<BlockItem> GINGERBREAD_BLOCK = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_BLOCK);
    public static final DeferredItem<BlockItem> GINGERBREAD_STAIRS = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_STAIRS);
    public static final DeferredItem<BlockItem> GINGERBREAD_SLAB = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_SLAB);
    public static final DeferredItem<BlockItem> GINGERBREAD_BRICKS = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_BRICKS);
    public static final DeferredItem<BlockItem> GINGERBREAD_BRICK_STAIRS = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_BRICK_STAIRS);
    public static final DeferredItem<BlockItem> GINGERBREAD_BRICK_SLAB = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_BRICK_SLAB);
    public static final DeferredItem<BlockItem> GINGERBREAD_SHINGLES = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_SHINGLES);
    public static final DeferredItem<BlockItem> GINGERBREAD_SHINGLE_STAIRS = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_SHINGLE_STAIRS);
    public static final DeferredItem<BlockItem> GINGERBREAD_SHINGLE_SLAB = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_SHINGLE_SLAB);

    // Gingerbread door
    public static final DeferredItem<Item> GINGERBREAD_DOOR = ITEMS.register("gingerbread_door", () -> new DoubleHighBlockItem(SGBlocks.GINGERBREAD_DOOR.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> GINGERBREAD_TRAPDOOR = ITEMS.registerSimpleBlockItem(SGBlocks.GINGERBREAD_TRAPDOOR);

    // endregion

    public static final DeferredItem<BlockItem> ICING = ITEMS.registerSimpleBlockItem(SGBlocks.ICING);
    public static final DeferredItem<BlockItem> ICING_BLOCK = ITEMS.registerSimpleBlockItem(SGBlocks.ICING_BLOCK);
    public static final DeferredItem<BlockItem> ICING_STAIRS = ITEMS.registerSimpleBlockItem(SGBlocks.ICING_STAIRS);
    public static final DeferredItem<BlockItem> ICING_SLAB = ITEMS.registerSimpleBlockItem(SGBlocks.ICING_SLAB);

    public static final DeferredItem<BlockItem> PEPPERMINT_BLOCK = ITEMS.registerSimpleBlockItem(SGBlocks.PEPPERMINT_BLOCK);
    public static final DeferredItem<BlockItem> PEPPERMINT_STAIRS = ITEMS.registerSimpleBlockItem(SGBlocks.PEPPERMINT_STAIRS);
    public static final DeferredItem<BlockItem> PEPPERMINT_SLAB = ITEMS.registerSimpleBlockItem(SGBlocks.PEPPERMINT_SLAB);

    public static final DeferredItem<BlockItem> CHOCOLATE_BLOCK = ITEMS.registerSimpleBlockItem(SGBlocks.CHOCOLATE_BLOCK);
    public static final DeferredItem<BlockItem> CHOCOLATE_STAIRS = ITEMS.registerSimpleBlockItem(SGBlocks.CHOCOLATE_STAIRS);
    public static final DeferredItem<BlockItem> CHOCOLATE_SLAB = ITEMS.registerSimpleBlockItem(SGBlocks.CHOCOLATE_SLAB);

    public static final DeferredItem<SpawnEggItem> GINGERBREAD_MAN_SPAWN_EGG = ITEMS.register("gingerbread_man_spawn_egg", () -> new DeferredSpawnEggItem(SGEntityType.GINGERBREAD_MAN, 0xFF8B24, 0xFFFFFF, new Item.Properties()));

    // Registry

    public static void registerItems(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
