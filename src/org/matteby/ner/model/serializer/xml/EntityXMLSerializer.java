package org.matteby.ner.model.serializer.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.matteby.ner.model.Entity;
import org.matteby.ner.model.Token;

/**
 * An XML serializer helper class for Entity
 */
public class EntityXMLSerializer {

	/**
	 * Serializes the supplied entity to the supplied XMLStreamWriter
	 */
	public void serialize(Entity entity, XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("entity");
		
		// serialize all the tokens in the sentence
		for (Token token : entity.getTokens()) {
			TokenXMLSerializer tokenSerializer = new TokenXMLSerializer();
			tokenSerializer.serialize(token, writer);
		}

		writer.writeEndElement();
	}
}
