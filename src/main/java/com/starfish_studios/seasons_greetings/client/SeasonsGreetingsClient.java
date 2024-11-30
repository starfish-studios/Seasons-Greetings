package com.starfish_studios.seasons_greetings.client;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.client.render.layers.SnowGolemDecorLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.component.DyedItemColor;

@Environment(EnvType.CLIENT)
public class SeasonsGreetingsClient  implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderers();
    }

    private static final String mainString = "main";

//    public static final ModelLayerLocation
//            SNOW_GOLEM_DECOR = new ModelLayerLocation(SeasonsGreetings.id("snow_golem_decor"), mainString);


    @SuppressWarnings("all")
    public static void registerRenderers() {
//        EntityModelLayerRegistry.registerModelLayer(SNOW_GOLEM_DECOR, () -> SnowGolemModel.createBodyLayer());
    }
}
