package org.matteby.ner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A data structure to represent a document as list of sentences.
 */
public class Document {

	// store the list of sentences as a ArrayList
	// so far the only uses of document are:
	// - appending sentences to the end: O(1)
	// - traversing to write XML: O(n)
	// 
	// ArrayList will be faster than a LinkedList for sequential read & write
	// and is more spatially compact.
	//
	// There is no need for thread safety. ArrayList isn't synchronized
	// so it will be faster than synchronized structures.
	private List<Sentence> sentences = new ArrayList<Sentence>();
	
	// name of document
	private String name;

	/**
	 * Append a sentence to the end of the Document
	 */
	public void appendSentence(Sentence sentence) {
		sentences.add(sentence);
	}

	/**
	 * Get a list of sentences in the document
	 */
	public List<Sentence> getSentences() {
		return sentences;
	}

	/**
	 * Get name of document
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name of document
	 */
	public void setName(String name) {
		this.name = name;
	}
}