/**
 * This is a simple structure to encapsulate a numbered wire.
 * <p>
 * TODO #5: Implement separation() and reverse().
 */

public class Wire {
    protected Coord from, to;
    protected int wireId;

    /**
     * Creates a new wire between grid cells at the given coordinates.
     */

    public Wire(int wireId, Coord from, Coord to) {
        this.wireId = wireId;
        this.from = from;
        this.to = to;
    }

    /**
     * Creates a new wire between grid cells at the given coordinates.
     */

    public Wire(int wireNumber, int x1, int y1, int x2, int y2) {
        this(wireNumber, new Coord(x1, y1), new Coord(x2, y2));
    }

    /**
     * TODO
     * <p>
     * Returns the minimal distance separating the two endpoints of this wire.
     * The result corresponds to the length of the shortest connecting wire.
     * This is also known of as the Manhattan Distance between two points on
     * a grid.
     * <p>
     * Note: This might be a useful property of a wire to incorporate into
     * a heuristic for laying out the wires. That's up to you. Feel free to
     * experiment with other properties to see how they work in practice.
     */

    public int separation() {
        return Math.abs(to.x - from.x) + Math.abs(to.y - from.y);
    }

    public int manhattan() {
        return Math.abs(to.x - from.x) + Math.abs(to.y - from.y);
    }

    /**
     * TODO
     * <p>
     * Reverses the starting and ending points of this wire.
     */

    public void reverse() {
        Coord temp = from;
        from = to;
        to = temp;
    }

    /**
     * Returns a textual representation of this wire.
     */

    public String toString() {
        return String.format("Wire[%d,%s,%s]", wireId, from, to);
    }
}
