package com.company;

/**
 * TODO: Implement kthLargest using the following algorithm:
 * <p>
 * Algorithm kthLargest(a, k)
 * Copy the keys from the array a into a max-heap.
 * Repeat k-1 times:
 * - Delete the root of the heap
 * - Restructure the heap to preserve the ordering property
 * Return the key at the root of the heap.
 *
 * @author <zkwindle xw46/username of you and your partner here>
 */

public class Select {

    /**
     * TODO
     * <p>
     * Returns the kth largest element in the array a. This method
     * is non-destructive (in the sense that no modifications to the
     * array a are made).
     */

    public static int kthLargest(int[] a, int k) {
        assert a != null;
        int n = a.length;
        assert n > 0 && k <= n;

        // TODO: implement the above algorithm.
        // Do not use Arrays.sort().

        for (int i = 0; i <= k; i++) {
            heapify(a);
            swap(a, 0, n-i-1);
        }

        return a[n - k];
    }

    /**
     * TODO
     * <p>
     * Rearranges the elements of a so that they form a max-heap.
     */

    public static void heapify(int[] a) {
        int n = a.length;
        int last = parent(n - 1);
        for (int p = last; p >= 0; p--) {
            siftDown(a, p, n);
        }

        // TODO: run siftDown level by level, starting with
        // the next to last level

    }

    /**
     * TODO
     * <p>
     * Restores the ordering property at node p so that elements from
     * 0 to last, inclusive, in the array a form a max-heap.
     */

    public static void siftDown(int[] a, int p, int last) {

        // TODO: implement the siftDown algorithm non-recursively
        //                                        ^^^^^^^^^^^^^^^

        /*int left = leftChild(p);
        if (left <= last){
            int maxChild = left;
            int right = rightChild(p);
            if (right <= last && a[left] < a[right]){
                maxChild = right;
            }
            if (a[maxChild] > a[p]){
                swap(a,p,maxChild);
                siftDown(a, maxChild, last);
            }
        }*/

        int left = leftChild(p);
        while (left < last) {
            int minChild = left;
            int right = rightChild(p);

            if (right < last && a[right] < a[left]) {
                minChild = right;
            }

            if (a[minChild] < a[p]) { //min heap?
                swap(a, p, minChild);
                p = minChild;
            } else
                return;
        left = leftChild(p);
        }

    }

    /**
     * TODO
     * <p>
     * Exchanges the elements at indices i and j in the array a.
     */

    public static void swap(int[] a, int i, int j) {

        // TODO: make use of a temp variable to exchange a[i]
        // with a[j]

        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * TODO
     * <p>
     * Returns a logical pointer to the left child of node p.
     */

    public static int leftChild(int p) {

        // TODO
        return 2 * p + 1;
    }

    /**
     * Returns a logical pointer to the right child of node p.
     */

    public static int rightChild(int p) {
        return leftChild(p) + 1;
    }

    /**
     * TODO
     * <p>
     * Returns a logical pointer to the parent of node p.
     */

    public static int parent(int p) {

        // TODO
        return (p - 1) / 2;
    }

    /**
     * Run a few simple tests.
     */

    public static void main(String[] args) {
        // Verify that asserts are being checked.
        assert true == false; // delete later

        int[] a, b, copyOfA;
        a = new int[]{8, 7, -3, 9, 2, 1, -5, 4, 12, -2};
        b = new int[]{12, 9, 8, 7, 4, 2, 1, -2, -3, -5};
        copyOfA = new int[]{8, 7, -3, 9, 2, 1, -5, 4, 12, -2};

        for (int i = 0; i < a.length; i++) {
            assert kthLargest(a, i + 1) == b[i];
            // Make sure kthLargest is non-destructive.
            assert a[i] == copyOfA[i];
        }

        System.out.println("Correctness checks passed!");
    }
}
