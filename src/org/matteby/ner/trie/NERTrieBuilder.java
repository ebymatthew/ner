package org.matteby.ner.trie;

import java.util.Arrays;

/**
 * A helper class that builds a trie of the entities
 * found in NER.txt
 */
public class NERTrieBuilder {
	
	// hard code data in Trie for NER.txt entities
	public static Trie getNERTrie() {
		
		Trie trie = new Trie();

		addEntity(new String[]{"Ernst", "Haeckel"}, trie);
		addEntity(new String[]{"Franz", "Ferdinand"}, trie);
		addEntity(new String[]{"Gavrilo", "Princip"}, trie);
		addEntity(new String[]{"Germany"}, trie);
		addEntity(new String[]{"Austria-Hungary"}, trie);
		addEntity(new String[]{"Yugoslavia"}, trie);
		addEntity(new String[]{"Serbia"}, trie);
		addEntity(new String[]{"Sarajevo"}, trie);
		addEntity(new String[]{"Europe"}, trie);
		addEntity(new String[]{"Euclid"}, trie);
		addEntity(new String[]{"Elements"}, trie);
		addEntity(new String[]{"Venice"}, trie);
		addEntity(new String[]{"Carl", "Benjamin", "Boyer"}, trie);
		addEntity(new String[]{"Bible"}, trie);
		addEntity(new String[]{"Broyden-Fletcher-Goldfarb-Shanno"}, trie); // converted to ASCII
		addEntity(new String[]{"Newton"}, trie);
		addEntity(new String[]{"BFGS"}, trie);
		addEntity(new String[]{"Montgomery", "Castle"}, trie);
		addEntity(new String[]{"Powys"}, trie);
		addEntity(new String[]{"Wales"}, trie);
		addEntity(new String[]{"England"}, trie);
		addEntity(new String[]{"Roger", "de", "Montgomery"}, trie);
		addEntity(new String[]{"Robert", "of", "Belleme"}, trie);
		addEntity(new String[]{"King", "Henry", "III"}, trie);
		addEntity(new String[]{"Llywelyn", "ap", "Gruffudd"}, trie);
		addEntity(new String[]{"Prince", "of", "Wales"}, trie);
		addEntity(new String[]{"Shrewsbury"}, trie);
		addEntity(new String[]{"Antikythera"}, trie);
		addEntity(new String[]{"Olympic", "Games"}, trie);
		addEntity(new String[]{"Japan"}, trie);
		addEntity(new String[]{"North", "America"}, trie);
		addEntity(new String[]{"Sun", "Microsystems"}, trie);
		addEntity(new String[]{"Sun"}, trie);
		addEntity(new String[]{"Oracle", "Corporation"}, trie);
		addEntity(new String[]{"Apollo", "11"}, trie);
		addEntity(new String[]{"Neil", "Armstrong"}, trie);
		addEntity(new String[]{"Buzz", "Aldrin"}, trie);
		addEntity(new String[]{"Michael", "Collins"}, trie);
		addEntity(new String[]{"Moon"}, trie);
		addEntity(new String[]{"Sea", "of", "Tranquility"}, trie);
		addEntity(new String[]{"Earth"}, trie);
		addEntity(new String[]{"Pacific", "Ocean"}, trie);
		addEntity(new String[]{"James", "Clerk", "Maxwell"}, trie);
		addEntity(new String[]{"Isaac", "Newton"}, trie);
		addEntity(new String[]{"Albert", "Einstein"}, trie);
		
		return trie;
	}
	
	private static void addEntity(String[] entity, Trie trie) {
		trie.add(Arrays.asList(entity));
	}

}
