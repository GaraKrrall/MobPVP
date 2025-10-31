package mc.garakrral.gen.structure;

import mc.garakrral.gen.WorldGen;
import mc.garakrral.gen.piece.BigPvETowerPiece;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class BigPvETower extends Structure {
    public static final MapCodec<BigPvETower> CODEC = createCodec(BigPvETower::new);

    public BigPvETower(Config config) {
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
            collector.addPiece(new BigPvETowerPiece(pos));
        });
    }


    @Override
    public StructureType<?> getType() {
        return WorldGen.BIG_PVE_TOWER_POND;
    }
}
