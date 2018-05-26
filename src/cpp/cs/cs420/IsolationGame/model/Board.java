package cpp.cs.cs420.IsolationGame.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mayalake on 5/17/18.
 */
public class Board {
    private Character[][] board;
    //data structure, needs to use contain() for quick traverse, 
    	//need to store both move coordinates in order, (letter# optional), literally just for printing
    
    private ArrayList<int[]> userMoves = new ArrayList<int[]>();
    private ArrayList<int[]> computerMoves = new ArrayList<int[]>();
    
    private int playerX, playerY, computerX, computerY;

    public Board(boolean userTurn){
        board = new Character[StaticVals.BOARD_SIZE][StaticVals.BOARD_SIZE];
        for (int i = 0; i < StaticVals.BOARD_SIZE; ++i){
        	for (int j = 0; j < StaticVals.BOARD_SIZE; ++j){
        		board[i][j] = '-';
        	}
        }
        char firstPlayer, secondPlayer;
        
        if (userTurn){		//set player positions
        	firstPlayer = 'O';
        	secondPlayer = 'X';
        	playerX = 0;
        	playerY = 0;
        	computerX = StaticVals.BOARD_SIZE-1;
        	computerY = StaticVals.BOARD_SIZE-1;
        } else{
        	firstPlayer = 'X';
        	secondPlayer = 'O';
        	playerX = StaticVals.BOARD_SIZE-1;
        	playerY = StaticVals.BOARD_SIZE-1;
        	computerX = 0;
        	computerY = 0;
        }
        board[0][0] = firstPlayer;
        board[StaticVals.BOARD_SIZE-1][StaticVals.BOARD_SIZE-1] = secondPlayer;
    }

    //Moves the player and checks for move validity, # represents used, O for user current position
    public boolean movePlayer(int row, int col, boolean isUser){
    	if (isUser){
    		if (checkMoveValidity(playerX, playerY, row, col)){
    			board[row][col] = 'O';
    			board[playerX][playerY] = '#';
    			
    			userMoves.add(new int[]{row, col});
    			
    			playerX = row; 
    			playerY = col;
    		} else {
    			return false;
    		}
    	}
    	else {
    		//move ai on board
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
    public ArrayList<int[]> getUserMoves(){
    	return userMoves;
    }
    public ArrayList<int[]> getComputerMoves(){
    	return computerMoves;
    }
/*    public HashMap<Integer, ArrayList<Integer>> getUserMoves(){
    	return userMoves;
    }
    public HashMap<Integer, ArrayList<Integer>> getComputerMoves(){
    	return computerMoves;
    }*/
}
