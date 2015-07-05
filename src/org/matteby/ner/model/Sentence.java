package org.matteby.ner.model;

import java.util.LinkedList;
import java.util.List;

/**
 * A data structure to represent a sentence as list of tokens.
 */
public class Sentence {

	// store the list of tokens as an ArrayList
	// so far the only uses of sentence are:
	// - appending tokens to the end: O(1)
	// - traversing to write XML: O(n)
	//
	// ArrayList will be faster than a LinkedList for sequential read & write
	// and is more spatially compact.
	//
	// There is no need for thread safety. ArrayList isn't synchronized
	// so it will be faster than synchronized structures.
	private LinkedList<Token> tokens = new LinkedList<Token>();

	/**
	 * Append a token to the end of the sentence
	 */
	public void appendToken(Token token) {
		tokens.add(token);
	}

	/**
	 * Get a list of tokens in the sentence
	 */
	public List<Token> getTokens() {
		return tokens;
	}
}
