package mc.garakrral.block.crop.api;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import mc.garakrral.item.ItemType;

import com.kaplanlib.api.behavior.BlockBehavior;

import static mc.garakrral.mobpvp.MOD_ID;

public class CropBlockBuilder {
    private final String name;
    private final AbstractBlock.Settings settings;
    private BlockBehavior behavior;


    private CropBlockBuilder(String name, AbstractBlock.Settings settings) {
        this.name = name;
        this.settings = settings;
    }

    public static CropBlockBuilder createCrop(String name, AbstractBlock.Settings settings) {
        return new CropBlockBuilder(name, settings);
    }

    public CropBlockBuilder behavior(BlockBehavior behavior) {
        this.behavior = behavior;
        return this;
    }

    public Block register() {
        Block block = new CropBlock(settings) {
            @Override
            protected ItemConvertible getSeedsItem() {

                return ItemType.RYE_SEED;
            }

            @Override
            protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropXp) {
                super.onStacksDropped(state, world, pos, tool, dropXp);
                if (behavior != null) {
                    behavior.onStacksDropped(state, world, pos, tool, dropXp);
                }
            }
        };

        Identifier id = Identifier.of(MOD_ID, name);
        return Registry.register(Registries.BLOCK, id, block);
    }
}
