package org.matteby.ner.concurrent;

import java.io.InputStreamReader;
import java.util.Queue;

import org.matteby.ner.DocumentProcessor;
import org.matteby.ner.model.Document;

/**
 * A worker thread that will process a document and place it into a thread safe
 * queue when finished.
 */
public class DocumentWorkerThread implements Runnable {

	// the input stream reader
	private InputStreamReader input;

	// a thread safe queue to place the document
	private Queue<Document> processedQueue;

	// constructor for DocumentWorkerThread
	public DocumentWorkerThread(InputStreamReader input, Queue<Document> processedQueue) {
		this.input = input;
		this.processedQueue = processedQueue;
	}

	// override the run method of Runnable	
	@Override
	public void run() {
		processDocument();
	}

	private void processDocument() {
		// do the document processing
		DocumentProcessor processor = new DocumentProcessor();
		Document doc = processor.processDocument(input);

		// add document to the processed queue
		processedQueue.add(doc);
	}
}