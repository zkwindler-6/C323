import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Random;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * The first three tests will run on the starter code.
 */

public class Testing {

    @Test
    public void testInorder() {
        BTree bt;
        bt = new BTree(3);
        assertEquals(0, bt.inorder().size());

        bt = makeStarterTree();

        /**
         *             8
         *         /        \
         *    1, 4, 5     10, 12
         */

        List<Integer> ls = bt.inorder();
        assertEquals(6, ls.size());

        for (Integer x : new int[]{1, 4, 5, 8, 10, 12})
            assertEquals(x, ls.remove(0));
    }

    @Test
    public void testSearch() {
        BTree bt = makeStarterTree();

        /**
         *             8
         *         /        \
         *    1, 4, 5     10, 12
         */

        assertEquals(6, bt.size());
        assertEquals(1, bt.search(1).get());
        assertEquals(4, bt.search(4).get());
        assertEquals(5, bt.search(5).get());
        assertEquals(8, bt.search(8).get());
        assertEquals(10, bt.search(10).get());
        assertEquals(12, bt.search(12).get());

        assertNull(bt.search(3));

        BTree.Location loc = bt.search(4);
        assertEquals(bt.root.children.get(0), loc.p);
        assertEquals(1, loc.i);
        assertEquals(4, loc.get());
    }

    @Test
    public void testSimpleInsert() {
        BTree bt = makeStarterTree();

        /**
         *             8
         *         /        \
         *    1, 4, 5     10, 12
         */

        bt.insert(18);

        /**
         *             8
         *         /        \
         *    1, 4, 5     10, 12, 18
         */

        assertEquals(7, bt.size());
        assertEquals(18, bt.search(18).get());
    }

    @Test
    public void testHeight() {
        BTree bt = new BTree(15);
        assertEquals(1, bt.height());

        bt = makeStarterTree();

        /**
         *             8
         *         /        \
         *    1, 4, 5     10, 12
         */

        assertEquals(2, bt.height());
        assertEquals(1, bt.root.children.get(0).height());
        assertEquals(1, bt.root.children.get(1).height());
    }

    @Test
    public void testInsertWithSplit() {
        BTree bt = makeStarterTree();
        bt.insert(18);

        /**
         *             8
         *         /        \
         *    1, 4, 5     10, 12, 18
         */


        // This will trigger a split.
        bt.insert(3);

        /**
         *            4, 8
         *         /    |    \
         *      1, 3    5   10, 12, 18
         */

        assertEquals(8, bt.size());
        assertEquals(2, bt.root.keys.size());
        assertEquals(Integer.valueOf(4), bt.root.keys.get(0));
        assertEquals(Integer.valueOf(8), bt.root.keys.get(1));
        assertEquals(2, bt.root.children.get(0).keys.size());
        assertEquals(Integer.valueOf(1), bt.root.children.get(0).keys.get(0));
        assertEquals(Integer.valueOf(3), bt.root.children.get(0).keys.get(1));
        assertEquals(1, bt.root.children.get(1).keys.size());
        assertEquals(Integer.valueOf(5), bt.root.children.get(1).keys.get(0));
        assertEquals(3, bt.root.children.get(2).keys.size());
        assertEquals(Integer.valueOf(10), bt.root.children.get(2).keys.get(0));
        assertEquals(Integer.valueOf(12), bt.root.children.get(2).keys.get(1));
        assertEquals(Integer.valueOf(18), bt.root.children.get(2).keys.get(2));

        // This will trigger a split.
        bt.insert(9);

        /**
         *            4,  8,  12
         *         /    /   \    \
         *      1, 3   5   9, 10   18
         */

        assertEquals(9, bt.size());
        assertEquals(3, bt.root.keys.size());
        assertEquals(Integer.valueOf(4), bt.root.keys.get(0));
        assertEquals(Integer.valueOf(8), bt.root.keys.get(1));
        assertEquals(Integer.valueOf(12), bt.root.keys.get(2));
        assertEquals(2, bt.root.children.get(2).keys.size());
        assertEquals(Integer.valueOf(9), bt.root.children.get(2).keys.get(0));
        assertEquals(Integer.valueOf(10), bt.root.children.get(2).keys.get(1));
        assertEquals(1, bt.root.children.get(3).keys.size());
        assertEquals(Integer.valueOf(18), bt.root.children.get(3).keys.get(0));
    }


    @Test
    public void testRootSplitting() {
        BTree bt;

        for (int key = 1; key <= 15; key += 2) {
            // Create the empty tree.
            bt = new BTree(7);
            // Fill up the root with even ints.
            for (int i = 2; i <= 14; i += 2)
                bt.insert(i);

            /**
             * 2, 4, 6, 8, 10, 12, 14
             */

            assertTrue(bt.root.isFull());
            assertEquals(1, bt.height());

            // Split the root and bubble up the 8.
            bt.insert(key);

            assertEquals(1, bt.root.keys.size());
            assertEquals(2, bt.height());
            assertEquals(Integer.valueOf(8), bt.root.keys.get(0));

            // Verify that the insert took place in the correct child.
            if (key < 8) {
                assertEquals(4, bt.root.children.get(0).keys.size());
                assertEquals(3, bt.root.children.get(1).keys.size());
            } else {
                assertEquals(3, bt.root.children.get(0).keys.size());
                assertEquals(4, bt.root.children.get(1).keys.size());
            }
        }

        bt = makeStarterTree();

        /**
         *             8
         *         /        \
         *    1, 4, 5     10, 12
         */

        bt.insert(18); // no split, inserts into 10, 12 to get 10, 12, 18
        bt.insert(3);  // splits 1, 4, 5 into 1, 3 and 5, bubbles 4 up
        bt.insert(9);  // splits 10, 12, 18 into 9, 10 and 18, bubbles 12 up

        /**
         *            4,  8, 12
         *         /    |   |     \
         *      1, 3    5  9, 10   18
         */

        // The root is full.
        assertTrue(bt.root.isFull());
        for (BTree.Node child : bt.root.children)
            assertTrue(child.isLeaf());
        assertEquals(2, bt.height());
        assertEquals(9, bt.size());

        // This will trigger a split in the root.
        bt.insert(7);

        /**
         *                   8
         *              /         \
         *            4             12
         *         /    \         /    \
         *      1, 3    5, 7    9, 10    18
         */
        assertEquals(10, bt.size());
        assertFalse(bt.root.isFull());
        for (BTree.Node child : bt.root.children)
            assertFalse(child.isLeaf());

        assertEquals(1, bt.root.keys.size());
        assertEquals(Integer.valueOf(8), bt.root.keys.get(0));
        assertEquals(1, bt.root.children.get(0).keys.size());
        assertEquals(Integer.valueOf(4), bt.root.children.get(0).keys.get(0));
        assertEquals(1, bt.root.children.get(1).keys.size());
        assertEquals(Integer.valueOf(12), bt.root.children.get(1).keys.get(0));
        assertEquals(2, bt.root.children.get(0).children.get(1).keys.size());
        assertEquals(Integer.valueOf(5),
                bt.root.children.get(0).children.get(1).keys.get(0));
        assertEquals(Integer.valueOf(7),
                bt.root.children.get(0).children.get(1).keys.get(1));
    }

    @Test
    public void testIgnoreDuplicateKeys() {
        BTree bt = makeStarterTree();

        /**
         *             8
         *         /        \
         *    1, 4, 5     10, 12
         */

        for (int x : new int[]{8, 1, 4, 5, 10, 12}) {
            bt.insert(x); // does not add
            assertEquals(6, bt.size());
        }
    }

    @Test
    public void testLargerBuckets() {
        BTree bt = new BTree(5);
        int[] a = new int[]{
                1, 2, 3, 5, 6, 7, 9, 12, 16, 18, 21, 11, 4, 8,
                10, 13, 14, 15, 17, 19, 20, 52, 43, 36, 47, 54,
                22, 23, 24, 25,
        };
        for (int i = 0; i < a.length; i++)
            bt.insert(a[i]);
        assertEquals(bt.size(), a.length);
        for (int i = 0; i < a.length; i++)
            bt.insert(a[i]);
        assertEquals(bt.size(), a.length);

        assertEquals(2, bt.root.keys.size());
        assertEquals(Integer.valueOf(10), bt.root.keys.get(0));
        assertEquals(Integer.valueOf(19), bt.root.keys.get(1));

        bt = new BTree(7);
        for (int i = 0; i < a.length; i++)
            bt.insert(a[i]);
        assertEquals(bt.size(), a.length);
        assertEquals(5, bt.root.keys.size());
        assertEquals(Integer.valueOf(5), bt.root.keys.get(0));
        assertEquals(Integer.valueOf(12), bt.root.keys.get(1));
        assertEquals(Integer.valueOf(16), bt.root.keys.get(2));
        assertEquals(Integer.valueOf(20), bt.root.keys.get(3));
        assertEquals(Integer.valueOf(43), bt.root.keys.get(4));
    }

    @Test
    public void stressInsert() {
        BTree bt;
        bt = makeBigTree(2000);
        assertEquals(10, bt.height());
        bt = makeRandomTree(100_000);
        assertEquals(10, bt.height());
    }

    @Test
    public void stressInorder() {
        int n = 100_000;
        BTree bt = makeRandomTree(n);
        Iterator<Integer> it = bt.inorder().iterator();
        Integer prev = it.next(), curr;
        while (it.hasNext()) {
            curr = it.next();
            if (prev <= curr)
                prev = curr;
            else
                fail();
        }
    }

    /**
     * Small utility to engineer a 3-node starter tree, independent
     * of BTree.insert().
     */

    public static BTree makeStarterTree() {
        BTree bt = new BTree(3);

        /**
         *             8
         *         /        \
         *    1, 4, 5     10, 12
         */

        bt.root.keys.add(8);
        bt.root.children.add(bt.new Node());
        bt.root.children.add(bt.new Node());
        bt.root.children.get(0).keys.add(1);
        bt.root.children.get(0).keys.add(4);
        bt.root.children.get(0).keys.add(5);
        bt.root.children.get(1).keys.add(10);
        bt.root.children.get(1).keys.add(12);
        bt.n = 6;
        return bt;
    }

    public static BTree makeBigTree(int n) {
        BTree bt = new BTree(3);
        for (int x = 1; x <= n / 2; x++)
            bt.insert(x);
        for (int x = n; x > n / 2; x--)
            bt.insert(x);
        assertEquals(n, bt.size());
        return bt;
    }

    public static BTree makeRandomTree(int n) {
        int b = 5;
        BTree bt = new BTree(b);
        Random gen = new Random();
        Set<Integer> xs = new HashSet<>();
        while (xs.size() != n)
            xs.add(gen.nextInt(b * n));
        for (int x : xs)
            bt.insert(x);
        assertEquals(xs.size(), bt.size());
        return bt;
    }

}

