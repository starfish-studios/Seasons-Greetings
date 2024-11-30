package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class SGEntityType {
//    public static final EntityType<Hex> HEX = register(
//            "hex",
//            FabricEntityTypeBuilder.createMob()
//                    .entityFactory(Hex::new)
//                    .defaultAttributes(Hex::createAttributes)
//                    .spawnGroup(MobCategory.CREATURE)
//                    .dimensions(EntityDimensions.scalable(0.5F, 0.5F))
//                    .trackRangeChunks(10)
//    );
//
//    public static final EntityType<ThrownLightningCharge> THROWN_LIGHTNING_CHARGE = register(
//            "thrown_lightning_charge",
//            FabricEntityTypeBuilder.<ThrownLightningCharge>create()
//                    .entityFactory(ThrownLightningCharge::new)
//                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
//                    .trackRangeBlocks(4)
//                    .trackedUpdateRate(10)
//    );


    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, SeasonsGreetings.id(id), entityType.build());
    }
}
