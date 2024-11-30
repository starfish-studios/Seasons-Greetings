package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class SGEffects {

//    public static final Holder<MobEffect> METAMORPHOSIS = register("metamorphosis", new MetamorphosisEffect(MobEffectCategory.HARMFUL, 0x4A4217));

    private static Holder<MobEffect> register(String id, MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, SeasonsGreetings.id(id), mobEffect);
    }
}
