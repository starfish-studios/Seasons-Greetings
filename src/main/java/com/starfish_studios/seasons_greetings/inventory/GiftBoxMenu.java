package com.starfish_studios.seasons_greetings.inventory;

import com.starfish_studios.seasons_greetings.registry.SGMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GiftBoxMenu extends AbstractContainerMenu {
    private final Container gitBox;

    public GiftBoxMenu(int i, Inventory inventory) {
        this(i, inventory, new SimpleContainer(5));
    }


    public Container getContainer() {
        return this.gitBox;
    }

    public GiftBoxMenu(int i, Inventory inventory, Container container) {
        super(SGMenus.GIFT_BOX, i);
        checkContainerSize(container, 5);
        this.gitBox = container;
        container.startOpen(inventory.player);
        int j;
        int k;
        for(j = 0; j < 1; ++j) {
            for(k = 0; k < 5; ++k) {
                this.addSlot(new GiftBoxSlot(container, k + j * 5, 44 + k * 18, 35 + j * 18));
            }
        }

        for(j = 0; j < 3; ++j) {
            for(k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for(j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
        }

    }

    public boolean stillValid(Player player) {
        return this.gitBox.stillValid(player);
    }

    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < 5) {
                if (!this.moveItemStackTo(itemStack2, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, 5, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
        }

        return itemStack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.gitBox.stopOpen(player);
    }
}
