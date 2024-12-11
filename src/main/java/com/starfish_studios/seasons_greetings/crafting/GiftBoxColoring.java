package com.starfish_studios.seasons_greetings.crafting;

import com.starfish_studios.seasons_greetings.block.GiftBoxBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class GiftBoxColoring extends CustomRecipe {
    public GiftBoxColoring(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    public boolean matches(CraftingInput craftingInput, Level level) {
        int i = 0;
        int j = 0;

        for(int k = 0; k < craftingInput.size(); ++k) {
            ItemStack itemStack = craftingInput.getItem(k);
            if (!itemStack.isEmpty()) {
                if (Block.byItem(itemStack.getItem()) instanceof GiftBoxBlock) {
                    ++i;
                } else {
                    if (!(itemStack.getItem() instanceof DyeItem)) {
                        return false;
                    }

                    ++j;
                }

                if (j > 1 || i > 1) {
                    return false;
                }
            }
        }

        return i == 1 && j == 1;
    }

    public @NotNull ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        ItemStack itemStack = ItemStack.EMPTY;
        DyeItem dyeItem = (DyeItem) Items.WHITE_DYE;

        for(int i = 0; i < craftingInput.size(); ++i) {
            ItemStack itemStack2 = craftingInput.getItem(i);
            if (!itemStack2.isEmpty()) {
                Item item = itemStack2.getItem();
                if (Block.byItem(item) instanceof GiftBoxBlock) {
                    itemStack = itemStack2;
                } else if (item instanceof DyeItem) {
                    dyeItem = (DyeItem)item;
                }
            }
        }

        Block block = GiftBoxBlock.getBlockByColor(dyeItem.getDyeColor());
        return itemStack.transmuteCopy(block, 1);
    }

    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return SGRecipeSerializer.GIFT_BOX_COLORING;
    }
}
