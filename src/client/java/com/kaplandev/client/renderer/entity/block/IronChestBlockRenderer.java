package com.kaplandev.client.renderer.entity.block;

/*import com.kaplandev.entity.block.IronChestBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;

import static com.kaplandev.mobpvp.MOD_ID;
import static com.kaplandev.util.path.Paths.IRON_CHEST_TEXTURE;

@Environment(EnvType.CLIENT)
public class IronChestBlockRenderer implements BlockEntityRenderer<IronChestBlockEntity> {
    private final MinecraftClient client = MinecraftClient.getInstance();

    // Model parçaları (tek/double vs)
    private final ModelPart singleChestLid;
    private final ModelPart singleChestBase;
    private final ModelPart singleChestLatch;
    private final ModelPart doubleChestLeftLid;
    private final ModelPart doubleChestLeftBase;
    private final ModelPart doubleChestLeftLatch;
    private final ModelPart doubleChestRightLid;
    private final ModelPart doubleChestRightBase;
    private final ModelPart doubleChestRightLatch;
    private boolean christmas;

    public IronChestBlockRenderer(BlockEntityRendererFactory.Context ctx) {
        // İsteğe bağlı: Noel teması örneği (aynı vanilla gibi)
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if (calendar.get(java.util.Calendar.MONTH) + 1 == 12 &&
                calendar.get(java.util.Calendar.DAY_OF_MONTH) >= 24 &&
                calendar.get(java.util.Calendar.DAY_OF_MONTH) <= 26) {
            this.christmas = true;
        }

        // Burada vanilla layer'ları kullanıyoruz. Eğer özel layer tanımladıysan onu buraya koy.
        ModelPart chestSingle = ctx.getLayerModelPart(EntityModelLayers.CHEST);
        this.singleChestBase = chestSingle.getChild("bottom");
        this.singleChestLid = chestSingle.getChild("lid");
        this.singleChestLatch = chestSingle.getChild("lock");

        ModelPart doubleLeft = ctx.getLayerModelPart(EntityModelLayers.DOUBLE_CHEST_LEFT);
        this.doubleChestLeftBase = doubleLeft.getChild("bottom");
        this.doubleChestLeftLid = doubleLeft.getChild("lid");
        this.doubleChestLeftLatch = doubleLeft.getChild("lock");

        ModelPart doubleRight = ctx.getLayerModelPart(EntityModelLayers.DOUBLE_CHEST_RIGHT);
        this.doubleChestRightBase = doubleRight.getChild("bottom");
        this.doubleChestRightLid = doubleRight.getChild("lid");
        this.doubleChestRightLatch = doubleRight.getChild("lock");
    }

    public static TexturedModelData getSingleTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        root.addChild("bottom", ModelPartBuilder.create()
                        .uv(0, 19)
                        .cuboid(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F),
                ModelTransform.NONE);
        root.addChild("lid", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F),
                ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        root.addChild("lock", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(7.0F, -2.0F, 14.0F, 2.0F, 4.0F, 1.0F),
                ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getRightDoubleTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        root.addChild("bottom", ModelPartBuilder.create()
                        .uv(0, 19)
                        .cuboid(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F),
                ModelTransform.NONE);
        root.addChild("lid", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F),
                ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        root.addChild("lock", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(15.0F, -2.0F, 14.0F, 1.0F, 4.0F, 1.0F),
                ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getLeftDoubleTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        root.addChild("bottom", ModelPartBuilder.create()
                        .uv(0, 19)
                        .cuboid(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F),
                ModelTransform.NONE);
        root.addChild("lid", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F),
                ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        root.addChild("lock", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(0.0F, -2.0F, 14.0F, 1.0F, 4.0F, 1.0F),
                ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(IronChestBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean hasWorld = world != null;

        BlockState state = hasWorld ? entity.getCachedState() :
                Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = state.contains(ChestBlock.CHEST_TYPE) ? state.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
        if (!(state.getBlock() instanceof AbstractChestBlock<?> abstractChestBlock)) return;

        matrices.push();
        float rotation = ((Direction)state.get(ChestBlock.FACING)).asRotation();
        matrices.translate(0.5F, 0.5F, 0.5F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation));
        matrices.translate(-0.5F, -0.5F, -0.5F);

        // Fallback / actual block entity source
        DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> propertySource;
        if (hasWorld) {
            propertySource = abstractChestBlock.getBlockEntitySource(state, world, entity.getPos(), true);
        } else {
            propertySource = DoubleBlockProperties.PropertyRetriever::getFallback;
        }

        float openProgress = ((Float2FloatFunction) propertySource.apply(ChestBlock.getAnimationProgressRetriever(entity))).get(tickDelta);
        openProgress = 1.0F - openProgress;
        openProgress = 1.0F - openProgress * openProgress * openProgress;

        int lightCoords = propertySource.apply(new LightmapCoordinatesRetriever());


        // Texture seçimi (vanilla’nın chest texture logic’ini kullanıyor; kendi texture’ını koyacaksan burayı değiştir)
        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(
                TexturedRenderLayers.CHEST_ATLAS_TEXTURE,
                Identifier.of("mobpvp", "textures/block/iron_chest.png")
        );

        VertexConsumer consumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        if (chestType != ChestType.SINGLE) {
            if (chestType == ChestType.LEFT) {
                renderParts(matrices, consumer, this.doubleChestLeftLid, this.doubleChestLeftLatch, this.doubleChestLeftBase, openProgress, lightCoords, overlay);
            } else { // RIGHT
                renderParts(matrices, consumer, this.doubleChestRightLid, this.doubleChestRightLatch, this.doubleChestRightBase, openProgress, lightCoords, overlay);
            }
        } else {
            renderParts(matrices, consumer, this.singleChestLid, this.singleChestLatch, this.singleChestBase, openProgress, lightCoords, overlay);
        }

        matrices.pop();
    }

    private void renderParts(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart latch, ModelPart base, float openFactor, int light, int overlay) {
        lid.pitch = -(openFactor * ((float)Math.PI / 2F));
        latch.pitch = lid.pitch;
        lid.render(matrices, vertices, light, overlay);
        latch.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }

    private static class LightmapCoordinatesRetriever implements DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Integer> {
        @Override
        public Integer getFrom(ChestBlockEntity chestBlockEntity) {
            return 0; // Veya ışık hesaplaması
        }

        @Override
        public Integer getFromBoth(ChestBlockEntity chestBlockEntity, ChestBlockEntity other) {
            return 0; // Double chest durumunda ışık ortalaması yapılabilir
        }

        @Override
        public Integer getFallback() {
            return 0; // Dünya yoksa (client-side render fallback)
        }
    }


}
*/