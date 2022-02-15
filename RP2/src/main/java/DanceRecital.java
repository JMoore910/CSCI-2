/*
 *  COP 3503 Spring 2022 Programming Assignment P2: Tentaizu
 *  Copyright 2022 Jeanne Claire Moore
 */

import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

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

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static int baseline = 0;

    //  Main method
    public static void main(String[] args) throws IOException {
        String firstInput = br.readLine();
        int size = parseInt(firstInput);

        Integer[] used = new Integer[size];
        ArrayList<Integer> perm = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            //  Init used array to -1
            used[i] = -1;
        }


        //  Get dances from input method
        String[] dances = readInput(size);

        //  Now that we have the input, we can create a baseline to check other permutations by
        for (int i = 1; i < size; i++) {
            //  Starting from the second element and moving through get int val of all dance compares
            baseline += danceCompare(dances[i-1], dances[i]);
        }


        //  Start the recursive call
        if (!danceRecitals(perm, used, dances, 0, 0)) {
            System.out.println("Some error has occurred");
        }
    }

    //  dance recitals permutation method
    //  Recursive!!!
    public static boolean danceRecitals(ArrayList<Integer> perm, Integer[] used, String[] dances, int k, int quickChanges) {
        //  Use backtracking if we find a perm begins passing the baseline


        //  DO REST TOMORROW
        //  for (int i = 0; i < dances)


        return false;
    }


    //  Read method
    public static String[] readInput(int size) throws IOException {
        String[] dances = new String[size];

        for (int i = 0; i < size; i++) {
            //  Read all dances in as input from buffered reader
            dances[i] = br.readLine();
        }

        //  Return the dances to the user
        return dances;
    }


    //  dance comparing method
    public static int danceCompare(String last, String cur) {
        int quickChanges = 0;
        for (int i = 0; i < last.length(); i++) {
            //  a single char is not acceptable for the contains method, add blank char to make it a char sequence
            if (cur.contains(last.charAt(i)+"")) {
                //  If the Dancer in the last dance is within the current dance, a quick change was required
                quickChanges++;
            }
        }

        return quickChanges;
    }
}
