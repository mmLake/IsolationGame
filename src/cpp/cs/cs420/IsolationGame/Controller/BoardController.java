package cpp.cs.cs420.IsolationGame.Controller;

import cpp.cs.cs420.IsolationGame.model.Board;

/**
 * Created by mayalake on 5/17/18.
 */
public class BoardController {
	Board board;
	
	public BoardController(){
		board = new Board(true);
	}
}
