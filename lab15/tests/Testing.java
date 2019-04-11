import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Random;
import java.util.Iterator;

public class Testing {
    static LinkedGraph gr1, gr2;

    static {
        gr1 = new LinkedGraph("Planar");
        /*
         *             A---.
         *           1 |\ 4 \
         *             | \   \ 2
         *             B  D---E
         *             | /   1
         *           2 |/ 3
         *             C
         */
        gr1.addEdge("A", "B", 1);
        gr1.addEdge("B", "C", 2);
        gr1.addEdge("C", "D", 3);
        gr1.addEdge("D", "A", 4);
        gr1.addEdge("D", "E", 1);
        gr1.addEdge("A", "E", 2);

        gr2 = new LinkedGraph("Lecture");
        /*
         *           A
         *         / | \
         *     8 /   |2  \ 4
         *      /    |    \
         *    /  7   |   1  \
         *   B ----- C ----- D
         *    \     / \     /
         *    2\  3/   \9  /5
         *      \ /     \ /
         *       E ----- F
         *           6
         */
        gr2.addEdge("A", "B", 8);
        gr2.addEdge("A", "C", 2);
        gr2.addEdge("A", "D", 4);
        gr2.addEdge("B", "C", 7);
        gr2.addEdge("B", "E", 2);
        gr2.addEdge("C", "D", 1);
        gr2.addEdge("C", "E", 3);
        gr2.addEdge("C", "F", 9);
        gr2.addEdge("D", "F", 5);
        gr2.addEdge("E", "F", 6);
    }

    @Test
    public void testPathLength() {
        List<String> path = new DoublyLinkedList<>();
        assertEquals(0, gr1.pathLength(path));
        path.add("A");
        assertEquals(0, gr1.pathLength(path));
        path.add("B");
        assertEquals(1, gr1.pathLength(path));
        path.add("C");
        assertEquals(3, gr1.pathLength(path));
        path.add("D");
        assertEquals(6, gr1.pathLength(path));
        path.add("E");
        assertEquals(7, gr1.pathLength(path));
        path.add("A");
        assertEquals(9, gr1.pathLength(path));
        path.add("D");
        assertEquals(13, gr1.pathLength(path));
        path = new DoublyLinkedList<>();
        for (Character v : "BCEBCEBCEBCEBCEBCE".toCharArray())
            path.add(v.toString());
        assertEquals(70, gr2.pathLength(path));
        path = new DoublyLinkedList<>();
        for (Character v : "EFCEBCADFCBACEFEFDACBE".toCharArray())
            path.add(v.toString());
        assertEquals(105, gr2.pathLength(path));
    }

    @Test
    public void testShortestPath() {
        List<String> path;
        path = gr1.shortestPath("A", "A"); // A
        assertEquals(0, gr1.pathLength(path));
        assertEquals(1, path.size());
        assertEquals("A", path.get(0));
        path = gr1.shortestPath("A", "B"); // A B
        assertEquals(1, gr1.pathLength(path));
        assertEquals(2, path.size());
        assertEquals("A", path.get(0));
        assertEquals("B", path.get(1));
        path = gr1.shortestPath("A", "C"); // A B C
        assertEquals(3, gr1.pathLength(path));
        assertEquals(3, path.size());
        assertEquals("A", path.get(0));
        assertEquals("B", path.get(1));
        assertEquals("C", path.get(2));
        path = gr1.shortestPath("A", "D"); // A E D
        assertEquals(3, path.size());
        assertEquals("A", path.get(0));
        assertEquals("E", path.get(1));
        assertEquals("D", path.get(2));
        path = gr1.shortestPath("A", "E"); // A E
        assertEquals(2, path.size());
        assertEquals(2, gr1.pathLength(path));
        assertEquals("A", path.get(0));
        assertEquals("E", path.get(1));
    }

    @Test
    public void testLectureExample() {
        List<String> path;

        path = gr2.shortestPath("A", "A"); // A
        assertEquals(1, path.size());
        assertEquals(0, gr2.pathLength(path));

        path = gr2.shortestPath("A", "B"); // A C E B
        assertEquals(4, path.size());
        assertEquals(7, gr2.pathLength(path));

        path = gr2.shortestPath("A", "C"); // A C
        assertEquals(2, path.size());
        assertEquals(2, gr2.pathLength(path));

        path = gr2.shortestPath("A", "D"); // A C D
        assertEquals(3, path.size());
        assertEquals(3, gr2.pathLength(path));

        path = gr2.shortestPath("A", "E"); // A C E
        assertEquals(3, path.size());
        assertEquals(5, gr2.pathLength(path));

        path = gr2.shortestPath("A", "F"); // A C D F
        assertEquals(4, path.size());
        assertEquals(8, gr2.pathLength(path));
    }

}