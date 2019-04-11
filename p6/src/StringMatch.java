
/**
 * TODO #3
 * <p>
 * There is much for you to do here.
 * <p>
 * When you are done here, go to MatchBot.
 */

public class StringMatch {

    /**
     * TODO
     * <p>
     * Returns the result of running the naive algorithm to match
     * pattern in text.  A result is an association of two integers: the
     * position in the text of the match (possibly -1) and the exact
     * number of key comparisons performed to find the match.
     */

    public static Assoc<Integer, Integer> matchNaive(String pattern, String text) {
        int m = pattern.length();
        int n = text.length();
        int i = 0, j = 0;
        int comp = 0;
        while (i < m && j <= n - m + i) {
            if (pattern.charAt(i) == text.charAt(j)) {
                i++;
                j++;
                comp++;
            } else {
                j = j - i + 1;
                i = 0;
                comp++;
            }
        }
        if (i == m)
            return new Assoc<>(j - m, comp);
        return new Assoc<>(-1, comp);
    }

    /**
     * TODO
     * <p>
     * Populates flink with the failure links for the KMP machine
     * associated with the given pattern, and returns the cost in terms
     * of the number of character comparisons.
     */

    public static int buildKMP(String pattern, int[] flink) {
        flink[0] = -1;
        if (pattern.length() < 1) {
            return 0;
        }
        flink[1] = 0;
        int i = 2;
        int j;
        int comp = 0;
        while (i <= pattern.length()) {
            j = flink[i - 1];
            while (j != -1) {
                comp++;
                if (pattern.charAt(j) == pattern.charAt(i - 1))
                    break;
                j = flink[j];
            }
            flink[i] = j + 1;
            i++;
        }
        return comp;
    }

    /**
     * TODO
     * <p>
     * Returns the result of running the KMP machine specified by flink
     * (built for the given pattern) on the text.
     */

    public static Assoc<Integer, Integer> runKMP(String pattern, String text, int[] flink) {
        int j = -1;
        int state = -1;
        //need - 1?
        int m = pattern.length();
        int n = text.length();
        int comp = 0;
        char delta = '\0';

        //test empty cases
        if (m == 0)
            return new Assoc<>(0, comp);
        if (n == 0)
            return new Assoc<>(-1, comp);
        while (true) {
            if (state == -1) {
                state = state + 1;
                j = j + 1;
                if (j == n)
                    return new Assoc<>(-1, comp);
                delta = text.charAt(j);
            }
            comp++;
            if (delta == pattern.charAt(state)) {
                state = state + 1;
                //location or comp incorrect?
                if (state == m) //in-class algorithm: if state is a member of F then
                    return new Assoc<>(j - m + 1, comp);
                j = j + 1;
                if (j == n)
                    return new Assoc<>(-1, comp);
                delta = text.charAt(j);
            } else {
                state = flink[state];
            }
        }
    }

    /**
     * Returns the result of running the KMP algorithm to match pattern in text.
     * The number of comparisons includes the cost of building the machine from
     * the pattern.
     */

    public static Assoc<Integer, Integer> matchKMP(String pattern, String text) {
        int m = pattern.length();
        int[] flink = new int[m + 1];
        int comps = buildKMP(pattern, flink);
        Assoc<Integer, Integer> ans = runKMP(pattern, text, flink);
        return new Assoc<>(ans.key, comps + ans.value);
    }

    /**
     * TODO
     * <p>
     * Populates delta1 with the shift values associated with each character in
     * the alphabet. Assume delta1 is large enough to hold any ASCII value.
     */

    public static void buildDelta1(String pattern, int[] delta1) {
        int m = pattern.length();
        for (int i = 0; i < delta1.length; i++)
            delta1[i] = m;
        for (int j = 0; j < m; j++)
            delta1[pattern.charAt(j)] = m - j - 1;
    }

    /**
     * TODO
     * <p>
     * Returns the result of running the simplified Boyer-Moore algorithm using
     * the delta1 table from the pre-processing phase.
     */

    //runBoyerMoore() makes an improper comparison count. expected:<35536> but was:<37414>
    public static Assoc<Integer, Integer> runBoyerMoore(String pattern, String text, int[] delta1) {
        int j = pattern.length() - 1;
        int i = pattern.length() - 1;
        int k = 0;
        int comp = 0;
        int m = pattern.length();
        int n = text.length();

        //test empty cases
        if (m == 0)
            return new Assoc<>(0, comp);
        if (n == 0)
            return new Assoc<>(-1, comp);

        while (i < n) {
            comp++;
            if (text.charAt(i - k) == pattern.charAt(j)) {
                k++;
                j--;
                if (k == pattern.length())
                    return new Assoc<>(i - m + 1, comp);
            } else if (text.charAt(i - k) < Constants.SIGMA_SIZE) {
                i = i + Math.max(1, delta1[text.charAt(i - k)] - k);
                j = pattern.length() - 1;
                k = 0;
            } else {
                i = m;
                j = pattern.length() - 1;
                k = 0;
            }
        }
        return new Assoc<>(-1, comp);
    }

    /**
     * Returns the result of running the simplified Boyer-Moore
     * algorithm to match pattern in text.
     */

    public static Assoc<Integer, Integer> matchBoyerMoore(String pattern,
                                                          String text) {
        int[] delta1 = new int[Constants.SIGMA_SIZE];
        buildDelta1(pattern, delta1);
        return runBoyerMoore(pattern, text, delta1);
    }

}
