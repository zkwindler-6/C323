/**
 * There are two methods for you to implement here.
 * <p>
 * Coord represents an (x,y)-coordinate on a 2D board. The origin, (0,0) is
 * assumed to be in the upper left corner. X-coordinates increase from left
 * to right. Y-coordinates increase from top to bottom. Operations are provided
 * to easily locate neighboring coordinates (in the NSEW compass directions)
 * on a board.
 *
 * @author <Zachary Windler>
 */

public class Coord implements Comparable<Coord> {

    /**
     * The upper left corner of the board.
     */

    public static Coord ORIGIN = new Coord(0, 0);

    private int x, y;

    /**
     * Constructs a new Coord that is a copy of the given Coord.
     */

    public Coord(Coord coord) {
        this(coord.x, coord.y);
    }

    /**
     * Constructs a new Coord representing (x,y).
     */

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the Coord that is directly above (i.e., north of) this one.
     */

    public Coord up() {
        return new Coord(x, y - 1);
    }

    /**
     * Returns the Coord that is directly below (i.e., south of) this one.
     */

    public Coord down() {
        return new Coord(x, y + 1);
    }

    /**
     * Returns the Coord that is immediately to the left (i.e., west)
     * of this one.
     */

    public Coord left() {
        return new Coord(x - 1, y);
    }

    /**
     * Returns the Coord that is immediately to the right (i.e., east)
     * of this one.
     */

    public Coord right() {
        return new Coord(x + 1, y);
    }

    /**
     * TODO
     * <p>
     * Returns true iff this Coord is visible on a board of the given size.
     */

    public boolean onBoard(int size) {
        if (this.x < size && this.y < size && this.x >= 0 && this.y >= 0)
            return true;

        return false;
    }

    /**
     * TODO
     * <p>
     * Returns a list of the immediate board coordinates of this Coord's north,
     * south, east, and west neighbors.
     */

    public List<Coord> neighbors(int size) {
        //point of size???

        List<Coord> neighbor = new DoublyLinkedList<>();

        if (this.up().x < size && this.up().y < size && this.up().x >= 0 && this.up().y >= 0)
            neighbor.add(this.up());

        if (this.down().x < size && this.down().y < size && this.down().x >= 0 && this.down().y >= 0)
            neighbor.add(this.down());

        if (this.right().x < size && this.right().y < size && this.right().x >= 0 && this.right().y >= 0)
            neighbor.add(this.right());

        if (this.left().x < size && this.left().y < size && this.left().x >= 0 && this.left().y >= 0)
            neighbor.add(this.left());

        return neighbor;
    }

    /**
     * Returns true iff the (x,y)-coordinates of the given object match
     * this Coord's (x,y)-coordinates.
     */

    public boolean equals(Object obj) {
        if (obj instanceof Coord) {
            Coord that = (Coord) obj;
            return that.x == this.x && that.y == this.y;
        }
        return false;
    }

    /**
     * Coords are ordered first on the x-coordinate and then on the y-coordinate.
     */

    public int compareTo(Coord that) {
        if (this.x == that.x)
            return this.y - that.y;
        return this.x - that.x;
    }

    /**
     * Returns the x-coordinate.
     */

    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate.
     */

    public int getY() {
        return y;
    }

    /**
     * Returns this Coord as a string of the form (x, y).
     */

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Simple testing.
     */

    public static void main(String... args) {
        Coord someCoord = new Coord(2, 1);
        System.out.println("someCoord = " + someCoord);
        System.out.println("someCoord.onBoard(4) = " + someCoord.onBoard(4));
        System.out.println("neighbors on a 3x3 board = " + someCoord.neighbors(3));
        System.out.println("neighbors on a 4x4 board = " + someCoord.neighbors(4));
        System.out.println();

        //is it okay to have negative coordinates. for example, outside of the board
        someCoord = ORIGIN;
        System.out.println("someCoord = " + someCoord);
        System.out.println("someCoord.onBoard(3) = " + someCoord.onBoard(3));
        System.out.println("neighbors on a 3x3 board = " + someCoord.neighbors(3));
        System.out.println("neighbors on a 1x1 board = " + someCoord.neighbors(1));
        System.out.println();

        someCoord = new Coord(5, 5);
        System.out.println("someCoord = " + someCoord);
        System.out.println("someCoord.onBoard(5) = " + someCoord.onBoard(5));
        System.out.println("neighbors on a 3x3 board = " + someCoord.neighbors(3));
        System.out.println("neighbors on a 6x6 board = " + someCoord.neighbors(6));
    }
}
