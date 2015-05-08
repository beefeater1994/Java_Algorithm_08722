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
 * sorts words according to alphabets first and if there is a tie, then words
 * are sorted by their frequencies.
 * 
 * @author zhaoru
 *
 */
public class AlphaFreq implements Comparator<Word> {

	@Override
	public int compare(Word w1, Word w2) {
		int result = w1.getWord().compareTo(w2.getWord());
		if (result != 0)
			return result;
		else
			return w1.getFrequency() - w2.getFrequency();
	}
}