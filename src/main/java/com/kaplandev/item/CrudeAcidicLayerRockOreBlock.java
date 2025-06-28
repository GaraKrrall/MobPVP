package com.kaplandev.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrudeAcidicLayerRockOreBlock extends Block {

    public CrudeAcidicLayerRockOreBlock(Settings settings) {
        super(settings);
    }

    //@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Sadece mobpvp:kalp ile tıklanınca zombi çıkar
        Item usedItem = player.getStackInHand(hand).getItem();
        Item kalp = Registries.ITEM.get(Identifier.of("mobpvp", "key"));

        if (!world.isClient && usedItem == kalp) {
            ZombieEntity zombie = EntityType.ZOMBIE.create((ServerWorld) world);
            if (zombie != null) {
                zombie.refreshPositionAndAngles(pos.up(), 0.0F, 0.0F);
                world.spawnEntity(zombie);
                world.breakBlock(pos, false); // blok yok olsun
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}

