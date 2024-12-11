package com.starfish_studios.seasons_greetings.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GiftBoxSlot extends Slot {
    public GiftBoxSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.getItem().canFitInsideContainerItems();
    }
}