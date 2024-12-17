package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.common.crafting.SGRecipeSerializer;
import net.neoforged.bus.api.IEventBus;

public class SGRegistry {

    public static void registerAll(IEventBus eventBus) {
        SGRecipeSerializer.registerCustomRecipes(eventBus);
        SGEffects.registerEffects(eventBus);
        SGParticles.registerParticles(eventBus);
        SGBlockEntityType.registerBlockEntities(eventBus);
        SGMenus.registerMenus(eventBus);
        SGSoundEvents.registerSoundEvents(eventBus);
        SGPotions.registerPotions();
        SGEntityType.registerEntities(eventBus);
        SGBlocks.registerBlocks(eventBus);
        SGItems.registerItems(eventBus);
        SGCreativeTabs.registerCreativeTabs(eventBus);
        SGConditions.registerConditions(eventBus);
    }
}
