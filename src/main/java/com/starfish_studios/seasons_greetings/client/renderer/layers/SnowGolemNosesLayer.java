package com.starfish_studios.seasons_greetings.client.renderer.layers;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class SnowGolemNosesLayer extends RenderLayer<SnowGolem, SnowGolemModel<SnowGolem>> {
    public static final HashMap<Item, ResourceLocation> TEXTURE_LOCATION = Util.make(new HashMap<>(), (hashMap) -> {
        hashMap.put(Items.CARROT, SeasonsGreetings.id("textures/entity/snow_golem/nose/carrot.png"));
        hashMap.put(Items.POTATO, SeasonsGreetings.id("textures/entity/snow_golem/nose/potato.png"));
        hashMap.put(Items.BEETROOT, SeasonsGreetings.id("textures/entity/snow_golem/nose/beetroot.png"));
        hashMap.put(Items.WHEAT, SeasonsGreetings.id("textures/entity/snow_golem/nose/wheat.png"));
    });

//    if (this.isAlive() && this.random.nextInt(1000) < this.ambientSoundTime++) {
    // Explain how this works in real words.


    public SnowGolemNosesLayer(RenderLayerParent<SnowGolem, SnowGolemModel<SnowGolem>> renderLayerParent) {
        super(renderLayerParent);
    }

    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, SnowGolem snowGolem, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = snowGolem.getItemBySlot(EquipmentSlot.HEAD);
        if (TEXTURE_LOCATION.containsKey(itemStack.getItem())) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(TEXTURE_LOCATION.get(itemStack.getItem())));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        }
    }
}
