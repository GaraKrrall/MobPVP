package mc.garakrral.entity.mob;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mc.garakrral.entity.goal.FollowAndProtectGolemGoal;
import mc.garakrral.sound.SoundType;

public class MiniCopperGolemEntity extends IronGolemEntity {

    public MiniCopperGolemEntity(EntityType<? extends IronGolemEntity> type, World world) {
        super(type, world);
        this.setPlayerCreated(false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return IronGolemEntity.createIronGolemAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);

    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new FollowAndProtectGolemGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.6D));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundType.WALK_COPPER, 0.15F, 1.0F);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isOf(Items.COPPER_INGOT)) {
            return ActionResult.PASS;
        } else {
            float f = this.getHealth();
            this.heal(25.0F);
            if (this.getHealth() == f) {
                return ActionResult.PASS;
            } else {
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, g);
                itemStack.decrementUnlessCreative(1, player);
                return ActionResult.success(this.getWorld().isClient);
            }
        }
    }
}
