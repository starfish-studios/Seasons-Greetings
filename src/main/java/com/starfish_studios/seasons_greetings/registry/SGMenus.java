package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.inventory.GiftBoxMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SGMenus {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, SeasonsGreetings.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<GiftBoxMenu>> GIFT_BOX = register("gift_box", GiftBoxMenu::new);

    public static void registerMenus(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(String id, MenuType.MenuSupplier<T> menuSupplier) {
        return MENU_TYPES.register(id, () -> new MenuType<T>(menuSupplier, FeatureFlags.VANILLA_SET));
    }
}
