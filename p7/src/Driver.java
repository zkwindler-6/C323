import java.io.File;
import javax.swing.SwingUtilities;

/**
 * This is the main entry point for the application. When run, a GUI
 * will appear showing the chip with the wires connected. The layout
 * computed by PathFinder will be used unless it returns an empty
 * layout. In that case, a fixedLayout will be used (if it exists for
 * the chip).
 * <p>
 * TODO #3: Follow the instructions on Canvas to run the GUI. When you
 * are done, go to Obstacle.
 * <p>
 * TODO #7: Write your report here:
 *
 * 1. i check the starting coordinates neighbors and then those neighbors neighbors
 *    until i find the end coordinates
 *
 * 2. BFS(Chip chip, Wire wire) {
 *     queue <- priorityqueue based on distance
 *     pathFinder <- hashmap of Coord and Coord
 *     path <- new Path of wire
 *
 *     queue.offer(wire.from)
 *     while queue is not empty:
 *           curr <- queue.poll()
 *           if curr == wire.to
 *                while curr doesnt equal wire.from
 *                    path.addSecond(curr)
 *                    curr <- pathFinder.get(curr)
 *                return path
 *
 *           for each coord in curr's neighbors
 *                 if coord is free or wired and if its not null
 *                      queue.offer(coord)
 *                      pathFinder.put(coord, curr)
 *
 * }
 *
 * 3. i used a DLL and a HashMaps. The DLL just acts as the queue in the BFS.
 *    The HashMap is used to traceback the path. It is filled with 2 coordinates.
 *    the current one and the parent one (how i got to that current coord)
 *
 * 4. added an addSecond for DLL class so i can skip over the first coord (from) and
 *    add on to the rest of the list.
 *
 * 5. data            success      failure
 *    big_01             X
 *    big_02             X
 *    big_03             X
 *    big_04             X
 *    huge_01            X
 *    medium_01          X
 *    medium_02          X
 *    medium_03          X
 *    medium_04          X
 *    nopath_01          X
 *    nopath_02          X
 *    small_01           X
 *    small_02           X
 *    small_03           X
 *    small_04           X
 *    small_05           X
 *    small_06           X
 *    small_07           X
 *    small_08           X
 *    small_09           X
 *    small_10           X
 *    small_11                          X
 *    small_12                          X
 */

public class Driver {

    public static void main(String... args) {
        String chipName = "medium_04";  // change this name for different chips

        System.out.println(Constants.TITLE);
        System.out.println(String.format("Start the GUI on %s ...", chipName));
        String fileName = String.format("%s/%s%s", Constants.INPUTS_FOLDER,
                chipName, Constants.EXTENSION);
        File file = new File(fileName);
        Chip chip = new Chip(file);
        Map<Integer, Path> layout = getLayout(chip);

        int[][] data;
        if (layout.isEmpty() && (data = getData(chipName)) != null) {
            /**
             * data[i] contains the flattened coordinates of wire (i+1)'s path.
             * Manually lay out the wires for this chip as specified by data.
             */
            for (int i = 0; i < data.length; i++) {
                Path p = new Path();
                for (int j = 0; j < data[i].length; j += 2) {
                    Coord coord = new Coord(data[i][j], data[i][j + 1]);
                    p.add(coord);
                    chip.grid.put(coord, i + 1);
                }
                layout.put(i + 1, p);
            }
        }
        SwingUtilities.invokeLater(() -> new GUI(chip, layout, fileName));
    }

    /**
     * Use PathFinder to layout the wires on the chip.
     */

    public static Map<Integer, Path> getLayout(Chip chip) {
        Map<Integer, Path> layout;
        try {
            layout = PathFinder.connectAllWires(chip);
        } catch (NoPathException ex) {
            System.out.println(String.format("Could not layout %s.", ex.wire));
            layout = new HashMap<>();
        }
        return layout;
    }

    /**
     * Returns a data description of the wire connections for the chip with
     * the given name, if one exists in the database. Otherwise, returns null.
     */

    public static int[][] getData(String chipName) {
        Map<String, int[][]> database = new HashMap<>();
        database.put("small_02", new int[][]{
                {0, 0, 1, 0, 1, 1}, // 1
        });
        database.put("small_09", new int[][]{
                {1, 0, 2, 0, 3, 0, 3, 1, 3, 2, 3, 3, 3, 4, 2, 4, 1, 4}, // 1
                {0, 1, 1, 1, 2, 1}, // 2
                {0, 2, 1, 2, 2, 2}, // 3
                {0, 3, 1, 3, 2, 3}, // 4
        });
        database.put("medium_03", new int[][]{
                {10, 12, 10, 13, 10, 14, 10, 15, 10, 16, 10, 17, 10, 18, 10, 19, 10,
                        20, 10, 21}, // 1
                {12, 10, 13, 10, 14, 10, 15, 10, 16, 10, 17, 10, 18, 10, 19, 10, 20,
                        10, 21, 10}, // 2
                {23, 12, 23, 13, 23, 14, 23, 15, 23, 16, 23, 17, 23, 18, 23, 19, 23,
                        20, 23, 21}, // 3
                {12, 23, 13, 23, 14, 23, 15, 23, 16, 23, 17, 23, 18, 23, 19, 23, 20,
                        23, 21, 23}, // 4
                {11, 13, 11, 14, 11, 15}, // 5
                {22, 13, 22, 14, 22, 15}, // 6
                {11, 18, 11, 19, 11, 20}, // 7
                {22, 18, 22, 19, 22, 20}, // 8
                {13, 11, 14, 11, 15, 11}, // 9
                {18, 11, 19, 11, 20, 11}, // 10
                {13, 22, 14, 22, 15, 22}, // 11
                {18, 22, 19, 22, 20, 22}, // 12
                {17, 13, 17, 14, 17, 15}, // 13
                {16, 18, 16, 19, 16, 20}, // 14
                {13, 16, 14, 16, 15, 16}, // 15
                {18, 17, 19, 17, 20, 17}, // 16
                {12, 13, 12, 14, 12, 15, 13, 15, 14, 15, 15, 15, 16, 15}, // 17
                {18, 16, 19, 16, 19, 15, 20, 15, 20, 14, 20, 13, 21, 13}, // 18
                {13, 21, 13, 20, 13, 19, 14, 19, 15, 19, 15, 18, 15, 17}, // 19
                {17, 18, 17, 19, 17, 20, 18, 20, 18, 21, 19, 21, 20, 21}, // 21
                {10, 10}, // 21
        });
        return database.get(chipName);
    }
}