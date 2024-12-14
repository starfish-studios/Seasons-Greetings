package com.starfish_studios.seasons_greetings.common.crafting;

import com.starfish_studios.seasons_greetings.registry.SGItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class StringLightsRecipe extends CustomRecipe {

    public StringLightsRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        Set<ItemStack> uniqueItems = new HashSet<>();
        Set<ResourceLocation> itemIds = new HashSet<>();

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack stack = recipeInput.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.is(SGItems.RED_LIGHTS) || stack.is(SGItems.ORANGE_LIGHTS) ||
                    stack.is(SGItems.YELLOW_LIGHTS) || stack.is(SGItems.GREEN_LIGHTS) ||
                    stack.is(SGItems.BLUE_LIGHTS) || stack.is(SGItems.PURPLE_LIGHTS)) {

                if (!itemIds.add(ResourceLocation.parse(stack.getItem().toString()))) {
                    return false;
                }
                uniqueItems.add(stack);
            }
        }
        return uniqueItems.size() == 4;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        return new ItemStack(SGItems.MULTICOLOR_LIGHTS, 4);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 4;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SGRecipeSerializer.STRING_LIGHTS;
    }
}
