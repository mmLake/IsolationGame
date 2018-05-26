package cpp.cs.cs420.IsolationGame.Controller;

import cpp.cs.cs420.IsolationGame.View.UserUI;
import cpp.cs.cs420.IsolationGame.model.Board;

public class Main {
    private boolean playersTurn;

    public static void main(String[] args) {
    	UserUI ui = new UserUI();
    	Board board = new Board(true);
    	ui.printBoard(board);
    	ui.printMoves(board);
    	board.movePlayer(1, 1, true);
    	ui.printBoard(board);
    	ui.printMoves(board);
    	
 //   	System.out.println(board.getUserMoves().get(0)[0] + ", " + board.getUserMoves().get(0)[1]);
//    	System.out.println(board.checkMoveValidity(0, 0, 7, 0));
    }
}
