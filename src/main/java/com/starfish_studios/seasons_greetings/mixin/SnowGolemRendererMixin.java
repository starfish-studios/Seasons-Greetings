package com.starfish_studios.seasons_greetings.mixin;

import com.starfish_studios.seasons_greetings.client.SeasonsGreetingsClient;
import com.starfish_studios.seasons_greetings.client.render.layers.SnowGolemDecorLayer;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.SnowGolem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowGolemRenderer.class)
public class SnowGolemRendererMixin extends MobRenderer<SnowGolem, SnowGolemModel<SnowGolem>> {
    public SnowGolemRendererMixin(EntityRendererProvider.Context context, SnowGolemModel<SnowGolem> entityModel, float f) {
        super(context, entityModel, f);
        this.addLayer(new SnowGolemDecorLayer(this, context.getModelSet()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SnowGolem entity) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/snow_golem.png");
    }
}
