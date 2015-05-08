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
 * sorts words according to their frequencies. A word with highest frequency
 * comes first.
 * 
 * @author zhaoru
 *
 */
public class Frequency implements Comparator<Word> {

	@Override
	public int compare(Word w1, Word w2) {
		return w2.getFrequency() - w1.getFrequency();
	}
}