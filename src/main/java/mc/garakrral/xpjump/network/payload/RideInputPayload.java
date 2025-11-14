package mc.garakrral.xpjump.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.kaplanlib.util.path.Paths.MOBPVP;

public record RideInputPayload(boolean forward, boolean sprintBrake) implements CustomPayload {

    public static final Id<RideInputPayload> ID =
            new Id<>(Identifier.of(MOBPVP, "ride_input"));

    public static final PacketCodec<PacketByteBuf, RideInputPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.BOOL, RideInputPayload::forward,
                    PacketCodecs.BOOL, RideInputPayload::sprintBrake,
                    RideInputPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
