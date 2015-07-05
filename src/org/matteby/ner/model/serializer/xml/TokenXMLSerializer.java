package org.matteby.ner.model.serializer.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.matteby.ner.model.Token;

/**
 * An XML serializer helper class for Token
 */
public class TokenXMLSerializer {

	/**
	 * Serializes the supplied token to the supplied XMLStreamWriter
	 */
	public void serialize(Token token, XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("token");
		writer.writeCharacters(token.getValue());
		writer.writeEndElement();
	}
}
