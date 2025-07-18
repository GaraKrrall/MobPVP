package com.kaplandev.mixin.client;

import java.util.Random;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SplashTextResourceSupplier.class)
public abstract class BetterTitleScreen {


    private static final List<String> CUSTOM_SPLASHES = List.of(
            "MobPVP Better!",
            "Fight Smart!",
            "Level Up or Die!",
            "Monster Mayhem!",
            "Survival of the Fittest!",
            "Kill or Be Killed!",
            "Mob Warfare!",
            "PVP Evolved!",
            "No Mercy!",
            "Bloodthirsty Mode!",
            "Creature Carnage!",
            "Hunter's Paradise!",
            "Only the Strong Survive!",
            "Mob Slayer!",
            "Ruthless Battles!",
            "Wild Combat!",
            "Beast vs Beast!",
            "Feral Fight Club!",
            "Savage Showdown!",
            "Nature's Brutality!",
            "Tooth and Claw!",
            "Predator's Playground!",
            "Mob Massacre!",
            "Untamed Violence!",
            "Evolution of Combat!",
            "Wilderness Wars!",
            "Creature Conquest!",
            "Brutal Mob Battles!",
            "Survival Instincts!",
            "The Strongest Prevail!",
            "Mob Dominance!",
            "Fight for Existence!",
            "No Rules, Just Fight!",
            "Animal Aggression!",
            "Primeval PVP!",
            "Mob vs Mob vs You!",
            "Bloodsport Enabled!",
            "Darwinian Dueling!",
            "Fangs Out!",
            "Claws Sharpened!",
            "CEZA ADAMDIR! (: (easter egg)"
    );

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void onGet(CallbackInfoReturnable<SplashTextRenderer> cir) {
        String splash = CUSTOM_SPLASHES.get(new Random().nextInt(CUSTOM_SPLASHES.size()));
        cir.setReturnValue(new SplashTextRenderer(splash));
    }
}
