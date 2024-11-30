package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

import static com.starfish_studios.seasons_greetings.registry.SGItems.*;

public class SGCreativeTabs {
    @SuppressWarnings("unused")
    public static final CreativeModeTab SPELLBOUND_TAB = register("item_group", FabricItemGroup.builder().icon(FRUITCAKE::getDefaultInstance).title(Component.translatable("itemGroup.seasonsgreetings.tab")).displayItems((featureFlagSet, output) -> {

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
