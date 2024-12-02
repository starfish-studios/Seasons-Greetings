package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

import static com.starfish_studios.seasons_greetings.registry.SGItems.*;

public class SGCreativeTabs {
    @SuppressWarnings("unused")
    public static final CreativeModeTab SEASONS_GREETINGS_TAB = register("item_group", FabricItemGroup.builder().icon(FRUITCAKE::getDefaultInstance).title(Component.translatable("itemGroup.seasonsgreetings.tab")).displayItems((featureFlagSet, output) -> {
        output.accept(FRUITCAKE);
        output.accept(STRING_LIGHTS);
        output.accept(CHOCOLATE);
        output.accept(HOT_COCOA_BOTTLE);

        output.accept(WHITE_GIFT_BOX);
        output.accept(LIGHT_GRAY_GIFT_BOX);
        output.accept(GRAY_GIFT_BOX);
        output.accept(BLACK_GIFT_BOX);
        output.accept(BROWN_GIFT_BOX);
//        output.accept(RED_GIFT_BOX);
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

    }).build()
    );

    @SuppressWarnings("all")
    private static CreativeModeTab register(String id, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SeasonsGreetings.id(id), tab);
    }

    public static void registerCreativeTabs() {
//    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> entries.addAfter(Items.MACE, SGItems.WAND));
    }
}
