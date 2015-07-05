package org.matteby.ner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.matteby.ner.concurrent.ZipDocumentProcessor;
import org.matteby.ner.model.DocumentCollection;
import org.matteby.ner.model.serializer.xml.DocumentCollectionXMLSerializer;

/**
 * Main class for the NamedEntityRecognition program. Program processes an input
 * text source and produces an XML document as a sequence of sentences that are
 * sequences of tokens.
 */
public class NamedEntityRecognizer {

	/**
	 * Print usage information to stdout.
	 */
	private static void printUsage() {
		System.out.println("Usage: java -jar NamedEntityRecognizer.jar [options] <input> <output>");
		System.out.println("");
		System.out.println("   --help         print usage");
	}

	/**
	 * Parse and validate the input arguments.
	 */
	private static Map<String, String> parseArgs(String[] args) {
		Map<String, String> argMap = new HashMap<String, String>();

		// if help argument was passed, print usage and exit.
		for (String arg : args) {
			if (arg.equals("--help")) {
				printUsage();
				System.exit(0);
			}
		}

		// check that at least two arguments were passed
		if (args.length < 2) {
			printUsage();
			exitWithMessage("please supply both an input and output parameter");
		}

		// populate arguments
		argMap.put("input", args[args.length - 2]);
		argMap.put("output", args[args.length - 1]);

		// return the parsed arguments as a map
		return argMap;
	}

	/**
	 * Helper method to exit with an error message.
	 */
	private static void exitWithMessage(String message) {
		System.err.println(message);
		System.exit(1);
	};

	/**
	 * Helper method to validate input file path is okay.
	 */
	private static File checkInputPath(String path) {
		File input = new File(path);
		// check that input file exists
		if (!input.exists()) {
			exitWithMessage("Input zip file not found: " + input.getPath());
		} else if (!input.isFile()) {
			exitWithMessage("Input parameter is a directory. Please supply a zip file. Input: " + input.getPath());
		}
		return input;
	}

	/**
	 * Helper method to validate output file path is okay.
	 */
	private static File checkOutputPath(String path) {
		File output = new File(path);

		// if output already exists, ensure that it is not a directory
		if (output.exists()) {
			if (output.isDirectory()) {
				exitWithMessage("Output file is a directory. Please supply a file. Output: " + output.getPath());
			}
		}
		// if output does not exist, ensure that its parent directory does exist
		else {
			// check that output file directory exists
			File outputDirectory = new File(output.getParent());
			if (!outputDirectory.exists()) {
				exitWithMessage("Output file directory does not exist: " + output.getPath());
			}
		}

		return output;
	}

	public static void main(String[] args) {

		// parse arguments
		Map<String, String> argMap = parseArgs(args);

		// validate and get the input and output files
		File input = checkInputPath(argMap.get("input"));
		File output = checkOutputPath(argMap.get("output"));

		// process the input file into a Document data structure
		DocumentCollection documents = null;

		// zipFile is auto closeable so wrap it in try
		// to auto close
		try (ZipFile zipFile = new ZipFile(input)) {
			ZipDocumentProcessor processor = new ZipDocumentProcessor();
			documents = processor.processDocuments(zipFile);
		} catch (FileNotFoundException exception) {
			exitWithMessage("Input file not found: " + exception.getLocalizedMessage());
		} catch (ZipException exception) {
			exitWithMessage(
					"ZipException while processing input document. Ensure that the input is a valid zip file: " 
					+ exception.getLocalizedMessage());
		} catch (IOException exception) {
			exitWithMessage("IOException while processing input document: " + exception.getLocalizedMessage());
		} catch (InterruptedException exception) {
			exitWithMessage("InterruptedException while processing input document: " + exception.getLocalizedMessage());
		}

		// check that document processing succeeded
		if (documents == null) {
			exitWithMessage("Error processing document collection.");
		}

		// persist the DocumentCollection to XML
		// wrap the FileWriter in a try statement to auto close
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		try (FileWriter fileWriter = new FileWriter(output)) {
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			XMLStreamWriter writer = factory.createXMLStreamWriter(bufferedWriter);

			// write the xml document
			writer.writeStartDocument("1.0");
			DocumentCollectionXMLSerializer documentCollectionSerializer = new DocumentCollectionXMLSerializer();
			documentCollectionSerializer.serialize(documents, writer);
			writer.writeEndDocument();

			// flush the document
			writer.flush();
		} catch (XMLStreamException exception) {
			exitWithMessage("Error serializing XML document: " + exception.getLocalizedMessage());
		} catch (IOException exception) {
			exitWithMessage("Error writing XML document: " + exception.getLocalizedMessage());
		}
	}
}
