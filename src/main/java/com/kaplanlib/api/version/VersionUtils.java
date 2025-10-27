package com.kaplanlib.api.version;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * Gelişmiş beta sürüm tarih formatlayıcısı.
 * Örnek çıktı: 25w32d2, 25w32d2+3.1.4
 */
public class VersionUtils {


    /**
     * Güncel tarihi kullanarak beta sürüm kodunu döndürür.
     * Format: YYwWWdD (örnek: 25w32d2)
     */
    public static String getBetaVersionNumber() {
        LocalDate today = LocalDate.now();
        return generateBetaCode(today);
    }
    /**
     * Önceden belirlenmiş kalıcı beta sürüm kodunu döndürür.
     * Format: YYwWWdD (örnek: 25w32d2)
     */
    public static String getPermanentBetaVersionNumber() {return BetaVersions.BETA_VERSION_LAST;}

    /**
     * Tam sürüm ile birlikte beta sürüm kodunu döndürür.
     * Örnek: 25w32d2+3.1.4
     *
     * @param fullVersion Ana sürüm numarası (örn: "3.1.4")
     * @return Beta sürüm kodu + tam sürüm
     */
    public static String getBetaVersionNumberWithFullVersion(String fullVersion) {
        return getBetaVersionNumber() + "+" + fullVersion;
    }
    /**
     * Tam sürüm ile birlikte kalıcı beta sürüm kodunu döndürür.
     * Örnek: 25w32d2+3.1.4
     *
     * @param fullVersion Ana sürüm numarası (örn: "3.1.4")
     * @return Beta sürüm kodu + tam sürüm
     */
    public static String getPermanentBetaVersionNumberWithFullVersion(String fullVersion) {return BetaVersions.BETA_VERSION_LAST + "+" + fullVersion;}

    /**
     * Belirli bir tarihten beta kodu üretir.
     *
     * @param date Tarih (genellikle LocalDate.now())
     * @return YYwWWdD formatında beta kodu
     */
    private static String generateBetaCode(LocalDate date) {
        // ISO standardına göre hafta numarası ve gün
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int year = date.getYear() % 100; // son 2 hane
        int week = date.get(weekFields.weekOfWeekBasedYear());
        int day = date.getDayOfWeek().getValue(); // Pazartesi = 1, Pazar = 7

        return String.format("%02dw%02dd%d", year, week, day);
    }


}
