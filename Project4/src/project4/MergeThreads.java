/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project4;

import java.util.Arrays;

/**
 *
 * @author dan
 */
public class MergeThreads extends Thread{

private int arr1[];
private int arr2[];
private int tMerger[];
    
    public MergeThreads(int [] arr1, int [] arr2){
        this.arr1 = new int[arr1.length];
        this.arr2 = new int[arr2.length];
        this.tMerger = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, this.arr1, 0, arr1.length );
        System.arraycopy(arr2, 0, this.arr2, 0, arr2.length );

    }

    public int[] gettMerger() {
        return tMerger;
    }
    
    public void merge(int[] list1, int[] list2, int[] tMerger) {
        int index1 = 0;
        int index2 = 0;
        int index3 = 0;

        while (index1 < list1.length && index2 < list2.length) {

            if (list1[index1] < list2[index2]) {
                
                tMerger[index3] = list1[index1];
                index3++;
                index1++;
            } else {

                tMerger[index3] = list2[index2];
                index3++;
                index2++;
            }
        }

        while (index1 < list1.length) {

            tMerger[index3++] = list1[index1++];
        }
        while (index2 < list2.length) {

            tMerger[index3++] = list2[index2++];
        }
        
        

    }

    @Override
    public void run() {
        merge(this.arr1, this.arr2, this.tMerger);
        
    }
    
}
