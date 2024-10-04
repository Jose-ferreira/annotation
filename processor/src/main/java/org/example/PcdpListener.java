package org.example;

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Repeatable(PcdpListeners.class)
public @interface PcdpListener {
    String context();
}
