package com.example.classes;

import java.util.Random;
public class QuickSort {
    private static final Random random = new Random(42L);
    public static void sort(int[] array){
        if(array == null || array.length <2){
            return;
        }
        quickSort(array, 0 , array.length-1);
    }
    private static void quickSort(int[] array, int low, int high) {
        if(low < high){
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex-1);
            quickSort(array, pivotIndex+1, high);
        }
    }
    private static int partition(int[] array, int low, int high) {
        int randomIndex = low + random.nextInt(high - low + 1);
        int temp = array[randomIndex];
        array[randomIndex] = array[high];
        array[high] = temp;

        int pivot = array[high];
        int i = low -1;
        for(int j = low ; j < high ; j++){
            if(array[j] < pivot){
                i++;
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        temp = array[i+1];
        array[i+1] = array[high];
        array[high] = temp;
        return i+1;
    }
    
}
