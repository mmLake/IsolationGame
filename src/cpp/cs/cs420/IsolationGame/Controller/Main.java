package cpp.cs.cs420.IsolationGame.Controller;

import cpp.cs.cs420.IsolationGame.View.UserUI;
import cpp.cs.cs420.IsolationGame.model.Board;

public class Main {
    private boolean playersTurn;

    public static void main(String[] args) {
    	UserUI ui = new UserUI();		//need to start testing in UI class, as this board isn't modified, but UI's is
    	ui.startGame();
    }
}
