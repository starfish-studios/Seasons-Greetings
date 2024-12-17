package com.starfish_studios.seasons_greetings.client;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.client.gui.screens.GiftBoxScreen;
import com.starfish_studios.seasons_greetings.client.particles.PoppingBubbleParticle;
import com.starfish_studios.seasons_greetings.client.renderer.GingerbreadManRenderer;
import com.starfish_studios.seasons_greetings.registry.SGBlocks;
import com.starfish_studios.seasons_greetings.registry.SGEntityType;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import com.starfish_studios.seasons_greetings.registry.SGMenus;
import com.starfish_studios.seasons_greetings.registry.SGParticles;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import software.bernie.geckolib.util.Color;

import java.util.Objects;

import static com.starfish_studios.seasons_greetings.SeasonsGreetings.getColor;

public class SeasonsGreetingsClient {

    public static void init(IEventBus eventBus) {
        eventBus.addListener(SeasonsGreetingsClient::onClientSetup);
        eventBus.addListener(SeasonsGreetingsClient::registerItemColors);
        eventBus.addListener(SeasonsGreetingsClient::registerScreens);
        eventBus.addListener(SeasonsGreetingsClient::registerEntityModelLayers);
        eventBus.addListener(SeasonsGreetingsClient::registerParticles);
        eventBus.addListener(SeasonsGreetingsClient::addResourcePacks);
    }

    private static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(SeasonsGreetingsClient::registerAllGiftBoxProperties);
        registerRenderers();
    }

    private static void registerItemColors(final RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) ->
                        tintIndex > 0 ? -1 : DyedItemColor.getOrDefault(stack, getColor(Color.WHITE.argbInt())),
                SGItems.CHRISTMAS_HAT);
    }


    private static void addResourcePacks(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            event.addPackFinders(SeasonsGreetings.id("snowier_snow"), PackType.CLIENT_RESOURCES,
                    Component.literal("§b§o❆ §9§oSnowier Snow §b§o❆"), PackSource.DEFAULT, false, Pack.Position.TOP);
        }
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
        registerGiftBoxProperties(SGItems.WHITE_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.LIGHT_GRAY_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.GRAY_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.BLACK_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.BROWN_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.RED_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.ORANGE_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.YELLOW_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.LIME_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.GREEN_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.CYAN_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.LIGHT_BLUE_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.BLUE_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.PURPLE_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.MAGENTA_GIFT_BOX.asItem());
        registerGiftBoxProperties(SGItems.PINK_GIFT_BOX.asItem());
    }

    private static void registerEntityModelLayers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SGEntityType.GINGERBREAD_MAN.get(), GingerbreadManRenderer::new);
    }

    private static void registerScreens(final RegisterMenuScreensEvent event) {
        event.register(SGMenus.GIFT_BOX.get(), GiftBoxScreen::new);
    }

    private static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SGParticles.COCOA_BUBBLE.get(), PoppingBubbleParticle.Provider::new);
        event.registerSpriteSet(SGParticles.MILK_BUBBLE.get(), PoppingBubbleParticle.Provider::new);
        event.registerSpriteSet(SGParticles.EGGNOG_BUBBLE.get(), PoppingBubbleParticle.Provider::new);
    }

    @SuppressWarnings("all")
    private static void registerRenderers() {
        ItemBlockRenderTypes.setRenderLayer(Blocks.SNOW, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.ICICLE.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(SGBlocks.WHITE_LIGHTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.RED_LIGHTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.ORANGE_LIGHTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.YELLOW_LIGHTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.GREEN_LIGHTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.BLUE_LIGHTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.PURPLE_LIGHTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.MULTICOLOR_LIGHTS.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(SGBlocks.WREATH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.GINGERBREAD_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.ICING.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(SGBlocks.WHITE_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.LIGHT_GRAY_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.GRAY_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.BLACK_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.BROWN_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.RED_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.ORANGE_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.YELLOW_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.LIME_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.GREEN_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.CYAN_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.LIGHT_BLUE_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.BLUE_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.PURPLE_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.MAGENTA_GIFT_BOX.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SGBlocks.PINK_GIFT_BOX.get(), RenderType.cutout());


//        EntityModelLayerRegistry.registerModelLayer(SNOW_GOLEM_DECOR, () -> SnowGolemModel.createBodyLayer());
    }
}
