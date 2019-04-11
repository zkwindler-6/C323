import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * lab14: starter
 * <p>
 * TODO: Complete the following four tasks in the order shown:
 * <p>
 * 1. Implement Node.height(), which is the helper for BTree.height().
 * <p>
 * 2. Read Node.insert(), which is the helper for BTree.insert(), and
 * then implement its helper: Node.splitChild().
 * <p>
 * 3. Handle the root full case in BTree.insert().
 * <p>
 * 4. (Challenge) Make the appropriate modifications so that duplicate
 * keys are not inserted.
 */

public class BTree {

    /**
     * Location is a structure to pinpoint the location of a key in the
     * tree. This is used as the return type of the search() method.
     */

    class Location {
        Node p;
        int i;

        Location(Node p, int i) {
            this.p = p;
            this.i = i;
        }

        public int get() {
            return p.keys.get(i);
        }
    }

    /**
     * A node has up to b keys and b + 1 subtrees.
     */

    class Node {
        List<Integer> keys = new ArrayList<>(b);
        List<Node> children = new ArrayList<>(b + 1);  // no child is null

        /**
         * Returns true iff this node is a leaf (i.e., it is childless).
         */

        boolean isLeaf() {
            return children.size() == 0;
        }

        /**
         * Returns true iff the bucket for the keys is full.
         */

        boolean isFull() {
            return keys.size() == b;
        }

        /**
         * TODO #1: This is just a warm-up. Don't dawdle here!
         * <p>
         * Returns the height of the tree rooted at this Node.
         */

        int height() {
            Node p = this;
            int height = 1;

            while (p.children.size() != 0){
                p = children.get(0);
                height++;
            }

            return height;
        }

        /**
         * Returns a list of the keys in the tree rooted at this Node as
         * encountered during an inorder traversal.
         * <p>
         * Note: We covered the implementation of this method in lec14a.
         */

        List<Integer> inorder() {
            List<Integer> ans = new LinkedList<>();
            if (isLeaf())
                ans.addAll(keys);
            else {
                int n = keys.size();
                for (int i = 0; i < n; i++) {
                    ans.addAll(children.get(i).inorder());
                    ans.add(keys.get(i));
                }
                ans.addAll(children.get(n).inorder());
            }
            return ans;
        }

        /**
         * Returns the index of key in keys, if it exists. Otherwise,the
         * index of where key can be inserted into keys to preserve the
         * sorted order is returned. Note: the return value could be
         * keys.size(), so need to check for that before using the index
         * on a get().  This is a helper for Node.insert() and
         * Node.search().
         */

        int binarySearch(int key) {
            int low = 0, high = keys.size() - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (key == keys.get(mid))
                    return mid;
                if (key < keys.get(mid))
                    high = mid - 1;
                else
                    low = mid + 1;
            }
            return low;
        }

        /**
         * Returns the location of the given key in the tree rooted at
         * this node. If the key is not found, then null is returned.
         * This is a helper for BTree.search().
         */

        public Location search(int key) {
            int i = binarySearch(key);
            if (i != keys.size() && key == keys.get(i))
                return new Location(this, i);
            if (isLeaf())
                return null;
            return children.get(i).search(key);
        }

        /**
         * TODO #2: This is where you will do most of your work.
         * <p>
         * Pre-conditions: This node is not full and the i-th child of
         * this node is full. Consequently, this node is not a leaf.
         * <p>
         * The middle key from the i-th child is propagated up into this
         * node and the remaining keys in the i-th child are split as
         * evenly as possible amongst left and right siblings. In the case
         * that b is even, there are two middle keys and we choose the one
         * on the left; consequently the right sibling will have one more
         * key than the left.
         * <p>
         * Post-conditions: This node will have one more key (and one more
         * child).  The i-th child of this node will be half full, as will
         * its right sibling.
         */

        void splitChild(int i) {
            int m = keys.size();
            Node left = null, right = null;

            /**
             * Verify the pre-conditions:
             */

            assert !isFull();
            assert !isLeaf();
            assert children.get(i).isFull();

            /**
             * TODO
             *
             * Split the i-th child of this node into two sibling nodes.
             * Propagate the middle key into this node, along with a pointer
             * to the right sibling.
             */

//            Node splitNode = children.get(i);
//            int n = splitNode.keys.size();
//            int mid = splitNode.keys.get(n/2);
//            this.keys.add(binarySearch(mid), mid);
//            right = splitNode;
//            left = splitNode;
//            for (int j = n/2; j <= n-1; j++) {
//                left.keys.remove(j);
//            }
//            for (int k = 0; k <= n/2 ; k++)
//                right.keys.add(splitNode.keys.get(k));
//            this.children.remove(i);
//            this.children.add(i, left);
//            this.children.add(i + 1, right);

            //work at home
            Node splitNode = children.get(i);
            int n = splitNode.keys.size();
            int midLoc = n/2;
            int midKey = splitNode.keys.get(midLoc);
            this.keys.add(binarySearch(midKey), midKey);
            left = splitNode;
            right = new Node();
            for (int j = midLoc; j < n; j++)
                right.keys.add(left.keys.remove(j));
            this.children.remove(i);
            this.children.add(i, left);
            this.children.add(i + 1, right);

            /**
             * Verify the post-conditions:
             */

            // This node has one more key and one more child.
            assert keys.size() == m + 1;
            assert children.size() == (m + 1) + 1;

            // The i-th child has been split into left and right.
            assert children.get(i) == left;
            assert children.get(i + 1) == right;

            // The bubbled up key is in order in this node.
            assert i == 0 || keys.get(i - 1) < keys.get(i);
            assert i == m || keys.get(i) < keys.get(i + 1);

            // The split nodes are each half full.
            assert !left.isFull();
            assert !right.isFull();
            assert left.keys.size() + right.keys.size() == b - 1;
            assert left.keys.size() == b / 2;
            assert right.keys.size() == b / 2;
        }

        /**
         * TODO #4: Challenge
         * <p>
         * Precondition: This node is not full.
         * <p>
         * Inserts the key into the tree rooted at this node. If this key
         * already exists in the tree, then nothing is done.
         * <p>
         * This is a helper for BTree.search().
         */

        void insert(int key) {
            assert !isFull();
            int i = binarySearch(key);

            /**
             * TODO: Only if you have time!
             *
             * This code assumes that key does not already exist in the
             * tree.  Make the appropriate changes to ensure that duplicate
             * keys are not inserted.
             */

            if (isLeaf()) {
                // Can just add the key to the leaf because we know there's room.
                n++;
                keys.add(i, key);
            } else {
                // The key belongs in the i-th child of this node.

                // Look ahead to see if the child is full and, if so, take
                // immediate corrective action.
                if (children.get(i).isFull()) {
                    splitChild(i);

                    // There is now room in both the i-th child and the (i+1)-st
                    // child.  Need to determine which of the two new children
                    // to insert the key into.
                    if (key > keys.get(i))
                        i++;
                }
                // Now, we can guarantee that there's room in this node, so go
                // ahead and insert.
                children.get(i).insert(key);
            }
        }

        public String toString() {
            return String.format("(%s, %s)", keys, children);
        }
    }

    int n;                   // total number of keys in the tree
    final int b;             // bucket size
    Node root = new Node();  // root is never null

    BTree(int b) {
        assert b >= 1 && b % 2 == 1;
        this.b = b;
    }

    /**
     * Returns a list of the keys in this tree, as visited by an inorder
     * traversal.
     */

    List<Integer> inorder() {
        return root.inorder();
    }

    /**
     * Returns the height of this tree.
     */

    int height() {
        return root.height();
    }

    /**
     * Searches this tree for the key and, if found, returns its
     * location.
     */

    Location search(int key) {
        return root.search(key);
    }

    /**
     * TODO #3
     * <p>
     * Insert key into this tree. If the key already exists, then
     * nothing is done.
     */

    void insert(int key) {
        if (root.isFull()) {

            /**
             * TODO
             *
             * Split preemptively, so that we'll have room in the root if a
             * child needs to split later. Do this by creating a new root
             * with no keys and the old root as its only child, and then
             * calling splitChild() to split the full child.
             */


            // Post-conditions:
            assert root.keys.size() == 1;
            assert root.children.size() == 2;
        }

        assert !root.isFull();
        root.insert(key);
    }

    /**
     * Returns the number of keys in this tree.
     */

    int size() {
        return n;
    }

    /**
     * Returns true iff this tree is empty.
     */

    boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Clears all keys from this tree.
     */

    void clear() {
        n = 0;
        root = new Node();
    }

    /**
     * Returns a textual representation of this tree. Defers to
     * Node.toString().
     */

    public String toString() {
        return root.toString();
    }
}

