import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * TODO #2: Complete the Chip(File) constructor. When you are done, go
 * to Driver.
 * <p>
 * A chip is printed on a rectangular circuit board. Components are
 * "laid out" on the chip in a grid of cells. A cell is addressed by
 * its (x,y) coordinate in the usual way. Some rectangular (possibly
 * overlapping) regions on the grid are covered by obstacles.
 * <p>
 * Pairs of cells to be connected by a wire are identified by a unique
 * wire id. The "Wire Routing Problem" is to find paths connecting all
 * such pairs so that the total length of all wires is minimized.
 * Wires may not overlap and may not pass over or through an obstacle.
 */

public class Chip {
    protected Dimension dim;
    protected List<Obstacle> obstacles;
    protected List<Wire> wires;
    protected Map<Coord, Integer> grid;

    /**
     * Creates a new chip of the given dimensions with the specified obstacles
     * and wire endpoints.
     */

    public Chip(Dimension dim, List<Obstacle> obstacles, List<Wire> wires) {
        this.dim = dim;
        this.obstacles = new DoublyLinkedList<>(obstacles);
        this.wires = new DoublyLinkedList<>(wires);
        initLayout();
    }

    /**
     * TODO
     * <p>
     * This method constructs a chip from a description in an input file.
     * Most of this method is already written for you. You need to fill in
     * the code to read the number of wires and the description of each
     * wire from the file. You are also responsible for creating and
     * populating the wires instance variable (which will be used by the
     * layout() method to initialize the grid).
     */

    public Chip(File file) {
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Read size data and initialize dim.
            int width = Integer.parseInt(bufferedReader.readLine());
            int height = Integer.parseInt(bufferedReader.readLine());
            dim = new Dimension(width, height);

            // Read the obstacle data and initialize obstacles.
            int numObstacles = Integer.parseInt(bufferedReader.readLine());
            obstacles = new DoublyLinkedList<>();
            for (int i = 0; i < numObstacles; i++) {
                String line = bufferedReader.readLine();
                String tokens[] = line.split(" ");
                obstacles.add(new Obstacle(new Coord(tokens[0], tokens[1]),
                        new Coord(tokens[2], tokens[3])));
            }
            //********** don't change anything above this line **********

            /**
             * TODO: Read the wire data and create/initialize wires.
             */

            int numWires = Integer.parseInt(bufferedReader.readLine());
            wires = new DoublyLinkedList<>();
            for (int i = 0; i < numWires; i++) {
                String line = bufferedReader.readLine();
                String wireTokens[] = line.split(" ");
                wires.add(new Wire(i + 1, new Coord(wireTokens[0], wireTokens[1]),
                        new Coord(wireTokens[2], wireTokens[3])));
            }


            //********** don't change anything below this line **********
            bufferedReader.close();
            initLayout();
        } catch (FileNotFoundException e) {
            System.out.println("The input file, " + file.getName() + ", is not found.\n.");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Lays out the initial grid.
     */

    public void initLayout() {
        // Build an empty grid.
        grid = new HashMap<>();
        for (int y = 0; y < dim.height; y++)
            for (int x = 0; x < dim.width; x++)
                grid.put(new Coord(x, y), Constants.FREE);
        // Lay out the obstacles on the grid.
        for (Obstacle obs : obstacles) {
            for (int y = obs.upperLeft.getY(); y <= obs.lowerRight.getY(); y++)
                for (int x = obs.upperLeft.getX(); x <= obs.lowerRight.getX(); x++)
                    grid.put(new Coord(x, y), Constants.OBSTACLE);
        }
        // Lay out the wire endpoints on the grid.
        for (Wire wire : wires) {
            grid.put(wire.from, wire.wireId);
            grid.put(wire.to, wire.wireId);
        }
    }

    /**
     * Lays out the wire with the given id on the grid along the specified path.
     */

    public void layoutWire(int wireId, Path path) {
        for (Coord coord : path)
            grid.put(coord, wireId);
    }

    /**
     * Returns true iff the cell at the specified coord has been reserved for
     * the given wire.
     */

    public boolean isWired(Coord coord, Wire wire) {
        return grid.get(coord) == wire.wireId;
    }

    /**
     * Returns true iff the cell at the specified coord is in a region
     * occupied by an obstacle.
     */

    public boolean isObstacle(Coord coord) {
        return grid.get(coord) == Constants.OBSTACLE;
    }

    /**
     * Returns true iff the cell at the specified coord is free.
     */

    public boolean isFree(Coord coord) {
        return grid.get(coord) == Constants.FREE;
    }

    /**
     * Marks the cell on the grid at coordinate coord with the given value.
     */

    public void mark(Coord coord, int value) {
        grid.put(coord, value);
    }

    /**
     * Returns the dimension of this grid.
     */

    public Dimension size() {
        return new Dimension(dim);
    }

    /**
     * Returns a list of coordinates on this grid that are neighbors of the
     * given coord.
     */

    public List<Coord> neighbors(Coord coord) {
        return coord.neighbors(dim);
    }

    /**
     * Returns a pretty version of this grid.
     */

    public String toString() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat formatter = new DecimalFormat("####");
        for (int y = 0; y < dim.height; y++) {
            for (int x = 0; x < dim.width; x++)
                sb.append(String.format("%5s",
                        formatter.format(grid.get(new Coord(x, y)))));
            sb.append('\n');
        }
        return sb.toString();
    }
}

