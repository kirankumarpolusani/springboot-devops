package com.sapient.feecalc.util;

import java.time.LocalTime;
import java.util.Objects;

public class FunctionalInterfaces {
    public static void main(String[] args) {
        System.out.println(composeHashCodes2("Hello", "World"));
        System.out.println(composeHashCodes2("Hello", null));
    }

    public static int composeHashCodes(Object a, Object b) {
        Objects.requireNonNull(a, "a may not be null "+getApplicationStatus());
        Objects.requireNonNull(b, "b may not be null "+getApplicationStatus());
        return a.hashCode() ^ b.hashCode();
    }

    public static int composeHashCodes2(Object a, Object b) {
        Objects.requireNonNull(a, () -> "a may not be null "+getApplicationStatus());
        Objects.requireNonNull(b, () -> "b may not be null "+getApplicationStatus());
        return a.hashCode() ^ b.hashCode();
    }

    public static String getApplicationStatus() {
        System.out.println("getApplicationStatus");
        return "It's "+ LocalTime.now();
    }
}
