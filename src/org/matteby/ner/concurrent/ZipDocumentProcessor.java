package org.matteby.ner.concurrent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.matteby.ner.model.Document;
import org.matteby.ner.model.DocumentCollection;

/*
 * Takes a zip file and concurrently processes the documents
 */
public class ZipDocumentProcessor {

	public DocumentCollection processDocuments(ZipFile zipFile)
			throws FileNotFoundException, IOException, InterruptedException {

		// a list of yet to be processed documents as a list of input stream
		// readers
		// it does not need to be thread safe since this is the only thread that
		// will use it
		Queue<InputStreamReader> documentStreams = new LinkedList<InputStreamReader>();

		// a list of documents that have been processed. using a thread safe
		// list since multiple threads will put processed documents in the queue
		Queue<Document> documents = new ConcurrentLinkedQueue<Document>();

		// iterate over entries in zip file
		final Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			final ZipEntry entry = entries.nextElement();
			// don't care about directories
			if (!entry.isDirectory()) {
				String entryName = entry.getName();

				// ignore __MACOSX... there is probably a more elegant way to do
				if (!entryName.contains("__MACOSX")) {
					// get an InputStream for the entry and
					// add it to the Queue for processing
					InputStream inputStream = zipFile.getInputStream(entry);
					// read documents as UTF-8 to handle unicode input
					InputStreamReader input = new InputStreamReader(inputStream, "UTF-8");
					documentStreams.add(input);
				}
			}
		}

		// kick off processing threads
		// assume a fixed thread pool of size 5
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 10; i++) {
			// pass an InputStream to the DocumentWorkerThread and a thread safe
			// list of documents to store the output result
			Runnable worker = new DocumentWorkerThread(documentStreams.remove(), documents);
			// execute the worker thread
			executor.execute(worker);
		}
		// shutdown allows submitted tasks to execute before terminating.
		executor.shutdown();
		// wait until all threads are finished should finish in well under 10
		// minutes
		executor.awaitTermination(10, TimeUnit.MINUTES);

		// aggregate the document collection
		// assume order doesn't matter.
		DocumentCollection documentCollection = new DocumentCollection();
		while (!documents.isEmpty()) {
			documentCollection.appendDocument(documents.remove());
		}

		return documentCollection;
	}
}
