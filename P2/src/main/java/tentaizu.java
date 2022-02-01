/*
 *  COP 3503 Spring 2022 Programming Assignment P2: Tentaizu
 *  Copyright 2022 Jeanne Claire Moore
 */

import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

//  Create an application that solves a given number of
//  tentaizu boards through the use of backtracking recursion.
//  There is a number of cases input, followed by that many boards
//  for the program to solve

public class tentaizu {
    //  Declare directional arrays
    static int dx[] = {0,1,1,1,0,-1,-1,-1};
    static int dy[] = {1,1,0,-1,-1,-1,0,1};



    //  main method
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numCases = input.nextInt();
        for (int loop = 1; loop <= numCases; loop++) {
            //  Declare tentaizu boards
            char starBoard[][] = new char[7][7];
            int numBoard[][] = new int[9][9];


            //  Initialize the numboard to all negative ones
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++) {
                    numBoard[i][j] = -1;
                }
            }

            String temp;
            //  At start of a case, read in the tentaizu board
            for (int i = 0; i < 7; i++){
                temp = input.next();
                for (int j = 0; j < 7; j++){
                    starBoard[i][j] = temp.charAt(j);

                    if (Character.isDigit(temp.charAt(j))) {
                        numBoard[i+1][j+1] = (Character.getNumericValue(temp.charAt(j)));
                    }
                }
            }


            if (!tentaizuHelper(starBoard,numBoard, 0, 1, 1, loop)) {
                System.out.println("Board #" + loop + " Was not solved \n");
            }

        }
    }

    private static boolean tentaizuHelper(char[][] starBoard, int[][] numBoard, int numStars, int x, int y, int loop){
        if (y > 7) {
            return false;
        }

        //  First check if x and y location points to a number. If so, move to the next recursion
        if (numBoard[y][x] >= 0){
            if (x == 7) {
                x = 0;
                y++;
            }

            //  Return the result of the following board permutations past this spot
            return tentaizuHelper(starBoard, numBoard, numStars, x+1, y, loop);
        }

        //  Check around current spot for any zeros. If a zero is found, that means no stars may be placed there.
        //  Also check that there is a
        boolean numFlag = false;
        boolean zeroFlag = false;

        for (int i = 0; i < 8; i++){
            if (numBoard[y+dy[i]][x+dx[i]] > -1) {
                if (numBoard[y+dy[i]][x+dx[i]] == 0) {
                    zeroFlag = true;
                }
                numFlag = true;
            }
        }

        //  Check for conditions to see if program should move to the next spot
        if (zeroFlag || !numFlag) {
            return tentaizuHelper(starBoard, numBoard, numStars, (x%7)+1,y+(x/7), loop);
        }

        //  After checking to see if conditions apply for a star to be placed, the program places a star
        starBoard[y-1][x-1] = '*';
        numStars++;

        //  Move around the new star and decrease all spots by one
        for (int i = 0; i < 8; i++) {
            numBoard[y+dy[i]][x+dx[i]]--;
        }

        //  Check to see if the star placed was the last one: only a finished board may have ten stars
        //  And all vacancies near numbers must also be filled
        if ((numStars == 10)) {
            if (numsLeft(numBoard)) {
                System.out.print("x");
                return false;
            }

            tentaizuPrinter(starBoard, loop);
            return true;
        }

        if (tentaizuHelper(starBoard, numBoard, numStars, (x%7)+1,y+(x/7), loop)) {
            return true;
        }

        //  If true is not returned above, the board remains unsolved. Backtrack by removing the star, reduce numstars,
        //  and continue recursion

        starBoard[y-1][x-1] = '.';

        if (x == 7) {
            x = 0;
            y++;
        }

        return (tentaizuHelper(starBoard,numBoard,numStars-1,(x%7)+1,y+(x/7),loop));
    }

    private static void tentaizuPrinter(char[][] starBoard, int loop) {
        //  print out all characters on the board
        System.out.println("Tentaizu Board #1:");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++){
                System.out.print(starBoard[i][j]);
            }
            System.out.print("\n");
        }
    }

    private static boolean numsLeft(int [][] numBoard) {
        //  When 10 stars have been placed, this method checks if all numbers on numboard have been reduced to zero
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (numBoard[i][j] > 0) return true;
            }
        }
        return false;
    }

}
