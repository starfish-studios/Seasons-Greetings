package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.crafting.SGRecipeSerializer;

public class SGRegistry {

    public static void registerAll() {
        SGRecipeSerializer.registerCustomRecipes();
        SGEffects.registerEffects();
        SGParticles.registerParticles();
        SGBlockEntityType.registerBlockEntities();
        SGMenus.registerMenus();
        SGSoundEvents.registerSoundEvents();
        SGPotions.registerPotions();
        SGItems.registerItems();
        SGCreativeTabs.registerCreativeTabs();
    }
}
