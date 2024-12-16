package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.effects.CozyEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SGEffects {
    private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, SeasonsGreetings.MOD_ID);

    public static final Holder<MobEffect> COZY = EFFECTS.register("cozy", () -> new CozyEffect(MobEffectCategory.BENEFICIAL, 0xFFC72E));

    public static void registerEffects(IEventBus eventbus) {
        EFFECTS.register(eventbus);
    }
}
