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
 * sorts words by case insensitive alphabetical order. In other words, if this
 * comparator is passed into buildIndex method, then all of the words need to be
 * converted into lowercase and then added into the BST
 * 
 * @author zhaoru
 *
 */
public class IgnoreCase implements Comparator<Word> {

	@Override
	public int compare(Word w1, Word w2) {
		return w1.getWord().compareToIgnoreCase(w2.getWord());
	}
}