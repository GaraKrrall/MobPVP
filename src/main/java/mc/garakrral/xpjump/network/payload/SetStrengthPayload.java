package mc.garakrral.xpjump.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import static com.kaplanlib.util.path.Paths.MOBPVP;

public record SetStrengthPayload(int strength) implements CustomPayload {

    public static final Id<SetStrengthPayload> ID =
            new Id<>(net.minecraft.util.Identifier.of(MOBPVP, "set_strength"));

    // Bu codec, strength alanını otomatik yazıp okur.
    public static final PacketCodec<PacketByteBuf, SetStrengthPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.VAR_INT,            // strength değişkeninin tipi
                    SetStrengthPayload::strength,     // getter
                    SetStrengthPayload::new           // constructor
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}