import java.util.Iterator;

/**
 * lab12: starter
 * <p>
 * A Trie is a collection of strings. The main operations on a Trie
 * are insert(), contains(), and remove().
 * <p>
 * TODO: Copy the latest versions of Map and HashMap (and other
 * associated class files: Assoc, AList, DoublyLinkedList) that you
 * developed for the lec12b pre-lecture exercise into the src
 * directory for this project.
 * <p>
 * TODO: There are 3 methods for you to complete in this class.
 * <p>
 * Implement the insert(), contains(), and remove() operations, using
 * recursive helper methods in the Node class, as described in
 * lecture. The remove() operation should compress paths that don't
 * lead to data whenever possible.
 * <p>
 * When you are done, proceed to the Dictionary class and implement
 * the startsWith() method.
 */

public class Trie {
    class Node {
        String word;   // if word is null, then no data present in this node
        Map<Character, Node> children;  // only non-null children are stored

        /**
         * Creates an empty leaf node.
         */

        Node() {
            this(null);
        }

        /**
         * Creates a leaf node with the given word present.
         */

        Node(String word) {
            this.word = word;
            children = new HashMap<>();
        }

        /**
         * Returns true iff this node is a leaf.
         */

        boolean isLeaf() {
            return children.isEmpty();
        }

        /**
         * TODO #1
         * <p>
         * Inserts s into the trie assuming that the first i characters
         * in s have led us to this node.
         */

        void insert(String s, int i) {

            if (i == s.length()) {
                if (word == null) {
                    n++;
                    word = s;
                }
            } else {
                char c = s.charAt(i);
                if (children.get(c) == null)
                    children.put(c, new Node());
                children.get(c).insert(s, i + 1);
            }
        }

        /**
         * TODO #2
         * <p>
         * Returns true iff s is in the trie assuming that the first i
         * characters in s have led us to this node.
         */

        boolean contains(String s, int i) {

            if (i == s.length()) {
                return word != null;
            } else {
                char c = s.charAt(i);
                if (children.get(c) != null)
                     return children.get(c).contains(s, i + 1);
            }
            return false;
        }

        /**
         * TODO #3
         * <p>
         * Removes s from the trie assuming that the first i character in
         * s have led us to this node. Collapses dead end paths on the way
         * back out of the recursion. Returns true iff this node is now a
         * dead end.
         */

        boolean remove(String s, int i) {

            if (i == s.length()) {
                if (word != null) {
                    n--;
                    word = null;
                }
            } else {
                char c = s.charAt(i);
                Node child = children.get(c);
                if (child != null)
                    if (child.remove(s, i +1))
                        children.remove(c);
            }
            return word == null && this.isLeaf();
        }
    }

    Node root = new Node();   // root is never null
    int n = 0;                // number of keys in this trie

    /**
     * Inserts s into this trie. If s was already present in this trie,
     * then nothing happens.
     */

    public void insert(String s) {
        root.insert(s, 0);
    }

    /**
     * Return true iff s is a key in this trie.
     */

    public boolean contains(String s) {
        return root.contains(s, 0);
    }

    /**
     * Removes s from this trie. If s was not in this trie to begin with,
     * then nothing happens.
     */

    public void remove(String s) {
        root.remove(s, 0);
    }

    /**
     * Returns the number of keys in this trie. Must run in O(1) time.
     */

    public int size() {
        return n;
    }

    /**
     * Returns true iff this trie is empty. Must run in O(1) time.
     */

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Resets this trie to be empty.
     */

    public void clear() {
        n = 0;
        root = new Node();
    }
}

