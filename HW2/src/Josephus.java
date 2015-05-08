import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**********************************************************
 *
 * 08-722 Data Structures for Application Programmers
 *
 * Homework Assignment 2 Solve Josephus problem
 * with different data structures and different algorithms
 * and compare running time
 *
 * Andrew ID: ruz
 * Name: Ru Zhao
 *
 **********************************************************/

public class Josephus {

	/**
	 * This method uses ArrayDeque data structure as Queue/Deque to find the
	 * survivor's position.
	 *
	 * @param size
	 *            Number of people in the circle that is bigger than 0.
	 * @param rotation
	 *            Elimination order in the circle. The value has to be greater
	 *            than 0.
	 * @return The position value of the survivor.
	 */
	public int playWithAD(int size, int rotation) {
		// illegal arguments
		if (size <= 0)
			throw new RuntimeException("Illegal Size");
		if (rotation <= 0)
			throw new RuntimeException("Illegal Rotation");

		Deque<Integer> ad = new ArrayDeque<Integer>();
		
		// Initialize the ArrayDeque
		for (int i = 1; i < size + 1; i++) {
			ad.add(i);
		}
		
		// let num be the number of people left
		int num = size;
		
		while (num > 1) {
			// the position to remove
			int rot = rotation % num;
			
			// if mod is 0, we need to set it to the last to remove the last
			if (rot == 0)
				rot = num;
			
			// turn position to index
			rot--;
			
			// dequeue and enqueue the unkilled people
			for (int j = 0; j < rot; j++) {
				int tmp = ad.poll();
				ad.add(tmp);
			}
			
			// killed person
			ad.poll();
			
			num--;
		}
		return ad.poll();
	}

	/**
	 * This method uses LinkedList data structure as Queue/Deque to find the
	 * survivor's position.
	 *
	 * @param size
	 *            Number of people in the circle that is bigger than 0.
	 * @param rotation
	 *            Elimination order in the circle. The value has to be greater
	 *            than 0.
	 * @return The position value of the survivor.
	 */
	public int playWithLL(int size, int rotation) {
		// illegal arguments
		if (size <= 0)
			throw new RuntimeException("Illegal Size");
		if (rotation <= 0)
			throw new RuntimeException("Illegal Rotation");

		Deque<Integer> ll = new LinkedList<Integer>();
		
		// Initialize the LinkedList
		for (int i = 1; i < size + 1; i++) {
			ll.add(i);
		}
		
		// let num be the number of people left
		int num = size;
		
		while (num > 1) {
			// the position to remove
			int rot = rotation % num;
			
			// if mod is 0, we need to set it to the last to remove the last
			if (rot == 0)
				rot = num;
			
			// turn position to index
			rot--;
			
			// dequeue and enqueue the unkilled people
			for (int j = 0; j < rot; j++) {
				int tmp = ll.poll();
				ll.add(tmp);
			}
			
			// killed person
			ll.poll();
			
			num--;
		}
		return ll.poll();
	}

	/**
	 * This method uses LinkedList data structure to find the survivor's
	 * position. However, this does not use the LinkedList as Queue/Deque.
	 * Instead, this method uses LinkedList as "Linked List."
	 *
	 * That means, it uses index value to find and remove a person to be
	 * executed in the circle.
	 *
	 * @param size
	 *            Number of people in the circle that is bigger than 0.
	 * @param rotation
	 *            Elimination order in the circle. The value has to be greater
	 *            than 0.
	 * @return The position value of the survivor.
	 */
	public int playWithLLAt(int size, int rotation) {
		// illegal arguments
		if (size <= 0)
			throw new RuntimeException("Illegal Size");
		if (rotation <= 0)
			throw new RuntimeException("Illegal Rotation");

		List<Integer> llAt = new LinkedList<Integer>();
		
		// Initialize the LinkedList
		for (int i = 1; i < size + 1; i++) {
			llAt.add(i);
		}
		
		// let num be the number of people left; index to mark the index of
		// recent deleted
		int num = size, index = 0;
		
		while (num > 1) {
			// the position to remove
			index = (index + rotation) % num;
			
			// if mod is 0, we need to set it to the last to remove the last
			if (index == 0)
				index = num;
			
			// turn position to index
			index--;
			
			// killed person
			llAt.remove(index);
			
			num--;
		}
		return llAt.get(0);
	}

}