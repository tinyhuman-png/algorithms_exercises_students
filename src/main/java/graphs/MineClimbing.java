package graphs;

//feel free to import anything here


import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * You just bought yourself the latest game from the Majong™
 * studio (recently acquired by Macrosoft™): MineClimb™.
 * In this 3D game, the map is made up of size 1
 * dimensional cube blocks, aligned on a grid,
 * forming vertical columns of cubes.
 * There are no holes in the columns, so the state
 * of the map can be represented as a matrix M of size n⋅m
 * where the entry M_{i,j} is the height of
 * the cube column located at i,j (0 ≤ i < n, 0 ≤ j < m).
 * You can't delete or add cubes, but you do have
 * climbing gear that allows you to move from one column to another
 * (in the usual four directions (north, south, east, west),
 * but not diagonally). The world of MineClimb™ is round:
 * the position north of (0,j) is (n-1,j), the position
 * west of (i,0) is (i,m-1) , and vice versa.
 * <p>
 * The time taken to climb up or down a column depends on
 * the difference in height between the current column and the next one.
 * Precisely, the time taken to go from column (i,j)
 * to column (k,l) is |M_{i,j}-M_{k,l}|
 * <p>
 * Given the map of the mine, an initial position
 * and an end position, what is the minimum time needed
 * to reach the end position from the initial position?
 */
public class MineClimbing {


    /**
     * Returns the minimum distance between (startX, startY) and (endX, endY), knowing that
     * you can climb from one mine block (a,b) to the other (c,d) with a cost Math.abs(map[a][b] - map[c][d])
     * <p>
     * Do not forget that the world is round: the position (map.length,i) is the same as (0, i), etc.
     * <p>
     * map is a matrix of size n times m, with n,m > 0.
     * <p>
     * 0 <= startX, endX < n
     * 0 <= startY, endY < m
     */
    public static int best_distance(int[][] map, int startX, int startY, int endX, int endY) {
        // shortest path with costs, gotta use Dijkstra I guess
        int rows = map.length;
        int columns = map[0].length;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        HashSet<Point> redondance = new HashSet<>();  //IMPORTANT

        int[][] distTo = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                distTo[i][j] = Integer.MAX_VALUE;
            }
        }
        distTo[startX][startY] = 0;

        PriorityQueue<Point> pq = new PriorityQueue<>();
        pq.add(new Point(startX, startY, 0));

        while (!pq.isEmpty()) {
            Point v = pq.poll();
            //comme la pq de Java on ne sait pas modifier un elem qui est dedans, quand on veut modifier une distance pour un noeud qui est deja dans la pq, on l'ajoute juste avec la meilleure distance;
            if (redondance.contains(v)) continue;  //DONC il faut skip quand on rencontre le noeud outdated avec l'ancienne distance aka quand on a deja rencontre ce noeud avant
            redondance.add(v);
            int vX = v.x;
            int vY = v.y;
            for (int[] direction : directions) {
                int wX = vX + direction[0];
                if (wX < 0) wX = rows -1;
                else if (wX > rows -1) wX = 0;

                int wY = vY + direction[1];
                if (wY < 0) wY = columns -1;
                else if (wY > columns -1) wY = 0;

                int cost = Math.abs(map[vX][vY] - map[wX][wY]);
                if (distTo[wX][wY] > distTo[vX][vY] + cost) {
                    distTo[wX][wY] = distTo[vX][vY] + cost;
                    pq.add(new Point(wX, wY, distTo[wX][wY]));
                }

            }
        }

         return distTo[endX][endY];
    }

    private static class Point implements Comparable<Point>{
        public int x, y, distance;
        public Point(int coordX, int coordY, int distance) {
            this.x = coordX;
            this.y = coordY;
            this.distance = distance;
        }

        @Override
        public int compareTo(Point o) {
            return this.distance - o.distance;  //ATTENTION comparer selon les distances pour que dans la priority queue la meilleure distance sorte en premier
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + this.x;
            result = 31 * result + this.y;
            return result;
        }

    }

}
