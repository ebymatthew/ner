package org.matteby.ner.trie;

import java.util.HashMap;
import java.util.List;

/**
 * An implementation of Trie
 */
public class Trie {

	// Trie will be created once and read many times
	// use a HashMap for O(1) lookup
	private HashMap<String, Trie> children = new HashMap<String, Trie>();

	// a flag to determine if this trie node is the terminus of a full match 
	private boolean terminal = false;

	// not worring about efficiency of add since this is a single static cost
	public void add(List<String> tokens) {
		if (tokens.isEmpty()) {
			return;
		}

		// get the child TrieNode
		String firstToken = tokens.get(0);
		Trie childNode = children.get(firstToken);

		// if firstEntityToken not already in tree go ahead and add it
		if (childNode == null) {
			childNode = new Trie();
			children.put(firstToken, childNode);
		}

		List<String> tokenTail = tokens.subList(1, tokens.size());
		if (tokenTail.isEmpty()) {
			// if this was the last token this is a terminal node in the Trie
			childNode.terminal = true;
		} else {
			// recursively add rest of the tokens to child node
			childNode.add(tokenTail);
		}
	}

	// returns the child trie where the supplied token is the key
	// returns null of the key is not in the trie
	// O(n) performance
	public Trie getChild(String token) {
		return children.get(token);
	}

	// returns true if this trie node is a terminal node
	// and hence a full match
	public boolean isTerminal() {
		return terminal;
	}
}
