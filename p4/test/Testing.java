import java.awt.Color;
import java.util.Iterator;

import static org.junit.Assert.*;

import org.junit.Test;

public class Testing {
    private static ColorTable colorTable;

    @Test
    public void addTestSmall() {
        Assoc<String, Integer> a1 = new Assoc<>("cat", 3),
                a2 = new Assoc<>("dog", 7),
                a3 = new Assoc<>("pig", 9),
                a4 = new Assoc<>("cat", 9);
        AList<String, Integer> alist = new AList<>();
        alist.addFront(a1);
        assertEquals(a1, alist.get(0));
        alist.addFront(a2);
        assertEquals(a2, alist.get(0));
        alist.addFront(a3);
        assertEquals(a3, alist.get(0));
        alist.addFront(a4);
        assertEquals(a4, alist.get(0));
    }

    @Test
    public void addTestMedium() {
        Assoc<String, Integer> a1 = new Assoc<>("cat", 3),
                a2 = new Assoc<>("dog", 7),
                a3 = new Assoc<>("pig", 9),
                a4 = new Assoc<>("cat", 1),
                a5 = new Assoc<>("rat", 2),
                a6 = new Assoc<>("bat", 4),
                a7 = new Assoc<>("mouse", 8),
                a8 = new Assoc<>("bird", 6),
                a9 = new Assoc<>("lizard", 10),
                a10 = new Assoc<>("squirrel", 8);
        AList<String, Integer> alist = new AList<>();
        assertTrue(alist.isEmpty());
        alist.addFront(a1);
        assertEquals(a1, alist.get(0));
        alist.addFront(a2);
        assertEquals(a2, alist.get(0));
        alist.addFront(a3);
        assertEquals(a3, alist.get(0));
        alist.addFront(a4);
        assertEquals(a4, alist.get(0));
        alist.addFront(a5);
        assertEquals(a5, alist.get(0));
        alist.addFront(a6);
        assertEquals(a6, alist.get(0));
        alist.addFront(a7);
        assertEquals(a7, alist.get(0));
        alist.addFront(a8);
        assertEquals(a8, alist.get(0));
        alist.addFront(a9);
        assertEquals(a9, alist.get(0));
        alist.addFront(a10);
        assertEquals(a10, alist.get(0));
    }

    @Test
    public void addTestBig() {
        Assoc<String, Integer> a1 = new Assoc<>("cat", 3),
                a2 = new Assoc<>("dog", 7),
                a3 = new Assoc<>("pig", 9),
                a4 = new Assoc<>("cat", 1),
                a5 = new Assoc<>("rat", 2),
                a6 = new Assoc<>("bat", 4),
                a7 = new Assoc<>("mouse", 8),
                a8 = new Assoc<>("bird", 6),
                a9 = new Assoc<>("lizard", 10),
                a10 = new Assoc<>("squirrel", 8),
                a11 = new Assoc<>("cow", 18),
                a12 = new Assoc<>("goat", 14),
                a13 = new Assoc<>("sheep", 11),
                a14 = new Assoc<>("bull", 8),
                a15 = new Assoc<>("zebra", 13),
                a16 = new Assoc<>("tiger", 5),
                a17 = new Assoc<>("lion", 15),
                a18 = new Assoc<>("giraffe", 20),
                a19 = new Assoc<>("hippo", 17),
                a20 = new Assoc<>("penguin", 12);
        AList<String, Integer> alist = new AList<>();
        assertTrue(alist.isEmpty());
        alist.addFront(a1);
        assertEquals(a1, alist.get(0));
        alist.addFront(a2);
        assertEquals(a2, alist.get(0));
        alist.addFront(a3);
        assertEquals(a3, alist.get(0));
        alist.addFront(a4);
        assertEquals(a4, alist.get(0));
        alist.addFront(a5);
        assertEquals(a5, alist.get(0));
        alist.addFront(a6);
        assertEquals(a6, alist.get(0));
        alist.addFront(a7);
        assertEquals(a7, alist.get(0));
        alist.addFront(a8);
        assertEquals(a8, alist.get(0));
        alist.addFront(a9);
        assertEquals(a9, alist.get(0));
        alist.addFront(a10);
        assertEquals(a10, alist.get(0));
        alist.addFront(a11);
        assertEquals(a11, alist.get(0));
        alist.addFront(a12);
        assertEquals(a12, alist.get(0));
        alist.addFront(a13);
        assertEquals(a13, alist.get(0));
        alist.addFront(a14);
        assertEquals(a14, alist.get(0));
        alist.addFront(a15);
        assertEquals(a15, alist.get(0));
        alist.addFront(a16);
        assertEquals(a16, alist.get(0));
        alist.addFront(a17);
        assertEquals(a17, alist.get(0));
        alist.addFront(a18);
        assertEquals(a18, alist.get(0));
        alist.addFront(a19);
        assertEquals(a19, alist.get(0));
        alist.addFront(a20);
        assertEquals(a20, alist.get(0));
    }

    @Test
    public void sizeTestSmall() {
        Assoc<String, Integer> a1 = new Assoc<>("cat", 3),
                a2 = new Assoc<>("dog", 7),
                a3 = new Assoc<>("pig", 9),
                a4 = new Assoc<>("cat", 9);
        AList<String, Integer> alist = new AList<>();
        assertTrue(alist.isEmpty());
        alist.addFront(a1);
        alist.addFront(a2);
        alist.addFront(a3);
        alist.addFront(a4);
        assertEquals(4, alist.size());
    }

    @Test
    public void sizeTestMedium() {
        Assoc<String, Integer> a1 = new Assoc<>("cat", 3),
                a2 = new Assoc<>("dog", 7),
                a3 = new Assoc<>("pig", 9),
                a4 = new Assoc<>("cat", 1),
                a5 = new Assoc<>("rat", 2),
                a6 = new Assoc<>("bat", 4),
                a7 = new Assoc<>("mouse", 8),
                a8 = new Assoc<>("bird", 6),
                a9 = new Assoc<>("lizard", 10),
                a10 = new Assoc<>("squirrel", 8);
        AList<String, Integer> alist = new AList<>();
        assertTrue(alist.isEmpty());
        alist.addFront(a1);
        alist.addFront(a2);
        alist.addFront(a3);
        alist.addFront(a4);
        alist.addFront(a5);
        alist.addFront(a6);
        alist.addFront(a7);
        alist.addFront(a8);
        alist.addFront(a9);
        alist.addFront(a10);
        assertEquals(10, alist.size());
    }

    @Test
    public void sizeTestBig() {
        Assoc<String, Integer> a1 = new Assoc<>("cat", 3),
                a2 = new Assoc<>("dog", 7),
                a3 = new Assoc<>("pig", 9),
                a4 = new Assoc<>("cat", 1),
                a5 = new Assoc<>("rat", 2),
                a6 = new Assoc<>("bat", 4),
                a7 = new Assoc<>("mouse", 8),
                a8 = new Assoc<>("bird", 6),
                a9 = new Assoc<>("lizard", 10),
                a10 = new Assoc<>("squirrel", 8),
                a11 = new Assoc<>("cow", 18),
                a12 = new Assoc<>("goat", 14),
                a13 = new Assoc<>("sheep", 11),
                a14 = new Assoc<>("bull", 8),
                a15 = new Assoc<>("zebra", 13),
                a16 = new Assoc<>("tiger", 5),
                a17 = new Assoc<>("lion", 15),
                a18 = new Assoc<>("giraffe", 20),
                a19 = new Assoc<>("hippo", 17),
                a20 = new Assoc<>("penguin", 12);
        AList<String, Integer> alist = new AList<>();
        assertTrue(alist.isEmpty());
        alist.addFront(a1);
        alist.addFront(a2);
        alist.addFront(a3);
        alist.addFront(a4);
        alist.addFront(a5);
        alist.addFront(a6);
        alist.addFront(a7);
        alist.addFront(a8);
        alist.addFront(a9);
        alist.addFront(a10);
        alist.addFront(a11);
        alist.addFront(a12);
        alist.addFront(a13);
        alist.addFront(a14);
        alist.addFront(a15);
        alist.addFront(a16);
        alist.addFront(a17);
        alist.addFront(a18);
        alist.addFront(a19);
        alist.addFront(a20);
        assertEquals(20, alist.size());
    }

    @Test
    public void getTestSmall() {
        Assoc<String, Integer> a1 = new Assoc<>("cat", 3),
                a2 = new Assoc<>("dog", 7),
                a3 = new Assoc<>("pig", 9),
                a4 = new Assoc<>("cat", 9);
        AList<String, Integer> alist = new AList<>();
        alist.addFront(a1);
        alist.addFront(a2);
        alist.addFront(a3);
        alist.addFront(a4);

        assertEquals(a2, alist.get("dog"));
        assertEquals(a3, alist.get("pig"));
        assertEquals(a4, alist.get("cat"));
    }

    @Test
    public void getTestMedium() {
        Assoc<String, Integer> a1 = new Assoc<>("cat", 3),
                a2 = new Assoc<>("dog", 7),
                a3 = new Assoc<>("pig", 9),
                a4 = new Assoc<>("cat", 1),
                a5 = new Assoc<>("rat", 2),
                a6 = new Assoc<>("bat", 4),
                a7 = new Assoc<>("mouse", 8),
                a8 = new Assoc<>("bird", 6),
                a9 = new Assoc<>("lizard", 10),
                a10 = new Assoc<>("squirrel", 8);
        AList<String, Integer> alist = new AList<>();
        alist.addFront(a1);
        alist.addFront(a2);
        alist.addFront(a3);
        alist.addFront(a4);
        alist.addFront(a5);
        alist.addFront(a6);
        alist.addFront(a7);
        alist.addFront(a8);
        alist.addFront(a9);
        alist.addFront(a10);

        assertEquals(a2, alist.get("dog"));
        assertEquals(a3, alist.get("pig"));
        assertEquals(a4, alist.get("cat"));
        assertEquals(a5, alist.get("rat"));
        assertEquals(a6, alist.get("bat"));
        assertEquals(a7, alist.get("mouse"));
        assertEquals(a8, alist.get("bird"));
        assertEquals(a9, alist.get("lizard"));
        assertEquals(a10, alist.get("squirrel"));
    }

    @Test
    public void getTestBig() {
        Assoc<String, Integer> a1 = new Assoc<>("cat", 3),
                a2 = new Assoc<>("dog", 7),
                a3 = new Assoc<>("pig", 9),
                a4 = new Assoc<>("cat", 1),
                a5 = new Assoc<>("rat", 2),
                a6 = new Assoc<>("bat", 4),
                a7 = new Assoc<>("mouse", 8),
                a8 = new Assoc<>("bird", 6),
                a9 = new Assoc<>("lizard", 10),
                a10 = new Assoc<>("squirrel", 8),
                a11 = new Assoc<>("cow", 18),
                a12 = new Assoc<>("goat", 14),
                a13 = new Assoc<>("sheep", 11),
                a14 = new Assoc<>("bull", 8),
                a15 = new Assoc<>("zebra", 13),
                a16 = new Assoc<>("tiger", 5),
                a17 = new Assoc<>("lion", 15),
                a18 = new Assoc<>("giraffe", 20),
                a19 = new Assoc<>("hippo", 17),
                a20 = new Assoc<>("penguin", 12);
        AList<String, Integer> alist = new AList<>();
        alist.addFront(a1);
        alist.addFront(a2);
        alist.addFront(a3);
        alist.addFront(a4);
        alist.addFront(a5);
        alist.addFront(a6);
        alist.addFront(a7);
        alist.addFront(a8);
        alist.addFront(a9);
        alist.addFront(a10);
        alist.addFront(a11);
        alist.addFront(a12);
        alist.addFront(a13);
        alist.addFront(a14);
        alist.addFront(a15);
        alist.addFront(a16);
        alist.addFront(a17);
        alist.addFront(a18);
        alist.addFront(a19);
        alist.addFront(a20);

        assertEquals(a2, alist.get("dog"));
        assertEquals(a3, alist.get("pig"));
        assertEquals(a4, alist.get("cat"));
        assertEquals(a5, alist.get("rat"));
        assertEquals(a6, alist.get("bat"));
        assertEquals(a7, alist.get("mouse"));
        assertEquals(a8, alist.get("bird"));
        assertEquals(a9, alist.get("lizard"));
        assertEquals(a10, alist.get("squirrel"));
        assertEquals(a11, alist.get("cow"));
        assertEquals(a12, alist.get("goat"));
        assertEquals(a13, alist.get("sheep"));
        assertEquals(a14, alist.get("bull"));
        assertEquals(a15, alist.get("zebra"));
        assertEquals(a16, alist.get("tiger"));
        assertEquals(a17, alist.get("lion"));
        assertEquals(a18, alist.get("giraffe"));
        assertEquals(a19, alist.get("hippo"));
        assertEquals(a20, alist.get("penguin"));
    }

    @Test
    public void testHashMapSize() {
        HashMap<Color, String> hm = new HashMap<>(5);
        assertTrue(hm.isEmpty());
        hm.put(Color.RED, "red");
        hm.put(Color.GREEN, "green");
        hm.put(Color.YELLOW, "yellow");
        hm.put(Color.PINK, "pink");
        hm.put(Color.BLUE, "blue");
        hm.put(Color.CYAN, "cyan");
        hm.put(Color.MAGENTA, "magenta");
        hm.put(Color.BLACK, "black");
        hm.put(Color.WHITE, "white");
        assertEquals(9, hm.size());
        hm.put(Color.RED, "r");
        hm.put(Color.GREEN, "g");
        hm.put(Color.YELLOW, "y");
        hm.put(Color.PINK, "p");
        hm.put(Color.BLUE, "b");
        hm.put(Color.CYAN, "c");
        hm.put(Color.MAGENTA, "m");
        hm.put(Color.BLACK, "b");
        hm.put(Color.WHITE, "w");
        assertEquals(9, hm.size());
    }

    @Test
    public void testHashMapRemove() {
        HashMap<Color, String> hm = new HashMap<>(5);
        hm.put(Color.RED, "red");
        hm.put(Color.GREEN, "green");
        hm.put(Color.YELLOW, "yellow");
        hm.put(Color.PINK, "pink");
        hm.put(Color.BLUE, "blue");
        hm.put(Color.CYAN, "cyan");
        hm.put(Color.MAGENTA, "magenta");
        hm.put(Color.BLACK, "black");
        hm.put(Color.WHITE, "white");
        assertEquals("blue", hm.remove(Color.BLUE));
        assertEquals(8, hm.size());
        assertEquals("magenta", hm.remove(Color.MAGENTA));
        assertEquals(7, hm.size());
        hm.put(Color.MAGENTA, "magenta");
        assertEquals(8, hm.size());
        assertNull(hm.remove(Color.BLUE));
        assertEquals(8, hm.size());
    }

    // The next group of tests focus on ColorTable

    @Test
    public void testBasicProperties() {
        colorTable = new ColorTable(3, 8, 3.0);
        assertEquals(0, colorTable.size());
        assertEquals(3, colorTable.capacity());
        assertEquals(8, colorTable.getBitsPerChannel());
    }

    @Test
    public void testBasicPut() {
        colorTable = new ColorTable(10, 5, 4.0);
        assertEquals(0, colorTable.size());
        assertEquals(10, colorTable.capacity());
        Color[] colors = new Color[30];
        for (int i = 0; i < colors.length; i++)
            colors[i] = new Color(15 + i * 8, 255 - i * 7, 53 + i * 6);
        for (int i = 0; i < colors.length; i++) {
            colorTable.put(colors[i], new Long(i + 100));
            assertEquals(i + 1, colorTable.size());
        }
        int[] expectedChainLengths = new int[]{4, 3, 4, 2, 3, 3, 3, 3, 2, 3};
        for (int i = 0; i < colorTable.table.length; i++)
            assertEquals(expectedChainLengths[i], colorTable.table[i].size());
    }

    @Test
    public void testPuttingZeroes() {
        colorTable = new ColorTable(10, 5, 4.0);
        colorTable.put(Color.RED, 0L);
        assertEquals(0, colorTable.size());
        colorTable.put(Color.GREEN, 0L);
        assertEquals(0, colorTable.size());
        Color[] colors = new Color[30];
        for (int i = 0; i < colors.length; i++)
            colors[i] = new Color(15 + i * 8, 255 - i * 7, 53 + i * 6);
        for (int i = 0; i < colors.length; i++)
            colorTable.put(colors[i], new Long(i + 100));
        assertEquals(colors.length, colorTable.size());
        for (int i = 0; i < colors.length; i++)
            assertEquals(new Long(i + 100), colorTable.put(colors[i], 0L));
        assertEquals(0, colorTable.size());
    }

    @Test
    public void testBasicGet() {
        colorTable = new ColorTable(10, 8, 4.0);
        assertEquals(0, colorTable.size());
        assertEquals(10, colorTable.capacity());
        Color[] colors = new Color[30];
        for (int i = 0; i < colors.length; i++)
            colors[i] = new Color(15 + i * 8, 255 - i * 7, 53 + i * 6);
        for (int i = 0; i < colors.length; i++) {
            colorTable.put(colors[i], new Long(i + 100));
            assertEquals(new Long(i + 100), colorTable.get(colors[i]));
        }
    }

    @Test
    public void testGettingZeroes() {
        colorTable = new ColorTable(3, 6, 0.9);
        assertEquals(new Long(0), colorTable.get(Color.BLUE));
        assertEquals(new Long(0), colorTable.get(Color.PINK));
        assertEquals(new Long(0), colorTable.get(Color.CYAN));
        assertEquals(0, colorTable.size());

        colorTable = new ColorTable(10, 8, 4.0);
        assertEquals(0, colorTable.size());
        assertEquals(10, colorTable.capacity());
        Color[] colors = new Color[30];
        for (int i = 0; i < colors.length; i++)
            colors[i] = new Color(15 + i * 8, 255 - i * 7, 53 + i * 6);
        for (int i = 0; i < colors.length; i++)
            assertEquals(new Long(0), colorTable.get(colors[i]));
        assertEquals(0, colorTable.size());
    }

    @Test
    public void testPutAndGetTogether() {
        colorTable = new ColorTable(3, 6, 0.9);
        assertEquals(0, colorTable.size());
        assertEquals(3, colorTable.capacity());
        colorTable.put(Color.BLACK, 5L);
        assertEquals(1, colorTable.size());
        assertEquals(new Long(5), colorTable.get(Color.BLACK));
        colorTable.put(Color.WHITE, 5L);
        assertEquals(2, colorTable.size());
        colorTable.put(Color.RED, 5L);
        colorTable.put(Color.RED, 23L);
        assertEquals(3, colorTable.size());
        assertEquals(new Long(23), colorTable.get(Color.RED));
    }

    @Test
    public void testPutAndGetWithRehash() {
        colorTable = new ColorTable(3, 2, 0.9);
        assertEquals(0, colorTable.size());
        assertEquals(3, colorTable.capacity());
        assertEquals(true, colorTable.isEmpty());
        colorTable.put(Color.BLACK, 5L);
        assertEquals(1, colorTable.size());
        assertEquals(3, colorTable.capacity());
        assertEquals(false, colorTable.isEmpty());
        colorTable.put(Color.WHITE, 5L);
        colorTable.put(Color.RED, 5L);
        colorTable.put(Color.BLUE, 5L);
        assertEquals(7, colorTable.capacity());
        colorTable.put(Color.RED, 23L);
        assertEquals(7, colorTable.capacity());
    }

    @Test
    public void testIncrementAndGet() {
        colorTable = new ColorTable(3, 4, 0.9);
        colorTable.increment(Color.RED);
        colorTable.increment(Color.RED);
        assertEquals(new Long(2), colorTable.get(Color.RED));
        for (int i = 0; i < 100; i++)
            colorTable.increment(Color.BLUE);
        assertEquals(new Long(100), colorTable.get(Color.BLUE));
        assertEquals(new Long(2), colorTable.get(Color.RED));
    }

    @Test
    public void testIncrementTruncated() {
        colorTable = new ColorTable(3, 1, 0.9);  // just using 3 bits for a color
        Color c1 = new Color(0xf0, 0xe0, 0xd0);
        Color c2 = new Color(0xff, 0xee, 0xdd);
        Color c3 = new Color(0xd1, 0xf2, 0xe3);

        colorTable.put(c1, 5L);
        assertEquals(new Long(5), colorTable.get(c1));
        colorTable.increment(c2);
        colorTable.increment(c3);
        assertEquals(new Long(7), colorTable.get(c1));
    }

    @Test
    public void testIncrementAndResizing() {
        colorTable = new ColorTable(3, 6, 0.8);
        colorTable.increment(Color.BLACK);
        assertEquals(new Long(1), colorTable.get(Color.BLACK));
        colorTable.increment(Color.WHITE);
        assertEquals(new Long(1), colorTable.get(Color.WHITE));
        colorTable.increment(Color.BLACK);
        assertEquals(new Long(2), colorTable.get(Color.BLACK));
        colorTable.increment(Color.BLACK);
        assertEquals(new Long(3), colorTable.get(Color.BLACK));
        colorTable.increment(Color.WHITE);
        assertEquals(new Long(2), colorTable.get(Color.WHITE));
        colorTable.increment(Color.BLACK);
        assertEquals(new Long(4), colorTable.get(Color.BLACK));

        // System.out.println(colorTable);

        // Check status prior to rehashing.
        assertEquals(2, colorTable.size());
        assertEquals(3, colorTable.capacity());
        // This increment will trigger a rehash event.
        colorTable.increment(Color.RED);
        // Check status after rehashing.
        assertEquals(3, colorTable.size());
        System.out.println(colorTable);
        assertEquals(7, colorTable.capacity());

        // System.out.println(colorTable);

        // Check the final frequency counts.
        assertEquals(new Long(1), colorTable.get(Color.RED));
        assertEquals(new Long(4), colorTable.get(Color.BLACK));
        assertEquals(new Long(2), colorTable.get(Color.WHITE));
    }

    @Test
    public void testLowLoadFactor() {
        colorTable = new ColorTable(3, 4, 0.1);
        assertEquals(0, colorTable.size());
        assertEquals(3, colorTable.capacity());
        assertEquals(true, colorTable.getLoadFactor() < 0.1);
        colorTable.put(Color.BLACK, 5L);
        assertEquals(1, colorTable.size());
        assertEquals(19, colorTable.capacity());
        assertEquals(true, colorTable.getLoadFactor() < 0.1);
        assertEquals(new Long(5), colorTable.get(Color.BLACK));
        colorTable.put(Color.WHITE, 5L);
        assertEquals(2, colorTable.size());
        assertEquals(43, colorTable.capacity());
        assertEquals(true, colorTable.getLoadFactor() < 0.1);
        for (int c = 0xFF; c >= 0xF0; c--)
            colorTable.put(new Color(c, c, c), new Long(c));
        assertEquals(new Long(0xF0), colorTable.get(Color.WHITE));
        assertEquals(2, colorTable.size());
        for (int c = 0x0F; c <= 0xFF; c += 0x10)
            colorTable.put(new Color(c, c, c), new Long(c));
        assertEquals(211, colorTable.capacity());
        assertEquals(true, colorTable.getLoadFactor() < 0.1);
    }

    @Test
    public void testIterator() {
        colorTable = new ColorTable(13, 2, 0.49);
        for (int i = 0; i < 20 * 17; i += 17)
            colorTable.put(new Color(i * i), new Long(i));
        assertEquals(9, colorTable.size());
        Long[] expected = {272L, 119L, 102L, 0L, 0L, 289L, 0L,
                306L, 0L, 0L, 323L, 221L, 255L, 238L};
        Iterator<Long> it = colorTable.iterator();
        int k = 0;
        while (it.hasNext() && k < expected.length) {
            assertEquals(expected[k], it.next());
            k++;
        }

        colorTable = new ColorTable(13, 2, 0.49);
        for (int i = 0; i < 20 * 17; i += 17)
            colorTable.put(new Color(i * i), new Long(i));
        assertEquals(9, colorTable.size());
        it = colorTable.iterator();
        k = 0;
        while (it.hasNext() && k < expected.length) {
            assertEquals(expected[k], it.next());
            k++;
        }
    }

    @Test
    public void testCosineSimilarity() {
        ColorTable ct1 = Util.vectorize(Painting.BLUE_DANCERS.get(), 1);
        ColorTable ct2 = Util.vectorize(Painting.STARRY_NIGHT.get(), 1);
        Long[] expectedCounts;
        expectedCounts = new Long[]{
                185844L, 22927L, 4639L, 42667L, 1202L, 4L, 1465L, 25652L,
        };
        for (int i = 0; i < expectedCounts.length; i++)
            assertEquals(expectedCounts[i], ct1.table[i].get(0).value);
        expectedCounts = new Long[]{
                93892L, 53325L, 2416L, 19151L, 356L, 64L, 8108L, 24688L
        };
        for (int i = 0; i < expectedCounts.length; i++)
            assertEquals(expectedCounts[i], ct2.table[i].get(0).value);

        // The following three tests assume that you've defined the two
        // suggested helper functions in Util. Uncomment once you've done that.
    /*
    assertEquals(193822.72576764572, Util.magnitude(ct1), .01);
    assertEquals(112726.34557192032, Util.magnitude(ct2), .01);
    assertEquals(2.0145773628E10, Util.dotProduct(ct1,  ct2), .01);
    assertEquals(0.9220486254283441, Util.cosineSimilarity(ct1, ct2), .01);
    */
    }
}