/***********************************************************
 *
 * 08-722 Data Structures for Application Programmers Homework 4 HashTable
 * Implementation with linear probing
 *
 * Andrew ID: ruz Name: Ru Zhao
 *
 ***********************************************************/

public class MyHashTable implements MyHTInterface {

	private static final int INITIAL_DEFALUT_CAP = 10;
	private static final double LOAD_FACTOR = 0.5;
	private static final int ALPHABET_NUM = 27;

	// use key.equals(hashArray[hashVal].value) instead of
	// hashArray[hashVal].value.equals(key), to avoid null pointer exception.
	// Pre-condition: key is non-null
	private static final DataItem DELETED = new DataItem(null, 0);

	private DataItem[] hashArray;
	private int tableLength, size;
	private double loadFactor;

	/**
	 * Constructor without initial size, default as 10
	 */
	public MyHashTable() {
		loadFactor = 0;
		size = 0;
		tableLength = INITIAL_DEFALUT_CAP;
		hashArray = new DataItem[tableLength];
	}

	/**
	 * Constructor with initial size
	 * 
	 * @param newTableLength
	 *            initial size
	 */
	public MyHashTable(int newTableLength) {
		if (newTableLength <= 0)
			throw new IllegalArgumentException(
					"Initial capacity should be bigger than 0.");
		loadFactor = 0;
		size = 0;
		tableLength = newTableLength;
		hashArray = new DataItem[tableLength];
	}

	/**
	 * Inserts a new String value (word). Frequency of each word be stored too.
	 *
	 * @param value
	 *            String value to be added.
	 */
	@Override
	public void insert(String value) {
		int hashVal = hashValue(value);

		// position to insert the value
		int position = hashVal;

		// if is valid word
		if (hashVal != -1) {
			DataItem added = new DataItem(value);

			// find a non-null or non-deleted position for the added value
			while (hashArray[position] != null
					&& hashArray[position] != DELETED) {

				// if the frequency of the value should be added
				if (value.equals(hashArray[position].value)) {
					hashArray[position].incrementFreq();
					return;
				}

				// there is collision
				position++;
				position %= tableLength;
			}
			// update the hash table
			hashArray[position] = added;
			size++;

			loadFactor = (double) size / (double) tableLength;
		}
		// ensure load factor stays within 0.5
		rehash();
	}

	/**
	 * Returns the size, number of items, of the table
	 *
	 * @return the number of items in the table.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Displays the values of the table If an index is empty, it shows ** If
	 * previously existed item is deleted, then it should show #DEL#
	 */
	@Override
	public void display() {
		for (DataItem di : hashArray) {
			// null
			if (di == null)
				System.out.print("** ");
			// deleted
			else if (di == DELETED)
				System.out.print("#DEL# ");
			// occupied
			else
				System.out.print("[" + di.value + ", " + di.frequency + "] ");
		}
		System.out.println();
	}

	/**
	 * Returns true if value is contained in the table
	 *
	 * @param key
	 *            String key value to be searched
	 * @return true if found, false if not found.
	 */
	@Override
	public boolean contains(String key) {
		// null key
		if (key == null)
			return false;
		int hashVal = hashValue(key);
		// invalid words' hash value is -1
		if (hashVal != -1) {
			// within a period of collision area
			while (hashArray[hashVal] != null) {
				// found
				if (key.equals(hashArray[hashVal].value))
					return true;
				hashVal++;
				hashVal %= tableLength;
			}
		}
		return false;
	}

	/**
	 * Returns the number of collisions in relation to insert and rehash.
	 *
	 * When rehashing process happens, the number of collisions should be
	 * properly updated.
	 *
	 * The definition of collision is
	 * "two different keys map to the same hash value."
	 *
	 * Be careful with the situation where you could over count. Try to think as
	 * if you are using separate chaining!
	 * "How would you count the number of collisions in separate chaining?"
	 *
	 * @return number of collisions
	 */
	@Override
	public int numOfCollisions() {
		// all hash values of all data items
		int[] hashVals = new int[size];
		int num = 0, numOfCollisions = 0;
		// initialize hashVals
		for (DataItem di : hashArray) {
			if (di != null && di != DELETED)
				hashVals[num++] = hashFunc(di.value);
		}
		// boolean array to check if the item is already checked, default as all
		// false
		boolean[] bools = new boolean[num];

		// check the duplicated data items
		for (int i = 0; i < num; i++) {
			if (bools[i] == false) {
				for (int j = i + 1; j < num; j++) {
					if (bools[j] == false) {
						if (hashVals[i] == hashVals[j]) {
							numOfCollisions++;
							bools[i] = true;
							bools[j] = true;
						}
					}
				}
			}

		}
		return numOfCollisions;
	}

	/**
	 * Returns the hash value of a String
	 *
	 * @param value
	 *            value for which the hash value should be calculated
	 * @return int hash value of a String.
	 */
	@Override
	public int hashValue(String value) {
		// check is valid word
		if (isValidWord(value)) {
			// change the value to all lower letters
			return hashFunc(value);
		}
		// invalid words' hash value is -1
		return -1;
	}

	/**
	 * Returns the frequency of a key String
	 *
	 * @param key
	 *            key string value to find its frequency
	 * @return frequency value if found. If not found, return 0
	 */
	@Override
	public int showFrequency(String key) {
		int hashVal = hashValue(key);
		// valid words
		if (hashVal != -1) {
			// check the period of items that the key may exist
			while (hashArray[hashVal] != null) {
				// found
				if (key.equals(hashArray[hashVal].value))
					return hashArray[hashVal].frequency;
				hashVal++;
				hashVal %= tableLength;
			}
		}
		return 0;
	}

	/**
	 * Removes and returns removed value
	 *
	 * @param value
	 *            String value to be removed
	 * @return value that is removed
	 */
	@Override
	public String remove(String key) {
		int hashVal = hashValue(key);
		if (hashVal != -1) {
			while (hashArray[hashVal] != null) {
				// find the key needed to remove
				if (key.equals(hashArray[hashVal].value)) {
					String removed = hashArray[hashVal].value;

					hashArray[hashVal] = DELETED;
					size--;

					// update the load factor
					loadFactor = (double) size / (double) tableLength;

					return removed;
				}
				hashVal++;
				hashVal %= tableLength;
			}
		}
		// not found
		return key;
	}

	/*
	 * Instead of using String's hashCode, you are to implement your own here,
	 * taking the table length into your account.
	 * 
	 * In other words, you are to combine the following two steps into one step
	 * here. 1. converting Object into integer value 2. compress into the table
	 * using modular hashing (division method)
	 * 
	 * Helper method to hash a string for English lowercase alphabet and blank,
	 * we have 27 total. But, you can assume that blank will not be added into
	 * your table. Refer to the instructions for the definition of words.
	 * 
	 * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
	 * 
	 * But, to make the hash process faster, Horner's method should be applied
	 * as follows;
	 * 
	 * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
	 * (((var4*n + var3)*n + var2)*n + var1)*n + var0
	 * 
	 * Note: You must use 27 for this homework.
	 * 
	 * However, if you have time, I would encourage you to try with other
	 * constant values other than 27 and compare the results but it is not
	 * required.
	 */
	private int hashFunc(String input) {
		int symbolNum = 0;
		for (int i = 0; i < input.length(); i++) {
			// this happens when there is a upper case
			if ((input.charAt(i) - 'a' + 1) < 0)
				return -1;
			// Horner's method
			symbolNum = (ALPHABET_NUM * symbolNum + (input.charAt(i) - 'a' + 1))
					% tableLength;
		}
		return symbolNum;
	}

	// doubles array length and rehash items whenever the load factor is reached
	private void rehash() {
		if (loadFactor > LOAD_FACTOR) {
			int newSize = nextPrime(tableLength * 2);
			System.out.println("Rehashing " + size + " items, new size is "
					+ newSize);
			// make a new MyHashTable to store a new instance
			MyHashTable newHashTable = new MyHashTable(newSize);
			for (int i = 0; i < hashArray.length; i++) {
				// insert non-null and non-deleted item to new hash table
				if (hashArray[i] != null && hashArray[i] != DELETED) {

					// in case some items' frequency is bigger than 1
					while (hashArray[i].frequency > 0) {
						newHashTable.insert(hashArray[i].value);
						hashArray[i].decrementFreq();
					}
				}
			}
			// copy information
			hashArray = newHashTable.hashArray;
			tableLength = newHashTable.tableLength;
			size = newHashTable.size;
			loadFactor = newHashTable.loadFactor;
		}
	}

	// find next prime number after num
	// find the next prime number of a given number
	private int nextPrime(int num) {
		while (!isPrime(num)) {
			num++;
		}
		return num;
	}

	// check if num is prime

	// check if a number is a prime number
	private boolean isPrime(int num) {
		if (num == 2)
			return true;
		if (num < 2)
			return false;
		if (num % 2 == 0)
			return false;
		for (int i = 3; i < Math.sqrt(num); i++) {
			if (num % i == 0)
				return false;
		}
		return true;
	}

	// check if a word is valid, which is from a-z or A-Z
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

	// private static data item nested class
	private static class DataItem {
		private String value;
		private int frequency;

		/**
		 * Constructor
		 * 
		 * @param newValue
		 *            newValue to set
		 */
		public DataItem(String newValue) {
			value = newValue;
			frequency = 1;
		}

		/**
		 * Constructor with newFreq
		 * 
		 * @param newValue
		 *            newValue to set
		 * @param newFreq
		 *            newFreq to set
		 */
		public DataItem(String newValue, int newFreq) {
			value = newValue;
			frequency = newFreq;
		}

		/**
		 * increment frequency
		 */
		public void incrementFreq() {
			frequency++;
		}

		/**
		 * decrement frequency
		 */
		public void decrementFreq() {
			frequency--;
		}
	}
}