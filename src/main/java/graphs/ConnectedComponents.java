package graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * You are asked to implement the ConnectedComponent class given a graph.
 * The Graph class available in the code is the one of the Java class API.
 * <p>
 * public class ConnectedComponents {
 * <p>
 * public static int numberOfConnectedComponents(Graph g){
 * // TODO
 * return 0;
 * }
 * }
 * Hint: Feel free to add methods or even inner-class (private static class)
 *       to help you but don't change the class name or the signature of the numberOfConnectedComponents method.
 *
 *
 */
public class ConnectedComponents {


    /**
     * @return the number of connected components in g
     */
    public static int numberOfConnectedComponents(Graph g) {
        // typically use dfs that will color all nodes connected to source, and add a count
        boolean[] marked = new boolean[g.V()];
        int count = 0;
        for (int i = 0; i < g.V(); i++) {
            if (!marked[i]) {
                count ++;
                marked[i] = true;
                dfs(g, i, marked);
            }
        }
         return count;
    }

    private static void dfs(Graph g, int source, boolean[] marked) {
        marked[source] = true;
        for (int v : g.adj(source)) {
            if (!marked[v]) {
                dfs(g, v, marked);
            }
        }
    }

    static class Graph {

        private List<Integer>[] edges;

        public Graph(int nbNodes) {
            this.edges = (ArrayList<Integer>[]) new ArrayList[nbNodes];
            for (int i = 0;i < edges.length;i++)
            {
                edges[i] = new ArrayList<>();
            }
        }

        /**
         * @return the number of vertices
         */
        public int V() {
            return edges.length;
        }

        /**
         * @return the number of edges
         */
        public int E() {
            int count = 0;
            for (List<Integer> bag : edges) {
                count += bag.size();
            }

            return count/2;
        }

        /**
         * Add edge v-w to this graph
         */
        public void addEdge(int v, int w) {
            edges[v].add(w);
            edges[w].add(v);
        }

        /**
         * @return the vertices adjacent to v
         */
        public Iterable<Integer> adj(int v) {
            return edges[v];
        }
    }

}
