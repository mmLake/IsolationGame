package cpp.cs.cs420.IsolationGame.View;

import java.util.Scanner;

import cpp.cs.cs420.IsolationGame.Controller.BoardController;
import cpp.cs.cs420.IsolationGame.model.Board;
import cpp.cs.cs420.IsolationGame.model.StaticVals;


/**
 * Created by mayalake on 5/17/18.
 */
public class UserUI {
	BoardController bc;// = new BoardController(true);
	
    Scanner sc = new Scanner(System.in);

    public void welcomeMessage(){
        System.out.printf("Welcome to the Isolation Game. To win, be the last one to make a move\n");
    }

    public Character enterFirstPlayer(){
        char firstPlayer;
        do{
            System.out.printf("Enter 'O' to play first or enter 'X' for computer to play first\n");
            firstPlayer = sc.nextLine().toUpperCase().charAt(0);
        }while((firstPlayer != 'X') &&  (firstPlayer != '0'));

        return firstPlayer;
    }
    
    // Player enters a coordinate system input to move their piece
    public void playerMove(){
    	System.out.println("Enter a coordinate to move to: x, y");
    	int x, y;
    	while (true){
    		//check valid (board boundaries, used slots, other player coordinates in boardcontroller-> board
    	}
    }
    // print board
    public void printBoard(Board board){
    	StringBuilder sb = new StringBuilder(StaticVals.BOARD_COLUMNS); 

    	for (int i = 0; i < StaticVals.BOARD_SIZE; ++i){
    		for (int j = -1; j < StaticVals.BOARD_SIZE; ++j){
    			if (j == -1){
    				sb.append(StaticVals.ALPHABET[i] + " ");
    			} else{
    				sb.append(board.getBoard()[i][j] + " ");
    			}
    		}
    		sb.append("\n");
    	}
    	System.out.println(sb.toString());
    }
    
    // print player and computer moves
    public void printMoves(Board board){
    	StringBuilder sb = new StringBuilder(StaticVals.VERSUS);
    	int size = (board.getUserMoves().size() > board.getComputerMoves().size() ? 
    			board.getUserMoves().size() : board.getComputerMoves().size());
    	for (int i = 0; i < size; ++i){
    		sb.append("\t" + i + ". ");
    		if (board.getComputerMoves().size() >= i){
    			sb.append(board.getComputerMoves().get(i) + "\t");
    		} else {
    			sb.append("\t");
    		}
    		if (board.getUserMoves().size() >= i){
    			sb.append(board.getUserMoves().get(i) + "\t");
    		} else {
    			sb.append("\t");
    		}
    	}
    	System.out.println(sb.toString());
    }
}
