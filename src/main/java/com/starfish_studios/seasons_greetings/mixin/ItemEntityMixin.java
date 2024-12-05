package com.starfish_studios.seasons_greetings.mixin;

import com.starfish_studios.seasons_greetings.entity.GingerbreadMan;
import com.starfish_studios.seasons_greetings.registry.SGBlocks;
import com.starfish_studios.seasons_greetings.registry.SGEntityType;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import com.starfish_studios.seasons_greetings.registry.SGTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Unique
    private void shrinkItemStack(ItemEntity itemEntity, Level level, BlockPos blockPos) {
        int stackSize = itemEntity.getItem().getCount();
        if (stackSize > 1) {
            itemEntity.getItem().setCount(stackSize - 1);
        } else {
            itemEntity.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void sg$isInBlock(CallbackInfo ci) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ItemStack itemStack = itemEntity.getItem();
        BlockPos blockPos = itemEntity.blockPosition();
        Level level = itemEntity.level();

        GingerbreadMan gingerbreadMan = SGEntityType.GINGERBREAD_MAN.create(level);

        if (itemStack.is(SGItems.GINGERBREAD_COOKIE) && level.getBlockState(blockPos.below()).is(Blocks.BEACON)) {

            if (level.getGameTime() % 100 == 0) {
                assert gingerbreadMan != null;
                gingerbreadMan.moveTo(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
                shrinkItemStack(itemEntity, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.ILLUSIONER_CAST_SPELL, itemEntity.getSoundSource(), 0.5F, 1.3F);
                for(int j = 0; j < 10; ++j) {
                    double g = level.random.nextGaussian() * 0.2;
                    double h = level.random.nextGaussian() * 0.1;
                    double i = level.random.nextGaussian() * 0.2;

                    if (!level.isClientSide) {
                        ServerLevel serverLevel = (ServerLevel) level;
                        serverLevel.sendParticles(ParticleTypes.POOF, gingerbreadMan.getX(), gingerbreadMan.getY(), gingerbreadMan.getZ(), 1, g, h, i, 0.0D);
                    }
                }
                level.addFreshEntity(gingerbreadMan);

            }
        }

        if (level.getBlockState(blockPos).is(SGBlocks.MILK_CAULDRON) && level.getBlockState(blockPos).getValue(LayeredCauldronBlock.LEVEL) == 3) {

            if (level.getBlockState(blockPos.below()).is(Blocks.FIRE) || level.getBlockState(blockPos.below()).is(Blocks.LAVA)) {

                if (itemStack.is(SGItems.CHOCOLATE)) {
                    level.setBlockAndUpdate(blockPos, SGBlocks.HOT_COCOA_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
                    level.playSound(null, blockPos, SoundEvents.AXOLOTL_SPLASH, itemEntity.getSoundSource(), 0.5F, 1.3F);
                    shrinkItemStack(itemEntity, level, blockPos);
                }
            }

//            if (itemStack.is(SGTags.SGItemTags.ICE)){
//                level.setBlockAndUpdate(blockPos, Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
//                shrinkItemStack(itemEntity, level, blockPos);
//            }

        }
    }
}
