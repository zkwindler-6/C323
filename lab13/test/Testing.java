import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Random;
import java.util.Iterator;

public class Testing {

    @Test
    public void testKeys() {
        Map<Integer, String> hm = new HashMap<>();
        hm.put(3, "three");
        hm.put(7, "seven");
        hm.put(9, "nine");
        hm.put(1, "one");
        hm.put(2, "two");
        Iterator<Integer> it = hm.keys();
        int sum = 0;
        while (it.hasNext())
            sum += it.next();
        assertEquals(3 + 7 + 9 + 1 + 2, sum);
    }

    @Test
    public void testEmpty() {
        LinkedGraph gr = new LinkedGraph("G");
        assertEquals("G", gr.getName());
        assertEquals(0, gr.getNumVertices());
        assertEquals(0, gr.getNumEdges());
        assertFalse(gr.hasVertex("A"));
        assertFalse(gr.hasEdge("A", "B"));
    }

    @Test
    public void testAddEdge() {
        Graph gr = new LinkedGraph("Planar");
        /*
         *             A
         *             |\
         *             | \
         *             B  D---E
         *             | /
         *             |/
         *             C
         */
        gr.addEdge("A", "B");
        gr.addEdge("B", "C", 2);
        gr.addEdge("C", "D", 3);
        gr.addEdge("D", "A", 4);
        gr.addEdge("D", "E", 5);
        assertEquals(5, gr.getNumVertices());
        assertEquals(5, gr.getNumEdges());
        assertTrue(gr.hasVertex("A"));
        assertTrue(gr.hasEdge("A", "B"));
        assertTrue(gr.hasEdge("B", "A"));
        assertEquals(1, gr.weight("A", "B"));
        assertEquals(3, gr.weight("C", "D"));

        /*
         *             A---.
         *             |\   \
         *             | \   \
         *             B  D---E
         *             | /
         *             |/
         *             C
         */
        gr.addEdge("A", "E");
        assertEquals(5, gr.getNumVertices());
        assertEquals(6, gr.getNumEdges());
        System.out.println("\n" + gr);
    }

    @Test
    public void testDisconnected() {
        /*
         *             A
         *             |
         *             |
         *             B
         *
         *             C      G
         *             |     /
         *             |    /
         *             D---E---F
         *
         *             H
         */
        Graph gr = new LinkedGraph("ThreeComponents");
        gr.addEdge("A", "B", 1);
        gr.addEdge("C", "D", 2);
        gr.addEdge("D", "E", 3);
        gr.addEdge("E", "F", 4);
        gr.addEdge("E", "G", 5);
        gr.addVertex("H");
        assertEquals(8, gr.getNumVertices());
        assertEquals(5, gr.getNumEdges());
        assertFalse(gr.hasEdge("B", "C"));
        assertFalse(gr.hasEdge("A", "H"));
        assertFalse(gr.hasEdge("G", "F"));
        assertFalse(gr.hasEdge("B", "F"));
        System.out.println("\n" + gr);
    }

    @Test
    public void testAdj() {
        Graph gr = new LinkedGraph("G");
        /*
         *             A
         *             |\
         *             | \
         *             B  D---E
         *             | /
         *             |/
         *             C
         */
        gr.addEdge("A", "B");
        gr.addEdge("B", "C", 2);
        gr.addEdge("C", "D", 3);
        gr.addEdge("D", "A", 4);
        gr.addEdge("D", "E", 5);
        List<String> vs;
        vs = gr.adj("A");
        assertEquals(2, vs.size());
        assertTrue(vs.contains("B"));
        assertTrue(vs.contains("D"));
        vs = gr.adj("B");
        assertEquals(2, vs.size());
        assertTrue(vs.contains("A"));
        assertTrue(vs.contains("C"));
        vs = gr.adj("C");
        assertEquals(2, vs.size());
        assertTrue(vs.contains("B"));
        assertTrue(vs.contains("D"));
        vs = gr.adj("D");
        assertEquals(3, vs.size());
        assertTrue(vs.contains("A"));
        assertTrue(vs.contains("C"));
        assertTrue(vs.contains("E"));
        vs = gr.adj("E");
        assertEquals(1, vs.size());
        assertTrue(vs.contains("D"));
        vs = gr.adj("F");
        assertEquals(0, vs.size());
    }

    // DELETE FROM STARTER
//    @Test
//    public void testHops() {
//        LinkedGraph gr = new LinkedGraph("G");
//        /*
//         *             A---.
//         *             |\   \
//         *             | \   \
//         *             B  D---E
//         *             | /
//         *             |/
//         *             C
//         */
//        gr.addEdge("A", "B");
//        gr.addEdge("B", "C", 2);
//        gr.addEdge("C", "D", 3);
//        gr.addEdge("D", "A", 4);
//        gr.addEdge("D", "E", 5);
//        gr.addEdge("A", "E");
//        List<String> neighbors;
//        neighbors = gr.twoHopsAway("A");
//        assertTrue(neighbors.contains("C"));
//        assertTrue(neighbors.contains("D"));
//        assertTrue(neighbors.contains("E"));
//        assertFalse(neighbors.contains("A"));
//        assertEquals(3, neighbors.size());
//    }

    @Test
    public void testIsConnected() {
        LinkedGraph gr = new LinkedGraph("TwoComponents");
        assertFalse(gr.isConnected("A", "B"));
        /*
         *             A---.
         *             |\   \
         *             | \   \
         *             B  D---E
         *             | /
         *             |/
         *             C
         *
         *             F---G---H
         */
        gr.addEdge("A", "B");
        assertTrue(gr.isConnected("A", "B"));
        assertTrue(gr.isConnected("B", "A"));
        gr.addVertex("F");
        gr.addVertex("G");
        assertFalse(gr.isConnected("F", "G"));
        gr.addEdge("B", "C", 2);
        gr.addEdge("C", "D", 3);
        gr.addEdge("D", "A", 4);
        gr.addEdge("D", "E", 5);
        gr.addEdge("A", "E");
        gr.addEdge("F", "G");
        gr.addEdge("G", "H");
        System.out.println("\n" + gr);

        String[][] components = new String[][]{
                new String[]{"A", "B", "C", "D", "E"},  // component 0
                new String[]{"F", "G", "H"},            // component 1
        };
        // all vertices in component 0 are connected to each other
        for (String v : components[0])
            for (String w : components[0])
                assertTrue(gr.isConnected(v, w));
        // all vertices in component 1 are connected to each other
        for (String v : components[1])
            for (String w : components[1])
                assertTrue(gr.isConnected(v, w));
        // no vertex in component 0 is connected to a vertex in component 1
        for (String v : components[0])
            for (String w : components[1])
                assertFalse(gr.isConnected(v, w));
        // no vertex in component 1 is connected to a vertex in component 0
        for (String v : components[1])
            for (String w : components[0])
                assertFalse(gr.isConnected(v, w));
    }

}