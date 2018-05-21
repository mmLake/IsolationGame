package cpp.cs.cs420.IsolationGame.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mayalake on 5/17/18.
 */
public class Board {
    private Character[][] board;
    private ArrayList<String> userMoves = new ArrayList<String>();
    private ArrayList<String> computerMoves = new ArrayList<String>();
    private int playerX, playerY, computerX, computerY;

    public Board(boolean userTurn){
        board = new Character[StaticVals.BOARD_SIZE][StaticVals.BOARD_SIZE];
        for (int i = 0; i < StaticVals.BOARD_SIZE; ++i){
        	for (int j = 0; j < StaticVals.BOARD_SIZE; ++j){
        		board[i][j] = '-';
        	}
        }
        //set initial board state
        char firstPlayer = (userTurn? 'O' : 'X');
        char secondPlayer = (!userTurn? 'O' : 'X');

        board[0][0] = firstPlayer;
        board[StaticVals.BOARD_SIZE-1][StaticVals.BOARD_SIZE-1] = secondPlayer;
    }

    public boolean movePlayer(int row, int col, boolean isUser){
    	if (isUser){
    		if (checkMoveValidity(playerX, playerY, row, col)){
    			board[row][col] = 'O';
    			board[playerX][playerY] = '#';
    			//userMoves.add()
    			// to do, user arraylist of string or of integer[] 
    			playerX = row; 
    			playerY = col;
    		} else {
    			return false;
    		}
    	}
    	return true;
    }
    
    //if minimizer has upper hand, then negative value
    //store board states in a tree in boardcontroller? remove unused states then only expand leaf nodes?
    public int getValue(){
    	return 0;
    	//return value for minimizer/maximizer to use
    }
    // check if inputed moves are valid, checking for board position, used tiles, computer location
    public boolean checkMoveValidity(int oldX, int oldY, int x, int y){
    	if (x < 0 || x > 7 || y < 0 || y > 7){
    		return false;
    	}
    	double numerator = (double)oldY - (double)y;
    	double denominator = (double)oldX - (double)x;
    	
    	double slope = (numerator)/(denominator);

		int smallerY = ((denominator >= 0) ? y : oldY);
		int biggerY = ((denominator >= 0) ? oldY : y);
		int smallerX = ((numerator >= 0) ? x : oldX);
		int biggerX = ((numerator >= 0) ? oldX : x);
    		
    	if (Double.isInfinite(slope)){	//vertical
    		for (int i = smallerY; i <= biggerY; ++i){
    			if (board[x][i] == '#')
    				return false;
    		}
    		return true;
    	}else if (slope == 0){ //horizontal
    		for (int i = smallerX; i <= biggerX; ++i){
    			if (board[i][y] == '#')
    				return false;
    		}
    		return true;
    	}
    	else if (slope == 1){ // positive diagonal
    		for (int i = smallerX; i <= biggerX; ++i){
    			if (board[smallerX+i][smallerY+i] == '#'){
    				return false;
    			}
    		}
    		return true;
    	} else if(slope == -1){	// negative diagonal
    		for (int i = smallerX; i <= biggerX; ++i){
    			if (board[smallerX-i][smallerY-i] == '#'){
    				return false;
    			}
    		}
    		return true;
    	}else {
    		return false;
    	}
    }
    	
    public Character[][] getBoard(){
        return board;
    }
    public void setBoardPosition(int x, int y, char z){
    	board[x][y] = z;
    }
    public ArrayList<String> getUserMoves(){
    	return userMoves;
    }
    public ArrayList<String> getComputerMoves(){
    	return computerMoves;
    }
}
