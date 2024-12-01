package com.starfish_studios.seasons_greetings.client;

import com.starfish_studios.seasons_greetings.client.gui.screens.GiftBoxScreen;
import com.starfish_studios.seasons_greetings.registry.SGBlocks;
import com.starfish_studios.seasons_greetings.registry.SGMenus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class SeasonsGreetingsClient  implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderers();
        registerScreens();
    }

//    public static final ModelLayerLocation
//            SNOW_GOLEM_DECOR = new ModelLayerLocation(SeasonsGreetings.id("snow_golem_decor"), mainString);


    public static void registerScreens() {
        MenuScreens.register(SGMenus.GIFT_BOX, GiftBoxScreen::new);
    }

    @SuppressWarnings("all")
    public static void registerRenderers() {
    BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
            SGBlocks.WHITE_GIFT_BOX,
            SGBlocks.LIGHT_GRAY_GIFT_BOX,
            SGBlocks.GRAY_GIFT_BOX,
            SGBlocks.BLACK_GIFT_BOX,
            SGBlocks.BROWN_GIFT_BOX,
            SGBlocks.RED_GIFT_BOX,
            SGBlocks.ORANGE_GIFT_BOX,
            SGBlocks.YELLOW_GIFT_BOX,
            SGBlocks.LIME_GIFT_BOX,
            SGBlocks.GREEN_GIFT_BOX,
            SGBlocks.CYAN_GIFT_BOX,
            SGBlocks.LIGHT_BLUE_GIFT_BOX,
            SGBlocks.BLUE_GIFT_BOX,
            SGBlocks.PURPLE_GIFT_BOX,
            SGBlocks.MAGENTA_GIFT_BOX,
            SGBlocks.PINK_GIFT_BOX
        );

//        EntityModelLayerRegistry.registerModelLayer(SNOW_GOLEM_DECOR, () -> SnowGolemModel.createBodyLayer());
    }
}
