package graphs;


import java.util.HashMap;
import java.util.HashSet;

/**
 * In this exercise, we revisit the GlobalWarming
 * class from the sorting package.
 * You are still given a matrix of altitude in
 * parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to,
 * the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water
 * level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 * 
 * If we replace the submerged entries
 * by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implement two methods that
 * can answer the following questions:
 *      1) Are two entries on the same island?
 *      2) How many islands are there
 *
 * Two entries above the water level are
 * connected if they are next to each other on
 * the same row or the same column. They are
 * **not** connected **in diagonal**.
 * Beware that the methods must run in O(1)
 * time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class
 * in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming {

    private final int[][] altitude;
    private final int waterlevel;
//    private final HashSet<Point> safePoints;
//    private int count;
//    private HashMap<Point, Integer> id;

    private final int[] id;
    private int count;


    /**
     * Constructor. The run time of this method is expected to be in 
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    public GlobalWarming(int [][] altitude, int waterLevel) {
        this.altitude = altitude;
        this.waterlevel = waterLevel;
//        this.safePoints = new HashSet<>();
//
//        for (int i = 0; i < this.altitude.length; i++) {
//            for (int j = 0; j < this.altitude[0].length; j++) {
//                if (this.altitude[i][j] > this.waterlevel) {
//                    this.safePoints.add(new Point(i, j));
//                }
//            }
//        }
//
//        this.id = new HashMap<>();
//        this.count = 0;
//        for (Point point : this.safePoints) {
//            if (!id.containsKey(point)) {
//                dfs(point);
//                count ++;
//            }
//        }

        int rows = this.altitude.length;
        int columns = this.altitude[0].length;
        this.id = new int[rows * columns];  //so there is place for every coordinate in the array
        for (int i = 0; i < rows * columns; i++) {
            this.id[i] = (rows * columns) +1;   //this will make it unnecessary to have a boolean marked array because it is impossible to have (rows * columns) +1 island so if the coord is already processed it will be <= (rows * columns) +1
        }
        this.count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                analyseCoord(i, j);
                if (this.id[i*rows + j] == this.count) this.count ++;  //increment count only if the coordinate is a safe point
            }
        }


    }

//    private void dfs(Point source) {
//        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
//        int sourceX = source.x;
//        int sourceY = source.y;
//
//        id.put(source, this.count);
//        for (int[] direction : directions) {
//            Point neighbour = new Point(sourceX + direction[0], sourceY + direction[1]);
//            if(this.safePoints.contains(neighbour) && !this.id.containsKey(neighbour)) {
//                dfs(neighbour);
//            }
//        }
//
//    }

    private void analyseCoord(int x, int y) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int rows = this.altitude.length;
        int columns = this.altitude[0].length;

        if(this.altitude[x][y] <= this.waterlevel) {
            this.id[x*rows + y] = -1;
        } else {
            if (this.id[x*rows + y] == (rows * columns) +1) this.id[x*rows + y] = this.count;  //update the island id only if it was not already visited before
            else return;  //if the island was visited before, no need to visit it again
            for (int[] direction : directions) {
                int nextX = x + direction[0];
                int nextY = y + direction[1];

                if(nextX >= 0 && nextX < rows && nextY >= 0 && nextY < columns && this.id[nextX*rows + nextY] == (rows * columns) +1) {
                    analyseCoord(nextX, nextY);
                }
            }
        }
    }

    /**
     * Returns the number of island
     *
     * Expected time complexity O(1)
     */
    public int nbIslands() {
        return this.count;
    }

    /**
     * Return true if p1 is on the same island as p2, false otherwise
     *
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    public boolean onSameIsland(Point p1, Point p2) {
//        if (!id.containsKey(p1) || !id.containsKey(p2)) return false;
//        return id.get(p1).equals(id.get(p2));

        int rows = this.altitude.length;
        if (id[p1.getX()*rows + p1.getY()] == -1 || id[p2.getX()*rows + p2.getY()] == -1) return false;
        return id[p1.getX()*rows + p1.getY()] == id[p2.getX()*rows + p2.getY()];
    }


    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + x;
            result = 31 * result + y;
            return result;
        }
    }
}
