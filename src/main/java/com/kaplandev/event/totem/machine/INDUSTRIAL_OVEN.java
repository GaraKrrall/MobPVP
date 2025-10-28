package com.kaplandev.event.totem.machine;

import com.kaplandev.block.BlockType;
import com.kaplandev.scheduler.ServerScheduler;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class INDUSTRIAL_OVEN {

    public static ActionResult onUseBlock(net.minecraft.entity.player.PlayerEntity player, World world, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.PASS;

        BlockPos clickedPos = hit.getBlockPos();
        BlockState state = world.getBlockState(clickedPos);

        // 🔒 sadece bizim bloğumuz
        if (!state.isOf(BlockType.INDUSTRIAL_OVEN)) {
            return ActionResult.PASS;
        }

        ServerWorld serverWorld = (ServerWorld) world;

        System.out.println("[INDUSTRIAL_OVEN] Oyuncu HEAVY_CRUSHER_HEAD bloğuna tıkladı: " + clickedPos);

        // Sandık yönü oyuncunun baktığı yönün tersinde
        BlockPos chestPos = clickedPos.offset(player.getHorizontalFacing().getOpposite());
        BlockState chestState = world.getBlockState(chestPos);

        if (!chestState.isOf(Blocks.CHEST)) {
            System.out.println("[INDUSTRIAL_OVEN] HATA: Sandık doğru konumda değil (" + chestState.getBlock() + ")");
            return ActionResult.PASS;
        }

        if (!isStructureValid(serverWorld, clickedPos, chestPos)) {
            System.out.println("[INDUSTRIAL_OVEN] Yapı geçersiz ❌");
            return ActionResult.PASS;
        }

        System.out.println("[INDUSTRIAL_OVEN] Yapı geçerli ✅");

        ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(chestPos);
        if (chest == null) {
            System.out.println("[INDUSTRIAL_OVEN] HATA: Sandık entity bulunamadı!");
            return ActionResult.PASS;
        }

        boolean hasIron = hasItem(chest, Items.IRON_INGOT);
        System.out.println("[INDUSTRIAL_OVEN] Sandıkta demir var mı? " + hasIron);

        if (!hasIron) {
            spawnParticles(serverWorld, chestPos, false, 40, 3); // duman efekti
            world.playSound(null, clickedPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
            return ActionResult.SUCCESS;
        }

        // 🔥 üretim başladı
        world.playSound(null, clickedPos, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1f, 1f);
        System.out.println("[INDUSTRIAL_OVEN] Üretim başladı... 10 saniye boyunca partikül patlaması!");

        // 10 saniye boyunca yoğun partikül efekti
        for (int i = 0; i < 200; i += 5) {
            int delay = i;
            ServerScheduler.schedule(delay, () -> spawnParticles(serverWorld, clickedPos, true, 20, 5));
        }

        // 10 saniye sonra dönüşüm
        ServerScheduler.schedule(200, () -> completeCraft(serverWorld, chest, clickedPos));
        return ActionResult.SUCCESS;
    }

    private static void completeCraft(ServerWorld world, ChestBlockEntity chest, BlockPos pos) {
        if (!hasItem(chest, Items.IRON_INGOT)) {
            System.out.println("[INDUSTRIAL_OVEN] Demir kalmadı, işlem iptal.");
            return;
        }

        removeItem(chest, Items.IRON_INGOT);
        chest.setStack(0, new ItemStack(Items.GOLDEN_APPLE));

        spawnParticles(world, pos, false, 50, 6);
        world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1f, 1.2f);
        System.out.println("[INDUSTRIAL_OVEN] Üretim tamamlandı ✅ Demir -> Altın Elma");
    }

    private static boolean isStructureValid(ServerWorld world, BlockPos headPos, BlockPos chestPos) {
        for (Direction dir : Direction.values()) {
            BlockPos checkPos = headPos.offset(dir);
            BlockState checkState = world.getBlockState(checkPos);

            if (checkPos.equals(chestPos)) continue;
            if (!checkState.isOf(BlockType.INDUSTRIAL_OVEN_BLOCK)) {
                System.out.println("[INDUSTRIAL_OVEN] Hatalı blok: " + checkPos + " -> " + checkState.getBlock());
                return false;
            }
        }
        return true;
    }

    private static boolean hasItem(ChestBlockEntity chest, net.minecraft.item.Item item) {
        for (int i = 0; i < chest.size(); i++) {
            if (chest.getStack(i).isOf(item)) return true;
        }
        return false;
    }

    private static void removeItem(ChestBlockEntity chest, net.minecraft.item.Item item) {
        for (int i = 0; i < chest.size(); i++) {
            ItemStack stack = chest.getStack(i);
            if (stack.isOf(item)) {
                stack.decrement(1);
                chest.markDirty();
                return;
            }
        }
    }

    private static void spawnParticles(ServerWorld world, BlockPos pos, boolean fire, int count, double spread) {
        var type = fire ? ParticleTypes.FLAME : ParticleTypes.SMOKE;
        for (int i = 0; i < count; i++) {
            double x = pos.getX() + 0.5 + (Math.random() - 0.5) * spread;
            double y = pos.getY() + 0.5 + (Math.random() - 0.5) * spread;
            double z = pos.getZ() + 0.5 + (Math.random() - 0.5) * spread;
            world.spawnParticles(type, x, y, z, 40, 0.1, 0.1, 0.1, 0.02);
        }
    }
}
