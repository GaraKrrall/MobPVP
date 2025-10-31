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

public class WaterPondPiece extends StructurePiece {
    private final BlockPos origin;

    public WaterPondPiece(BlockPos pos) {
        super(WorldGen.WATER_POND_PIECE, 0, BlockBox.create(
                new BlockPos(pos.getX() - 8, pos.getY() - 4, pos.getZ() - 8),
                new BlockPos(pos.getX() + 8, pos.getY() + 4, pos.getZ() + 8)
        ));
        this.origin = pos;
        this.setOrientation(null);
    }

    public WaterPondPiece(StructureContext context, NbtCompound nbt) {
        super(WorldGen.WATER_POND_PIECE, nbt);
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

        int radius = 6 + random.nextInt(4); // daha büyük gölet

        // Gölet tabanı ve suyu
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double dist = Math.sqrt(x * x + z * z);
                BlockPos pos = origin.add(x, -2, z);

                if (dist <= radius) {
                    // taban kil + yosunlu
                    if (random.nextFloat() < 0.15f) {
                        world.setBlockState(pos, Blocks.MOSS_BLOCK.getDefaultState(), 3);
                    } else {
                        world.setBlockState(pos, Blocks.CLAY.getDefaultState(), 3);
                    }
                    // su doldur
                    world.setBlockState(pos.up(), Blocks.WATER.getDefaultState(), 3);
                    world.setBlockState(pos.up(2), Blocks.WATER.getDefaultState(), 3);
                } else if (dist <= radius + 1.5) {
                    // kenar sahil
                    world.setBlockState(pos, Blocks.SAND.getDefaultState(), 3);
                }
            }
        }

        // Antik taş duvarlar
        for (int dx = -radius - 2; dx <= radius + 2; dx++) {
            for (int dz = -radius - 2; dz <= radius + 2; dz++) {
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist >= radius + 1.8 && dist <= radius + 2.5) {
                    BlockPos wallPos = origin.add(dx, 0, dz);

                    // taş türü çeşitliliği
                    float r = random.nextFloat();
                    if (r < 0.5f) {
                        world.setBlockState(wallPos, Blocks.STONE_BRICKS.getDefaultState(), 3);
                    } else if (r < 0.7f) {
                        world.setBlockState(wallPos, Blocks.MOSSY_STONE_BRICKS.getDefaultState(), 3);
                    } else {
                        world.setBlockState(wallPos, Blocks.CRACKED_STONE_BRICKS.getDefaultState(), 3);
                    }

                    // bazı yerlerde sütun/merdiven
                    if (random.nextFloat() < 0.15f) {
                        world.setBlockState(wallPos.up(), Blocks.STONE_BRICK_WALL.getDefaultState(), 3);
                    }
                    if (random.nextFloat() < 0.1f) {
                        world.setBlockState(wallPos.up(), Blocks.STONE_BRICK_STAIRS.getDefaultState(), 3);
                    }
                }
            }
        }

        // Ana merkez sandık
        BlockPos mainChestPos = origin.add(0, -1, 0);
        world.setBlockState(mainChestPos, Blocks.CHEST.getDefaultState(), 3);
        var be = world.getBlockEntity(mainChestPos);
        if (be instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
            chest.setLootTable(net.minecraft.loot.LootTables.SIMPLE_DUNGEON_CHEST, random.nextLong());
        }

        // Dört gizli sandık (duvar diplerinde)
        for (int i = 0; i < 4; i++) {
            int angle = 90 * i + random.nextInt(30) - 15; // biraz rastgele açı
            double rad = Math.toRadians(angle);
            int dx = (int) Math.round(Math.cos(rad) * (radius + 1));
            int dz = (int) Math.round(Math.sin(rad) * (radius + 1));
            BlockPos chestPos = origin.add(dx, -1, dz);

            world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
            var be2 = world.getBlockEntity(chestPos);
            if (be2 instanceof net.minecraft.block.entity.ChestBlockEntity chest2) {
                chest2.setLootTable(net.minecraft.loot.LootTables.ANCIENT_CITY_ICE_BOX_CHEST, random.nextLong());
            }
        }

        // Su altı ışıkları
        for (int i = 0; i < 5; i++) {
            int rx = random.nextInt(radius * 2) - radius;
            int rz = random.nextInt(radius * 2) - radius;
            BlockPos glow = origin.add(rx, -2, rz);
            if (world.getBlockState(glow).isOf(Blocks.CLAY)) {
                world.setBlockState(glow, Blocks.SEA_LANTERN.getDefaultState(), 3);
            }
        }

        // Sarmaşıklar ile antik atmosfer
        for (int i = 0; i < 20; i++) {
            BlockPos vinePos = origin.add(
                    random.nextInt(radius * 2) - radius,
                    1 + random.nextInt(3),
                    random.nextInt(radius * 2) - radius
            );
            if (world.isAir(vinePos)) {
                world.setBlockState(vinePos, Blocks.VINE.getDefaultState(), 3);
            }
        }
    }


}
