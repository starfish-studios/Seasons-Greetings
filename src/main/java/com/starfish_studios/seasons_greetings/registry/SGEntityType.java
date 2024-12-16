package com.starfish_studios.seasons_greetings.registry;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.entity.GingerbreadMan;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SGEntityType {

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, SeasonsGreetings.MOD_ID);

    // Gingerbread Man
    public static final DeferredHolder<EntityType<?>, EntityType<GingerbreadMan>> GINGERBREAD_MAN = ENTITIES.register("gingerbread_man",
            () -> EntityType.Builder.<GingerbreadMan>of(GingerbreadMan::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.8F)
                    .eyeHeight(0.45F)
                    .clientTrackingRange(10)
                    .build("gingerbread_man")
    );

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(GINGERBREAD_MAN.get(), GingerbreadMan.createAttributes().build());
    }

    public static void registerEntities(IEventBus eventBus) {
        ENTITIES.register(eventBus);

        eventBus.addListener(SGEntityType::registerEntityAttributes);
    }
}
