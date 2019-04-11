/**
 * TODO #4
 * <p>
 * Design and run a variety of experiments in MatchBot.main() and then
 * summarize your results below in this comment. Which of the three algorithms
 * works best on the Twitter dataset? What about other kinds of data?
 * Hypothesize about the reasons why one algorithm is better suited to a
 * particular type of data. Support your conclusions with evidence.
 * <p>
 * ==========================================================================
 * REPORT:
 *
 * For twitter datasets, naive has been making less comparisons than KMP by about 9,000.
 * I was not expecting this because naive only moves on character at a time. But whats even
 * better than the Naive algorithm is the Boyer-Moore. It has significantly less comparisons
 * and that's because the Boyer-Moore works better on English alphabets.
 *
 * For other kinds of data, Boyer-Moore seems to be the best. When the data is empty or just one
 * character, then they are the same (which is no surprise). Even when we test for partial repeats
 * Boyer-Moore comes out on top. Naive and KMP were usually the worst and BM the best. (See below)
 * testPartialRepeat
 *   50    42    33 : Boyer-Moore
 *   25    53    25 : Boyer-Moore
 *
 * Now on random datasets, Boyer-Moore had the best turnout all around. It had the least amount
 * of comparisons usually besides a few cases where it was KMP or naive. It seems to be that between KMP and BM,
 * KMP is fast when it comes to an alphabet that is small and slow when its large. BM is fast when the alphabet
 * is large and slow when its small. BM is also fast when the pattern length is large.
 */

public class MatchBot extends TwitterBot {

    /**
     * Constructs a MatchBot to operate on the last numTweets of the given user.
     */

    public MatchBot(String user, int numTweets) {
        super(user, numTweets);
    }

    /**
     * TODO
     * <p>
     * Employs the KMP string matching algorithm to add all tweets containing
     * the given pattern to the provided list. Returns the total number of
     * character comparisons performed.
     */

    //searchTweetsKMP does not return the correct comparison count. expected:<3918> but was:<3544>
    public int searchTweetsKMP(String pattern, List<String> ans) {
        int comp = 0;
        String patternT = pattern.toLowerCase();
        int[] flink = new int[pattern.length() + 1];
        int comp2 = StringMatch.buildKMP(patternT, flink);
        for (String str: tweets) {
            Assoc<Integer, Integer> temp = StringMatch.runKMP(patternT, str.toLowerCase(), flink);
            comp += temp.value;
            if (temp.key != -1)
                ans.add(str);
        }
        return comp + comp2;
    }

    /**
     * TODO
     * <p>
     * Employs the naive string matching algorithm to add all tweets containing
     * the given pattern to the provided list. Returns the total number of
     * character comparisons performed.
     */

    //searchTweetsKMP does not return the correct comparison count. expected:<3774> but was:<3487>
    public int searchTweetsNaive(String pattern, List<String> ans) {
        int comp = 0;
        String patternT = pattern.toLowerCase();
        for (String x: tweets) {
            Assoc<Integer, Integer> temp = StringMatch.matchNaive(patternT, x.toLowerCase());
            comp += temp.value;
            if (temp.key != -1)
                ans.add(x);
        }
        return comp;
    }

    //searchTweetsKMP does not return the correct comparison count. expected:<1117> but was:<1040>
    public int searchTweetsBoyerMoore(String pattern, List<String> ans) {
        int comp = 0;
        String patternT = pattern.toLowerCase();
        for (String x: tweets) {
            Assoc<Integer, Integer> temp = StringMatch.matchBoyerMoore(patternT, x.toLowerCase());
            comp += temp.value;
            if (temp.key != -1)
                ans.add(x);
        }
        return comp;
    }

    /**
     * TODO: Write your experiments here!
     */

    public static void main(String... args) {
        String handle = "realDonaldTrump", pattern = "mexico";
        MatchBot bot = new MatchBot(handle, 2000);

        // search all tweets for the pattern
        List<String> ansNaive = new DoublyLinkedList<>();
        int compsNaive = bot.searchTweetsNaive(pattern, ansNaive);

        List<String> ansKMP = new DoublyLinkedList<>();
        int compsKMP = bot.searchTweetsKMP(pattern, ansKMP);

        List<String> ansBM = new DoublyLinkedList<>();
        int compsBM = bot.searchTweetsBoyerMoore(pattern, ansBM);

        System.out.println("naive comps = " + compsNaive +
                ", KMP comps = " + compsKMP + ", BM comps = " + compsBM);

        for (int i = 0; i < ansKMP.size(); i++) {
            String tweet = ansKMP.get(i);
            assert tweet.equals(ansNaive.get(i));
            System.out.println(i++ + ". " + tweet);
            System.out.println(pattern + " appears at index " +
                    tweet.toLowerCase().indexOf(pattern.toLowerCase()));
        }

        /**
         * TODO: Now, do something similar to the above, but for the Boyer-Moore
         * matching algorithm. Use your results to write your REPORT, as
         * described in the comment at the top of this file.
         */

    }
}
