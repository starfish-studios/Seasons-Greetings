package com.starfish_studios.seasons_greetings.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;

public class SnowGolemHeadLayer extends RenderLayer<SnowGolem, SnowGolemModel<SnowGolem>> {
    private final float scaleX;
    private final float scaleY;
    private final float scaleZ;
    private final ItemInHandRenderer itemInHandRenderer;
    public SnowGolemHeadLayer(RenderLayerParent<SnowGolem, SnowGolemModel<SnowGolem>> renderLayerParent, EntityModelSet modelSet, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.scaleX = 1.0F;
        this.scaleY = 1.0F;
        this.scaleZ = 1.0F;
        this.itemInHandRenderer = itemInHandRenderer;
    }

    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, SnowGolem livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
        if (!itemStack.isEmpty()) {
            Item item = itemStack.getItem();
            poseStack.pushPose();
            poseStack.scale(this.scaleX, this.scaleY, this.scaleZ);

            this.getParentModel().getHead().translateAndRotate(poseStack);
            {
                {
                    if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSkullBlock) {
                        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                        poseStack.translate(0.0D, 0.5D, 0.0D);
                        this.itemInHandRenderer.renderItem(livingEntity, itemStack, ItemDisplayContext.HEAD, false, poseStack, multiBufferSource, i);
                    } else {
                        translateToHead(poseStack, livingEntity.isBaby());
                        this.itemInHandRenderer.renderItem(livingEntity, itemStack, ItemDisplayContext.HEAD, false, poseStack, multiBufferSource, i);
                    }
                }
            }

            poseStack.popPose();
        }
    }

    public static void translateToHead(PoseStack poseStack, boolean bl) {
        poseStack.translate(0.0F, -0.25F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(0.625F, -0.625F, -0.625F);
        if (bl) {
            poseStack.translate(0.0F, 0.1875F, 0.0F);
        }

    }
}
