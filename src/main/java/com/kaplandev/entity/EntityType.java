package com.kaplandev.entity;

import com.kaplandev.entity.boss.BulwarkEntity;
import com.kaplandev.entity.mob.MadSkeletonEntity;
import com.kaplandev.entity.mob.MadZombieEntity;
import com.kaplandev.entity.passive.MiniIronGolemEntity;

public class EntityType {
    public static net.minecraft.entity.EntityType<MadZombieEntity> MAD_ZOMBIE;
    public static net.minecraft.entity.EntityType<MadSkeletonEntity> MAD_SKELETON;
    public static net.minecraft.entity.EntityType<BulwarkEntity> BULWARK;
    public static net.minecraft.entity.EntityType<MiniIronGolemEntity> MINIGOLEM;
}
