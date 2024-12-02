package com.starfish_studios.seasons_greetings.registry;

public class SGRegistry {

    public static void registerAll() {
//        SeasonsGreetingsEntityType.registerEntities();
        SGParticles.registerParticles();
        SGBlockEntityType.registerBlockEntities();
        SGMenus.registerMenus();
        SGSoundEvents.registerSoundEvents();
        SGPotions.registerPotions();
        SGItems.registerItems();
        SGCreativeTabs.registerCreativeTabs();
    }
}
