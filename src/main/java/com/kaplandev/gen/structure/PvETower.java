package com.kaplandev.gen.structure;

import com.kaplandev.gen.WorldGen;
import com.kaplandev.gen.piece.PvETowerPiece;
import com.kaplandev.gen.piece.WaterPondPiece;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class PvETower extends Structure {
    public static final MapCodec<PvETower> CODEC = createCodec(PvETower::new);

    public PvETower(Config config) {
        super(config);
    }

    @Override
    public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
        return Structure.getStructurePosition(context, Heightmap.Type.WORLD_SURFACE_WG, collector -> {
            ChunkPos cp = context.chunkPos();
            BlockPos pos = new BlockPos(cp.getCenterX(),
                    context.chunkGenerator().getHeightInGround(cp.getCenterX(), cp.getCenterZ(),
                            Heightmap.Type.WORLD_SURFACE_WG, context.world(), context.noiseConfig()),
                    cp.getCenterZ());
            collector.addPiece(new PvETowerPiece(pos));
        });
    }


    @Override
    public StructureType<?> getType() {
        return WorldGen.PVE_TOWER_POND;
    }
}
