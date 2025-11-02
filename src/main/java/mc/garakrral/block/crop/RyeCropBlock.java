package mc.garakrral.block.crop;

import mc.garakrral.item.ItemType;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class RyeCropBlock extends CropBlock {
    public RyeCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ItemType.RYE_SEED;
    }
}

