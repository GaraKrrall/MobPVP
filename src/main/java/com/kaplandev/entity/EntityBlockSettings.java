package com.kaplandev.entity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.sound.BlockSoundGroup;

public class EntityBlockSettings {
    public static final AbstractBlock.Settings spawnerSettings = AbstractBlock.Settings.create().strength(5.0f).requiresTool().resistance(1200.0f).hardness(-1.0f).sounds(BlockSoundGroup.TRIAL_SPAWNER);
}