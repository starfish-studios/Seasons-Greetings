package com.starfish_studios.seasons_greetings.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.starfish_studios.seasons_greetings.client.model.GingerbreadManModel;
import com.starfish_studios.seasons_greetings.entity.GingerbreadMan;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

@Environment(EnvType.CLIENT)
public class GingerbreadManRenderer extends GeoEntityRenderer<GingerbreadMan> {

    public GingerbreadManRenderer(EntityRendererProvider.Context context) {

        super(context, new GingerbreadManModel());
        this.shadowRadius = 0.0F;

        this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, GingerbreadMan animatable) {
                ItemStack mainHandItem = animatable.getMainHandItem();
                if (bone.getName().equals("right_held_item")) return mainHandItem;
                return null;
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, GingerbreadMan animatable) {
                bone.getName();
                return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, GingerbreadMan animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {

                poseStack.mulPose(Axis.XP.rotationDegrees(-75));
                poseStack.mulPose(Axis.YP.rotationDegrees(0));
                poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                poseStack.scale(0.8F, 0.8F, 0.8F);
                poseStack.translate(0.0D, -0.1D, -0.125D);

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight,
                        packedOverlay);
            }
        });
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