package com.sapient.feecalc.util;

import java.util.Random;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {
        /*final Random random = new Random();
        Stream<Integer> randoms = Stream.generate(random::nextInt);
        randoms.filter(n -> n>0).distinct().limit(10).forEach(System.out::println);*/

        int count = 1;
        String abc = "abcdbcaefgdfcdag";
        char[] a = abc.toCharArray();
        boolean sorted = false;
        char temp;
        while(!sorted) {
            sorted = true;
            for(int i=0; i<a.length-1; i++){
                if(a[i] > a[i+1]){
                    temp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = temp;
                    sorted = false;
                    count++;
                }
            }
        }
        System.out.println(a);
        System.out.println(" : "+ count);

    }

    static int partition(int[] array, int begin, int end) {
        int pivot = end;

        int counter = begin;
        for (int i = begin; i < end; i++) {
            if (array[i] < array[pivot]) {
                int temp = array[counter];
                array[counter] = array[i];
                array[i] = temp;
                counter++;
            }
        }
        int temp = array[pivot];
        array[pivot] = array[counter];
        array[counter] = temp;

        return counter;
    }

    public static void quickSort(int[] array, int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(array, begin, end);
        quickSort(array, begin, pivot-1);
        quickSort(array, pivot+1, end);
    }
}
