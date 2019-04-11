import java.util.Random;

/**
 * TODO
 * <p>
 * There is much for you to do here.
 * <p>
 * Implement the bestResult(), fillCache(), getResult(), and
 * traceback() methods, in that order. This is the biggest part of
 * this project.
 *
 * @author <Zachary Windler>
 */

public class SequenceAligner {
    private static Random gen = new Random();

    private String s, t;
    private int n, m;
    private String alignedS, alignedT;
    private Result[][] cache;
    private Judge judge;

    /**
     * Generates a pair of random DNA strands, where s is of length n and
     * t has some length between n/2 and 3n/2, and aligns them using the
     * default judge.
     */

    public SequenceAligner(int n) {
        this(randomDNA(n),
                randomDNA(n - gen.nextInt(n / 2) *
                        (gen.nextInt(2) * 2 - 1)));
    }

    /**
     * Aligns the given strands using the default judge.
     */

    public SequenceAligner(String s, String t) {
        this(s, t, new Judge());
    }

    /**
     * Aligns the given strands using the specified judge.
     */

    public SequenceAligner(String s, String t, Judge judge) {
        // Pads s and t with blanks on left to synchronize indices on the cache
        s = s.trim().toUpperCase();
        t = t.trim().toUpperCase();
        this.s = " " + s;
        this.t = " " + t;
        this.judge = judge;
        n = s.length();
        m = t.length();
        cache = new Result[n + 1][m + 1];
        fillCache();
        traceback();
    }

    /**
     * Returns the s strand. Note: the strand is padded on left with one blank.
     */

    public String getS() {
        return s;
    }

    /**
     * Returns the t strand. Note: the strand is padded on left with one blank.
     */

    public String getT() {
        return t;
    }

    /**
     * Returns the judge associated with this pair.
     */

    public Judge getJudge() {
        return judge;
    }

    /**
     * Returns the aligned version of the s strand. Note: the aligned strand
     * is not padded with a blank.
     */

    public String getAlignedS() {
        return alignedS;
    }

    /**
     * Returns the aligned version of the t strand. Note: the aligned strand
     * is not padded with a blank.
     */

    public String getAlignedT() {
        return alignedT;
    }

    /**
     * TODO
     * <p>
     * Returns the Result corresponding to the biggest payoff among the scores
     * for the three different directions.
     * <p>
     * Tiebreaking Rule: So that your code will identify the same alignment
     * as is expected in Testing, we establish the following preferred order
     * of operations: M (diag), I (left), D (up). This only applies when you
     * are picking the operation with the biggest payoff and two or more
     * operations have the same max score.
     */

    public static Result bestResult(int diag, int left, int up) {

        Result res = new Result(diag, Direction.DIAGONAL);
        if (diag >= left && diag >= up)
            return res = new Result(diag, Direction.DIAGONAL);
        else if (left >= diag && left >= up)
            return res = new Result(left, Direction.LEFT);
        return res = new Result(up, Direction.UP);
    }

    /**
     * TODO: Solve the alignment problem using bottom-up dynamic programming
     * algorithm described in lecture. When you're done, cache[i][j] will hold
     * the result of solving the alignment problem for the first i characters
     * in s and the first j characters in t.
     * <p>
     * Your algorithm must run in O(n * m) time, where n is the length of s
     * and m is the length of t.
     */

    private void fillCache() {

        cache[0][0] = new Result(0, Direction.NONE);
        cache[0][0].markPath();

        //i is row
        for (int i = 1; i <= n; i++) {
            cache[i][0] = new Result(i * judge.getGapCost(), Direction.UP);
        }

        //j is column
        for (int j = 1; j <= m; j++) {
            cache[0][j] = new Result(j * judge.getGapCost(), Direction.LEFT);
        }

        //filling
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                cache[i][j] = bestResult(cache[i - 1][j - 1].getScore() + judge.score(s.charAt(i), t.charAt(j)),
                        cache[i][j - 1].getScore() + judge.getGapCost(),
                        cache[i - 1][j].getScore() + judge.getGapCost());
            }
        }
    }

    /**
     * first i characters in s and the first j characters in t. You can
     * find the result in O(1) time by looking in your cache.
     */

    public Result getResult(int i, int j) {
        return cache[i][j];
    }

    /**
     * TODO: Mark the path by tracing back through parent pointers, starting
     * with the Result in the lower right corner of the cache. Run Result.markPath()
     * on each Result along the path. The GUI will highlight all such marked cells
     * when you check 'Show path'. As you're tracing back along the path, build
     * the aligned strings in alignedS and alignedT (using Constants.GAP_CHAR
     * to denote a gap in the strand).
     * <p>
     * Your algorithm must run in O(n + m) time, where n is the length of s
     * and m is the length of t.
     */

    private void traceback() {

        StringBuilder sBuild = new StringBuilder();
        StringBuilder tBuild = new StringBuilder();
        int sLength = n;
        int tLength = m;
        Result path = cache[sLength][tLength];

        while (!path.getParent().equals(Direction.NONE)) {
            if (path.getParent().equals(Direction.DIAGONAL)) {
                //i-- and j--
                sBuild.append(s.charAt(sLength--));
                tBuild.append(t.charAt(tLength--));
            } else if (path.getParent().equals(Direction.UP)) {
                //i--
                sBuild.append(s.charAt(sLength--));
                tBuild.append(Constants.GAP_CHAR);
            } else if (path.getParent().equals(Direction.LEFT)) {
                //j--
                sBuild.append(Constants.GAP_CHAR);
                tBuild.append(t.charAt(tLength--));
            }
            path.markPath();
            path = cache[sLength][tLength];
        }

        alignedS = sBuild.reverse().toString();
        alignedT = tBuild.reverse().toString();
    }

    /**
     * Returns true iff these strands are seemingly aligned.
     */

    public boolean isAligned() {
        return alignedS != null && alignedT != null &&
                alignedS.length() == alignedT.length();
    }

    /**
     * Returns the score associated with the current alignment.
     */

    public int getScore() {
        if (isAligned())
            return judge.score(alignedS, alignedT);
        return 0;
    }

    /**
     * Returns a nice textual version of this alignment.
     */

    public String toString() {
        if (!isAligned())
            return "[s=" + s.trim() + ",t=" + t.trim() + "]";
        final char GAP_SYM = '.', MATCH_SYM = '|', MISMATCH_SYM = ':';
        StringBuilder ans = new StringBuilder();
        ans.append(alignedS).append('\n');
        int n = alignedS.length();
        for (int i = 0; i < n; i++)
            if (alignedS.charAt(i) == Constants.GAP_CHAR ||
                    alignedT.charAt(i) == Constants.GAP_CHAR)
                ans.append(GAP_SYM);
            else if (alignedS.charAt(i) == alignedT.charAt(i))
                ans.append(MATCH_SYM);
            else
                ans.append(MISMATCH_SYM);
        ans.append('\n').append(alignedT).append('\n').
                append("score = ").append(getScore());
        return ans.toString();
    }

    /**
     * Returns a DNA strand of length n with randomly selected nucleotides.
     */

    public static String randomDNA(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append("ACGT".charAt(gen.nextInt(4)));
        return sb.toString();
    }

}
