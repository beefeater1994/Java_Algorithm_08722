/************************************************************
 *
 * 08-722 Data Structures for Applications Programmers Homework Assignment 3
 * SortedLinkedList Implementation with Recursion
 *
 * Andrew ID: ruz Name: Ru Zhao
 *
 ************************************************************/

/**
 * This class is a sorted linked class
 * 
 * @author zhaoru
 *
 */
public class SortedLinkedList implements MyListInterface {

	private Node head;

	/**
	 * Constructor with no para
	 */
	public SortedLinkedList() {
		head = null;
	}

	/**
	 * Constructor with para
	 * 
	 * @param unsorted
	 *            the unsorted String list to initialize
	 */
	public SortedLinkedList(String[] unsorted) {
		head = null;
		Node node = head;
		int index = 0;
		initHelper(node, unsorted, index);
	}

	@Override
	public void add(String value) {
		if (value == null)
			return;
		// empty case
		if (isEmpty()) {
			head = new Node(value, null);
			return;
		}
		// do nothing if duplicated
		if (contains(value))
			return;
		Node node = head, pre = null;
		addHelper(pre, node, value);
	}

	@Override
	public int size() {
		int count = 0;
		Node node = head;
		return sizeHelper(node, count);
	}

	@Override
	public void display() {
		// edge case, empty list
		if (head == null) {
			System.out.println("[]");
			return;
		}
		Node node = head;
		System.out.print("[");
		displayHelper(node);
		System.out.println();
	}

	@Override
	public boolean contains(String key) {
		Node node = head;
		return containsHelper(node, key);
	}

	@Override
	public boolean isEmpty() {
		// no element
		return head == null;
	}

	@Override
	public String removeFirst() {
		if (head == null)
			return null;
		String first = head.data;
		head = head.next;
		return first;
	}

	@Override
	public String removeAt(int index) {
		// if the list is empty
		if (isEmpty())
			return null;
		// special case
		if (index == 0)
			return removeFirst();
		int count = 0;
		Node node = head;
		return removeAtHelper(node, count, index);
	}

	/**********************************************************
	 * Static Nested Node Class with String data
	 **********************************************************/
	private static class Node {
		private String data;
		private Node next;

		public Node(String data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	/* helper functions recursively */

	/**
	 * Recursively helper function for constructor
	 * 
	 * @param node
	 *            the node to recurse
	 * @param unsorted
	 *            unsorted list
	 * @param index
	 *            index to add each step
	 */
	private void initHelper(Node node, String[] unsorted, int index) {
		if (unsorted.length == 0 || unsorted == null)
			return;
		if (index >= unsorted.length)
			return;
		add(unsorted[index]);
		index++;
		initHelper(node, unsorted, index);
	}

	/**
	 * Recursively helper function for adding
	 * 
	 * @param pre
	 *            previous node
	 * @param node
	 *            current node
	 * @param value
	 *            value to insert
	 */
	private void addHelper(Node pre, Node node, String value) {
		// if empty list
		if (head == null && pre == null) {
			head = new Node(value, null);
			return;
		}
		// if value should be the last
		if (node == null) {
			pre.next = new Node(value, null);
			return;
		}
		// base case. When found the larger element to insert
		if (node.data.compareTo(value) > 0) {
			// new node to be added
			Node added = new Node(value, node);
			// if the node to be added is to the head
			if (pre == null)
				head = added;
			else {
				pre.next = added;
			}
			return;
		} else {
			pre = node;
			node = node.next;
			// if value is larger, find the next
			addHelper(pre, node, value);
		}
	}

	/**
	 * Recursively helper function for size
	 * 
	 * @param node
	 *            current node
	 * @param count
	 *            counter
	 * @return size of current node
	 */
	private int sizeHelper(Node node, int count) {
		// base case
		if (node == null)
			return count;
		else {
			// next element
			count++;
			return sizeHelper(node.next, count);
		}
	}

	/**
	 * Recursively helper function for display
	 * 
	 * @param node
	 *            current node
	 */
	private void displayHelper(Node node) {
		// last element to pring without comma
		if (node.next == null) {
			System.out.print(node.data + "]");
			return;
		} else {
			System.out.print(node.data + ", ");
			displayHelper(node.next);
		}
	}

	/**
	 * Recursively helper function for contain
	 * 
	 * @param node
	 *            current node
	 * @param key
	 *            String to compare
	 * @return true or false
	 */
	private boolean containsHelper(Node node, String key) {
		// reach the last element
		if (node == null)
			return false;
		else {
			// find the element
			if (node.data.equals(key))
				return true;
			return containsHelper(node.next, key);
		}
	}

	/**
	 * Recursively helper function for remove at
	 * 
	 * @param node
	 *            current node
	 * @param count
	 *            counter
	 * @param index
	 *            current index
	 * @return the String removed
	 */
	private String removeAtHelper(Node node, int count, int index) {
		// base case, when counted to the previous node of the index one
		if (count == index - 1) {
			String remove = node.next.data;
			node.next = node.next.next;
			return remove;
		} else {
			count++;
			// reach the last element
			if (node == null)
				return null;
			return removeAtHelper(node.next, count, index);
		}
	}
}