package com.kaplandev.gen.structure.generator;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Basit, yüksek kaliteli "yıkılmış köy" üreticisi.
 * - Daha büyük ve çeşitli evler (temel, duvarlarda rastgele eksikler, çöküş izleri)
 * - Çakıl yollar, meydan
 * - Dağınık enkaz, yosunlu taşlar, uzun otlar ve sarmaşık
 * - Arada bir lootlu chest ("blacksmith-like"), birkaç köylü (az)
 * <p>
 * Bu sınıfın generate metodunu mevcut yapınızla değiştirin veya içeriğini kopyalayın.
 */
public class RuinedVillageGenerator {


    public void generate(StructureWorldAccess world,
                         StructureAccessor accessor,
                         ChunkGenerator generator,
                         Random random,
                         BlockBox chunkBox,
                         ChunkPos chunkPos,
                         BlockPos pivot) {

        // --- Büyük köy alanı ---
        int radius = 50; // köy yarıçapı ~100x100

        // Merkezi meydan
        buildPlaza(world, pivot);

        // Evleri halka şeklinde yerleştir
        int houseCount = 18;
        for (int i = 0; i < houseCount; i++) {
            double angle = (2 * Math.PI / houseCount) * i;
            int dist = 25 + random.nextInt(15); // plaza çevresinde halka
            int dx = (int)(Math.cos(angle) * dist);
            int dz = (int)(Math.sin(angle) * dist);

            BlockPos center = pivot.add(dx, 0, dz);
            int y = world.getTopY(net.minecraft.world.Heightmap.Type.WORLD_SURFACE_WG, center.getX(), center.getZ());
            center = new BlockPos(center.getX(), y, center.getZ());

            // Ev tipi seç
            if (i % 5 == 0) {
                buildBlacksmithRuins(world, center, random); // büyük loot
            } else if (i % 3 == 0) {
                buildTwoStoryHouse(world, center, random); // 2 katlı
            } else {
                buildNormalHouse(world, center, random);
            }

            // Evin etrafına küçük enkaz ve taşlar
            scatterDebrisAroundHouse(world, center, random);
        }

        // Evleri birbirine bağlayan yollar
        createPaths(world, pivot, houseCount, radius, random);

        // Dağınık enkaz ve çalılar
        scatterVillageDebris(world, pivot, radius, random);

        // Hayatta kalmış birkaç köylü spawn
        int villagerCount = 2 + random.nextInt(5);
        if (world instanceof ServerWorld serverWorld) {
            for (int i = 0; i < villagerCount; i++) {
                BlockPos p = pivot.add(random.nextInt(radius * 2) - radius, 0, random.nextInt(radius * 2) - radius);
                int y = world.getTopY(net.minecraft.world.Heightmap.Type.WORLD_SURFACE_WG, p.getX(), p.getZ());
                VillagerEntity villager = new VillagerEntity(EntityType.VILLAGER, serverWorld);
                villager.refreshPositionAndAngles(new BlockPos(p.getX(), y, p.getZ()), 0, 0);
                serverWorld.spawnEntity(villager);
            }
        }
    }

// --- Yardımcı fonksiyonlar (örnek blueprint) ---

    private void buildPlaza(StructureWorldAccess world, BlockPos pivot) {
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pivot.getX() + x, pivot.getZ() + z);
                BlockPos pos = new BlockPos(pivot.getX() + x, y, pivot.getZ() + z);
                world.setBlockState(pos, Blocks.GRAVEL.getDefaultState(), 3);
            }
        }
    }

    private void buildNormalHouse(StructureWorldAccess world, BlockPos center, Random random) {
        int w = 5, l = 7, h = 3;

        // Temel ve duvarlar
        for (int x = -w/2; x <= w/2; x++) {
            for (int z = -l/2; z <= l/2; z++) {
                int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, center.getX() + x, center.getZ() + z);
                BlockPos floor = new BlockPos(center.getX() + x, y, center.getZ() + z);
                world.setBlockState(floor, Blocks.COBBLESTONE.getDefaultState(), 3);

                // Duvarlar
                for (int dy = 1; dy <= h; dy++) {
                    if (x == -w/2 || x == w/2 || z == -l/2 || z == l/2) {
                        BlockPos wall = floor.up(dy);
                        world.setBlockState(wall, Blocks.OAK_PLANKS.getDefaultState(), 3);
                    }
                }
            }
        }

        // Çatı
        int roofY = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, center.getX(), center.getZ()) + h + 1;
        for (int x = -w/2; x <= w/2; x++) {
            for (int z = -l/2; z <= l/2; z++) {
                BlockPos p = new BlockPos(center.getX() + x, roofY, center.getZ() + z);
                world.setBlockState(p, Blocks.OAK_PLANKS.getDefaultState(), 3);
            }
        }

        // Loot chest (normal ev)
        if (random.nextInt(100) < 30) {
            BlockPos chestPos = center.add(0, 1, 0);
            world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
            var chestBE = world.getBlockEntity(chestPos);
            if (chestBE instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
                chest.setLootTable(LootTables.VILLAGE_ARMORER_CHEST, random.nextLong());
            }
        }
    }

    private void buildTwoStoryHouse(StructureWorldAccess world, BlockPos center, Random random) {
        buildNormalHouse(world, center, random);
        // Üst kat
        int h = 3;
        for (int x = -2; x <= 2; x++) {
            for (int z = -3; z <= 3; z++) {
                BlockPos floor = center.add(x, h+1, z);
                world.setBlockState(floor, Blocks.OAK_PLANKS.getDefaultState(), 3);
            }
        }

        // Üst kat çatısı
        int roofY = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, center.getX(), center.getZ()) + h + 2;
        for (int x = -2; x <= 2; x++) {
            for (int z = -3; z <= 3; z++) {
                world.setBlockState(center.add(x, roofY, z), Blocks.OAK_PLANKS.getDefaultState(), 3);
            }
        }

        // Loot chest
        if (random.nextInt(100) < 50) {
            BlockPos chestPos = center.add(0, 2, 0);
            world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
            var chestBE = world.getBlockEntity(chestPos);
            if (chestBE instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
                chest.setLootTable(LootTables.VILLAGE_TEMPLE_CHEST, random.nextLong());
            }
        }
    }

    private void buildBlacksmithRuins(StructureWorldAccess world, BlockPos center, Random random) {
        int w = 7, l = 9, h = 3;

        // Temel
        for (int x = -w/2; x <= w/2; x++) {
            for (int z = -l/2; z <= l/2; z++) {
                int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, center.getX() + x, center.getZ() + z);
                world.setBlockState(new BlockPos(center.getX()+x, y, center.getZ()+z), Blocks.COBBLESTONE.getDefaultState(), 3);
            }
        }

        // Duvarlar (yıkık/eksik parçalar)
        for (int x = -w/2; x <= w/2; x++) {
            for (int z = -l/2; z <= l/2; z++) {
                if (x == -w/2 || x == w/2 || z == -l/2 || z == l/2) {
                    if (random.nextInt(100) < 70) {
                        BlockPos p = new BlockPos(center.getX()+x, world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, center.getX()+x, center.getZ()+z)+1, center.getZ()+z);
                        world.setBlockState(p, Blocks.COBBLESTONE.getDefaultState(), 3);
                    }
                }
            }
        }

        // Loot chest
        BlockPos chestPos = center.add(0, 1, 0);
        world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
        var chestBE = world.getBlockEntity(chestPos);
        if (chestBE instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
            chest.setLootTable(LootTables.VILLAGE_WEAPONSMITH_CHEST, random.nextLong());
        }
    }

    private void scatterDebrisAroundHouse(StructureWorldAccess world, BlockPos center, Random random) {
        for (int i = 0; i < 6; i++) {
            int rx = center.getX() + random.nextInt(6) - 3;
            int rz = center.getZ() + random.nextInt(6) - 3;
            int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, rx, rz);
            BlockPos p = new BlockPos(rx, y, rz);
            world.setBlockState(p, Blocks.COBBLESTONE.getDefaultState(), 3);
        }
    }

    private void createPaths(StructureWorldAccess world, BlockPos pivot, int houseCount, int radius, Random random) {
        // Basit yollar: evlerin merkezinden pivot’a
        for (int i = 0; i < houseCount; i++) {
            double angle = (2 * Math.PI / houseCount) * i;
            int dist = 25 + random.nextInt(15);
            int dx = (int)(Math.cos(angle) * dist);
            int dz = (int)(Math.sin(angle) * dist);
            BlockPos houseCenter = pivot.add(dx, 0, dz);
            int steps = Math.max(Math.abs(dx), Math.abs(dz));
            for (int s = 0; s <= steps; s++) {
                int x = pivot.getX() + (int)((dx) * s / (double)steps);
                int z = pivot.getZ() + (int)((dz) * s / (double)steps);
                int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z);
                world.setBlockState(new BlockPos(x, y, z), Blocks.GRAVEL.getDefaultState(), 3);
            }
        }
    }

    private void scatterVillageDebris(StructureWorldAccess world, BlockPos pivot, int radius, Random random) {
        for (int i = 0; i < radius*2; i++) {
            int rx = pivot.getX() + random.nextInt(radius*2) - radius;
            int rz = pivot.getZ() + random.nextInt(radius*2) - radius;
            int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, rx, rz);
            BlockPos p = new BlockPos(rx, y, rz);
            if (random.nextInt(100) < 15) world.setBlockState(p, Blocks.COBBLESTONE.getDefaultState(), 3);
            else if (random.nextInt(100) < 10) world.setBlockState(p, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3);
            else if (random.nextInt(100) < 8) world.setBlockState(p, Blocks.OAK_PLANKS.getDefaultState(), 3);
            if (random.nextInt(100) < 20) world.setBlockState(p.up(), Blocks.TALL_GRASS.getDefaultState(), 3);
        }
    }

}
