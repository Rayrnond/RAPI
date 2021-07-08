package com.reflexian.rapi.api.annotation;

import com.avaje.ebean.validation.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    @NotNull String name();
    String description();
    String[] aliases() default {};

}
