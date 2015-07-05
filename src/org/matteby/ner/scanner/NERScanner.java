package org.matteby.ner.scanner;

import java.io.Reader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * NERScanner is an abstract class that provides functionality to iterate over
 * input text sources using custom delimiter regular expressions. Implementing
 * classes must override 'getDelimiterArray()' to return an array of regular
 * expression strings used to delimit the input text.
 * 
 * NERScanner implements the Iterator<String> interface. Iterating over this
 * class will will return a sequence of strings from the input source delimited
 * according to the regular expressions provided by 'getDelimiterArray()'.
 */
public abstract class NERScanner implements Iterator<String> {

	// compile the regular expression pattern
	private final Pattern delimiterPattern = Pattern.compile(getDelimiter());

	// scanner used to delimit text
	protected Scanner scanner;

	/**
	 * Constructor for SentenceScanner expects a Reader as an input source of
	 * text.
	 */
	public NERScanner(Reader input) {
		// create a scanner for the input
		scanner = new Scanner(input);
		// using the delimiter regex to lex
		scanner.useDelimiter(delimiterPattern);
	}

	/**
	 * Implementing classes should override 'getDelimiterArray' to provide an
	 * array of regular expressions which will be used to delimit the source
	 * text.
	 */
	protected abstract String[] getDelimiterArray();

	/**
	 * Build the delimiter regular expression from the list of delimiters
	 * provided by 'getDelimiterArray()'.
	 */
	private final String getDelimiter() {
		// build delimiters regular expression
		// as an 'or' of all expression in DELIMITERS
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		Boolean first = true;
		for (String s : getDelimiterArray()) {
			if (first) {
				first = false;
			} else {
				sb.append("|");
			}
			sb.append(s);
		}
		sb.append(")");

		return sb.toString();
	}

	/**
	 * Override the 'hasNext' of the Iterable interface.
	 */
	@Override
	public boolean hasNext() {
		return scanner.hasNext();
	}

	/**
	 * Override the 'next' of the Iterable interface.
	 */
	@Override
	public String next() {
		return scanner.next();
	}

	/**
	 * Override the 'remove' of the Iterable interface.
	 */
	@Override
	public void remove() {
		scanner.remove();
	}
}