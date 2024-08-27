package graphs;

import java.util.*;

/**
 * Yikes! It's Christmas Eve and Nayeli still has one gift missing on her list.
 * The good thing is, she has a list of stores where she can find it with the gift price in each of them.
 * The bad news is that she only has a limited amount of time to go shopping for the gift.
 * She wants to know the lowest price at which she could get her product within her travel time limit.
 *
 *
 * You must design an algorithm that takes the following inputs :
 * - the road network (a weighted graph) where each node is a place and the weight on an edge the time cost between its ends;
 * - the node where Nayeli is;
 * - the maximum time she has for this errand (Note that she can reach the place just in time);
 * - the list of the places where the product is and its prices.
 *
 * and returns the smallest price at which Nayeli can buy her gift within the time limit.
 * If she cannot make it to any store in time, return -1;
 *
 * Example : In the following figure Nayeli is at the node O. The product is at 4 and 5 at respectively a price of 60 euros or 40 euros.
 *
 * If Nayeli has 5 units of time available she can get the gift at 40 euros because she is able to go to the store at 5.
 * With 4 units of time she can only reach node 4 so the smallest price is 60 euros.
 *
 *                  (1)
 *               1 ----- 2
 *          (1) /      /   \ (4)
 *             0  (1) /     5
 *          (2) \    /      / (1)
 *                3 ----- 4
 *                   (2)
 *
 * Feel free to use existing java classes.
 */
public class SmallestPrice {

    /**
     *
     * @param graph  a weighted graph with each node being a place and
     *               the weight of the edge the time cost to move from the origin to the destination.
     * @param source a node of the graph.
     * @param maxTime the maximum travel time.
     * @param destinations a list of Pair (place, value) where each place a store on the graph and
     *                     value the price of the gift at that place.
     * @return the smallest price to buy the gift from the source
     *         or -1 if there is no path within the maxTime.
     */
    public static int getSmallestPrice(WeightedGraph graph, int source, int maxTime, List<Pair> destinations) {
        PriorityQueue<Node> pqueue = new PriorityQueue<>();
        int[] distTo = new int[graph.V()];
        boolean[] visited = new boolean[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            distTo[i] = Integer.MAX_VALUE;
        }

        HashMap<Integer, Integer> mapOfShops = new HashMap<>();
        for (Pair pair : destinations) {
            mapOfShops.putIfAbsent(pair.getNode(), pair.getPrice());
        }
        int bestPrice = Integer.MAX_VALUE;

        distTo[source] = 0;
        pqueue.add(new Node(source, 0));  //ATTENTION utiliser des Nodes dans la PQ et pas juste l'indice du noeud, comme ca il sait trier par distance!!
        while (!pqueue.isEmpty()) {
            Node n = pqueue.poll();
            int v = n.getValue();

            if (visited[v]) continue;
            visited[v] = true;

            Integer price = mapOfShops.get(v);
            if (price != null) {
                if (price < bestPrice) bestPrice = price;
                mapOfShops.remove(v);
            }
            if (mapOfShops.isEmpty()) {  //serves nothing to compute the smallest path to shop that don't have the gift
                break;
            }

            for (DirectedEdge e : graph.outEdges(v)) {
                int w = e.to();
                int pathCost = distTo[v] + e.weight();

                if (distTo[w] > pathCost && pathCost <= maxTime) {  //only update the distance and put in the PQ if it is  less than maxtime, no need to go look further since we won't choose these shops anyway
                    distTo[w] = pathCost;
                    pqueue.add(new Node(w, distTo[w]));
                }
            }

        }

//        for (int i = 0; i < graph.V(); i++) {
//            if (mapOfShops.containsKey(i) && distTo[i] <= maxTime) {
//                Integer price = mapOfShops.get(i);
//                if (price < bestPrice) bestPrice = price;
//            }
//        }

        if (bestPrice == Integer.MAX_VALUE) return -1;
        return bestPrice;

    }



    static class Node implements Comparable<Node> {  //MEGA IMPORTANT a ne pas oublier quand on fait dijkstra et sa PQ
        private final int value;

        private final int distance;

        public Node(int val, int dist) {
            this.value = val;
            this.distance = dist;
        }

        public int getValue() {
            return this.value;
        }

        @Override
        public int compareTo(Node o) {
            return this.distance - o.distance;
        }
    }


    static class Pair {
        private final int node;
        private final int price;

        public Pair(int node, int price) {
            this.node = node;
            this.price = price;
        }

        public int getNode() {
            return node;
        }

        public int getPrice() {
            return price;
        }

    }


    static class WeightedGraph {

        private final int V;                // number of nodes in this digraph
        private int E;                      // number of edges in this digraph
        private List<DirectedEdge>[] adj;    // adj[v] = adjacency list for node v



        public WeightedGraph(int V) {
            this.V = V;
            this.E = 0;
            adj = (ArrayList<DirectedEdge>[]) new ArrayList[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new ArrayList<>();
            }
        }

        public int V() {
            return V;
        }

        public int E() {
            return E;
        }


        public void addEdge(DirectedEdge e) {
            int v = e.from();
            int w = e.to();
            adj[v].add(e);
            E++;
        }

        /**
         *
         * @param from a node of the graph
         * @return an Iterator with the outgoing edges adjacent to from
         */
        public Iterable<DirectedEdge> outEdges(int from) {
            return adj[from];
        }


    }

    static class DirectedEdge {

        private final int v;
        private final int w;
        private final int weight;

        /**
         * Initializes a directed edge from node {@code v} to node {@code w} with
         * the given {@code weight}.
         *
         * @param v      the tail node
         * @param w      the head node
         * @param weight the weight of the directed edge
         */
        public DirectedEdge(int v, int w, int weight) {
            if (v < 0) throw new IllegalArgumentException("Vertex names must be non-negative integers");
            if (w < 0) throw new IllegalArgumentException("Vertex names must be non-negative integers");
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        /**
         * Returns the tail node of the directed edge.
         *
         * @return the tail node of the directed edge
         */
        public int from() {
            return v;
        }

        /**
         * Returns the head node of the directed edge.
         *
         * @return the head node of the directed edge
         */
        public int to() {
            return w;
        }

        /**
         * Returns the weight of the directed edge.
         *
         * @return the weight of the directed edge
         */
        public int weight() {
            return weight;
        }


    }

}
