/*
 *  COP 3503 Spring 2022 Programming Assignment RP3: ternarian weights
 *  Copyright 2022 Jeanne Claire Moore
 */

//  To find the solution to the weights problem:
//  at the start the target is added from input as a target. That target becomes the balance of the
//  plates at the start, always being negative. When looping, if the balance is negative, add to the
//  right side, otherwise if it's positive add to the left.

//  The method to find the next weight to add is as follows: start with a sum and number.
//  The number is added to sum with each loop and loop runs until it either finds the last
//  available weight, or a weight whose sum is greater than or equal to the target. Add that
//  weight to the appropriate pan, and perform an operation (+ if right / - if left) on the
//  balance to find a new balance. This process repeats until the balance reaches 0


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

            doSolutions(-1 * parseInt(br.readLine()), rightSolutions,leftSolutions);
        }
    }

    static void doSolutions(int balance, ArrayList<Integer> rightSolutions, ArrayList<Integer> leftSolutions) {

        int sum;
        boolean addingRight;
        ArrayList<Integer> used = new ArrayList<>();


        while (balance != 0) {
            //  Assign whether we are adding to right side or left side based on balance
            addingRight = balance < 0;

            //  Base case: If the balance is 1 or -1 add it to the appropriate side, print and return;
            if (abs(balance) == 1) {
                if (addingRight)
                    rightSolutions.add(1);
                else
                    leftSolutions.add(1);

                printSolutions(rightSolutions, leftSolutions);
                return;
            }

            //  Find weight that makes sum of all weights above or equal to the balance
            int above = 3;
            int below = 1;
            sum = below + above;

            while (sum < abs(balance)) {
                above *= 3;
                below *= 3;
                sum += above;
            }

            //  At this point we should be looking at an above and below that the balance lies between.
            //  Check if the weights contains above and use that, if not use below instead
            int next;
            if (!used.contains(above))
                next = above;
            else
                next = below;

            //  Since next is being used use next
            used.add(next);

            if (addingRight) {
                balance += next;
                rightSolutions.add(next);
            } else {
                balance -= next;
                leftSolutions.add(next);
            }


        }
        //  Both pans are complete, go ahead and print them
        printSolutions(rightSolutions, leftSolutions);
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
            System.out.print(i + " ");
        }

        System.out.println("\n");
    }
}


