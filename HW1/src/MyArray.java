/**
 * Name: Ru Zhao 
 * 
 * Andrew ID: ruz
 */

import java.util.Arrays;

/**
 * MyArray is an ArrayList implementation using arrays
 * 
 * @author zhaoru Name: Ru Zhao Andrew ID: ruz
 *
 */
public class MyArray {
	private String[] myArray;
	private int size;

	/**
	 * Constructor
	 * 
	 * @param capacity
	 *            the default capacity to parse in
	 */
	public MyArray(int capacity) {
		size = 0;
		myArray = new String[capacity];
	}

	/**
	 * size of myArray, which is the number of elements
	 * 
	 * @return size
	 */
	public int size() {
		return size;
	}

	/**
	 * get capacity, or the current length, of the array
	 * 
	 * @return capacity, or the current length, of the array
	 */
	public int getCapacity() {
		return myArray.length;
	}

	/**
	 * add element into myArray. when inserting, duplicates are allowed. add
	 * method takes care of validating words. array will double its size when
	 * necessary
	 * 
	 * @param word
	 */
	public void add(String word) {
		if (isValidWord(word)) {
			if (needDoubleSize())
				doubleUp();
			myArray[size] = word;
			size++;
		}
	}

	/**
	 * find a word in the words array
	 * 
	 * @param word
	 *            the string to find
	 * @return true or false
	 */
	public boolean search(String word) {
		for (int i = 0; i < size; i++) {
			if (myArray[i].equals(word))
				return true;
		}
		return false;
	}

	/**
	 * remove all of the duplicates in the words array.
	 */
	public void removeDups() {
		int count = 0; // number of elements in new array, which should equals
						// size at last.
		boolean flag = false; // check if there is duplicate.
		String[] newArray = new String[getCapacity()]; // an array to hold all
														// non-duplicate words.
		// size is changing so we cannot use size as boundary
		for (int i = 0; i < myArray.length && myArray[i] != null; i++) {
			for (int j = 0; j < newArray.length && newArray[j] != null; j++) {
				if (myArray[i].equals(newArray[j])) {
					size--;
					flag = true;
					break;
				}
			}
			if (!flag) {
				newArray[count] = myArray[i];
				count++;
			}
			flag = false;
		}
		myArray = newArray;
	}

	/**
	 * print words in the words array
	 */
	public void display() {
		for (String word : myArray) {
			if (word != null)
				System.out.print(word + " ");
		}
		System.out.println();
	}

	/* helper methods */
	
	/**
	 * check is a word is valid, which is from a-z or A-Z
	 * 
	 * @param word
	 *            the word to check
	 * @return true or false
	 */
	private boolean isValidWord(String word) {
		if (word == null)
			return false;
		if (word.trim().length() == 0)
			return false;
		for (int i = 0; i < word.length(); i++) {
			if (!(word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')
					&& !(word.charAt(i) >= 'a' && word.charAt(i) <= 'z'))
				return false;
		}
		return true;
	}

	/**
	 * check if the capacity of myArray needs doubled
	 * 
	 * @return true or false
	 */
	private boolean needDoubleSize() {
		return size == getCapacity();
	}

	/**
	 * double up the capacity of myArray
	 */
	private void doubleUp() {
		int newCapacity;
		if (getCapacity() == 0)
			newCapacity = 1;
		else
			newCapacity = getCapacity() * 2;
		String[] newArray = Arrays.copyOf(myArray, newCapacity);
		myArray = newArray;
	}
}
