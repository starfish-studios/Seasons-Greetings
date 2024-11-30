package com.starfish_studios.seasons_greetings.registry;

public class SGRegistry {

    public static void registerAll() {
//        SeasonsGreetingsEntityType.registerEntities();
        SGPotions.registerPotions();
        SGItems.registerItems();
        SGCreativeTabs.registerCreativeTabs();
    }
}
