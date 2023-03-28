package foundry.animamundi.grimoire;

import foundry.animamundi.AnimaMundi;
import foundry.animamundi.content.entity.hook.HookSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class AnimaMundiNetworking {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(AnimaMundi.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static int packetID = 0;

    public static void init() {
        CHANNEL.registerMessage(packetID++, HookSyncPacket.class, HookSyncPacket::encode, HookSyncPacket::new, HookSyncPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static <T> void sendToServer(T t) {
        CHANNEL.sendToServer(t);
    }

    public static <T> void sendToPlayer(ServerPlayer player, T t) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> {
            return player;
        }), t);
    }
}
