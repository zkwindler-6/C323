package com.company;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Testing {

  //@Test
  /**
   * Start with this test. 
   */
  public void gettersTest() {
    /**
     *       0
     *     /   \
     *    1     2
     *   / \   /
     *  3  4  5
     */
    assertEquals(1, Select.leftChild(0));
    assertEquals(3, Select.leftChild(1));
    assertEquals(5, Select.leftChild(2));
    assertEquals(2, Select.rightChild(0));
    assertEquals(4, Select.rightChild(1));
    assertEquals(0, Select.parent(1));
    assertEquals(0, Select.parent(2));
    assertEquals(1, Select.parent(3));
    assertEquals(1, Select.parent(4));
    assertEquals(2, Select.parent(5));
  }

  //@Test
  /**
   * When you're ready to run this test, remove the // from the above line.
   */
  public void swapTest() {
    int[] a = new int[] { 5, 9, 8, 2, 0, 3 };
    Select.swap(a, 0, 1);
    assertArrayEquals(new int[] { 9, 5, 8, 2, 0, 3 }, a);
    Select.swap(a, 5, 2);
    assertArrayEquals(new int[] { 9, 5, 3, 2, 0, 8 }, a);
    Select.swap(a, 3, 3);
    assertArrayEquals(new int[] { 9, 5, 3, 2, 0, 8 }, a);
    Select.swap(a, a.length - 1, 0);
    assertArrayEquals(new int[] { 8, 5, 3, 2, 0, 9 }, a);
  }

  @Test
  public void siftDownTest() {
    int[] a = new int[] { 1, 2 };
    Select.siftDown(a, 0, 1);
    assertArrayEquals(new int[] { 2, 1 }, a);
    a = new int[] { 5 };
    Select.siftDown(a, 0, 0);
    assertArrayEquals(new int[] { 5 }, a);
    a = new int[] { 1, 2 };
    Select.siftDown(a, 0, 0);
    assertArrayEquals(new int[] { 1, 2 }, a);
    a = new int[] { 1, 2, 3 };
    Select.siftDown(a, 0, 2);
    assertArrayEquals(new int[] { 3, 2, 1 }, a);
    a = new int[] { 1, 9, 8, 7, 6, 5, 4, 3, 2 };
    Select.siftDown(a, 0, 8);
    assertArrayEquals(new int[] { 9, 7, 8, 3, 6, 5, 4, 1, 2 }, a);  
    int[] heap = new int[] { 25, 23, 19, 10, 18, 15, 17, 7, 5, 13, 9, 8, 14, 1, 4, 3, 6, 2 };
    int n = heap.length;
    assertTrue(verifyOrderingProperty(heap));
    heap[0] = 20;
    Select.siftDown(heap, 0, n - 1);
    assertArrayEquals(new int[] { 23, 20, 19, 10, 18, 15, 17, 7, 5, 13, 9, 8, 14, 1, 4, 3, 6, 2 },
        heap);
    assertTrue(verifyOrderingProperty(heap));
  }

  //@Test
  public void smallHeapify() {
    int[] a = new int[] { 0, 3, 9, 4, 8, 2, 5 };
    Select.heapify(a);
    assertArrayEquals(new int[] { 9, 8, 5, 4, 3, 2, 0 }, a);
  }

  //@Test
  public void bigHeapify() {
    int[] a = new int[10000];
    List<Integer> ints = new LinkedList<>();
    for (int i = 0; i < a.length; i++)
      ints.add(i);
    Random gen = new Random();
    for (int i = 0; i < a.length; i++)
      a[i] = ints.remove(gen.nextInt(ints.size()));
    Select.heapify(a);
    assertTrue(verifyOrderingProperty(a));
  }

  //@Test
  public void smallSelect() {
    int[] a;
    a = new int[] { 5 };
    assertEquals(5, Select.kthLargest(a, 1));
    assertArrayEquals(new int[] { 5 }, a);
    a = new int[] { 5, 3 };
    assertEquals(5, Select.kthLargest(a, 1));
    assertArrayEquals(new int[] { 5, 3 }, a);
    a = new int[] { 3, 5 };
    assertEquals(5, Select.kthLargest(a, 1));
    assertArrayEquals(new int[] { 3, 5 }, a);
    assertEquals(3, Select.kthLargest(a, 2));
    assertArrayEquals(new int[] { 3, 5 }, a); 
    a = new int[] { 3, 4, 5 };
    assertEquals(5, Select.kthLargest(a, 1));
    assertEquals(4, Select.kthLargest(a, 2));
    assertEquals(3, Select.kthLargest(a, 3));
    assertArrayEquals(new int[] { 3, 4, 5 }, a);
    a = new int[] { 5, 3, 4 };
    assertEquals(5, Select.kthLargest(a, 1));
    assertEquals(4, Select.kthLargest(a, 2));
    assertEquals(3, Select.kthLargest(a, 3));
    assertArrayEquals(new int[] { 5, 3, 4 }, a);
 }

  /**
   * TODO: Write some medium sized tests of kthLargest().
   */

  //@Test
  public void mediumSelect() {
    // TODO
 
  }

  /********* Some Testing Utilities *************/

  public static boolean verifyOrderingProperty(int[] heap) {
    int n = heap.length;
    int last = Select.parent(n - 1);
    for (int p = last; p >= 0; p--) {
      int left = Select.leftChild(p);
      if (heap[p] < heap[left])
        return false;
      int right = left + 1;
      if (right < n && heap[p] < heap[right])
        return false;
    }
    return true;
  }

  public static boolean isSorted(int[] a, int n) {
    for (int i = 1; i < n; i++)
      if (a[i - 1] > a[i])
        return false;
    return true;
  }
}
