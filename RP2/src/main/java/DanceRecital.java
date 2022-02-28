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

        boolean[] used = new boolean[size];
        ArrayList<String> perm = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            //  Init used array to -1
            used[i] = false;
        }


        //  Get dances from input method
        String[] dances = readInput(size);

        //  Now that we have the input, we can create a baseline to check other permutations by
        for (int i = 1; i < size; i++) {
            //  Starting from the second element and moving through get int val of all dance compares
            baseline += danceCompare(dances[i-1], dances[i]);
        }


        //  Start the recursive call
        danceRecitals(perm, used, dances, 0, 0, size);
        //  baseline global changed to be the lowest possible num of quick changes, so print it
        System.out.println(baseline);
    }

    //  dance recitals permutation method
    //  Recursive!!!
    public static void danceRecitals(ArrayList<String> perm, boolean[] used, String[] dances, int k, int quickChanges, int size) {
        //  Check if k is equal to the size
        if (k == size) {
            //  Check quick changes. If it is less than baseline set it to be baseline
            if (quickChanges < baseline) {
                baseline = quickChanges;
                return;
            }
        }

        if (quickChanges >= baseline) {
            //  If baseline is passed, do not go further with permutation
            return;
        }

        int changes;
        //  Create permutations but use backtracking if quickChanges becomes larger than baseline
        for (int i = 0; i < size; i++) {
            //  Check if dance i is already being used
            if (used[i]) {
                continue;
            }

            //  if k == 0 we are at start so go ahead and add then continue
            if (k == 0) {
                used[i] = true;
                perm.add(dances[i]);
                //  No need to check for changes since there is no previous element just recurse forwards
                danceRecitals(perm, used, dances, k+1, quickChanges, size);

                //  After recursion remove it from the array
                perm.remove(dances[i]);
                used[i] = false;
                continue;
            }

            //  If not used, go ahead and add the item and recurse
            used[i] = true;
            perm.add(dances[i]);
            changes = danceCompare(perm.get(k-1), perm.get(k));

            //  Use new changes to recurse further
            danceRecitals(perm, used, dances, k+1, quickChanges + changes, size);

            //  After recursion remove from array
            perm.remove(dances[i]);
            used[i] = false;
        }

        return;
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
