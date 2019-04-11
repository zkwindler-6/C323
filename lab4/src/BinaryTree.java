
import java.util.List;
import java.util.ArrayList;

/**
 * Tree interface and BinaryTree class from lec4b.
 * <p>
 * Implement and test the Tree.height() method, as described by
 * your AI. Then, answer the following question:
 * <p>
 * Question: What is the Big-O running time of Tree.height() on a
 * binary tree with n nodes?
 * <p>
 * Answer: O(n)
 * <p>
 * Challenge for people with time on their hands: Parameterize Tree
 * and BinaryTree with a type variable E so that the structure holds
 * elements of type E. Do not add a type variable to the Node class.
 * Do not submit to the autograder as tests are not in place for this
 * update. Write your own tests.
 */

interface Tree {
    void insert(int key);

    default void remove(int key) {
        throw new UnsupportedOperationException();
    }

    boolean contains(int key);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default int height() {
        throw new UnsupportedOperationException();
    }
}

public class BinaryTree implements Tree {

    class Node {
        int data;
        Node left, right;

        Node(int key) {
            this(key, null, null);
        }

        Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }
    }

    Node root;
    int n;

    /**
     * Default constructor: builds the empty tree.
     */

    public BinaryTree() {
    }

    /**
     * Build a binary tree by viewing the given array as a heap
     * structure.
     */

    public BinaryTree(int[] a) {
        n = a.length;
        if (n > 0) {
            // First, create all the nodes.
            List<Node> ls = new ArrayList<>(n);
            for (int i = 0; i < n; i++)
                ls.add(new Node(a[i]));
            // Now, link the child from its parent.
            for (int i = n - 1; i > 0; i--) {
                Node parent = ls.get((i - 1) / 2);
                if (i % 2 == 1)
                    parent.left = ls.get(i);
                else
                    parent.right = ls.get(i);
            }
            // Finally, set the root.
            root = ls.get(0);
        }
    }

    /**
     * Insert a new key into the existing tree by first trying to
     * fill in the root's children and, if that fails, build upwards
     * by creating a new root node.
     */

    public void insert(int key) {
        n++;
        if (root == null)
            root = new Node(key);
        else if (root.left == null)
            root.left = new Node(key);
        else if (root.right == null)
            root.right = new Node(key);
        else if (Math.random() < 0.5)
            root = new Node(key, root, null);
        else
            root = new Node(key, null, root);
    }

    /**
     * Returns true iff key exists in some node's data field.
     */

    public boolean contains(int key) {
        return containsHelper(key, root);
    }

    /**
     * Helper to search the tree rooted at p for the key.
     */

    private boolean containsHelper(int key, Node p) {
        if (p == null)
            return false;
        if (p.data == key)
            return true;
        return containsHelper(key, p.left) || containsHelper(key, p.right);
    }

    /**
     * Returns the number of elements in this tree.
     */

    public int size() {
        return n;
    }

    public int height(){
        return height(root);
    }

    public int height(Node p){
        if (p == null)
            return 0;

        int left = height(p.left);
        int right = height(p.right);

        if (left > right){
            return left + 1;
        } else {
            return right + 1;
        }
    }

    public void runTimeHeight(){

    }

    /**
     * Simple testing.
     */

    public static void main(String... args) {
        int[] a = new int[]{3, 12, 9, 7, 2, 1, 11, 5, 6, 13, 4, 8, 0, 10,};
        BinaryTree tr = new BinaryTree();
        assert tr.isEmpty();
        // Tends to build long skinny trees.
        for (int key : a)
            tr.insert(key);
        tr = new BinaryTree(a);
        assert tr.size() == a.length;
        assert !tr.root.isLeaf();
        for (int key : a)
            assert tr.contains(key);
        try {
            tr.remove(3);
            assert false;
        } catch (UnsupportedOperationException e) {
        }

        // Builds a nearly complete tree.
        tr = new BinaryTree(a);
        assert tr.size() == a.length;
        assert tr.root != null;
        assert !tr.root.isLeaf();
        assert tr.root.data == 3;
        assert tr.root.left.data == 12;
        assert tr.root.right.data == 9;
        for (int key : a)
            assert tr.contains(key);

        assert 4 == tr.height();

        System.out.println("Passed all tests...");
    }
}
