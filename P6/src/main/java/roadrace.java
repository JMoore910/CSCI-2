/*
 *  COP 3503 Spring 2022 Programming Assignment P6: Roadrace
 *  Copyright 2022 Jeanne Claire Moore
 */


import java.util.Arrays;
import java.util.Scanner;

public class roadrace {
    //  Dynamic Programming Globals needed:
    //  A 1D array storing d^2 + 5 for every d
    //  A 2D array travel time on each lane
    private static int n, m;
    private static int[] changeLane, dist;
    private static int[][] travelTime, lanes;

    public static void main(String[] args) {

        //  Call read input
        readInput();


        //  application takes in num segments n, and num lanes m
        //  the next line contains n segment lengths in meters
        //  the following n lines contains m different average speeds for each lane

        //  Created a 2D array storing all lane speeds
        //  in first row choose row with highest speed


        //  Initialize dpArray to size of num of lanes
        //  dp array is a global that stores the change time

        //  To track the lowest time I will follow the same method of solution as the 0-1 knapsack problem
        //  A single 1D array holding the shortest time will go through the whole 2d array of lane speeds and determine
        //  the lowest time

        //  **********      DP array starts off holding the first three travel times
        //  *50 50 60*      Store the travel times of each lane in a 2d array so that calculations may only be made once
        //  *60 50 50*
        //  *50 60 50*      Looking at every spot, take the lowest time produced by each of the times in the
        //  *40 30 30*      previous lanes, factoring in the
        //  **********

    }


    private static void readInput() {

        //  INPUT:
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        m = input.nextInt();
        input.nextLine();


        //  Initialize data and dp arrays
        dist = new int[n];
        lanes = new int[n][m+1];
        changeLane = new int[m+1];
        travelTime = new int[n][m+1];

        //  Fill the two dp arrays with negative vals
        Arrays.fill(travelTime, -1);
        Arrays.fill(changeLane, -1);

        //  Read in distance array
        for (int i = 0; i < n; i++){
            dist[i] = input.nextInt();
        }
        input.nextLine();

        //  Read in lanes array
        for (int i = 0; i < n; i++){
            for (int j = 1; j < m; j++) {
                lanes[i][j] = input.nextInt();
            }
            input.nextLine();
        }
    }
}
