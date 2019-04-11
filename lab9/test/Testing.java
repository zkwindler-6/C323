import org.junit.Test;
import static org.junit.Assert.*;

public class Testing {

  @Test 
  public void testPartition() {
    DoublyLinkedList<Integer> xs = new DoublyLinkedList<>();
    int[] a = new int[] { 28, 8, 10, 40, 15, 3, 70, 80, 20, 5};
    for (int x : a)
      xs.add(x);
    xs.partition((x, y) -> x < y, xs.head.next, xs.head.prev);
    Integer[] expected = new Integer[] { 5, 8, 10, 20, 15, 3, 28, 80, 70, 40 };
    int i = 0;
    for (Integer x : xs)
      assertEquals(expected[i++], x);
  }

  @Test 
  public void testSortIntegers() {
    DoublyLinkedList<Integer> xs = new DoublyLinkedList<>();
    int[] a = new int[] { 4, 3, 0, 6, 5, 7, 2, 8, 1 };
    for (int x : a)
      xs.add(x);
    // Sort xs in the natural order:
    xs.sort((x, y) -> x < y);
    for (int i = 1; i < xs.size(); i++)
      assertTrue(xs.get(i - 1) <= xs.get(i));
    // Sort xs in the reverse of the natural order:
    xs.sort((x, y) -> x > y);
    for (int i = 1; i < xs.size(); i++)
      assertTrue(xs.get(i - 1) >= xs.get(i));
  }

  @Test 
  public void testSortSmallLists() {
    DoublyLinkedList<Integer> xs = new DoublyLinkedList<>();
    // Evens before odds:
    xs.sort((x, y) -> x % 2 == 0);
    assertTrue(xs.isEmpty());
    xs.add(4);
    xs.sort((x, y) -> x % 2 == 0);
    assertEquals(Integer.valueOf(4), xs.get(0));
    xs.add(1);
    xs.sort((x, y) -> x % 2 == 0);
    assertEquals(Integer.valueOf(4), xs.get(0));
  }

  @Test 
  public void testSortStrings() {
    DoublyLinkedList<String> pets = new DoublyLinkedList<>();
    for (String pet : new String[] { "cat", "Dog", "ant", "Bat", "pig", "COW" })
      pets.add(pet);
    // Sort xs in the natural order:
    pets.sort((x, y) -> x.compareToIgnoreCase(y) < 0);
    assertEquals("ant", pets.get(0));
    assertEquals("Bat", pets.get(1));
    assertEquals("cat", pets.get(2));
    assertEquals("COW", pets.get(3));
    assertEquals("Dog", pets.get(4));
    assertEquals("pig", pets.get(5));
  }
}