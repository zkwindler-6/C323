import java.util.Comparator;

/**
 * lab11: starter code
 * <p>
 * A PriorityQueue is a collection of keys, ordered according to a
 * specified comparator. The keys will be stored in a heap that is
 * represented as an array. The main operations on a PriorityQueue are
 * offer(), peek(), and poll().
 * <p>
 * TODO: There are 4 methods for you to complete:
 * <p>
 * #1 and #2: Implement the heap utilities siftUp() and siftDown()
 * that will be used to restore the ordering property on a heap after
 * inserting or deleting, respectively, keys from the heap.
 * <p>
 * #3 and #4: Implement the offer() and poll() methods, as described
 * in lecture.
 * <p>
 * If you are unable to complete this exercise during lab, continue to
 * work on it on your own because you will need an implementation of
 * PriorityQueue for when you implement Huffman's algorithm next week.
 */

public class PriorityQueue<E> {

    E[] heap = newTable(10);
    Comparator<E> comp;
    int n;

    /**
     * Creates a priority queue that orders its elements according to
     * the specified comparator.
     */

    public PriorityQueue(Comparator<E> comp) {
        this.comp = comp;
    }

    /**
     * TODO #3
     * <p>
     * Inserts the specified element into this priority queue.
     * <p>
     * Implementation requirements:
     * <p>
     * (1) If the heap array is full, then create a new array that is
     * twice the size and copy in the keys.
     * <p>
     * (2) Add the new element as a new rightmost leaf on the last level
     * of the heap and then run the siftUp() utility to adjust the
     * ordering of the keys within the heap.
     */

    public void offer(E e) {

        if (n == heap.length) {
            E[] heap2 = newTable(n * 2);
            for (int i = 0; i < n; i++)
                heap2[i] = heap[i];
            heap = heap2;
        }
        heap[n] = e;
        siftUp(n);
        n++;
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns
     * null if this queue is empty.
     */

    public E peek() {
        if (isEmpty())
            return null;
        return heap[0];
    }

    /**
     * TODO #4
     * <p>
     * Retrieves and removes the head of this queue, or returns null if this
     * queue is empty.
     * <p>
     * Implementation requirements:
     * <p>
     * (1) To delete the root, cut off the rightmost leaf on the last
     * level of the heap and move its key into root position.
     * <p>
     * (2) Adjust the heap ordering property by running the siftDown()
     * utility.
     */

    public E poll() {

        if (n == 0)
            return null;
        E holder  = heap[0];
        //swap(0, n);
        heap[0] = heap[n-1];
        heap[this.size() - 1] = null;
        n--;
        siftDown(0);
        return holder;
    }

    /**
     * Returns the number of keys in this queue.
     */

    public int size() {
        return n;
    }

    /**
     * Returns true iff this queue is empty.
     */

    public boolean isEmpty() {
        return size() == 0;
    }

    /////////////////////////////////////
    // Utility methods below this line //
    /////////////////////////////////////

    /**
     * TODO #1
     * <p>
     * Restores the ordering property at node p so that the first n
     * elements of array heap form a heap ordered by this queue's
     * comparator. Used by poll().
     * <p>
     * Implementation requirements: Use O(1) additional space. In
     * particular, this means that you should not use recursion to
     * implement this method.
     */

    public void siftDown(int p) {

        int left = leftChild(p);
        while (left < n) {
            int minChild = left;
            int right = rightChild(p);

            if (right < n && comp.compare(heap[right], heap[minChild]) < 0) {
                minChild = right;
            }

            if (comp.compare(heap[minChild], heap[p]) < 0) {
                swap(p, minChild);
                p = minChild;
            } else
                return;
            left = leftChild(p);
        }
    }

    /**
     * TODO #2
     * <p>
     * Restores the heap ordering property by sifting the key at
     * position q up into the heap. Used by offer().
     * <p>
     * Implementation requirements: Use O(1) additional space. In
     * particular, this means that you should not use recursion to
     * implement this method.
     */

    public void siftUp(int q) {

        int parent = parent(q);
        while (parent < n) {
            if (comp.compare(heap[q], heap[parent]) < 0) {
                swap(q, parent);
                q = parent;
            } else
                return;
            parent = parent(q);
        }
    }

    /**
     * Exchanges the elements at indices i and j in the heap.
     */

    private void swap(int i, int j) {
        if (i != j) {
            E temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }

    /**
     * Returns a logical pointer to the left child of node p.
     */

    private static int leftChild(int p) {
        return 2 * p + 1;
    }

    /**
     * Returns a logical pointer to the right child of node p.
     */

    private static int rightChild(int p) {
        return leftChild(p) + 1;
    }

    /**
     * Returns a logical pointer to the parent of node p.
     */

    private static int parent(int p) {
        return (p - 1) / 2;
    }

    /**
     * Technical workaround for creating a generic array.
     */

    @SafeVarargs
    private static <E> E[] newTable(int length,
                                    E... table) {
        return java.util.Arrays.copyOf(table, length);
    }

}
