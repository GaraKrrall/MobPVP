package mc.garakrral.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mc.garakrral.entity.goal.FindIngotAndHealGolemGoal;
import mc.garakrral.sound.SoundType;

public class MiniIronGolemEntity extends IronGolemEntity {

    public MiniIronGolemEntity(EntityType<? extends IronGolemEntity> type, World world) {
        super(type, world);
        this.setPlayerCreated(true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return IronGolemEntity.createIronGolemAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D);

    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FindIngotAndHealGolemGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.6D));
        this.goalSelector.add(3, new LookAroundGoal(this));
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundType.WALK_IRON, 0.15F, 1.0F);
    }
}
