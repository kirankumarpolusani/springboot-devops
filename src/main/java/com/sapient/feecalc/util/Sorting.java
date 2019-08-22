package com.sapient.feecalc.util;

import java.util.Arrays;

public class Sorting {
    public static void main(String[] args) {
        int[] arr = new int[]{12,6,8,9,4,7};
        //selectionSort(arr);
        //bubbleSort(arr);
        insertionSort(arr);
        Arrays.stream(arr).forEach(System.out::println);

    }

    public static int[] selectionSort(int[] a) {
        int minIndex, temp;
        for(int i=0; i<a.length-1; i++) {
            minIndex = i;
            for(int j=i+1; j<a.length; j++) {
                if(a[j] < a[minIndex]){
                    minIndex = j;
                }
            }
            temp = a[minIndex];
            a[minIndex] = a[i];
            a[i] = temp;
        }
        return a;
    }

    public static int[] bubbleSort(int[] a) {
        boolean sorted = false;
        int temp, count=0;
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
       return a;
    }

    //12,6,8,9,4,7
    public static int[] insertionSort(int[] a) {
        int current;
        for(int i=1; i<a.length; i++) {
            current = a[i];
            int j = i-1;
            while(j >= 0 && a[j] > current) {
                a[j+1] = a[j];
                j = j-1;
            }
            a[j+1] = current;
        }
        return a;
    }

}
