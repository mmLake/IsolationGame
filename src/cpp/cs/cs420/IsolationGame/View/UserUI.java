package cpp.cs.cs420.IsolationGame.View;

import java.util.Scanner;

import cpp.cs.cs420.IsolationGame.Controller.BoardController;
import cpp.cs.cs420.IsolationGame.model.Board;
import cpp.cs.cs420.IsolationGame.model.StaticVals;


/**
 * Created by mayalake on 5/17/18.
 */
public class UserUI {
	BoardController bc = new BoardController(true);
	
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
    	int x = 0, y = 0;
    	String playerMove = sc.nextLine().replaceAll("\\s","");
    	
    	//System.out.println(playerMove);
    	
    	try {	//error check
	    	x = Integer.parseInt(playerMove.split(",")[0]);
	    	y = Integer.parseInt(playerMove.split(",")[1]);
    	} catch (NumberFormatException e){
    		System.out.println(e.getMessage());
    		playerMove();
    	}
    	
    	if (!bc.movePlayer(x, y, true)){	// if movement is not possible
    		//check valid (board boundaries, used slots, other player coordinates in boardcontroller-> board
    		playerMove();
    	} else {	// on successful move, print new board?
    		printBoard(bc.getBoard());
    	}
    }
    // print board using the board object
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
    
    // print player and computer moves using arraylist of int[] from board class
    public void printMoves(Board board){
    	StringBuilder sb = new StringBuilder(StaticVals.VERSUS);
    	int size = (board.getUserMoves().size() > board.getComputerMoves().size() ? 
    			board.getUserMoves().size() : board.getComputerMoves().size());
    	
    	for (int i = 0; i < size; ++i){	//# rows equal to size of usermoves list
    		sb.append((i+1) + ".\t");	//print turn count
    		
    		if (board.getComputerMoves().size() >= i && board.getComputerMoves().size() > 0){	//check for empty slot, prevent null pointer
    			sb.append(convertNumToChar(board.getComputerMoves().get(i)[0])); 
    			sb.append(board.getComputerMoves().get(i)[1] + "\t ");
    		} else {
    			sb.append("\t");
    		}
    		if (board.getUserMoves().size() >= i  && board.getUserMoves().size() > 0){
    			sb.append(convertNumToChar(board.getUserMoves().get(i)[0])); 
    			sb.append(board.getUserMoves().get(i)[1] + "\t ");
    		} else {
    			sb.append("\t");
    		}
    	}
    	System.out.println(sb.toString());
    }
    //convert rows of the arraylist<int[]> data structure in board, which stores the size 2 array, where the 
    //first represents the row, second the col
    public char convertNumToChar(int row){
    	System.out.println("row =" + row);
    	switch (row){	//add breaks?
	    	case 0 : return 'A';
	    	case 1 : return 'B';
	    	case 2 : return 'C';
	    	case 3 : return 'D';
	    	case 4 : return 'E';
	    	case 5 : return 'F';
	    	case 6 : return 'G';
	    	case 7 : return 'H';
    	}
    	return 'z';
    }
}
