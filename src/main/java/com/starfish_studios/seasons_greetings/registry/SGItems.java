package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;

public class SGItems {

    public static final Item FRUITCAKE = registerItem("fruitcake", new Item(new Item.Properties()));

    // Registry

    @SuppressWarnings("all")
    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, SeasonsGreetings.id(name), item);
    }

    public static void registerItems() {}
}
