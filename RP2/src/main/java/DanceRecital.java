/*
 *  COP 3503 Spring 2022 Programming Assignment P2: Tentaizu
 *  Copyright 2022 Jeanne Claire Moore
 */

import java.util.*;

//  Create an application:
//  Application reads input from user for a number of dances that take place in a dance recital, then
//  reads in each dance line by line. Dancers are represented by single characters, and are arranged
//  in each dance in alphabetical order. When one dance finishes and another begins, if any dancers who
//  were in the last dance are performing again, they must perform a quick change, and this can cause
//  a performance to be bogged down. Determine what the lowest amount of quick changes in a performance
//  can be if the dances are moved around to compensate for the dancers.


//  To make the program faster, track the lowest similarities count, starting with the input dance. Add each
//  Comparison similarity value as you recurse further and at any point if it exceeds the current lowest similarity
//  count, return false. If it makes it to the end, keep track of the similarities count, if it is less than the current
//  lowest, make it the new lowest and continue permutations. IF IT IS 0 STOP AND MAKE LOWEST 0


public class DanceRecital {

    //  Main method
    public static void main(String[] args) {

    }

    //  dance recitals permutation method
    //  Recursive!!!
    public boolean danceRecitals(ArrayList<Integer> perm, Integer[] used, int k, int quickChanges) {


        return false;
    }


    //  Read method
    public static String[] readInput(int K) {

        return new String[K];
    }

    //  dance comparing method
    public static int danceCompare(String last, String cur) {

        return 0;
    }
}
