import static org.junit.Assert.*;

import org.junit.Test;

public class Lab6Testing {

    @Test
    public void smallInsertNodeSize() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(3);
        assertEquals(1, bst.root.n);
        bst.insert(2);
        assertEquals(2, bst.root.n);
        assertEquals(1, bst.root.left.n);
        bst.insert(4);
        assertEquals(3, bst.root.n);
        assertEquals(1, bst.root.left.n);
        assertEquals(1, bst.root.right.n);
    }

    @Test
    public void mediumInsertNodeSize() {
        Integer[] a = new Integer[]{3, 9, 7, 2, 1, 5, 6, 4, 8};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (Integer key : a)
            bst.insert(key);
        assertEquals(9, bst.root.n);
        assertEquals(2, bst.root.left.n);
        assertEquals(1, bst.root.left.left.n);
        assertEquals(6, bst.root.right.n);
        assertEquals(5, bst.root.right.left.n);
        assertEquals(3, bst.root.right.left.left.n);
        assertEquals(1, bst.root.right.left.right.n);
        assertEquals(1, bst.root.right.left.left.left.n);
        assertEquals(1, bst.root.right.left.left.right.n);
    }

    @Test
    public void removeNodeSize() {
        Integer[] a = new Integer[]{3, 9, 7, 2, 1, 5, 6, 4, 8};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (Integer key : a)
            bst.insert(key);

        bst.remove(7);
        assertEquals(8, bst.root.n);
        assertEquals(2, bst.root.left.n);
        assertEquals(1, bst.root.left.left.n);
        assertEquals(5, bst.root.right.n);
        assertEquals(4, bst.root.right.left.n);
        assertEquals(1, bst.root.right.left.right.n);
        assertEquals(2, bst.root.right.left.left.n);
        assertEquals(1, bst.root.right.left.left.left.n);

        bst.remove(3);
        assertEquals(7, bst.root.n);
        assertEquals(1, bst.root.left.n);
        assertEquals(5, bst.root.right.n);
        assertEquals(4, bst.root.right.left.n);
        assertEquals(1, bst.root.right.left.right.n);
        assertEquals(2, bst.root.right.left.left.n);
        assertEquals(1, bst.root.right.left.left.left.n);

        // test removing keys that aren't in the tree
        assertFalse(bst.remove(3));
        assertFalse(bst.remove(7));
        assertFalse(bst.remove(0));
        assertFalse(bst.remove(10));
        assertEquals(7, bst.root.n);
        assertEquals(1, bst.root.left.n);
        assertEquals(5, bst.root.right.n);
        assertEquals(4, bst.root.right.left.n);
        assertEquals(1, bst.root.right.left.right.n);
        assertEquals(2, bst.root.right.left.left.n);
        assertEquals(1, bst.root.right.left.left.left.n);
    }

    @Test
    public void kthSmallest() {
        Integer[] a = new Integer[]{3, 9, 7, 2, 1, 5, 6, 4, 8};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (Integer key : a)
            bst.insert(key);
        for (Integer i = 1; i <= 9; i++)
            assertEquals(i, bst.kthSmallest(i));
    }

    @Test
    public void kthSmallestExceptions() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        try {
            bst.kthSmallest(0);
            fail();
        } catch (IllegalArgumentException ex) {
        }

        Integer[] a = new Integer[]{3, 9, 7, 2, 1, 5, 6, 4, 8};
        for (Integer key : a)
            bst.insert(key);

        try {
            bst.kthSmallest(0);
            fail();
        } catch (IllegalArgumentException ex) {
        }

        try {
            bst.kthSmallest(10);
            fail();
        } catch (IllegalArgumentException ex) {
        }

        // make sure the node sizes didn't get messed up
        assertEquals(9, bst.root.n);
        assertEquals(2, bst.root.left.n);
        assertEquals(1, bst.root.left.left.n);
        assertEquals(6, bst.root.right.n);
        assertEquals(5, bst.root.right.left.n);
        assertEquals(3, bst.root.right.left.left.n);
        assertEquals(1, bst.root.right.left.right.n);
        assertEquals(1, bst.root.right.left.left.left.n);
        assertEquals(1, bst.root.right.left.left.right.n);
    }

}
