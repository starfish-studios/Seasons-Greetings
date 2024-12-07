package com.starfish_studios.seasons_greetings.client.renderer;

import com.starfish_studios.seasons_greetings.client.model.ChristmasHatModel;
import com.starfish_studios.seasons_greetings.client.renderer.layers.ChristmasHatOverlay;
import com.starfish_studios.seasons_greetings.item.ChristmasHatItem;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.specialty.DyeableGeoArmorRenderer;
import software.bernie.geckolib.util.Color;

public class ChristmasHatRenderer extends DyeableGeoArmorRenderer<ChristmasHatItem> {

    public ChristmasHatRenderer() {
        super(new ChristmasHatModel());
        this.addRenderLayer(new ChristmasHatOverlay(this));
    }

    @Override
    protected boolean isBoneDyeable(GeoBone geoBone) {
        return true;
    }

    @Override
    protected @NotNull Color getColorForBone(GeoBone bone) {
        return Color.WHITE;
    }

    @Override
    public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {


    }
}
