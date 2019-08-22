package com.sapient.feecalc.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class SortExample {
    public static void main(String[] args) {
        /*Integer[] arr = {12,23,1,5,34,9,15,63,34,78};
        Arrays.sort(arr, (a, b) -> b.compareTo(a));
        Arrays.stream(arr).forEach(System.out::println);

        String[] fruits = {"Orange", "Grape", "Apple", "Lemon", "Banana"};

        Arrays.sort(fruits);
        System.out.println("Alphabetical order: " + Arrays.toString(fruits));

        Arrays.sort(fruits, Collections.reverseOrder());
        System.out.println("Reverse-alphabetical order: " + Arrays.toString(fruits));*/
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "abc");
        System.out.println(map.put("a", "xyz"));
    }
}
