import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.Point;

/**
 * A Digit represents one image in the format of the MNIST database. A Digit
 * may or may not be classified. A Digit can compute the distance between
 * itself and another Digit.
 */

public class Digit {
    private static int UNCLASSIFIED = -1;

    private int label = UNCLASSIFIED;
    private int[][] image = new int[Constants.DIM][Constants.DIM]; // solid black
    private boolean select;

    /**
     * Default constructor creates an unclassified, completely black, digit.
     */

    public Digit() {
    }

    /**
     * Creates a digit based on the code string, which is formatted as a
     * comma-separated-values line. The first token on the line is
     * (optionally) the label of the digit. This is followed by 784 gray-scale
     * values which are used to fill the 28 x 28 image in row major order.
     */

    public Digit(String code) {
        // Tokens are delimited by a comma, followed by one or more spaces.
        // Trailing delimiters do no harm.
        String[] tokens = code.split(",\\s*");
        int n = tokens.length;
        int expected = Constants.DIM * Constants.DIM;
        assert n == expected || n == expected + 1;
        int i = 0;
        if (n == expected + 1)
            label = Integer.parseInt(tokens[i++]);
        for (int y = 0; y < Constants.DIM; y++)
            for (int x = 0; x < Constants.DIM; x++)
                image[x][y] = Integer.parseInt(tokens[i++]);
    }

    /**
     * Creates a B&W digit from the given list of quantized points.
     */

    public Digit(List<Point> quants) {
        for (Point p : quants)
            image[p.x][p.y] = 255;
    }

    /**
     * Returns the label associated with this image.
     */

    public int getLabel() {
        return label;
    }

    /**
     * Classifies this digit by setting its label to the given value.
     */

    public void classify(int d) {
        assert d >= 0 && d <= 9;
        label = d;
    }

    /**
     * Returns true iff the classification of this digit is unknown.
     */

    public boolean isUnknown() {
        return label == UNCLASSIFIED;
    }

    /**
     * Returns the pixel intensity at the (x,y) coordinate.
     */

    public int get(int x, int y) {
        return image[x][y];
    }

    /**
     * Sets the pixel intensity of the (x,y) coordinate to the given value.
     */

    public void put(int val, int x, int y) {
        image[x][y] = val;
    }

    /**
     * TODO
     * <p>
     * The distance between this digit and that digit is measured by the
     * Euclidean algorithm. That is, it is defined to be the square root
     * of the sum of the squares of the differences of corresponding gray-scale
     * values.
     */

    public int distance(Digit that) {
        // TODO
        //Digit dog = new Digit();
        //dog.distance(that);
        // Euclidean Distance is sqrt((point1[0]-point2[0])**2 + (point1[1]-point2[1])**2)

        int holder = 0;
        int euc_dist;
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                int point1 = this.get(i, j);
                int point2 = that.get(i, j);
                holder += (int) (Math.pow(point1-point2,2));
            }
        }

        euc_dist = (int) Math.sqrt(holder);
        return euc_dist;
    }

    /**
     * Marks this digit as selected.
     */

    public void select() {
        select = true;
    }

    /**
     * Unselects this digit.
     */

    public void deselect() {
        select = false;
    }

    /**
     * Returns true iff this digit is selected.
     */

    public boolean isSelected() {
        return select;
    }

    /**
     * Returns the csv encoding of this digit.
     */

    public String getCode() {
        StringBuilder sb = new StringBuilder();
        if (!isUnknown())
            sb.append(label).append(",");
        for (int y = 0; y < Constants.DIM; y++)
            for (int x = 0; x < Constants.DIM; x++)
                sb.append(get(x, y)).append(",");
        return sb.toString();
    }

    /**
     * Uses the given graphics context to draw this digit on the associated
     * panel.
     */

    public void draw(Graphics2D g2) {
        int res = Constants.RESOLUTION_UNIT;
        for (int y = 0; y < Constants.DIM; y++)
            for (int x = 0; x < Constants.DIM; x++) {
                int intensity = get(x, y);
                g2.setColor(new Color(intensity, intensity, intensity));
                g2.fillRect(x * res, y * res, res, res);
            }
        if (select)
            g2.setPaint(Constants.KNOWN);
        else
            g2.setPaint(Constants.UNKNOWN);
        g2.setFont(new Font("Arial", Font.PLAIN, 22));
        g2.drawString(isUnknown() ? "?" : "" + label, 10, 20);
    }

    /**
     * Returns an ASCII Art version of this digit, suitable for printing.
     */

    public String toString() {
        String grayLevels = " .:-=+*#%@";
        int blockSize = (int) Math.ceil(256.0 / grayLevels.length());
        StringBuilder sb = new StringBuilder();
        sb.append("Digit: ").append(isUnknown() ? "Unknown" : "label=" + label)
                .append('\n');
        for (int y = 0; y < Constants.DIM; y++) {
            for (int x = 0; x < Constants.DIM; x++)
                sb.append(grayLevels.charAt(get(x, y) / blockSize));
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Returns a hash code for this digit.
     */

    public int hashCode() {
        return getCode().hashCode();
    }

    public static void main(String[] args) {
        System.out.println(new Digit(Constants.THREE));
    }

}
