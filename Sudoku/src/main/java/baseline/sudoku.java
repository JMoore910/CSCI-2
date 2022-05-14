/*
 *  Copyright Jeanne Claire Moore
 *  Summer 2022
 */


package baseline;

//  Application goal: Use backtracking to solve a given baseline.sudoku board. If a board is unsolvable, simply print impossible
//  If a board IS solvable, print the board to screen

public class sudoku {
    public static void main(String[] args) {

    }


    /*
    Sudoku solver method:
        is a recursive, backtracking
        the method goes horizontally. When it reaches the last slot in a quadrant, go through all options
        and check the quadrant



     */



    /*
    Helper method to take in a sudoku board as input
    



     */
}


class sudokuHelpers{
    //  Helper Methods Needed:

    /*  boolean Check Row(board, x, y)
            char cur = board[y][x]
            for (int i = 0; i < 9; i++){
                if (i != y) {
                    if (board[i][x]) == cur
                        return false
                }
            }
            return true
    */


    /*  boolean Check Col(board, x, y)
            char cur = board[y][x]
            for (int i = 0; i < 9; i++){
                if (i != x) {
                    if (board[y][i]) == cur
                        return false
                }
            }
            return true
     */


    /*  boolean Check Quad(board, x, y)
            cur = board[y][x]
            //  For each coordinate divide their value by 3
            xquad = 3 * (floor(x/3.0))
            yquad = 3 * (floor(y/3.0))
            for (int i = xquad; i < 3+xquad; i++){
                for (int j = yquad; j < 3+yquad; j++) {
                    if (board[i][j] == cur)
                        return false
            }
            return true
     */


    /*  boolean Final Check(board)
            boolean check true
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    check = checkCol(board, j, i) && checkRow(board, j, i) && checkQuad(board, j, i)
                    if (!check)
                        return check

                }
            }
            return check

     */


    /*  sudokuBoard printBoard(board)
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    sout board[i][j]
                }
                sout \n
            }



     */
}
