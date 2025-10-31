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

public class PvETowerPiece extends StructurePiece {
    private final BlockPos origin;

    public PvETowerPiece(BlockPos pos) {
        super(WorldGen.PVE_TOWER_POND_PIECE, 0, BlockBox.create(
                new BlockPos(pos.getX() - 8, pos.getY() - 4, pos.getZ() - 8),
                new BlockPos(pos.getX() + 8, pos.getY() + 4, pos.getZ() + 8)
        ));
        this.origin = pos;
        this.setOrientation(null);
    }

    public PvETowerPiece(StructureContext context, NbtCompound nbt) {
        super(WorldGen.PVE_TOWER_POND_PIECE, nbt);
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

        int floors = 5;
        int floorHeight = 6;
        int radius = 6;

        // --- TABAN ---
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos ground = origin.add(x, -1, z);
                if (Math.sqrt(x * x + z * z) <= radius) {
                    world.setBlockState(ground, Blocks.STONE_BRICKS.getDefaultState(), 3);
                }
            }
        }

        // --- KATLAR ---
        BlockPos pos = null;
        for (int floor = 0; floor < floors; floor++) {
            int baseY = origin.getY() + floor * floorHeight;

            // Duvarlar
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double dist = Math.sqrt(x * x + z * z);
                    pos = origin.add(x, baseY, z);

                    if (dist >= radius - 0.5 && dist <= radius + 0.5) {
                        for (int y = 0; y < floorHeight; y++) {
                            BlockPos wall = pos.up(y);
                            float r = random.nextFloat();
                            if (r < 0.6f) {
                                world.setBlockState(wall, Blocks.STONE_BRICKS.getDefaultState(), 3);
                            } else if (r < 0.8f) {
                                world.setBlockState(wall, Blocks.MOSSY_STONE_BRICKS.getDefaultState(), 3);
                            } else {
                                world.setBlockState(wall, Blocks.CRACKED_STONE_BRICKS.getDefaultState(), 3);
                            }
                        }
                    }
                }
            }

            // Zemin
            for (int x = -radius + 1; x < radius; x++) {
                for (int z = -radius + 1; z < radius; z++) {
                    if (Math.sqrt(x * x + z * z) < radius - 1.5) {
                        world.setBlockState(origin.add(x, baseY, z), Blocks.COBBLESTONE.getDefaultState(), 3);
                    }
                }
            }

            // Spiral merdiven
            for (int step = 0; step < floorHeight; step++) {
                int angle = (floor * floorHeight + step) * 90 / 2; // spiral dönüş
                double rad = Math.toRadians(angle);
                int sx = (int) Math.round(Math.cos(rad) * (radius - 2));
                int sz = (int) Math.round(Math.sin(rad) * (radius - 2));
                BlockPos stairPos = origin.add(sx, baseY + step, sz);
                world.setBlockState(stairPos, Blocks.STONE_BRICK_STAIRS.getDefaultState(), 3);
            }

            // Spawner (kat ortası)
            BlockPos spawnerPos = origin.add(0, baseY + 1, 0);
            world.setBlockState(spawnerPos, Blocks.SPAWNER.getDefaultState(), 3);
            var be = world.getBlockEntity(spawnerPos);
            if (be instanceof net.minecraft.block.entity.MobSpawnerBlockEntity spawner) {
                var type = random.nextBoolean()
                        ? net.minecraft.entity.EntityType.ZOMBIE
                        : net.minecraft.entity.EntityType.SKELETON;

                // spawner'a entity türünü ayarla
                spawner.setEntityType(type, world.getRandom());
            }


            // Sandık (duvara yakın)
            BlockPos chestPos = origin.add((random.nextBoolean() ? radius - 2 : -(radius - 2)), baseY + 1, 0);
            world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
            var chestBE = world.getBlockEntity(chestPos);
            if (chestBE instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
                chest.setLootTable(net.minecraft.loot.LootTables.SIMPLE_DUNGEON_CHEST, random.nextLong());
            }

            // Kat tavanı
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (Math.sqrt(x * x + z * z) <= radius) {
                        world.setBlockState(origin.add(x, baseY + floorHeight, z), Blocks.STONE_BRICKS.getDefaultState(), 3);
                    }
                }
            }
            ;
        }

        // --- EN ÜST KAT LOOT ---
        BlockPos bossChest = origin.add(0, origin.getY() + floors * floorHeight, 0);
        world.setBlockState(bossChest, Blocks.CHEST.getDefaultState(), 3);
        var beBoss = world.getBlockEntity(bossChest);
        if (beBoss instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
            chest.setLootTable(net.minecraft.loot.LootTables.ANCIENT_CITY_CHEST, random.nextLong());
        }

        // Çatı kubbe
        int topY = origin.getY() + floors * floorHeight;
        for (int y = 0; y < 4; y++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double dist = Math.sqrt(x * x + z * z);
                    if (dist < radius - y) {
                        BlockPos roof = origin.add(x, topY + y, z);
                        world.setBlockState(roof, Blocks.STONE_BRICKS.getDefaultState(), 3);
                    }
                }
            }
        }
        // Roket altı / gemi altı ekleme
        BlockPos baseCenter = pos.down(1); // kulenin altı

// Ortadaki ana motor bloğu (örnek: demir blok)
        world.setBlockState(baseCenter, Blocks.IRON_BLOCK.getDefaultState(), 3);

// Çevresine ateş efektli bloklar (örnek: magma)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (Math.abs(dx) + Math.abs(dz) == 1) { // artı şeklinde
                    BlockPos flamePos = baseCenter.add(dx, 0, dz);
                    world.setBlockState(flamePos, Blocks.MAGMA_BLOCK.getDefaultState(), 3);

                    // Altına alev efekti (fire block)
                    BlockPos firePos = flamePos.down();
                    world.setBlockState(firePos, Blocks.FIRE.getDefaultState(), 3);
                }
            }
        }


    }


}
