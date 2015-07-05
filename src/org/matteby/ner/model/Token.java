package org.matteby.ner.model;

/**
 * A data structure to represent a token. A token is a part of a sentence.
 */
public class Token implements ISentencePart {

	private String value;

	public Token(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
