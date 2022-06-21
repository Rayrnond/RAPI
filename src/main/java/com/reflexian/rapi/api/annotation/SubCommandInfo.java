package com.reflexian.rapi.api.annotation;

import com.avaje.ebean.validation.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommandInfo {

    @NotNull String name();
    @NotNull Class<?> command();
    String description() default "";
    String[] aliases() default {};
    

}
