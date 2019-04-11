/**
 * TODO: Implement the addFront() method.
 */

import java.util.Iterator;

public class AList<K, V> extends DoublyLinkedList<Assoc<K, V>> {

    /**
     * Inserts the association a at the front of this list.
     * @param a association to put in.
     */

    public void addFront(Assoc<K, V> a) {
        Node newNode = new Node(a, head, head.next);//make a new node contains association
        head.next.prev = newNode;
        head.next = newNode;
        n++;
        modCount++;//array is been modified.
    }

    /**
     * Returns the association with the given key in this map, if it
     * exists, and null otherwise. Self-adjusts by moving the returned
     * association to the front.
     */
    public Assoc<K, V> get(K key) {
        Assoc<K, V> a;
        Iterator<Assoc<K, V>> it = iterator();
        while (it.hasNext()) {
            a = it.next();
            if (key.equals(a.key)) {
                it.remove();
                addFront(a);
                return a;
            }
        }
        return null;
    }

}
