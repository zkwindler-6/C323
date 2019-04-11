import java.util.function.BiPredicate;

/**
 * TODO: This is your second major task.
 * <p>
 * This class implements a generic height-balanced binary search tree,
 * using the AVL algorithm. Beyond the constructor, only the insert()
 * method needs to be implemented. All other methods are unchanged.
 */

public class AVLTree<K> extends BinarySearchTree<K> {

    /**
     * Creates an empty AVL tree as a BST organized according to the
     * lessThan predicate.
     */

    public AVLTree(BiPredicate<K, K> lessThan) {
        super(lessThan);
    }

    /**
     * TODO
     * <p>
     * Inserts the given key into this AVL tree such that the ordering
     * property for a BST and the balancing property for an AVL tree are
     * maintained.
     */

    public Node insert(K key) {
        Node q = super.insert(key);

        boolean flag = false;

        while(flag = false){
            if (q.parent.isOverweight()){

            }
            q = q.parent;
        }

        return q;
    }

    //LL
    public Node LL(Node k1, Node k2) {
        if (k1.parent != null){
            k2.parent = k1.parent;
            k1.parent =k2;
        }
        else{
            root = k2;
            k1.parent = root;
            root.parent = null;
        }
        k1.left = k2.right;
        if (k2.right != null)
            k2.right.parent = k1;
        k2.right = k1;
        k1.parent = k2;

        k1.fixHeight();
        k2.fixHeight();

        return k1;
    }

    public Node RR(Node k1, Node k2) {
        if (k1.parent != null){
            k2.parent = k1.parent;
            k1.parent =k2;
        }
        else{
            root = k2;
            k1.parent = root;
            root.parent = null;
        }
        k1.right = k2.left;
        if (k2.left != null)
            k2.left.parent = k1;
        k2.left = k1;
        k1.parent = k2;

        k1.fixHeight();
        k2.fixHeight();

        return k1;

    }

    public void LR(Node pivot) {

    }

    public void RL(Node pivot) {

    }

    int getBalance(Node p) {
        if (p == null)
            return 0;
        else if (p.left == null && p.right != null)
            return p.right.height;
        else if (p.right == null && p.left != null)
            return p.left.height;
        else if (p.left != null && p.right != null)
            return p.left.height - p.right.height;
        else
            return 0;

//        if (p == null)
//            return 0;
//
//        return height(p.left) - height(p.right);
    }

    int height(Node p) {
        if (p == null)
            return 0;

        return p.height;
    }
}
