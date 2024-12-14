package com.starfish_studios.seasons_greetings.mixin;

import com.starfish_studios.seasons_greetings.common.entity.PlayerMiningTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Inject(method = "handlePlayerAction", at = @At("HEAD"))
    private void onHandlePlayerAction(ServerboundPlayerActionPacket packet, CallbackInfo ci) {
        ServerGamePacketListenerImpl handler = (ServerGamePacketListenerImpl) (Object) this;
        ServerPlayer player = handler.player;
        ServerboundPlayerActionPacket.Action action = packet.getAction();
        BlockPos pos = packet.getPos();

        switch (action) {
            case START_DESTROY_BLOCK:
                PlayerMiningTracker.setMinedBlock(player.getUUID(), pos);
                break;
            case ABORT_DESTROY_BLOCK:
            case STOP_DESTROY_BLOCK:
                PlayerMiningTracker.setMinedBlock(player.getUUID(), null);
                break;
            default:
                break;
        }
    }
}
