import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Iterator;

public class MapTesting {

    @Test
    public void smallValuesTest() {
        Map<Integer, Integer> hm = new HashMap<>();
        Iterator<Integer> it = hm.values();
        assertFalse(it.hasNext());
        hm.put(2, 3);
        it = hm.values();
        assertTrue(it.hasNext());
        assertEquals(Integer.valueOf(3), it.next());
    }

    @Test
    public void basicValuesTest() {
        List<String> ls = new DoublyLinkedList<>();
        ls.add("one");
        ls.add("two");
        ls.add("three");
        ls.add("four");
        ls.add("five");
        ls.add("six");
        ls.add("seven");
        ls.add("eight");
        ls.add("nine");
        ls.add("ten");
        Map<Integer, String> hm = new HashMap<>();
        for (int i = 0; i < ls.size(); i++)
            hm.put(i + 1, ls.get(i));
        int n = 0;
        Iterator<String> it = hm.values();
        assertTrue(it.hasNext());
        while (it.hasNext()) {
            String s = it.next();
            assertTrue(ls.contains(s));
            for (int j = 0; j < ls.size(); j++)
                if (ls.get(j).equals(s)) {
                    ls.remove(j);
                    break;
                }
            n++;
        }
        assertEquals(n, hm.size());
        assertTrue(ls.isEmpty());
    }

    @Test
    public void independenceValuesTest() {
        Map<String, Integer> hm = new HashMap<>();
        hm.put("blue", 5);
        hm.put("green", 7);
        hm.put("yellow", 4);
        Iterator<Integer> it;
        it = hm.values();
        Integer x, y, z;
        x = it.next();
        y = it.next();
        z = it.next();
        assertEquals(5 + 7 + 4, x + y + z);
    }
}
