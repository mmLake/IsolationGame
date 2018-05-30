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
	/*public static boolean isValidMove(Board board, int newX, int newY, int previousX, int previousY){
		if (newX == previousX && newY == previousY){//3.0 -> 3,5
			return false;
		}

		int smallerX, biggerX, smallerY, biggerY;

		if (previousX < newX){
			smallerX = previousX+1;
			biggerX = newX;
		} else if(previousX == newX){
			smallerX = previousX;
			biggerX = newX;
		}else {
			smallerX = newX;
			biggerX = previousX-1;
		}

		if (previousY < newY){
			smallerY = previousY+1;
			biggerY = newY;
		} else if(previousY == newY){
			smallerY = previousY;
			biggerY = newY;
		}else {
			smallerY = newY;
			biggerY = previousY-1;
		}

		System.out.println("isvalidmove " + smallerX + "," + smallerY + " and " + biggerX + "," + biggerY);
		for (int i = smallerX; i <= biggerX; ++i){
			for (int j = smallerY; j <= biggerY; ++j){
				System.out.println(i + ", " + j + " : " + board.getBoard()[i][j]);
				//if (board.getBoard()[i][j] == '-' && i != previousX && j != previousY){
				if (board.getBoard()[i][j] == '#' || board.getBoard()[i][j] == 'O' || board.getBoard()[i][j] == 'X' ){
					return false;
				}
			}
		}
		return true;
	}*/
	/*
	public static boolean isValidMove(Board board, int x, int y, int oldx, int oldy){
		if (x < 0 || x > 7 || y < 0 || y > 7){
			return false;
		}
		if (oldx == x && oldy == y){
			System.out.println("same spot");
			return false;
		}
			//rise
		double numerator = (double)x - (double)oldx;
			//run
		double denominator = (double)y - (double)oldy;

		double slope = -1*(numerator)/(denominator);
		System.out.println("slope" + slope);

		int biggery = ((denominator >= 0) ? y : oldy);
		int smallery = ((denominator >= 0) ? oldy : y);
		
		int biggerx = ((numerator >= 0) ? x : oldx);
		int smallerx = ((numerator >= 0) ? oldx : x);

		if (Double.isInfinite(slope)){	//vertical
			for (int i = smallerx; i <= biggerx; ++i){
				if (board.getBoard()[i][y] == '#' || board.getBoard()[i][y] == 'O' || board.getBoard()[i][y] == 'X')
					return false;
			}
			return true;
		}else if (slope == 0){ //horixontal
			for (int i = smallery; i <= biggery; ++i){
				if (board.getBoard()[x][i] == '#' || board.getBoard()[x][i] == 'X' || board.getBoard()[x][i] == 'O')
					return false;
			}
			return true;
		}
		else if (slope == 1){ // positive diagonal
			for (int i = smallerx; i <= biggerx; ++i){
				if (board.getBoard()[smallerx-i][biggery+i] == '#' || board.getBoard()[smallerx-i][biggery+i] == 'X' 
						|| board.getBoard()[smallerx-i][biggery+i] == 'O'){
					return false;
				}
			}
			return true;
		} else if(slope == -1){	// negative diagonal
			for (int i = smallery; i <= biggery; ++i){
				if (board.getBoard()[smallerx+i][smallery+i] == '#' || board.getBoard()[smallerx+i][smallery+i] == 'X' ){
						//|| board.getBoard()[smallerx+i][smallery+i] == 'O'){
					return false;
				}
			}
			return true;
		}else {
			return false;
		}
	}
*/
	public static boolean isValidMove(Board board, int newX, int newY, int previousX, int previousY){
		//rise
	double verticalDiff = (double)newX - (double)previousX;
		//run
	double horizDiff = (double)newY - (double)previousY;
	double slope = (verticalDiff)/(horizDiff);
//	System.out.println("vert:" + verticalDiff + ", horiz:" + horizDiff + ", slope:" + slope);
	if (verticalDiff > 0 && horizDiff == 0){//top to bottom
		for (int i = previousX+1; i <= newX; ++i){
			if (board.getBoard()[i][previousY] == '#' || board.getBoard()[i][previousY] == 'O' || board.getBoard()[i][previousY] == 'X')
				return false;
		}
	} else if(verticalDiff < 0 && horizDiff == 0){	//bot to top
		for (int i = newX+1; i < previousX; ++i){
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
		for (int i = newY, counter = 1; i < previousY; ++i, ++counter){
			if (board.getBoard()[previousX+counter][i] == '#' || board.getBoard()[previousX+counter][i] == 'O' || board.getBoard()[previousX+counter][i] == 'X'){
				//System.out.println("checking");
				return false;
			}
		}
	} else if (slope == 1 && previousY < newY){ //topleft to botright
		System.out.println("asd");
		for (int i = previousY+1, counter = 1; i <= newY; ++i, ++counter){
			if (board.getBoard()[previousX+counter][i] == '#' || board.getBoard()[previousX+counter][i] == 'O' || board.getBoard()[previousX+counter][i] == 'X')
				return false;
		}
	} else if (slope == 1.0 && newY < previousY){ //botright to topleft
//		System.out.println("prevcheck" + board.getBoard()[previousX-1][0]);
		for (int i = newY, counter = 1; i < previousY; ++i, ++counter){
			if (board.getBoard()[previousX-counter][i] == '#' || board.getBoard()[previousX-counter][i] == 'O' || board.getBoard()[previousX-counter][i] == 'X'){
				System.out.println(board.getBoard()[previousX-counter][i] + "checking: " + (previousX-counter) + ", " + i );
				return false;
			}
		}
	} else {
		System.out.println("Unspecified slope/coordinate combination");
	}
		return true;
	}
	
	/*
    move validity checked during io- this method simply moves player and returns updated board
	 */
	public static Board movePiece(Board currentBoard, int row, int col, boolean userTurn){
		Board nextBoard = new Board(currentBoard, userTurn);

		if (userTurn){
			System.out.println("player turn");
			//get current player position
			int playerX = currentBoard.getPlayerX();
			int playerY = currentBoard.getPlayerY();

			//update current Player position
			nextBoard.setPlayerX(row);
			nextBoard.setPlayerY(col);

			nextBoard.getUserMoves().add(new int[]{playerX, playerY});

			nextBoard.setBoardPosition(row, col, 'O');
			nextBoard.setBoardPosition(playerX, playerY, '#');
		}
		else {
			System.out.println("comp turn");
			//get current computer position
			int computerX = currentBoard.getComputerX();
			int computerY = currentBoard.getComputerY();
			System.out.println("comp position" + computerX + ", " + computerY);
			//update current Computer position
			nextBoard.setComputerX(row);
			nextBoard.setComputerY(col);

			nextBoard.getComputerMoves().add(new int[]{row, computerY});

			nextBoard.setBoardPosition(row, col, 'X');
			nextBoard.setBoardPosition(computerX, computerY, '#');
		}
		return nextBoard;
	}
}