package org.matteby.ner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A data structure to represent a document collection as list of documents.
 */
public class DocumentCollection {

	// store the list of documents as a ArrayList
	// so far the only uses of document are:
	// - appending documents to the end: O(1)
	// - traversing to write XML: O(n)
	//
	// ArrayList will be faster than a LinkedList for sequential read & write
	// and is more spatially compact.
	//
	// There is no need for thread safety. ArrayList isn't synchronized
	// so it will be faster than synchronized structures.
	private List<Document> documents = new ArrayList<Document>();

	/**
	 * Append a document to the end of the collection
	 */
	public void appendDocument(Document document) {
		documents.add(document);
	}

	/**
	 * Get a list of document in the collection
	 */
	public List<Document> getDocuments() {
		return documents;
	}
}