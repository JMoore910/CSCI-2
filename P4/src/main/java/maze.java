/*
 *  COP 3503 Spring 2022 Programming Assignment P4: Maze
 *  Copyright 2022 Jeanne Claire Moore
 */


import java.util.*;
import java.lang.String;
import static java.lang.Integer.MAX_VALUE;

//  class coordinates contains the coordinates of a tile and it's contents
class coordinates {
    public char tile;
    public int x;
    public int y;
    public int moves;

    //  Create a constructor for coordinates
    public coordinates(int x, int y, char tile){
        this.x = x;
        this.y = y;
        this.tile = tile;
        moves = MAX_VALUE;
    }
}

//  Coordinates will have a num of moves to make
//  Implement BFS and BACKTRACKING!!!!

public class maze {
    //  Create DX and DY arrays
    static int[] DX = { 1, 1, 0,-1,-1,-1, 0, 1};
    static int[] DY = { 0, 1, 1, 1, 0,-1,-1,-1};

    public static void main(String[] args) {
        //  Implement maze search with BFS and Backtracking
        //  Proceedures are as follows:
        //  Take in input using a tokenizer
        //  Take in sizeY and sizeX
        //  Take in sizeY * sizeX grid of characters
        //  While taking in input keep track of num of *s and $s in grid.
        //  Set start coordinate with *
        //  Set target coordinate with $
        //  Create a priority queue to store possible moves
        //  At end if either * count or $ count do not equal 1, return -1

        //  Now that we have a grid, and a starting point, we may begin.
        //  Place starting coordinate into priority queue
        //  plug all necessary vars into mazeRunner method
    }

    //  Make method recursive for traversing maze pathways
    private static coordinates[][] mazeRunner(coordinates start, coordinates target,
                                              coordinates[][] grid, coordinates[] visited, int moves,
                                              PriorityQueue<coordinates> priorityQueue,  int sizeX, int sizeY ){
        //  ALWAYS CHECK IF MOVES IS LARGER THAN CURRENT, if so return the grid
        //  Run while priority queue is not empty. After the priority queue is empty, return grid
        //  coordinates location = priorityQueue.poll()
        //  If standing on a Capital letter (regex [A-Z]) find teleport tiles with teleport method
        //      and add what has not been visited yet to the priority queue
        //  Then add all places around current location that are valid and not visited using DX and DY
        //  if isvalid && notvisited
        //      if grid[DY + y][DX + x].tile == '.'
        //          here is a valid point to add to the priority queue
        //
        //  Once priority queue has all possible moves from location, call recursively with moves+1
        return grid;
    }


    private static ArrayList<coordinates> teleport(char teleportID, coordinates curLocation, coordinates[][] grid){
        //  Takes the id of the teleporter being used.
        //  Finds all points on grid that can be teleported to and add them to an arraylist of coordinates
        //  ArrayList is returned
        return new ArrayList<>();
    }

    private static boolean visited(coordinates point, ArrayList<coordinates> visited){
        //  loop through and check if point falls within the visited array list, if so return true
        return false;
    }


    private static boolean isValid(int sizeX, int sizeY, int x, int y) {
        //  Simply return whether the point's
        return ((x < sizeX) && (y < sizeY));
    }
}
