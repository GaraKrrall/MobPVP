package com.kaplandev.client.renderer.entity.block;

import com.kaplandev.block.BlockType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class CustomPistonRenderer implements BlockEntityRenderer<PistonBlockEntity> {

    private final BlockRenderManager manager;

    public CustomPistonRenderer(BlockRenderManager manager) {
        this.manager = manager;
    }

    @Override
    public void render(PistonBlockEntity piston, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        System.out.println("CustomPistonRenderer called for " + piston.getPos());

        World world = piston.getWorld();
        if (world == null) return;

        BlockState state = piston.getPushedBlock();
        if (state.isAir()) return;

        Direction dir = piston.getMovementDirection();
        BlockPos pos = piston.getPos().offset(dir.getOpposite());
        BlockPos frontPos = piston.getPos().offset(dir);

        boolean hasHeavyHead = world.getBlockState(frontPos).isOf(BlockType.HEAVY_CRUSHER_HEAD);

        matrices.push();
        matrices.translate(
                piston.getRenderOffsetX(tickDelta),
                piston.getRenderOffsetY(tickDelta),
                piston.getRenderOffsetZ(tickDelta)
        );

        // Eğer önünde ağır başlık varsa pressed versiyonlarını kullan
        if (hasHeavyHead) {
            renderPressedPiston(world, state, pos, matrices, vertexConsumers, light, overlay);
        } else {
            renderNormalPiston(world, state, pos, matrices, vertexConsumers, light, overlay);
        }

        matrices.pop();
    }

    private void renderNormalPiston(World world, BlockState state, BlockPos pos,
                                    MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                    int light, int overlay) {
        RenderLayer layer = RenderLayers.getMovingBlockLayer(state);
        manager.getModelRenderer().render(
                world,
                manager.getModel(state),
                state,
                pos,
                matrices,
                vertexConsumers.getBuffer(layer),
                false,
                Random.create(),
                state.getRenderingSeed(pos),
                overlay
        );
    }

    private void renderPressedPiston(World world, BlockState state, BlockPos pos,
                                     MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                     int light, int overlay) {
        // vanilla piston texture’larını çekiyoruz
        Identifier texBase = Identifier.of("minecraft", "block/piston_");
        Identifier texPressedBase = Identifier.of("mobpvp", "textures/block/piston/");

        // pressed piston modeli render (vanilla modelle aynı geometriyi kullanıyor)
        RenderLayer layer = RenderLayers.getMovingBlockLayer(state);
        var buffer = vertexConsumers.getBuffer(layer);

        // Modeli çek
        var model = manager.getModel(state);

        // 🔥 Texture override
        MinecraftClient.getInstance().getTextureManager().bindTexture(
                Identifier.of("mobpvp", "textures/block/piston/piston_top_pressed.png")
        );

        // Vanilla render
        manager.getModelRenderer().render(
                world,
                model,
                state,
                pos,
                matrices,
                buffer,
                false,
                Random.create(),
                state.getRenderingSeed(pos),
                overlay
        );
    }

    @Override
    public int getRenderDistance() {
        return 68;
    }
}
