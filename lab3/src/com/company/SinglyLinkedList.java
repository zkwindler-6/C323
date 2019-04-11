package com.company; /**
 * lab3: Below is the List interface and SinglyLinkedList class
 * developed in lecture this week.
 *
 * @author <Zachary Windler zkwindle, Glenna Hedden gahedden>
 * <p>
 * TODO
 * (1) Implement the toString() method using a StringBuilder. Test
 * thoroughly in main().
 * <p>
 * Here is an example test:
 * <p>
 * List nums = new SinglyLinkedList();
 * assert "()".equals(nums.toString());
 * nums.add(4);
 * assert "(4)".equals(nums.toString());
 * nums.add(3);
 * nums.add(6);
 * nums.add(5);
 * assert "(4 3 6 5)".equals(nums.toString());
 * <p>
 * (2) Implement the remove() method. Test thoroughly in main().
 * <p>
 * (3) Turn List into a generic interface and SinglyLinkedList into a
 * generic class so that the structure can be used to hold any
 * type T of data. Test thoroughly in main() with a variety of
 * data types.
 * <p>
 * Here is an example test:
 * <p>
 * List<String> names = new SinglyLinkedList<>();
 * names.add("Jack");
 * names.add("James");
 * names.add("Jeremiah");
 * names.add("Joshua");
 * assert "(Jack James Jeremiah Joshua)".equals(names.toString());
 * <p>
 * Note that we use List, not SinglyLinkedList, in the type
 * declaration of names. This is intentional.
 * <p>
 * (4) Challenge Problem: Add a method named swap() to the
 * SinglyLinkedList class that takes two indices and exchanges the
 * data in the nodes at the given indices. Throw an
 * IndexOutOfBoundsException if either of the two indices is out
 * of range for the list. Don't make any changes to the List
 * interface.
 * <p>
 * Here is an example test:
 * <p>
 * SinglyLinkedList<Integer> nums = new SinglyLinkedList<>();
 * nums.add(4);
 * nums.add(3);
 * nums.add(6);
 * nums.add(5);
 * nums.add(8);
 * nums.add(7);
 * nums.swap(1, 4);
 * assert "(4 8 6 5 3 7)".equals(nums.toString());
 * <p>
 * Note that to swap the elements at indices i and j, you need to
 * swap the data values in the two nodes. Implement this method by
 * walking the list just once. That is, once you've arrived at
 * node Math.min(i, j), then save your place and continue on to
 * node Math.max(i, j) rather than starting again at the
 * beginning.
 * <p>
 * You may be wondering why we used SinglyLinkedList, instead of
 * just List, in the type declaration of nums in the above
 * example. If you don't understand why this must be the case,
 * then ask your AI for clarification.
 */

/**
 * If you want to call yourself a List, then implement this interface:
 */

interface List {
    void add(int x);

    int remove(int i);

    int get(int i);

    boolean contains(int x);

    int size();

    boolean isEmpty();
}

/**
 * A SinglyLinkedList is a list containing zero or more values.
 */

public class SinglyLinkedList implements List {

    /**
     * A Node is a pair containing a data field and a pointer to
     * the next node in the list.
     */

    class Node {
        int data;
        Node next;

        Node(int data) {
            this(data, null);
        }

        Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    Node head;  // points to the first node on this list, initially null
    int n;      // the number of nodes in this list, initially 0

    /**
     * Inserts the value x at the end of this list.
     */

    public void add(int x) {
        n++;
        if (head == null)
            head = new Node(x);
        else {
            Node p = head;
            while (p.next != null)
                p = p.next;
            p.next = new Node(x);
        }
    }

    /**
     * Removes the element at index i from this list.
     *
     * @return the data in the removed node.
     * @throw IndexOutOfBoundsException iff i is out of range for this list.
     */

    public int remove(int i) {
        // TODO
        if (i < 0 || i >= n)
            throw new IndexOutOfBoundsException();
        n--;
        int data = 0;
        Node p = head;

        int counter = 0;
        for (int j = i; j > 0; j--) {
            if (i == 0) {
                p = p.next;
                data = p.data;
            } else {
                counter++;
                p = p.next;
                if (counter == i) {
                    data = get(i);
                    p.next = p.next.next;
                }
            }
        }

        return data;
    }

    /**
     * Returns the i-th element from this list, where i is a zero-based
     * index.
     *
     * @throw IndexOutOfBoundsException iff i is out of range for this list.
     */

    public int get(int i) {
        if (i < 0 || i >= size())
            throw new IndexOutOfBoundsException();
        Node p = head;
        while (i > 0) {
            p = p.next;
            i--;
        }
        return p.data;
    }

    /**
     * Returns true iff the value x appears somewhere in this list.
     */

    public boolean contains(int x) {
        Node p = head;
        while (p != null) {
            if (p.data == x)
                return true;
            p = p.next;
        }
        return false;
    }

    /**
     * Returns the number of elements in this list.
     */

    public int size() {
        return n;
    }

    /**
     * Returns true iff this is the empty list.
     */

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns a string representing this list (resembling a Racket list).
     */

    public String toString() {
        // TODO
        StringBuilder sb = new StringBuilder();
        Node p = head;
        while (p != null) {
            sb.append(p.data);
            p = p.next;
        }
        return "()";
    }

    /**
     * Simple testing.
     */

    public static void main(String... args) {
        List xs = new SinglyLinkedList();
        assert "()".equals(xs.toString());
        int[] a = new int[]{7, 4, 6, 9, 2,};
        for (int x : a)
            xs.add(x);
        assert "(7 4 6 9 2)".equals(xs.toString());
        for (int x : a)
            assert xs.contains(x);
        for (int i = 0; i < xs.size(); i++)
            assert a[i] == xs.get(i);
        assert "(7 4 6 9 2)".equals(xs.toString());
        xs.remove(3);
        assert "(7 4 6 2)".equals(xs.toString());
        while (!xs.isEmpty())
            xs.remove(0);
        assert "()".equals(xs.toString());

        List nums = new SinglyLinkedList();
        assert "()".equals(nums.toString());
        nums.add(4);
        assert "(4)".equals(nums.toString());
        nums.add(3);
        nums.add(6);
        nums.add(5);
        assert "(4 3 6 5)".equals(nums.toString());

        nums.remove(2);
        assert "(4 6 5)".equals(nums.toString());
    }
}
