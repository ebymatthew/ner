package org.matteby.ner.model.serializer.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.matteby.ner.model.Document;
import org.matteby.ner.model.DocumentCollection;

/**
 * An XML serializer helper class for DocumentCollection
 */
public class DocumentCollectionXMLSerializer {

	/**
	 * Serializes the supplied document to the supplied XMLStreamWriter
	 */
	public void serialize(DocumentCollection documentCollection, XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("documentCollection");

		// serialize all the sentences in the document
		for (Document document : documentCollection.getDocuments()) {
			DocumentXMLSerializer documentSerializer = new DocumentXMLSerializer();
			documentSerializer.serialize(document, writer);
		}

		writer.writeEndElement();
	}
}
