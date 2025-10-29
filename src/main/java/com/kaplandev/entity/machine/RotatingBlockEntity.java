package com.kaplandev.entity.machine;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*public class RotatingBlockEntity extends Entity {
    private float rotation = 0f;

    public RotatingBlockEntity(EntityType<? extends RotatingBlockEntity> type, World world) {
        super(type, world);
        this.noClip = true;
    }

    // Kolay kullanım için ekstra constructor
    public RotatingBlockEntity(World world, BlockPos pos, net.minecraft.block.BlockState state) {
        this(com.kaplandev.entity.EntityType.ROTATING_BLOCK, world);
        this.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    @Override
    public void tick() {
        super.tick();
        rotation += 5f;
        if (rotation >= 360f) rotation = 0f;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {}

    @Override
    public boolean shouldSave() {
        // ❌ Kaydedilmesin (dünya kapanınca yeniden oluşturulacak)
        return false;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(1f, 1f);
    }
}
*/