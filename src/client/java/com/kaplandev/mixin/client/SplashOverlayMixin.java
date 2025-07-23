package com.kaplandev.mixin.client;

import com.kaplandev.bouncingelf10.animatedLogo.DarkLoadingScreenCompat;
import com.kaplandev.util.path.Paths;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.resource.ResourceReload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;
import java.util.function.IntSupplier;

import static com.kaplandev.util.path.Paths.LOGGER;
import static com.kaplandev.util.path.Paths.MOBPVP;
import static net.minecraft.util.math.ColorHelper.Abgr.withAlpha;

@Mixin(SplashOverlay.class)
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class SplashOverlayMixin {
    @Mutable
    @Shadow
    @Final
    private ResourceReload reload;
    @Shadow
    private float progress;

    @Unique
    private int count = 0;
    @Unique
    private Identifier[] frames;
    @Unique
    private boolean inited = false;
    @Unique
    private static final int FRAMES = 12;
    @Unique
    private static final int IMAGE_PER_FRAME = 4;
    @Unique
    private static final int FRAMES_PER_FRAME = 2;
    @Unique
    private static final long TEXT_FADE_IN_DURATION_MS = 800;
    @Unique
    private static final long TEXT_VISIBLE_DURATION_MS = 1400;
    @Unique
    private static final long TEXT_FADE_OUT_DURATION_MS = 800;
    @Unique
    private long animationDelayStartTime2 = System.currentTimeMillis(); // başlangıç zamanı
    @Unique
    private float f = 0;
    @Unique
    private boolean animationDone = false;

    @Shadow
    @Final
    private static IntSupplier BRAND_ARGB; // Color of background
    @Unique
    private static int whiteARGB = ColorHelper.Argb.getArgb(255, 255, 255, 255);

    @Unique
    private static IntSupplier LOADING_FILL = () ->
            DarkLoadingScreenCompat.getBarColor(whiteARGB);
    @Unique
    private static IntSupplier LOADING_BORDER = () ->
            DarkLoadingScreenCompat.getBorderColor(whiteARGB);

    @Unique
    private static IntSupplier TEXT_COLOR = () ->
            applyAlphaToColor(DarkLoadingScreenCompat.getLogoColor(whiteARGB), 1.0f);


    @Unique
    private boolean soundPlayed = false;
    @Unique
    private boolean animationReady = false;
    @Unique
    private boolean isFadingOut = false;
    @Unique
    private boolean isFadingFinished = false;

    @Unique
    private long animationStartTime = -1;
    @Unique
    private static final float TOTAL_ANIMATION_DURATION = 3.0f; // in seconds
    @Unique
    private long animationDelayStartTime = -1;
    @Unique
    private static final long ANIMATION_DELAY_MS = 1;
    @Unique
    private long fadeOutStartTime = -1;
    @Unique
    private static final long FADE_OUT_DURATION_MS = 1000; // in milliseconds
    @Unique
    private static float loadingBarProgress = 0.0f; // in seconds

    @Unique
    private static boolean HAS_LOADED_ONCE = false;
    @Unique
    private static final int LOGO_TEXTURE_WIDTH = 640;
    @Unique
    private static final int LOGO_TEXTURE_HEIGHT = 57;

    // Draw vanilla loading bar
    // Copied from: @see net.minecraft.client.gui.screen.SplashOverlay.renderProgressBar
    @Unique
    private void drawLoadingBar(DrawContext context, float opacity, float progress) {
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        int centerX = screenWidth / 2;
        int progressBarY = (int) (screenHeight * 0.8325);

        double logoHeight = Math.min(screenWidth * 0.75, screenHeight) * 0.25;
        double logoWidth = logoHeight * 4.0;
        int halfLogoWidth = (int) (logoWidth * 0.5);

        int progressBarHeight = 4; // Daha ince bir bar
        int progressBarWidth = (int) (screenWidth * 0.3); // Genişliği küçült

        int minX = centerX - (progressBarWidth / 2);
        int maxX = centerX + (progressBarWidth / 2);
        int minY = progressBarY - (progressBarHeight / 2);
        int maxY = progressBarY + (progressBarHeight / 2);


        int filled = MathHelper.ceil((float) (maxX - minX - 2) * progress);
        int colorFilled = applyAlphaToColor(LOADING_FILL.getAsInt(), opacity);
        int colorOutline = applyAlphaToColor(LOADING_BORDER.getAsInt(), opacity);

        context.fill(minX + 2, minY + 1, minX + filled, maxY - 1, colorFilled);
        context.fill(minX, minY, maxX, minY + 1, colorOutline); // üst kenar
        context.fill(minX, maxY - 1, maxX, maxY, colorOutline); // alt kenar
        context.fill(minX, minY, minX + 1, maxY, colorOutline); // sol kenar
        context.fill(maxX - 1, minY, maxX, maxY, colorOutline); // sağ kenar
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(MinecraftClient client, ResourceReload monitor, Consumer<Throwable> exceptionHandler, boolean reloading, CallbackInfo ci) {
        if (HAS_LOADED_ONCE) {
            LOGGER.warn("Animated Mojang Logo has already been loaded once, skipping initialization.");
            return;
        }
        animationDelayStartTime = System.currentTimeMillis();
    }

    // Stop rendering of title
    @ModifyArg(method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;setShaderColor(FFFF)V", ordinal = 0),
            index = 3)
    private float removeText(float red) {
        return HAS_LOADED_ONCE ? red : 0;
    }

    // Stop rendering of loading bar
    @ModifyArg(method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SplashOverlay;renderProgressBar(Lnet/minecraft/client/gui/DrawContext;IIIIF)V", ordinal = 0),
            index = 5)
    private float removeBar(float opacity) {
        return HAS_LOADED_ONCE ? opacity : 0;
    }


    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void preRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (HAS_LOADED_ONCE) {
            return;
        }

        long elapsed = System.currentTimeMillis() - animationDelayStartTime;

        if (elapsed < ANIMATION_DELAY_MS) {
            context.fill(RenderLayer.getGuiOverlay(), 0, 0,
                    context.getScaledWindowWidth(), context.getScaledWindowHeight(),
                    withAlpha((int) ((elapsed * 255) / ANIMATION_DELAY_MS / 10),
                            applyAlphaToColor(BRAND_ARGB.getAsInt(), 1.0f)));
            ci.cancel();
            return;
        }

        if (!animationDone) {
            drawAnimatedIntro(context);
            ci.cancel();
        }
    }

    @Unique
    private void drawAnimatedIntro(DrawContext context) {
        if (!reload.isComplete() && !isFadingOut && !isFadingFinished) {

            context.fill(RenderLayer.getGuiOverlay(), 0, 0,
                    context.getScaledWindowWidth(), context.getScaledWindowHeight(),
                    applyAlphaToColor(BRAND_ARGB.getAsInt(), 1.0f));

            drawLoadingBar(context, 1.0f, Math.max(loadingBarProgress, reload.getProgress()));
            loadingBarProgress = reload.getProgress();
            animationDelayStartTime2 = System.currentTimeMillis();
            drawKaplanBedwarsImage(context);


            return;
        }

        if (reload.isComplete() && !isFadingOut && !isFadingFinished) {
            isFadingOut = true;
            fadeOutStartTime = System.currentTimeMillis();
        }

        if (isFadingOut && !isFadingFinished) {
            long elapsedFade = System.currentTimeMillis() - fadeOutStartTime;
            float fadeFactor = 1.0f - MathHelper.clamp((float) elapsedFade / FADE_OUT_DURATION_MS, 0.0f, 1.0f);

            context.fill(RenderLayer.getGuiOverlay(), 0, 0,
                    context.getScaledWindowWidth(), context.getScaledWindowHeight(),
                    applyAlphaToColor(BRAND_ARGB.getAsInt(), 1.0f));

            drawLoadingBar(context, fadeFactor, 1.0f);
            loadingBarProgress = reload.getProgress();

            if (fadeFactor <= 0.0) {
                isFadingFinished = true;
            }

            return;
        }

        if (isFadingFinished && !animationReady) {
            animationReady = true;
            animationStartTime = System.nanoTime();

            if (!soundPlayed) {
                MinecraftClient.getInstance().getSoundManager().play(
                        PositionedSoundInstance.master(Paths.STARTUP_SOUND_EVENT, 1.0F)
                );
                LOGGER.info("Playing startup sound");
                soundPlayed = true;
            }

            if (!inited) {
                this.frames = new Identifier[FRAMES];
                for (int i = 0; i < FRAMES; i++) {
                    this.frames[i] = Identifier.of(MOBPVP, "textures/gui/frame_" + i + ".png");
                }
                inited = true;
            }
        }

        if (animationReady) {
            double elapsedSeconds = (System.nanoTime() - animationStartTime) / 1_000_000_000.0;
            double animationProgress = Math.min(elapsedSeconds / TOTAL_ANIMATION_DURATION, 1.0);

            int totalFrameCount = FRAMES * IMAGE_PER_FRAME * FRAMES_PER_FRAME;
            count = (int) (animationProgress * totalFrameCount);

            if (animationProgress >= 1.0) {
                animationDone = true;
                count = totalFrameCount - 1;
            }

            int screenWidth = context.getScaledWindowWidth();
            int screenHeight = context.getScaledWindowHeight();
            int width = screenWidth / 2;
            int height = width * 256 / 1024;
            int x = (screenWidth - width) / 2;
            int y = (screenHeight - height) / 2;

            int frameIndex = count / IMAGE_PER_FRAME / FRAMES_PER_FRAME;
            int subFrameY = 256 * ((count % (IMAGE_PER_FRAME * FRAMES_PER_FRAME)) / FRAMES_PER_FRAME);

            context.fill(RenderLayer.getGuiOverlay(), 0, 0,
                    context.getScaledWindowWidth(), context.getScaledWindowHeight(),
                    applyAlphaToColor(BRAND_ARGB.getAsInt(), 1.0f));

            setShaderColor(TEXT_COLOR.getAsInt(), 1.0f);
            context.drawTexture(frames[frameIndex], x, y, width, height, 0, subFrameY, 1024, 256, 1024, 1024);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Unique
    private void drawKaplanBedwarsImage(DrawContext context) {
        long elapsed = System.currentTimeMillis() - animationDelayStartTime;
        long totalDuration = TEXT_FADE_IN_DURATION_MS + TEXT_VISIBLE_DURATION_MS + TEXT_FADE_OUT_DURATION_MS;

        if (elapsed > totalDuration) return;

        float alpha;
        if (elapsed < TEXT_FADE_IN_DURATION_MS) {
            alpha = (float) elapsed / TEXT_FADE_IN_DURATION_MS;
        } else if (elapsed < TEXT_FADE_IN_DURATION_MS + TEXT_VISIBLE_DURATION_MS) {
            alpha = 1.0f;
        } else {
            long fadeOutElapsed = elapsed - TEXT_FADE_IN_DURATION_MS - TEXT_VISIBLE_DURATION_MS;
            alpha = 1.0f - (float) fadeOutElapsed / TEXT_FADE_OUT_DURATION_MS;
        }

        Identifier texture = Identifier.of(MOBPVP, "textures/gui/klogo5.png");

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        int drawWidth = 220;  // Resmin genişliği
        int drawHeight = 168;  // Resmin yüksekliği

        int x = (screenWidth - drawWidth) / 2;
        int y = (screenHeight - drawHeight) / 2; // Ortaya hizalanıyor

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, texture);
        // Yumuşatma filtresi — BU SATIR YENİ
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);



        context.drawTexture(texture, x, y, 0, 0, drawWidth, drawHeight, drawWidth, drawHeight);

        RenderSystem.disableBlend();
    }



    @Inject(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIFFIIII)V",
            ordinal = 1, shift = At.Shift.AFTER))
    private void onAfterRenderLogo(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci,
                                   @Local(ordinal = 2) int scaledWidth, @Local(ordinal = 3) int scaledHeight,
                                   @Local(ordinal = 3) float alpha, @Local(ordinal = 4) int x, @Local(ordinal = 5) int y,
                                   @Local(ordinal = 0) double height, @Local(ordinal = 6) int halfHeight,
                                   @Local(ordinal = 1) double width, @Local(ordinal = 7) int halfWidth) {
        if (!animationDone || HAS_LOADED_ONCE) return;

        // Studios.png
        float progress = MathHelper.clamp(this.progress * 0.95F + this.reload.getProgress() * 0.050000012F, 0.0F, 1.0F);
        if (progress >= 0.8) {
            f = Math.min(alpha, f + 0.2f);
            int sw = (int) (width * 0.45);
            setShaderColor(TEXT_COLOR.getAsInt(), f);
            context.drawTexture(Identifier.of(MOBPVP, "textures/gui/studios.png"), x - sw / 2, (int) (y - halfHeight + height - height / 12), sw, (int) (height / 5.0), 0, 0, 450, 50, 512, 512);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        // Title (last frame)
        int finalFrameScreenWidth = context.getScaledWindowWidth();
        int finalFrameScreenHeight = context.getScaledWindowHeight();
        int finalFrameWidth = finalFrameScreenWidth / 2;
        int finalFrameHeight = finalFrameWidth * 256 / 1024;
        int finalFrameX = (finalFrameScreenWidth - finalFrameWidth) / 2;
        int finalFrameY = (finalFrameScreenHeight - finalFrameHeight) / 2;
        int finalSubFrameY = 256 * ((count % (IMAGE_PER_FRAME * FRAMES_PER_FRAME)) / FRAMES_PER_FRAME);

        Identifier finalFrame = frames[FRAMES - 1];

        setShaderColor(TEXT_COLOR.getAsInt(), alpha);
        context.drawTexture(finalFrame, finalFrameX, finalFrameY, finalFrameWidth, finalFrameHeight, 0, finalSubFrameY, 1024, 256, 1024, 1024);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (alpha <= 0.0f) {
            HAS_LOADED_ONCE = true;
        }

    }

    @Unique
    private static void setShaderColor(int color, float alpha) {
        RenderSystem.setShaderColor(
                ((applyAlphaToColor(color, alpha) >> 16) & 0xFF) / 255.0f,
                ((applyAlphaToColor(color, alpha) >> 8) & 0xFF) / 255.0f,
                (applyAlphaToColor(color, alpha) & 0xFF) / 255.0f,
                ((applyAlphaToColor(color, alpha) >> 24) & 0xFF) / 255.0f
        );
    }

    @Unique
    private static int applyAlphaToColor(int color, float alpha) {
        int rgb = color & 0x00FFFFFF;
        int a = MathHelper.clamp((int) (alpha * 255), 0, 255);
        return (a << 24) | rgb;
    }
}