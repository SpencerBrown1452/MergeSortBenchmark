/**
 * File: SortInterface.java
 * Date: 05 February 2021
 * Author: Spencer Brown
 * Purpose: Acts as the interface for the sort class
 */
public interface SortInterface {
    /**
     * Requires a recursive sorting method
     * @param list Takes an unsorted list
     * @return The input list, recursively sorted
     * @throws UnsortedException if the sort fails for any reason
     */
    public void recursiveSort(int[] list) throws UnsortedException;

    /**
     * Requires an iterative version of the mergesort method
     * @param list Take an unsorted list of integers
     * @return the input list, iteratively sorted
     * @throws UnsortedException if the sort fails for any reason
     */
    public void iterativeSort(int[] list) throws UnsortedException;

    /**
     * Requires a method to keep count of the efficiency of the sort
     * @return the efficiency of the sort
     */
    public int getCount();

    /**
     * Requires a method which calculates the amount of time the sort takes
     * @return the length of time the sort took to run
     */
    public long getTime();
}
