package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class SGTags {

    public static class SeasonsGreetingsEntityTags {
//        public static final TagKey<EntityType<?>> PASSIVE = of("passive");

        private static TagKey<EntityType<?>> of(String id) {
            return TagKey.create(Registries.ENTITY_TYPE, SeasonsGreetings.id(id));
        }
    }

    public static class SeasonsGreetingsItemTags {

        public static final TagKey<Item> SNOW_GOLEM_NOSES = of("snow_golem_noses");

//        public static final TagKey<Item> PEARLESCENT_FLOWERS = of("pearlescent_flowers");

        private static TagKey<Item> of(String id) {
            return TagKey.create(Registries.ITEM, SeasonsGreetings.id(id));
        }
    }

    public static class SeasonsGreetingsEnchantmentTags {
//        public static final TagKey<Enchantment> COMPRESS = of("compress");


        private static TagKey<Enchantment> of(String id) {
            return TagKey.create(Registries.ENCHANTMENT, SeasonsGreetings.id(id));
        }
    }

}
