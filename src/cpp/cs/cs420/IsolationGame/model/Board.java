package cpp.cs.cs420.IsolationGame.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mayalake on 5/17/18.
 */
public class Board {
    private Character[][] board;
    private int playerX, playerY, computerX, computerY;
    private boolean userTurn;

    private ArrayList<int[]> userMoves = new ArrayList<int[]>();
    private ArrayList<int[]> computerMoves = new ArrayList<int[]>();

    public Board(Board prevBoard, boolean userTurn){
        this.board = prevBoard.board;

//        this.userTurn = !prevBoard.userTurn;
        this.userTurn = userTurn;
        this.userMoves = prevBoard.userMoves;
        this.computerMoves = prevBoard.computerMoves;
        this.playerX = prevBoard.playerX;
        this.playerY = prevBoard.playerY;
        this.computerX = prevBoard.computerX;
        this.computerY = prevBoard.computerY;
    }

    public Board(boolean userTurn){
        board = new Character[StaticVals.BOARD_SIZE][StaticVals.BOARD_SIZE];

        //initialize empty board
        for (int i = 0; i < StaticVals.BOARD_SIZE; ++i){
        	for (int j = 0; j < StaticVals.BOARD_SIZE; ++j){
        		board[i][j] = '-';
        	}
        }

        //set user and computer positions on board
        char firstPlayer, secondPlayer;
        
        if (userTurn){
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
        this.userTurn = userTurn;
    }

    
    //if minimizer has upper hand, then negative value
    // returns # of available moves in the current board
    public int getValue(int posX, int posY){
//    	System.out.println(posX + ", " + posY + ", " + board[posX][posY]); 
    	int availableMoves = 0;
    	boolean top = true, topRight = true, right = true, botRight = true, 
    			bot = true, botLeft = true, left = true, topLeft = true;
    	
    	for (int i = 1; i < StaticVals.BOARD_SIZE; ++i){
    		
    		if (checkPosition(posX, posY+i, top)){
    			++availableMoves;
    		} else{
    			top = false;
    		}
    		if (checkPosition(posX+i, posY+i, topRight)){
    			++availableMoves;
    		} else{
    			topRight = false;
    		}
    		if (checkPosition(posX+i, posY, right)){
    			++availableMoves;
    		} else{
    			right = false;
    		}
    		if (checkPosition(posX+i, posY-i, botRight)){
    			++availableMoves;
    		} else{
    			botRight = false;
    		}
    		if (checkPosition(posX, posY-i, bot)){
    			++availableMoves;
    		} else{
    			bot = false;
    		}
    		if (checkPosition(posX-i, posY-i, botLeft)){
    			++availableMoves;
    		} else{
    			botLeft = false;
    		}
    		if (checkPosition(posX-i, posY, left)){
    			++availableMoves;
    		} else{
    			left = false;
    		}
    		if (checkPosition(posX-i, posY+i, topLeft)){
    			++availableMoves;
    		} else{
    			topLeft = false;
    		}
    	}
    	return availableMoves;
    }
    //helper method, pass in available moves for a certain board position
    private boolean checkPosition(int x, int y, boolean keepChecking){
    	if (x < StaticVals.BOARD_SIZE && y < StaticVals.BOARD_SIZE && x >= 0 && y >= 0 && keepChecking && board[x][y] == '-'){
//    		System.out.println(x + ", " + y);
    		return true;
    	}
    	return false;
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

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getComputerX() {
        return computerX;
    }

    public void setComputerX(int computerX) {
        this.computerX = computerX;
    }

    public int getComputerY() {
        return computerY;
    }

    public void setComputerY(int computerY) {
        this.computerY = computerY;
    }

    public boolean isUserTurn() {
        return userTurn;
    }

}
