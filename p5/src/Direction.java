/**
 * There's nothing for you to do here.
 *
 * We use this enumeration to represent the direction of the parent result
 * along the optimal path. If the largest score comes from an M operation
 * (i.e., a match or a mismatch), then the direction is DIAGONAL. If the
 * largest score comes from an I operation (i.e., a gap is inserted into
 * strand x), then the direction to the parent is UP. If the largest score
 * comes from a D operation (i.e., a gap is inserted into strand y), then
 * the direction to the parent is LEFT.
 */

public enum Direction {
  DIAGONAL {
    public String toString() {
      return "diag";
    }
  },
  LEFT {
    public String toString() {
      return "left";
    }
  },
  UP {
    public String toString() {
      return "up";
    }
  },
  NONE {
    public String toString() {
      return "";
    }
  };
}
