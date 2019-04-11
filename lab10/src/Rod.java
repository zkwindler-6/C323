/**
 * lab10: starter code
 * <p>
 * A Rod is constructed from an array of non-negative prices, where
 * price[i] represents the selling price of a rod of length i. We
 * assume here that price[0] is 0.
 * <p>
 * We wish to determine the maximum profit obtainable by cutting up a
 * rod of length n and selling the pieces. We also wish to know the
 * lengths of each cut segment that obtains the maximum profit. Thus,
 * a Result consists of two pieces of information: the total profit
 * and a list of lengths.
 * <p>
 * TODO:
 * <p>
 * (1) Create a lab10 project in IntelliJ and copy your code for Map,
 * HashMap, AList, Assoc, and DoublyLinkedList from p4 into the src
 * directory.
 * <p>
 * (2) Implement the naive brute-force solution, as described in
 * lecture, to generate all configurations of different pieces and
 * find the highest priced configuration. Do this in two stages:
 * <p>
 * -- Implement the basicCut() method to return an int
 * representing the maximal profit.
 * <p>
 * -- Implement the fancyCut() method to return a Result
 * object that includes the maximal profit and a list
 * of corresponding rod lengths
 * <p>
 * (3) Memoize your fancyCut() method by using a cache (of type
 * HashMap) as an instance variable.
 * <p>
 * Zachary Windler zkwindle
 * Sam Oates samoates
 */

public class Rod {

    Map<Integer, Result> cache;

    class Result {
        int profit;
        List<Integer> lengths;

        Result(int profit) {
            this.profit = profit;
            lengths = new DoublyLinkedList<>();
        }

        public String toString() {
            return String.format("[numCuts=%d, profit=%d, lengths=%s]",
                    lengths.size() - 1, profit, lengths);
        }
    }

    private int[] price;

    public Rod(int[] price) {
        this.price = price;
        cache = new HashMap<>();
    }

    /**
     * TODO
     * <p>
     * Returns the maximal profit obtainable by cutting a rod
     * of length n. Implemented as a top-down recursive method.
     * Runs in O(2^n) time.
     */

    public int basicCut(int n) {
        if (n == 0)
            return 0;

        //top down
        int profit = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            int var = basicCut(n-i) + price[i];
            profit = Math.max(profit, var);
        }

        //bottom up
//        int profit = Integer.MIN_VALUE;
//        for (int i = 0; i < n; i++) {
//            int var = basicCut(i) + price[n - i];
//            profit = Math.max(profit, var);
//        }

        return profit;
    }

    /**
     * TODO
     * <p>
     * Returns a Result containing the maximal profit obtainable by
     * cutting a rod of length n and a list of corresponding rod
     * lengths. Implemented as a memoized top-down recursive method.
     * Runs in O(n^2) time.
     */

    public Result fancyCut(int n) {

        Result temp = cache.get(n);
        if (temp != null)
            return temp;

        //brute force recursive
        if (n == 0)
            return new Result(0);

        Result maxProfit = new Result(Integer.MIN_VALUE);

        //top down
        for (int i = 1; i <= n; i++) {
            Result posProfit = new Result(Integer.MIN_VALUE);
            posProfit.lengths.add(i);
            Result holder = fancyCut(n - i);
            posProfit.profit = holder.profit + price[i];
            if (posProfit.profit > maxProfit.profit) {
                maxProfit.profit = posProfit.profit;
                for (Integer x : holder.lengths) {
                    posProfit.lengths.add(x);
                }
                maxProfit.lengths = posProfit.lengths;
            }
        }

        //bottom up
//        for (int i = 0; i < n; i++) {
//            Result posProfit = new Result(Integer.MIN_VALUE);
//            posProfit.lengths.add(n-i);
//            Result holder = fancyCut(i);
//            posProfit.profit = holder.profit + price[n-i];
//            if (posProfit.profit > maxProfit.profit) {
//                maxProfit.profit = posProfit.profit;
//                for (Integer x : holder.lengths) {
//                    posProfit.lengths.add(x);
//                }
//                maxProfit.lengths = posProfit.lengths;
//            }
//        }

        cache.put(n, maxProfit);

        return maxProfit;
    }

}