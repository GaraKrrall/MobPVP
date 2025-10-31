package mc.garakrral.mixin;

import mc.garakrral.block.BlockType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.block.entity.PistonBlockEntity;

@Mixin(PistonBlockEntity.class)
public class PistonHandlerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private static void onPistonTick(World world, BlockPos pos, BlockState state, PistonBlockEntity piston, CallbackInfo ci) {
        if (world.isClient || !piston.isExtending()) return;

        Direction dir = piston.getFacing();
        BlockPos headPos = pos.offset(dir);
        BlockState headState = world.getBlockState(headPos);

        // Eğer piston Heavy Crusher Head'i ittiriyorsa
        if (headState.isOf(BlockType.HEAVY_CRUSHER_HEAD)) {
            BlockPos targetPos = headPos.offset(dir);
            BlockState target = world.getBlockState(targetPos);

            if (target.isOf(Blocks.IRON_BLOCK)) {
                world.breakBlock(targetPos, false);
                world.playSound(null, targetPos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1.0f, 1.0f);

                ItemEntity drop = new ItemEntity((ServerWorld) world,
                        targetPos.getX() + 0.5,
                        targetPos.getY() + 0.5,
                        targetPos.getZ() + 0.5,
                        new ItemStack(Items.IRON_NUGGET, 3));
                world.spawnEntity(drop);
            }
        }
    }
}
