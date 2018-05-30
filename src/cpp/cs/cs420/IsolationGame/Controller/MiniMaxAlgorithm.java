package cpp.cs.cs420.IsolationGame.Controller;

import cpp.cs.cs420.IsolationGame.model.Board;
import cpp.cs.cs420.IsolationGame.model.MiniMaxNode;
import cpp.cs.cs420.IsolationGame.model.StaticVals;

import java.util.*;

/**
 * Created by mayalake on 5/27/18.
 */
public class MiniMaxAlgorithm {
	//store all nodes in hashmap with index, al<node>
	private HashMap<Integer, ArrayList<MiniMaxNode>> tree = new HashMap<>();
	private MiniMaxNode root;
	private int currentDepth;
	Random random = new Random();

	public MiniMaxAlgorithm(Board root){
		//create root
		currentDepth = 0;
		this.root = new MiniMaxNode(root, true);
		this.root.setAlpha(StaticVals.MINIMAX_MIN_VALUE);
		this.root.setBeta(StaticVals.MINIMAX_MAX_VALUE);
		tree.put(currentDepth, new ArrayList<MiniMaxNode>(Arrays.asList(this.root)));
	}

	////generate as many children nodes as possible? or just a certain number of depth, keep in mind exponential growth, log func?

	//generates children for all parents at a given depth, (leaves) in bfs fashion
	public void generateChildren(int depth){	//can add variable depth increments
		ArrayList<MiniMaxNode> parents = tree.get(depth);
		for (int j = 0; j < parents.size(); ++j){
			//generate 5 depth, temp testing
			int depthTracker = depth;
			while (depth - depthTracker <= 5){
				
				for (int i = 0; i < 5; i++){	//5 children for a parent
					if (tree.containsKey(depth)){
						tree.get(depth).add(createChild(parents.get(i)));
					} else{
						tree.put(depth, new ArrayList<MiniMaxNode>(Arrays.asList(createChild(parents.get(i)))));
					}
				}
				++depth;
			}
		}
		//run minimax with alpha beta pruning
	}

	//dfs with iterative deepening, use stack to store nodes to traverse?
	//add last to first to stack, expands first, adds last to first again until leaf
	public MiniMaxNode optimalNode(MiniMaxNode state, int depth){
		int depthCounter = 0;
		ArrayList<MiniMaxNode> childrenTemp = state.getChildren();
		Stack<MiniMaxNode> stack = new Stack<MiniMaxNode>();
		
		while (depthCounter < depth){	//traverse down to depth to get node
			//null check, map checking?
			for (int i = childrenTemp.size()-1; i >= 0; --i){
				stack.push(childrenTemp.get(i));	//adds all children to stack
			}
			
		}
		return null;
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
    returns a node with a valid board state of one move difference(non stationary) and opposite maxmin of parent
	 */
	public MiniMaxNode createChild(MiniMaxNode node){
		int curX = node.getBoard().getComputerX();
		int curY = node.getBoard().getComputerY();
		MiniMaxNode child = null;
		//Randomly searches for a child if there is at least one available move to make
		if (node.getBoard().getValue(curX, curY) > 0){

			int randomVal;
			int newX;
			int newY;
			Board newChildBoard;

			Random random = new Random();
			int randDirection;

			do {	//get a direction with at least one adjacent spot open
				randDirection = random.nextInt(8);
				System.out.println(randDirection);
			}while(!isValidBoundary(node.getBoard(), randDirection, curX, curY));
			System.out.println("direction: " + randDirection);
			
			//get a move in a direction, not guaranteed to be valid
			int[] newMove;
			do{
				newMove = getValidMove(randDirection, curX, curY);
				System.out.println("new move is: " + newMove[0] + ", " + newMove[1] + " : cur " + curX + ", " + curY + " : dir" + randDirection);
			} while (newMove[0] == curX && newMove[1] == curY);
			//check if the move is valid, 
			
			//else move the desired position one step closer to the position of the character, getting the furthest valid location
			
			System.out.println("new moves" + newMove[0] + ", " + newMove[1] + " direction:" + randDirection + "  : old move " + curX + ", " + curY);
			
			while(!(BoardController.isValidMove(node.getBoard(), newMove[0], newMove[1], curX, curY))){
				newMove = getMove(randDirection, newMove[0], newMove[1]);
				System.out.println("get another new move" + newMove[0] + ", " + newMove[1]);
			}

//			newChildBoard = new Board(node.getBoard(), false);
			newChildBoard = BoardController.movePiece(node.getBoard(), newMove[0], newMove[1], false);

			child = new MiniMaxNode(newChildBoard, !node.isMaxNode());
			child.setParent(node);
			node.addChild(child);

			return child;
		}
		return null;
	}

	//if blocker is between new move and current move, set new move 1 tile closer to current move in the correct direction
	//Given a valid direction, returns a move one step closer in that direction with no guarantee of being valid
	//(such as returning an unreachable coordinate that's past a obstacle).
	private int[] getMove(int direction, int x, int y){
		switch (direction){
		case 0:
			return new int[]{++x, y};
		case 1:
			return new int[]{++x, --y};
		case 2:
			return new int[]{x, --y};
		case 3:
			return new int[]{--x, --y};
		case 4:
			return new int[]{--x, y};
		case 5:
			return new int[]{--x, ++y};
		case 6:
			return new int[]{x, ++y};
		case 7:
			return new int[]{++x, ++y};
		}
		return new int[]{x, y};
	}

	//generate random val between x and randVal or y and randVal
	//Returns an array of size 2 of coordinates of the next random move in a given direction 
	//that has a guarantee of at least 1 valid move in that direction.
	private int[] getValidMove(int randDirection, int x, int y){
		
		int smallerVal, randomIncrement;

		switch (randDirection){
		case 0:
			return new int[]{random.nextInt(x), y};
		case 1:
			smallerVal = (x < 7-y)? x : 7-y;
			randomIncrement = random.nextInt(smallerVal+1);
			return new int[]{x-randomIncrement, y+randomIncrement};
		case 2:
			return new int[]{x, random.nextInt(7-y)+y};
		case 3:
			smallerVal = (7-y < 7-x)? 7-y: 7-x;
			randomIncrement = random.nextInt(smallerVal+1);
			return new int[]{x+randomIncrement, y+randomIncrement};
		case 4:
			return new int[]{random.nextInt(7-x)+x, y};
		case 5:
			//smallerVal = (7-y < x)? 7-y: x;
			smallerVal = (7-x < y) ? 7-x : y;
			randomIncrement = random.nextInt(smallerVal+1);

			return new int[]{x+randomIncrement, y-randomIncrement};
		case 6:
			return new int[]{x, random.nextInt(y)};
		case 7:
			smallerVal = (y < x)? y: x;
			randomIncrement = random.nextInt(smallerVal+1);

			return new int[]{x-randomIncrement, y-randomIncrement};
		}
		return null;
	}
	//Given a board, direction, and current position, checks to see if it is next to a wall
	//or there is an adjacent open space in that direction. (at least one valid space available)
	private boolean isValidBoundary(Board board, int direction, int x, int y){
		switch (direction){
		case 0:
			return (x != 0 && board.getBoard()[x-1][y] == '-');
		case 1:
			return (x != 0 && y != 7 && board.getBoard()[x-1][y+1] == '-');
		case 2:
			return (y != 7 && board.getBoard()[x][y+1] == '-');
		case 3:
			return (x != 7 && y != 7 && board.getBoard()[x+1][y+1] == '-');
		case 4:
			return (x != 7 && board.getBoard()[x+1][y] == '-') ;
		case 5:
			return (x != 7 && y != 0 && board.getBoard()[x+1][y-1] == '-');
		case 6:
			return (y != 0 && board.getBoard()[x][y-1] == '-');
		case 7:
			return (x != 0 && y != 0 && board.getBoard()[x-1][y-1] == '-');
		}

		return false;
	}

}
