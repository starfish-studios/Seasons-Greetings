package com.starfish_studios.seasons_greetings.entity;

import net.minecraft.core.BlockPos;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerMiningTracker {
    private static final Map<UUID, BlockPos> CURRENTLY_MINED_BLOCK = new ConcurrentHashMap<>();

    public static void setMinedBlock(UUID playerUUID, BlockPos pos) {
        if (pos == null) {
            CURRENTLY_MINED_BLOCK.remove(playerUUID);
        } else {
            CURRENTLY_MINED_BLOCK.put(playerUUID, pos);
        }
    }

    public static BlockPos getMinedBlock(UUID playerUUID) {
        return CURRENTLY_MINED_BLOCK.get(playerUUID);
    }
}
