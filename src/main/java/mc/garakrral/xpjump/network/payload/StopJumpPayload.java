package mc.garakrral.xpjump.network.payload;

import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.kaplanlib.util.path.Paths.MOBPVP;

public record StopJumpPayload() implements CustomPayload {

    public static final Id<StopJumpPayload> ID =
            new Id<>(Identifier.of(MOBPVP, "stop_jump"));

    public static final PacketCodec<Object, StopJumpPayload> CODEC =
            PacketCodec.unit(new StopJumpPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
