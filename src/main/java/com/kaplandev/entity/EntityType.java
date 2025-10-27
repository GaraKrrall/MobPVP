package com.kaplandev.entity;

//import com.kaplandev.entity.block.IronChestBlockEntity;
import com.kaplandev.entity.block.MobTableBlockEntity;
import com.kaplandev.entity.block.PvpSpawnerBlockEntity;
import com.kaplandev.entity.block.PvpSpawnerMaxBlockEntity;
import com.kaplandev.entity.boss.BulwarkEntity;
import com.kaplandev.entity.item.IronReinforcedCopperBallEntity;
import com.kaplandev.entity.mob.MadSkeletonEntity;
import com.kaplandev.entity.mob.MadZombieEntity;
import com.kaplandev.entity.mob.MiniCopperGolemEntity;
import com.kaplandev.entity.passive.MiniIronGolemEntity;

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
    public static net.minecraft.entity.EntityType<IronReinforcedCopperBallEntity> IRON_REINFORCED_COPPER_BALL;
  //  public static BlockEntityType<IronChestBlockEntity> IRON_CHEST;
    public static void touch(){}
}
