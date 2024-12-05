package com.starfish_studios.seasons_greetings.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.starfish_studios.seasons_greetings.client.model.GingerbreadManModel;
import com.starfish_studios.seasons_greetings.entity.GingerbreadMan;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class GingerbreadManRenderer extends GeoEntityRenderer<GingerbreadMan> {

    public GingerbreadManRenderer(EntityRendererProvider.Context context) {

        super(context, new GingerbreadManModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    public float getMotionAnimThreshold(GingerbreadMan gingerbreadMan) {
        return 0.001F;
    }

    @Override
    public void render(GingerbreadMan gingerbreadMan, float yaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {

        float adultScale = 1.0F;
        float babyScale = 0.6F;

        if (gingerbreadMan.isBaby()) poseStack.scale(babyScale, babyScale, babyScale);
        else poseStack.scale(adultScale, adultScale, adultScale);

        super.render(gingerbreadMan, yaw, partialTick, poseStack, bufferSource, packedLight);
    }
}