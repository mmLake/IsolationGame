package cpp.cs.cs420.IsolationGame.View;

import java.util.Scanner;

import cpp.cs.cs420.IsolationGame.Controller.BoardController;
import cpp.cs.cs420.IsolationGame.model.Board;
import cpp.cs.cs420.IsolationGame.model.StaticVals;


public class UserUI {
    Scanner sc = new Scanner(System.in);
    
    public void startGame(){
    	welcomeMessage();

		boolean userTurn = enterFirstPlayer();
		Board root = new Board(userTurn);

		printBoard(root);

		while (true){	//change to win condition
			int[] newMove = enterPlayerMove(root);
			Board newBoard = BoardController.movePlayer(root, newMove[0], newMove[1]);
			System.out.println("value" + newBoard.getValue(newMove[0], newMove[1]));
			
			System.out.println();
			printBoard(newBoard);

			root = newBoard;

    		//computer move
    	}
    	
    }

    public void welcomeMessage(){
        System.out.printf("Welcome to the Isolation Game. To win, be the last one to make a move\n");
    }

    public boolean enterFirstPlayer(){
        char firstPlayer;
        do{
            System.out.printf("Enter 'O' to play first or enter 'X' for computer to play first\n");
            firstPlayer = sc.nextLine().toUpperCase().charAt(0);
        }while((firstPlayer != 'X') &&  (firstPlayer != 'O'));
        
        if (firstPlayer == 'O')
        	return true;
        return false;
    }

    public int[] enterPlayerMove(Board root) {
		boolean askForMove = true;
		int[] newMove = new int[2];

		while (askForMove){
			try {
				System.out.println("Enter a coordinate to move to: xy (ex: A1)");
				String playerMove = sc.nextLine().replaceAll("\\s","");

				int x = StaticVals.ALPHABET.indexOf(Character.toUpperCase(playerMove.charAt(0)));
				int y = Integer.parseInt(playerMove.substring(1))-1;
				newMove[0] =x;
				newMove[1] =y;

				if (isValidPlayerMove(root, x, y)){
					askForMove = false;
				}


			} catch (Exception e) {
				System.out.println("error");
			}
		}

		return newMove;
	}

    
    // Player enters a coordinate system input to move their piece
    public boolean isValidPlayerMove(Board currentBoard, int x, int y){

		//if move goes off of the board
		if (x < 0 || x > 7 || y < 0 || y > 7){
			return false;
		}

		//if move is not a legitimate queen move
		double numerator = (double)currentBoard.getPlayerY()- (double)y;
		double denominator = (double)currentBoard.getPlayerX() - (double)x;
		double slope = (numerator)/(denominator);

		if (!(slope == 0.0) && !Double.isInfinite(slope) && !(slope == 1.0) && !(slope == -1.0)){
			return false;
		}

		//if has a blocker in between the old move and new move
		if (BoardController.isValidMove(currentBoard, x,y, currentBoard.getPlayerX(), currentBoard.getPlayerY())){
//			BoardController.movePlayer(currentBoard, x, y);
			return true;
		}

		return false;
    }
    // print board using the board object
    public void printBoard(Board board){
    	StringBuilder sb = new StringBuilder(StaticVals.BOARD_COLUMNS); 

    	for (int i = 0; i < StaticVals.BOARD_SIZE; ++i){
    		for (int j = -1; j < StaticVals.BOARD_SIZE; ++j){
    			if (j == -1){
    				sb.append(StaticVals.ALPHABET.charAt(i) + " ");
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
    			sb.append(StaticVals.ALPHABET.charAt(i));
    			sb.append(board.getComputerMoves().get(i)[1] + "\t ");
    		} else {
    			sb.append("\t");
    		}
    		if (board.getUserMoves().size() >= i  && board.getUserMoves().size() > 0){
    			sb.append(StaticVals.ALPHABET.charAt(i));
    			sb.append(board.getUserMoves().get(i)[1] + "\t ");
    		} else {
    			sb.append("\t");
    		}
    	}
    	System.out.println(sb.toString());
    }

}
