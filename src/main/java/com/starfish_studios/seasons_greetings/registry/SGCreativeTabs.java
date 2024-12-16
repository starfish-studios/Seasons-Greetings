package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SGConfig;
import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.starfish_studios.seasons_greetings.registry.SGItems.BLACK_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.BLUE_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.BLUE_LIGHTS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.BROKEN_GINGERBREAD_MAN;
import static com.starfish_studios.seasons_greetings.registry.SGItems.BROWN_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.CHISELED_SNOW;
import static com.starfish_studios.seasons_greetings.registry.SGItems.CHOCOLATE;
import static com.starfish_studios.seasons_greetings.registry.SGItems.CHOCOLATE_BLOCK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.CHOCOLATE_SLAB;
import static com.starfish_studios.seasons_greetings.registry.SGItems.CHOCOLATE_STAIRS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.CHRISTMAS_HAT;
import static com.starfish_studios.seasons_greetings.registry.SGItems.CYAN_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.EGGNOG;
import static com.starfish_studios.seasons_greetings.registry.SGItems.EGGNOG_BUCKET;
import static com.starfish_studios.seasons_greetings.registry.SGItems.FRUITCAKE;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_BLOCK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_BRICKS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_BRICK_SLAB;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_BRICK_STAIRS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_COOKIE;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_CRUMBS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_DOOR;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_MAN;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_MAN_SPAWN_EGG;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_SHINGLES;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_SHINGLE_SLAB;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_SHINGLE_STAIRS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_SLAB;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_STAIRS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GINGERBREAD_TRAPDOOR;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GRAY_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GREEN_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GREEN_GUMDROP_BLOCK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GREEN_GUMDROP_BUTTON;
import static com.starfish_studios.seasons_greetings.registry.SGItems.GREEN_LIGHTS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.HOT_COCOA;
import static com.starfish_studios.seasons_greetings.registry.SGItems.HOT_COCOA_BUCKET;
import static com.starfish_studios.seasons_greetings.registry.SGItems.ICICLE;
import static com.starfish_studios.seasons_greetings.registry.SGItems.ICING;
import static com.starfish_studios.seasons_greetings.registry.SGItems.ICING_BLOCK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.ICING_SLAB;
import static com.starfish_studios.seasons_greetings.registry.SGItems.ICING_STAIRS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.LIGHT_BLUE_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.LIGHT_GRAY_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.LIME_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.MAGENTA_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.MULTICOLOR_LIGHTS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.ORANGE_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.ORANGE_GUMDROP_BLOCK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.ORANGE_GUMDROP_BUTTON;
import static com.starfish_studios.seasons_greetings.registry.SGItems.ORANGE_LIGHTS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.PACKED_SNOW;
import static com.starfish_studios.seasons_greetings.registry.SGItems.PEPPERMINT_BLOCK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.PEPPERMINT_SLAB;
import static com.starfish_studios.seasons_greetings.registry.SGItems.PEPPERMINT_STAIRS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.PINK_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.PURPLE_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.PURPLE_GUMDROP_BLOCK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.PURPLE_GUMDROP_BUTTON;
import static com.starfish_studios.seasons_greetings.registry.SGItems.PURPLE_LIGHTS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.RED_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.RED_GUMDROP_BLOCK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.RED_GUMDROP_BUTTON;
import static com.starfish_studios.seasons_greetings.registry.SGItems.RED_LIGHTS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.SNOW_BRICKS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.SNOW_BRICK_SLAB;
import static com.starfish_studios.seasons_greetings.registry.SGItems.SNOW_BRICK_STAIRS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.WARM_MILK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.WHITE_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.WHITE_LIGHTS;
import static com.starfish_studios.seasons_greetings.registry.SGItems.WREATH;
import static com.starfish_studios.seasons_greetings.registry.SGItems.YELLOW_GIFT_BOX;
import static com.starfish_studios.seasons_greetings.registry.SGItems.YELLOW_GUMDROP_BLOCK;
import static com.starfish_studios.seasons_greetings.registry.SGItems.YELLOW_GUMDROP_BUTTON;
import static com.starfish_studios.seasons_greetings.registry.SGItems.YELLOW_LIGHTS;

public class SGCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SeasonsGreetings.MOD_ID);

    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SEASONS_GREETINGS_TAB = CREATIVE_MODE_TABS.register("item_group", () -> CreativeModeTab.builder().icon(() -> FRUITCAKE.get().getDefaultInstance()).title(Component.translatable("itemGroup.seasonsgreetings.tab")).displayItems((featureFlagSet, output) -> {
        ItemStack stack = new ItemStack(CHRISTMAS_HAT.asItem());
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(0xA06540, false));

        output.accept(stack);

//        output.accept(WARM_MILK_BUCKET);
        output.accept(HOT_COCOA_BUCKET);
        output.accept(EGGNOG_BUCKET);
        output.accept(WARM_MILK);
        output.accept(HOT_COCOA);
        output.accept(EGGNOG);

        output.accept(FRUITCAKE);
        output.accept(GINGERBREAD_COOKIE);
        output.accept(GINGERBREAD_MAN);
        output.accept(BROKEN_GINGERBREAD_MAN);

                // Config to enable/disable Gingerbread Blocksets - enabled by default.
                if (SGConfig.gingerbreadBlocks) {
                    output.accept(GINGERBREAD_CRUMBS);
                    output.accept(GINGERBREAD_DOOR);
                    output.accept(GINGERBREAD_TRAPDOOR);
                    output.accept(GINGERBREAD_BLOCK);
                    output.accept(GINGERBREAD_STAIRS);
                    output.accept(GINGERBREAD_SLAB);
                    output.accept(GINGERBREAD_BRICKS);
                    output.accept(GINGERBREAD_BRICK_STAIRS);
                    output.accept(GINGERBREAD_BRICK_SLAB);
                    output.accept(GINGERBREAD_SHINGLES);
                    output.accept(GINGERBREAD_SHINGLE_STAIRS);
                    output.accept(GINGERBREAD_SHINGLE_SLAB);
                }

                // Config to enable/disable String Lights - enabled by default.
                if (SGConfig.stringLights) {
                    output.accept(WHITE_LIGHTS);
                    output.accept(RED_LIGHTS);
                    output.accept(ORANGE_LIGHTS);
                    output.accept(YELLOW_LIGHTS);
                    output.accept(GREEN_LIGHTS);
                    output.accept(BLUE_LIGHTS);
                    output.accept(PURPLE_LIGHTS);
                    output.accept(MULTICOLOR_LIGHTS);
                }

                // Config to enable/disable Wreath Blocks - enabled by default.
                if (SGConfig.wreathBlock) {
                    output.accept(WREATH);
                }

                // Config to enable/disable Snow Blocks - enabled by default.
                if (SGConfig.snowBlocks) {
                    output.accept(PACKED_SNOW);
                    output.accept(SNOW_BRICKS);
                    output.accept(SNOW_BRICK_STAIRS);
                    output.accept(SNOW_BRICK_SLAB);
                    output.accept(CHISELED_SNOW);
                }

                // Config to enable/disable Icicles - enabled by default.
                if (SGConfig.icicle) {
                    output.accept(ICICLE);
                }

                // Config to enable/disable Peppermint Blocks - enabled by default.
                if (SGConfig.peppermint) {
                    output.accept(PEPPERMINT_BLOCK);
                    output.accept(PEPPERMINT_STAIRS);
                    output.accept(PEPPERMINT_SLAB);
                }

                // Config to enable/disable Chocolate and Chocolate Blocks - enabled by default.
                if (SGConfig.chocolate) {
                    output.accept(CHOCOLATE);
                    output.accept(CHOCOLATE_BLOCK);
                    output.accept(CHOCOLATE_STAIRS);
                    output.accept(CHOCOLATE_SLAB);
                }

                // Config to enable/disable Icing and Icing Blocks - enabled by default.
                if (SGConfig.icing) {
                    output.accept(ICING);
                    output.accept(ICING_BLOCK);
                    output.accept(ICING_STAIRS);
                    output.accept(ICING_SLAB);
                }

                // Config to enable/disable Gumdrops and Gumdrops Blocks - enabled by default.
                if (SGConfig.gumdrops) {
                    output.accept(RED_GUMDROP_BLOCK);
                    output.accept(ORANGE_GUMDROP_BLOCK);
                    output.accept(YELLOW_GUMDROP_BLOCK);
                    output.accept(GREEN_GUMDROP_BLOCK);
                    output.accept(PURPLE_GUMDROP_BLOCK);

                    output.accept(RED_GUMDROP_BUTTON);
                    output.accept(ORANGE_GUMDROP_BUTTON);
                    output.accept(YELLOW_GUMDROP_BUTTON);
                    output.accept(GREEN_GUMDROP_BUTTON);
                    output.accept(PURPLE_GUMDROP_BUTTON);
                }

                // Config to enable/disable Gifts - enabled by default.
                if (SGConfig.gifts) {
                    output.accept(WHITE_GIFT_BOX);
                    output.accept(LIGHT_GRAY_GIFT_BOX);
                    output.accept(GRAY_GIFT_BOX);
                    output.accept(BLACK_GIFT_BOX);
                    output.accept(BROWN_GIFT_BOX);
                    output.accept(RED_GIFT_BOX);
                    output.accept(ORANGE_GIFT_BOX);
                    output.accept(YELLOW_GIFT_BOX);
                    output.accept(LIME_GIFT_BOX);
                    output.accept(GREEN_GIFT_BOX);
                    output.accept(CYAN_GIFT_BOX);
                    output.accept(LIGHT_BLUE_GIFT_BOX);
                    output.accept(BLUE_GIFT_BOX);
                    output.accept(PURPLE_GIFT_BOX);
                    output.accept(MAGENTA_GIFT_BOX);
                    output.accept(PINK_GIFT_BOX);
                }

        output.accept(GINGERBREAD_MAN_SPAWN_EGG);

    }).build()
    );

    public static void registerCreativeTabs(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
//    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> entries.addAfter(Items.MACE, SGItems.WAND));
    }
}
