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

    public Board(Board prevBoard){
        this.board = prevBoard.board;

        this.userTurn = !prevBoard.userTurn;
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
    //store board states in a tree in boardcontroller? remove unused states then only expand leaf nodes?
    public int getValue(){
    	return 0;
    	//return value for minimizer/maximizer to use
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
