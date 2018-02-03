import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.function.*;

public class FastACTree {
    //Trie
    final FastACTree[] childs = new FastACTree['z'-'a'+1];
    final FastACTree   parent;

    char       chr;
    boolean    isEnd;
    String     word;
    FastACTree fall;


    private int size;
    private boolean isCompiled;

    public FastACTree() {
	this((char) 0, null);
    }

    public FastACTree(char chr, FastACTree parent) {
	this.chr        = chr;
	this.parent     = parent;
	this.isEnd      = false;
	this.fall       = this;
	this.isCompiled = false;
	this.size       = 0;
    }


    // returns a child marked with the chr, null if there is no path
    public FastACTree getChild(char chr) {
	return this.childs[chr - 'a'];
    }

    // adds a child marked with the chr
    public FastACTree putChild(FastACTree nNode) {
	childs[nNode.chr - 'a'] = nNode;
	return this;
    }

    // Split a string into chars and all addWord on it
    public FastACTree addWord(String word) {
	return addWord(word.toCharArray());
    }

    // Adds a new word onto the Trie
    public FastACTree addWord(char[] word) {
	String w = new String(word);
	FastACTree currentNode = this;
	FastACTree nextNode = null;
	for (char e: word) {
	    nextNode = currentNode.getChild(e)
	    if (nextNode == null) {
		nextNode = new FastACTree(e, currentNode);
		currentNode.putChild(nextNode);
	    }
	    currentNode = nextNode;
	}
	currentNode.isEnd = true;
	currentNode.word  = w;
	isCompiled = false;
	return this;
    }

    public boolean isRoot() {
	return this.parent == null;
    }

    public boolean isFinal() {
	return this.isEnd;
    }

    public FastACTree compile() {
	ArrayDeque<FastACTree> queue = new ArrayDeque<>();
	for (FastACTree e: this.childs)
	    queue.addLast(e);
	FastACTree currentNode = null;
	while (!queue.isEmpty()) {
	    currentNode = queue.pop();
	    for (FastACTree e: currentNode.childs)
		queue.addLast(e);
	    FastACTree fall = currentNode.parent.fall;
	    while (fall.getChild(chr) == null && !fall.isRoot())
		fall = fall.fall;
	    currentNode.fall = fall.getChild(chr);
	    if (currentNode.fall == null)
		currentNode.fall = this;
	    if (currentNode == currentNode.fall)
		currentNode.fall = this;
	}
	isCompiled = true;
	return this;
    }

    @Override
    public String toString() {
	return String.format("[Root = %b\n Char = %c\n Parent chr = %c]", isEnd, chr, parent.chr);
    }
}
