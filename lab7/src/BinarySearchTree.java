/**
 * Starter code for lab7. This is an implementation of BinarySearchTree
 * for int data.
 * <p>
 * (1) Implement BinarySearchTree.traverse()
 *
 * @author <put your name and the name of your partner here>
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinarySearchTree<E extends Comparable<E>> {

    class Node {
        E data;
        Node left, right;

        Node(E key) {
            this(key, null, null);
        }

        Node(E data, Node left, Node right) {
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
     * Inserts the key into this tree. Runs in O(h) time, where h is
     * the height of the tree.
     */

    public void insert(E key) {
        n++;
        root = insertHelper(key, root);
    }

    private Node insertHelper(E key, Node p) {
        if (p == null)
            p = new Node(key);
        else if (key.compareTo(p.data) < 0)
            p.left = insertHelper(key, p.left);
        else
            // if keys are unique, it must be the case that key > p.data
            p.right = insertHelper(key, p.right);
        return p;
    }

    /**
     * Returns true iff key is in this tree. Runs in O(h) time, where h is
     * the height of the tree.
     */

    public boolean contains(E key) {
        return containsHelper(key, root);
    }

    private boolean containsHelper(E key, Node p) {
        if (p == null)
            return false;
        if (key.equals(p.data))
            return true;
        if (key.compareTo(p.data) < 0)
            return containsHelper(key, p.left);
        return containsHelper(key, p.right);
    }

    /**
     * Returns the number of keys in this tree.
     */

    public int size() {
        return n;
    }

    /**
     * Returns the length of the longest root to leaf path in
     * this tree.
     */

    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(Node p) {
        if (p == null)
            return 0;
        return 1 + Math.max(heightHelper(p.left),
                heightHelper(p.right));
    }

    /**
     * Returns a pretty text version of this tree.
     */

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        toStringHelper(root, sb);
        return sb.toString().trim() + ")";
    }

    private void toStringHelper(Node p, StringBuilder sb) {
        if (p != null) {
            toStringHelper(p.left, sb);
            sb.append(p.data).append(" ");
            toStringHelper(p.right, sb);
        }
    }

    /**
     * TODO
     *
     * Returns a list containing nodes' data in a level-order traversal
     * For example: The level order traversal of the following tree
     *                        1
     *                       / \
     *                      2   3
     *                     / \
     *                    4  5
     * is 1 2 3 4 5
     */

    public List<E> traverse() {
        List<E> result = new LinkedList<>();
        Queue<Node> toVisit = new LinkedList<>();

        //use of loop
        toVisit.add(root);

        while (!toVisit.isEmpty()) {
            Node hold = toVisit.poll();
            result.add(hold.data);
            if (hold.left != null)
                toVisit.add(hold.left);
            if (hold.right != null)
                toVisit.add(hold.right);
        }
        return result;
    }
}
