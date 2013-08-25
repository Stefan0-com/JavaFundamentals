package Fundamentals;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author stefan0
 */
/*************************************************************************
 *  Compilation:  javac BinarySearch.java
 *  Execution:    java BinarySearch whitelist.txt < input.txt
 *  Data files:   http://algs4.cs.princeton.edu/11model/tinyW.txt
 *                http://algs4.cs.princeton.edu/11model/tinyT.txt
 *                http://algs4.cs.princeton.edu/11model/largeW.txt
 *                http://algs4.cs.princeton.edu/11model/largeT.txt
 *
 *  % java BinarySearch tinyW.txt < tinyT.txt
 *  50
 *  99
 *  13
 *
 *  % java BinarySearch largeW.txt < largeT.txt | more
 *  499569
 *  984875
 *  295754
 *  207807
 *  140925
 *  161828
 *  [3,675,966 total values]
 *  
 *************************************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class BinarySearch {

    // precondition: array a[] is sorted
    //public static int rank(int key, int[] a) {
    public static int rank(int key, Integer[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        Path path;
        FileSystem fileSystem = FileSystems.getDefault();
        String pathString = "data";
        String whiteListFile = "tinyW.txt";
        String searchFile = "tinyT.txt";
        Path dataPath = FileSystems.getDefault().getPath(pathString);
        Path dp = null;
        try {
            dp = dataPath.toRealPath();
        } catch (Exception ex) {
            System.out.printf("Path %s does not exist.\n", pathString);
            return;
        }
        Path whiteListPath= Paths.get(pathString, whiteListFile);
        if (! Files.isReadable(whiteListPath)) {
            String whiteListPathString = dataPath + File.separator + whiteListFile;
            System.out.printf("File %s does not exist.", whiteListPathString);
            return;
        }
        Path searchPath = Paths.get(pathString, searchFile);
        if (! Files.isReadable(searchPath)) {
            String searchPathString = dataPath + File.separator + searchFile;
            System.out.printf("File %s does not exist.", searchPathString);
            return;
        }
        ArrayList<Integer> whiteList = new ArrayList();
        Charset charset = Charset.forName("US-ASCII");
        try {
            BufferedReader reader = Files.newBufferedReader(whiteListPath, charset);
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.printf("Next whitelist token = %s\n", line);
                try {
                    int nextInt = Integer.parseInt(line);
                    whiteList.add(nextInt);
                } catch (Exception ex) {
                    System.out.printf("Unable to parse token to integer\n");
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        //Integer[] whiteListArray = (Integer[]) whiteList.toArray();
        Integer[] whiteListArray = new Integer[whiteList.size()];
        whiteList.toArray(whiteListArray);
        //int [] whiteListArray = new int[whiteList.size()];
        //for (int i=0; i)
        Arrays.sort(whiteListArray);
        
        try {
            BufferedReader reader = Files.newBufferedReader(searchPath, charset);
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.printf("Next search token = %s\n", line);
                try {
                    int nextInt = Integer.parseInt(line);
                    if (rank(nextInt, whiteListArray) == -1) {
                        System.out.printf("Token %d not found\n", nextInt);                        
                    } else {
                        System.out.printf("Token %d found\n", nextInt);
                    }
                } catch (Exception ex) {
                    System.out.printf("Unable to parse search token to integer\n");
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }        
        
        /*
        int[] whitelist = In.readInts(args[0]);

        Arrays.sort(whitelist);

        // read key; print if not in whitelist
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (rank(key, whitelist) == -1)
                StdOut.println(key);
        }
        */
    }
}
