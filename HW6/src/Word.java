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
 * consists of a string of word name, a frequency, and a set of index
 * 
 * @author zhaoru
 *
 */
public class Word implements Comparable<Word> {

	private String word;
	private Set<Integer> index; // word's line number in the source text
	private int frequency; // counts occurrences of the word

	/**
	 * Constructor with a word of string
	 * 
	 * @param word
	 *            the word of string
	 */
	public Word(String word) {
		this.word = word;
		this.index = new HashSet<Integer>();
		this.frequency = 1;
	}

	/* Following are some getter and setter methods */

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Set<Integer> getIndex() {
		return index;
	}

	public void addIndex(Integer index) {
		this.index.add(index);
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/* override toString */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.word);
		sb.append(" ");
		sb.append(this.frequency);
		sb.append(" ");
		sb.append(index.toString());
		return sb.toString();
	}

	/* override compareTo */
	@Override
	public int compareTo(Word o) {
		return this.word.compareTo(o.word);
	}
}