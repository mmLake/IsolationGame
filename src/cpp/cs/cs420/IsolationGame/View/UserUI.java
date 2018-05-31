package cpp.cs.cs420.IsolationGame.View;

import java.util.Arrays;
import java.util.Scanner;

import cpp.cs.cs420.IsolationGame.Controller.BoardController;
import cpp.cs.cs420.IsolationGame.Controller.MiniMaxAlgorithm;
import cpp.cs.cs420.IsolationGame.model.Board;
import cpp.cs.cs420.IsolationGame.model.MiniMaxNode;
import cpp.cs.cs420.IsolationGame.model.StaticVals;


public class UserUI {
    Scanner sc = new Scanner(System.in);
    
    public void startGame(){
    	welcomeMessage();

		boolean userTurn = enterFirstPlayer();
		Board currentBoard = new Board(userTurn);
		
		printBoard(currentBoard);

		while (currentBoard.getValue(currentBoard.getPlayerX(), currentBoard.getPlayerY()) != 0 && 
				currentBoard.getValue(currentBoard.getComputerX(), currentBoard.getComputerY()) != 0){	//change to win condition, heuristic of board 0 for either player?
			//player move
			int[] newMove = enterPlayerMove(currentBoard);
			Board newBoard = BoardController.movePiece(currentBoard, newMove[0], newMove[1], userTurn);
			
			System.out.println();
			printBoard(newBoard);
			//printMoves(newBoard);

			currentBoard = new Board(newBoard, !userTurn);

    		//computer move
			
			currentBoard = computerTurn(currentBoard);
			printBoard(currentBoard);
			//printMoves(newBoard);

	//		currentBoard = new Board(test.getBoard(), userTurn);
			if (currentBoard.getValue(currentBoard.getPlayerX(), currentBoard.getPlayerY()) == 0){
				System.out.println("You lose!");
				System.exit(0);
			} else if (currentBoard.getValue(currentBoard.getComputerX(), currentBoard.getComputerY()) == 0){
				System.out.println("You win!");
				System.exit(0);
			}
    	}
    }
    //computer has root minimaxnode, passes in current state (board), and how many levels (depth) to generate
    public Board computerTurn(Board board){//, int depth){
    	MiniMaxAlgorithm mini = new MiniMaxAlgorithm(board);
    	int[] moves = mini.minimax(5);
    	return BoardController.movePiece(board, moves[0], moves[1], false);
    		//Instantiate root of child states
	//	MiniMaxNode root = new MiniMaxNode(board, true);	
    	
			//should return a bunch of child states in tree form
		

			//returns a single child state of the root
//		MiniMaxNode test = mini.createChild(root);	//replace with bunch of states
		
//		printBoard(test.getBoard());
		
			//get optimal move for user
		//int[] playerMove = mini.getOptimalMove(board, true);
		//BoardController.movePiece(board, playerMove[0], playerMove[1], true);
	//	printBoard(board);
		
			//then call getOptimalMove for user on the child boards
		
//		return test.getBoard();
		
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
				System.out.println("move is :" + newMove[0] + ", " +newMove[1]);
				
				if (isValidPlayerMove(root, x, y)){
					askForMove = false;
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return newMove;
	}

    
    // Player enters an input in letter,number format to move their piece
    public static boolean isValidPlayerMove(Board currentBoard, int x, int y){
//    	System.out.println("current pos" + currentBoard.getPlayerX() + ", " + currentBoard.getPlayerY());
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
//			System.out.println("player pos : " + currentBoard.getPlayerX() + "," + currentBoard.getPlayerY());
//			BoardController.movePlayer(currentBoard, x, y);
			return true;
		}

		return false;
    }
    // print board using the board object
    public static void printBoard(Board board){
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
    			for (int j = 0; j < board.getComputerMoves().size(); ++j){
    				System.out.println("testComp" + Arrays.toString(board.getComputerMoves().get(j)));
    			}
    			sb.append(StaticVals.ALPHABET.charAt(board.getComputerMoves().get(i)[0]));
    			sb.append(board.getComputerMoves().get(i)[1]+1 + "\t ");
    		} else {
    			sb.append("\t");
    		}
    		if (board.getUserMoves().size() >= i  && board.getUserMoves().size() > 0){
    			for (int j = 0; j < board.getUserMoves().size(); ++j){
    				System.out.println("testUser" + Arrays.toString(board.getUserMoves().get(j)));
    			}
    			sb.append(StaticVals.ALPHABET.charAt(board.getUserMoves().get(i)[0]));
    			sb.append(board.getUserMoves().get(i)[1]+1 + "\t ");
    		} else {
    			sb.append("\t");
    		}
    	}
    	System.out.println(sb.toString() + "\n");
    }

}
