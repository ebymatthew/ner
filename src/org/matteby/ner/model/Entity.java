package org.matteby.ner.model;

import java.util.LinkedList;
import java.util.List;

/**
 * A data structure to represent an Entity as list of tokens. An entity is a
 * sentence part.
 */
public class Entity implements ISentencePart {

	// store the list of tokens as an ArrayList
	// so far the only uses of entity are:
	// - appending tokens to the end: O(1)
	// - traversing to write XML or format as String: O(n)
	//
	// ArrayList will be faster than a LinkedList for sequential read & write
	// and is more spatially compact.
	//
	// There is no need for thread safety. ArrayList isn't synchronized
	// so it will be faster than synchronized structures.
	private LinkedList<Token> tokens = new LinkedList<Token>();

	/**
	 * Append a token to the end of the entity
	 */
	public void appendToken(Token token) {
		tokens.add(token);
	}

	/**
	 * Get a list of tokens in the entity
	 */
	public List<Token> getTokens() {
		return tokens;
	}
	
	/*
	 * String representation of an Entity is a space delimited list of the tokens
	 */
	@Override
	public String toString() {
		StringBuilder entityString = new StringBuilder();
		for(Token t : tokens) {
			entityString.append(t.getValue());
			entityString.append(" ");
		}
		return entityString.toString().trim();
	}
}
