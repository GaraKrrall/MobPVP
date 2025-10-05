package com.kaplanlib.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.function.ValueLists.OutOfBoundsHandling;

import java.util.function.IntFunction;

public enum Rarity implements StringIdentifiable {
    COMMON(0, "common", Formatting.WHITE),
    UNCOMMON(1, "uncommon", Formatting.YELLOW),
    EPIC(3, "epic", Formatting.LIGHT_PURPLE),
    DULL(0, "dull", Formatting.GRAY),               // sıradan
    STURDY(1, "sturdy", Formatting.DARK_GREEN),     // sağlam
    SHARPENED(2, "sharpened", Formatting.BLUE),     // bilenmiş
    INFUSED(3, "infused", Formatting.DARK_AQUA),    // sihirli
    ENGRAVED(4, "engraved", Formatting.DARK_PURPLE),// işlenmiş
    GLORIOUS(5, "glorious", Formatting.GOLD),       // görkemli
    FORBIDDEN(6, "forbidden", Formatting.DARK_RED), // yasaklı
    ETHEREAL(7, "ethereal", Formatting.AQUA),       // ruhani
    VOIDTOUCHED(8, "voidtouched", Formatting.DARK_GRAY), // boşlukla temaslı
    TRANSCENDENT(9, "transcendent", Formatting.GREEN);   // ötesel

    public static final Codec<Rarity> CODEC = StringIdentifiable.createBasicCodec(Rarity::values);
    public static final IntFunction<Rarity> ID_TO_VALUE = ValueLists.createIdToValueFunction(
            Rarity::getIndex, values(), OutOfBoundsHandling.ZERO
    );

    public static final PacketCodec<ByteBuf, Rarity> PACKET_CODEC = PacketCodecs.indexed(ID_TO_VALUE, (value) -> value.index);

    private final int index;
    private final String name;
    private final Formatting formatting;

    Rarity(int index, String name, Formatting formatting) {
        this.index = index;
        this.name = name;
        this.formatting = formatting;
    }

    public Formatting getFormatting() {
        return this.formatting;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public int getIndex() {
        return this.index;
    }
}
