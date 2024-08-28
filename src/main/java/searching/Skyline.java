package searching;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * In this exercise, you must compute the skyline defined by a set of buildings.
 * When viewed from far away, the buildings only appear as if they were on a two-dimensionnal line.
 * Hence, they can be defined by three integers: The start of the building (left side), the height
 * of the building and the end of the building (right side).
 * For example, a building defined by (2, 5, 4) would look like
 *
 *   xxx
 *   xxx
 *   xxx
 *   xxx
 *   xxx
 * ________
 *
 * Obviously in practice buildings are not on a line, so they can overlap. If we add a new, smaller,
 * building in front of the previous one, defined by (3, 3, 6), then the view looks like:
 *
 *   xxx
 *   xxx
 *   xyyyy
 *   xyyyy
 *   xyyyy
 * ________
 *
 * The skyline is then define as the line that follows the highest building at any given points.
 * Visually, for the above example, it gives:
 *
 *   sss
 *      
 *      ss
 *        
 *        
 * ________
 *
 *
 * We ask you to compute, gien a set of building, their skyline.
 */
public class Skyline {


    /**
     *   The buildings are defined with triplets (left, height, right).
     *         int[][] buildings = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};
     *
     *         [{1,11},{3,13},{9,0},{12,7},{16,3},{19,18},{22,3},{23,13},{29,0}]
     *
     * @param buildings
     * @return  the skyline in the form of a list of "key points [x, height]".
     *          A key point is the left endpoint of a horizontal line segment.
     *          The key points are sorted by their x-coordinate in the list.
     */
    public static List<int[]> getSkyline(int[][] buildings) {
		//consider that the buildings are already in ascending order of left side

        LinkedList<int[]> keypoints = new LinkedList<>();
        PriorityQueue<Point> pqueue = new PriorityQueue<>();

        for (int[] build : buildings) {
            pqueue.add(new Point(build[0], build[1]));
            pqueue.add(new Point(build[2], -build[1]));
        }

        TreeSet<Integer> max = new TreeSet<>();
        max.add(0); //ajoute a la fin de la linkedlist (last)
        while(!pqueue.isEmpty()) {
            Point p = pqueue.poll();

            int maxInt = max.last();
            if ((p.heightChange > maxInt && !keypoints.isEmpty() && p.x != keypoints.getLast()[0]) || (p.heightChange > maxInt && keypoints.isEmpty())) {
                max.add(p.heightChange);
                int[] key = {p.x, p.heightChange};
                keypoints.add(key);
            } else if (p.heightChange > maxInt && p.x == keypoints.getLast()[0] && keypoints.getLast()[1] < p.heightChange){
                keypoints.removeLast();
                max.add(p.heightChange);
                int[] key = {p.x, p.heightChange};
                keypoints.add(key);
            } else if (p.heightChange > 0) {
                max.add(p.heightChange);
            } else {
                max.remove(-p.heightChange);
                if (max.isEmpty()) {
                    max.add(0);
                    int[] key = {p.x, 0};
                    keypoints.add(key);
                } else if (max.last() < -p.heightChange) {
                    int[] key = {p.x, max.last()};
                    keypoints.add(key);
                }
            }
        }

//        HashMap<Integer, int[]> correspondanceMap = new HashMap<>();
//        for (int[] key : keypoints) {
//            if (correspondanceMap.containsKey(key[0])) {
//                int[] oldValue = correspondanceMap.get(key[0]);
//                if (oldValue[1] < key[1]) {
//                    correspondanceMap.put(key[0], key);
//                }
//            } else {
//                correspondanceMap.put(key[0], key);
//            }
//        }
//        List<int[]> returnPoints = new ArrayList<>(correspondanceMap.values());
//        returnPoints.sort(new Comparator<int[]>() {
//            @Override
//            public int compare(int[] o1, int[] o2) {
//                return o1[0] - o2[0];
//            }
//        });

        return keypoints;
    }

    private static class Point implements Comparable<Point> {
        public int x;
        public int heightChange;

        public Point(int x, int height) {
            this.x = x;
            this.heightChange = height;
        }

        @Override
        public int compareTo(Point o) {
            return this.x - o.x;
        }
    }

}
