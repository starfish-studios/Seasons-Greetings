package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SGParticles {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, SeasonsGreetings.MOD_ID);

    public static DeferredHolder<ParticleType<?>, SimpleParticleType> COCOA_BUBBLE = registerSimple("cocoa_bubble");
    public static DeferredHolder<ParticleType<?>, SimpleParticleType> MILK_BUBBLE = registerSimple("milk_bubble");
    public static DeferredHolder<ParticleType<?>, SimpleParticleType> EGGNOG_BUBBLE = registerSimple("eggnog_bubble");

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> registerSimple(String id) {
        return register(id, new SimpleParticleType(false) {});
    }

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(String id, SimpleParticleType type) {
        return PARTICLE_TYPES.register(id, () -> type);
    }

    public static void registerParticles(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
