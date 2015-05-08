/*****************************************************
 *
 * 08-722 Data Structures for Application Programmers
 * Homework 6: Building Index using BST
 *
 * Andrew ID: ruz
 * Name: Ru Zhao
 *
 *****************************************************/
import java.io.*;
import java.util.*;

public class Index {

	/**
	 * Build a tree giving a file name
	 *
	 * @param fileName
	 *            - input file name
	 * @return BST object
	 */
	public BST<Word> buildIndex(String fileName) {
		return buildIndex(fileName, null);
	}

	/**
	 * Build a tree with a file name and comparator object
	 *
	 * @param fileName
	 *            - input file name
	 * @param comparator
	 *            - comparator to be used
	 * @return BST object
	 */
	public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {
		BST<Word> bst = new BST<Word>(comparator);

		if (fileName == null)
			return null;

		// the set of words read from the file
		List<Word> wordNodes = new ArrayList<Word>();

		Scanner scanner = null;
		int lineNum = 0;
		try {
			scanner = new Scanner(new File(fileName));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNum++;
				String[] wordStrings = line.split("\\W");
				for (String wordString : wordStrings) {
					if (isValidWord(wordString)) {
						// check if the comparator is IgnoreCase
						if (comparator instanceof IgnoreCase) {
							wordString = wordString.toLowerCase();
						}
						// build the new word node
						Word newWordNode = new Word(wordString);
						boolean contain = false;
						for (Word wordNode : wordNodes) {
							if (wordNode.compareTo(newWordNode) == 0) {
								// add the index and frequency
								wordNode.addIndex(lineNum);
								wordNode.setFrequency(wordNode.getFrequency() + 1);
								// found the word, so no need to add a new one
								// to the set
								contain = true;
								break;
							}
						}
						// not found the word in set, add a new one
						if (!contain) {
							// do not forget to add the index
							newWordNode.addIndex(lineNum);
							wordNodes.add(newWordNode);
						}
						// reset the flag
						contain = false;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null)
				scanner.close();
		}

		for (Word word : wordNodes)
			bst.insert(word);
		return bst;
	}

	/**
	 * Build a tree with a given list and comparator
	 *
	 * @param list
	 *            - ArrayList of words
	 * @param comparator
	 *            - comparator to be used
	 * @return BST object
	 */
	public BST<Word> buildIndex(ArrayList<Word> list,
			Comparator<Word> comparator) {
		BST<Word> bst = new BST<Word>(comparator);

		// when list is null, return a empty tree.
		if (list == null)
			return bst;

		for (Word wordNode : list) {
			bst.insert(wordNode);
		}
		return bst;
	}

	/**
	 * Sort words alphabetically Note: Should keep the state of the tree
	 *
	 * @param tree
	 *            - BST tree
	 * @return ArrayList of words sorted alphabetically
	 */
	public ArrayList<Word> sortByAlpha(BST<Word> tree) {
		if (tree == null)
			return null;
		ArrayList<Word> words = new ArrayList<Word>();
		for (Word word : tree)
			words.add(word);

		Collections.sort(words, new Comparator<Word>() {
			@Override
			public int compare(Word w1, Word w2) {
				return w1.compareTo(w2);
			}
		});
		return words;
	}

	/**
	 * Sort words by frequency Note: Should keep the state of the tree
	 *
	 * @param tree
	 *            - BST tree
	 * @return ArrayList of words sorted by frequency
	 */
	public ArrayList<Word> sortByFrequency(BST<Word> tree) {
		if (tree == null)
			return null;
		ArrayList<Word> words = new ArrayList<Word>();
		for (Word word : tree)
			words.add(word);
		// just use the existing comparator
		Collections.sort(words, new Frequency());
		return words;
	}

	/**
	 * Find all words of the highest frequency Note: Should keep the state of
	 * the tree
	 *
	 * @param tree
	 *            - BST tree
	 * @return ArrayList of words that have highest frequency
	 */
	public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
		if (tree == null)
			return null;
		// get the sorted list by frequency
		ArrayList<Word> words = sortByFrequency(tree);
		ArrayList<Word> result = new ArrayList<Word>();
		int i = 0;
		while (i < words.size()) {
			result.add(words.get(i));
			i++;
			// running to the last item
			if (i >= words.size())
				break;
			// find a smaller frequency than before
			if (words.get(i).getFrequency() < words.get(i - 1).getFrequency())
				break;
		}
		return result;
	}

	/**
	 * helper method to check if a word is valid, considering the null input
	 */
	private boolean isValidWord(String word) {
		if (word == null)
			return false;
		if (!word.matches("[a-zA-Z]+"))
			return false;
		return true;
	}
}