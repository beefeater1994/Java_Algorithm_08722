/**********************************************************************
 *
 * 08-722 Data Structures for Application Programmers
 * Lab 7 Heaps and Java PriorityQueue class
 *
 * Find median of sequence of integers using both maxHeap and minHeap
 *
 * Andrew ID:
 * Name:
 *
 **********************************************************************/

import java.util.*;

public class FindMedian {
	private PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(20,
			Collections.reverseOrder());
	private PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(20);

	public static void main(String[] args) {
		FindMedian tester = new FindMedian();
		tester.addNumber(6);
		tester.addNumber(4);
		tester.addNumber(3);
		tester.addNumber(10);
		tester.addNumber(12);

		// 6.0
		System.out.println(tester.getMedian());

		tester.addNumber(5);

		// 5.5
		System.out.println(tester.getMedian());

		tester.addNumber(7);
		tester.addNumber(8);

		// 6.5
		System.out.println(tester.getMedian());
	}

	/*
	 * It maintains a condition that maxHeap.size() >= minHeap.size()
	 */
	public void addNumber(int value) {
		if (maxHeap.size() == minHeap.size()) {
			if (minHeap.peek() != null && value > minHeap.peek()) {
				maxHeap.offer(minHeap.poll());
				minHeap.offer(value);
			} else {
				maxHeap.offer(value);
			}
		} else {
			if (value < maxHeap.peek()) {
				minHeap.offer(maxHeap.poll());
				maxHeap.offer(value);
			}else {
				minHeap.offer(value);
			}
		}
	}

	/*
	 * If maxHeap and minHeap are of different sizes,
	 * then maxHeap must have one extra element.
	 */
	public double getMedian() {
		if (maxHeap.isEmpty())
			return 0;
		if (maxHeap.size() == minHeap.size())
			return ((double)maxHeap.peek() + (double)minHeap.peek()) / 2;
		else
			return (double)maxHeap.peek();
	}

}