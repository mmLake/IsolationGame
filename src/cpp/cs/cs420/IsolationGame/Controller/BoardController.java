package cpp.cs.cs420.IsolationGame.Controller;

import cpp.cs.cs420.IsolationGame.model.Board;

/**
 * Created by mayalake on 5/17/18.
 */
public class BoardController {
	private Board board;
	//private boolean isUser;
	
	public BoardController(boolean isUser){
		//this.isUser = isUser;
		board = new Board(isUser);
	}
	
	//call board to change player position if possible, board returns true/false for possibility
	public boolean movePlayer(int x, int y, boolean isUser){
		if (board.movePlayer(x, y, isUser)){
			return true;
		}
		return false;
	}
	public Board getBoard(){
		return board;
	}
}
