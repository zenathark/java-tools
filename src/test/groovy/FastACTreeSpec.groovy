import spock.lang.*

class FastACTreeSpec extends Specification {


    @Unroll
    def "Adding a word"() {
	given: "The word test and a Trie"
	String word = "test"
	char[] chars = word.toCharArray()
	FastACTree trie = new FastACTree()

	when: "Add the word test"
	trie.addWord word

	then: "t should be a son of root"
	FastACTree cNode = trie
	for (int i = 0; i < chars.length; i++) {
	    assert cNode.childs[chars[i]-('a' as char)].chr == chars[i]
	    cNode = cNode.childs[chars[i] - ('a' as char)]
	}

    }

    class BranchProvider implements Iterable {
	char[] input
	private int pos
	FastACTree cNode

	BranchProvider(String input, FastACTree root) {
	    this(input.toCharArray(), root)
	}

	BranchProvider(char[] input, FastACTree root) {
	    this.input = input
	    pos = 1
	    cNode = root.getChild input[0]
	}

	@Override
	Iterator iterator() {
	    [
		hasNext: {
		    cNode != null
		},
		next: {
		    def ans = cNode
		    cNode = cNode.getChild input[pos++]
		    ans
		}
	    ] as Iterator
	}
    }
}
