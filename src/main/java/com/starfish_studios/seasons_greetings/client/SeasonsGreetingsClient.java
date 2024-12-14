package com.starfish_studios.seasons_greetings.client;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.client.gui.screens.GiftBoxScreen;
import com.starfish_studios.seasons_greetings.client.particles.PoppingBubbleParticle;
import com.starfish_studios.seasons_greetings.client.renderer.GingerbreadManRenderer;
import com.starfish_studios.seasons_greetings.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib.util.Color;

import java.util.Objects;

import static com.starfish_studios.seasons_greetings.SeasonsGreetings.getColor;

@Environment(EnvType.CLIENT)
public class SeasonsGreetingsClient  implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        addResourcePacks();

        registerRenderers();
        registerScreens();
        registerParticles();
        registerEntityModelLayers();

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                        tintIndex > 0 ? -1 : DyedItemColor.getOrDefault(stack, getColor(Color.WHITE.argbInt())),
                SGItems.CHRISTMAS_HAT
        );

        registerAllGiftBoxProperties();
    }


    public static void addResourcePacks() {
        ModContainer modContainer = FabricLoader.getInstance().getModContainer(SeasonsGreetings.MOD_ID).orElseThrow(() -> new IllegalStateException("Season's Greetings' ModContainer couldn't be found!"));
        ResourceManagerHelper.registerBuiltinResourcePack(SeasonsGreetings.id("snowier_snow"), modContainer, Component.literal("§b§o❆ §9§oSnowier Snow §b§o❆"), ResourcePackActivationType.DEFAULT_ENABLED);
    }


    private static void registerGiftBoxProperties(Item item) {
        for (int i = 0; i < 16; i++) {
            ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(SeasonsGreetings.MOD_ID, "bow_color"), (stack, world, entity, num) -> {
                if (stack.has(DataComponents.BASE_COLOR)) {
                    return (float) Objects.requireNonNull(stack.get(DataComponents.BASE_COLOR)).getId() / 15;
                }
                return 0;
            });
        }
    }

    private static void registerAllGiftBoxProperties() {
        registerGiftBoxProperties(SGItems.WHITE_GIFT_BOX);
        registerGiftBoxProperties(SGItems.LIGHT_GRAY_GIFT_BOX);
        registerGiftBoxProperties(SGItems.GRAY_GIFT_BOX);
        registerGiftBoxProperties(SGItems.BLACK_GIFT_BOX);
        registerGiftBoxProperties(SGItems.BROWN_GIFT_BOX);
        registerGiftBoxProperties(SGItems.RED_GIFT_BOX);
        registerGiftBoxProperties(SGItems.ORANGE_GIFT_BOX);
        registerGiftBoxProperties(SGItems.YELLOW_GIFT_BOX);
        registerGiftBoxProperties(SGItems.LIME_GIFT_BOX);
        registerGiftBoxProperties(SGItems.GREEN_GIFT_BOX);
        registerGiftBoxProperties(SGItems.CYAN_GIFT_BOX);
        registerGiftBoxProperties(SGItems.LIGHT_BLUE_GIFT_BOX);
        registerGiftBoxProperties(SGItems.BLUE_GIFT_BOX);
        registerGiftBoxProperties(SGItems.PURPLE_GIFT_BOX);
        registerGiftBoxProperties(SGItems.MAGENTA_GIFT_BOX);
        registerGiftBoxProperties(SGItems.PINK_GIFT_BOX);
    }

    private static void registerEntityModelLayers() {
        EntityRendererRegistry.register(SGEntityType.GINGERBREAD_MAN, GingerbreadManRenderer::new);
    }

    public static void registerScreens() {
        MenuScreens.register(SGMenus.GIFT_BOX, GiftBoxScreen::new);
    }

    private static void registerParticles() {
        ParticleFactoryRegistry.getInstance().register(SGParticles.COCOA_BUBBLE, PoppingBubbleParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(SGParticles.MILK_BUBBLE, PoppingBubbleParticle.Provider::new);
    }

    @SuppressWarnings("all")
    public static void registerRenderers() {
    BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
            Blocks.SNOW,
            SGBlocks.ICICLE,

            SGBlocks.WHITE_LIGHTS,
            SGBlocks.RED_LIGHTS,
            SGBlocks.ORANGE_LIGHTS,
            SGBlocks.YELLOW_LIGHTS,
            SGBlocks.GREEN_LIGHTS,
            SGBlocks.BLUE_LIGHTS,
            SGBlocks.PURPLE_LIGHTS,
            SGBlocks.MULTICOLOR_LIGHTS,

            SGBlocks.WREATH,
            SGBlocks.GINGERBREAD_DOOR,
            SGBlocks.ICING,

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
