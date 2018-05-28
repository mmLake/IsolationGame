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
        int smallerX = (previousX < newX) ? previousX : newX;
        int biggerX = (smallerX == previousX) ? newX : previousX;

        int smallerY = (previousY < newY) ? previousY : newY;
        int biggerY = (smallerY == previousY) ? newY : previousY;

        for (int i = smallerX; i <= biggerX; ++i){
            for (int j = smallerY; j <= biggerY; ++j){
                if (board.getBoard()[i][j] == '#'){
                    return false;
                }
            }
        }
        return true;
    }

    /*
    move validity checked during io- this method simply moves player and returns updated board
     */
    public static Board movePlayer(Board currentBoard, int row, int col){
        Board nextBoard = new Board(currentBoard);

        if (currentBoard.isUserTurn()){
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
            //get current computer position
            int computerX = currentBoard.getComputerX();
            int computerY = currentBoard.getComputerY();

            //update current Computer position
            nextBoard.setComputerX(row);
            nextBoard.setComputerY(col);

            nextBoard.getComputerMoves().add(new int[]{computerX, computerY});

            nextBoard.setBoardPosition(row, col, 'X');
            nextBoard.setBoardPosition(computerX, computerY, '#');
        }
        return nextBoard;
    }


}
