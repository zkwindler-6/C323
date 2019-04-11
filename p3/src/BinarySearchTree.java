import java.util.function.BiPredicate;

/**
 * TODO: This is your first major task.
 * <p>
 * This class implements a generic unbalanced binary search tree (BST).
 */

public class BinarySearchTree<K> implements Tree<K> {

    /**
     * A Node is a Location, which means that it can be the return value
     * of a search on the tree.
     */

    class Node implements Location<K> {

        /**
         * Note that our nodes have been enhanced with three additional
         * pieces of information, as outlined in lecture.
         */

        K data;
        Node left, right;
        Node parent;     // the parent of this node
        int height;      // the height of the subtree rooted at this node
        boolean dirty;   // true iff the key in this node has been removed
        //added by me
        //Node after, before; //do we need these?

        /**
         * Constructs a leaf node with the given key.
         */

        Node(K key) {
            this(key, null, null);
        }

        /**
         * TODO (DONE)
         * <p>
         * Constructs a new node with the given values for fields.
         */

        // do i need to have parent and n in the constructor? what about
        Node(K data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = 1;
            this.dirty = false;
            fixHeight();
            //parent pointers
            if (left != null)
                this.left.parent = this;
            if (right != null)
                this.right.parent = this;
        }

        /**
         * Return true iff this node is a leaf in the tree.
         */

        boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * TODO (DONE)
         * <p>
         * Performs a local update on the height of this node. Assumes that the
         * heights in the child nodes are correct. Returns true iff the height
         * actually changed. This function *must* run in O(1) time.
         */

        boolean fixHeight() {
            if (this.left != null && this.right != null) {
                this.height = 1 + Math.max(this.left.height, this.right.height);
                return true;
            }

            if (this.left != null) {
                this.height = 1 + this.left.height;
                return true;
            }

            if (this.right != null) {
                this.height = 1 + this.right.height;
                return true;
            }
            return false;
        }

        /**
         * TODO (DONE)
         * <p>
         * Returns the data in this node.
         */

        public K get() {
            return this.data;
        }

        /**
         * TODO (DONE)
         * <p>
         * Returns the location containing the inorder predecessor of this node.
         */

        public Node getBefore() {
            return getBeforeHelper(this);
        }

        public Node getBeforeHelper(Node p){
            if (p.left != null) {
                Node holder = p.largestNode(p.left);
                if (holder.dirty) {
                    return holder.getBefore();
                }
                return holder;
            }
            else{
                Node cur = p;
                Node x = p.parent;
                while (x != null && x.left == cur) {
                    cur = x;
                    x = x.parent;
                }
                if (x != null && x.dirty) {
                    return x.getBefore();
                } else {
                    return x;
                }
            }
        }

        //used in getBefore()
        public Node largestNode(Node p){
            while(p.right != null)
                p = p.right;
            return p;
        }

        /**
         * TODO (DONE)
         * <p>
         * Returns the location containing the inorder successor of this node.
         */

        public Node getAfter() {
            return getAfterHelper(this);
        }

        public Node getAfterHelper(Node p) {
            Node holder = p.parent;
            if (p.right != null){
                Node x = smallestNode(p.right);
                if (x.dirty){
                    return x.getAfter();
                }
                return x;
            }
            while (holder != null && holder.right == p) {
                p = holder;
                holder = holder.parent;
            }
            if (holder != null && holder.dirty){
                return getAfterHelper(holder);
            }
            return holder;
        }

        //used in getAfter()
        public Node smallestNode (Node p){
            while (p.left != null)
                p = p.left;
            return p;
        }

        /**
         * TODO (DONE)
         * <p>
         * Returns true iff this node is overweight. Must run in O(1) time!
         */

        boolean isOverweight() {
            if (this == null)
                return false;
            else if (this.left == null && this.right != null && this.right.height > 1)
                return true;
            else if (this.right == null && this.left != null && this.left.height > 1)
                return true;
            else if (this.left != null && this.right != null && Math.abs(this.left.height - this.right.height) > 1)
                return true;
            else
                return false;
        }

        /**
         * TODO (DONE)
         * <p>
         * Returns true iff this node is balanced. Must run in O(1) time!
         */

        //MUST CHECK IT AGAINST 0 AND NOT
        boolean isBalanced() {
            int height_left = 0;
            if (this.left != null)
                height_left = this.left.height;
            int height_right = 0;
            if (this.right != null)
                height_right = this.right.height;
            int total = height_left - height_right;
            if (total == 0)
                return true;
            return false;
        }
    }

    Node root;
    int n;
    BiPredicate<K, K> lessThan;

    int dirtyCount;

    /**
     * Constructs an empty BST, where the data is to be organized according to
     * the given lessThan relation.
     */

    public BinarySearchTree(BiPredicate<K, K> lessThan) {
        this.lessThan = lessThan;
    }

    /**
     * TODO (DONE)
     * <p>
     * Looks up the key in this tree and, if found, returns the (possibly dirty)
     * location containing the key.
     */

    public Node search(K key) {
        return searchHelper(key, root);
    }

    private Node searchHelper(K key, Node p) {
        if (p == null || p.data.equals(key))
            return p;

        if (lessThan.test(key, p.data))
            return searchHelper(key, p.left);

        return searchHelper(key, p.right);
    }

    /**
     * TODO (DONE)
     * <p>
     * Returns the height of this tree. Must run in O(1) time!
     */

    public int height() {
        if (root == null)
            return 0;
        return root.height;
    }

    /**
     * TODO (DONE)
     * <p>
     * Clears all the keys from this tree. Must run in O(1) time!
     */

    public void clear() {
        root = null;
        //must set size to 0 when clear
        this.n = 0;
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
     * Inserts the given key into this BST, as a leaf, where the path
     * to the leaf is determined by the predicate provided to the tree
     * at construction time. The parent pointer of the new node and
     * the heights in all node along the path to the root are adjusted
     * accordingly.
     * <p>
     * Note: we assume that all keys are unique. Thus, if the given
     * key is already present in the tree, nothing happens.
     * <p>
     * Returns the location where the insert occurred (i.e., the leaf
     * node containing the key).
     */

    public Node insert(K key) {
        boolean flag = false;
        Node p = root;
        Node holder = null;

        Node search = search(key);
        //if the key is already in the tree
        if (search != null && search.dirty == true) {
            search.dirty = false;
            dirtyCount--;
            n++;
            return search;
        }
        //catches duplicates
        if (search != null) {
            return search;
        }
        while (!flag) {
            if (p == null) {
                n++;
                root = new Node(key);
                return root;
            }
            if (lessThan.test(key, p.data)) {
                if (p.left == null) {
                    p.left = new Node(key);
                    holder = p.left;
                    p.left.parent = p;
                    flag = true;
                } else
                    p = p.left;
            } else {
                if (p.right == null) {
                    p.right = new Node(key);
                    holder = p.right;
                    p.right.parent = p;
                    flag = true;
                } else
                    p = p.right;
            }
        }
        p.fixHeight();
        if (p.parent != null) {
            //fixes the heights up the tree
            do {
                p.parent.fixHeight();
                p = p.parent;
            } while (p.parent != null);
        }
        n++;
        return holder;
    }

    /**
     * TODO: Write this method in terms of search(). (DONE)
     * <p>
     * Returns true iff the given key is in this BST.
     */

    public boolean contains(K key) {
        if (search(key) == null || search(key).dirty == true)
            return false;
        return true;
    }

    /**
     * TODO (DONE)
     * <p>
     * Removes the key from this BST. If the key is not in the tree,
     * nothing happens. Implement the removal using lazy deletion.
     * <p>
     * Use the following algorithm:
     * (1) Search for the key.
     * (2) If found (and it's not already dirty), then make it dirty.
     */

    public boolean remove(K key) {
        if (search(key) == null)
            return false;
        Node p = search(key);
        if (p.dirty == true) {
            return false;
        } else {
            p.dirty = true;
            dirtyCount++;
            n--;
            return true;
        }
    }

    /**
     * TODO
     * <p>
     * Clears out all dirty nodes from this BST and reconstructs a
     * nicely balanced tree.
     * <p>
     * Use the following algorithm:
     * (1) Let ks be the list of keys in this tree.
     * (2) Clear this tree.
     * (3) As long as ks is not empty:
     * Remove the middle element in ks and
     * Insert it into this tree.
     */

    public void rebuild() {
        List<K> ks;
        ks = keys();
        clear();
        //must set dirtyCount back to zero
        dirtyCount = 0;

        while (!ks.isEmpty()) {
            int in = ks.size() / 2;
            K info = ks.remove(in);
            insert(info);
        }
    }

    /**
     * TODO
     * <p>
     * Returns a sorted list of all the keys in this tree.
     * Must run in O(n) time.
     */

    public List<K> keys() {
        List<K> sortedList = new DoublyLinkedList<>();
        sortedList = listHelper(root, (DoublyLinkedList) sortedList);
        return sortedList;
    }

    List<K> listHelper(Node p, DoublyLinkedList l) {
        if (p != null) {
            listHelper(p.left, l);
            if (p.dirty == false)
                l.add(p.data);
            listHelper(p.right, l);
        }
        return l;
    }

    /**
     * Returns a preorder traversal of this BST.
     */

    public String toString() {
        return toStringPreorder(root);
    }

    private String toStringPreorder(Node p) {
        if (p == null)
            return "";
        String left = toStringPreorder(p.left);
        if (left.length() != 0) left = " " + left;
        String right = toStringPreorder(p.right);
        if (right.length() != 0) right = " " + right;
        String data = p.data.toString();
        if (p.dirty) data = "*" + data;
        return data + left + right;
    }
}
