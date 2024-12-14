package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.inventory.GiftBoxMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class SGMenus {

    public static final MenuType<GiftBoxMenu> GIFT_BOX = register("gift_box", GiftBoxMenu::new);

    public static void registerMenus() {
    }

    private static <T extends AbstractContainerMenu> MenuType register(String id, MenuType.MenuSupplier<T> menuSupplier) {
        return Registry.register(BuiltInRegistries.MENU, SeasonsGreetings.id(id), new MenuType(menuSupplier, FeatureFlags.VANILLA_SET));
    }
}
