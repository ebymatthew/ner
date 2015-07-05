package org.matteby.ner.scanner;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.matteby.ner.model.Entity;
import org.matteby.ner.model.ISentencePart;
import org.matteby.ner.model.Token;
import org.matteby.ner.trie.NERTrieBuilder;
import org.matteby.ner.trie.Trie;

/**
 * EntityScanner takes an Iterator<String> as an input token source and
 * implements the Iterator<String> interface. Iterating over this class will
 * will return a sequence of English language sentence parts (Token or Entity)
 * in the input source.
 * 
 * EntityScanner uses a trie data structure to match token streams with
 * Entities. A trie is appropriate for efficiently doing longest matching
 * sequence searches. It allows the matching of an entity in O(n) time where n
 * is the length of the entity.
 */
public class EntityScanner implements Iterator<ISentencePart> {

	// an iterator of tokens to scan for entities
	private Iterator<String> inputIterator;

	// a deque of tokens that have been consumed from
	// the input token source but not returned as a part of sentence
	// from the previous iteration of this Iterator
	//
	// use a deque because tokens need to be add to both the
	// front and the back.
	private Deque<String> lookaheadTokens = new LinkedList<String>();

	// get the NER trie, it does not change so make it final
	private static final Trie rootTrie = NERTrieBuilder.getNERTrie();

	/**
	 * Constructor for TokenScanner expects an Iterator<String> as an input
	 * source of English language tokens.
	 */
	public EntityScanner(Iterator<String> input) {
		inputIterator = input;
	}

	/**
	 * Override the 'hasNext' of the Iterator interface.
	 */
	@Override
	public boolean hasNext() {
		return inputIterator.hasNext() || !lookaheadTokens.isEmpty();
	}

	/**
	 * Override the 'next' of the Iterator interface.
	 */
	@Override
	public ISentencePart next() {

		// return value
		ISentencePart sentencePart = null;

		// a deque of tokens taken, but not yet assigned to a sentence part
		// use a deque because tokens need to be taken from both the
		// front and the back
		Deque<String> tokenDeque = new LinkedList<String>();

		// add the current token to a list of tokens taken, but not
		// yet assigned to a sentence part
		tokenDeque.add(getNextToken());

		// test if the current token is part of an entity
		Trie trie = rootTrie;
		Entity entity = null;
		while ((trie = trie.getChild(tokenDeque.peekLast())) != null) {

			// if the current trie is a terminal trie an Entity has been matched
			// add all tokens in queue to the entity
			if (trie.isTerminal()) {

				// if no Entity has been created then create it
				if (entity == null) {
					entity = new Entity();
				}

				// add all the matched tokens to the Entity
				while (!tokenDeque.isEmpty()) {
					entity.appendToken(new Token(tokenDeque.removeFirst()));
				}

				// print out the recognized entity per problem statement
				System.out.println("Recognized Entity: " + entity.toString());
			}

			// get next token from input stream and put it
			// in the unassigned tokens deque
			tokenDeque.add(getNextToken());
		}

		if (entity != null) {
			// if an entity was created it is the sentence part
			sentencePart = entity;
		} else {
			// if no entity was matched take first unassigned token and
			// assign it to a Token sentence part
			sentencePart = new Token(tokenDeque.removeFirst());
		}

		// if there are remaining unassigned tokens add them to lookaheadTokens
		// so the can be part of the next sentence part
		while (!tokenDeque.isEmpty()) {
			// remove from tokenDequeue in reverse order
			// and push to the front of lookaheadTokens to
			// preserve ordering
			lookaheadTokens.addFirst(tokenDeque.removeLast());
		}

		return sentencePart;
	}

	/**
	 * Override the 'remove' of the Iterator interface.
	 */
	@Override
	public void remove() {
		next();
	}

	private String getNextToken() {
		String nextToken = null;
		if (!lookaheadTokens.isEmpty()) {
			// if a lookahead token was previously saved
			// it is the current token to process
			nextToken = lookaheadTokens.remove();
		} else {
			// if there is no lookahead token
			// get the current token from the input stream
			nextToken = inputIterator.next();
		}
		return nextToken;
	}
}
