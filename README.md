# NamedEntityRecognizer

NamedEntityRecognizer processes an input text source and produces an XML
document as a sequence of sentences that are sequences of tokens and entities.

## How to Use

### Setup

#### Prerequisites

* JDK 1.7 or greater

#### Distribution

A pre-built jar is checked in at dist/NamedEntityRecognizer.jar

### Running NamedEntityRecognizer

The usage documentation of NamedEntityRecognizer is printed below. The input parameter should be an English plain text file. The output parameter is the name of an XML file where the processed results are written.

<pre>
Usage: java -jar NamedEntityRecognizer.jar [options] &lt;input&gt; &lt;output&gt; 

   --help         print usage
</pre>

Example:
<pre>
cd dist
java -jar NamedEntityRecognizer.jar ../resources&#47;nlp&#95;data.txt ../resources&#47;nlp&#95;data.xml 
</pre>


## Design Overview

NamedEntityRecognizer converts an input text file to an XML representation of the language model saved to a file. This conversion takes place in two stages.

**Processing Stages**

1. A document processing stage converts the source file into in-memory data structures representing a primitive language model where sentences are lists of entities and tokens. The input text processing is implemented with InputStreams and custom Scanners which scan through the InputStreams emitting parts of speech to the next scanner in the pipeline.
2. A serialization stage persists the in-memory data structures as XML to disk. The serialization stage uses an XMLStreamWriter for streaming the data structures to disk.

**Entity Matching**

EntityScanner uses a trie data structure to match token streams with Entities. A trie is appropriate for efficiently doing longest matching sequence searches. It allows the matching of an entity in O(n) time where n is the length of the entity.

**Data Representation**

The sentence, entities and tokens are stored in array ArrayLists. There are two
use cases for these data structures in the program:

* appending sentences to the end: O(1)
* traversing to write XML: O(n)
 
ArrayList will be faster than a LinkedList for sequential read & write and is more spatially compact.

There is no need for thread safety. ArrayList isn't synchronized so it will be faster than concurrent structures that are synchronized.

**Serialization**

The serializers were are not part from the data structures since serialization format should be independent of the data. This leaves room for future migration to other formats (i.e. Binary or JSON)

### Assumptions

* The sample text is 100% representative any possible input.
* The Java platform's default charset is sufficient for the supplied text.
* The data structures described in the problem statement imply in-memory data structures. 
* Sentences are always small enough that there is not a performance impact to read the entire sentence into memory while scanning.

### Limitations

**Full Coverage of English Text**

The sample text does not offer anywhere near complete coverage of English language. More extensive testing would be needed on large corpus of text that has more complete coverage of English language structure.

**Efficiency Limitations**

_Regular expressions_

  * This implementation recompiles the regex for each instance of a class. This could be done once with singleton or static member.
  * Java's implementation of regular expressions uses recursive backtracking. Recursive backtracking can exhibit super linear performance scalability with certain pathological expressions. The expressions used in the implemented Scanners have bounded lookahead and lookbehind and should scale well. However, this choice may limit future scalability if new delimiter expressions are needed.
  
**Large Documents**
* Since the entire document is read into memory the upper bound on document size is limited by the configured max heap size of the JVM.  

**Named Entity Recognition**

* Entity recognition will only recognize entities previously encountered in the training sources (i.e. NER.txt).
* Entity recognition does not detect references to Entities. For example, when 'he' is a reference to a named entity 'Franz Ferdinand' it will not correlate 'he' to 'Franz Ferdinand'.

### Alternative Approaches

**Named Entity Recognition**

* To identify entities not previously encountered, probabilistic models are needed to for part of speech tagging. Hidden Markov Models and Averaged Percepton are two popular models for part of speech tagging.
* To associate entity references to an entity, parse trees are needed. Probablistic Context Free Grammars are commonly used to build parse trees.

**External Libraries**

* Existing NLP tokenization libraries such as Stanford's PTBTokenizer and DocumentPreprocessor have support for a much larger set of the English language and are well tested.

* External libraries exist that are well suited for representing modeling and persisting XML data (i.e. Eclipse Modeling Framework). For a simple models like that used in this utility that is overkill. For larger more complex models, these frameworks are useful for developer productivity and are well tested.

**Efficiency Improvements**

_Regular expressions_

* This implementation recompiles the regex for each instance of a class. This could be done once with singleton or static member.
  
* Russ Cox has a [good explanation] [1] of the conditions that cause regular expression matching to become super linear. Other libraries that implement Ken Thompson's NFA matching algorithm could be used to improve performance.

**Large Documents**

* The entire processing and persisting pipelines are implemented as Streams and Iterators. The input and output pipelines could be directly connected skipping the intermediate in-memory representation of the document. This would make the disk size the upper limit on input size or even larger if other text sources and sinks are used.

**Better Modularity and Abstraction**

* Time was not invested to abstract proper interfaces to the model, scanners and serializers. More thought would be needed in this area to create a distributable library.


[1]: https://swtch.com/~rsc/regexp/regexp1.html        "Russ Cox"

