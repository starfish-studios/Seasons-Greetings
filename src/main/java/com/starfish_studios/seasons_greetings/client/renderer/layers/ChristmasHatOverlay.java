package com.starfish_studios.seasons_greetings.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.item.ChristmasHatItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

public class ChristmasHatOverlay extends GeoRenderLayer<ChristmasHatItem> {
    private static final ResourceLocation TEXTURE = SeasonsGreetings.id("textures/models/armor/christmas_hat_overlay.png");

    public ChristmasHatOverlay(GeoRenderer<ChristmasHatItem> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, ChristmasHatItem animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType armorRenderType = RenderType.armorCutoutNoCull(TEXTURE);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, armorRenderType,
                bufferSource.getBuffer(armorRenderType), partialTick, packedLight, packedOverlay, Color.WHITE.argbInt());
    }
}
