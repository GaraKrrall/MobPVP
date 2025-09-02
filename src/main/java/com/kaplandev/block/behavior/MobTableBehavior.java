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
    protected ActionResult onUse(BlockState state, World world, BlockPos pos,
                                 PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            // İstersen istatistik de tutabilirsin
            // player.incrementStat(Stats.CUSTOM.get(MY_CUSTOM_STAT));
            return ActionResult.CONSUME;
        }
    }

    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state,
                                                                   World world,
                                                                   BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) ->
                        new MobTableScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)),
                TITLE
        );
    }
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
        if (!world.isClient) Block.dropStack(world, pos, new ItemStack(Items.HEARTH_PART));
    }
}