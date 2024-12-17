package com.starfish_studios.seasons_greetings.client.model;

import com.starfish_studios.seasons_greetings.SeasonsGreetings;
import com.starfish_studios.seasons_greetings.common.entity.GingerbreadMan;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class GingerbreadManModel extends DefaultedEntityGeoModel<GingerbreadMan> {

    public GingerbreadManModel() {
        super(SeasonsGreetings.id("gingerbread_man"), true);
    }

    @Override
    public ResourceLocation getModelResource(GingerbreadMan gingerbreadMan) {
        return SeasonsGreetings.id("geo/entity/gingerbread_man.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GingerbreadMan gingerbreadMan) {
        return SeasonsGreetings.id("textures/entity/gingerbread_man.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GingerbreadMan gingerbreadMan) {
        return SeasonsGreetings.id("animations/gingerbread_man.animation.json");
    }

    @Override
    public void setCustomAnimations(GingerbreadMan gingerbreadMan, long instanceId, AnimationState<GingerbreadMan> animationState) {

        super.setCustomAnimations(gingerbreadMan, instanceId, animationState);
        if (animationState == null) return;

        GeoBone root = this.getAnimationProcessor().getBone("rootRot");

    }
}