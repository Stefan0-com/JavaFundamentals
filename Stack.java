/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Fundamentals;

/**
 *
 * @author stefan0
 */

/*************************************************************************
 *  Compilation:  javac Stack.java
 *  Execution:    java Stack < input.txt
 *
 *  A generic stack, implemented using a linked list. Each stack
 *  element is of type Item.
 *  
 *  % more tobe.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java Stack < tobe.txt
 *  to be not that or be (2 left on stack)
 *
 *************************************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 *  The <tt>Stack</tt> class represents a last-in-first-out (LIFO) stack of generic items.
 *  It supports the usual <em>push</em> and <em>pop</em> operations, along with methods
 *  for peeking at the top item, testing if the stack is empty, and iterating through
 *  the items in LIFO order.
 *  <p>
 *  All stack operations except iteration are constant time.
 *  <p>
 *  For additional documentation, see <a href="/algs4/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Stack<Item> implements Iterable<Item> {
    private int N;          // size of the stack
    private Node first;     // top of stack

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

   /**
     * Create an empty stack.
     */
    public Stack() {
        first = null;
        N = 0;
        assert check();
    }

   /**
     * Is the stack empty?
     */
    public boolean isEmpty() {
        return first == null;
    }

   /**
     * Return the number of items in the stack.
     */
    public int size() {
        return N;
    }

   /**
     * Add the item to the stack.
     */
    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
        assert check();
    }

   /**
     * Delete and return the item most recently added to the stack.
     * @throws java.util.NoSuchElementException if stack is empty.
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        N--;
        assert check();
        return item;                   // return the saved item
    }


   /**
     * Return the item most recently added to the stack.
     * @throws java.util.NoSuchElementException if stack is empty.
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

   /**
     * Return string representation.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
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
     * Return an iterator to the stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator()  { return new ListIterator();  }

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


   /**
     * A test client.
     */
    public static void main(String[] args) {
        Stack<String> s = new Stack<String>();
        
        final boolean useConsoleInput = false;
        if (useConsoleInput) {
            Scanner sc = new Scanner(System.in);
            //while (!StdIn.isEmpty()) {
            //    String item = StdIn.readString();
            while (sc.hasNext()) {
                String item = sc.next();
                if (!item.equals("-")) {
                    s.push(item);
                } else if (!s.isEmpty()) {
                    //StdOut.print(s.pop() + " ");
                    System.out.printf("%s ", s.pop());
                }
            }
        } else {
            String pathString = "data";
            String inputFile = "tobe.txt";
            Path inputPath= Paths.get(pathString, inputFile);
            String inputPathString = pathString + File.separator + inputFile;
            if (! Files.isReadable(inputPath)) {
                System.out.printf("File %s does not exist.", inputPathString);
                return;
            }
            File inputFileObject = inputPath.toFile();
            try {
                Scanner sc = new Scanner(inputFileObject);
                //while (!StdIn.isEmpty()) {
                //    String item = StdIn.readString();
                while (sc.hasNext()) {
                    String item = sc.next();
                    if (!item.equals("-")) {
                        s.push(item);
                    } else if (!s.isEmpty()) {
                        //StdOut.print(s.pop() + " ");
                        System.out.printf("%s ", s.pop());
                    }
                }
            } catch (Exception ex) {
                System.out.printf("File %s not found", inputPathString);
            }
            /*
            Charset charset = Charset.forName("US-ASCII");
            try {
                BufferedReader reader = Files.newBufferedReader(inputPath, charset);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    
                }
            } catch (Exception ex) {                
            }
            */

        }
        //StdOut.println("(" + s.size() + " left on stack)");
        System.out.println("(" + s.size() + " left on stack)");
    }
}
