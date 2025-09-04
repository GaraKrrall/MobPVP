package com.kaplandev.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.RawFilteredPair;
import net.minecraft.text.Text;

import java.util.List;

public class BookOpener {
    public static void openFakeSignedBook() {
        // Boş written book stack
        ItemStack fakeBook = new ItemStack(Items.WRITTEN_BOOK);

        // Sayfaları RawFilteredPair olarak hazırlıyoruz
        List<RawFilteredPair<Text>> pages = List.of(
                RawFilteredPair.of(Text.literal("Merhaba!\nBu sayfa imzalı bir kitap gibi açıldı.")),
                RawFilteredPair.of(Text.literal("2. sayfa: Burayı da biz doldurduk."))
        );

        // İçerik bileşeni
        WrittenBookContentComponent content = new WrittenBookContentComponent(
                RawFilteredPair.of("MobTable Günlüğü"), // title
                "KaplanDev",        // author
                0,                  // generation
                pages,
                true                // signed
        );

        // Kitaba içerik ata
        fakeBook.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, content);

        // Kitap ekranı aç (Artık ItemStack değil Contents gerekiyor)
        MinecraftClient.getInstance().setScreen(
                new BookScreen(BookScreen.Contents.create(fakeBook))
        );
    }
}
