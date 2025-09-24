package com.kaplandev.client.config;

import com.kaplanlib.client.api.ToastType;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "mobpvp_config")
public class ConfigManager implements ConfigData {
    @ConfigEntry.Gui.NoTooltip
    @Comment("Custom toast title.")
    public String customToastTitle = "MobPvP Addon";

    @ConfigEntry.Gui.NoTooltip
    @Comment("Custom toast message.")
    public String customToastMessage = "Hello!";

    @ConfigEntry.ColorPicker(allowAlpha = false)
    @Comment("Custom toast text color. Default: Yellow")
    public int customToastColor = 0xFFFF00;

    @ConfigEntry.Gui.NoTooltip
    @Comment("Toast style type.")
    public ToastType customToastType = ToastType.PERIODIC_NOTIFICATION;

    @ConfigEntry.Gui.NoTooltip
    @Comment("Shows the original AKOT message.")
    public boolean showAKOTOriginalMessage = true;

    @ConfigEntry.Category("old_features")
    @ConfigEntry.Gui.NoTooltip
    @Comment("Shows level and XP information on the in-game HUD.")
    public boolean showLevelHud = false;

    @ConfigEntry.Category("old_features")
    @ConfigEntry.Gui.NoTooltip
    @Comment("Enables the MobPvP panorama in the main menu background.")
    public boolean showMobPvPPanorama = true;

    @ConfigEntry.Category("debug")
    @ConfigEntry.Gui.NoTooltip
    @Comment("")
    public boolean debugHasShownToast = false;

    @ConfigEntry.Category("debug")
    @ConfigEntry.Gui.NoTooltip
    @Comment("")
    public boolean hasOpenedBetaNotice = false;

    @ConfigEntry.Category("old_features")
    @ConfigEntry.ColorPicker(allowAlpha = false)
    @Comment("Sets the text color for the level display. Default: Yellow")
    public int levelColor = 0xFFFF00;

    @ConfigEntry.Category("old_features")
    @ConfigEntry.ColorPicker(allowAlpha = false)
    @Comment("Sets the text color for the XP display. Default: White")
    public int xpColor = 0xFFFFFF;
}