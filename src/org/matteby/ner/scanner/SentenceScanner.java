package org.matteby.ner.scanner;

import java.io.Reader;

/**
 * SentenceScanner takes a Reader as an input text source and implements the
 * Iterator<String> interface. Iterating over this class will will return a
 * sequence of English language sentences in the in input source.
 */
public class SentenceScanner extends NERScanner {

	// an array of regex delimiters used to delimit sentences in an input text
	// source this only works on a subset of the English language found in
	// nlp_data.txt
	private static final String[] DELIMITERS = new String[] {
			// split immediately after a single period that is not preceeded by a period
			// or preceeded by 'Inc' and is not succeed by a period, quotation mark or digit
			"(?<=(?<!([.]|Inc))[.])(?=[^\".\\d])",
			// split immediately after a question mark or exclamation mark
			// that is not succeeded by a quotation mark
			"(?<=[!?])(?=[^\"])",
			// split immediately after a quotation mark that preceded by an end
			// of sentence mark
			"(?<=[.!?][\"])" };

	/**
	 * Constructor for SentenceScanner expects a Reader as an input source of
	 * English text.
	 */
	public SentenceScanner(Reader input) {
		super(input);
	}	
	
	/**
	 * Override the 'next' of the Iterator interface.
	 */
	@Override
	public String next() {
		
		// the sentence scanner also replaces unicode characters with the ascii equivalent
		// this is the wrong place to this but it works for the test data set.
		// should be done before sentence delimiting occurs and should use a utility
		// that does general purpose ASCII conversion
		String token = scanner
				.next()
				.replace('\u2013', '-')
				.replace('\u00d7', 'x');
		
		return token;
	}

	/**
	 * Override getDelimiterArray() to provide the list of delimiters used to
	 * delimit English language sentences
	 */
	@Override
	protected String[] getDelimiterArray() {
		return DELIMITERS;
	}
}
