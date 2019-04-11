import static org.junit.Assert.*;

import java.util.Random;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;

/**
 * TODO: Write your own tests!!!
 */

public class Testing {
    private static Random random = new Random();
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";

    @Test
    public void testRank() {
        PopularityBot billyBot = new PopularityBot();
        for (String lyric : new String[]{
                "We didn't start the fire",
                "It was always burning",
                "Since the world's been turning",
                "We didn't start the fire",
                "No we didn't light it",
                "But we tried to fight it",
        })
            billyBot.tweets.add(lyric);
        billyBot.split();
        billyBot.rank();
        assertEquals(3, billyBot.vocab.size());
        assertEquals("didnt", billyBot.vocab.get(0).key);
        assertEquals("fire", billyBot.vocab.get(1).key);
        assertEquals("start", billyBot.vocab.get(2).key);
        assertEquals(Integer.valueOf(3), billyBot.vocab.get(0).value);
        assertEquals(Integer.valueOf(2), billyBot.vocab.get(1).value);
        assertEquals(Integer.valueOf(2), billyBot.vocab.get(2).value);
    }

    @Test
    public void testNaive() {
        String[] pats = new String[]{
                "AAAA",
                "BAAA",
                "AAAB",
                "AAAC",
                "ABAB",
        };
        String text = "AAAAAAAAABAAAAAAAAAB";
        assertEquals(20, text.length());
        Integer[][] results = new Integer[][]{
                {0, 4},
                {9, 13},
                {6, 28},
                {-1, 62},
                {-1, 35},
        };
        int i = 0;
        for (String pat : pats) {
            Assoc<Integer, Integer> res = StringMatch.matchNaive(pat, text);
            assertEquals(results[i][0], res.key);
            assertEquals(results[i][1], res.value);
            i++;
        }
    }

    @Test
    public void smallGods() { // Shout out to Terry Pratchett.
        String[] pats = new String[]{
                "",
                "A",
                "AB",
                "AA",
                "AAAA",
                "BAAA",
                "AAAB",
                "AAAC",
                "ABAB",
                "ABCD",
                "ABBA",
                "AABC",
                "ABAAB",
                "AABAACAABABA",
                "ABRACADABRA",
        };
        int[][] flinks = new int[][]{
                {-1},
                {-1, 0},
                {-1, 0, 0},
                {-1, 0, 1},
                {-1, 0, 1, 2, 3},
                {-1, 0, 0, 0, 0},
                {-1, 0, 1, 2, 0},
                {-1, 0, 1, 2, 0},
                {-1, 0, 0, 1, 2},
                {-1, 0, 0, 0, 0},
                {-1, 0, 0, 0, 1},
                {-1, 0, 1, 0, 0},
                {-1, 0, 0, 1, 1, 2},
                {-1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1},
                {-1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 3, 4},
        };
        int[] comps = new int[]{0, 0, 1, 1, 3, 3, 5, 5, 3, 3, 3, 4, 5, 16, 12};
        int i = 0;
        for (String pat : pats) {
            int[] flink = new int[pat.length() + 1];
            assertEquals(comps[i], StringMatch.buildKMP(pat, flink));
            assertArrayEquals(flinks[i], flink);
            i++;
        }
    }

    @Test
    public void lec13bKMP() {
        String[] pats = new String[]{
                "AABC",
                "ABCDE",
                "AABAACAABABA",
                "ABRACADABRA",
        };
        int[][] flinks = new int[][]{
                {-1, 0, 1, 0, 0},
                {-1, 0, 0, 0, 0, 0},
                {-1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1},
                {-1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 3, 4},
        };
        String text = "AAAAAABRACADABAAAAAAAAAAAAAAAAAAAAAAA" +
                "BCAAAAAAAAAAABAABAAAAAAAAAAAAAAA";
        Integer[][] results = new Integer[][]{
                {35, 68},
                {-1, 128},
                {-1, 123},
                {-1, 126},
        };
        int i = 0;
        for (String pat : pats) {
            Assoc<Integer, Integer> res = StringMatch.runKMP(pat, text, flinks[i]);
            //position is good
            assertEquals(results[i][0], res.key);
            //comparisons need work
            assertEquals(results[i][1], res.value);
            i++;
        }
    }

    @Test
    public void testFlinks() {
        String pattern = "AABAACAABABA";
        int m = pattern.length();
        int[] flink = new int[m + 1];
        StringMatch.buildKMP(pattern, flink);
        System.out.println(java.util.Arrays.toString(flink));
        assertArrayEquals(flink, new int[]{
                -1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1
        });
        assertTrue(verifyMachine(pattern, flink));
    }

    /**
     * The following tests may be useful when writing your REPORT.
     * Remove the // in front of @Test to run.
     */

    @Test
    public void testEmpty() {
        System.out.println("testEmpty");
        match("", "");
        match("", "ab");
        System.out.println();
    }

    @Test
    public void testOneChar() {
        System.out.println("testOneChar");
        match("a", "a");
        match("a", "b");
        System.out.println();
    }

    @Test
    public void testRepeat() {
        System.out.println("testRepeat");
        match("aaa", "aaaaa");
        match("aaa", "abaaba");
        match("abab", "abacababc");
        match("abab", "babacaba");
        System.out.println();
    }

    @Test
    public void testPartialRepeat() {
        System.out.println("testPartialRepeat");
        match("aaacaaaaac", "aaacacaacaaacaaaacaaaaac");
        match("ababcababdabababcababdaba", "ababcababdabababcababdaba");
        System.out.println();
    }

    @Test
    public void testRandomly() {
        System.out.println("testRandomly");
        for (int i = 0; i < 100; i++) {
            String pattern = makeRandomPattern();
            for (int j = 0; j < 100; j++) {
                String text = makeRandomText(pattern);
                match(pattern, text);
            }
        }
        System.out.println();
    }

    //My tests

    @Test
    public void testDelta1() {
        String pattern = "AADACA";
        int[] delta1 = new int[Constants.SIGMA_SIZE];
        StringMatch.buildDelta1(pattern, delta1);
        System.out.println(java.util.Arrays.toString(delta1));
        int[] test = new int[]{0, 6, 1, 3};
        assertEquals(delta1[65], test[0]);
        assertEquals(delta1[66], test[1]);
        assertEquals(delta1[67], test[2]);
        assertEquals(delta1[68], test[3]);
    }

    @Test
    public void testBM() {
        String text = "ABCDABCDABCDABCD";
        String pattern = "ABCD";
        Assoc<Integer, Integer> ans = StringMatch.matchBoyerMoore(pattern, text);
        assertTrue(0 == ans.key);
        assertTrue(4 == ans.value);
    }

    @Test
    public void testBM2() {
        String text = "BACDABBCDABADCBACDBAABDCAABDBACDBACBDABCD";
        String pattern = "AABDCAA";
        Assoc<Integer, Integer> ans = StringMatch.matchBoyerMoore(pattern, text);
        assertTrue(19 == ans.key);
        assertTrue( 12 == ans.value);
    }

    @Test
    public void testBM3() {
        String text = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAB";
        String pattern = "AAAB";
        Assoc<Integer, Integer> ans = StringMatch.matchBoyerMoore(pattern, text);
        assertTrue(26 == ans.key);
        assertTrue( 30 == ans.value);
    }

    //classmate tests

    /**
     * This is a test for Delta1.
     * @author Jacob Mitchell/mitchjak
     */

    @Test
    public void testDelta1pt2() {
        String pattern = "AADACA";
        int m = pattern.length();
        int[] delta1 = new int[Constants.SIGMA_SIZE];
        StringMatch.buildDelta1(pattern, delta1);
        assertEquals(m, delta1['B']);
        assertEquals(0, delta1['A']);
        assertEquals(1, delta1['C']);
        assertEquals(3, delta1['D']);

        pattern = "Testing";
        m = pattern.length();
        delta1 = new int[Constants.SIGMA_SIZE];
        StringMatch.buildDelta1(pattern, delta1);
        assertEquals(m, delta1['z']);
        assertEquals(m, delta1['P']);
        int expected = 6;
        for(int i = 0; i < pattern.length(); i++) {
            assertEquals(expected, delta1[pattern.charAt(i)]);
            expected--;
        }

        pattern = "AAAAAAAAAAAAAAAAAAAA";
        m = pattern.length();
        delta1 = new int[Constants.SIGMA_SIZE];
        StringMatch.buildDelta1(pattern, delta1);
        assertEquals(0, delta1['A']);
        String notInPattern = "BCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < notInPattern.length(); i++)
            assertEquals(m, delta1[notInPattern.charAt(i)]);

        pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        delta1 = new int[Constants.SIGMA_SIZE];
        StringMatch.buildDelta1(pattern, delta1);
        expected = 25;
        for(int i = 0; i < pattern.length(); i++) {
            assertEquals(expected, delta1[pattern.charAt(i)]);
            expected--;
        }

        pattern = "";
        m = pattern.length();
        delta1 = new int[Constants.SIGMA_SIZE];
        StringMatch.buildDelta1(pattern, delta1);
        notInPattern = "abcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyz1234567890";
        for(int i = 0; i < notInPattern.length(); i++)
            assertEquals(m, delta1[notInPattern.charAt(i)]);
    }

    /**
     * This is a test for Boyer-Moore.
     * @author Xander Tatum/abtatum
     */

    @Test
    public void testBoyer(){
        String text = "AAACABACCABBAACAADACAACA";
        System.out.println(text.length());
        String pattern = "AADACA";
        Assoc<Integer, Integer> ans = StringMatch.matchBoyerMoore(pattern, text);
        System.out.println(ans.key);
        assertTrue(15 == ans.key);
    }

    @Test
    public void testSmall(){
        String text = "a";
        String pattern = "a";
        Assoc<Integer, Integer> ans = StringMatch.matchBoyerMoore(pattern, text);
        System.out.println(ans.key);
        System.out.println(ans.value);
        assertTrue(0 == ans.key);
    }

    @Test
    public void testBig() {
        String text = "aaaaaaaaaaaaacaaaaaaaaaaaaaaaaaadaaaaaaaaaaaaaaeaaaaaaaaaraaaaaaaaaaaataaaaaaaaaaayaaaaaaaaaaaaasaaaaaaaaahadaa";
        String pattern = "baaa";
        Assoc<Integer, Integer> ans = StringMatch.matchBoyerMoore(pattern, text);
        System.out.println(ans.key);
        System.out.println(ans.value);
        assertTrue(-1 == ans.key);
    }

    /**
     * This is a test for Rank().
     * @author Xander Tatum/abtatum
     */

    @Test
    public void newRankTest() {
        PopularityBot xanderBot = new PopularityBot();
        for (String word : new String[] {
                "Apples Apples Apples Apples Apples Apples Apples",
                "AARDVARK AARDVARK AARDVARK AARDVARK AARDVARK AARDVARK AARDVARK",
                "oranges oranges oranges oranges oranges oranges oranges oranges oranges oranges",
                "Super Super Super",})
            xanderBot.tweets.add(word);
        xanderBot.split();
        xanderBot.rank();
        assertEquals(4, xanderBot.vocab.size());
        System.out.println(xanderBot.vocab);
        assertEquals("oranges", xanderBot.vocab.get(0).key);
        assertEquals("aardvark", xanderBot.vocab.get(1).key);
        assertEquals("apples", xanderBot.vocab.get(2).key);
    }
    /* Helper functions */

    private static String makeRandomPattern() {
        StringBuilder sb = new StringBuilder();
        int steps = random.nextInt(10) + 1;
        for (int i = 0; i < steps; i++) {
            if (sb.length() == 0 || random.nextBoolean()) {  // Add literal
                int len = random.nextInt(5) + 1;
                for (int j = 0; j < len; j++)
                    sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
            } else {  // Repeat prefix
                int len = random.nextInt(sb.length()) + 1;
                int reps = random.nextInt(3) + 1;
                if (sb.length() + len * reps > 1000)
                    break;
                for (int j = 0; j < reps; j++)
                    sb.append(sb.substring(0, len));
            }
        }
        return sb.toString();
    }

    private static String makeRandomText(String pattern) {
        StringBuilder sb = new StringBuilder();
        int steps = random.nextInt(100);
        for (int i = 0; i < steps && sb.length() < 10000; i++) {
            if (random.nextDouble() < 0.7) {  // Add prefix of pattern
                int len = random.nextInt(pattern.length()) + 1;
                sb.append(pattern.substring(0, len));
            } else {  // Add literal
                int len = random.nextInt(30) + 1;
                for (int j = 0; j < len; j++)
                    sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
            }
        }
        return sb.toString();
    }

    private static void match(String pattern, String text) {
        // run all three algorithms and test for correctness
        Assoc<Integer, Integer> ansNaive = StringMatch.matchNaive(pattern, text);
        Integer expected = text.indexOf(pattern);
        assertEquals(expected, ansNaive.key);
        Assoc<Integer, Integer> ansKMP = StringMatch.matchKMP(pattern, text);
        assertEquals(expected, ansKMP.key);
        Assoc<Integer, Integer> ansBoyerMoore =
                StringMatch.matchBoyerMoore(pattern, text);
        assertEquals(expected, ansBoyerMoore.key);
        System.out.println(String.format("%5d %5d %5d : %s",
                ansNaive.value, ansKMP.value, ansBoyerMoore.value,
                (ansNaive.value < ansKMP.value &&
                        ansNaive.value < ansBoyerMoore.value) ?
                        "Naive" :
                        (ansKMP.value < ansNaive.value &&
                                ansKMP.value < ansBoyerMoore.value) ?
                                "KMP" : "Boyer-Moore"));
    }

    //--------- For Debugging ---------------
    public static boolean verifyMachine(String pattern, int[] flink) {
        int m = pattern.length();
        if (flink.length != pattern.length() + 1)
            return false;  // bad length
        if (flink[0] != -1)
            return false;
        for (int i = 2; i < m; i++) {
            if (flink[i] < 0 || flink[i] >= i)
                return false;  // link out of range
            // Search for the nearest state whose label is a suffix of i's label
            String iLabel = pattern.substring(0, i);
            int j = i - 1;
            while (j >= 0 && !iLabel.endsWith(pattern.substring(0, j)))
                j--;
            if (flink[i] != j)
                return false;  // fails to the wrong state
        }
        return true;
    }
}
