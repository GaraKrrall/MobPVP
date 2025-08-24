package com.kaplandev.gen.piece;

import com.kaplandev.gen.WorldGen;
import com.kaplandev.gen.structure.generator.RuinedVillageGenerator;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class VillagePiece extends StructurePiece {
    private final BlockPos origin;

    public VillagePiece(BlockPos pos) {
        super(WorldGen.VILLAGE_POND_PIECE, 0, BlockBox.create(
                new BlockPos(pos.getX() - 8, pos.getY() - 4, pos.getZ() - 8),
                new BlockPos(pos.getX() + 8, pos.getY() + 4, pos.getZ() + 8)
        ));
        this.origin = pos;
        this.setOrientation(null);
    }

    public VillagePiece(StructureContext context, NbtCompound nbt) {
        super(WorldGen.VILLAGE_POND_PIECE, nbt);
        this.origin = new BlockPos(nbt.getInt("ox"), nbt.getInt("oy"), nbt.getInt("oz"));
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
        nbt.putInt("ox", origin.getX());
        nbt.putInt("oy", origin.getY());
        nbt.putInt("oz", origin.getZ());
    }

    @Override
    public void generate(StructureWorldAccess world,
                         StructureAccessor accessor,
                         ChunkGenerator generator,
                         Random random,
                         BlockBox chunkBox,
                         ChunkPos chunkPos,
                         BlockPos pivot) {

        // Buraya RuinedVillageGenerator kodunu çağır
        new RuinedVillageGenerator().generate(world, accessor, generator, random, chunkBox, chunkPos, pivot);
    }
}


