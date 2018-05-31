package cpp.cs.cs420.IsolationGame.Controller;

import cpp.cs.cs420.IsolationGame.model.Board;

/**
 * Created by mayalake on 5/17/18.
 */
public class BoardController {
	/*
        Assume playerMoveY is already valid queen move
        checks if there is a blocker between old & new move
	 */
	public static boolean isValidMove(Board board, int newX, int newY, int previousX, int previousY){
		//rise
		double verticalDiff = (double)newX - (double)previousX;
		//run
		double horizDiff = (double)newY - (double)previousY;
		double slope = (verticalDiff)/(horizDiff);

		if (verticalDiff > 0 && horizDiff == 0){//top to bottom
			for (int i = previousX+1; i <= newX; ++i){
				if (board.getBoard()[i][previousY] == '#' || board.getBoard()[i][previousY] == 'O' || board.getBoard()[i][previousY] == 'X')
					return false;
			}
		} else if(verticalDiff < 0 && horizDiff == 0){	//bot to top
			for (int i = newX; i < previousX; ++i){
				if (board.getBoard()[i][previousY] == '#' || board.getBoard()[i][previousY] == 'O' || board.getBoard()[i][previousY] == 'X')
					return false;
			}
		} else if(horizDiff > 0 && verticalDiff == 0){ // left to right
			for (int i = previousY+1; i <= newY; ++i){
				if (board.getBoard()[previousX][i] == '#' || board.getBoard()[previousX][i] == 'O' || board.getBoard()[previousX][i] == 'X')
					return false;
			}
		} else if (horizDiff < 0  && verticalDiff == 0){ //right to left
			for (int i = newY; i < previousY; ++i){
				if (board.getBoard()[previousX][i] == '#' || board.getBoard()[previousX][i] == 'O' || board.getBoard()[previousX][i] == 'X')
					return false;
			}
		} else if (slope == -1 && previousY < newY){ //botleft to topright
			for (int i = previousY+1, counter = 1; i <= newY; ++i, ++counter){
				if (board.getBoard()[previousX-counter][i] == '#' || board.getBoard()[previousX-counter][i] == 'O' || board.getBoard()[previousX-counter][i] == 'X')
					return false;
			}
		} else if (slope == -1 && previousY > newY){	//topright to botleft
			for (int i = newY, counter = 0; i < previousY; ++i, ++counter){
				if (board.getBoard()[newX-counter][i] == '#' || board.getBoard()[newX-counter][i] == 'O' || board.getBoard()[newX-counter][i] == 'X'){
					return false;
				}
			}
		} else if (slope == 1 && previousY < newY){ //topleft to botright
			for (int i = previousY+1, counter = 1; i <= newY; ++i, ++counter){
				if (board.getBoard()[previousX+counter][i] == '#' || board.getBoard()[previousX+counter][i] == 'O' || board.getBoard()[previousX+counter][i] == 'X')
					return false;
			}
		} else if (slope == 1.0 && newY < previousY){ //botright to topleft
			for (int i = newY, counter = 0; i < previousY; ++i, ++counter){
				if (board.getBoard()[newX+counter][i] == '#' || board.getBoard()[newX+counter][i] == 'O' || board.getBoard()[newX+counter][i] == 'X'){
					return false;
				}
			}
		} else {
		//	System.out.println("Unspecified slope/coordinate combination");
		}
		return true;
	}

	/*
    move validity checked during io- this method simply moves player and returns updated board
	 */
	public static Board movePiece(Board currentBoard, int curX, int curY, boolean userTurn){
		Board nextBoard = new Board(currentBoard, userTurn);

		if (userTurn){
//			System.out.println("player turn");
			//get current player position
			int playerX = currentBoard.getPlayerX();
			int playerY = currentBoard.getPlayerY();

			//update current Player position
			nextBoard.setPlayerX(curX);
			nextBoard.setPlayerY(curY);

			nextBoard.getUserMoves().add(new int[]{playerX, playerY});

			nextBoard.setBoardPosition(curX, curY, 'O');
			nextBoard.setBoardPosition(playerX, playerY, '#');
		}
		else {
//			System.out.println("comp turn");
			//get current computer position
			int computerX = currentBoard.getComputerX();
			int computerY = currentBoard.getComputerY();
//			System.out.println("comp position" + computerX + ", " + computerY);
			
			//update current Computer position
			nextBoard.setComputerX(curX);
			nextBoard.setComputerY(curY);

			nextBoard.getComputerMoves().add(new int[]{curX, computerY});

			nextBoard.setBoardPosition(curX, curY, 'X');
			nextBoard.setBoardPosition(computerX, computerY, '#');
		}
		return nextBoard;
	}
	// same as move piece, but does not 
}