package com.kotlinprc.accountapi.aop;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;

import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;

@Target(ElementType.METHOD)
@Retention(AnnotationRetention.RUNTIME)
@Deprecated
@Inherited
public @interface AccountLock {
    long tryLockTime() default 5000L;
}
