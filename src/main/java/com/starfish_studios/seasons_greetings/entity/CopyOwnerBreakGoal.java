package com.starfish_studios.seasons_greetings.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class CopyOwnerBreakGoal extends Goal {
    private final GingerbreadMan gingerbreadMan;
    private final double speed;
    private BlockPos targetBlockPos = null;
    private static final Map<BlockPos, Float> breakingProgressMap = new HashMap<>();
    private static final int MAX_BREAKING_STAGE = 9;
    private static final int OWNER_BREAKING_TICKS = 3;
    private int confirmBreakingCooldown = 0;
    private BlockPos lastTargetBlockPos = null;
    private float destroyTime;

    public CopyOwnerBreakGoal(GingerbreadMan gingerbreadMan, double speed) {
        this.gingerbreadMan = gingerbreadMan;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!gingerbreadMan.isTame()) {
            return false;
        }

        if (gingerbreadMan.getOwner() instanceof ServerPlayer owner) {

            if (owner.getAttackAnim(0.0F) > 0.0F && owner.swinging) {
                HitResult hitResult = owner.pick(20.0D, 0.0F, false);
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos newBlockPos = ((BlockHitResult) hitResult).getBlockPos();
                    BlockState blockState = owner.level().getBlockState(newBlockPos);

                    if (!blockState.isAir() && isBreakableBlock(newBlockPos) && hasCorrectTool(blockState) && isMostEfficientTool(blockState)) {
                        if (lastTargetBlockPos == null || !lastTargetBlockPos.equals(newBlockPos)) {
                            lastTargetBlockPos = newBlockPos;
                            confirmBreakingCooldown = OWNER_BREAKING_TICKS;
                        } else if (confirmBreakingCooldown > 0) {
                            confirmBreakingCooldown--;
                        }

                        if (confirmBreakingCooldown == 0) {
                            if (targetBlockPos == null || !targetBlockPos.equals(newBlockPos)) {
                                this.targetBlockPos = newBlockPos;
                                breakingProgressMap.putIfAbsent(targetBlockPos, 0.0F);
                                destroyTime = calculateDestroyTime(blockState);
                                System.out.println("Targeting block at: " + targetBlockPos + " - Block: " + blockState.getBlock().getDescriptionId() + " - Destroy Time: " + destroyTime);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }


    @Override
    public void start() {
        if (targetBlockPos != null) {
            this.gingerbreadMan.getNavigation().moveTo(targetBlockPos.getX() + 0.5, targetBlockPos.getY(), targetBlockPos.getZ() + 0.5, this.speed);
        }
    }

    @Override
    public void tick() {
        if (targetBlockPos != null) {
            double distanceToBlock = this.gingerbreadMan.distanceToSqr(targetBlockPos.getX() + 0.5, targetBlockPos.getY() + 0.5, targetBlockPos.getZ() + 0.5);
            if (distanceToBlock > 4.0D) {
                this.gingerbreadMan.getNavigation().moveTo(targetBlockPos.getX() + 0.5, targetBlockPos.getY(), targetBlockPos.getZ() + 0.5, this.speed);
            } else {
                Vec3 targetCenter = Vec3.atCenterOf(targetBlockPos);
                this.gingerbreadMan.getLookControl().setLookAt(targetCenter.x, targetCenter.y, targetCenter.z);

                Level level = this.gingerbreadMan.level();
                BlockState blockState = level.getBlockState(targetBlockPos);

                if (blockState.isAir()) {
                    stop();
                    return;
                }

                boolean requiresTool = blockState.requiresCorrectToolForDrops();
                boolean correctTool = isCorrectToolForBlock(blockState);
                boolean efficientTool = isMostEfficientTool(blockState);

                if (!requiresTool || efficientTool) {
                    if (!level.isClientSide()) {
                        ServerLevel serverLevel = (ServerLevel) level;
                        float currentProgress = breakingProgressMap.getOrDefault(targetBlockPos, 0.0F);

                        double breakSpeed = requiresTool ? (correctTool ? gingerbreadMan.getAttributeValue(Attributes.BLOCK_BREAK_SPEED) : 1.0) : gingerbreadMan.getAttributeValue(Attributes.BLOCK_BREAK_SPEED);

                        float increment = (float) (breakSpeed / calculateDestroyTime(blockState));
                        currentProgress += increment;

                        if (currentProgress >= 1.0F) {
                            level.destroyBlock(targetBlockPos, true, gingerbreadMan);
                            breakingProgressMap.remove(targetBlockPos);
                            stop();
                            return;
                        }

                        breakingProgressMap.put(targetBlockPos, currentProgress);
                        int newBreakingStage = (int) (currentProgress * MAX_BREAKING_STAGE);

                        serverLevel.destroyBlockProgress(this.gingerbreadMan.getId(), targetBlockPos, newBreakingStage);

                        this.gingerbreadMan.swing(InteractionHand.MAIN_HAND);
                    }
                } else {
                    stop();
                }
            }
        }
    }


    public BlockPos getGoalTargetBlockPos() {
        return this.targetBlockPos;
    }

    @Override
    public void stop() {
        if (targetBlockPos != null && gingerbreadMan.level() instanceof ServerLevel level) {
            level.destroyBlockProgress(this.gingerbreadMan.getId(), targetBlockPos, -1);
            breakingProgressMap.remove(targetBlockPos);
        }
        targetBlockPos = null;
        confirmBreakingCooldown = 0;
        lastTargetBlockPos = null;

        gingerbreadMan.swinging = false;

        if (gingerbreadMan.level() instanceof ServerLevel level) {
            breakingProgressMap.entrySet().removeIf(entry -> {
                BlockPos pos = entry.getKey();
                float progress = entry.getValue();

                boolean isBeingMined = level.getEntitiesOfClass(GingerbreadMan.class, gingerbreadMan.getBoundingBox().inflate(2.0D)).stream()
                        .anyMatch(entity -> getGoalTargetBlockPos() != null && getGoalTargetBlockPos().equals(pos));
                return !isBeingMined && progress > 0.0F;
            });
        }
    }




    @Override
    public boolean canContinueToUse() {
        if (targetBlockPos == null) {
            return false;
        }

        Level level = gingerbreadMan.level();

        if (gingerbreadMan.getOwner() instanceof ServerPlayer owner) {
            if (owner.getAttackAnim(0.0F) > 0.0F && owner.swinging) {
                HitResult hitResult = owner.pick(20.0D, 0.0F, false);
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos newBlockPos = ((BlockHitResult) hitResult).getBlockPos();
                    BlockState newBlockState = owner.level().getBlockState(newBlockPos);

                    if (!newBlockState.isAir() && isBreakableBlock(newBlockPos) && hasCorrectTool(newBlockState) && isMostEfficientTool(newBlockState)) {
                        if (!newBlockPos.equals(targetBlockPos)) {
                            targetBlockPos = newBlockPos;
                            breakingProgressMap.putIfAbsent(targetBlockPos, 0.0F);
                            destroyTime = calculateDestroyTime(newBlockState);
//                            System.out.println("Switching to new target block at: " + targetBlockPos);
                        }
                    } else {
                        return !level.getBlockState(targetBlockPos).isAir();
                    }
                }
            }
        }
        return !level.getBlockState(targetBlockPos).isAir();
    }


    private boolean isBreakableBlock(BlockPos blockPos) {
        Level level = gingerbreadMan.level();
        return level.getBlockState(blockPos).getDestroySpeed(level, blockPos) >= 0.0F;
    }

    private float calculateDestroyTime(BlockState blockState) {
        float baseDestroyTime = blockState.getBlock().defaultDestroyTime();

        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);

        if (mainHandItem.getItem() instanceof TieredItem tieredItem) {
            float toolSpeed = tieredItem.getTier().getSpeed();

            if (toolSpeed > 0) {
                baseDestroyTime /= toolSpeed;
            }
        }
        return baseDestroyTime;
    }

    private boolean isCorrectToolForBlock(BlockState blockState) {
        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);

        if (blockState.requiresCorrectToolForDrops()) {
            if (mainHandItem.getItem() instanceof TieredItem tieredItem) {
                return tieredItem.isCorrectToolForDrops(mainHandItem, blockState);
            }
            return false;
        }
        return true;
    }

    private boolean isMostEfficientTool(BlockState blockState) {
        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);

        if (mainHandItem.isEmpty() || !(mainHandItem.getItem() instanceof TieredItem) && !blockState.requiresCorrectToolForDrops()) {
            return true;
        }

        if (mainHandItem.isEmpty() || !(mainHandItem.getItem() instanceof TieredItem)) {
            return false;
        }

        if (blockState.is(BlockTags.MINEABLE_WITH_AXE) && mainHandItem.is(ItemTags.AXES)) {
            return true;
        }
        if (blockState.is(BlockTags.MINEABLE_WITH_PICKAXE) && mainHandItem.is(ItemTags.PICKAXES)) {
            return true;
        }
        if (blockState.is(BlockTags.MINEABLE_WITH_SHOVEL) && mainHandItem.is(ItemTags.SHOVELS)) {
            return true;
        }
        if (blockState.is(BlockTags.MINEABLE_WITH_HOE) && mainHandItem.is(ItemTags.HOES)) {
            return true;
        }

        return false;
    }


    private boolean hasCorrectTool(BlockState blockState) {
        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);

        if (!blockState.requiresCorrectToolForDrops()) {
            return true;
        }

        if (mainHandItem.getItem() instanceof TieredItem tieredItem) {
            return tieredItem.isCorrectToolForDrops(mainHandItem, blockState);
        }

        return false;
    }
}
