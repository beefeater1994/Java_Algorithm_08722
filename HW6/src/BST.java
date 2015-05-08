/*****************************************************
 *
 * 08-722 Data Structures for Application Programmers
 * Homework 6: Building Index using BST
 *
 * Andrew ID: ruz
 * Name: Ru Zhao
 *
 *****************************************************/
import java.util.*;

/**
 * This class stands for a binary search tree
 * 
 * @author zhaoru
 *
 * @param <T>
 *            any type
 */
public class BST<T extends Comparable<T>> implements Iterable<T>,
		BSTInterface<T> {

	private Node<T> root;
	private Comparator<T> comparator;

	/**
	 * Constructor with no parameter
	 */
	public BST() {
		this(null);
	}

	/**
	 * Constructor with a comparator
	 * 
	 * @param comparator
	 *            the comparator to compare T
	 */
	public BST(Comparator<T> comparator) {
		this.comparator = comparator;
		root = null;
	}

	/**
	 * Returns the comparator used to order this collection.
	 *
	 * @return comparator
	 */
	public Comparator<T> comparator() {
		return comparator;
	}

	/**
	 * Returns the root data of this tree.
	 *
	 * @return root data
	 */
	public T getRoot() {
		if (root == null)
			return null;
		return root.data;
	}

	/**
	 * Returns the height of this tree. if the tree is empty or tree has only a
	 * root node, then the height of the tree is 0.
	 *
	 * @return int value of the height
	 */
	public int getHeight() {
		// deal with if the root is null. if no this line, result would be -1
		if (root == null)
			return 0;
		return getHeightHelper(root);
	}

	/**
	 * Recursive helper method
	 */
	private int getHeightHelper(Node<T> curNode) {
		// base case
		if (curNode == null)
			return -1;
		// recursive case
		return 1 + Math.max(getHeightHelper(curNode.left),
				getHeightHelper(curNode.right));
	}

	/**
	 * Returns the number of ndoes in this tree.
	 *
	 * @return int value of the number of nodes.
	 */
	public int getNumberOfNodes() {
		return getNumberOfNodesHelper(root);
	}

	/**
	 * Recursive helper method
	 */
	private int getNumberOfNodesHelper(Node<T> curNode) {
		// base case
		if (curNode == null)
			return 0;
		// recursive case
		return 1 + getNumberOfNodesHelper(curNode.left)
				+ getNumberOfNodesHelper(curNode.right);
	}

	/**
	 * Given the value (object) to be searched, it tries to find it.
	 *
	 * @param toSearch
	 *            - value to be searched
	 * @return The value (object) of the search result. If nothing found, null.
	 */
	@Override
	public T search(T toSearch) {
		return searchHelper(root, toSearch);
	}

	/**
	 * Recursive helper method
	 */
	private T searchHelper(Node<T> curNode, T toSearch) {
		// base case
		if (curNode == null)
			return null;

		// get the compare result using comparator or not
		int compareResult;
		if (comparator == null)
			compareResult = toSearch.compareTo(curNode.data);
		else
			compareResult = comparator.compare(toSearch, curNode.data);

		// recursive case
		if (compareResult < 0)
			return searchHelper(curNode.left, toSearch);
		else if (compareResult > 0)
			return searchHelper(curNode.right, toSearch);
		else
			return toSearch;
	}

	/**
	 * Inserts a value (object) to the tree. No duplicates allowed.
	 *
	 * @param toInsert
	 *            - a value (object) to be inserted to the tree.
	 */
	@Override
	public void insert(T toInsert) {
		if (root == null) {
			root = new Node<T>(toInsert);
			return;
		}
		insertHelper(root, root, toInsert, 0);
	}

	/**
	 * Recursive helper method
	 */
	private void insertHelper(Node<T> curNode, Node<T> parentNode, T toInsert,
			int result) {
		// base case
		if (curNode == null) {
			// result is to record whether to go left or right
			if (result < 0)
				parentNode.left = new Node<T>(toInsert);
			else if (result > 0)
				parentNode.right = new Node<T>(toInsert);
			// else, they are equal, we do not insert the duplicate one
			return;
		}

		// get the compare result using comparator or not
		int compareResult;
		if (comparator == null)
			compareResult = toInsert.compareTo(curNode.data);
		else
			compareResult = comparator.compare(toInsert, curNode.data);

		// recursive case
		if (compareResult < 0)
			insertHelper(curNode.left, curNode, toInsert, compareResult);
		else if (compareResult > 0)
			insertHelper(curNode.right, curNode, toInsert, compareResult);
		// else, they are equal, we do not insert the duplicate one
	}

	/**
	 * In-order iterator
	 *
	 * @return iterator object
	 */
	@Override
	public Iterator<T> iterator() {
		return new BSTIterator();
	}

	/**
	 * an iterator class used for an in-order traversal
	 * 
	 * @author zhaoru
	 *
	 */
	private class BSTIterator implements Iterator<T> {
		private Node<T> nextNode;
		// queue store the in-order structure of all nodes
		private Queue<Node<T>> queue = new ArrayDeque<Node<T>>();

		/**
		 * Constructor
		 */
		public BSTIterator() {
			if (root == null)
				nextNode = null;
			Node<T> curNode = root;
			// intermediate data structure to store left nodes
			Stack<Node<T>> stack = new Stack<Node<T>>();
			// iterate until we run out of nodes
			while (curNode != null || !stack.isEmpty()) {
				// find leftmost node of current node
				while (curNode != null) {
					stack.push(curNode);
					curNode = curNode.left;
				}
				// get leftmost node, denote it, and find its right node
				curNode = stack.pop();
				queue.offer(curNode);
				curNode = curNode.right;
			}
			// next node is the first one from the queue
			nextNode = queue.poll();
		}

		@Override
		public boolean hasNext() {
			return nextNode != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				return null;
			}
			T result = nextNode.data;
			// update the nextNode
			nextNode = queue.poll();
			return result;
		}

		@Override
		public void remove() {
			return;
		}
	}

	// private static nested class for Node
	private static class Node<T> {
		private T data;
		private Node<T> left;
		private Node<T> right;

		public Node(T data) {
			this(data, null, null);
		}

		public Node(T data, Node<T> left, Node<T> right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}

		public String toString() {
			return data.toString();
		}
	}

	/***********************************************************
	 *
	 * For a very simple debug purpose:
	 *
	 * Test your BST with this first to make sure your BST works. But, this is a
	 * starting point. Should test more!
	 *
	 ***********************************************************/
	public static void main(String[] args) {
		BST<Integer> b = new BST<Integer>();
		int[] ar = { 31, 16, 49, 5, 18, 51, 4, 13, 17, 19, 8 };
		for (Integer x : ar)
			b.insert(x);

		for (Integer x : b)
			System.out.print(x + " ");

		System.out.println();
		System.out.println(b.search(8));
		System.out.println(b.getHeight());
		System.out.println(b.getNumberOfNodes());
	}

}