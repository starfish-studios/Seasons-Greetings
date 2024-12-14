package com.starfish_studios.seasons_greetings.common.entity;

import com.starfish_studios.seasons_greetings.SGConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static net.minecraft.world.entity.LivingEntity.getSlotForHand;

public class CopyOwnerBreakGoal extends Goal {
    private static final Logger LOGGER = LogManager.getLogger("SeasonsGreetings");
    private static final Map<BlockPos, BlockProgressData> BLOCK_PROGRESS = new HashMap<>();

    private final GingerbreadMan gingerbreadMan;
    private final double speed;
    private BlockPos targetBlockPos = null;
    private int vantageFailCount = 0;
    private static final int MAX_VANTAGE_FAILS = 5;

    public CopyOwnerBreakGoal(GingerbreadMan gingerbreadMan, double speed) {
        this.gingerbreadMan = gingerbreadMan;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!gingerbreadMan.isTame() || !SGConfig.gingerbreadManCopyBreak) return false;
        if (!(gingerbreadMan.getOwner() instanceof ServerPlayer owner)) return false;

        BlockPos minedBlockPos = PlayerMiningTracker.getMinedBlock(owner.getUUID());
        if (minedBlockPos == null) return false;

        Level level = owner.level();
        BlockState state = level.getBlockState(minedBlockPos);

        if (gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND).isDamageableItem() && gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND).getDamageValue() == gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND).getMaxDamage()) {
            return false;
        }
        else if (!state.isAir() && isBlockBreakable(level, minedBlockPos) && hasCorrectTool(state) && isMostEfficientTool(state)) {
            if (targetBlockPos == null || !targetBlockPos.equals(minedBlockPos)) {
                this.targetBlockPos = minedBlockPos;
                ensureBlockProgressData(minedBlockPos);
                gingerbreadMan.getNavigation().moveTo(targetBlockPos.getX() + 0.5, targetBlockPos.getY(), targetBlockPos.getZ() + 0.5, speed);
//                LOGGER.info("GingerbreadMan {} targeting block at {} that player is mining.", gingerbreadMan.getId(), targetBlockPos);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        Level level = gingerbreadMan.level();

        if (targetBlockPos == null) return false;
        if (level.getBlockState(targetBlockPos).isAir()) return false;

        if (gingerbreadMan.hurtTime > 0) {
//            LOGGER.info("GingerbreadMan {} hurt, abandoning block {}", gingerbreadMan.getId(), targetBlockPos);
            stop();
            return false;
        }

        if (gingerbreadMan.getOwner() instanceof ServerPlayer owner) {
            BlockPos minedBlockPos = PlayerMiningTracker.getMinedBlock(owner.getUUID());

            if (minedBlockPos != null && !minedBlockPos.equals(targetBlockPos)) {
//                LOGGER.info("Player started mining a new block, GingerbreadMan {} abandoning {}", gingerbreadMan.getId(), targetBlockPos);
                stop();
                return false;
            }
        }
        return true;
    }

    @Override
    public void tick() {
        if (targetBlockPos == null) {
            stop();
            return;
        }

        Level level = gingerbreadMan.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            stop();
            return;
        }

        double maxReach = gingerbreadMan.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
        double maxReachSq = maxReach * maxReach;
        Vec3 blockCenter = Vec3.atCenterOf(targetBlockPos);
        double distSq = gingerbreadMan.distanceToSqr(blockCenter);

        if (distSq > maxReachSq) {
            boolean pathFound = gingerbreadMan.getNavigation().moveTo(blockCenter.x, blockCenter.y, blockCenter.z, speed);
            if (!pathFound) {
                vantageFailCount++;

                if (vantageFailCount >= MAX_VANTAGE_FAILS) {
//                    LOGGER.warn("GingerbreadMan {} couldn't reach block at {} after {} attempts. Teleporting to fallback position.",
//                            gingerbreadMan.getId(), targetBlockPos, vantageFailCount);
                    if (level.getBlockState(targetBlockPos.above()).isAir()) {
                        gingerbreadMan.teleportTo(blockCenter.x, blockCenter.y + 1, blockCenter.z);
                        serverLevel.playSound(null, gingerbreadMan.getX(), gingerbreadMan.getY(), gingerbreadMan.getZ(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        serverLevel.sendParticles(ParticleTypes.POOF, gingerbreadMan.getX(), gingerbreadMan.getY(), gingerbreadMan.getZ(), 10, 0.5, 0.5, 0.5, 0.0);
                        vantageFailCount = 0;
                    } else {
                        stop();
                    }
                }
            } else {
                vantageFailCount = 0;
            }
            return;
        }

        vantageFailCount = 0;

        final double LOOK_RADIUS = 0.5D;
        double offsetX = (level.random.nextDouble() - 0.5) * 2 * LOOK_RADIUS;
        double offsetZ = (level.random.nextDouble() - 0.5) * 2 * LOOK_RADIUS;
        Vec3 adjustedBlockCenter = blockCenter.add(offsetX, 0, offsetZ);

        Vec3 eyePosition = gingerbreadMan.getEyePosition();
        ClipContext context = new ClipContext(
                eyePosition,
                adjustedBlockCenter,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                gingerbreadMan
        );

        HitResult result = level.clip(context);

        boolean clearLineOfSight = false;
        if (result.getType() == HitResult.Type.BLOCK && result instanceof BlockHitResult bhr) {
            BlockPos hitPos = bhr.getBlockPos();
            if (hitPos.equals(targetBlockPos)) {
                clearLineOfSight = true;
            }
        }

        if (!clearLineOfSight) {
            gingerbreadMan.getLookControl().setLookAt(blockCenter.x, blockCenter.y, blockCenter.z);
            return;
        }

        gingerbreadMan.getLookControl().setLookAt(adjustedBlockCenter.x, adjustedBlockCenter.y, adjustedBlockCenter.z);

        BlockState state = level.getBlockState(targetBlockPos);
        if (state.isAir()) {
            stop();
            return;
        }

        BlockProgressData data = BLOCK_PROGRESS.get(targetBlockPos);
        if (data == null) {
            ensureBlockProgressData(targetBlockPos);
            data = BLOCK_PROGRESS.get(targetBlockPos);
        }

        data.addBreaker(gingerbreadMan.getId());
        float increment = getGingerbreadManIncrement(state, targetBlockPos);

        if (gingerbreadMan.getOwner() instanceof ServerPlayer owner) {
            BlockPos minedBlockPos = PlayerMiningTracker.getMinedBlock(owner.getUUID());
            if (minedBlockPos != null && minedBlockPos.equals(targetBlockPos)) {
                data.addBreaker(owner.getId());
                increment += getPlayerIncrement(owner, state, targetBlockPos);
            } else {
                data.removeBreaker(owner.getId());
            }
        }

        data.progress += increment;
        int stage = (int) (data.progress * 10.0F) - 1;
        stage = Math.min(Math.max(stage, 0), 9);

        int repId = data.getRepresentativeId();
        serverLevel.destroyBlockProgress(repId, targetBlockPos, stage);

        if (stage != data.lastStage) {
            data.lastStage = stage;
            if (stage < 9) {
                var soundType = state.getSoundType();
                serverLevel.playSound(
                        null,
                        targetBlockPos,
                        soundType.getHitSound(),
                        SoundSource.BLOCKS,
                        (soundType.volume + 1.0F) / 8.0F,
                        soundType.pitch * 0.5F
                );
            }
        }

        gingerbreadMan.swing(InteractionHand.MAIN_HAND);

        if (data.progress >= 1.0F) {
            dropBlockWithToolEnchantments(serverLevel, targetBlockPos, state);
//            LOGGER.info("Block at {} destroyed by combined effort!", targetBlockPos);

            serverLevel.levelEvent(2001, targetBlockPos, Block.getId(state));
            damageGingerbreadManTool();
            BLOCK_PROGRESS.remove(targetBlockPos);
            stop();
        }
    }

    @Override
    public void stop() {
        if (targetBlockPos != null && gingerbreadMan.level() instanceof ServerLevel serverLevel) {
            BlockProgressData data = BLOCK_PROGRESS.get(targetBlockPos);
            if (data != null) {
                data.removeBreaker(gingerbreadMan.getId());

                if (gingerbreadMan.getOwner() instanceof ServerPlayer owner) {
                    data.removeBreaker(owner.getId());
                }

                if (data.breakerIds.isEmpty()) {
                    serverLevel.destroyBlockProgress(gingerbreadMan.getId(), targetBlockPos, -1);
                    BLOCK_PROGRESS.remove(targetBlockPos);
                }
            }
        }
        targetBlockPos = null;
        gingerbreadMan.swinging = false;
    }

    private void damageGingerbreadManTool() {
        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!mainHandItem.isEmpty() && mainHandItem.getItem() instanceof TieredItem) {
            mainHandItem.hurtAndBreak(1, gingerbreadMan, getSlotForHand(InteractionHand.MAIN_HAND));
        }
    }

    private void ensureBlockProgressData(BlockPos pos) {
        BLOCK_PROGRESS.putIfAbsent(pos, new BlockProgressData());
    }

    private float getGingerbreadManIncrement(BlockState state, BlockPos pos) {
        float hardness = state.getDestroySpeed(gingerbreadMan.level(), pos);
        if (hardness <= 0) return 0.0F;
        float speed = getDigSpeedLikePlayer(state);
        if (isCorrectToolForBlock(state)) {
            return (speed / hardness) / 30F;
        } else {
            return (speed / hardness) / 50F;
        }
    }

    private float getPlayerIncrement(ServerPlayer player, BlockState state, BlockPos pos) {
        float hardness = state.getDestroySpeed(player.level(), pos);
        if (hardness <= 0) return 0.0F;
        float speed = getPlayerDigSpeed(player, state, pos);
        if (isCorrectToolForPlayerItem(player.getMainHandItem(), state)) {
            return (speed / hardness) / 30F;
        } else {
            return (speed / hardness) / 50F;
        }
    }

    private float getDigSpeedLikePlayer(BlockState state) {
        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);
        float destroySpeed = mainHandItem.getDestroySpeed(state);
        ServerLevel level = (ServerLevel) gingerbreadMan.level();

        boolean correctTool = isCorrectToolForBlock(state);
        if (correctTool && destroySpeed > 1.0F) {

            ItemEnchantments enchantments = mainHandItem.get(DataComponents.ENCHANTMENTS);
            if (!(enchantments == null)
                    && enchantments.getLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY)) > 0) {
                destroySpeed += (enchantments.getLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY)) * enchantments.getLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY)) + 1);
            }
        } else if (!correctTool) {
            destroySpeed = 0.2F;
        }

        return destroySpeed;
    }

    private float getPlayerDigSpeed(ServerPlayer player, BlockState state, BlockPos pos) {
        ItemStack mainHandItem = player.getMainHandItem();
        float destroySpeed = mainHandItem.getDestroySpeed(state);

        boolean correctTool = isCorrectToolForPlayerItem(mainHandItem, state);
        if (correctTool && destroySpeed > 1.0F) {

            ItemEnchantments enchantments = mainHandItem.get(DataComponents.ENCHANTMENTS);
            if (!(enchantments == null)
                    && enchantments.getLevel(player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY)) > 0) {
                destroySpeed += (enchantments.getLevel(player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY)) * enchantments.getLevel(player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY)) + 1);
            }
        } else if (!correctTool) {
            destroySpeed = 0.2F;
        }

        return destroySpeed;
    }

    private boolean isCorrectToolForPlayerItem(ItemStack stack, BlockState state) {
        if (!state.requiresCorrectToolForDrops()) return true;
        if (stack.getItem() instanceof TieredItem tieredItem) {
            return tieredItem.isCorrectToolForDrops(stack, state);
        }
        return false;
    }

    private boolean isBlockBreakable(Level level, BlockPos blockPos) {
        return level.getBlockState(blockPos).getDestroySpeed(level, blockPos) >= 0.0F;
    }

    private boolean isCorrectToolForBlock(BlockState blockState) {
        if (!blockState.requiresCorrectToolForDrops()) return true;
        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);
        if (mainHandItem.getItem() instanceof TieredItem tieredItem) {
            return tieredItem.isCorrectToolForDrops(mainHandItem, blockState);
        }
        return false;
    }

    private boolean isMostEfficientTool(BlockState blockState) {
        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!blockState.requiresCorrectToolForDrops() && mainHandItem.isEmpty()) {
            return true;
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
        return !blockState.requiresCorrectToolForDrops();
    }

    private boolean hasCorrectTool(BlockState blockState) {
        if (!blockState.requiresCorrectToolForDrops()) return true;
        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);
        if (mainHandItem.getItem() instanceof TieredItem tieredItem) {
            return tieredItem.isCorrectToolForDrops(mainHandItem, blockState);
        }
        return false;
    }

    private void dropBlockWithToolEnchantments(ServerLevel serverLevel, BlockPos pos, BlockState state) {
        ItemStack mainHandItem = gingerbreadMan.getItemBySlot(EquipmentSlot.MAINHAND);
        BlockEntity blockEntity = serverLevel.getBlockEntity(pos);

        LootParams.Builder builder = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.TOOL, mainHandItem)
                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity)
                .withParameter(LootContextParams.THIS_ENTITY, gingerbreadMan);

        List<ItemStack> drops = state.getDrops(builder);
        serverLevel.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);


        serverLevel.levelEvent(2001, pos, Block.getId(state));

        for (ItemStack drop : drops) {
            net.minecraft.world.level.block.Block.popResource(serverLevel, pos, drop);
        }
    }

    private static class BlockProgressData {
        float progress = 0.0F;
        final Set<Integer> breakerIds = new HashSet<>();
        int lastStage = -1;

        void addBreaker(int id) {
            breakerIds.add(id);
        }

        void removeBreaker(int id) {
            breakerIds.remove(id);
        }

        int getRepresentativeId() {
            return breakerIds.stream().min(Integer::compareTo).orElse(-1);
        }
    }
}
