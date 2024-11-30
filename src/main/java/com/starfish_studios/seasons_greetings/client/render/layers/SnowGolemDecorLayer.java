package com.starfish_studios.seasons_greetings.client.render.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WoolCarpetBlock;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

@Environment(EnvType.CLIENT)
public class SnowGolemDecorLayer extends RenderLayer<SnowGolem, SnowGolemModel<SnowGolem>> {
    private static final EnumMap<DyeColor, @Nullable Object> TEXTURE_LOCATION = Util.make(Maps.newEnumMap(DyeColor.class), (enumMap) -> {
        enumMap.put(DyeColor.byId(DyeColor.RED.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/red.png"));
        enumMap.put(DyeColor.byId(DyeColor.ORANGE.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/orange.png"));
        enumMap.put(DyeColor.byId(DyeColor.YELLOW.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/yellow.png"));
        enumMap.put(DyeColor.byId(DyeColor.LIME.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/lime.png"));
        enumMap.put(DyeColor.byId(DyeColor.GREEN.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/green.png"));
        enumMap.put(DyeColor.byId(DyeColor.CYAN.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/cyan.png"));
        enumMap.put(DyeColor.byId(DyeColor.LIGHT_BLUE.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/light_blue.png"));
        enumMap.put(DyeColor.byId(DyeColor.BLUE.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/blue.png"));
        enumMap.put(DyeColor.byId(DyeColor.PURPLE.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/purple.png"));
        enumMap.put(DyeColor.byId(DyeColor.MAGENTA.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/magenta.png"));
        enumMap.put(DyeColor.byId(DyeColor.PINK.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/pink.png"));
        enumMap.put(DyeColor.byId(DyeColor.WHITE.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/white.png"));
        enumMap.put(DyeColor.byId(DyeColor.LIGHT_GRAY.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/light_gray.png"));
        enumMap.put(DyeColor.byId(DyeColor.GRAY.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/gray.png"));
        enumMap.put(DyeColor.byId(DyeColor.BLACK.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/black.png"));
        enumMap.put(DyeColor.byId(DyeColor.BROWN.getId()), SeasonsGreetings.id("textures/entity/snow_golem/decor/brown.png"));

    });

    public SnowGolemDecorLayer(RenderLayerParent<SnowGolem, SnowGolemModel<SnowGolem>> renderLayerParent) {
        super(renderLayerParent);
    }

    private static DyeColor getDyeColor(ItemStack itemStack) {
        Block block = Block.byItem(itemStack.getItem());
        return block instanceof WoolCarpetBlock ? ((WoolCarpetBlock)block).getColor() : null;
    }

    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, SnowGolem snowGolem, float f, float g, float h, float j, float k, float l) {
        DyeColor dyeColor = getDyeColor(snowGolem.getItemBySlot(EquipmentSlot.BODY));
        if (dyeColor != null) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout((ResourceLocation)TEXTURE_LOCATION.get(dyeColor)));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        }
    }
}
