/**
 * File: mergeSort.java
 * Date: 05 February 2021
 * Author: Spencer Brown
 * Purpose: Uses the mergesort algorithm to sort the array
 * Uses a modified version of the MergeSort algorithms from geeksforgeeks.com
 */

public class MergeSort implements SortInterface{
    //Variable declaration
    private long finishTime;
    private int count;

    //Default Constructor
    public MergeSort(){
        //Initializes the values for efficiency and time
        finishTime = 0;
        count = 0;
    }

    /**
     * Helper method for the recursive sort algorithm
     * @param list Takes an unsorted list of integers
     * @return the list in sorted order
     * @throws UnsortedException if the array could not be sorted
     */
    @Override
    public void recursiveSort(int[] list) throws UnsortedException {
        count = 0;
        int n = list.length;
        //Temporary Array to hold the sorted values
        int[] temp = new int[n];
        long startTime = System.nanoTime();
        recursiveSort(list, temp, 0, n);
        finishTime = System.nanoTime() - startTime;
    }

    /**
     * Recursive version of the mergeSort algorithm
     * @param list Array to be sorted
     * @param temp Temporary array to store the sorted values
     * @param l Left element of tbe array
     * @param r Right element of the array
     */
    public void recursiveSort(int[] list, int[] temp, int l, int r) {
        //Base case
        if(r - l <= 1){
            return;
        }
        //If there are elements that remain unsorted
        if (l < r) {
            //Find the middle of the list
            int m = l + (r - l) / 2;
            //Sort the first half of the input list
            recursiveSort(list, temp, l, m);
            //Sort the second half of the input list
            recursiveSort(list, temp, m , r);
            //Merge the sorted halves
            merge(list, temp, l, m, r);
        }
    }

    /**
     * The iterative version of the merge sort algorithm
     * @param list Take an unsorted list of integers
     * @return the list in sorted order
     * @throws UnsortedException if the array could not be sorted
     */
    public void iterativeSort(int[] list) throws UnsortedException {
        count = 0;
        int n = list.length;
        //Temporary Array to hold the sorted values
        int[] temp = new int[n];
        long startTime = System.nanoTime();
        //Sort and merge from the bottom up
        for(int size = 1; size < n; size *= 2)
            for(int left = 0; left < n; left += 2 * size){
                merge(list,temp, left, left + size, left + 2 * size);
            }
        finishTime = System.nanoTime() - startTime;
    }

    /**
     * Merges the sorted subarrays
     * @param list the array to be sorted
     * @param temp the temporary array to store the sorted values
     * @param l the left-most element of the subarray
     * @param m the middle of the subarray
     * @param r the right-most element of the subarray
     */
    private void merge(int[] list, int[] temp, int l, int m, int r) {
        if (m >= list.length){
            return;
        }
        //Set r to the length of the array to prevent index out-of-bounds
        if(r > list.length){
            r = list.length;
        }
        int i = l; //i = left index
        int j = m; //j = middle index
        for(int k = l; k < r; k++){
            if(i==m){
                temp[k] = list[j++];
            }else if(j == r){
                temp[k] = list [i++];
            }else{
                count ++;
                if(list[j] < list[i]){
                    temp[k] = list[i++];
                } else{
                    temp[k] = list [j++];
                }
            }
        }
        //Copy the sorted contents of the temp array to the original array, list
        System.arraycopy(temp, l, list, l, r - l);
    }

    /**
     * Returns the count of the efficiency
     * @return this sort's count
     */
    @Override
    public int getCount() {
        return this.count;
    }

    /**
     * Returns the amount of time the sort took to complete
     * @return the length of time this sort took to execute
     */
    @Override
    public long getTime() {
        return this.finishTime;
    }
}
