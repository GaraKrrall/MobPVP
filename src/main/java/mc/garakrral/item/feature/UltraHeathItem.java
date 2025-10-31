package mc.garakrral.item.feature;

import mc.garakrral.block.BlockType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UltraHeathItem extends Item {
    public UltraHeathItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) return TypedActionResult.pass(player.getStackInHand(hand));

        ItemStack stack = player.getStackInHand(hand);
        BlockPos center = player.getBlockPos();

        // --- 0. 9x9 sıvı kontrolü ---
        int fluidRadius = 4; // 4 blok sağa-sola → toplam 9x9 alan
        for (int fx = -fluidRadius; fx <= fluidRadius; fx++) {
            for (int fz = -fluidRadius; fz <= fluidRadius; fz++) {
                BlockPos checkPos = center.add(fx, -1, fz);
                if (world.getFluidState(checkPos).isIn(net.minecraft.registry.tag.FluidTags.WATER)
                        || world.getFluidState(checkPos).isIn(net.minecraft.registry.tag.FluidTags.LAVA)) {
                    player.sendMessage(Text.translatable("error.mobpvp.block"), true);
                    return TypedActionResult.fail(stack);
                }
            }
        }

        // --- 1. 5x5 alan UYGUNLUK kontrolü ---
        int size = 2;
        for (int dx = -size; dx <= size; dx++) {
            for (int dz = -size; dz <= size; dz++) {
                BlockPos ground = center.add(dx, -1, dz);   // parke gelecek yer
                BlockPos above = ground.up();               // oyuncunun ayağı
                BlockPos above2 = ground.up(2);             // oyuncunun kafası

                // Alt blok hava olamaz ve katı olmalı
                if (world.getBlockState(ground).isAir()
                        || !world.getBlockState(ground).isSolidBlock(world, ground)) {
                    player.sendMessage(Text.translatable("error.mobpvp.block"), true);
                    return TypedActionResult.fail(stack);
                }

                // Oyuncunun ayağı/kafası dolu olamaz
                if (!world.getBlockState(above).isAir() || !world.getBlockState(above2).isAir()) {
                    player.sendMessage(Text.translatable("error.mobpvp.alan"), true);
                    return TypedActionResult.fail(stack);
                }
            }
        }

        // --- 2. 5x5 alanı parke ile değiştir ---
        for (int dx = -size; dx <= size; dx++) {
            for (int dz = -size; dz <= size; dz++) {
                BlockPos pos = center.add(dx, -1, dz);
                world.setBlockState(pos, net.minecraft.block.Blocks.CUT_COPPER.getDefaultState());
            }
        }

        // --- 3. Köşelere yapılar yerleştir ---
        int offset = 7; // 5–10 arası
        int[][] corners = {
                { offset, 0, offset},
                {-offset, 0, offset},
                { offset, 0,-offset},
                {-offset, 0,-offset}
        };

        for (int[] c : corners) {
            BlockPos structureCenter = center.add(c[0], c[1], c[2]);
            placeStructure(world, structureCenter);
        }

        // Ses ve item tüketme
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE, player.getSoundCategory(), 1f, 1f);
        stack.decrement(1);
        return TypedActionResult.success(stack);
    }


    // Küçük yapı yerleştirme metodu
    private void placeStructure(World world, BlockPos center) {
        // Basit 3x3 taban
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos pos = center.add(dx, -1, dz);
                world.setBlockState(pos, net.minecraft.block.Blocks.CUT_COPPER.getDefaultState());
            }
        }

        // Ortada PVP Spawner olsun
        world.setBlockState(center, BlockType.PVP_SPAWNER.getDefaultState());

        // %25 şansla üstte 2 süper blok
        if (world.random.nextFloat() < 0.25f) {
            world.setBlockState(center.up(), BlockType.PVP_SPAWNER_MAX.getDefaultState());
        }
    }


}
