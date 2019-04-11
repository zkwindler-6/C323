import java.util.Iterator;

/**
 * TODO #6
 * <p>
 * Most of the work for this project involves implementing the
 * connectAllWires() method in this class. Feel free to create
 * any helper methods that you deem necessary.
 * <p>
 * Your goal is to come up with an efficient algorithm that will
 * find a layout that connects all the wires (if one exists) while
 * attempting to minimize the overall wire length. Note that you
 * are expected to describe your algorithm in a report in the
 * Driver comment.
 */

public class PathFinder {

    /**
     * TODO
     * <p>
     * Lays out a path connecting each wire on the chip, and then
     * returns a map that associates a wire id numbers to the paths
     * corresponding to the connected wires on the grid. If it is
     * not possible to connect the endpoints of a wire, then there
     * should be no association for the wire id# in the result.
     */

    public static Map<Integer, Path> connectAllWires(Chip chip) {
        Map<Integer, Path> layout = new HashMap<>();

        //use pq
        //fill pq with the wires from chip
        //then use those wires in the BFS (path)
        //use the id and path for the layout
        //use id, path, and wire for the layout method

        PriorityQueue<Wire> pq = new PriorityQueue<>((x, y) -> x.manhattan() - y.manhattan());
        for (Wire x : chip.wires) {
            pq.offer(x);
        }

        for (int i = 0; i < chip.wires.size(); i++) {
            Wire x = pq.poll();
            Path path = BFS(chip, x);
            if (path.length() == 1 && !(x.from.equals(x.to)))
                throw new NoPathException(x);
            layout.put(x.wireId, path);
            //something else here
            chip.layoutWire(x.wireId, path);
        }

        return layout;
        //throw new NoPathException(null);
    }

    //addfront lab 13
    //add to dll
    //changed head to head.next

    // 1. check if curr = final
    // 2. get currs neighbors
    // 3. check if the neighbors are free
    // 4. check if they arent in visited
    // 5. add to queue

    public static Path BFS(Chip chip, Wire wire) {
        if (wire.from.equals(wire.to))
            return new Path(wire);

        PriorityQueue<Coord> queue = new PriorityQueue<>((x, y) ->
                (int) ((int) Math.sqrt(Math.pow(wire.to.x - x.x, 2) + Math.pow(wire.to.y - x.y, 2))
                        - Math.sqrt(Math.pow(wire.to.x - y.x, 2) + Math.pow(wire.to.y - y.y, 2))));

        HashMap<Coord, Coord> pathFinder = new HashMap<>();
        Path path = new Path(wire);
        queue.offer(wire.from);

        while (!queue.isEmpty()) {
            Coord curr = queue.poll();
            if (curr.equals(wire.to)) {
                while (!curr.equals(wire.from)) {
                    path.addSecond(curr);
                    curr = pathFinder.get(curr);
                }
                return path;
            }

            for (Coord co : curr.neighbors(chip.dim)) {
                if ((chip.isFree(co) || chip.isWired(co, wire))&& pathFinder.get(co) == null) {
                    queue.offer(co);
                    pathFinder.put(co, curr);
                }
            }
        }

        return path;
    }

    /**
     * TODO
     * <p>
     * Returns the sum of the lengths of all non-null paths in the given layout.
     */

    public static int totalWireUsage(Map<Integer, Path> layout) {

        int sum = 0;
//        for (int i = 0; i < layout.size(); i++) {
//            if (layout.get(i) != null)
//                sum += layout.get(i).length();
//        }
        Iterator<Path> it = layout.values();
        while (it.hasNext()) {
            Path path = it.next();
            if (path != null)
                sum += path.length();
        }
        return sum;
    }

}

class NoPathException extends RuntimeException {
    public Wire wire;

    public NoPathException(Wire wire) {
        this.wire = wire;
    }
}

