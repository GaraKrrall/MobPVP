package mc.garakrral.entity;

import mc.garakrral.entity.block.MobTableBlockEntity;
import mc.garakrral.entity.block.PvpSpawnerBlockEntity;
import mc.garakrral.entity.block.PvpSpawnerMaxBlockEntity;
import mc.garakrral.entity.block.UpgradedHopperBlockEntity;
import mc.garakrral.entity.boss.BulwarkEntity;
import mc.garakrral.entity.item.IronReinforcedCopperBallEntity;
import mc.garakrral.entity.mob.MadSkeletonEntity;
import mc.garakrral.entity.mob.MadZombieEntity;
import mc.garakrral.entity.mob.MiniCopperGolemEntity;
import mc.garakrral.entity.passive.MiniIronGolemEntity;

import net.minecraft.block.entity.BlockEntityType;

public class EntityType {
    public static net.minecraft.entity.EntityType<MadZombieEntity> MAD_ZOMBIE;
    public static net.minecraft.entity.EntityType<MadSkeletonEntity> MAD_SKELETON;
    public static net.minecraft.entity.EntityType<BulwarkEntity> BULWARK;
    public static net.minecraft.entity.EntityType<MiniIronGolemEntity> MINIGOLEM;
    public static net.minecraft.entity.EntityType<MiniCopperGolemEntity> MINIGOLEM_COPPER;
    public static BlockEntityType<PvpSpawnerBlockEntity> PVP_SPAWNER;
    public static BlockEntityType<PvpSpawnerMaxBlockEntity> PVP_SPAWNER_MAX;
    public static BlockEntityType<MobTableBlockEntity> MOB_TABLE;
    public static BlockEntityType<UpgradedHopperBlockEntity> UPGREADED_HOPPER;
    public static net.minecraft.entity.EntityType<IronReinforcedCopperBallEntity> IRON_REINFORCED_COPPER_BALL;
}
