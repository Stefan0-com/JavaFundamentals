package Fundamentals;

/*************************************************************************
 *  Compilation:  javac Bag.java
 *  Execution:    java Bag < input.txt
 *
 *  A generic bag or multiset, implemented using a linked list.
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The <tt>Bag</tt> class represents a bag (or multiset) of 
 *  generic items. It supports insertion and iterating over the 
 *  items in arbitrary order.
 *  <p>
 *  The <em>add</em>, <em>isEmpty</em>, and <em>size</em>  operation 
 *  take constant time. Iteration takes time proportional to the number of items.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class MyBag<Item> extends Bag<Item> implements Iterable<Item> {
    private int N;         // number of elements in bag
    private Node first;    // beginning of bag

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

   /**
     * Create an empty stack.
     */
    public MyBag() {
        first = null;
        N = 0;
        assert check();
    }

   /**
     * Is the BAG empty?
     */
    public boolean isEmpty() {
        return first == null;
    }

   /**
     * Return the number of items in the bag.
     */
    public int size() {
        return N;
    }

   /**
     * Add the item to the bag.
     */
    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
        assert check();
    }
    
    public void add(Bag next_bag) {
        if (next_bag == null) {
            return;
        }
        Iterator<Item> iterator = next_bag.iterator();
        while (iterator.hasNext()) {
            Item next_bag_item = iterator.next();
            add(next_bag_item);
        }
    }
    
    public String toString() {
        return toString(null);
    }
    
    public String toString(String sepString) {
        StringBuilder returnString = new StringBuilder();
        returnString.append("Size = " + size() + "\n");
        Iterator iterator = iterator();
        int i_item = 0;
        while (iterator.hasNext()) {
            if (sepString != null) {
                returnString.append(sepString).append("\n");
            }
            Item item = (Item) iterator.next();
            returnString.append("Item # " + i_item + "\n");
            i_item += 1;
            returnString.append(item.toString());            
        }        
        return returnString.toString();
    }

    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null) return false;
        }
        else if (N == 1) {
            if (first == null)      return false;
            if (first.next != null) return false;
        }
        else {
            if (first.next == null) return false;
        }

        // check internal consistency of instance variable N
        int numberOfNodes = 0;
        for (Node x = first; x != null; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != N) return false;

        return true;
    } 


   /**
     * Return an iterator that iterates over the items in the bag.
     */
    public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    //--------------------------------------------------------------------------
    // New MyBag methods
    
    public void reverse() {
        Node oldFirst = first;
        Node node1 = first;
        if (node1 == null) return;
        Node node0;
        Node node2 = node1.next;
        while (node2 != null) {
            node0 = node1;
            node1 = node2;
            node2 = node1.next;
            node1.next = node0;
        }
        oldFirst.next = null;
        first = node1;
    }
    
    public class DoubleItem<Item> {
        public Item item1;
        public Item item2;
        public DoubleItem next;
    }
    
    private class DoubleIterator implements Iterator<DoubleItem<Item>> {
        private DoubleItem current = null;
        
        public DoubleIterator() {
            // This is the tricky part!
            // All symmetric combintations DoubleItem objects must be constructed
            // and added to the linked list
            if (size() < 2) return;
            Iterator iterator1 = iterator();
            int iskip=0;
            DoubleItem<Item> lastDoubleItem = null;
            DoubleItem<Item> currentDoubleItem = null;
            while (iterator1.hasNext()) {
                Item item1 = (Item) iterator1.next();
                // stop iterator1 at the element before the end 
                if (! iterator1.hasNext()) {
                    break;
                }
                iskip += 1;
                Iterator iterator2 = iterator();
                // Skip the number of items in the outer (i) loop
                for (int i=0; i<iskip; i++) {
                    iterator2.next();
                }
                while (iterator2.hasNext()) {
                    Item item2 = (Item) iterator2.next();
                    lastDoubleItem = currentDoubleItem;
                    currentDoubleItem = new DoubleItem<Item>();
                    currentDoubleItem.item1 = item1;
                    currentDoubleItem.item2 = item2;
                    if (null != lastDoubleItem) {
                        lastDoubleItem.next= currentDoubleItem;
                    }
                    if (null == current) {
                        current = currentDoubleItem;
                    }
                }
            }            
        }
        
        public boolean hasNext() {return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        
        public DoubleItem next() {
            if (!hasNext()) throw new NoSuchElementException();
            DoubleItem item = current;
            current = current.next;
            return item;
        }
    }
    
    public DoubleIterator doubleIterator() {
      return new DoubleIterator();
    }
        
}
