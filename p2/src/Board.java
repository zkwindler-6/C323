import javax.swing.plaf.synth.SynthOptionPaneUI;

/**
 * There is MUCH for you to do here.
 * <p>
 * A Board represents the current state of the game. Boards know their
 * dimension, the collection of tiles that are inside the current flooded
 * region, and those tiles that are on the outside.
 *
 * @author <Zachary Windler>
 */

public class Board {
    List<Tile> inside, outside;
    private int size;

    /**
     * Constructs a square game board of the given size, initializes the list of
     * inside tiles to include just the tile in the upper left corner, and puts
     * all the other tiles in the outside list.
     */

    public Board(int size) {
        // A tile is either inside or outside the current flooded region.
        inside = new DoublyLinkedList<>();
        outside = new DoublyLinkedList<>();
        this.size = size;
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                Coord coord = new Coord(x, y);
                outside.add(new Tile(coord));
            }
        // Move the corner tile into the flooded region.
        Tile corner = get(Coord.ORIGIN);
        outside.remove(corner);
        inside.add(corner);
    }

    /**
     * TODO
     * <p>
     * Returns the tile at the specified coordinate on this board.
     *
     * @throws CoordOutOfBoundsException if no such tile is found at the
     *                                   given coordinate.
     */

    public Tile get(Coord coord) {

        for (Tile t : inside) {
            if (t.getCoord().equals(coord))
                return t;
        }

        for (Tile t2 : outside) {
            if (t2.getCoord().equals(coord))
                return t2;
        }

        throw new CoordOutOfBoundsException();
    }

    /**
     * Returns the size of this board.
     */

    public int getSize() {
        return size;
    }

    /**
     * TODO
     * <p>
     * Returns true iff all tiles on the board have the same color.
     * Must run in O(1) time.
     */

    public boolean fullyFlooded() {
        return inside.size() == size * size;
    }

    /**
     * TODO
     * <p>
     * Updates this board by changing the color of the current flood region
     * and extending its reach. Make this as efficient as possible.
     */

    public void flood(WaterColor color) {

        for (Tile t : inside) {
            t.setColor(color);
        }
        List<Tile> frontier;

        do {
            frontier = new DoublyLinkedList<>();

            for (Tile t : inside) {

                List<Coord> neighbors = t.getCoord().neighbors(size);

                for (Tile t2 : outside) {
                    if (neighbors.contains(t2.getCoord())) {
                        WaterColor col = t2.getColor();
                        if (col.equals(color))
                            frontier.add(t2);
                    }
                }
            }

            for (Tile t3 : frontier) {
                if (outside.remove(t3)){
                    inside.add(t3);
                }
            }
        } while (!frontier.isEmpty());
    }

    /**
     * TODO
     * <p>
     * Returns the "best" WaterColor for the next move.
     * <p>
     * My algorithm gets the tiles that are in the frontier
     * and then counts the colors that associate with the tiles.
     * Then I check which color has the most occurrences in the frontier and
     * return that color.
     */

    public WaterColor suggest() {
        //have counter to count how many neighbors are that color

        PairColor blue = new PairColor(WaterColor.BLUE, new DoublyLinkedList());
        PairColor red = new PairColor(WaterColor.RED, new DoublyLinkedList());
        PairColor yellow = new PairColor(WaterColor.YELLOW, new DoublyLinkedList());
        PairColor cyan = new PairColor(WaterColor.CYAN, new DoublyLinkedList());
        PairColor pink = new PairColor(WaterColor.PINK, new DoublyLinkedList());

        List<Tile> frontier;

        do {
            frontier = new DoublyLinkedList<>();

            for (Tile t : inside) {

                List<Coord> neighbors = t.getCoord().neighbors(size);

                for (Tile t2 : outside) {
                    WaterColor col = t2.getColor();
                    if (neighbors.contains(t2.getCoord())) {
                        if (col.equals(WaterColor.BLUE))
                            if (!blue.count.contains(t2))
                                blue.count.add(t2);
                        if (col.equals(WaterColor.RED))
                            if (!red.count.contains(t2))
                                red.count.add(t2);
                        if (col.equals(WaterColor.YELLOW))
                            if (!yellow.count.contains(t2))
                                yellow.count.add(t2);
                        if (col.equals(WaterColor.PINK))
                            if (!pink.count.contains(t2))
                                pink.count.add(t2);
                        if (col.equals(WaterColor.CYAN))
                            if (!cyan.count.contains(t2))
                                cyan.count.add(t2);
                    }
                }
            }
        } while (!frontier.isEmpty());

        if (red.count.size() >= yellow.count.size() && red.count.size() >= blue.count.size() && red.count.size() >= cyan.count.size() && red.count.size() >= pink.count.size())
            return WaterColor.RED;
        else if (blue.count.size() >= yellow.count.size() && blue.count.size() >= red.count.size() && blue.count.size() >= cyan.count.size() && blue.count.size() >= pink.count.size())
            return WaterColor.BLUE;
        else if (yellow.count.size() >= red.count.size() && yellow.count.size() >= blue.count.size() && yellow.count.size() >= cyan.count.size() && yellow.count.size() >= pink.count.size())
            return WaterColor.YELLOW;
        else if (cyan.count.size() >= yellow.count.size() && cyan.count.size() >= blue.count.size() && cyan.count.size() >= red.count.size() && cyan.count.size() >= pink.count.size())
            return WaterColor.CYAN;
        else if (pink.count.size() >= yellow.count.size() && pink.count.size() >= blue.count.size() && pink.count.size() >= cyan.count.size() && pink.count.size() >= red.count.size())
            return WaterColor.PINK;

        WaterColor cornerColor = inside.get(0).getColor();
        return WaterColor.pickOneExcept(cornerColor);
    }

    /**
     * Returns a string representation of this board. Tiles are given as their
     * color names, with those inside the flooded region written in uppercase.
     */

    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Tile curr = get(new Coord(x, y));
                WaterColor color = curr.getColor();
                ans.append(String.format("%-8s",
                        inside.contains(curr) ?
                                color.toString().toUpperCase() :
                                color));
            }
            ans.append("\n");
        }
        return ans.toString();
    }

    /**
     * Simple testing.
     */
    public static void main(String... args) {
        // Print out boards of size 1, 2, ..., 5
        int n = 5;
        for (int size = 1; size <= n; size++) {
            Board someBoard = new Board(size);
            System.out.println(someBoard);
        }
    }
}

class CoordOutOfBoundsException extends IndexOutOfBoundsException {

}






