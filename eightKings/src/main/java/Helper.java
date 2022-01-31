/*
 *  Copyright 2022 Spring 2022 Jeanne Moore
 *  UCF Spring 2022 Jeanne Moore
 */

import static java.lang.Math.abs;

    public class Helper {

    public void eightKingsMethod(int[] board, int k) {
        //  Method starts a for loop and iterates through every possible column to add at row k
        for (int i = 0; i < 8; i ++) {

            //  Check if we are observing the first row
            if (k == 0){
                //  Place the first king and continue
                board[k] = i;
                eightKingsMethod(board,k+1);
                continue;
            }

            //  Check if we have finished the chess board
            if (k == 8){
                printBoard(board);
                return;
            }

            //  Skip condition one: The column we want to insert is already used
            if (boardContains(board,i,k)) {
                //  This column is already used, continue
                continue;
            }

            //  Skip condition two: The previous row contains a column adjacent to current one
            if (abs(board[k-1]-i) < 2) {
                //  This column is diagonal to a previous column, continue
                continue;
            }

            //  All skip conditions have been passed, the king may now be inserted
            board[k] = i;
            eightKingsMethod(board,k+1);
        }
    }

    public void printBoard(int[] board) {
        //  Method prints out the board
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < board[i]; j++){
                System.out.print("_ ");
            }

            System.out.print("K ");

            for (int j = 0; j < 7 - board[i]; j++){
                System.out.print("_ ");
            }

            System.out.print("\n");
        }
        System.out.print("\n\n\n");
    }

    public boolean boardContains(int[] board, int col, int k){
        //  Method returns whether a column is already used in the board
        for (int i = 0; i <= k; i++) {
            if (board[i] == col) {
                //  Column has been found within the board already
                return true;
            }
        }
        return false;
    }
}
