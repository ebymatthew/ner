package org.matteby.ner.scanner;

import java.io.Reader;

/**
 * TokenScanner takes a Reader as an input text source and implements the
 * Iterator<String> interface. Iterating over this class will will return a
 * sequence of English language tokens in the in input source.
 */
public class TokenScanner extends NERScanner {

	// an array of regex delimiters used to tokenize input text
	// this only works on a subset of the English language found in nlp_data.txt
	private static final String[] DELIMITERS = new String[] {
			// split on whitespace, and consume whitespace
			"\\s+",
			// split immediately after an ellipsis
			"(?<=[.]{3})",
			// split immediately before an ellipsis
			"(?=[.]{3})",
			// split immediately after a period that is not preceded or
			// succeeded by a period and is not succeeded by a digit
			"(?<=[^.][.])(?=[^.\\d])",
			// split immediately before a period that is not preceded by a
			// period or digit and is not succeed by a period or is succeeded by
			// an end of string
			"(?<=[^.\\d])(?=([.]([^.]|$)))",
			// split immediately before a period that is preceeded by a digit
			// and is not succeded by a digit
			"(?<=[\\d])(?=([.](\\D|$)))",
			// split immediately after any of the following punctuation: ",()?
			"(?<=[\",()?])",
			// split immediately before any of the following punctuation: ",()?':;
			"(?=[\",()?':;])",
			// split immediately after an apostrophe that is not
			// succeeded by an s
			"(?<=['])(?=([^s]))" };

	/**
	 * Constructor for TokenScanner expects a Reader as an input source of
	 * English text.
	 */
	public TokenScanner(Reader input) {
		super(input);
	}

	/**
	 * Override getDelimiterArray() to provide the list of delimiters used to
	 * tokenize text
	 */
	@Override
	protected String[] getDelimiterArray() {
		return DELIMITERS;
	}
}
