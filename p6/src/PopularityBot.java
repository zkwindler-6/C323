import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.function.BiPredicate;

/**
 * TODO #2
 * <p>
 * Study the implementation of this class (and its super class). Complete
 * the TODOs in the rank() method so you pass the first test in Testing.
 * <p>
 * Then use this class to fill out the following table with the most popular
 * word and the second most popular word in the last RECENT tweets of the
 * indicated user. Include the timeStamp to show when you collected the data.
 * <p>
 * user            |  most popular  | second most popular |   timeStamp
 * ----------------+----------------+---------------------+------------------
 * mike_pence      |     great      |    president        |  2018.04.12.20.35.07
 * realDonaldTrump |     great      |       very          |  2018.04.12.20.30.31
 * taylorswift13   |  #reputation   |         ❤          |  2018.04.12.20.35.59
 * billnye         |    science     |      us             |  2018.04.12.20.37.11
 * nasa            |     live       |      #dragon        |  2018.04.12.20.37.52
 * Oprah           |   #fixmylife   |     #queensugar     |  2018.04.12.20.38.47
 * <p>
 * When you are done here, go to StringMatch.
 */

public class PopularityBot extends TwitterBot {
    public static final int RECENT = 200;

    String timeStamp;
    AList<String, Integer> vocab = new AList<>();

    // Read list of boring vocab from a file. Just need to do this once.
    private static List<String> boringWords = new DoublyLinkedList<>();

    static {
        try {
            Scanner in = new Scanner(new File(Constants.BORING_WORDS));
            while (in.hasNext())
                boringWords.add(in.next());
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open " + Constants.BORING_WORDS);
        }
    }

    /**
     * Default constructor.
     */

    public PopularityBot() {
    }

    /**
     * Fetches the requested number of tweets from the given user.
     */

    public PopularityBot(String user, int numTweets) {
        super(user, numTweets);
        timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        split();
        rank();
    }

    /**
     * TODO
     * <p>
     * Sorts the vocab list by word, combines all duplicate words into a single
     * association whose value is the frequency count, and filters out all
     * singletons.
     */

    public void rank() {
        /**
         * TODO: Replace the null in the argument to sort so that the vocab list
         * is sorted in dictionary order on the words.
         */
        vocab.sort((x, y) -> x.key.compareTo(y.key) < 0);

        compressRuns();

        /**
         * TODO: Replace the null in the argument to sort so that the vocab list
         * is sorted in decreasing order by frequency.
         */

        //rank() does not properly sort by frequency after sorting by dictionary order.
        //expected:<[little]> but was:<[star]>
        vocab.sort((x, y) -> x.value > y.value || x.value == y.value && x.key.compareTo(y.key) < 0);
    }

    /**
     * Takes the text of each status and splits it into individual lowercase
     * vocab, trims the punctuation, and ignores any boring vocab.
     */

    public void split() {
        for (String tweet : tweets) {
            for (String word : scrub(tweet).toLowerCase().split(" "))
                if (word.length() > 1 && word.charAt(0) != '@' &&
                        !boringWords.contains(word))
                    vocab.add(new Assoc<>(word, 1));
        }
    }

    /**
     * Returns the result of removing common punctuation from the given word.
     * Note that we do not remove # or @ from the word.
     */

    private String scrub(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++)
            if (Constants.PUNCTUATION.indexOf(word.charAt(i)) == -1)
                sb.append(word.charAt(i));
        return sb.toString();
    }

    /**
     * Compresses all runs of the same word in vocab to the first association
     * in the run and updates the value to the length of the run. Runs of
     * length 1 are discarded.
     */

    private void compressRuns() {
        Iterator<Assoc<String, Integer>> it = vocab.iterator();
        Assoc<String, Integer> curr, prev;
        if (it.hasNext()) {
            prev = it.next();
            while (it.hasNext()) {
                curr = it.next();
                if (prev.key.equals(curr.key)) {
                    prev.value++;
                    it.remove();
                } else
                    prev = curr;
            }
        }
        // Discard runs of length 1.
        it = vocab.iterator();
        while (it.hasNext()) {
            curr = it.next();
            if (curr.value == 1)
                it.remove();
        }
    }

    /**
     * Returns the k-th most popular word in the user's tweets, where k is
     * 1, 2, 3, ... In the case that k is out of range for the vocab,
     * returns null.
     */

    public String kthMostPopularWord(int k) {
        if (k < 1 || k > vocab.size())
            return null;
        return vocab.get(k - 1).key;
    }

    /**
     * Returns a string representing the timestamp corresponding to when the
     * tweet data was collected.
     */

    public String getTimeStamp() {
        return timeStamp;
    }

}  
