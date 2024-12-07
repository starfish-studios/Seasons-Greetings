package com.starfish_studios.seasons_greetings.mixin;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.SnowGolem;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowGolemModel.class)
public abstract class SnowGolemModelMixin<T extends Entity> extends HierarchicalModel<T> {
    @Unique
    private final ModelPart upperBody;
    @Unique
    private final ModelPart head;
    @Unique
    private final ModelPart leftArm;
    @Unique
    private final ModelPart rightArm;

    public SnowGolemModelMixin(ModelPart modelPart) {
        this.head = modelPart.getChild("head");
        this.leftArm = modelPart.getChild("left_arm");
        this.rightArm = modelPart.getChild("right_arm");
        this.upperBody = modelPart.getChild("upper_body");
    }


    @Inject(method = "createBodyLayer", at = @At(value = "RETURN"), cancellable = true)
    private static void sg$createBodyLayer(CallbackInfoReturnable<LayerDefinition> cir) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        CubeDeformation cubeDeformation = new CubeDeformation(-0.5F);
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation), PartPose.offset(0.0F, 4.0F, 0.0F));
        CubeListBuilder cubeListBuilder = CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 12.0F, 2.0F, 2.0F, cubeDeformation);
        partDefinition.addOrReplaceChild("left_arm", cubeListBuilder, PartPose.offsetAndRotation(5.0F, 6.0F, 1.0F, 0.0F, 0.0F, 1.0F));
        partDefinition.addOrReplaceChild("right_arm", cubeListBuilder, PartPose.offsetAndRotation(-5.0F, 6.0F, -1.0F, 0.0F, 3.1415927F, -1.0F));
        partDefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, cubeDeformation), PartPose.offset(0.0F, 13.0F, 0.0F));
        partDefinition.addOrReplaceChild("lower_body", CubeListBuilder.create().texOffs(0, 36).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, cubeDeformation), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = partDefinition.getChild("head");

        head.addOrReplaceChild("carrot_nose", CubeListBuilder.create().texOffs(33, 16).addBox(0.0F, -24.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 19.0F, -7.5F));
        head.addOrReplaceChild("potato_nose", CubeListBuilder.create().texOffs(44, 12).addBox(-2.0F, -27.0F, 1.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 20.75F, -7.0F));

        head.addOrReplaceChild("wheat_hair", CubeListBuilder.create().texOffs(41, 31).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        head.addOrReplaceChild("wheat_hair_2", CubeListBuilder.create().texOffs(41, 31).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        head.addOrReplaceChild("beetroot_nose", CubeListBuilder.create().texOffs(41, 22).addBox(-2.0F, -26.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 19.75F, -6.25F));


        cir.setReturnValue(LayerDefinition.create(meshDefinition, 64, 64));
    }

    @Inject(method = "setupAnim", at = @At(value = "HEAD"))
    private void sg$setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
    }
}
