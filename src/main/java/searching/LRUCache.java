package searching;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * We are interested in the implementation of an LRU cache,
 * i.e. a (hash)-map of limited capacity where the addition of
 * a new entry might induce the suppression of the Least Recently Used (LRU)
 * entry if the maximum capacity is exceeded.
 *
 * Your LRU cache implements the same two methods as a MAP :
 * - put(key, elem) inserts the given element in the cache,
 *      this element becomes the most recently used element
 *      and if needed (the cache is full and the key not yet present),
 *      the least recently used element is removed.
 * - get(key) returns the entry with the given key from the cache,
 *      this element becomes the most recently used element
 *
 * These methods need to be implemented with an expected time complexity of O(1).
 * You are free to choose the type of data structure that you consider
 * to best support this cache. You can also use data-structures from Java.
 *
 * Hint for your implementation:
 *       Use a doubly linked list to store the elements from the least
 *       recently used (head) to the most recently used (tail).
 *       If needed the element to suppress is the head of the list.
 *
 *       Use java.util.HashMap with the <key,node> where node is a reference to the node
 *       in the doubly linked list.
 *
 *       Of course, at every put/get the list will need to be updated so that
 *       the "accessed node" is placed at the end of the list.
 *
 *       Feel free to use existing java classes.
 *
 *  Example of usage of an LRU cache with capacity of 3:
 *  // step 0:
 *  put(A,7)  // map{(A,7)}  A is the LRU
 *  // step 1:
 *  put(B,10) // map{(A,7),(B,10)}  A is the LRU
 *  // step 2:
 *  put(C,5)  // map{(A,7),(B,10),(C,5)}  A is the LRU
 *  // step 3:
 *  put(D,8)  // map{(B,10),(C,5),(D,8)}  A is suppressed, B is the LRU
 *  // step 4:
 *  get(B)    // C is the LRU
 *  // step 5
 *  put(E,9)  // map{(B,10),(D,8),(E,9)} D is the LRU
 *  // step 6
 *  put(D,3)  // map{(B,10),(D,3),(E,9)} B is the LRU
 *  // step 7
 *  get(B)    // map{(B,10),(D,3),(E,9)} E is the LRU
 *  // step 8
 *  put(A,2)  // map{(B,10),(D,3),(A,2)} D is the LRU
 *
 *  Feel free to use existing java classes from Java
 */
public class LRUCache<K,V> {

    private int capacity;
    private HashMap<K, Node> map;
    private Node LRU;
    private Node MRU;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
    }

    public V get(K key) {
        if (!this.map.containsKey(key)) {
            return null;
        }

        Node n = this.map.get(key);
        this.remove(n);
        this.addFirst(n);

        return n.value;
    }

    public void put(K key, V value) {
        Node new_node;
        if (!this.map.containsKey(key)) {
            new_node = new Node(key, value, null, null);
            this.addFirst(new_node);

            this.map.put(key, new_node);
        } else {
            new_node = map.get(key);
            new_node.value = value;

            this.remove(new_node);
            this.addFirst(new_node);
        }

        if (this.map.size() > this.capacity) {
            K keyToremove = this.LRU.key;
            this.map.remove(keyToremove);

            this.remove(this.LRU);
        }
    }

    private void remove(Node node) {
        if (node.next == null && node.previous == null) {
            this.LRU = null;
            this.MRU = null;
        } else if (node.next != null && node.previous != null) {
            node.next.previous = node.previous;
            node.previous.next = node.next;
        } else if (node.next == null) {
            node.previous.next = null;
            this.MRU = node.previous;
        } else {
            node.next.previous = null;
            this.LRU = node.next;
        }
    }

    private void addFirst(Node node) {
        if (this.MRU == null) {
            node.previous = null;
            node.next = null;
            this.LRU = node;
            this.MRU = node;
        } else {
            node.previous = this.MRU;
            this.MRU.next = node;
            node.next = null;
            this.MRU = node;
        }
    }

    private class Node {
        public K key;
        public V value;
        public Node previous;
        public Node next;

        public Node(K key, V value, Node prev, Node next) {
            this.key = key;
            this.value = value;
            this.previous = prev;
            this. next = next;
        }
    }
}
