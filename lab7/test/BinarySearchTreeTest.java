import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class BinarySearchTreeTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(1);

    @Test
    public void traverseSampleTest() {
        String[] input = new String[] {
            "sardonic",
            "prusiano",
            "scriniary",
            "unpunctuated",
            "regrede",
            "turgoid",
            "unfernlike",
            "lambling",
            "predeficiency",
            "isogeotherm"
        };

        String[] output = new String[] {
            "sardonic",
            "prusiano",
            "scriniary",
            "lambling",
            "regrede",
            "unpunctuated",
            "isogeotherm",
            "predeficiency",
            "turgoid",
            "unfernlike"
        };

        BinarySearchTree<String> bst = new BinarySearchTree<>();
        for (String str : input)
            bst.insert(str);

        List<String> expected = Arrays.asList(output);
        assertEquals("traverse() should do a level-order traversal", expected, bst.traverse());
    }

    @Test
    public void traverseInvariantTest() {
        Random rand = new Random();
        int size = 750 + rand.nextInt(250);
        Integer[] input = rand.ints(size).sorted().boxed().toArray(Integer[]::new);
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int val : input)
            bst.insert(val);
        // This is just a skinny tree!
        Integer[] output = new Integer[size];
        output = bst.traverse().toArray(output);

        assertArrayEquals("traverse() should just return the same sorted tree", input, output);
    }

}
