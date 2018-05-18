package cpp.cs.cs420.IsolationGame.model;

import java.util.ArrayList;

/**
 * Created by mayalake on 5/17/18.
 */
public class Board {
    private Character[][] board;
    private ArrayList userVisitedTiles = new ArrayList();
    private ArrayList computerVisitedTiles = new ArrayList();

    public Board(boolean userTurn){
        board = new Character[StaticVals.BOARD_SIZE][StaticVals.BOARD_SIZE];

        //set initial board state
        char firstPlayer = (userTurn? 'O' : 'X');
        char secondPlayer = (!userTurn? 'O' : 'X');

        board[0][0] = firstPlayer;
        board[StaticVals.BOARD_SIZE-1][StaticVals.BOARD_SIZE-1] = secondPlayer;
    }

    public void movePlayer(int row, int col, char player){
        board[row][col] = player;
    }

    public Character[][] getBoard(){
        return board;
    }

}
