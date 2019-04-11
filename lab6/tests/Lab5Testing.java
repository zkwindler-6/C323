import static org.junit.Assert.*;

import org.junit.Test;

public class Lab5Testing {

    @Test
    public void BSTinsertInteger() {
        Integer[] a = new Integer[]{3, 9, 7, 2, 1, 5, 6, 4, 8};
        Tree<Integer> bst = new BinarySearchTree<>();
        assertTrue(bst.isEmpty());
        for (Integer key : a)
            bst.insert(key);
        assertEquals(a.length, bst.size());
        for (Integer key : a)
            assertTrue(bst.contains(key));
    }

    @Test
    public void BSTinsertString() {
        String[] a = new String[]{"dog", "rat", "bat", "cat", "cow", "pig",};
        Tree<String> bst = new BinarySearchTree<>();
        assertTrue(bst.isEmpty());
        for (String key : a)
            bst.insert(key);
        assertEquals(a.length, bst.size());
        for (String key : a)
            assertTrue(bst.contains(new String(key)));
    }

    @Test
    public void BSTtoString() {
        Tree<Integer> bst = new BinarySearchTree<>();
        assertEquals("()", bst.toString());
        bst.insert(3);
        assertEquals("(3)", bst.toString());
        bst.insert(9);
        assertEquals("(3 9)", bst.toString());
        bst.insert(7);
        assertEquals("(3 7 9)", bst.toString());
        bst.insert(2);
        assertEquals("(2 3 7 9)", bst.toString());
        bst.insert(1);
        assertEquals("(1 2 3 7 9)", bst.toString());
        bst.insert(5);
        assertEquals("(1 2 3 5 7 9)", bst.toString());
        bst.insert(6);
        assertEquals("(1 2 3 5 6 7 9)", bst.toString());
        bst.insert(4);
        assertEquals("(1 2 3 4 5 6 7 9)", bst.toString());
        bst.insert(8);
        assertEquals("(1 2 3 4 5 6 7 8 9)", bst.toString());
    }

    @Test
    public void BSTlargestNode() {
        Integer[] a = new Integer[]{3, 9, 7, 2, 1, 5, 6, 4, 8};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertEquals(0, bst.n);
        assertNull(bst.root);
        assert bst.root == null;
        for (Integer key : a)
            bst.insert(key);
        assertEquals(a.length, bst.n);
        Integer expected = 9;
        assertEquals(expected, bst.largestNode(bst.root).data);
        expected = 2;
        assertEquals(expected, bst.largestNode(bst.root.left).data);
        expected = 9;
        assertEquals(expected, bst.largestNode(bst.root.right).data);
        expected = 8;
        assertEquals(expected, bst.largestNode(bst.root.right.left).data);
    }

    @Test
    public void BSTremoveSmall() {
        Tree<Integer> bst = new BinarySearchTree<>();
        assertFalse(bst.remove(2));
        bst.insert(2);
        assertTrue(bst.remove(2));
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
        bst.insert(3);
        bst.insert(2);
        assertTrue(bst.remove(2));
        assertEquals(1, bst.size());
        bst.insert(4);
        assertTrue(bst.remove(4));
        assertEquals(1, bst.size());
        bst.insert(1);
        bst.insert(2);
        assertTrue(bst.remove(1));
        assertEquals(2, bst.size());
        bst.insert(5);
        bst.insert(4);
        assertTrue(bst.remove(5));
        assertEquals(3, bst.size());
    }

    @Test
    public void BSTremoveMedium() {
        Integer[] a = new Integer[]{3, 9, 7, 2, 1, 5, 6, 4, 8};
        Tree<Integer> bst = new BinarySearchTree<>();
        assertTrue(bst.isEmpty());
        for (Integer key : a)
            bst.insert(key);
        assertEquals(a.length, bst.size());
        assertTrue(bst.remove(7));
        assertEquals(8, bst.size());
        assertTrue(bst.remove(3));
        assertEquals(7, bst.size());
        assertTrue(bst.remove(9));
        assertEquals(6, bst.size());
    }

}
