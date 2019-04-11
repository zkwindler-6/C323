import static org.junit.Assert.*;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.regexp.internal.REDebugCompiler;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * There is MUCH for you to do here!!
 * <p>
 * JUnit tests for all TODO methods.
 * <p>
 * We have started writing some tests for you, but you will need to write
 * many more of your own. The first few tests are translations of the basic
 * asserts given to you in the starter code for the DoublyLinkedList class.
 * We also provide a simple test of the new List.remove() method you are to
 * implement.
 * <p>
 * After you have completely debugged your DoublyLinkedList class, you should
 * move on to the Coord class. There are just a couple of methods for you
 * to implement there. A few very simple tests are provided here, but you
 * will need to augment them with tests of your own. Please do not skimp
 * on testing.
 * <p>
 * When you've done with Coord, start working on the Board class. This is
 * where you will put most of your effort. You are on your own to write
 * the unit tests.
 */

public class Testing {

    @Test
    public void testDLL() {
        List<Integer> xs = new DoublyLinkedList<>();
        int[] a = new int[]{4, 3, 6, 5, 7, 8};
        for (int x : a)
            xs.add(x);
        assertEquals(6, xs.size());
        for (int i = 0; i < a.length; i++) {
            int x = xs.get(i);
            assertEquals(a[i], x);
        }
        assertFalse(xs.contains(null));
        for (int x : a)
            assertTrue(xs.contains(x));
        assertEquals("(4 3 6 5 7 8)", xs.toString());
        int val = xs.remove(0);
        assertEquals(4, val);
        val = xs.remove(1);
        assertEquals(6, val);
        assertEquals(4, xs.size());
        assertEquals("(3 5 7 8)", xs.toString());
        while (!xs.isEmpty())
            xs.remove(xs.size() - 1);
        assertEquals(0, xs.size());
        assertEquals("()", xs.toString());
        for (int x : a)
            xs.add(x);
        assertEquals("(4 3 6 5 7 8)", xs.toString());
        for (int x : xs)
            assertTrue(xs.contains(x));
    }

    @Test
    public void testDLLExceptions() {
        List<Integer> xs = new DoublyLinkedList<>();
        int[] a = new int[]{4, 3, 6, 5, 7, 8};
        for (int x : a)
            xs.add(x);
        Iterator<Integer> it = xs.iterator();
        try {
            it.remove();
            fail();
        } catch (IllegalStateException ex) {
        }
        assertEquals("(4 3 6 5 7 8)", xs.toString());
        assertTrue(it.hasNext());
        int val = it.next();
        assertEquals(4, val);
        it.remove();
        assertEquals("(3 6 5 7 8)", xs.toString());
        try {
            it.remove();
            fail();
        } catch (IllegalStateException ex) {
        }
        it = xs.iterator();
        while (it.hasNext())
            if (it.next() % 2 == 0)
                it.remove();
        assertEquals(3, xs.size());
        assertEquals("(3 5 7)", xs.toString());
        try {
            for (Integer i : xs) {
                if (i != 3)
                    xs.remove(0);
            }
            fail();
        } catch (ConcurrentModificationException ex) {
        }
        assertEquals("(5 7)", xs.toString());
        try {
            for (Integer i : xs)
                xs.add(i);
            fail();
        } catch (ConcurrentModificationException ex) {
        }
        assertEquals("(5 7 5)", xs.toString());

        try {
            for (int x : xs) {
                it = xs.iterator();
                while (it.hasNext()) {
                    int y = it.next();
                    if (y == 7)
                        it.remove();
                }
            }
            fail();
        } catch (ConcurrentModificationException ex) {
        }
    }

    @Test
    public void testDLLOverloadedRemove() {
        List<String> xs = new DoublyLinkedList<>();
        String[] a = new String[]{"cat", "dog", "pig", "cow", "rat", "bat"};
        for (String x : a)
            xs.add(x);
        assertTrue(xs.remove("cat"));
        assertEquals("dog", xs.get(0));
        assertTrue(xs.remove("bat"));
        assertEquals("rat", xs.get(xs.size() - 1));
        assertTrue(xs.remove(new String("pig")));
        assertEquals("cow", xs.get(1));
    }

    @Test
    public void testOnBoard() {
        assertFalse(new Coord(3, 4).onBoard(4));
        assertTrue(new Coord(3, 4).onBoard(5));
        assertFalse(new Coord(2, -1).onBoard(3));
        // add lots more tests of your own!

        // my own tests
        assertFalse(new Coord(0, -1).onBoard(2));
        assertTrue(new Coord(0, 0).onBoard(1));
        assertFalse(new Coord(3, 3).onBoard(3));
        assertTrue(new Coord(4, 5).onBoard(6));
        assertFalse(new Coord(2, 5).onBoard(4));
        assertTrue(new Coord(3, 1).onBoard(5));
        assertFalse(new Coord(0, 8).onBoard(8));
        assertTrue(new Coord(2, 2).onBoard(3));
    }

    @Test
    public void testNeighbors() {
        List<Coord> neighbors;
        neighbors = new Coord(0, 0).neighbors(5);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(new Coord(1, 0)));
        assertTrue(neighbors.contains(new Coord(0, 1)));

        neighbors = new Coord(0, 2).neighbors(5);
        // add three asserts similar to the above three
        assertEquals(3, neighbors.size());
        assertTrue(neighbors.contains(new Coord(0, 1)));
        assertTrue(neighbors.contains(new Coord(1, 2)));
        assertTrue(neighbors.contains(new Coord(0, 3)));

        neighbors = new Coord(2, 3).neighbors(5);
        // add three asserts similar to the above three
        assertEquals(4, neighbors.size());
        assertTrue(neighbors.contains(new Coord(2, 2)));
        assertTrue(neighbors.contains(new Coord(2, 4)));
        assertTrue(neighbors.contains(new Coord(3, 3)));
        assertTrue(neighbors.contains(new Coord(1, 3)));

        neighbors = new Coord(4, 4).neighbors(5);
        // add three asserts similar to the above three
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(new Coord(4, 3)));
        assertTrue(neighbors.contains(new Coord(3, 4)));

        neighbors = new Coord(-1, 3).neighbors(3);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    public void getTest() {

        Board testBoard = new Board(2);
        testBoard.get(new Coord(0, 0)).setColor(WaterColor.RED);
        testBoard.get(new Coord(0, 1)).setColor(WaterColor.BLUE);
        testBoard.get(new Coord(1, 0)).setColor(WaterColor.PINK);
        testBoard.get(new Coord(1, 1)).setColor(WaterColor.CYAN);

        assert testBoard.get(new Coord(0, 0)).equals(new Tile(new Coord(0, 0), WaterColor.RED));
        assert testBoard.get(new Coord(0, 1)).equals(new Tile(new Coord(0, 1), WaterColor.BLUE));
        assert testBoard.get(new Coord(1, 0)).equals(new Tile(new Coord(1, 0), WaterColor.PINK));
        assertFalse(testBoard.get(new Coord(1, 1)).equals(new Tile(new Coord(1, 1), WaterColor.RED)));
        assertFalse(testBoard.get(new Coord(0, 1)).equals(new Tile(new Coord(0, 1), WaterColor.YELLOW)));

        try {
            testBoard.get(new Coord(0, -1));
            fail();
        } catch (CoordOutOfBoundsException ex) {
        }
    }

    @Test
    public void fullyFloodedTest(){
        Board testBoard = new Board(3);
        testBoard.inside = new DoublyLinkedList<>();
        Tile corner = new Tile(new Coord(0,0), WaterColor.BLUE);
        testBoard.inside.add(corner);
        assertFalse(testBoard.fullyFlooded());
        testBoard.inside.add(new Tile (new Coord(0,1), WaterColor.RED));
        testBoard.inside.add(new Tile (new Coord(0,2), WaterColor.YELLOW));
        testBoard.inside.add(new Tile (new Coord(1,0), WaterColor.CYAN));
        testBoard.inside.add(new Tile (new Coord(1,1), WaterColor.BLUE));
        assertFalse(testBoard.fullyFlooded());
        testBoard.inside.add(new Tile (new Coord(1,2), WaterColor.PINK));
        testBoard.inside.add(new Tile (new Coord(2,0), WaterColor.RED));
        testBoard.inside.add(new Tile (new Coord(2,1), WaterColor.BLUE));
        testBoard.inside.add(new Tile (new Coord(2,2), WaterColor.CYAN));
        assertTrue(testBoard.fullyFlooded());
    }

    @Test
    public void floodTest(){
        Board testBoard = new Board(3);
        testBoard.inside = new DoublyLinkedList<>();
        testBoard.outside = new DoublyLinkedList<>();
        Tile corner = new Tile(new Coord(0,0), WaterColor.CYAN);
        testBoard.inside.add(corner);
        testBoard.outside.add(new Tile(new Coord(0,1), WaterColor.RED));
        testBoard.outside.add(new Tile(new Coord(0,2), WaterColor.RED));
        testBoard.outside.add(new Tile(new Coord(1,0), WaterColor.BLUE));
        testBoard.outside.add(new Tile(new Coord(1,1), WaterColor.PINK));
        testBoard.outside.add(new Tile(new Coord(1,2), WaterColor.CYAN));
        testBoard.outside.add(new Tile(new Coord(2,0), WaterColor.YELLOW));
        testBoard.outside.add(new Tile(new Coord(2,1), WaterColor.YELLOW));
        testBoard.outside.add(new Tile(new Coord(2,2), WaterColor.CYAN));
        testBoard.flood(WaterColor.RED);
        assertTrue(testBoard.inside.contains(new Tile(new Coord(0,1),WaterColor.RED)));
        assertTrue(testBoard.inside.contains(new Tile(new Coord(0,2),WaterColor.RED)));
        assertTrue(corner.getColor().equals(WaterColor.RED));
        testBoard.flood(WaterColor.CYAN);
        assertTrue(testBoard.inside.contains(new Tile(new Coord(1,2),WaterColor.CYAN)));
        assertTrue(testBoard.inside.contains(new Tile(new Coord(2,2),WaterColor.CYAN)));
        assertTrue(corner.getColor().equals(WaterColor.CYAN));
        testBoard.flood(WaterColor.YELLOW);
        assertTrue(testBoard.inside.contains(new Tile(new Coord(2,1),WaterColor.YELLOW)));
        assertTrue(testBoard.inside.contains(new Tile(new Coord(2,0),WaterColor.YELLOW)));
        assertTrue(corner.getColor().equals(WaterColor.YELLOW));
        testBoard.flood(WaterColor.BLUE);
        assertTrue(testBoard.inside.contains(new Tile(new Coord(1,0),WaterColor.BLUE)));
        assertTrue(corner.getColor().equals(WaterColor.BLUE));
        testBoard.flood(WaterColor.PINK);
        assertTrue(testBoard.inside.contains(new Tile(new Coord(1,1),WaterColor.PINK)));
        assertTrue(corner.getColor().equals(WaterColor.PINK));
    }

    @Test
    public void suggestTest(){
        Board testBoard = new Board(3);
        testBoard.inside = new DoublyLinkedList<>();
        testBoard.outside = new DoublyLinkedList<>();
        Tile corner = new Tile(new Coord(0,0), WaterColor.CYAN);
        testBoard.inside.add(corner);
        testBoard.outside.add(new Tile(new Coord(0,1), WaterColor.RED));
        testBoard.outside.add(new Tile(new Coord(0,2), WaterColor.BLUE));
        testBoard.outside.add(new Tile(new Coord(1,0), WaterColor.RED));
        testBoard.outside.add(new Tile(new Coord(1,1), WaterColor.PINK));
        testBoard.outside.add(new Tile(new Coord(1,2), WaterColor.CYAN));
        testBoard.outside.add(new Tile(new Coord(2,0), WaterColor.YELLOW));
        testBoard.outside.add(new Tile(new Coord(2,1), WaterColor.YELLOW));
        testBoard.outside.add(new Tile(new Coord(2,2), WaterColor.CYAN));
        assert testBoard.suggest() == WaterColor.RED;
    }
}