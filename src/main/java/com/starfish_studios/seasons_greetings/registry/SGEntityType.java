package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.entity.GingerbreadMan;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class SGEntityType {

    // Gingerbread Man
    public static final EntityType<GingerbreadMan> GINGERBREAD_MAN = register(
            "gingerbread_man",
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(GingerbreadMan::new)
                    .defaultAttributes(GingerbreadMan::createAttributes)
                    .spawnGroup(MobCategory.CREATURE)
                    .dimensions(EntityDimensions.scalable(0.6F, 0.8F))
                    .trackRangeChunks(10)
    );


    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, SeasonsGreetings.id(id), entityType.build());
    }
}
