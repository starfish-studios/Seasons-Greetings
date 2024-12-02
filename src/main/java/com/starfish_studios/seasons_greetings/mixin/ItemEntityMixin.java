package com.starfish_studios.seasons_greetings.mixin;

import com.starfish_studios.seasons_greetings.registry.SGBlocks;
import com.starfish_studios.seasons_greetings.registry.SGItems;
import com.starfish_studios.seasons_greetings.registry.SGTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
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
        level.playSound(null, blockPos, SoundEvents.AXOLOTL_SPLASH, itemEntity.getSoundSource(), 0.5F, 1.3F);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void sg$isInCauldron(CallbackInfo ci) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ItemStack itemStack = itemEntity.getItem();
        BlockPos blockPos = itemEntity.blockPosition();
        Level level = itemEntity.level();
        if (level.getBlockState(blockPos).is(Blocks.WATER_CAULDRON) && level.getBlockState(blockPos).getValue(LayeredCauldronBlock.LEVEL) == 3) {

            if (level.getBlockState(blockPos.below()).is(Blocks.FIRE) || level.getBlockState(blockPos.below()).is(Blocks.LAVA)) {

                if (itemStack.is(SGItems.CHOCOLATE)) {
                    level.setBlockAndUpdate(blockPos, SGBlocks.HOT_COCOA_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
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
