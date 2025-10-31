package mc.garakrral.gen.piece;

import mc.garakrral.gen.WorldGen;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

public class HousePiece extends StructurePiece {
    private final BlockPos origin;

    public HousePiece(BlockPos pos) {
        super(WorldGen.HOUSE_POND_PIECE, 0, BlockBox.create(
                new BlockPos(pos.getX() - 8, pos.getY() - 4, pos.getZ() - 8),
                new BlockPos(pos.getX() + 8, pos.getY() + 4, pos.getZ() + 8)
        ));
        this.origin = pos;
        this.setOrientation(null);
    }

    public HousePiece(StructureContext context, NbtCompound nbt) {
        super(WorldGen.HOUSE_POND_PIECE, nbt);
        this.origin = new BlockPos(nbt.getInt("ox"), nbt.getInt("oy"), nbt.getInt("oz"));
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
        nbt.putInt("ox", origin.getX());
        nbt.putInt("oy", origin.getY());
        nbt.putInt("oz", origin.getZ());
    }
    @Override
    public void generate(StructureWorldAccess world, net.minecraft.world.gen.StructureAccessor accessor,
                         net.minecraft.world.gen.chunk.ChunkGenerator generator, Random random,
                         BlockBox chunkBox, net.minecraft.util.math.ChunkPos chunkPos, BlockPos pivot) {

        // Ev boyutları
        int width = 7;
        int length = 7;
        int height = 5;

        // Ev tabanı
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = -length / 2; z <= length / 2; z++) {
                BlockPos pos = pivot.add(x, 0, z);
                world.setBlockState(pos, Blocks.OAK_PLANKS.getDefaultState(), 3);
            }
        }

        // Dış duvarlar
        for (int y = 1; y <= height; y++) {
            for (int x = -width / 2; x <= width / 2; x++) {
                for (int z = -length / 2; z <= length / 2; z++) {
                    if (x == -width / 2 || x == width / 2 || z == -length / 2 || z == length / 2) {
                        BlockPos pos = pivot.add(x, y, z);

                        // Rastgele taş ve ahşap duvar karışımı
                        if (y == height) {
                            world.setBlockState(pos, Blocks.OAK_PLANKS.getDefaultState(), 3);
                        } else if (random.nextFloat() < 0.2f) {
                            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState(), 3);
                        } else {
                            world.setBlockState(pos, Blocks.OAK_LOG.getDefaultState(), 3);
                        }
                    }
                }
            }
        }

        // Kapı
        BlockPos doorPos = pivot.add(0, 1, -length / 2);
        world.setBlockState(doorPos, Blocks.AIR.getDefaultState(), 3);
        world.setBlockState(doorPos.up(), Blocks.AIR.getDefaultState(), 3);

        // Pencereler
        for (int y = 2; y <= 3; y++) {
            world.setBlockState(pivot.add(-width / 2, y, 0), Blocks.GLASS_PANE.getDefaultState(), 3);
            world.setBlockState(pivot.add(width / 2, y, 0), Blocks.GLASS_PANE.getDefaultState(), 3);
            world.setBlockState(pivot.add(0, y, length / 2), Blocks.GLASS_PANE.getDefaultState(), 3);
        }

        // Çatı (basit üçgen)
        for (int y = 0; y < 3; y++) {
            for (int x = -width / 2 - y; x <= width / 2 + y; x++) {
                for (int z = -length / 2 - y; z <= length / 2 + y; z++) {
                    BlockPos pos = pivot.add(x, height + y, z);
                    world.setBlockState(pos, Blocks.OAK_SLAB.getDefaultState(), 3);
                }
            }
        }

        // İç dekor ve sandık
        BlockPos chestPos = pivot.add(0, 1, 0);
        world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
        var chestBE = world.getBlockEntity(chestPos);
        if (chestBE instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
            chest.setLootTable(net.minecraft.loot.LootTables.VILLAGE_BUTCHER_CHEST, random.nextLong());
        }

        // İçeride birkaç dekoratif blok
        world.setBlockState(pivot.add(1, 1, 1), Blocks.CRAFTING_TABLE.getDefaultState(), 3);
        world.setBlockState(pivot.add(-1, 1, 1), Blocks.FURNACE.getDefaultState(), 3);
    }


}
