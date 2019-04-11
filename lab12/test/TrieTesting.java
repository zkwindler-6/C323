import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Iterator;

public class TrieTesting {

    @Test
    public void insertTrie() {
        Trie trie = new Trie();
        String[] words;
        words = new String[]{"he", "she", "his", "hers", "her", "him"};
        for (String word : words)
            trie.insert(word);
        assertEquals(6, trie.size());
        assertEquals(2, trie.root.
                children.size());
        assertEquals(2, trie.root.
                children.get('h').
                children.size());
        assertEquals(1, trie.root.
                children.get('s').
                children.size());
        assertEquals(2, trie.root.
                children.get('h').
                children.get('i').
                children.size());
        assertEquals(0, trie.root.
                children.get('h').
                children.get('i').
                children.get('s').
                children.size());
        assertEquals(0, trie.root.
                children.get('h').
                children.get('i').
                children.get('m').
                children.size());
        assertEquals(1, trie.root.
                children.get('h').
                children.get('e').
                children.size());
        assertEquals(1, trie.root.
                children.get('h').
                children.get('e').
                children.get('r').
                children.size());
        assertEquals(0, trie.root.
                children.get('h').
                children.get('e').
                children.get('r').
                children.get('s').
                children.size());
    }

    @Test
    public void doubleInsertTrie() {
        Trie trie = new Trie();
        trie.insert("he");
        trie.insert("he");
        trie.insert("he");
        assertEquals(1, trie.size());
    }

    @Test
    public void containsTrie() {
        Trie trie = new Trie();
        String[] words;
        words = new String[]{"he", "she", "his", "hers", "her", "him"};
        for (String word : words)
            trie.insert(word);
        for (String word : words)
            assertTrue(trie.contains(word));
    }

    @Test
    public void basicRemoveTrie() {
        Trie trie = new Trie();
        trie.remove("harmless");
        assertEquals(0, trie.size());
        String[] words;
        words = new String[]{"he", "she", "his", "hers", "her", "him"};
        for (String word : words)
            trie.insert(word);
        trie.remove("he");
        trie.remove("he");
        assertEquals(5, trie.size());
        trie.insert("ours");
        assertEquals(3, trie.root.children.size());
        assertEquals(6, trie.size());
        assertFalse(trie.contains("he"));
        assertTrue(trie.contains("her"));
        trie.insert("they");
        trie.insert("their");
        trie.insert("these");
        assertEquals(9, trie.size());
        assertEquals(4, trie.root.children.size());
        assertEquals(1, trie.root.
                children.get('t').
                children.size());
        assertEquals(1, trie.root.
                children.get('t').
                children.get('h').
                children.size());
        assertEquals(3, trie.root.
                children.get('t').
                children.get('h').
                children.get('e').
                children.size());
        trie.remove("their");
        assertEquals(8, trie.size());
        assertFalse(trie.contains("their"));
    }

    @Test
    public void collapseRemoveTrie() {
        Trie trie = new Trie();
        trie.insert("he");
        trie.remove("he");
        assertEquals(0, trie.root.children.size());
        assertTrue(trie.isEmpty());
        String[] words;
        words = new String[]{"he", "she", "his", "hers", "her", "him",
                "they", "their", "these"};
        for (String word : words)
            trie.insert(word);
        trie.remove("hers");
        assertEquals(0, trie.root.
                children.get('h').
                children.get('e').
                children.get('r').
                children.size());
        assertEquals(3, trie.root.
                children.get('t').
                children.get('h').
                children.get('e').
                children.size());
        trie.remove("their");
        assertEquals(2, trie.root.
                children.get('t').
                children.get('h').
                children.get('e').
                children.size());
        assertEquals(1, trie.root.
                children.get('t').
                children.get('h').
                children.get('e').
                children.get('s').
                children.size());
    }
}