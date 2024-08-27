package graphs;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implement the Digraph.java interface in
 * the Digraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */
public class Digraph {

    private Set<Integer>[] adjacency;
    private int V;
    private int E;


    public Digraph(int V) {
        this.adjacency = (HashSet<Integer>[]) new HashSet[V];
        for (int i = 0; i < V; i++) {
            this.adjacency[i] = new HashSet<>();
        }

        this.V = V;
        this.E = 0;
    }

    /**
     * The number of vertices
     */
    public int V() {
        return this.V;
    }

    /**
     * The number of edges
     */
    public int E() {
        return this.E;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        this.E ++;
        this.adjacency[v].add(w);
    }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v) {
        return this.adjacency[v];
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        Digraph rev = new Digraph(this.V);
        for (int i = 0; i < this.V; i++) {
            for(Integer neighbour : this.adj(i)) {
                rev.addEdge(neighbour, i);
            }
        }

        return rev;
    }

}
