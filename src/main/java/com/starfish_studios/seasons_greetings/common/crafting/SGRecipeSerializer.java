package com.starfish_studios.seasons_greetings.common.crafting;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SGRecipeSerializer {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, SeasonsGreetings.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<GiftBoxColoring>> GIFT_BOX_COLORING = RECIPE_SERIALIZERS.register(
            "gift_box_coloring", () -> new SimpleCraftingRecipeSerializer<>(GiftBoxColoring::new));

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<StringLightsRecipe>> STRING_LIGHTS = RECIPE_SERIALIZERS.register(
            "multicolor_lights_recipe", () -> new SimpleCraftingRecipeSerializer<>(StringLightsRecipe::new));

    public static void registerCustomRecipes(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}