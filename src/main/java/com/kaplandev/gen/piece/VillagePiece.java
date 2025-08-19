package com.kaplandev.gen.piece;

import com.kaplandev.gen.WorldGen;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;

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
    public void generate(StructureWorldAccess world, net.minecraft.world.gen.StructureAccessor accessor,
                         net.minecraft.world.gen.chunk.ChunkGenerator generator, Random random,
                         BlockBox chunkBox, net.minecraft.util.math.ChunkPos chunkPos, BlockPos pivot) {

        int tents = 8 + random.nextInt(2); // 4-5 çadır
        int spacing = 6; // çadırlar arası boşluk

        for (int i = 0; i < tents; i++) {
            int dx = (random.nextBoolean() ? 1 : -1) * (spacing + random.nextInt(3));
            int dz = (random.nextBoolean() ? 1 : -1) * (spacing + random.nextInt(3));

            BlockPos tentPos = pivot.add(dx, 0, dz);

            // Yere bağlı spawn
            int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, tentPos.getX(), tentPos.getZ());
            BlockPos spawnPos = new BlockPos(tentPos.getX(), y, tentPos.getZ());

            // Çadır tabanı
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    world.setBlockState(spawnPos.add(x, 0, z), Blocks.OAK_PLANKS.getDefaultState(), 3);
                }
            }

            // Çadır çatısı (basit üçgen)
            for (int h = 1; h <= 2; h++) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        if (Math.abs(x) + Math.abs(z) <= 2 - h) {
                            world.setBlockState(spawnPos.add(x, h, z), Blocks.WHITE_WOOL.getDefaultState(), 3);
                        }
                    }
                }
            }

            // Çadır içine sandık
            BlockPos chestPos = spawnPos.add(0, 1, 0);
            world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
            var chestBE = world.getBlockEntity(chestPos);
            if (chestBE instanceof net.minecraft.block.entity.ChestBlockEntity chest) {
                chest.setLootTable(LootTables.VILLAGE_ARMORER_CHEST, random.nextLong());
            }


            // Köylüler (2-3 tane her çadır)
            int villagerCount = 2 + random.nextInt(2);
            for (int v = 0; v < villagerCount; v++) {
                BlockPos villagerPos = spawnPos.add(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
                if (world instanceof net.minecraft.server.world.ServerWorld serverWorld) {
                    net.minecraft.entity.passive.VillagerEntity villager = new net.minecraft.entity.passive.VillagerEntity(
                            net.minecraft.entity.EntityType.VILLAGER, serverWorld
                    );
                    villager.refreshPositionAndAngles(villagerPos, 0, 0);
                    serverWorld.spawnEntity(villager);
                }
            }
        }

        // Ortadaki küçük meydan
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos pos = pivot.add(x, world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pivot.getX() + x, pivot.getZ() + z), z);
                world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState(), 3);
            }
        }
    }

}
