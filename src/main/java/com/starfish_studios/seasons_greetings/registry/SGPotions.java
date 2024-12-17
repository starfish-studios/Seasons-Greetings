package com.starfish_studios.seasons_greetings.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.Potion;

public class SGPotions {
//    public static final Holder<Potion> METAMORPHOSIS = register("metamorphosis", new Potion(new MobEffectInstance(SeasonsGreetingsEffects.METAMORPHOSIS, 200)));

    private static Holder<Potion> register(String string, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, ResourceLocation.withDefaultNamespace(string), potion);
    }

    public static void registerPotions() {
//        FabricBrewingRecipeRegistryBuilder.BUILD.register((builder) -> {
//            builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(Items.BLAZE_POWDER), METAMORPHOSIS);
//        });
    }
}
