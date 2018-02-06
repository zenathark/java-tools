import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.function.*;

class AhoCorasickTokenizer {// implements Iterator {

    // final private AhoCorasickTree root;
    // private AhoCorasickTree currentNode;
    // private Character input;
    // private int pos;

    // private interface StateFn extends Supplier<StateFn> {}

    // private StateFn state;

    // final StateFn finalState = () -> null;

    // final StateFn initState = () -> {
    //	Character e;
    //	AhoCorasickTree currentNode = tokenizer.currentNode;
    //	while (++tokenizer.pos < tokenizer.input.length) {
    //	    e = tokenizer.input[i];
    //	    while (currentNode.childs.get(e) == null && !currentNode.isRoot())
    //		currentNode = currentNode.fall.get();
    //	    if (currentNode.isRoot()) {
    //		currentNode = currentNode.childs.get(e);
    //		if (currentNode == null) currentNode = root;
    //	    } else
    //		currentNode = currentNode.childs.get(e);
    //	    tokenizer.currentNode = currentNode;
    //	    if (currentNode.isFinal())
    //		return this.onTokenState;
    //	}
    //	tokenizer.currentNode = null;
    //	return this.finalState;
    // };

    // final StateFn onTokenState = () -> {
    //	    currentNode = currentNode.fall.get();
    //	    if (currentNode.isFinal())
    //		return onTokenState;
    //	    else
    //		return searchState;
    // };

    // public AhoCorasickTokenizer(Character[] input, AhoCorasickTree root) {
    //	this.input = input;
    //	index = -1;
    //	this.root = root;
    //	currentNode = root;
    //	currentState = searchState;
    //	next();
    // }

    // @Override
    // public String next() {
    //	String ans = currentNode.word;
    //	currentState = currentState.get();
    // }

}
