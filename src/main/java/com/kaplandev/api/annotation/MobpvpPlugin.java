package com.kaplandev.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Bu annotation, bir sınıfın KaplanMod plugin'i olduğunu belirtir.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MobpvpPlugin {
}
