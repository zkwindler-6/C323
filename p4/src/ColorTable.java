import java.awt.Color;
import java.lang.annotation.Target;
import java.util.Random;
import java.util.Iterator;

/**
 * There is much to do here!
 * <p>
 * A ColorTable represents a dictionary of frequency counts, keyed on
 * Color. However, it is implemented as a HashMap whose keys are of
 * type TinyColor and whose values are of type Long. The benefit to
 * doing this is so that we can reduce the size of the key space by
 * limiting each Color to a certain number of bits per channel.
 * <p>
 * TODO:
 * Implement this class, including whatever data members you need
 * and all of the public methods below. You may create any number of
 * private methods if you find them to be helpful. Replace all TODO
 * comments with appropriate javadoc style comments. Be sure to
 * document all data fields and helper methods you define.
 */

public class ColorTable extends HashMap<TinyColor, Long> {
    private int bitsPerChannel;
    private double rehashThreshold;
    int initialCapacity;

    /**
     * TODO
     * <p>
     * Constructs a color table with a starting capacity of
     * initialCapacity. Keys in the color key space are truncated to
     * bitsPerChannel bits. The rehashThrehold specifies the maximum
     * tolerable load factor before triggering a rehash. Note that
     * because we are using chaining as our collision resolution
     * strategy, the load factor may rise above 1 without impacting
     * performance.
     *
     * @throws RuntimeException if initialCapacity is not in the range
     *                          [1..Constants.MAX_CAPACITY]
     * @throws RuntimeException if bitsPerChannel is not in the range
     *                          [1..8]
     */

    //DONE
    //a basic constructor and throws the appropiate exceptions for initial capacity
    //bitsperchannel, and rehashthreshold
    public ColorTable(int initialCapacity, int bitsPerChannel,
                      double rehashThreshold) {
        super(initialCapacity);
        if (initialCapacity < 1 || initialCapacity > Constants.MAX_CAPACITY)
            throw new RuntimeException();
        if (bitsPerChannel < 1 || bitsPerChannel > 8)
            throw new RuntimeException();
        if (rehashThreshold <= 0)
            throw new RuntimeException();
        this.bitsPerChannel = bitsPerChannel;
        this.rehashThreshold = rehashThreshold;
        this.initialCapacity = initialCapacity;
    }

    //DONE
    //returns the bitsperchannel for the colorTable
    public int getBitsPerChannel() {
        return bitsPerChannel;
    }

    //DONE
    //returns the frequency count from the given color
    public Long get(Color color) {
        TinyColor tiny = new TinyColor(color, bitsPerChannel);
        if (super.get(tiny) == null)
            return 0L;
        return super.get(tiny);
    }

    //DONE
    //puts a tiny color and count in the colortable.
    //tinycolor is made by the color and bitsperchannel
    public Long put(Color color, Long count) {
        TinyColor tiny = new TinyColor(color, bitsPerChannel);
        Long ans = super.put(tiny, count);
        if (count < 0)
            throw new IllegalStateException();
        if (count == 0L) {
            if (remove(tiny) == null)
                return 0L;
        }
        if (getLoadFactor() >= rehashThreshold)
            this.rehash();
        if (ans == null)
            return 0L;
        return ans;
    }

    //DONE
    //increments the count with the color
    public void increment(Color color) {
        TinyColor tiny = new TinyColor(color, bitsPerChannel);
        Long cnt = get(color);
        if (cnt != null)
            this.put(tiny, cnt + 1);
        else
            this.put(tiny, cnt + 1);
        if (getLoadFactor() >= rehashThreshold)
            this.rehash();
    }

    //returns the loadfactor
    public double getLoadFactor() {
        double loadFactor = (double)size() / (double)table.length;
        return loadFactor;
    }

    //returns the table length
    public int capacity() {
        return table.length;
    }

    /**
     * TODO
     * <p>
     * Increases the size of the array to the smallest prime greater
     * than double the current size that is of the form 4j + 3, and then
     * moves all the key/value associations into the new array.
     * <p>
     * Hints:
     * -- Make use of Util.isPrime().
     * -- Multiplying a positive integer n by 2 could result in a
     * negative number, corresponding to integer overflow. You should
     * detect this possibility and crop the new size to
     * Constants.MAX_CAPACITY.
     *
     * @throws RuntimeException if the table is already at maximum capacity.
     */

    //increases the size of the array if rehashthreshold is passed
    public void rehash() {
        if (table.length >= Constants.MAX_CAPACITY)
            throw new RuntimeException();
        int n = (capacity() * 2) + 1;
        while (!(n % 4 == 3) || !(Util.isPrime(n))) {
            n += 2;
        }
        ColorTable table2 = new ColorTable(n, bitsPerChannel, rehashThreshold);
        for (int i = 0; i < capacity(); i++){
            for (Assoc<TinyColor, Long> a : table[i]) {
                table2.put(a.key.expand(), a.value);
            }
        }
        this.table = table2.table;
        this.size = table2.size;
        this.initialCapacity = table2.initialCapacity;
    }

    //iterates through all the color possibilites based on the bitsperchannel
    public Iterator<Long> iterator() {
        return new Iterator<Long>() {

            int red = 0;
            int green = 0;
            int blue = 0;
            double step = Math.pow(2, (8-bitsPerChannel));

            public boolean hasNext() {
                return red < 256;
            }

            public Long next() {
                Color color = new Color(red, green, blue);
                blue += step;
                if (blue > 255) {
                    green += step;
                    blue = 0;
                    if (green > 255) {
                        red += step;
                        green = 0;
                    }
                }
                return get(color);
            }
        };
    }

    /**
     * Returns a String representation of this table.
     */

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < capacity(); i++)
            for (Assoc<TinyColor, Long> a : table[i])
                sb.append(a).append(" ");
        String ans = sb.toString().trim();
        return ans + "}";
    }

    /**
     * Simple testing.
     */

    public static void main(String[] args) {
        ColorTable ct = new ColorTable(11, 1, .49);
        ct.put(Color.RED, 5L);
        ct.put(Color.GREEN, 6L);
        ct.put(Color.BLUE, 7L);

        Iterator<Long> it = ct.iterator();
        while (it.hasNext())
            System.out.println(it.next());

        ColorTable table = new ColorTable(3, 6, .49);
        for (int code : new int[]{32960, 4293315, 99011, 296390})
            table.increment(new Color(code));

        System.out.println("capacity: " + table.capacity()); // Expected: 7
        System.out.println("size: " + table.size());         // Expected: 3

        System.out.println(table);
    }
}