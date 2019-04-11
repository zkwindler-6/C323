/**
 * TODO:
 * (1) Thoroughly document this class and its fields/methods.
 * (2) Implement the size() method defined in Map.
 * (3) Implement the remove() method defined in Map.
 */

public class HashMap<K, V> implements Map<K, V> {
    AList<K, V>[] table;
    int size = 0;

    public HashMap() {
        this(3);
    }

    public HashMap(int n) {
        table = newTable(n);
        for (int i = 0; i < table.length; i++)
            table[i] = new AList<>();
    }

    public V put(K key, V value) {
        int i = hash(key);
        Assoc<K, V> a = table[i].get(key);
        if (a == null) {
            table[i].addFront(new Assoc<K, V>(key, value));
            size++;
            return null;
        }
        V ans = a.value;
        a.value = value;
        return ans;
    }

    public V get(K key) {
        int i = hash(key);
        Assoc<K, V> a = table[i].get(key);
        if (a == null)
            return null;
        return a.value;
    }

    /**
     * TODO
     */

    // return the size of the hashmap.
    //the size is only updated when put inserts something new.
    public int size() {
        return size;
    }

    //returns the value removed or null if the value doesn't exist
    public V remove(K key ){
        int i = hash(key);
        Assoc<K, V> a = table[i].get(key); //adds the Assoc to the front of the list
        if (a == null) {
            return null;
        }
        table[i].remove(0); //removes the 0th index because its at the front
        size--;
        return a.value;
    }

    public int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Technical workaround for creating a generic array.
     */

    @SafeVarargs
    private static <K, V> AList<K, V>[] newTable(int length,
                                                 AList<K, V>... table) {
        return java.util.Arrays.copyOf(table, length);
    }
}