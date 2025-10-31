package mc.garakrral.build;

import mc.garakrral.block.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 3 odalı + koridorlu epik dungeon.
 * Kurgu:
 *  [Sol Oda]──[Merkez Salon / Sunak]──[Sağ Oda]
 *                 │
 *             [Arka Oda]
 *
 * Tuzak noktaları LOdestone ile işaretlidir (sen yerine kendi düzeneklerini koy).
 */
public class ArenaFeature extends Feature<DefaultFeatureConfig> {

    public ArenaFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    // Oda boyutları
    private static final int ROOM_W = 9;  // x
    private static final int ROOM_H = 7;  // y
    private static final int ROOM_D = 9;  // z

    // Koridor uzunluğu
    private static final int HALL_LEN = 5;
    private static final int FLOOR_Y_OFFSET = 0; // origin Y katı

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> ctx) {
        WorldAccess world = ctx.getWorld();
        Random rand = ctx.getRandom();
        BlockPos origin = ctx.getOrigin().withY(ctx.getOrigin().getY() + FLOOR_Y_OFFSET);


        // Dungeon yönünü rastgele seç (X ekseni boyunca solda/sağda odalar)
        Direction facing = rand.nextBoolean() ? Direction.EAST :  Direction.WEST;

        // Merkez salonun sol-üst köşe pozunu hesapla (inşaat kolaylığı için)
        BlockPos centerMin = origin.add(-ROOM_W / 2, 0, -ROOM_D / 2);
        BlockBox centerBox = new BlockBox(
                centerMin.getX(), centerMin.getY(), centerMin.getZ(),
                centerMin.getX() + ROOM_W - 1, centerMin.getY() + ROOM_H - 1, centerMin.getZ() + ROOM_D - 1
        );

        List<BlockPos> trapMarkers = new ArrayList<>();

        // 1) Merkez salon
        buildRoom(world, rand, centerBox, true, trapMarkers);

        // 2) Sol oda + koridor
        int dx = (facing == Direction.EAST ? -(ROOM_W / 2 + HALL_LEN + ROOM_W) : -(ROOM_W / 2 + HALL_LEN + ROOM_W));
        BlockBox leftHall = translate(centerBox, -(ROOM_W / 2 + HALL_LEN), 0, 0);
        carveHall(world, rand, leftHall, Direction.WEST);
        BlockBox leftRoom = translate(centerBox, dx, 0, 0);
        buildRoom(world, rand, leftRoom, false, trapMarkers);

        // 3) Sağ oda + koridor
        int sx = (facing == Direction.EAST ? (ROOM_W / 2 + HALL_LEN) : (ROOM_W / 2 + HALL_LEN));
        BlockBox rightHall = translate(centerBox, sx, 0, 0);
        carveHall(world, rand, rightHall, Direction.EAST);
        BlockBox rightRoom = translate(centerBox, ROOM_W / 2 + HALL_LEN + ROOM_W, 0, 0);
        buildRoom(world, rand, rightRoom, false, trapMarkers);

        // 4) Arka oda (merkez salonun arkasına)
        BlockBox backHall = translate(centerBox, 0, 0, ROOM_D / 2 + HALL_LEN);
        carveHall(world, rand, backHall, Direction.SOUTH);
        BlockBox backRoom = translate(centerBox, 0, 0, ROOM_D / 2 + HALL_LEN + ROOM_D);
        buildRoom(world, rand, backRoom, false, trapMarkers);

        // 5) Merkez sunak + sütunlar + özel cevher
        buildBossPlatform(world, rand, centerBox, trapMarkers);

        // 6) Dekoratif sarmaşık, yosun, tavan çökmesi efektleri
        decayPass(world, rand, centerBox);
        decayPass(world, rand, leftRoom);
        decayPass(world, rand, rightRoom);
        decayPass(world, rand, backRoom);

        // 7) Bazı yaratıklar (spawner yerine güvenli canlı spawn)
        if (world instanceof ServerWorld sw) {
            spawnMobs(sw, centerBox, rand, EntityType.ZOMBIE, 3);
            spawnMobs(sw, leftRoom, rand, EntityType.SKELETON, 2);
            spawnMobs(sw, rightRoom, rand, EntityType.ZOMBIE, 2);
            spawnMobs(sw, backRoom, rand, EntityType.SKELETON, 2);
        }


        return true;
    }

    /* ---------- İnşa Yardımcıları ---------- */

    private void buildRoom(WorldAccess world, Random rand, BlockBox box, boolean isCenter, List<BlockPos> traps) {
        // Temizle
        fill(world, box, Blocks.AIR);

        // Zemin + tavan + duvarlar
        // Zemin
        forXY(box, (x, z) -> {
            BlockPos p = new BlockPos(x, box.getMinY(), z);
            setPalette(world, rand, p, true);
        });
        // Tavan
        forXY(box, (x, z) -> {
            BlockPos p = new BlockPos(x, box.getMaxY(), z);
            setPalette(world, rand, p, false);
        });
        // Duvarlar
        forYZ(box, (y, z) -> {
            setPalette(world, rand, new BlockPos(box.getMinX(), y, z), false);
            setPalette(world, rand, new BlockPos(box.getMaxX(), y, z), false);
        });
        forXYVertical(box, (x, y) -> {
            setPalette(world, rand, new BlockPos(x, y, box.getMinZ()), false);
            setPalette(world, rand, new BlockPos(x, y, box.getMaxZ()), false);
        });

        // Köşe sütunları
        cornerPillar(world, rand, new BlockPos(box.getMinX()+1, box.getMinY()+1, box.getMinZ()+1), box.getMaxY()-1);
        cornerPillar(world, rand, new BlockPos(box.getMaxX()-1, box.getMinY()+1, box.getMinZ()+1), box.getMaxY()-1);
        cornerPillar(world, rand, new BlockPos(box.getMinX()+1, box.getMinY()+1, box.getMaxZ()-1), box.getMaxY()-1);
        cornerPillar(world, rand, new BlockPos(box.getMaxX()-1, box.getMinY()+1, box.getMaxZ()-1), box.getMaxY()-1);

        // Kapı boşlukları (merkez yönlere bakan duvarlarda)
        carveDoor(world, box, Direction.EAST);
        carveDoor(world, box, Direction.WEST);
        carveDoor(world, box, Direction.NORTH);
        carveDoor(world, box, Direction.SOUTH);

        // Odaiçi sütunlar ve küçük merdivenler
        internalDetails(world, rand, box);

        // Tuzak markerları (LODESTONE)
        // - Zemin basınç plakası/ TNT/ok tuzağı için ideal noktalar
        addTrapMarker(world, box, traps, box.getCenter().add(0, 0, -2));
        addTrapMarker(world, box, traps, box.getCenter().add(0, 0, +2));
        // - Tavan düşürme/ok dispenser tuzağı ankrajı
        addTrapMarker(world, box, traps, box.getCenter().add(+3, +3, 0));
        addTrapMarker(world, box, traps, box.getCenter().add(-3, +3, 0));

        // Merkez salona küçük bir yükseltilmiş platform (estetik)
        if (isCenter) {
            BlockPos c = box.getCenter();
            ring(world, c.add(0, 0, 0), 3, bState(Blocks.POLISHED_ANDESITE));
            set(world, c, bState(Blocks.CHISELED_STONE_BRICKS));
        }
    }

    private void carveHall(WorldAccess world, Random rand, BlockBox hallBox, Direction dir) {
        // Hall: 3 genişlik x 3 yükseklik, solid duvar+tavan zemini
        // Önce hepsini doldur
        fill(world, hallBox, Blocks.AIR);

        // Zemin / tavan / yan duvarlara palet uygula
        forXY(hallBox, (x, z) -> setPalette(world, rand, new BlockPos(x, hallBox.getMinY(), z), true));
        forXY(hallBox, (x, z) -> setPalette(world, rand, new BlockPos(x, hallBox.getMaxY(), z), false));
        forYZ(hallBox, (y, z) -> {
            setPalette(world, rand, new BlockPos(hallBox.getMinX(), y, z), false);
            setPalette(world, rand, new BlockPos(hallBox.getMaxX(), y, z), false);
        });
        forXYVertical(hallBox, (x, y) -> {
            setPalette(world, rand, new BlockPos(x, y, hallBox.getMinZ()), false);
            setPalette(world, rand, new BlockPos(x, y, hallBox.getMaxZ()), false);
        });

        // Kemer efekti: tepe ortasına değişik doku
        BlockPos mid = hallBox.getCenter().up(1);
        set(world, mid, bState(Blocks.CHISELED_STONE_BRICKS));
    }

    private void buildBossPlatform(WorldAccess world, Random rand, BlockBox centerBox, List<BlockPos> traps) {
        BlockPos c = centerBox.getCenter();
        int h = centerBox.getMinY();

        // Sunak sütunu (5 blok)
        for (int i = 0; i < 2; i++) {
            BlockPos p = c.up(i);
            BlockState s = switch (i) {
                case 0 -> Blocks.DEEPSLATE_BRICKS.getDefaultState();
                default -> Blocks.CHISELED_POLISHED_BLACKSTONE.getDefaultState();
            };
            set(world, p, s);
        }

        // Üst platform 3x3, ortası özel cevher
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue;
                set(world, c.up(2).add(dx, 0, dz), Blocks.POLISHED_ANDESITE.getDefaultState());
            }
        }
        // Senin özel cevherin
        set(world, c.up(3), BlockType.CRUDE_ACIDIC_ORE.getDefaultState());

        // Platform çevresine ok tuzağı ankrajı (marker)
        addTrapMarker(world, centerBox, traps, c.up(3).add(+4, 0, 0));
        addTrapMarker(world, centerBox, traps, c.up(3).add(-4, 0, 0));
        addTrapMarker(world, centerBox, traps, c.up(3).add(0, 0, +4));
        addTrapMarker(world, centerBox, traps, c.up(3).add(0, 0, -4));

        // Merdiven (4 yönden)
        for (Direction d : Direction.Type.HORIZONTAL) {
            BlockPos stepBase = c.add(d.getOffsetX() * 2, 0, d.getOffsetZ() * 2);
            set(world, stepBase, Blocks.STONE_BRICK_SLAB.getDefaultState());
            set(world, stepBase.add(d.getOffsetX(), 0, d.getOffsetZ()), Blocks.STONE_BRICK_STAIRS.getDefaultState().with(Properties.HORIZONTAL_FACING, d));
        }
    }

    private void internalDetails(WorldAccess world, Random rand, BlockBox box) {
        // Oda içi 4 adet kısa sütun
        BlockPos c = box.getCenter();
        int topY = box.getMinY() + 3;
        BlockPos[] points = new BlockPos[]{
                c.add(+2, 1, +2), c.add(-2, 1, +2),
                c.add(+2, 1, -2), c.add(-2, 1, -2)
        };
        for (BlockPos p : points) {
            set(world, p, Blocks.STONE_BRICKS.getDefaultState());
            set(world, p.up(1), Blocks.CRACKED_STONE_BRICKS.getDefaultState());
            set(world, p.up(2), Blocks.CHISELED_STONE_BRICKS.getDefaultState());
            // tavan bağlantısı
            set(world, new BlockPos(p.getX(), Math.min(topY, box.getMaxY()-1), p.getZ()), Blocks.STONE_BRICK_WALL.getDefaultState());
        }
    }

    private void decayPass(WorldAccess world, Random rand, BlockBox box) {
        // Yosun, sarmaşık, bazı yerleri kırık gibi görünür yap
        int tries = (ROOM_W * ROOM_D) / 2;
        for (int i = 0; i < tries; i++) {
            int x = rand.nextBetween(box.getMinX(), box.getMaxX());
            int y = rand.nextBetween(box.getMinY()+1, box.getMaxY()-1);
            int z = rand.nextBetween(box.getMinZ(), box.getMaxZ());
            BlockPos p = new BlockPos(x, y, z);

            if (rand.nextFloat() < 0.12f && world.getBlockState(p).isAir() && world.getBlockState(p.down()).isOpaque()) {
                // yer yer moss/fern
                BlockState s = rand.nextBoolean() ? Blocks.MOSS_CARPET.getDefaultState() : Blocks.AIR.getDefaultState();
                set(world, p, s);
            }
            if (rand.nextFloat() < 0.06f) {
                // dış duvar yüzeylerine vine
                if (isExposedWall(world, p)) {
                    set(world, p, Blocks.VINE.getDefaultState());
                }
            }
        }
    }

    private boolean isExposedWall(WorldAccess world, BlockPos p) {
        BlockState s = world.getBlockState(p);
        if (!s.isAir()) return false;
        for (Direction d : Direction.values()) {
            if (world.getBlockState(p.offset(d)).isOpaque()) return true;
        }
        return false;
    }

    /* ---------- Primitifler ---------- */

    private void setPalette(WorldAccess world, Random rand, BlockPos p, boolean floor) {
        float r = rand.nextFloat();
        BlockState s;
        if (floor) {
            if (r < 0.10f) s = Blocks.MOSS_BLOCK.getDefaultState();
            else if (r < 0.22f) s = Blocks.MOSSY_COBBLESTONE.getDefaultState();
            else if (r < 0.34f) s = Blocks.COBBLESTONE.getDefaultState();
            else if (r < 0.50f) s = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
            else s = Blocks.STONE_BRICKS.getDefaultState();
        } else {
            if (r < 0.15f) s = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
            else if (r < 0.30f) s = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
            else s = Blocks.STONE_BRICKS.getDefaultState();
        }
        set(world, p, s);
    }

    private void ring(WorldAccess world, BlockPos center, int radius, BlockState state) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz <= radius * radius && dx * dx + dz * dz >= (radius - 1) * (radius - 1)) {
                    set(world, center.add(dx, 0, dz), state);
                }
            }
        }
    }

    private void cornerPillar(WorldAccess world, Random rand, BlockPos base, int topY) {
        for (int y = base.getY(); y <= topY; y++) {
            BlockState s = rand.nextBoolean() ? Blocks.STONE_BRICKS.getDefaultState() : Blocks.CRACKED_STONE_BRICKS.getDefaultState();
            set(world, new BlockPos(base.getX(), y, base.getZ()), s);
        }
        set(world, new BlockPos(base.getX(), topY + 1, base.getZ()), Blocks.STONE_BRICK_WALL.getDefaultState());
    }

    private void carveDoor(WorldAccess world, BlockBox box, Direction dir) {
        // Duvar ortasında 3x3 boşluk
        int midX = (box.getMinX() + box.getMaxX()) / 2;
        int midZ = (box.getMinZ() + box.getMaxZ()) / 2;
        int y0 = box.getMinY() + 1;
        switch (dir) {
            case EAST -> {
                for (int y = y0; y < y0 + 3; y++)
                    for (int z = midZ - 1; z <= midZ + 1; z++)
                        set(world, new BlockPos(box.getMaxX(), y, z), Blocks.AIR.getDefaultState());
            }
            case WEST -> {
                for (int y = y0; y < y0 + 3; y++)
                    for (int z = midZ - 1; z <= midZ + 1; z++)
                        set(world, new BlockPos(box.getMinX(), y, z), Blocks.AIR.getDefaultState());
            }
            case SOUTH -> {
                for (int y = y0; y < y0 + 3; y++)
                    for (int x = midX - 1; x <= midX + 1; x++)
                        set(world, new BlockPos(x, y, box.getMaxZ()), Blocks.AIR.getDefaultState());
            }
            case NORTH -> {
                for (int y = y0; y < y0 + 3; y++)
                    for (int x = midX - 1; x <= midX + 1; x++)
                        set(world, new BlockPos(x, y, box.getMinZ()), Blocks.AIR.getDefaultState());
            }
        }
    }

    private void addTrapMarker(WorldAccess world, BlockBox scope, List<BlockPos> out, BlockPos pos) {
        if (!scope.contains(pos)) return;
        set(world, pos, BlockType.PVP_SPAWNER.getDefaultState()); // MARKER: burayı tuzak merkezi olarak kullan
        out.add(pos.toImmutable());
    }

    private void spawnMobs(ServerWorld sw, BlockBox box, Random rand, EntityType<?> type, int count) {
        for (int i = 0; i < count; i++) {
            int x = rand.nextBetween(box.getMinX() + 1, box.getMaxX() - 1);
            int z = rand.nextBetween(box.getMinZ() + 1, box.getMaxZ() - 1);
            BlockPos p = new BlockPos(x, box.getMinY() + 1, z);
            if (type == EntityType.ZOMBIE) {
                ZombieEntity e = new ZombieEntity(EntityType.ZOMBIE, sw);
                e.refreshPositionAndAngles(p, 0, 0);
                sw.spawnEntity(e);
            } else if (type == EntityType.SKELETON) {
                SkeletonEntity e = new SkeletonEntity(EntityType.SKELETON, sw);
                e.refreshPositionAndAngles(p, 0, 0);
                sw.spawnEntity(e);
            }
        }
    }

    /* ---------- Düşük seviye yardımcılar ---------- */

    private void set(WorldAccess world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
    }

    private void fill(WorldAccess world, BlockBox box, Block block) {
        BlockState s = block.getDefaultState();
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int y = box.getMinY(); y <= box.getMaxY(); y++) {
                for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                    world.setBlockState(new BlockPos(x, y, z), s, Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
                }
            }
        }
    }

    private void forXY(BlockBox box, XY consumer) {
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                consumer.apply(x, z);
            }
        }
    }

    private void forYZ(BlockBox box, YZ consumer) {
        for (int y = box.getMinY(); y <= box.getMaxY(); y++) {
            for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                consumer.apply(y, z);
            }
        }
    }

    private void forXYVertical(BlockBox box, XY consumer) {
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int y = box.getMinY(); y <= box.getMaxY(); y++) {
                consumer.apply(x, y);
            }
        }
    }

    private BlockBox translate(BlockBox src, int dx, int dy, int dz) {
        return new BlockBox(
                src.getMinX() + dx, src.getMinY() + dy, src.getMinZ() + dz,
                src.getMaxX() + dx, src.getMaxY() + dy, src.getMaxZ() + dz
        );
    }

    private BlockState bState(Block b) {
        return b.getDefaultState();
    }

    /* ---------- küçük functional arayüzler ---------- */
    @FunctionalInterface private interface XY { void apply(int x, int z); }
    @FunctionalInterface private interface YZ { void apply(int y, int z); }
}
