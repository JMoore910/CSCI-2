/*
 *  COP 3503 Spring 2022 Programming Assignment P2: Tentaizu
 *  Copyright 2022 Jeanne Claire Moore
 */


//  Method needs to be void/boolean recursive, should move through passing two arrays that get bigger as it is recursed through.
//  Not a backtracking program
//  Error checking: Check if input num is negative

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Integer.parseInt;

class ternarianweights {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Integer> rightSolutions;
        ArrayList<Integer> leftSolutions;
        int cases = parseInt(br.readLine());

        for (int i = 0; i < cases; i++) {
            rightSolutions = new ArrayList<>();
            leftSolutions = new ArrayList<>();

            doSolutions(parseInt(br.readLine()), rightSolutions,leftSolutions);
        }
    }

    static void doSolutions(int target, ArrayList<Integer> rightSolutions, ArrayList<Integer> leftSolutions) {
        //  Move through right solutions first, then add a new number that is
        //  the Greatest multiple of 3, but less than num

        boolean addingRight;
        while (target != 0) {
            if (target > 0)
                addingRight = true;
            else
                addingRight = false;

            //  find powers of 3 above and below the target
            int above = 3;
            int below = 1;
            int aboveDiff;
            int belowDiff;
            int change;

            //  Move above and below until they are around the target
            while (above < abs(target)) {
                above *= 3;
                below *= 3;
            }


            //  Get differences above and below target
            aboveDiff = above - abs(target);
            belowDiff = abs(target) - below;

            if (aboveDiff >= belowDiff && !numUsed(above,rightSolutions,leftSolutions)) {
                //  If the above power is closer to target or equal in distance, it needs to be added to solutions
                change = above;
            } else if (!numUsed(below,rightSolutions,leftSolutions)){
                //  If the below power is closer to target, it needs to be added to solutions
                change = below;
            }

            //  If adding right the change is subtracted from target, and added to rightSolutions
            if (addingRight) {
                target -= change;
                rightSolutions.add(change);
            }
            //  If not adding right the change is added to target, and added to leftSolutions
            else {
                target += change;
                leftSolutions.add(change);
            }


        }

        //  Both pans are complete, go ahead and print them
        printSolutions(rightSolutions,leftSolutions);
    }


    //   Method for printing the output in left and right pans
    static void printSolutions(ArrayList<Integer> rightSolutions, ArrayList<Integer> leftSolutions) {
        //  Print output from left pan
        System.out.print("left pan: ");
        for (int i : leftSolutions) {
            System.out.print(i + " ");
        }

        //  Print output from right pan
        System.out.print("\nright pan: ");
        for (int i : rightSolutions) {
            System.out.println(i + " ");
        }

        System.out.println("\n");
    }

    static boolean numUsed(int num, ArrayList<Integer> rightSolutions, ArrayList<Integer> leftSolutions) {
        return (rightSolutions.contains(num) || leftSolutions.contains(num));
    }

}


