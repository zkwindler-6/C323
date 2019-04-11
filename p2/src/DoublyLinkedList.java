/**
 * TODO: Include your implementation of DoublyLinkedList from hw3 here.
 * <p>
 * Add an overloaded remove() method to the List interface which allows
 * us to remove a specific element from a list. The return value should be
 * a boolean indicating whether or not the remove() was successful.
 * <p>
 * For example,
 * List<Coord> ls = ...;
 * ls.add(new Coord(1, 2));
 * ls.add(new Coord(3, 4));
 * assert 2 == ls.size();
 * assert ls.remove(new Coord(1, 2));
 * assert 1 == ls.size();
 * assert !ls.contains(new Coord(1, 2));
 * assert !ls.remove(new Coord(2, 3));
 * assert 1 == ls.size();
 */

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * If you want to call yourself a List, then implement this interface:
 */

interface List<T> extends Iterable<T> {
    void add(T x);  // simple add

    T remove(int i);

    boolean remove(T i);

    T get(int i);

    boolean contains(T x);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }
}

public class DoublyLinkedList<T> implements List<T> {

    /**
     * Node is a pair containing a data field and pointers to
     * the previous and next nodes in the list.
     */

    class Node {
        T data;
        Node next, prev;

        Node(T data) {
            this(data, null, null);
        }

        Node(T data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    final Node head;       // always points to the headnode for this list
    int n;           // the number of nodes in this list, initially 0
    int modCount;

    /**
     * Creates the empty list.
     */

    public DoublyLinkedList() {
        // TODO: Create the headnode.
        // Note that the prev and next fields in the headnode should
        // point back to the headnode.

        head = new Node(null);
        head.prev = head;
        head.next = head;

    }

    /**
     * Inserts the value x at the end of this list.
     */

    public void add(T x) {
        // TODO: This must run in O(1) time.

        n++;
        modCount++;
        Node last = head.prev;
        Node p = new Node(x, last, head);
        last.next = p;
        head.prev = p;
    }

    /**
     * Removes the element at index i from this list.
     *
     * @return the data in the removed node.
     * @throw IndexOutOfBoundsException iff i is out of range for this list.
     */

    public T remove(int i) {
        if (i < 0 || i >= size())
            throw new IndexOutOfBoundsException();
        // TODO: Don't forget to skip over the headnode.


        Node temp = head.next;

        for (int j = 0; j < i; j++) {
            temp = temp.next;
        }

        temp.next.prev = temp.prev;
        temp.prev.next = temp.next;

        T data = temp.data;

        n--;
        modCount++;

        return data;
    }

    public boolean remove(T obj) {
        Node temp = head.next;

        while (!temp.equals(head)) {
            if (obj.equals(temp.data)) {
                temp.next.prev = temp.prev;
                temp.prev.next = temp.next;
                n--;
                modCount++;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    /**
     * Returns the i-th element from this list, where i is a zero-based
     * index.
     *
     * @throw IndexOutOfBoundsException iff i is out of range for this list.
     */

    public T get(int i) {
        if (i < 0 || i >= size())
            throw new IndexOutOfBoundsException();
        // TODO: Don't forget to skip over the headnode.

        Node temp = head.next;
        for (int j = 0; j < i; j++) {
            temp = temp.next;
        }
        T data = temp.data;

        return data;
    }

    /**
     * Returns true iff the value x appears somewhere in this list.
     */

    public boolean contains(T x) {
        // TODO: Don't forget to skip over the headnode.

        Node temp = head.next;
        for (int j = 0; j < n; j++) {
            if (temp.data.equals(x))
                return true;
            temp = temp.next;
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
     * Returns an iterator for this list.
     */

    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int expectedModCount = modCount;
            Node p = head.next;
            boolean flag = false;

            public boolean hasNext() {
                // TODO
                return p != head;
            }

            public T next() {
                // TODO: Must detect a concurrent modification and
                // react by throwing a ConcurrentModificationException.

                if (modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                if (!hasNext())
                    throw new NoSuchElementException();
                T temp = p.data;
                p = p.next;
                flag = true;
                return temp;
            }

            public void remove() {
                // TODO: This must run in O(1) time.  This method can be
                // called only once per call to next().
                // Throw an Illegal StateException if next() has not yet been
                // called, or remove() has already been called after the last
                // call to next().

                if (!flag)
                    throw new IllegalStateException();
                if (modCount != expectedModCount)
                    throw new ConcurrentModificationException();

                p.prev.prev.next = p;
                p.prev = p.prev.prev;

                modCount++;
                expectedModCount++;
                n--;

                flag = false;
            }
        };
    }

    /**
     * Returns a string representing this list (resembling a Racket list).
     */

    public String toString() {
        if (isEmpty())
            return "()";
        Iterator<T> it = iterator();
        StringBuilder ans = new StringBuilder("(").append(it.next());
        while (it.hasNext())
            ans.append(" ").append(it.next());
        return ans.append(")").toString();
    }

    public static void main(String... args) {
        List<Integer> xs = new DoublyLinkedList<>();
        int[] a = new int[]{4, 3, 6, 5, 7, 8};
        for (int x : a)
            xs.add(x);
        assert 6 == xs.size();
        for (int i = 0; i < a.length; i++)
            assert xs.get(i) == a[i];
        assert !xs.contains(null);
        for (int x : a)
            assert xs.contains(x);
        assert "(4 3 6 5 7 8)".equals(xs.toString());
        assert xs.remove(0) == 4;
        assert xs.remove(1) == 6;
        assert 4 == xs.size();
        assert "(3 5 7 8)".equals(xs.toString());
        while (!xs.isEmpty())
            xs.remove(xs.size() - 1);
        assert 0 == xs.size();
        assert "()".equals(xs.toString());
        for (int x : a)
            xs.add(x);
        assert "(4 3 6 5 7 8)".equals(xs.toString());
        for (int x : xs)
            assert xs.contains(x);
        Iterator<Integer> it = xs.iterator();
        try {
            it.remove();
            assert false;
        } catch (IllegalStateException ex) {
        }
        assert "(4 3 6 5 7 8)".equals(xs.toString());
        assert it.hasNext();
        assert 4 == it.next();
        it.remove();
        assert "(3 6 5 7 8)".equals(xs.toString());
        try {
            it.remove();
            assert false;
        } catch (IllegalStateException ex) {
        }
        int x;
        it = xs.iterator();
        while (it.hasNext())
            if (it.next() % 2 == 0)
                it.remove();
        assert 3 == xs.size();
        assert "(3 5 7)".equals(xs.toString());
        try {
            for (Integer i : xs) {
                if (i != 3)
                    xs.remove(0);
            }
            assert false;
        } catch (ConcurrentModificationException ex) {
        }
        assert "(5 7)".equals(xs.toString());
        try {
            for (Integer i : xs)
                xs.add(i);
            assert false;
        } catch (ConcurrentModificationException ex) {
        }
        assert "(5 7 5)".equals(xs.toString());
        System.out.println("All tests passed...");
    }
}

