package cpp.cs.cs420.IsolationGame.Controller;

import cpp.cs.cs420.IsolationGame.model.Board;
import cpp.cs.cs420.IsolationGame.model.MiniMaxNode;
import cpp.cs.cs420.IsolationGame.model.StaticVals;

import java.util.*;

/**
 * Created by mayalake on 5/27/18.
 */
public class MiniMaxAlgorithm {

    public static void MiniMaxAlgorithm(Board root){
        int depth = 0;
        MiniMaxNode child;

        HashMap<Integer, ArrayList<MiniMaxNode>> tree = new HashMap<>();

        //create root
        MiniMaxNode rootNode = new MiniMaxNode(root, true);
        rootNode.setAlpha(StaticVals.MINIMAX_MIN_VALUE);
        rootNode.setBeta(StaticVals.MINIMAX_MAX_VALUE);

        tree.put(depth, new ArrayList<MiniMaxNode>(Arrays.asList(rootNode)));

        //generate 5 random children
        for (int i = 0; i < 5; i++){

        }


        //run minimax with alpha beta pruning

    }

    /*
    0- top
    1- topright
    2- right
    3- botright
    4- bot
    5- botleft
    6- left
    7- topleft
     */
    private MiniMaxNode createChild(MiniMaxNode node){
        int curX = node.getBoard().getComputerX();
        int curY = node.getBoard().getComputerY();

        int randomVal;
        int newX;
        int newY;
        Board newChildBoard;

        Random random = new Random();
        int randDirection;

        //check if move direction is valid
        do {
            randDirection =random.nextInt(8);
        }while(!isValidBoundary(node.getBoard(), randDirection, curX, curY));

        //create a child with random valid location
        int[] newMove = getValidMove(randDirection, curX, curY);
        while(!(BoardController.isValidMove(node.getBoard(), newMove[0], newMove[1], curX, curY))){
            newMove = getMove(randDirection, newMove[0], newMove[1]);
        }

        newChildBoard = new Board(node.getBoard());
        BoardController.movePlayer(newChildBoard, newMove[0], newMove[1]);

        return new MiniMaxNode(newChildBoard, !node.isMaxNode());
    }

    //if blocker is between new move and current move, set new move 1 tile closer to current move in the correct direction
    private int[] getMove(int direction, int x, int y){
        switch (direction){
            case 0:
                return new int[]{x, --y};
            case 1:
                return new int[]{++x, --y};
            case 2:
                return new int[]{++x, y};
            case 3:
                return new int[]{++x, ++y};
            case 4:
                return new int[]{x, ++y};
            case 5:
                return new int[]{--x, ++y};
            case 6:
                return new int[]{--x, y};
            case 7:
                return new int[]{--x, --y};
        }
        return new int[]{x, y};
    }

    //generate random val between x and randVal or y and randVal
    private int[] getValidMove(int randDirection, int x, int y){
        Random random = new Random();
        int smallerVal, randomIncrement;

        switch (randDirection){
            case 0:
                return new int[]{x, random.nextInt(y)};
            case 1:
                smallerVal = (y < 7-x)? y: 7-x;
                randomIncrement = random.nextInt(smallerVal);

                return new int[]{x+randomIncrement, y-randomIncrement};
            case 2:
                return new int[]{random.nextInt(7-x)+x, y};
            case 3:
                smallerVal = (7-y < 7-x)? 7-y: 7-x;
                randomIncrement = random.nextInt(smallerVal);

                return new int[]{x+randomIncrement, y+randomIncrement};
            case 4:
                return new int[]{x, random.nextInt(7-y)+y};
            case 5:
                smallerVal = (7-y < x)? 7-y: x;
                randomIncrement = random.nextInt(smallerVal);

                return new int[]{x-randomIncrement, y+randomIncrement};
            case 6:
                return new int[]{random.nextInt(x), y};
            case 7:
                smallerVal = (y < x)? y: x;
                randomIncrement = random.nextInt(smallerVal);

                return new int[]{x-randomIncrement, y-randomIncrement};
        }
        return null;
    }

    private boolean isValidBoundary(Board board, int direction, int x, int y){
        switch (direction){
            case 0:
                return (y != 0 && board.getBoard()[x][y-1] == '-');
            case 1:
                return (x != 7 && y != 0 && board.getBoard()[x+1][y-1] == '-');
            case 2:
                return (x != 7 && board.getBoard()[x+1][y] == '-');
            case 3:
                return (x != 7 && y != 7 && board.getBoard()[x+1][y+1] == '-');
            case 4:
                return (y != 7 && board.getBoard()[x][y+1] == '-') ;
            case 5:
                return (x != 0 && y != 7 && board.getBoard()[x-1][y+1] == '-');
            case 6:
                return (x != 0 && board.getBoard()[x-1][y] == '-');
            case 7:
                return (x != 0 && y != 0 && board.getBoard()[x-1][y-1] == '-');
        }

        return false;
    }

}
