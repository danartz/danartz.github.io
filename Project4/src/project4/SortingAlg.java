package project4;

import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dan
 */
public class SortingAlg extends Thread {

    private final char selection;
    private final int[] arr;
    private int start;
    private int end;

    public SortingAlg(int[] arr, char flag) {
        this.selection = flag;
        this.arr = new int[arr.length];
        System.arraycopy(arr, 0, this.arr, 0, arr.length );
        if (flag == 'q')
        {
            this.start = 0;
            this.end = this.arr.length -1;
        }

    }

    public int[] getArr() {
        return arr;
    }

    public void selectionSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            int j;
            for (j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }

            if (min != i) {
                int tempSwap = arr[i];
                arr[i] = arr[min];
                arr[min] = tempSwap;
            }

        }
    }

    public void bubbleSort(int[] arr) {
        boolean continueFlag = true;

        for (int i = 1; i < arr.length && continueFlag; i++) {
            continueFlag = false;
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tempSwap = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tempSwap;
                    continueFlag = true;
                }
            }
        }
    }

    public void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int current = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > current) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = current;
        }
    }
    
    public void quicksort(int[] arr, int first, int last){
        if(last > first){
            int pivotIndex = partition(arr, first, last);
            quicksort(arr, first, pivotIndex -1);
            quicksort(arr, pivotIndex +1, last);
        }
    }
    
    public static int partition(int [] arr, int first, int last){
        int pivot = arr[first];
        int low = first + 1;
        int high = last;
        
        while(high > low){
            while (low <= high && arr[low] <= pivot){
                low++;
            }
            while(low <= high && arr[high] > pivot){
                high--;
            }
            if(high > low){
                int temp = arr[high];
                arr[high] = arr[low];
                arr[low] = temp;
            }
        }
        
        while(high > first && arr[high] >= pivot){
            high--;
        }
        if(pivot > arr[high]){
            arr[first] = arr[high];
            arr[high] = pivot;
            return high;
        }else{
            return first;
        }
    }


    @Override
    public void run() {
        if (this.selection == 's') {
            selectionSort(this.arr);
            
        }
        if (selection == 'b') {
            bubbleSort(this.arr);
            
        }
        if (selection == 'i') {
            insertionSort(this.arr);
        }
        if (selection == 'q') {
            quicksort(this.arr, this.start, this.end);
        }
    }

}
