/*
 *  Copyright 2022 Spring 2022 Jeanne Moore
 *  UCF Spring 2022 Jeanne Moore
 */

/*
    Create an application that prints out every possible board of eight kings through backtracking.
 */

public class EightKings {
    public static void main(String[] args) {
        Helper helper = new Helper();
        helper.eightKingsMethod(new int[8], 0);
    }
}