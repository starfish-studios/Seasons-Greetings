package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class SGParticles {
    public static SimpleParticleType COCOA_BUBBLE = register("cocoa_bubble", FabricParticleTypes.simple());
    public static SimpleParticleType MILK_BUBBLE = register("milk_bubble", FabricParticleTypes.simple());
    public static SimpleParticleType EGGNOG_BUBBLE = register("eggnog_bubble", FabricParticleTypes.simple());

    public static SimpleParticleType register(String id, SimpleParticleType type) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, SeasonsGreetings.id(id), type);
    }

    public static void registerParticles() {
    }
}
