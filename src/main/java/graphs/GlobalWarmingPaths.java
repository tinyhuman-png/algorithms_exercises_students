package graphs;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,5,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Given a global water level, all positions in the matrix
 * with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3,
 * all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4),(5),(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is
 * a method that find a safe-path between
 * two positions (row,column) on the matrix.
 * The path assume you only make horizontal or vertical moves
 * but not diagonal moves.
 *
 * For a water level of 4, the shortest path
 * between (1,0) and (1,3) is
 * (1,0) -> (2,0) -> (2,1) -> (2,2) -> (2,3) -> (1,3)
 *
 *
 * Complete the code below so that the {@code  shortestPath} method
 * works as expected
 */
public class GlobalWarmingPaths {

    int waterLevel;
    int [][] altitude;

    Set<Point> safePoints;

    public GlobalWarmingPaths(int[][] altitude, int waterLevel) {
        this.waterLevel = waterLevel;
        this.altitude = altitude;

        this.safePoints = new HashSet<>();
        for (int i = 0; i < this.altitude.length; i++) {
            for (int j = 0; j < this.altitude[0].length; j++) {
                if (this.altitude[i][j] > this.waterLevel) {
                    this.safePoints.add(new Point(i, j));
                }
            }
        }
    }


    /**
     * Computes the shortest path between point p1 and p2
     * @param p1 the starting point
     * @param p2 the ending point
     * @return the list of the points starting
     *         from p1 and ending in p2 that corresponds
     *         the shortest path.
     *         If no such path, an empty list.
     */
    public List<Point> shortestPath(Point p1, Point p2) {
        // expected time complexity O(n^2)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        List<Point> path = new ArrayList<>();
        boolean test = (new Point(1, 0)).equals(p1);
        if (!safePoints.contains(p1) || !safePoints.contains(p2)) return path;

        // I'd say use bfs
        HashSet<Point> visited = new HashSet<>();
        HashMap<Point, Point> edgeTo = new HashMap<>();  //fait le lien avec le noeud precedant qui nous a permis d'arriver au noeud actuel
        Queue<Point> queue = new LinkedList<>();

        visited.add(p1);
        edgeTo.put(p1, null);
        queue.add(p1);

        while (!queue.isEmpty()) {
            Point v = queue.poll();
            for (int[] direction : directions) {
                int newI = v.x + direction[0];
                int newJ = v.y + direction[1];
                Point neighbour = new Point(newI, newJ);

                if (this.safePoints.contains(neighbour) && !visited.contains(neighbour)) {
                    visited.add(neighbour);  //ATTENTION on marque le noeud comme visited AVANT de le mettre dans la queue, pas en le sortant de la queue
                    edgeTo.put(neighbour, v);  //bien update edgeTo ici tant qu'on sait de quel noeud neighbour est le voisin
                    queue.add(neighbour);
                }
            }
        }

        Stack<Point> stack = new Stack<>();  //bien utiliser un stack pour que la reconstruction du chemin soit plus facile
        if (edgeTo.containsKey(p2)) {
            stack.push(p2);
            Point prev = edgeTo.get(p2);
            while(prev != null && !prev.equals(p1)) {  //faire le check pour null parce qu'on a l'entree (p1, null) et si p2 = p1 prev = null -> probleme de pas check
                stack.push(prev);
                prev = edgeTo.get(prev);
            }
            if (!p2.equals(p1)) stack.push(p1);  //si p2 = p1 on veut une seule fois le point dans la liste
        }

        while(!stack.isEmpty()) {
            path.add(stack.pop());
        }

        return path;

    }

    /**
     * This class represent a point in a 2-dimension discrete plane.
     * This is used to identify the cells of a grid
     * with X = row, Y = column
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

        // utiliser contains etc. dans des Hashmap/HashSet de ce type d'objet -> non seulement override la methode equals mais AUSSI LA METHODE HASHCODE sinon ca ne fonctionne pas bien
        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + x;
            result = 31 * result + y;
            return result;
        }
    }
}
