package com.kaplandev.block.behavior;


import com.kaplandev.handler.MobTableScreenHandler;
import com.kaplandev.item.Items;
import com.kaplanlib.api.behavior.BlockBehavior;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MobTableBehavior extends Block implements BlockBehavior {
    private static final Text TITLE = Text.literal("Mob Table");

    public MobTableBehavior(AbstractBlock.Settings settings) {
        super(settings);
    }



    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
        if (!world.isClient) Block.dropStack(world, pos, new ItemStack(Items.HEARTH_PART));
    }
}