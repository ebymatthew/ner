package org.matteby.ner.model.serializer.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.matteby.ner.model.Entity;
import org.matteby.ner.model.ISentencePart;
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
		for (ISentencePart sentencePart : sentence.getSentenceParts()) {
			// would be cleaner to use polymorphism here, but the goal for now
			// is to get this done
			if (sentencePart instanceof Token) {
				TokenXMLSerializer tokenSerializer = new TokenXMLSerializer();
				tokenSerializer.serialize((Token) sentencePart, writer);
			} else if (sentencePart instanceof Entity) {
				EntityXMLSerializer entitySerializer = new EntityXMLSerializer();
				entitySerializer.serialize((Entity) sentencePart, writer);
			}
		}

		writer.writeEndElement();
	}

}
