package com.kaplandev.gen.piece;

import com.kaplandev.gen.WorldGen;

import net.minecraft.block.Blocks;
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

public class StatuePiece extends StructurePiece {
    private final BlockPos origin;

    public StatuePiece(BlockPos pos) {
        super(WorldGen.STATUE_POND_PIECE, 0, BlockBox.create(
                new BlockPos(pos.getX() - 8, pos.getY() - 4, pos.getZ() - 8),
                new BlockPos(pos.getX() + 8, pos.getY() + 4, pos.getZ() + 8)
        ));
        this.origin = pos;
        this.setOrientation(null);
    }

    public StatuePiece(StructureContext context, NbtCompound nbt) {
        super(WorldGen.STATUE_POND_PIECE, nbt);
        this.origin = new BlockPos(nbt.getInt("ox"), nbt.getInt("oy"), nbt.getInt("oz"));
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
        nbt.putInt("ox", origin.getX());
        nbt.putInt("oy", origin.getY());
        nbt.putInt("oz", origin.getZ());
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor accessor,
                         ChunkGenerator generator, Random random,
                         BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {

        // 4 farklı heykel türü
        int type = random.nextInt(4);

        switch (type) {
            case 0 -> buildStatueHuman(world, pivot);
            case 1 -> buildStatueSword(world, pivot);
            case 2 -> buildStatueShield(world, pivot);
            case 3 -> buildStatueDragon(world, pivot);
        }
    }

// ----------------- Heykel fonksiyonları -----------------

    private void buildStatueHuman(StructureWorldAccess world, BlockPos pivot) {
        // Basit insan figürü 3x3 taban, 6 yükseklik
        for (int y = 0; y < 6; y++) {
            world.setBlockState(pivot.up(y), Blocks.STONE_BRICKS.getDefaultState(), 3);
        }
        // Kollar
        world.setBlockState(pivot.add(1, 3, 0), Blocks.STONE_BRICKS.getDefaultState(), 3);
        world.setBlockState(pivot.add(-1, 3, 0), Blocks.STONE_BRICKS.getDefaultState(), 3);
    }

    private void buildStatueSword(StructureWorldAccess world, BlockPos pivot) {
        // Kılıç figürü 1x1 taban, 5 yükseklik
        for (int y = 0; y < 5; y++) {
            world.setBlockState(pivot.up(y), Blocks.IRON_BLOCK.getDefaultState(), 3);
        }
        // Kılıç ucu
        world.setBlockState(pivot.up(5), Blocks.GOLD_BLOCK.getDefaultState(), 3);
    }

    private void buildStatueShield(StructureWorldAccess world, BlockPos pivot) {
        // Kalkan 3x3 taban, 4 yükseklik
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = -1; z <= 1; z++) {
                    world.setBlockState(pivot.add(x, y, z), Blocks.OAK_PLANKS.getDefaultState(), 3);
                }
            }
        }
        // Kalkan desenleri
        world.setBlockState(pivot.add(0, 2, 0), Blocks.GOLD_BLOCK.getDefaultState(), 3);
        world.setBlockState(pivot.add(0, 3, 0), Blocks.RED_WOOL.getDefaultState(), 3);
    }

    private void buildStatueDragon(StructureWorldAccess world, BlockPos pivot) {
        // Ejderha başı 3x3 taban
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                world.setBlockState(pivot.add(x, 4, z), Blocks.BLACKSTONE.getDefaultState(), 3);
            }
        }
        // Gövde 1x1, 4 yükseklik
        for (int y = 0; y < 4; y++) {
            world.setBlockState(pivot.up(y), Blocks.DARK_OAK_LOG.getDefaultState(), 3);
        }
        // Kuyruk
        world.setBlockState(pivot.add(0, 2, -1), Blocks.DARK_OAK_LOG.getDefaultState(), 3);
    }

}
