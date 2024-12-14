package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.conditions.RecipeLoadCondition;
import com.starfish_studios.seasons_greetings.common.conditions.StructureSpawnCondition;
import com.starfish_studios.seasons_greetings.common.crafting.SGRecipeSerializer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;

public class SGRegistry {

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(SeasonsGreetings.id(name));
    }

    public static void registerAll() {

        RecipeLoadCondition.register();
        StructureSpawnCondition.register();

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
