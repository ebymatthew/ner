package org.matteby.ner.model.serializer.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.matteby.ner.model.Document;
import org.matteby.ner.model.Sentence;

/**
 * An XML serializer helper class for Document
 */
public class DocumentXMLSerializer {

	/**
	 * Serializes the supplied document to the supplied XMLStreamWriter
	 */
	public void serialize(Document document, XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("document");

		// serialize all the sentences in the document
		for (Sentence sentence : document.getSentences()) {
			SentenceXMLSerializer sentenceSerializer = new SentenceXMLSerializer();
			sentenceSerializer.serialize(sentence, writer);
		}

		writer.writeEndElement();
	}
}
