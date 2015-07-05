package org.matteby.ner.model;

/**
 * A data structure to represent a token.
 */
public class Token {

	private String value;

	public Token(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
