package com.starfish_studios.seasons_greetings.client.model;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.item.ChristmasHatItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;

public class ChristmasHatModel extends GeoModel<ChristmasHatItem> {

    @Override
    public ResourceLocation getModelResource(ChristmasHatItem enchantmentRobeItem) {
        return SeasonsGreetings.id("geo/armor/christmas_hat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChristmasHatItem enchantmentRobeItem) {
        return SeasonsGreetings.id("textures/models/armor/christmas_hat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChristmasHatItem enchantmentRobeItem) {
        return SeasonsGreetings.id("animations/christmas_hat.animation.json");
    }

    @Override
    public void setCustomAnimations(ChristmasHatItem animatable, long instanceId, AnimationState<ChristmasHatItem> animationState) {

        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        GeoBone hatTip = this.getAnimationProcessor().getBone("poof");
        GeoBone hatBase = this.getAnimationProcessor().getBone("root");

        Entity entity = animationState.getData(DataTickets.ENTITY);

        if (entity instanceof Player playerEntity) {
            hatBase.setRotX(Mth.lerp(animationState.getPartialTick(), playerEntity.oBob, playerEntity.bob) * -0.25F);

            hatTip.setRotX((Mth.lerp(animationState.getPartialTick(), playerEntity.oBob, playerEntity.bob) * -2F)
                    + (float) (Mth.lerp(animationState.getPartialTick(), playerEntity.yCloakO, playerEntity.yCloak)
                    - Mth.lerp(animationState.getPartialTick(), playerEntity.yo, playerEntity.getY())) / -4);
        }
    }
}