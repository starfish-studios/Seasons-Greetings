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
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;

@Environment(EnvType.CLIENT)
public class SeasonsGreetingsClient  implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderers();
        registerScreens();
        registerParticles();
        registerEntityModelLayers();

        ItemProperties.register(SGItems.RED_GIFT_BOX, SeasonsGreetings.id("bow_color"), (stack, world, entity, num) -> {
            CustomData customData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
            String variantString = "bow";
            if (customData != null) {
            CompoundTag tag = customData.copyTag();
            return tag.contains(variantString) ? switch (tag.getString(variantString)) {
                case "white" -> 0.0625F;
                case "orange" -> 0.125F;
                case "magenta" -> 0.1875F;
                case "light_blue" -> 0.25F;
                case "yellow" -> 0.3125F;
                case "lime" -> 0.375F;
                case "pink" -> 0.4375F;
                case "gray" -> 0.5F;
                case "light_gray" -> 0.5625F;
                case "cyan" -> 0.625F;
                case "purple" -> 0.6875F;
                case "blue" -> 0.75F;
                case "brown" -> 0.8125F;
                case "green" -> 0.875F;
                case "red" -> 0.9375F;
                case "black" -> 1.0F;
                default -> 0.0F;
            } : 0.0F;
        } else {
            return 0.0F;
        }
    });
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

            SGBlocks.STRING_LIGHTS,
            SGBlocks.WREATH,
            SGBlocks.GINGERBREAD_DOOR,

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
