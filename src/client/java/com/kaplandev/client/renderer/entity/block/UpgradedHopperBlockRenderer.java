package com.kaplandev.client.renderer.entity.block;

import com.kaplandev.entity.block.UpgradedHopperBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UpgradedHopperBlockRenderer implements BlockEntityRenderer<UpgradedHopperBlockEntity> {

    private final BlockRenderManager blockRenderManager;

    public UpgradedHopperBlockRenderer(BlockEntityRendererFactory.Context context) {
        this.blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
    }

    @Override
    public void render(
            UpgradedHopperBlockEntity entity,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {
        World world = entity.getWorld();
        if (world == null) return;

        // Hopper’ın pozisyonu
        BlockPos pos = entity.getPos();

        // Vanilla hopper state
        var hopperState = Blocks.HOPPER.getDefaultState();

        // Matrix stack’e world pozisyonunu uygula
        matrices.push();
        matrices.translate(0.0, 0.0, 0.0); // merkezde render

        // Vanilla hopper modelini çiz
        blockRenderManager.renderBlockAsEntity(
                hopperState,
                matrices,
                vertexConsumers,
                light,
                overlay
        );

        matrices.pop();
    }
}
