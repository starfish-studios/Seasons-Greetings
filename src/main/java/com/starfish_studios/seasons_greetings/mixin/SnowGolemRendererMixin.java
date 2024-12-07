package com.starfish_studios.seasons_greetings.mixin;

import com.starfish_studios.seasons_greetings.client.renderer.layers.SnowGolemDecorLayer;
import com.starfish_studios.seasons_greetings.client.renderer.layers.SnowGolemNosesLayer;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.world.entity.animal.SnowGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolemRenderer.class)
public abstract class SnowGolemRendererMixin extends MobRenderer<SnowGolem, SnowGolemModel<SnowGolem>> {
    public SnowGolemRendererMixin(EntityRendererProvider.Context context, SnowGolemModel<SnowGolem> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addDecorLayer(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(new SnowGolemNosesLayer(this));
        this.addLayer(new SnowGolemDecorLayer(this));
    }

}



