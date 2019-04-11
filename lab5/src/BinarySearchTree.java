import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

/**
 * Starter code for lab5. This is an implementation of BinarySearchTree
 * for int data.
 * <p>
 * (1) Modify Tree and BinarySearchTree so that they are generic for
 * any Comparable type object.
 * <p>
 * (2) Implement BinarySearchTree.toString() so that it yields a
 * string containing the sorted sequence of elements in the tree.
 * <p>
 * (3) Implement the remove() method using the algorithm described by
 * your AI. Your code must run in O(h) time, where h is the height of
 * the tree.
 * <p>
 * -- Start by defining the helper method largestNode(p) that returns
 * a pointer to the node in the subtree rooted at p with the largest
 * data value. You may assume that p is non-null.
 *
 * @author <Zachary Windler zkwindle and Matthew Denney >
 */

interface Tree<E extends Comparable> {
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
}

public class BinarySearchTree<E extends Comparable> implements Tree<E> {

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
        if (p == null) //still allowed to use == when comparing with a null
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
     * TODO
     * <p>
     * Returns a pointer to the node containing the largest data
     * value in this non-empty BST.
     */

    Node largestNode(Node p) {
        assert p != null;
        if (p.right == null)
            return p;
        return largestNode(p.right);
    }

    /**
     * TODO
     * <p>
     * Returns a pretty text version of this tree.
     */

    public String toString() {
        //inorder traversal (L,ROOT,R)
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

    public boolean remove(E key) {

        /* way in lab
        boolean flag = removeHelp(root, key);
        if (flag)
            n--;
        return flag;
        */

        //way in solutions
        boolean ans = removeHelper(key, root, null);
        if (ans)
            n--;
        return ans;
    }

    public boolean removeHelper(E x, Node p, Node parent) { //lab way (Node p, E key)

        /* way in lab
        if (p == null)
            return false;
        if (p.data.equals(key)) {
            if (p.isLeaf())
                p = null;
            else if (p.right == null)
                p = p.left;
            else if (p.left == null)
                p = p.right;
            else {
                Node largest = largestNode(p.left);
                removeHelp(p.left, largest.data);
                p = largest;
                //n++;
            }
            //n--;
            return true;
        }
        if (key.compareTo(p.data) < 0)
            return removeHelp(p.left, key);
        return removeHelp(p.right, key);
        */

        //way in solutions
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
            }
            else if (p.left == null) {
                if (parent == null)
                    root = p.right;
                else if (parent.left == p)
                    parent.left = p.right;
                else
                    parent.right = p.right;
            }
            else if (p.right == null) {
                if (parent == null)
                    root = p.left;
                else if (parent.left == p)
                    parent.left = p.left;
                else
                    parent.right = p.left;
            }
            else {
                Node pred = largestNode(p.left);
                p.data = pred.data;
                removeHelper(pred.data, p.left, p);
            }
            return true;
        }
        if (x.compareTo(p.data) < 0)
            return removeHelper(x, p.left, p);
        return removeHelper(x, p.right, p);
    }
}


