import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Iterator;

public class DictionaryTesting {

    @Test
    public void dictionaryStartsWithTest() {
        Dictionary dict = new Dictionary();
        String[] words;
        words = new String[]{"bat", "boa", "boat", "bug", "cake", "cat",
                "cats", "cow", "do", "dog", "bake", "cattle",
                "cart", "corn", "dig", "dot", "door", "dust"};
        for (String word : words)
            dict.insert(word);
        List<String> ls = dict.startsWith("ca");
        assertEquals(5, ls.size());
        assertTrue(ls.contains("cat"));
        assertTrue(ls.contains("cats"));
        assertTrue(ls.contains("cake"));
        assertTrue(ls.contains("cattle"));
        assertTrue(ls.contains("cart"));
        assertEquals(dict.size(), dict.startsWith("").size());
        assertEquals(7, dict.startsWith("c").size());
        dict.remove("cake");
        assertEquals(6, dict.startsWith("c").size());
        assertEquals(6, dict.startsWith("d").size());
    }

    @Test
    public void testUnorderdIteration() {
        Dictionary dict = new Dictionary();
        String[] words;
        words = new String[]{"bat", "boa", "boat", "bug", "cake", "cat",
                "cart", "corn", "dig", "dot", "door", "dust",
                "cats", "cow", "do", "dog", "bake", "cattle"};
        java.util.Arrays.sort(words);
        for (String word : words)
            dict.insert(word);
        Iterator<String> it = dict.iterator();
        int matches = 0, i = 0;
        while (it.hasNext()) {
            String next = it.next();
            if (next.equals(words[i++]))
                matches++;
        }
        assertFalse(matches == words.length); // with high probability
    }

    @Test
    public void filterDictionary() {
        Dictionary dict = new Dictionary(word -> word.length() >= 14);
        int n = dict.size();
        assertEquals(113, n);
        assertTrue(dict.contains("psychoanalysis"));
        assertTrue(dict.contains("neuropathology"));
        for (String word : dict) {
            dict.remove(word);
            n--;
            assertEquals(n, dict.size());
        }
        assertTrue(dict.isEmpty());

        dict = new Dictionary(word -> word.length() == 5);
        assertFalse(dict.contains("cat"));
        assertFalse(dict.contains("psychoanalysis"));
        n = dict.size();
        assertEquals(2425, n);

        dict = new Dictionary(word -> word.endsWith("ing"));
        assertTrue(dict.contains("earthshaking"));
        n = dict.size();
        assertEquals(282, n);
    }
}