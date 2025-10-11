package com.kaplandev.gen.piece;

import com.kaplandev.gen.WorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
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

public class BigPvETowerPiece extends StructurePiece {
    private final BlockPos origin;

    public BigPvETowerPiece(BlockPos pos) {
        super(WorldGen.BIG_PVE_TOWER_PIECE, 0, BlockBox.create(
                new BlockPos(pos.getX() - 8, pos.getY() - 4, pos.getZ() - 8),
                new BlockPos(pos.getX() + 8, pos.getY() + 4, pos.getZ() + 8)
        ));
        this.origin = pos;
        this.setOrientation(null);
    }

    public BigPvETowerPiece(StructureContext context, NbtCompound nbt) {
        super(WorldGen.BIG_PVE_TOWER_PIECE, nbt);
        this.origin = new BlockPos(nbt.getInt("ox"), nbt.getInt("oy"), nbt.getInt("oz"));
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
        nbt.putInt("ox", origin.getX());
        nbt.putInt("oy", origin.getY());
        nbt.putInt("oz", origin.getZ());
    }

    @Override
    public void generate(StructureWorldAccess world, net.minecraft.world.gen.StructureAccessor accessor, net.minecraft.world.gen.chunk.ChunkGenerator generator, Random random, BlockBox chunkBox, net.minecraft.util.math.ChunkPos chunkPos, BlockPos pivot) {

        final int floors = 14;        // kat sayısı (devasa)
        final int floorHeight = 6;
        final int radius = 12;        // yarıçap (büyük kule)
        final int wallThickness = 1;

        // --- Foundation / Taban ---
        for (int x = -radius - 2; x <= radius + 2; x++) {
            for (int z = -radius - 2; z <= radius + 2; z++) {
                double dist = Math.hypot(x, z);
                BlockPos ground = origin.add(x, -2, z);
                if (dist <= radius + 1.25) {
                    BlockState base = dist < radius - 1.5 ? Blocks.COBBLESTONE.getDefaultState() : Blocks.STONE_BRICKS.getDefaultState();
                    world.setBlockState(ground, base, 3);
                }
            }
        }

        BlockPos centerLast = origin;

        for (int floor = 0; floor < floors; floor++) {
            int baseY = origin.getY() + floor * floorHeight;

            // --- Walls: dış duvar bandı ---
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double dist = Math.hypot(x, z);
                    if (dist >= radius - 0.75 && dist <= radius + 0.75) {
                        for (int t = 0; t < wallThickness; t++) {
                            for (int y = 0; y < floorHeight; y++) {
                                BlockPos p = origin.add(x * (1 + t), baseY + y, z * (1 + t));
                                BlockState bs = selectWallBlock(random);
                                world.setBlockState(p, bs, 3);
                            }
                        }
                        // pencere boşlukları (aralıklı)
                        if (floor % 2 == 0 && random.nextFloat() < 0.18f) {
                            placeVerticalWindow(world, origin.add(x, baseY + 1, z), Math.max(2, floorHeight - 3));
                        }
                    }
                }
            }

            // --- Inner floor / iç zemin desenleri ---
            for (int x = -radius + 1; x <= radius - 1; x++) {
                for (int z = -radius + 1; z <= radius - 1; z++) {
                    if (Math.hypot(x, z) < radius - 1.5) {
                        BlockPos p = origin.add(x, baseY, z);
                        BlockState floorState = ((Math.abs(x) + Math.abs(z)) % 6 == 0) ? Blocks.POLISHED_ANDESITE.getDefaultState() : Blocks.MOSSY_COBBLESTONE.getDefaultState();
                        world.setBlockState(p, floorState, 3);
                    }
                }
            }

            // --- Merkez: boşluk ve spiral merdiven + su asansörü (opsiyonel) ---
            buildCentralCore(world, origin, baseY, floorHeight, random);

            // --- Spawner ve sandık yerleşimi ---
            if (floor % 2 == 0) {
                BlockPos spawnerBase = origin.add(0, baseY, 0);
                BlockPos spawnerPos = spawnerBase.up();

                // 3x3 zemin platformu oluştur
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos floorPos = spawnerBase.add(x, 0, z);
                        world.setBlockState(floorPos, Blocks.POLISHED_ANDESITE.getDefaultState(), 3);
                    }
                }

                // Spawner yerleştir
                world.setBlockState(spawnerPos, com.kaplandev.block.Blocks.PVP_SPAWNER.getDefaultState(), 3);

            }

            // 2 adet sandık: zırh/loot + küçük dekor
            for (int i = 0; i < 2; i++) {
                int dx = (i == 0) ? radius - 2 : -(radius - 2);
                int dz = (i == 0) ? 1 : -1;
                BlockPos chestPos = origin.add(dx, baseY + 1, dz);
                world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
                var be = world.getBlockEntity(chestPos);
                if (be instanceof ChestBlockEntity chest) {
                    chest.setLootTable((i == 0) ? LootTables.SIMPLE_DUNGEON_CHEST : LootTables.BURIED_TREASURE_CHEST, random.nextLong());
                }
            }

            // --- Küçük balkonlar / dış kornişler ---
            buildFloorBalconies(world, origin, baseY, radius, random);

            // --- Kat tavanı ---
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (Math.hypot(x, z) <= radius) {
                        world.setBlockState(origin.add(x, baseY + floorHeight, z), Blocks.STONE_BRICKS.getDefaultState(), 3);
                    }
                }
            }

            centerLast = origin.add(0, baseY, 0);
        }

        // --- En üst: büyük boss odası ---
        BlockPos bossCenter = origin.add(0, floors * floorHeight + 2, 0);
        buildBossRoom(world, bossCenter, radius - 2, random);

        // --- Kubbe / zirve: cam & taş karışımı ---
        buildGlassDome(world, origin, floors, floorHeight, radius, random);

        // --- Dış: giriş, köprü ve çevre düzenlemesi ---
        buildEntranceBridge(world, origin, radius, random);

        // --- Roket / motor altı görselliği ---
        BlockPos baseCenter = centerLast.down(2);
        world.setBlockState(baseCenter, Blocks.IRON_BLOCK.getDefaultState(), 3);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (Math.abs(dx) + Math.abs(dz) == 1) {
                    BlockPos magma = baseCenter.add(dx, 0, dz);
                    world.setBlockState(magma, Blocks.MAGMA_BLOCK.getDefaultState(), 3);
                    world.setBlockState(magma.down(), Blocks.FIRE.getDefaultState(), 3);
                }
            }
        }

        // --- Son dokunuş: dış süslemeler ---
        addExteriorDecoration(world, origin, radius, random);
    }

    // ---------- Yardımcı metodlar (private static) ----------

    private static BlockState selectWallBlock(Random random) {
        float r = random.nextFloat();
        if (r < 0.52f) return Blocks.STONE_BRICKS.getDefaultState();
        if (r < 0.78f) return Blocks.MOSSY_STONE_BRICKS.getDefaultState();
        if (r < 0.94f) return Blocks.CRACKED_STONE_BRICKS.getDefaultState();
        return Blocks.CHISELED_STONE_BRICKS.getDefaultState();
    }


    private static void placeVerticalWindow(StructureWorldAccess world, BlockPos start, int height) {
        for (int i = 0; i < height; i++) {
            world.setBlockState(start.up(i), Blocks.GLASS_PANE.getDefaultState(), 3);
        }
    }

    private static void buildCentralCore(StructureWorldAccess world, BlockPos origin, int baseY, int floorHeight, Random random) {
        // 3x3 boşluk; ortada su asansörü (kaynak) + spiral merdiven kenarda
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 0; y < floorHeight; y++) {
                    BlockPos p = origin.add(x, baseY + y, z);
                    if (x == 0 && z == 0) {
                        world.setBlockState(p, Blocks.WATER.getDefaultState(), 3);
                    } else {
                        world.setBlockState(p, Blocks.AIR.getDefaultState(), 3);
                    }
                }
            }
        }
        // su kaynağı üstte bitki ile sabitlenir
        world.setBlockState(origin.add(0, baseY + floorHeight - 1, 0), Blocks.KELP_PLANT.getDefaultState(), 3);

        // spiral merdiven: dış çeper boyunca küçük basamaklar
        for (int step = 0; step < floorHeight; step++) {
            int angle = (baseY + step) * 45;
            double rad = Math.toRadians(angle);
            int sx = (int) Math.round(Math.cos(rad) * (5));
            int sz = (int) Math.round(Math.sin(rad) * (5));
            BlockPos stairPos = origin.add(sx, baseY + step, sz);
            world.setBlockState(stairPos, Blocks.STONE_BRICK_STAIRS.getDefaultState(), 3);
            if (random.nextFloat() < 0.45f) world.setBlockState(stairPos.up(), Blocks.COBBLESTONE_WALL.getDefaultState(), 3);
        }
    }

    private static void buildFloorBalconies(StructureWorldAccess world, BlockPos origin, int baseY, int radius, Random random) {
        for (int side = 0; side < 4; side++) {
            int dx = (side == 0 ? radius : (side == 2 ? -radius : 0));
            int dz = (side == 1 ? radius : (side == 3 ? -radius : 0));
            BlockPos b = origin.add(dx, baseY + 2, dz);
            for (int x = -2; x <= 2; x++) {
                for (int z = 0; z <= 2; z++) {
                    BlockPos p = b.add(x, 0, (dz == 0) ? z : -z);
                    world.setBlockState(p, Blocks.OAK_PLANKS.getDefaultState(), 3);
                }
            }
            world.setBlockState(b.up(1), Blocks.LANTERN.getDefaultState(), 3);
            if (random.nextFloat() < 0.25f) {
                world.setBlockState(b.add(0, 0, (dz == 0) ? 3 : -3), Blocks.POTTED_POPPY.getDefaultState(), 3);
            }
        }
    }

    private static void buildBossRoom(StructureWorldAccess world, BlockPos center, int chamberRadius, Random random) {
        int height = 6;
        // oda duvarları ve zemin
        for (int x = -chamberRadius; x <= chamberRadius; x++) {
            for (int z = -chamberRadius; z <= chamberRadius; z++) {
                double dist = Math.hypot(x, z);
                if (dist <= chamberRadius + 0.2) {
                    for (int y = 0; y <= height; y++) {
                        BlockPos p = center.add(x, -y, z);
                        world.setBlockState(p, Blocks.STONE_BRICKS.getDefaultState(), 3);
                    }
                    BlockPos floor = center.add(x, 0, z);
                    if ((Math.abs(x) + Math.abs(z)) % 4 == 0) world.setBlockState(floor, Blocks.GILDED_BLACKSTONE.getDefaultState(), 3);
                    else world.setBlockState(floor, Blocks.POLISHED_BASALT.getDefaultState(), 3);
                }
            }
        }

        // boss sandığı
        BlockPos bossChest = center.add(0, 1, 0);
        world.setBlockState(bossChest, Blocks.CHEST.getDefaultState(), 3);
        var beBoss = world.getBlockEntity(bossChest);
        if (beBoss instanceof ChestBlockEntity chest) {
            chest.setLootTable(LootTables.ANCIENT_CITY_CHEST, random.nextLong());
        }

        // çevresel sütunlar ve ruhani ışıklar
        for (int angle = 0; angle < 360; angle += 45) {
            double rad = Math.toRadians(angle);
            int sx = (int) Math.round(Math.cos(rad) * (chamberRadius - 1));
            int sz = (int) Math.round(Math.sin(rad) * (chamberRadius - 1));
            BlockPos col = center.add(sx, 1, sz);
            world.setBlockState(col, Blocks.QUARTZ_PILLAR.getDefaultState(), 3);
            world.setBlockState(col.up(3), Blocks.SOUL_LANTERN.getDefaultState(), 3);
        }

        // boss spawner (güçlü)
        BlockPos sp = center.add(0, 2, 0);
        world.setBlockState(sp, com.kaplandev.block.Blocks.PVP_SPAWNER_MAX.getDefaultState(), 3);
    }

    private static void buildGlassDome(StructureWorldAccess world, BlockPos origin, int floors, int floorHeight, int radius, Random random) {
        int top = origin.getY() + floors * floorHeight + 3;
        for (int y = 0; y < 8; y++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double dist = Math.hypot(x, z);
                    if (dist < radius - y * 0.9) {
                        BlockPos p = origin.add(x, top + y, z);
                        if (y == 7 && dist < 2) {
                            world.setBlockState(p, Blocks.GOLD_BLOCK.getDefaultState(), 3);
                        } else {
                            int pick = (Math.abs(x) + Math.abs(z) + y) % 7;
                            switch (pick) {
                                case 0 -> world.setBlockState(p, Blocks.RED_STAINED_GLASS.getDefaultState(), 3);
                                case 1 -> world.setBlockState(p, Blocks.BLUE_STAINED_GLASS.getDefaultState(), 3);
                                case 2 -> world.setBlockState(p, Blocks.GREEN_STAINED_GLASS.getDefaultState(), 3);
                                case 3 -> world.setBlockState(p, Blocks.YELLOW_STAINED_GLASS.getDefaultState(), 3);
                                case 4 -> world.setBlockState(p, Blocks.PURPLE_STAINED_GLASS.getDefaultState(), 3);
                                case 5 -> world.setBlockState(p, Blocks.CYAN_STAINED_GLASS.getDefaultState(), 3);
                                default -> world.setBlockState(p, Blocks.WHITE_STAINED_GLASS.getDefaultState(), 3);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void buildEntranceBridge(StructureWorldAccess world, BlockPos origin, int radius, Random random) {
        BlockPos doorBase = origin.add(0, 0, -(radius + 2));
        world.setBlockState(doorBase, Blocks.OAK_DOOR.getDefaultState(), 3);
        for (int i = 1; i <= 12; i++) {
            BlockPos p = origin.add(0, -1, -(radius + 2 + i));
            world.setBlockState(p, Blocks.STONE_BRICKS.getDefaultState(), 3);
            if (i % 3 == 0) world.setBlockState(p.up(), Blocks.TORCH.getDefaultState(), 3);
            if (random.nextFloat() < 0.12f) world.setBlockState(p.up(2), Blocks.VINE.getDefaultState(), 3);
        }
    }

    private static void addExteriorDecoration(StructureWorldAccess world, BlockPos origin, int radius, Random random) {
        for (int x = -radius - 6; x <= radius + 6; x += 3) {
            for (int z = -radius - 6; z <= radius + 6; z += 3) {
                if (random.nextFloat() < 0.08f) {
                    BlockPos p = origin.add(x, 0, z);
                    world.setBlockState(p.up(), Blocks.OAK_SAPLING.getDefaultState(), 3);
                }
                if (random.nextFloat() < 0.04f) {
                    BlockPos v = origin.add(x, 1, z);
                    world.setBlockState(v, Blocks.VINE.getDefaultState(), 3);
                }
            }
        }
        for (int i = -2; i <= 2; i += 2) {
            world.setBlockState(origin.add(radius + 1, 1, i), Blocks.LANTERN.getDefaultState(), 3);
            world.setBlockState(origin.add(-(radius + 1), 1, i), Blocks.LANTERN.getDefaultState(), 3);
        }
    }
}
