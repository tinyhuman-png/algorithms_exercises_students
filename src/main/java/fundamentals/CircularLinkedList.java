package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

    private long nOp = 0; // count the number of operations
    private int n;          // size of the stack
    private Node  last;   // trailer of the list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    public CircularLinkedList() {
        this.n=0;
        this.last=null;
        this.nOp=0;
    }

    public boolean isEmpty() {
         return n<=1;
    }

    public int size() {
         return n;
    }

    private long nOp() {
        return nOp;
    }



    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        Node node= new Node();
        node.item= item;
        if (last == null){
            last = node;
            last.next = node;
        }
        else {
            node.next = last.next;
            last.next = node;
            last=node;
        }
        n++;
        nOp++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index) throws IndexOutOfBoundsException{
        if (index > n-1 || index < 0) throw new IndexOutOfBoundsException();
        Node removed = null;
        Node current = this.last.next;
        if (index == 0) {
            removed = last.next;
            if (n == 1) {
                last = null;
            } else {
                last.next = removed.next;
            }
            n--;
            nOp++;
            return removed.item;
        }
        for (int i = 0; i < n; i++) {
            if (i == index-1 ){
                removed = current.next;
                current.next = current.next.next;
                n--;
                nOp++;
                return removed.item;
            }
            current=current.next;
        }
        return null;
    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator(last , n , nOp);
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item> {
        private Node next;
        private int size;
        private int current;
        private long nOpp;
        public ListIterator(Node last, int size, long nOp){
            if (last != null) {
                this.next = last.next;
            }
            this.size = size;
            this.nOpp = nOp;
        }

        // TODO You probably need a constructor here and some instance variables


        @Override
        public boolean hasNext() {
            return !(current>=size);
        }

        @Override
        public Item next() throws ConcurrentModificationException {
            if (this.nOpp!= nOp)throw new ConcurrentModificationException();
            if (hasNext() && next!= null){
                if (current == 0){
                    current++;
                    return next.item;
                }
                if (next.next!= null){
                    Node n = next;
                    next= next.next;
                    current++;
                    return n.next.item;
                }
            }
            return null;
        }

    }

}
