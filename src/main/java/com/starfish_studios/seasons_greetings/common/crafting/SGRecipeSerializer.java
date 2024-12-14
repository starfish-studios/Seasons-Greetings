package com.starfish_studios.seasons_greetings.common.crafting;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public interface SGRecipeSerializer {
    SimpleCraftingRecipeSerializer<GiftBoxColoring> GIFT_BOX_COLORING = register("gift_box_coloring", new SimpleCraftingRecipeSerializer<>(GiftBoxColoring::new));

    SimpleCraftingRecipeSerializer<StringLightsRecipe> STRING_LIGHTS = register("multicolor_lights_recipe", new SimpleCraftingRecipeSerializer<>(StringLightsRecipe::new));

    static void registerCustomRecipes() {
    }

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String string, S recipeSerializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, SeasonsGreetings.id(string), recipeSerializer);
    }
}