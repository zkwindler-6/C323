import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

public class Testing {

  @Test
  public void testSiftDown() {
    PriorityQueue<String> pets = new PriorityQueue<>((s, t) -> s.compareTo(t));
    Object[] heap;
    heap = pets.heap;
    int i = 0;
    /*
                  cat
           gnu           bat
        rat   pig     cow   dog
     */
    for (String pet : new String[] { "cat", "gnu", "bat", "rat", "pig", "cow", "dog" })
      heap[i++] = pet;
    pets.n = i;
    pets.siftDown(0);
    /*
                  bat
           gnu           cat
        rat   pig     cow   dog
     */
    assertEquals("bat", heap[0]);
    assertEquals("cat", heap[2]);
    heap[0] = "emu";
    /*
                  emu
           gnu           cat
        rat   pig     cow   dog
     */
    pets.siftDown(0);
    /*
                  cat
           gnu           cow
        rat   pig     emu   dog
     */
    assertEquals("cat", heap[0]);
    assertEquals("cow", heap[2]);
    assertEquals("emu", heap[5]);
  }

  @Test
  public void testSiftUp() {
    PriorityQueue<String> pets = new PriorityQueue<>((s, t) -> s.compareTo(t));
    Object[] heap;
    heap = pets.heap;
    int i = 0;
    /*
                  asp
           gnu           bat
        rat   ant     cow   dog
     */
    for (String pet : new String[] { "asp", "gnu", "bat", "rat", "ant", "cow", "dog" })
      heap[i++] = pet;
    pets.n = i;
    pets.siftUp(4);
    /*
                  ant
           asp           bat
        rat   gnu     cow   dog
     */
    assertEquals("ant", heap[0]);
    assertEquals("asp", heap[1]);
    assertEquals("gnu", heap[4]);
    heap[i++] = "alf";
    pets.n = i;
    /*
                  ant
           asp           bat
        rat   gnu     cow   dog
     alf
     */
    pets.siftUp(pets.n - 1);
    /*
                  alf
           ant           bat
        asp   gnu     cow   dog
     rat
     */
    assertEquals("alf", heap[0]);
    assertEquals("ant", heap[1]);
    assertEquals("asp", heap[3]);
    assertEquals("rat", heap[7]);
  }

  @Test
  public void testOffer() {
    PriorityQueue<String> pets = new PriorityQueue<>((s, t) -> s.compareTo(t));
    Object[] heap;
    heap = pets.heap;
    pets.offer("dog");
    assertEquals(1, pets.size());
    assertEquals("dog", heap[0]);
    pets.offer("cat");
    assertEquals(2, pets.size());
    assertEquals("cat", heap[0]);
    assertEquals("dog", heap[1]);
    pets.offer("ant");
    assertEquals(3, pets.size());
    assertEquals("ant", heap[0]);
    assertEquals("dog", heap[1]);
    assertEquals("cat", heap[2]);
    pets.offer("bat");
    assertEquals(4, pets.size());
    assertEquals("ant", heap[0]);
    assertEquals("bat", heap[1]);
    assertEquals("cat", heap[2]);
    assertEquals("dog", heap[3]);
    pets.offer("emu");
    assertEquals(5, pets.size());
    assertEquals("ant", heap[0]);
    assertEquals("bat", heap[1]);
    assertEquals("cat", heap[2]);
    assertEquals("dog", heap[3]);
    assertEquals("emu", heap[4]);
    // force a resize
    int len = heap.length;
    for (int i = 5; i <= len; i++)
      pets.offer("xxx");
    assertEquals(len + 1, pets.size());
    heap = pets.heap;
    assertEquals(2 * len, heap.length);
  }

  @Test
  public void testPoll() {
    PriorityQueue<String> pets;
    pets = new PriorityQueue<>((s, t) -> t.compareTo(s));
    assertNull(pets.poll());
    pets.offer("cat");
    assertEquals("cat", pets.poll());
    assertNull(pets.poll());
    String[] zoo = new String[] { 
        "emu", "boa", "gnu", "asp", "cat", "dog", "fox", "rat", 
        "hog", "lion", "bird", "bat", "ant", "cow", "goat",
        "squirrel", "monkey", "giraffe", "elephant", "bear",
        "tiger", "sloth", "deer", "racoon", "possum"};
    // mix up the zoo
    Random rand = new Random();
    int n = zoo.length;
    for (int k = 0; k < 100; k++) {
      int i = rand.nextInt(n);
      int j = rand.nextInt(n);
      if (i != j) {
        String temp = zoo[i];
        zoo[i] = zoo[j];
        zoo[j] = temp;
      }
    }
    for (String pet : zoo)
      pets.offer(pet);
    n = pets.size();
    assertEquals("tiger", pets.poll());
    assertEquals(n - 1, pets.size());
    assertEquals("squirrel", pets.poll());
    assertEquals(n - 2, pets.size());
    assertEquals("sloth", pets.poll());
    assertEquals(n - 3, pets.size());
    assertEquals("rat", pets.poll());
    assertEquals(n - 4, pets.size());
    assertEquals("racoon", pets.poll());
    assertEquals(n - 5, pets.size());
  }

  @Test
  public void testKthLargest() {
    int[] a;
    a = new int[] { 4, 2, 1, 7, 8, 9, 3, 5, 10, 6 };
    for (int x = 1; x <= a.length; x++)
      assertEquals(11 - x, Select.kthLargest(a, x));
    a = new int[100];
    for (int x = 1; x <= a.length; x++)
      assertEquals(0, Select.kthLargest(a, x));

    a = new int[1000];
    Random rand = new Random();
    for (int i = 0; i < a.length; i++) 
      a[i] = rand.nextInt(100);
    a[rand.nextInt(a.length)] = 1000;
    assertEquals(1000, Select.kthLargest(a, 1));
  }
}