/*
 *  COP 3503 Spring 2022 Programming Assignment P2: Tentaizu
 *  Copyright 2022 Jeanne Claire Moore
 */

import java.util.*;

//  Create an application that solves a given number of
//  tentaizu boards through the use of backtracking recursion.
//  There is a number of cases input, followed by that many boards
//  for the program to solve

public class tentaizu {
    //  Declare directional arrays
    static int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
    static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};


    //  main method
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numCases = input.nextInt();
        for (int loop = 1; loop <= numCases; loop++) {
            //  Declare tentaizu boards
            char[][] starBoard = new char[7][7];
            int[][] numBoard = new int[9][9];


            //  Initialize the numboard to all negative ones
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    //  Boarder spots should be labeled 9 and should be off limits
                    if ((i == 0) || (i == 8) || (j == 0) || (j == 8)) {
                        numBoard[i][j] = 9;
                    } else
                        numBoard[i][j] = -1;
                }
            }


            String temp;
            //  At start of a case, read in the tentaizu board
            for (int i = 0; i < 7; i++) {
                temp = input.next();
                for (int j = 0; j < 7; j++) {
                    starBoard[i][j] = temp.charAt(j);

                    if (Character.isDigit(temp.charAt(j))) {
                        numBoard[i + 1][j + 1] = (Character.getNumericValue(temp.charAt(j)));
                    }
                }
            }

            if (!tentaizuHelper(starBoard, numBoard, 0, 0, loop)) {
                System.out.println("Board #" + loop + " Was not solved \n");
            }

        }
    }

    //  Recursive method that moves through all possible spots on the board
    private static boolean tentaizuHelper(char[][] starBoard, int[][] numBoard, int numStars, int k, int loop) {
        //  k represents the spot in the 2D array we are looking at

        if (numStars == 10) {

            if (!checkStars(starBoard)) {
                return false;
            }
            tentaizuPrinter(starBoard, loop);
            return true;
        }

        for (int i = k; i < 49; i++) {
            //  Set location vars
            int x = (i % 7) + 1;
            int y = (i / 7) + 1;

            //  Check if a zero is present around this number
            //  Also check if this spot is valid for a star
            if (findZero(numBoard, x, y) || (numBoard[y][x] != -1)) {
                continue;
            }

            //  If the current number is a zero, each spot around it must be labeled 9 aka off limits for stars added
            if (numBoard[y][x] == 0 && starBoard[y - 1][x - 1] == '0') {
                for (int j = 0; j < 8; j++) {
                    if (numBoard[y + dy[j]][x + dx[j]] == -1 || numBoard[y + dy[j]][x + dx[j]] > 10) {
                        //  place a 9 for off limits
                        numBoard[y + dy[j]][x + dx[j]] += 12;
                    }
                }

            }

            //  Now we know that we are in fact looking at an empty space that is not near a zero.
            //  Add a star
            starBoard[y - 1][x - 1] = '*';
            numBoard[y][x] = 10;

            int[] temp = new int[8];

            //  Then sub all values around it by 1 that are number spots
            for (int j = 0; j < 8; j++) {
                if ((numBoard[y + dy[j]][x + dx[j]] > 0) && (numBoard[y + dy[j]][x + dx[j]] < 9)) {
                    temp[j] = numBoard[y + dy[j]][x + dx[j]];

                    //  If a num is going to be subbed to zero, make the area around it off limits
                    if (temp[j] == 1) {
                        for (int l = 0; l < 8; l++) {
                            if (numBoard[y + dy[j] + dy[l]][x + dx[j] + dy[l]] > 10) {
                                numBoard[y + dy[j] + dy[l]][x + dx[j] + dy[l]] += 12;
                            }
                        }

                    }
                    numBoard[y + dy[j]][x + dx[j]] = temp[j] - 1;
                }
            }

            //  recurse forwards at i+1
            if (tentaizuHelper(starBoard, numBoard, numStars + 1, i + 1, loop)) {
                return true;
            }

            //  remove the star and move to next loop

            starBoard[y - 1][x - 1] = '.';
            numBoard[y][x] = -1;

            //  Then sub all values around it by 1 that are number spots
            for (int j = 0; j < 8; j++) {
                if ((numBoard[y + dy[j]][x + dx[j]] > -1) && (numBoard[y + dy[j]][x + dx[j]] < 9)) {
                    numBoard[y + dy[j]][x + dx[j]] = temp[j];
                    if (temp[j] == 1) {
                        for (int l = 0; l < 8; l++) {
                            if (numBoard[y + dy[j] + dy[l]][x + dx[j] + dy[l]] > 10) {
                                numBoard[y + dy[j] + dy[l]][x + dx[j] + dy[l]] -= 12;
                            }
                        }
                    }
                }
            }
        }


        return false;
    }


    //  Find a zero around the x,y location specified return true if one is found
    private static boolean findZero(int[][] numBoard, int x, int y) {
        for (int i = 0; i < 8; i++) {
            if (numBoard[y + dy[i]][x + dx[i]] == 0) {
                return true;
            }
        }
        return false;
    }


    //  Print board method
    private static void tentaizuPrinter(char[][] starBoard, int loop) {
        //  print out all characters on the board
        System.out.printf("Tentaizu Board #%d:\n", loop);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(starBoard[i][j]);
            }
            System.out.print("\n");
        }

        System.out.print("\n");
    }


    //  Validate board and see if any nums are left on board between 0 and 9
    private static boolean checkStars(char[][] starBoard) {
        //  When 10 stars have been placed, this method checks if all numbers on numboard have been reduced to zero
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                //  Check star board to see if all nums have the right amount of stars around them
                if (Character.isDigit(starBoard[i][j])) {
                    int stars = Character.getNumericValue(starBoard[i][j]);
                    int temp = 0;

                    for (int k = 0; k < 8; k++) {
                        //  Check to see that the location is valid so we don't go out of bounds
                        if (!isValid(j + dx[k], i + dy[k]))
                            continue;

                        if (starBoard[i + dy[k]][j + dx[k]] == '*') {
                            temp++;
                        }
                    }

                    if (stars != temp) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //  return whether the input coordinates are valid
    private static boolean isValid(int x, int y) {
        return x != -1 && x != 7 && y != -1 && y != 7;
    }

}