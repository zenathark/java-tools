import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.function.*;

interface StateFn extends Supplier<StateFn> {}
    /** Aho-Corasick Node Three.
	Recursive Structure that represents an Aho-Cotasick Tree.
     */
class AhoCorasickTree {
    //--Trie
    HashMap<Character, AhoCorasickTree> childs;
    Optional<Character> chr;           // Content of the node
    boolean isEnd;                     // isEnd stores the index of the word that makes it final.
    String word;                       // Stores the matching word
    Optional<AhoCorasickTree> parent;  // Double link for easy navigation
    //--AhoCorasick extra links
    Optional<AhoCorasickTree> fall;    // AC connection
    boolean isCompiled;

    public AhoCorasickTree() {
	this(null, null);
    }

    public AhoCorasickTree(Character chr, AhoCorasickTree parent) {
	this.chr = Optional.ofNullable(chr);
	this.parent = Optional.ofNullable(parent);
	childs = new HashMap<>();
	isEnd = false;
	fall = Optional.of(this);
	isCompiled = false;
    }

    public AhoCorasickTree addWord(String word) {
	return addWord(word.toCharArray());
    }

    public AhoCorasickTree addWord(char[] word) {
	return addWord(toObject(word));
    }

    public AhoCorasickTree addWord(Character[] word) {
	String s = new String(toPrimitive(word));
	AhoCorasickTree currentNode = this;
	AhoCorasickTree nextNode = null;
	for (Character e: word) {
	    nextNode = currentNode.childs.get(e);
	    if (nextNode == null) {
		nextNode = new AhoCorasickTree(e, currentNode);
		currentNode.childs.put(e, nextNode);
	    }
	    currentNode = nextNode;
	}
	currentNode.isEnd = true;
	currentNode.word = s;
	isCompiled = false;
	return this;
    }

    public AhoCorasickTree compile() {
	ArrayDeque<AhoCorasickTree> queue = new ArrayDeque<>();
	for (AhoCorasickTree e: this.childs.values())
	    queue.push(e);
	AhoCorasickTree currentNode = null;
	while (!queue.isEmpty()) {
	    currentNode = queue.pop();
	    Character chr = currentNode.chr.get();
	    for (AhoCorasickTree e: currentNode.childs.values())
		queue.addLast(e);
	    AhoCorasickTree fall = currentNode.parent.get().fall.get();
	    while (fall.childs.get(chr) == null && !fall.isRoot())
		fall = fall.fall.get();
	    currentNode.fall = Optional.ofNullable(fall.childs.get(chr));
	    if (!currentNode.fall.isPresent())
		currentNode.fall = Optional.of(this);
	    if (currentNode == currentNode.fall.get())
		currentNode.fall = Optional.of(this);
	}
	isCompiled = true;
	return this;
    }

    public boolean isRoot() {
	return !this.parent.isPresent();
    }

    public boolean isFinal() {
	return this.isEnd;
    }

    public static Character[] toObject(char[] array) {
	if (array == null) {
	    return null;
	} else if (array.length == 0) {
	    return new Character[0];
	}
	final Character[] result = new Character[array.length];
	for (int i = 0; i < array.length; i++) {
	    result[i] = Character.valueOf(array[i]);
	}
	return result;
    }

    public static char[] toPrimitive(Character[] array) {
	if (array == null) {
	    return null;
	} else if (array.length == 0) {
	    return new char[0];
	}
	final char[] result = new char[array.length];
	for (int i = 0; i < array.length; i++) {
	    result[i] = array[i].charValue();
	}
	return result;
    }

    public Iterator<String> iterator(String input) {
	return iterator(toObject(input.toCharArray()));
    }

    public Iterator<String> iterator(Character[] input) {
	if (!isCompiled)
	    this.compile();
	return new AhoCorasickTokenizer(input, this);
    }

    /*** Inner class for iterator <Guy Steele> style ***/
    class AhoCorasickTokenizer implements Iterator<String> {

	private AhoCorasickTree root;
	private AhoCorasickTree currentNode;
	private AhoCorasickTree tempNode;
	public Character[] input;
	private int pos;
	private String lastToken;

	private StateFn state;

	final StateFn finalState = () -> null;

	final StateFn initState = () -> {
	    Character e;
	    AhoCorasickTree currentNode = this.currentNode;
	    while (++this.pos < this.input.length) {
		e = (this.input)[pos];
		while (currentNode.childs.get(e) == null && !currentNode.isRoot())
		    currentNode = currentNode.fall.get();
		if (currentNode.isRoot()) {
		    currentNode = currentNode.childs.get(e);
		    if (currentNode == null) currentNode = this.root;
		} else
		    currentNode = currentNode.childs.get(e);
		this.currentNode = currentNode;
		if (currentNode.isFinal()) {
		    this.tempNode = currentNode;
		    this.lastToken = currentNode.word;
		    return this.onTokenState;
		}
	    }
	    this.currentNode = null;
	    return this.finalState;
	};

	final StateFn onTokenState = () -> {
		this.tempNode = this.tempNode.fall.get();
		if (this.tempNode.isFinal()) {
		    this.lastToken = tempNode.word;
		    return this.onTokenState;
		}
		else
		    return this.initState.get();
	};

	public AhoCorasickTokenizer(Character[] input, AhoCorasickTree root) {
	    this.input = input;
	    this.pos = -1;
	    this.root = root;
	    this.currentNode = root;
	    this.state = initState;
	    next();
	}

	@Override
	public String next() {
	    String ans = lastToken;
	    this.state = this.state.get();
	    return ans;
	}

	@Override
	public boolean hasNext() {
	    return  this.state != finalState;
	}

    }
}
