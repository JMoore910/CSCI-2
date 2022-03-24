/*
 *  COP 3503 Spring 2022 Programming Assignment P4: Maze
 *  Copyright 2022 Jeanne Claire Moore
 */


import java.sql.Array;
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

class SortByMoves implements Comparator<coordinates> {
    //  Sorter for priority queue
    public int compare(coordinates x, coordinates y) {
        return x.moves-y.moves; //  Swap these if not working
    }
}

//  Coordinates will have a num of moves to make
//  Implement BFS and BACKTRACKING!!!!

public class maze {
    //  Create DX and DY arrays for 4 directions
    static int[] DX = { 1, 0,-1, 0};
    static int[] DY = { 0, 1, 0,-1};

    public static void main(String[] args) {
        //  Implement maze search with BFS and Backtracking
        //  Proceedures are as follows:
        //  Take in input using a Scanner
        //  Take in sizeY and sizeX
        Scanner sc = new Scanner(System.in);
        int sizeX;
        int sizeY;
        String in = sc.nextLine();

        sizeY = (Integer.parseInt(Arrays.asList(in.split(" ")).get(0)));
        sizeX = (Integer.parseInt(Arrays.asList(in.split(" ")).get(1)));

        coordinates start = new coordinates(MAX_VALUE,MAX_VALUE,'%');
        coordinates target= new coordinates(MAX_VALUE,MAX_VALUE,'%');

        coordinates[][] grid = new coordinates[sizeY][sizeX];





        int stars = 0;
        int finishes = 0;
        //  Take in sizeY * sizeX grid of characters
        for (int i = 0; i < sizeY; i++) {
            in = sc.nextLine();
            for (int j = 0; j < sizeX; j++) {
                grid[i][j] = new coordinates(j, i, in.charAt(j));
                if (grid[i][j].tile == '*') {
                    start = grid[i][j];
                    stars++;
                }
                if (grid[i][j].tile == '$') {
                    target = grid[i][j];
                    finishes++;
                }
            }


        }

        if ((stars > 1) || (finishes > 1) || (start.tile == '%') || (target.tile == '%'))
            return;



        //  Now that we have a grid, and a starting point, we may begin.
        //  Place starting coordinate into priority queue
        //  plug all necessary vars into mazeRunner method

        grid = mazeRunner(start,grid,sizeX,sizeY);
        if (grid[target.y][target.x].moves == MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(grid[target.y][target.x].moves);
        }
    }

    private static coordinates[][] mazeRunner(coordinates start, coordinates[][] grid, int sizeX, int sizeY){
        //  Method takes in the filled grid, the starting point, and the target
        //  Use BFS to search the areas around the current place

        //  Things we will need are a priority queue and a visited array to store places we've been
        List<coordinates> list = new ArrayList<>();
        boolean[][] visited = new boolean[sizeY][sizeX];

        //  Set cur to start and add it to the priority queue
        coordinates cur;
        list.add(start);
        int move = 0;
        int x;
        int y;
        char teleporter;
        ArrayList<coordinates> teleporters;



        while (!list.isEmpty()) {
            cur = list.get(0);
            System.out.print(cur.x + " " + cur.y);
            list.remove(0);

            grid[cur.y][cur.x].moves = move;
            visited[cur.y][cur.x] = true;

            System.out.println(cur.tile);
            //  Check if cur is a dollar sign if so return grid
            if (cur.tile == '$') {
                System.out.println("found it");
                return grid;
            }



            //  Check if current tile is a teleport tile.
            if (Character.isUpperCase(cur.tile)) {






                //  if so, search board for char teleport, and add them to priority queue
                System.out.println("Teleporter found!");

                teleporter = cur.tile;
                teleporters = teleport(teleporter,grid,sizeX,sizeY);

                for (coordinates i : teleporters) {
                    if (!visited[i.y][i.x])
                        grid[i.y][i.x].moves = cur.moves + 1;
                        list.add(i);
                }
                Collections.sort(list, new SortByMoves());
            }
            //  Use DX and DY to look at placements
            //  up down to left and right of current tile
            for (int i = 0; i < 4; i++) {
                x = cur.x + DX[i];
                y = cur.y + DY[i];
                if (isValid(sizeX, sizeY, x, y)) {
                    if ((grid[y][x].tile == '.' || grid[y][x].tile == '$' || Character.isUpperCase(grid[y][x].tile)) && !visited[y][x]) {
                        //  Add the observed tile to the priority queue
                        grid[y][x].moves = cur.moves + 1;
                        list.add(grid[y][x]);
                    }
                }
            }
        }

        return grid;
    }


    private static ArrayList<coordinates> teleport(char teleportID, coordinates[][] grid, int sizeX, int sizeY){
        //  Takes the id of the teleporter being used.
        //  Finds all points on grid that can be teleported to and add them to an arraylist of coordinates
        ArrayList<coordinates> teleporters = new ArrayList<>();

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (grid[i][j].tile == teleportID) {
                    teleporters.add(grid[i][j]);
                }
            }
        }
        //  ArrayList is returned
        return teleporters;
    }


    private static boolean isValid(int sizeX, int sizeY, int x, int y) {
        //  Simply return whether the point's
        return ((x > -1) && (x < sizeX) && (y > -1) && (y < sizeY));
    }
}
