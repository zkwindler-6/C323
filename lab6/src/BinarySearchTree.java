/**
 * Starter for lab6.
 * <p>
 * In this lab you will implement BinarySearchTree.kthSmallest(),
 * which takes a positive integer k and returns the k-th smallest
 * element in the BST. As usual, we assume that all keys in the BST
 * are unique. This function must run in O(h) time, where h is the
 * height of the tree.
 * <p>
 * Follow the following steps to implement kthSmallest().
 * <p>
 * (1) Add the signature for kthSmallest to the Tree interface and
 * provide a default implementation.
 * <p>
 * (2) Add an int data field to Node that holds the size of the
 * subtree rooted at the node. Name this field n. Note that this
 * is distinct from the n in the tree, which we leave undisturbed.
 * <p>
 * (3) Make the appropriate modifications to insert and then test by
 * running the unit tests from last week (Lab5Testing) and the new
 * tests for this week (Lab6Testing). Note that all but the first
 * test in Lab6Testing are commented out at the moment.
 * <p>
 * (4) Make the appropriate modifications to remove and then test.
 * <p>
 * (5) Define kthSmallest. Write a suitable javadoc style comment.
 * Implement the following algorithm:
 * <p>
 * To find the k-th smallest value in the tree rooted at p:
 * If the kth-smallest must be in the left subtree
 * then recur on the left subtree
 * else if the kth-smallest must be in p
 * then return p's data field
 * else recur on the right subtree
 *
 * @author <Zachary Windler, Samuel Oates, Taylor Johnson>
 */

interface Tree<E extends Comparable<E>> {
    void insert(E key);

    default boolean remove(E key) {
        throw new UnsupportedOperationException();
    }

    boolean contains(E key);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default int height() {
        throw new UnsupportedOperationException();
    }

    E kthSmallest(int k);
}

public class BinarySearchTree<E extends Comparable<E>>
        implements Tree<E> {

    class Node {
        E data;
        Node left, right;
        int n = 0;

        Node(E key) {
            this(key, null, null, 1);
        }

        Node(E data, Node left, Node right, int n) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.n = n;
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
        if (p == null) {
            //p.n++;
            return new Node(key);
        }
        if (key.compareTo(p.data) < 0) {
            //counter edited here?
            p.n++;
            p.left = insertHelper(key, p.left);
        } else {
            // if keys are unique, it must be the case that key > p.data
            // otherwise, we will put duplicate data on the right anyways
            p.n++;
            p.right = insertHelper(key, p.right);
        }
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

    @Override
    public E kthSmallest(int k) {
        return kthSmallestHelper(k, root);
    }

    public E kthSmallestHelper(int k, Node p) {
//        int size;
//        if (p.left == null) {
//            size = 0;
//            p.n = size;
//        }
//        else

        if (k <= p.left.n)
            return kthSmallestHelper(k, p.left);

        else if (!(k <= p.left.n)){
            if (!(k == p.left.n+1))
                return kthSmallestHelper(k, p.right);
        }

        else
            return p.data;
        return p.data;
    }

    /**
     * Attempts to remove an occurrence of x in this tree.
     * Returns true iff the remove succeeds.
     */

    public boolean remove(E x) {
        boolean ans = removeHelper(x, root, null);
        if (ans)
            n--;
        return ans;
    }

    public boolean removeHelper(E x, Node p, Node parent) {
        if (p == null)
            return false;
        if (p.data.equals(x)) {
            if (p.isLeaf()) {
                if (parent == null)
                    root = null;
                else if (parent.left == p)
                    parent.left = null;
                else
                    parent.right = null;
            } else if (p.left == null) {
                if (parent == null)
                    root = p.right;
                else if (parent.left == p)
                    parent.left = p.right;
                else
                    parent.right = p.right;
            } else if (p.right == null) {
                if (parent == null)
                    root = p.left;
                else if (parent.left == p)
                    parent.left = p.left;
                else
                    parent.right = p.left;
            } else {
                Node pred = largestNode(p.left);
                p.data = pred.data;
                p.n--;
                removeHelper(pred.data, p.left, p);
            }
            return true;
        }
        boolean res;
        if (x.compareTo(p.data) < 0) {
            res = removeHelper(x, p.left, p);
            if (res)
                p.n--;
            return res;
        } else {
            res = removeHelper(x, p.right, p);
            if (res)
                p.n--;
            return res;
        }
    }

    /**
     * Returns a pointer to the node containing the largest data
     * value in this non-empty BST.
     */

    Node largestNode(Node p) {
        if (p.right == null)
            return p;
        return largestNode(p.right);
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
}




