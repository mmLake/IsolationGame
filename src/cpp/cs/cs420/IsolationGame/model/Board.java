package cpp.cs.cs420.IsolationGame.model;

import java.util.ArrayList;

/**
 * Created by mayalake on 5/17/18.
 */
public class Board {
    private Character[][] board;
    private ArrayList userVisitedTiles = new ArrayList();
    private ArrayList computerVisitedTiles = new ArrayList();
    private int playerX, playerY;

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
    
    //if minimizer has upper hand, then negative value
    //store board states in a tree in boardcontroller? remove unused states then only expand leaf nodes?
    public int getValue(){
    	return 0;
    	//return value for minimizer/maximizer to use
    }
    // check if inputted moves are valid, checking for board position, used tiles, computer location
    public boolean checkMoveValidity(int x, int y){
    	return false;
    }
    	
    public Character[][] getBoard(){
        return board;
    }
    
}
