import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Scanner;

/***********************************************************
 *
 * 08-722 Data Structures for Application Programmers Homework Assignment 5
 * Document Similarity
 *
 * Andrew ID: ruz Name: Ru Zhao
 *
 ***********************************************************/

public class Similarity {
	// Parameters used for sqrt of a BigDecimal. SQRT_DIG of 1000 yields 1570
	// digits of precision.
	private static final BigDecimal SQRT_DIG = new BigDecimal(1000);
	private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

	private HashMap<String, BigInteger> map;
	private int numLines, numWords;

	/**
	 * Constructor for reading Strings
	 * 
	 * @param str
	 *            string input
	 */
	public Similarity(String str) {
		init();
		readString(str);
	}

	/**
	 * Constructor for reading files
	 * 
	 * @param filename
	 *            the file
	 */
	public Similarity(File filename) {
		init();
		readFile(filename);
	}

	/**
	 * get the number of lines
	 * 
	 * @return number of lines
	 */
	public int numberOfLines() {
		return numLines;
	}

	/**
	 * get the number of words
	 * 
	 * @return number of words
	 */
	public int numOfWords() {
		return numWords;
	}

	/**
	 * get the number of words which are no duplication
	 * 
	 * @return number of words which are no duplication
	 */
	public int numOfWordsNoDups() {
		return map.size();
	}

	/**
	 * get the map
	 * 
	 * @return the map
	 */
	public HashMap<String, BigInteger> getMap() {
		return map;
	}

	/**
	 * calculate euclideanNorm with no parameter, which would use the map
	 * 
	 * @return this map's euclideanNorm
	 */
	public double euclideanNorm() {
		return euclideanNorm(map);
	}

	/**
	 * calculate the dot product, in linear running time
	 * 
	 * @param newMap
	 *            another map to parse into to calculate the dot product
	 * @return the dot product of this map and newMap
	 */
	public double dotProduct(HashMap<String, BigInteger> newMap) {
		if (newMap == null)
			return 0;
		BigInteger dotProduct = BigInteger.ZERO;
		for (String word : map.keySet()) {

			// the common words of the two maps
			if (newMap.containsKey(word))
				dotProduct = dotProduct.add(map.get(word).multiply(newMap.get(word)));
		}
		return dotProduct.doubleValue();
	}

	/**
	 * calculate the distance between two inputs
	 * 
	 * @param newMap
	 *            another map to parse into to calculate the distance
	 * @return the distance of this map and newMap
	 */
	public double distance(HashMap<String, BigInteger> newMap) {
		// handle this unfriendly input, which should not happen
		if (newMap == null)
			return Math.PI / 2;

		// calculate the two euclideanNorm
		double freq1 = euclideanNorm(), freq2 = euclideanNorm(newMap);

		// when at least one map is empty, which is from a empty file
		if (freq1 == 0 || freq2 == 0)
			return Math.PI / 2;

		// when the two are same files/Strings
		if (freq1 == freq2)
			return 0;

		double distance = dotProduct(newMap) / freq1 / freq2;
		return Math.acos(distance);
	}

	/* Helper Methods */

	/**
	 * Initiate the fields
	 */
	private void init() {
		map = new HashMap<String, BigInteger>();
		numLines = 0;
		numWords = 0;
	}

	/**
	 * helper method to read a string, within constructor
	 */
	private void readString(String str) {
		if (str == null)
			return;
		// only increment once, number of line could always be 1
		numLines++;
		String[] wordsFromStr = str.split("\\W");
		for (String word : wordsFromStr) {
			if (isValidWord(word)) {
				// to lower
				String lowWord = word.toLowerCase();

				// no such key in map
				if (!map.containsKey(lowWord)) {
					map.put(lowWord, BigInteger.ONE);
				} else {
					// key already exists
					map.put(lowWord, map.get(lowWord).add(BigInteger.ONE));
				}
				// increment the word count
				numWords++;
			}
		}
	}

	/**
	 * helper method to read a file, within constructor
	 */
	private void readFile(File filename) {
		if (filename == null)
			return;
		Scanner scanner = null;
		try {
			scanner = new Scanner(filename, "latin1");

			// while the file still has something to read
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] wordsFromText = line.split("\\W");
				for (String word : wordsFromText) {
					if (isValidWord(word)) {
						// to lower
						String lowWord = word.toLowerCase();

						// no such key in map
						if (!map.containsKey(lowWord)) {
							map.put(lowWord, BigInteger.ONE);
						} else {
							// key already exists
							map.put(lowWord, map.get(lowWord).add(BigInteger.ONE));
						}
						// increment the word count
						numWords++;
					}
				}
				// increment the line count
				numLines++;
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			// close the file
			if (scanner != null)
				scanner.close();
		}
	}

	/**
	 * helper method to calculate euclideanNorm given a map parameter
	 */
	private double euclideanNorm(HashMap<String, BigInteger> newMap) {
		// handle this unfriendly input, which should not happen
		if (newMap == null)
			return 0;
		BigInteger euclideanNorm = BigInteger.ZERO;
		for (BigInteger i : newMap.values())
			euclideanNorm = euclideanNorm.add(i.pow(2));
		// change from BigInteger to BigDecimal, which is used for calculating square root
		BigDecimal result = new BigDecimal(euclideanNorm);
		return bigSqrt(result).doubleValue();
	}

	/**
	 * use Newton Raphson to compute the square root of a BigDecimal
	 */
	private BigDecimal bigSqrt(BigDecimal c) {
		return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE));
	}

	/**
	 * helper method used to compute the square root of a BigDecimal using Newton Raphson method
	 */
	private BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
		BigDecimal fx = xn.pow(2).add(c.negate());
		BigDecimal fpx = xn.multiply(new BigDecimal(2));
		BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(),
				RoundingMode.HALF_DOWN);
		xn1 = xn.add(xn1.negate());
		BigDecimal currentSquare = xn1.pow(2);
		BigDecimal currentPrecision = currentSquare.subtract(c);
		currentPrecision = currentPrecision.abs();
		if (currentPrecision.compareTo(precision) <= -1) {
			return xn1;
		}
		return sqrtNewtonRaphson(c, xn1, precision);
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