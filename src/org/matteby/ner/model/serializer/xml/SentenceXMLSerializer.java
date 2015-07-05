package org.matteby.ner.model.serializer.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.matteby.ner.model.Sentence;
import org.matteby.ner.model.Token;

/**
 * An XML serializer helper class for Token
 */
public class SentenceXMLSerializer {

	/**
	 * Serializes the supplied sentence to the supplied XMLStreamWriter
	 */
	public void serialize(Sentence sentence, XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("sentence");

		// serialize all the tokens in the sentence
		for (Token token : sentence.getTokens()) {
			TokenXMLSerializer tokenSerializer = new TokenXMLSerializer();
			tokenSerializer.serialize(token, writer);
		}

		writer.writeEndElement();
	}

}
