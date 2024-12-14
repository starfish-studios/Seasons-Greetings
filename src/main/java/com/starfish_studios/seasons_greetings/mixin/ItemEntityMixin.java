package com.starfish_studios.seasons_greetings.mixin;

import com.starfish_studios.seasons_greetings.common.entity.GingerbreadMan;
import com.starfish_studios.seasons_greetings.registry.SGBlocks;
import com.starfish_studios.seasons_greetings.registry.SGEntityType;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import com.starfish_studios.seasons_greetings.registry.SGTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
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



//        if (itemStack.is(SGItems.GINGERBREAD_COOKIE) && level.getBlockState(blockPos.below()).is(Blocks.BEACON)) {
//            // TODO : Add a check to see if the Beacon is active.
//            BeaconBlockEntity beaconBlockEntity = (BeaconBlockEntity) level.getBlockEntity(blockPos.below());
//
//                if (level.getGameTime() % 100 == 0) {
//                    assert gingerbreadMan != null;
//                    gingerbreadMan.setPos(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
//                    shrinkItemStack(itemEntity, level, blockPos);
//                    level.playSound(null, blockPos, SoundEvents.ILLUSIONER_CAST_SPELL, itemEntity.getSoundSource(), 0.5F, 1.3F);
//                    for(int j = 0; j < 10; ++j) {
//                        double g = level.random.nextGaussian() * 0.2;
//                        double h = level.random.nextGaussian() * 0.1;
//                        double i = level.random.nextGaussian() * 0.2;
//
//                        if (!level.isClientSide) {
//                            ServerLevel serverLevel = (ServerLevel) level;
//                            serverLevel.sendParticles(ParticleTypes.POOF, gingerbreadMan.getX(), gingerbreadMan.getY(), gingerbreadMan.getZ(), 1, g, h, i, 0.0D);
//                        }
//                    }
//                    gingerbreadMan.setCantCatchMe(true);
//                    level.addFreshEntity(gingerbreadMan);
//            }
//
//            if (level.getGameTime() % 100 == 0) {
//                assert gingerbreadMan != null;
//                gingerbreadMan.setPos(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
//                shrinkItemStack(itemEntity, level, blockPos);
//                level.playSound(null, blockPos, SoundEvents.ILLUSIONER_CAST_SPELL, itemEntity.getSoundSource(), 0.5F, 1.3F);
//                for(int j = 0; j < 10; ++j) {
//                    double g = level.random.nextGaussian() * 0.2;
//                    double h = level.random.nextGaussian() * 0.1;
//                    double i = level.random.nextGaussian() * 0.2;
//
//                    if (!level.isClientSide) {
//                        ServerLevel serverLevel = (ServerLevel) level;
//                        serverLevel.sendParticles(ParticleTypes.POOF, gingerbreadMan.getX(), gingerbreadMan.getY(), gingerbreadMan.getZ(), 1, g, h, i, 0.0D);
//                    }
//                }
//                gingerbreadMan.setCantCatchMe(true);
//                level.addFreshEntity(gingerbreadMan);
//
//            }
//        }

        if (level.getBlockState(blockPos).is(SGBlocks.MILK_CAULDRON) && level.getBlockState(blockPos).getValue(LayeredCauldronBlock.LEVEL) == 3) {

            if (level.getBlockState(blockPos.below()).is(SGTags.SGBlockTags.HEAT_SOURCES)) {
                if (itemStack.is(Items.EGG)) {
                    level.setBlockAndUpdate(blockPos, SGBlocks.EGGNOG_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
                    level.playSound(null, blockPos, SoundEvents.EGG_THROW, itemEntity.getSoundSource(), 0.5F, 1.3F);
                    shrinkItemStack(itemEntity, level, blockPos);
                }

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
