import java.util.Iterator;

/**
 * (1) Thoroughly document this class and its fields/methods.
 * (2) Implement the size() method defined in Map.
 * (3) Implement the remove() method defined in Map.
 */

public class HashMap<K, V> implements Map<K, V> {
    AList<K, V>[] table;//HashMap is holding a array of Alist named "table"
    int n;//size of this hashmap.

    public HashMap() {
        this(3);
    }//initial HashMap will hold three empty tables.

    /**
     * HashMap Constructor.
     * Creates a HashMap of size n. Initialize HashMap with empty ALists.
     *
     * @param n size of the table.
     */
    public HashMap(int n) {
        table = newTable(n);
        for (int i = 0; i < table.length; i++)
            table[i] = new AList<>();//initialize empty associations.
    }

    /**
     * Put association between given key and value.
     * If association is already exists in this HashMap, saves its value to return, and changes the value.
     * If association is not already exist, then add the association at front.
     *
     * @param key   key
     * @param value value
     */
    public V put(K key, V value) {
        int i = hash(key);//get index using hashCode of given key.
        Assoc<K, V> a = table[i].get(key);//get association in HashMap if exists.
        if (a == null) {//association is not exist
            table[i].addFront(new Assoc<K, V>(key, value));//add it to the front then
            n++;
            return null;
        }
        V ans = a.value;
        a.value = value;
        return ans;
    }

    /**
     * get association with given key.
     * If its not present in HashMap, return null.
     *
     * @param key key
     */
    public V get(K key) {
        int i = hash(key);//get the index based on hashCode value of key.
        Assoc<K, V> a = table[i].get(key);
        if (a == null)//return null if Hashmap doesn't have it.
            return null;
        return a.value;
    }

    /**
     * Return the size of this HashMap.
     *
     * @return int size of this hashmap.
     */

    public int size() {
        return n;
    }

    @Override
    public Iterator<V> values() {
        return new Iterator<V>() {
            int i = 0, j = -1;
            V currVal;

            public V skip() {
                int k = this.i;
                while (k < table.length) {
                    if (table[k].size() - 1 == j) {
                        j = -1;
                        k++;
                        continue;
                    }
                    if (!table[k].isEmpty() && j < table[k].size() - 1) {
                        j++;
                        this.i = k;
                        currVal = table[k].get(j).value;
                        return currVal;
                    }
                    k++;
                }
                currVal = null;
                return currVal;
            }

            @Override
            public boolean hasNext() {
                int tempj = j, tempi = i;
                if (skip() != null) {
                    i = tempi;
                    j = tempj;
                    return true;
                }
                return false;
            }

            @Override
            public V next() {
                return skip();
            }
        };
    }

    /**
     * remove association with given key from HashMap.
     *
     * @param key key
     * @return value associated with the key getting removed.
     */
    public V remove(K key) {
        int i = hash(key);//get hashcode
        AList<K, V> a = table[i];
        if (a == null) {
            return null;
        }
        int ind = 0;
        V ans = null;
        for (Assoc<K, V> s : a) {
            if (s.key.equals(key)) {
                ans = s.value;
                break;
            }
            ind++;
        }
        if (ans != null) {
            a.remove(ind);
            n--;
        }
        return ans;
    }

    /**
     * Return hashCode value with given key.
     *
     * @param key key.
     * @return int hashcode of the key given.
     */
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