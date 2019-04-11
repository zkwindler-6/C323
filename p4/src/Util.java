import java.awt.Color;
import java.util.Iterator;

/**
 * There's a moderate amount to do here, but only after you have
 * completed the ColorTable class.
 */

public class Util {

    //fines the cosinesimilarity from the two given colorTables
    public static double cosineSimilarity(ColorTable A, ColorTable B) {
        return dotProduct(A, B)/vectorMagnitude(A, B);
    }

    //returns the colorTable from the given image and bitsperchannel
    public static ColorTable vectorize(Image image, int bitsPerChannel) {
        int height = image.getHeight();
        int width = image.getWidth();

        ColorTable ct = new ColorTable(3, bitsPerChannel, .40);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color c = image.getColor(i, j);
                ct.increment(c);
            }
        }
        return ct;
    }

    //finds how similar two images are
    public static double similarity(Image image1, Image image2, int bitsPerChannel) {
        return cosineSimilarity(vectorize(image1, bitsPerChannel), vectorize(image2,bitsPerChannel));
    }

    //find the dot product from the two given colorTables
    private static double dotProduct(ColorTable a, ColorTable b) {
        Iterator<Long> ita = a.iterator();
        Iterator<Long> itb = b.iterator();
        double sum = 0;

        while (ita.hasNext()) {
            sum += ita.next() * itb.next();
        }
        return sum;
    }

    //find the vector magnitude from the two given colorTables
    private static double vectorMagnitude(ColorTable a, ColorTable b) {
        Iterator<Long> ita = a.iterator();
        Iterator<Long> itb = b.iterator();
        double sum = 0;
        double maga = 0;
        double magb = 0;

        while (ita.hasNext()) {
            maga += Math.pow(ita.next(),2);
        }
        while (itb.hasNext()) {
            magb += Math.pow(itb.next(),2);
        }
        sum = Math.sqrt(maga) * Math.sqrt(magb);
        return sum;
    }

    /**
     * Returns true iff n is a prime number. We handles several common
     * cases quickly, and then use a variation of the Sieve of
     * Eratosthenes.
     */

    public static boolean isPrime(int n) {
        if (n < 2)
            return false;
        if (n == 2 || n == 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        long sqrtN = (long) Math.sqrt(n) + 1;
        for (int i = 6; i <= sqrtN; i += 6) {
            if (n % (i - 1) == 0 || n % (i + 1) == 0)
                return false;
        }
        return true;
    }

}
