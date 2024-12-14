package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.effects.CozyEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class SGEffects {

    public static final Holder<MobEffect> COZY = register("cozy", new CozyEffect(MobEffectCategory.BENEFICIAL, 0xFFC72E));

    private static Holder<MobEffect> register(String id, MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, SeasonsGreetings.id(id), mobEffect);
    }

    public static void registerEffects() {
    }
}
