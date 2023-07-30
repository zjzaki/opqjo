package com.zjzaki.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * @author zjzaki
 * @create 2023年05月15日 23:30:02
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreBot {
}
