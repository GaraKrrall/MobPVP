package com.kaplandev.handler.type;

import com.kaplandev.handler.MobTableScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.kaplanlib.util.path.Paths.MOBPVP;

public class ScreenHandlerTypes {
    public static final ScreenHandlerType<MobTableScreenHandler> MOB_TABLE;

    static {
        MOB_TABLE = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MOBPVP, "mob_table"), new ScreenHandlerType<>((syncId, inv) -> new MobTableScreenHandler(syncId, inv, ScreenHandlerContext.EMPTY), FeatureFlags.VANILLA_FEATURES));
    }
}
