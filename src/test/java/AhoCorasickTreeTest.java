import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.function.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.google.common.primitives.Chars.*;

public class AhoCorasickTreeTest {
    @Test
    public void addWordTest() {
	AhoCorasickTree ac = new AhoCorasickTree();
	String testWord = "test";
	ac.addWord(testWord);
	AhoCorasickTree node = ac;
	for(Character e: asList(testWord.toCharArray())) {
	    assertNotNull(String.format("Tree must have child %c", e), node.childs.get(e));
	    node = node.childs.get(e);
	}
	assertTrue(String.format("Last char %c must be final", node.chr.get()), node.isFinal());
    }

    @Test
    public void wellFormedTrieTest() {
	AhoCorasickTree ac = new AhoCorasickTree();
	String[] testWords = {"a", "b", "c", "aa", "d"};
	String[] expectedWords = {"a", "b", "c", "d"};
	char[] expectedRootLeafs = {'a', 'b', 'c', 'd'};
	for (String e: testWords) {
	    ac.addWord(e);
	}
	for(Character e: asList(expectedRootLeafs)) {
	    assertNotNull(String.format("Tree must have child %c", e), ac.childs.get(e));
	}
	for(Character e: asList(expectedRootLeafs)) {
	    assertTrue(String.format("Child %c must be final", e), ac.childs.get(e).isFinal());
	}
	for (int i = 0; i < expectedWords.length; i++) {
	    assertEquals(expectedWords[i], ac.childs.get(Character.valueOf(expectedRootLeafs[i])).word);
	}
	AhoCorasickTree nodeA = ac.childs.get(Character.valueOf('a'));
	AhoCorasickTree nodeAA = nodeA.childs.get(Character.valueOf('a'));
	assertNotNull("Second a must not be null", nodeAA);
	assertTrue(nodeAA.isFinal());
	assertEquals("aa", nodeAA.word);
	assertEquals("Node a must be child of root",ac, nodeA.parent.get());
    }

    @Test
    public void getTokensTest() {
	AhoCorasickTree ac = new AhoCorasickTree();
	String[] expected = {"c", "a", "aa", "a", "aa", "a", "b"};
	ac.addWord("a")
	    .addWord("b")
	    .addWord("c")
	    .addWord("aa")
	    .addWord("d")
	    .addWord("b");
	Iterator<String> it = ac.iterator("caaab");
	for (int i = 0; i < expected.length; i++) {
	    String actual = it.next();
	    assertEquals(String.format("Place %d:", i), expected[i], actual);
	}
    }

    @Test
    public void compileTest() {
	AhoCorasickTree ac = new AhoCorasickTree();
	String[] words = {"fast", "sofa", "so", "take"};
	for (String e: words) {
	    ac.addWord(e);
	}
	ac.compile();
	Character[] branch1 = {'t', 'a', 'k', 'e'};
	Character[] branch2 = {'f', 'a', 's', 't'};
	Character[] branch3 = {'s', 'o', 'f', 'a'};
	AhoCorasickTree[] b1Nodes = new AhoCorasickTree[4];
	AhoCorasickTree[] b2Nodes = new AhoCorasickTree[4];
	AhoCorasickTree[] b3Nodes = new AhoCorasickTree[4];
	AhoCorasickTree cNode = ac;
	for (int i = 0; i < 4; i++) {
	    b1Nodes[i] = cNode.childs.get(branch1[i]);
	    cNode = cNode.childs.get(branch1[i]);
	}
	cNode = ac;
	for (int i = 0; i < 4; i++) {
	    b2Nodes[i] = cNode.childs.get(branch2[i]);
	    cNode = cNode.childs.get(branch2[i]);
	}
	cNode = ac;
	for (int i = 0; i < 4; i++) {
	    b3Nodes[i] = cNode.childs.get(branch3[i]);
	    cNode = cNode.childs.get(branch3[i]);
	}
	AhoCorasickTree[] fall1 = {ac, ac, ac, ac};
	AhoCorasickTree[] fall2 = {ac, ac, b3Nodes[2], b1Nodes[0]};
	AhoCorasickTree[] fall3 = {ac, ac, b2Nodes[0], b2Nodes[1]};
	for (int i = 0; i < 2; i++) {
	    assertEquals(String.format("Branch1 %d connect %c with %c", i, b1Nodes[i].chr.get(), fall1[i].chr.orElse('R')), fall1[i], b1Nodes[i].fall.get());
	    assertEquals(String.format("Branch2 %d connect %c with %c", i, b2Nodes[i].chr.get(), fall2[i].chr.orElse('R')), fall2[i], b2Nodes[i].fall.get());
	    assertEquals(String.format("Branch3 %d connect %c with %c", i, b3Nodes[i].chr.get(), fall3[i].chr.orElse('R')), fall3[i], b3Nodes[i].fall.get());
	}
    }
}
