/*
 *  COP 3503 Spring 2022 Recitation Project 1: CD Solution
 *  Copyright 2022 Jeanne Claire Moore
 */

import java.io.*;
import java.util.TreeSet;

//  Create an application that tracks
//  the number of CDs that two people have in common

class CDOwner {
    //  Owner should have a name
    //  Owner should have a TreeMap or a TreeSet that contains all cds they own
    private String name;
    private TreeSet<Integer> cds;

    //  Include a loaded constructor and getters
    public CDOwner(String name) {
        this.name = name;
    }
}
public class CD {
    //  Create a buffered reader before entering the while loop to read in N and M

    //  Once we have N and M, perform the rest of the program while both are not 0

    //      for N lines read each next line and add it to Jack's TreeSet
    //          Since it is a TreeSet duplicates are ignored
    //      for M lines read each next line and add it to Jill's TreeSet

    //      Create a var Integer common at 0, then go through all items in Jack's TreeSet
    //      check if Jill's TreeSet contains a node in the TreeSet belonging to Jack

    //          if so, iterate common by one

    //      print out common

    //      read N and M again in the same way as before entering the while loop

}
