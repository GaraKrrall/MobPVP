package com.kaplandev.client.gui.level;

import com.kaplandev.level.player.PlayerLevelData;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.core.OwoUIAdapter;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.UUID;

/**
 * Owo'ya minimum bağlı, sürüm farklarına dayanıklı profesyonel Level ekranı.
 * Tüm çizimi render() içinde yapıyoruz — böylece 'gap', 'alignment', 'color' gibi
 * eksik Owo metotlarından etkilenmiyoruz.
 */
public class LevelScreen extends BaseOwoScreen<FlowLayout> {

    private int level = 0;
    private int xp = 0;
    private int xpToNext = 1;

    // animasyon için önceki progress saklanabilir (smooth dolum)
    private float displayedProgress = 0f;

    public LevelScreen() {
        super(Text.translatable("screen.kaplandev.level.title"));
    }

    @Override
    protected OwoUIAdapter<FlowLayout> createAdapter() {
        // Owo adapter oluşturuyoruz ama içerik kullanmayacağız (uyumluluk için)
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout root) {
        // Boş bırakıyoruz — render() içinde tüm görsellik çizilecek.
    }

    @Override
    public void tick() {
        super.tick();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            UUID uuid = client.player.getUuid();
            this.level = PlayerLevelData.getLevel(uuid);
            this.xp = PlayerLevelData.getXp(uuid);
            this.xpToNext = Math.max(1, PlayerLevelData.getXpToLevelUp(level));
        }

        // displayedProgress'ü yumuşat (lerp tarzı) -> animasyonlu dolum
        float target = xpToNext > 0 ? (float) xp / xpToNext : 0f;
        final float speed = 0.06f; // küçükse daha yavaş, büyükse daha hızlı
        this.displayedProgress += (target - this.displayedProgress) * Math.min(1f, speed);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // önce arkaplan (karartma)
        this.renderBackground(context,mouseX,mouseY,delta);

        final int w = this.width;
        final int h = this.height;

        // panel boyutları (responsive)
        final int panelW = Math.min(560, (int) (w * 0.8f));
        final int panelH = Math.min(260, (int) (h * 0.5f));
        final int px = (w - panelW) / 2;
        final int py = (h - panelH) / 2;

        // Panel: yarı saydam koyu arka + üstten hafif gradient
        drawPanel(context, px, py, panelW, panelH);

        // Başlık
        String title = this.title.getString();
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("⚔ " + title + " ⚔"), w / 2, py + 14, 0xFFE0C341);

        // Seviye büyük metin
        String levelText = "★ Level " + level + " ★";
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(levelText), w / 2, py + 44, 0xFF55FFFF);

        // XP bar pozisyonu
        final int barW = Math.min(420, panelW - 80);
        final int barH = 22;
        final int barX = w / 2 - barW / 2;
        final int barY = py + panelH / 2 - barH / 2 + 12;

        // Arka plan bar
        int bgColor = 0xE0202024; // koyu yarı saydam
        context.fill(barX, barY, barX + barW, barY + barH, bgColor);

        // Dolgu gradient (displayedProgress kullan)
        int leftColor = 0xFF2DA6FF; // mavi
        int rightColor = 0xFF55FFFF; // açık mavi
        int filled = (int) (barW * displayedProgress);
        if (filled > 0) {
            // fillGradient(x1,y1,x2,y2,color1,color2)
            context.fillGradient(barX, barY, barX + filled, barY + barH, leftColor, rightColor);
        }

        // İnce kenarlık
        context.fill(barX, barY, barX + barW, barY + 1, 0xFF000000);
        context.fill(barX, barY + barH - 1, barX + barW, barY + barH, 0xFF000000);
        context.fill(barX, barY, barX + 1, barY + barH, 0xFF000000);
        context.fill(barX + barW - 1, barY, barX + barW, barY + barH, 0xFF000000);

        // XP metni ortada
        String xpText = xp + " / " + xpToNext + " XP";
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(xpText), w / 2, barY + (barH / 2) - 4, 0xFFFFFFFF);

        // Sağ alt köşede kapat butonu benzeri alan
        final String closeText = "✖ Close";
        int closeW = this.textRenderer.getWidth(closeText) + 12;
        int closeH = 18;
        int closeX = px + panelW - closeW - 12;
        int closeY = py + panelH - closeH - 12;
        context.fill(closeX, closeY, closeX + closeW, closeY + closeH, 0x88000000); // arka
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(closeText), closeX + closeW / 2, closeY + (closeH / 2) - 4, 0xFFFFFFFF);

        // Fare kapat butonunun üzerinde ise fareye tıklama işlemi yapabiliriz (isteğe bağlı)
        // (Buton çalışması için mouseClicked override edebiliriz.)

        // Ek: sol tarafta küçük istatistik kutuları (HP / Güç / Def)
        final int statBoxW = 120;
        final int statBoxH = 40;
        int statX = px + 20;
        int statY = py + panelH / 2 - statBoxH - 8;


        // son olarak Owo adapter / bileşen render çağrısına ihtiyaç yok çünkü biz çizdik
        super.render(context, mouseX, mouseY, delta);
    }

    // Paneli gradient + gölge ile çizer
    private void drawPanel(DrawContext ctx, int x, int y, int w, int h) {
        // büyük yarı-transparent arka plan
        ctx.fill(x, y, x + w, y + h, 0xCC0F1115);

        // üstten hafif gradient parlama
        ctx.fillGradient(x, y, x + w, y + Math.max(6, h / 8), 0x40FFFFFF, 0x00000000);

        // alt kenarda hafif koyuluk
        ctx.fillGradient(x, y + h - Math.max(6, h / 8), x + w, y + h, 0x00000000, 0x22000000);

        // ince border
        ctx.fill(x, y, x + w, y + 1, 0x66000000);
        ctx.fill(x, y + h - 1, x + w, y + h, 0x66000000);
        ctx.fill(x, y, x + 1, y + h, 0x66000000);
        ctx.fill(x + w - 1, y, x + w, y + h, 0x66000000);
    }

    // Basit istatistik kutucuğu
    private void drawStatBox(DrawContext ctx, int x, int y, int w, int h, String label, String value) {
        ctx.fill(x, y, x + w, y + h, 0x1FFFFFFF); // hafif
        ctx.fill(x, y, x + w, y + 1, 0x33000000);
        ctx.drawTextWithShadow(this.textRenderer, Text.literal(label), x + 8, y + 6, 0xFFD4C08A);
        ctx.drawTextWithShadow(this.textRenderer, Text.literal(value), x + 8, y + 18, 0xFFFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    // İstersen tıklama ile kapatmayı etkinleştirelim
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // kapat butonu alanını yeniden hesapla (aynı mantık render içi ile)
        final int w = this.width;
        final int h = this.height;
        final int panelW = Math.min(560, (int) (w * 0.8f));
        final int panelH = Math.min(260, (int) (h * 0.5f));
        final int px = (w - panelW) / 2;
        final int py = (h - panelH) / 2;
        final String closeText = "✖ Kapat";
        int closeW = this.textRenderer.getWidth(closeText) + 12;
        int closeH = 18;
        int closeX = px + panelW - closeW - 12;
        int closeY = py + panelH - closeH - 12;

        if (mouseX >= closeX && mouseX <= closeX + closeW && mouseY >= closeY && mouseY <= closeY + closeH) {
            this.close();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
