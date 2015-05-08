/********************************************************
 *
 * 08-722 Data Structures for Application Programmers
 * Lab 5 Comparing MergeSort with QuickSort
 *
 * A simple MergeSort implementation
 *
 * Andrew ID: ruz
 * Name: Ru Zhao
 *
 ********************************************************/

import java.util.*;

public class MergeSort {
	private static final int SIZE = 100000;
	private static Random rand = new Random();

	public static void main(String[] args) {
		MergeSort sorter = new MergeSort();
		int[] array = new int[SIZE];

		for (int i = 0; i < SIZE; i++) array[i] = rand.nextInt();

		// for(int i = 0; i < SIZE; i++) array[i] = SIZE - i;

		Stopwatch timer = new Stopwatch();
		sorter.mergeSort(array);
		System.out.println("Time taken to sort " + SIZE
				+ " elements (Merge Sort): " + timer.elapsedTime()
				+ " milliseconds");

		// to make sure sorting works.
		// add "-ea" vm argument
		assert isSorted(array);
	}

	public void mergeSort(int[] from) {
		// create a new array
		int[] to = new int[from.length];
		mergeSort(from, to, 0, from.length - 1);
	}

	private void mergeSort(int[] from, int[] to, int left, int right) {
		if (left >= right) // base case
			return;
		else { // recursive case
			// find midpoint
			int mid = left + (right - left) / 2;
			// sort left half recursively
			mergeSort(from, to, left, mid);
			// sort right half recursively
			mergeSort(from, to, mid + 1, right);
			// merge them
			merge(from, to, left, mid + 1, right);
		}
	}

	/*
	 * Instead of creating multiple arrays,
	 * this works with only two arrays (from and to)
	 *
	 * 1. leftPos: starting point of left half
	 * 2. rightPos: starting point of right half
	 * 3. rightBound: upper bound of right half
	 */
	public void merge(int[] from, int[] to, int leftPos, int rightPos,
			int rightBound) {
		int leftBound = rightPos - 1;
		int toIndex = leftPos;
		int numOfItems = rightBound - leftPos + 1;
		
		while (leftPos <= leftBound && rightPos <= rightBound) {
			if (from[leftPos] <= from[rightPos])
				to[toIndex++] = from[leftPos++];
			else {
				to[toIndex++] = from[rightPos++];
			}
		}
		
		while (leftPos <= leftBound)
			to[toIndex++] = from[leftPos++];
		
		while (rightPos <= rightBound)
			to[toIndex++] = from[rightPos++];
		
		for (int i = 0; i < numOfItems; i++, rightBound--)
			from[rightBound] = to[rightBound];
	}

	/**********************************************************
	 * Check if array is sorted. A simple debugging tool
	 **********************************************************/
	private static boolean isSorted(int[] array) {
		return isSorted(array, 0, array.length - 1);
	}

	private static boolean isSorted(int[] array, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (array[i] < array[i - 1])
				return false;
		return true;
	}

}