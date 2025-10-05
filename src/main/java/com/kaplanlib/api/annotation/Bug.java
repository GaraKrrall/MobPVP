package com.kaplanlib.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Runtime'da erişilebilir
@Target({
        ElementType.TYPE,          // Sınıflar, interfaceler, enumlar
        ElementType.FIELD,         // Değişkenler (instance variables)
        ElementType.METHOD,        // Metotlar
        ElementType.PARAMETER,     // Metot parametreleri
        ElementType.CONSTRUCTOR,  // Constructor'lar
        ElementType.LOCAL_VARIABLE, // Yerel değişkenler (local variables)
        ElementType.ANNOTATION_TYPE, // Diğer annotation'lar
        ElementType.PACKAGE,       // Paketler
        ElementType.TYPE_PARAMETER, // Generic tip parametreleri (Java 8+)
        ElementType.TYPE_USE       // Her türlü tip kullanımı (Java 8+)
})
public @interface Bug {
    String value() default "";
}
