/**
 * TODO
 * <p>
 * Starting with the DoublyLinkedList from p2, we add a search method that
 * returns a Location. This involves doing four things:
 * <p>
 * (1) Add the search method to the List interface. [This is done for you.]
 * (2) Make the Node class inside DoublyLinkedList implement the Location
 * interface. [This is done for you.]
 * (3) TODO: Implement the three Location methods in the Node class.
 * (4) TODO: Implement the search method in the DoublyLinkedList class.
 */

import java.util.Iterator;
import java.util.ConcurrentModificationException;

/**
 * If you want to call yourself a List, then implement this interface:
 */

interface List<T> extends Iterable<T> {
    void add(T x);

    T remove(int i);

    boolean remove(T x);

    T get(int i);

    boolean contains(T x);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    Location<T> search(T x);     // NEW!
}

public class DoublyLinkedList<T> implements List<T> {

    /**
     * Node is a pair containing a data field and pointers to
     * the previous and next nodes in the list.
     */

    class Node implements Location<T> {     // NEW!
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

        /**
         * Returns the data at this location.
         */

        public T get() {
            return this.data;
        }

        /**
         * Returns the location that precedes this one (if it exists).
         */

        public Node getBefore() {
            if (this.prev != head){
                return this.prev;
            }
            else
                return null;
        }

        /**
         * Returns the location that follows this one (if it exists).
         */

        public Node getAfter() {
            if (this.next != head){
                return this.next;
            }
            else
                return null;
        }
    }

    final Node head; // always points to the headnode for this list
    int n;           // the number of nodes in this list, initially 0
    int modCount;    // the number of modifications made to this list

    /**
     * Creates the empty list.
     */

    public DoublyLinkedList() {
        head = new Node(null);
        head.prev = head.next = head;
    }

    /**
     * Returns the location of the value x in this list, if it exists, and
     * null otherwise.
     */

    public Node search(T x) {
        Node p = head.next;

        while (p != head) {
            if (x.equals(p.data))
                return p;
            else
                p = p.next;
        }
        return null;
    }

    /**
     * Inserts the value x at the end of this list.
     */

    public void add(T x) {
        modCount++;
        n++;
        Node p = new Node(x, head.prev, head);
        head.prev = head.prev.next = p;
    }

    /**
     * Removes the element at index i from this list.
     *
     * @return the data in the removed node.
     * @throws IndexOutOfBoundsException iff i is out of range for this list.
     */

    public T remove(int i) {
        if (i < 0 || i >= size())
            throw new IndexOutOfBoundsException();
        Node p = head.next;
        while (i > 0) {
            p = p.next;
            i--;
        }
        return removeHelper(p);
    }

    /**
     * Helper method to remove the node pointed to by p.
     */

    private T removeHelper(Node p) {
        assert p != head;
        n--;
        modCount++;
        p = p.prev;
        T ans = p.next.data;
        p.next.next.prev = p;
        p.next = p.next.next;
        return ans;
    }

    /**
     * @return true iff an element is actually removed.
     */

    public boolean remove(T x) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T y = it.next();
            if (x.equals(y)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the i-th element from this list, where i is a zero-based
     * index.
     *
     * @throws IndexOutOfBoundsException iff i is out of range for this list.
     */

    public T get(int i) {
        if (i < 0 || i >= size())
            throw new IndexOutOfBoundsException();
        Node p = head.next;
        while (i > 0) {
            p = p.next;
            i--;
        }
        return p.data;
    }

    /**
     * Returns true iff the value x appears somewhere in this list.
     */

    public boolean contains(T x) {
        Node p = head.next;
        while (p != head) {
            if (p.data.equals(x))
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
     * Returns an iterator for this list.
     */

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node curr = head.next;
            int expectedModCount = modCount;
            boolean canRemove = false;

            public boolean hasNext() {
                return curr != head;
            }

            /**
             * Returns the next element in the list.
             *
             * @throws ConcurrentModificationException if the list has been
             * structurally modified since the last call to next().
             */

            public T next() {
                if (modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                T ans = curr.data;
                curr = curr.next;
                canRemove = true;
                return ans;
            }

            /**
             * Removes the element most recently returned by next(). Must run
             * in O(1) time.
             *
             * @throws IllegalStateException if next() has not yet been called, or
             * remove() has already been called after the last call to next().
             */

            public void remove() {
                if (!canRemove)
                    throw new IllegalStateException();
                canRemove = false;
                removeHelper(curr.prev);
                expectedModCount++;
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

    /**
     * Helper method to verify that the forward and backward links
     * are consistent.
     */

    public boolean checkLinks() {
        Node p = head;
        if (p == null)
            return false;
        p = p.next;
        while (p != head) {
            if (p == null || p.next == null || p != p.next.prev)
                return false;
            p = p.next;
        }
        return true;
    }

}

