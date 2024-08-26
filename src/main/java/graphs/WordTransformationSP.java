package graphs;

import java.util.*;


/**
 * You are asked to implement the WordTransformationSP
 * class which allows to find the shortest path
 * from a string A to another string B
 * (with the certainty that there is a path from A to B).
 * To do this, we define a rotation(x, y) operation that
 * reverses the order of the letters between the x and y positions (not included).
 * For example, with A=``HAMBURGER``, if we do rotation(A, 4, 8), we get HAMBEGRUR.
 * So you can see that the URGE sub-string
 * has been inverted to EGRU and the rest of the string
 * has remained unchanged: HAMB + ECRU + R = HAMBEGRUR.
 * Let's say that a rotation(x, y) has a cost of y-x.
 * For example going from HAMBURGER to HAMBEGRUR costs 8-4 = 4.
 * The question is what is the minimum cost to reach a string B from A?
 * So you need to implement a public static int minimalCost(String A, String B)
 * function that returns the minimum cost to reach String B
 * from A using the rotation operation.
 */
public class WordTransformationSP {

    /**
     * Rotate the substring between start and end of a given string s
     * eg. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     */
    /**
     * Rotate the substring between start and end of a given string s
     * eg. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     *
     * @param s
     * @param start
     * @param end
     * @return rotated string
     */
    public static String rotation(String s, int start, int end) {
        return s.substring(0, start) + new StringBuilder(s.substring(start, end)).reverse().toString() + s.substring(end);
    }

    /**
     * Compute the minimal cost from string "from" to string "to" representing the shortest path
     *
     * @param from
     * @param to
     * @return
     */
    public static int minimalCost(String from, String to) {
        int lengthFrom = from.length();
        if (lengthFrom != to.length()) return -1;

        HashMap<String, Integer> distToFrom = new HashMap<>();
        distToFrom.put(from, 0);

        //je pense que le mieux c'est de construire le graphe au fur et � mesure sinon ca va prendre masse de m�moire
        PriorityQueue<Entry> pq = new PriorityQueue<>();  //ATTENTION dans priorityqueue on doit mettre des comparable (?)
        pq.add(new Entry(from, 0));
        while(!pq.isEmpty()) {
            Entry v = pq.poll();
            String value = v.value;
            for (int i = 0; i < lengthFrom -1; i++) {
                for (int j = i +2; j < lengthFrom +1; j++) {
                    String adjacent = rotation(value, i, j);
                    if (!distToFrom.containsKey(adjacent) || distToFrom.get(adjacent) > distToFrom.get(value) + (j-i)) {
                        distToFrom.put(adjacent, distToFrom.get(value) + (j-i));
                        pq.add(new Entry(adjacent, distToFrom.get(value) + (j-i)));
                    }
                }
            }

        }

         return distToFrom.get(to);
    }

    private static class Entry implements Comparable<Entry> {
        public String value;
        public int distance;

        public Entry(String string, int dist) {
            this.value = string;
            this.distance = dist;
        }
        @Override
        public int compareTo(Entry o) {
            return this.distance - o.distance;
        }
    }



    //nopp not useful :((
    private static HashMap<String, Node> adjacentStrings(Node source, int length) {
        if (length < 2) return null;
        HashMap<String, Node> map = new HashMap<>();
        for (int i = 0; i < length -2; i++) {
            for (int j = i +2; j < length +1; j++) {
                map.putIfAbsent(rotation(source.value, i, j), new Node(rotation(source.value, i, j), j-i, source));
            }
        }
        return map;
    }

    private static class Node {
        public int distToSource = Integer.MAX_VALUE;
        public String value;
        public int costFromParent;
        public Node parent;

        public Node(String value, int costFromParent, Node parent) {
            this.value = value;
            this.costFromParent = costFromParent;
            this.parent = parent;
        }
    }


}
