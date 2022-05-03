/*
 *  COP 3503 Spring 2022 Programming Assignment P6: Roadrace
 *  Copyright 2022 Jeanne Claire Moore
 */


import java.util.Scanner;

public class roadrace {
    //  Dynamic Programming Globals needed:
    //  Two int vars to store the num of segments, and the num of lanes per segment
    //  A 1D array storing lane change times based on lane difference
    //  A 1D array storing the distances of each segment
    //  A 2D array storing the average speeds found in each lane
    //  A 2D array storing the dynamic programming memoization of the shortest times to each lane

    private static int n;
    private static int m;
    private static int[] changeLane;
    private static int[] dist;
    private static int[][] lanes;
    private static double[][] dp;

    public static void main(String[] args) {

        //  Call read input
        readInput();

        //  fill array changeLane
        for (int i = 1; i <= m; i++){
            changeLane[i] = (i*i)+5;
        }

        //  Go through each segment
        for (int i = 0; i < n; i++){
            //  If on the first segment, 0, then simply apply the time formula to each lane's speed
            //  and store the corresponding time in the dp array

            if (i == 0) {

                // Move through each lane and calculate time to travel each
                for (int j = 1; j <= m; j++) {
                    dp[i][j] = (dist[i]*3600)/(1.0 * (lanes[i][j] * 1000));
                }
            }
            //  If not looking at the first segment, the program must go through the previous lanes time and find the
            //  least time adding on the time to change lanes from the current one


            else {
                //  Call dynamic programming method
                dynamicRace(i);
            }



        }

        //  Set lowest to be a max value, find lowest value representing shortest path
        double lowest = Double.MAX_VALUE;
        for (int j = 1; j <= m; j++) {
            //  If lowest is larger than current dp element, set dp[n-1][j] to be lowest
            if (lowest > dp[n-1][j])
                lowest = dp[n-1][j];
        }

        //  Print out shortest path
        printPath();

        //  Print out shortest distance
        System.out.printf("%.2f ",lowest);
    }


    private static void dynamicRace(int i){
        //  Go through each element in dp calculating shortest time to reach and traverse that lane
        for (int j = 1; j <= m; j++) {
            double lowest = Integer.MAX_VALUE;
            for (int k = 1; k <= m; k++) {
                int change = Math.abs(k-j);
                if (change == 0){
                    //  No lane change
                    lowest = Math.min(lowest, dp[i-1][k] + 5);
                }
                else {
                    //  Lane change, so add change time
                    lowest = Math.min(lowest, dp[i-1][k] + changeLane[change]);
                }
            }

            //  We now have lowest, assign it to dp[i][j] and add the time it takes to traverse this segment
            dp[i][j] = (lowest + ((dist[i]*3600)/(1.0 * (lanes[i][j] * 1000))));
        }


    }


    private static void printPath() {
        //  Initialize a lowest variable and index
        int lowestIndex;
        double lowest;
        int[] path = new int[n];

        //  Start at the end of dp and move in reverse to build back the path
        for (int i = n-1; i >= 0; i--) {
            //  For each segment start, set the lowest to be the max value
            lowest = Double.MAX_VALUE;
            lowestIndex = 0;
            if (i == n-1) {
                //  find lowest time in lane in last legment
                for (int j = 1; j <= m; j++) {
                    //  If the lowest is larger than the current value, we have found the new lowest
                    if (lowest > dp[i][j]) {
                        lowestIndex = j;
                        lowest = dp[i][j];
                    }
                }
            }
            else {
                //  find lowest time in lane plus lane change
                int last = path[i+1];
                int change;
                for (int j = 1; j <= m; j++) {
                    change = Math.abs(j - last);
                    //  Use change time to determine whether this is the new lowest time value or not
                    if (lowest > (dp[i][j] + changeLane[change])) {
                        lowestIndex = j;
                        lowest = dp[i][j];
                    }
                }
            }

            //  Set lowest index to path at i
            path[i] = lowestIndex;
        }


        //  Go through and print the path
        for (int i = 0; i < n; i++) {
            System.out.print(path[i] + " ");
        }
    }



    private static void readInput() {

        //  Set scanner to take in input
        Scanner input = new Scanner(System.in);

        String line = input.nextLine();
        String[] nums = line.split(" ");

        //  Read in num of segments n and num of lanes per segment m
        n = Integer.parseInt(nums[0]);
        m = Integer.parseInt(nums[1]);

        //  Initialize data and dp arrays
        dist = new int[n];
        lanes = new int[n][m+1];
        changeLane = new int[m+1];
        dp = new double[n][m+1];

        //  Read in distance array
        nums = input.nextLine().split(" ");

        for (int i = 0; i < n; i++){
            dist[i] = Integer.parseInt(nums[i]);
        }

        //  Read in lanes array
        for (int i = 0; i < n; i++){
            nums = input.nextLine().split(" ");
            for (int j = 1; j <= m; j++) {
                lanes[i][j] = Integer.parseInt(nums[j-1]);
            }
        }
    }
}
