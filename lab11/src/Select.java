import java.util.Comparator;

public class Select {

    /**
     * TODO #5
     * <p>
     * Returns the k-th largest element in the given non-empty
     * array. Assume k is at least 1 and at most n, where n is the
     * length of the array.
     * <p>
     * Implementation requirements: Copy all n elements from the array
     * into a priority queue. Then remove k - 1 elements from the queue
     * and return the head of the queue.
     * <p>
     * What is the Big-O running time of kthLargest (in terms of k and
     * n)?
     */

    public static int kthLargest(int[] a, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((x, y) -> y - x);
        for (int i = 0; i < a.length; i++) {
            pq.offer(a[i]);
        }

        for (int i = 0; i < k-1; i++) {
            pq.poll();
        }

        return pq.peek();
    }

    /**
     * Simple testing.
     */

    public static void main(String... args) {
        int[] a = new int[]{4, 2, 1, 7, 8, 9, 3, 5, 10, 6};
        System.out.println(kthLargest(a, 4) == 7);
    }
}