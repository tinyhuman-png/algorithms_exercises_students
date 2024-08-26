package graphs;

import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * Let's consider a forest represented as a 2D grid.
 * Each cell of the grid can be in one of three states:
 *
 * 0 representing an empty spot.
 * 1 representing a tree.
 * 2 representing a burning tree (indicating a wildfire).
 *
 * The fire spreads from a burning tree to its four neighboring cells (up, down, left, and right) if there's a tree there.
 * Each minute, the trees in the neighboring cells of burning tree catch on fire.
 *
 * Your task is to calculate how many minutes it would take for the fire to spread throughout the forest i.e. to burn all the trees.
 *
 * If there are trees that cannot be reached by the fire (for example, isolated trees with no adjacent burning trees),
 * we consider that the fire will never reach them and -1 is returned.
 *
 * The time-complexity of your algorithm must be O(n) with n the number of cells in the forest.
 */
public class Wildfire {

    static final int EMPTY = 0;
    static final int TREE = 1;
    static final int BURNING = 2;


    /**
     * This method calculates how many minutes it would take for the fire to spread throughout the forest.
     *
     * @param forest
     * @return the number of minutes it would take for the fire to spread throughout the forest,
     *         -1 if the forest cannot be completely burned.
     */
    public int burnForest0(int [][] forest) {
        HashSet<Point> burningCoord = new HashSet<>();
        int numberTrees = 0;
        for (int i = 0; i < forest.length; i++) {
            for (int j = 0; j < forest[0].length; j++) {
                if (forest[i][j] == BURNING) {
                    burningCoord.add(new Point(i, j));
                    numberTrees += 1;
                } else if (forest[i][j] == TREE) {
                    numberTrees += 1;
                }
            }
        }

        int previousSize = burningCoord.size();
        HashSet<Point> temporaryCoord;
        HashSet<Point> previous = new HashSet<>(burningCoord);
        int time = 0;
        while(true) {
            if (burningCoord.size() == numberTrees) return time;
            temporaryCoord = new HashSet<>();

            for (Point coord : previous) {
                int i = coord.x;
                int j = coord.y;

                if (j > 0 && forest[i][j-1] == TREE) {
                    temporaryCoord.add(new Point(i, j-1));
                }
                if (j < forest[0].length -1 && forest[i][j+1] == TREE) {
                    temporaryCoord.add(new Point(i, j+1));
                }
                if (i > 0 && forest[i-1][j] == TREE) {
                    temporaryCoord.add(new Point(i-1, j));
                }
                if (i < forest.length -1 && forest[i+1][j] == TREE) {
                    temporaryCoord.add(new Point(i+1, j));
                }
            }
            burningCoord.addAll(temporaryCoord);
            previous.clear();
            previous.addAll(temporaryCoord);

            if (burningCoord.size() == previousSize) return -1;
            previousSize = burningCoord.size();
            time ++;
        }
    }

    public int burnForest(int [][] forest) {
        int numbRows = forest.length;
        int numbCol = forest[0].length;

//        //try actually using graphs
//        HashSet<Integer>[] adj = (HashSet<Integer>[]) new HashSet[numbRows * numbCol];
//        List<Integer> burned = new ArrayList<>();
//        for (int i = 0; i < numbRows; i++) {
//            for (int j = 0; j < numbCol; j++) {
//                adj[numbCol*i +j] = new HashSet<>();
//                if (i > 0) {
//                    //add up neighbour
//                    adj[numbCol*i +j].add(numbCol*(i -1) +j);
//                }
//                if (i < numbRows -1) {
//                    //add down neighbour
//                    adj[numbCol*i +j].add(numbCol*(i +1) +j);
//                }
//                if (j > 0) {
//                    //add left neighbour
//                    adj[numbCol*i +j].add(numbCol*i +(j -1));
//                }
//                if (j < numbCol -1) {
//                    //add right neighbour
//                    adj[numbCol*i +j].add(numbCol*i +(j +1));
//                }
//
//                if (forest[i][j] == BURNING) {
//                    burned.add(numbCol*i +j);
//                }
//            }
//        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<Cell> queue = new LinkedList<>();
        int burningTime = 0;

        for (int i = 0; i < numbRows; i++) {
            for (int j = 0; j < numbCol; j++) {
                if (forest[i][j] == BURNING) {
                    queue.add(new Cell(i, j, burningTime));
                }
            }
        }

        while(!queue.isEmpty()) {
            Cell current = queue.poll();
            burningTime = current.burnTime;
            for (int[] direction : directions) {
                int newI = current.coordI + direction[0];
                int newJ = current.coordJ + direction[1];

                if (newI > -1 && newI < numbRows && newJ > -1 && newJ < numbCol && forest[newI][newJ] == TREE) {
                    forest[newI][newJ] = BURNING;
                    queue.add(new Cell(newI, newJ, burningTime +1));
                }
            }
        }

        for (int i = 0; i < numbRows; i++) {
            for (int j = 0; j < numbCol; j++) {
                if (forest[i][j] == TREE) return -1;
            }
        }


        return burningTime;
    }

    //meh?? pourquoi on aurait besoin de ca??
    private static class Cell{
        public int coordI, coordJ;
        public int burnTime; // -1 si arbre pas encore brule, -2 si empty

        public Cell(int i, int j) {
            this.coordI = i;
            this.coordJ = j;
        }
        public Cell(int i, int j, int burnTime){
            this.coordI = i;
            this.coordJ = j;
            this.burnTime = burnTime;
        }
    }
}