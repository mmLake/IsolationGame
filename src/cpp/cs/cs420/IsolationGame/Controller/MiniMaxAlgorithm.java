package cpp.cs.cs420.IsolationGame.Controller;

import cpp.cs.cs420.IsolationGame.View.UserUI;
import cpp.cs.cs420.IsolationGame.model.Board;
import cpp.cs.cs420.IsolationGame.model.MiniMaxNode;
import cpp.cs.cs420.IsolationGame.model.StaticVals;

import java.lang.reflect.Array;
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
	//	static int MAX = 1000;
	//	static int MIN = -1000;

	public MiniMaxAlgorithm(Board root){
		//create root
		currentDepth = 0;
		this.root = new MiniMaxNode(root, true);
		this.root.setAlpha(StaticVals.MINIMAX_MIN_VALUE);
		this.root.setBeta(StaticVals.MINIMAX_MAX_VALUE);
		tree.put(currentDepth, new ArrayList<MiniMaxNode>(Arrays.asList(this.root)));

		//minimax(5);	

	}	
	// depth represents how many levels, ends on computer's turn
	public int[] minimax(int depth){
		ArrayList<MiniMaxNode> children;
		//start at root, depth 0 represents 1, 1- the children of it, 2 grandchild, etc
		for (int j = 0; j < depth; ++j){
//			System.out.println("j is" + j);
			if (!tree.containsKey(j)){
				//				System.out.println("doesnt have anything");
				tree.put(j, new ArrayList<MiniMaxNode>());	//add empty arraylist
			}
			//for all nodes at a depth
			for (int k = 0; k < tree.get(j).size(); ++k){
				MiniMaxNode parent = tree.get(j).get(k);	//get k children at a depth, root at j=0,k=0, children at j=1,k=0,size
				//				System.out.println("treesize:" +  tree.get(j).size());
				//generate a bunch of children of sep objects of 1 depth, next computer board state/move
				children = createChildren(parent, j+1);

				for (int i = 0; i < children.size(); ++i){	//for each child, get the next state 
					//hypothetical player turn
					int[] playerMove = getOptimalMove(children.get(i).getBoard(), true);
					// moves the player in the child boards

					//					System.out.println("child before " + i + " at ");
					//					UserUI.printBoard(children.get(i).getBoard());

					children.get(i).setBoard(BoardController.movePiece(children.get(i).getBoard(), playerMove[0], 
							playerMove[1], true));

					//					System.out.println("child " + i + " at: " + children.get(i).getBoard().getPlayerX() + ","
					//						 +	children.get(i).getBoard().getPlayerY());
					//					UserUI.printBoard(children.get(i).getBoard());
				}
			}
		}
		//		System.out.println("test alpha" + tree.get(depth).get(0).getAlpha() + " and " +tree.get(depth).get(0).getBeta() + "max?" + tree.get(depth).get(0).isMaxNode());
		//		System.out.println("parent test" + tree.get(depth-1).get(0).getAlpha() + " and " + tree.get(depth-1).get(0).getBeta() + "max?" + tree.get(depth-1).get(0).isMaxNode()); 
		Stack<MiniMaxNode> stack = new Stack<>();
		//bfs traversal
		for (int i = 0; i < depth; ++i){	//for each level
			for (int j = tree.get(i).size()-1; j >= 0; --j){	//how many nodes at a level
				stack.push(tree.get(i).get(j));
			}
		}
		for (int j = 0; j < stack.size(); ++j){
			MiniMaxNode temp = stack.pop();
			int currentDepth = 0;
				//get last depth
			while (tree.containsKey(currentDepth)){
				currentDepth++;
			}
			
			int currentValue = temp.getBoard().getValue(temp.getBoard().getComputerX(), temp.getBoard().getComputerY());
			if (temp.getParent() != null){	//root
				if(temp.getParent().isMaxNode()){
					//if beta <= alpha, stop search, beta is default 1k, alpha default -1k
					if (temp.getParent().getBeta() <= temp.getParent().getAlpha()){
							//if top of stack is a leaf, pop/remove
						if (tree.get(currentDepth).contains(stack.get(stack.size()-1))){
							stack.pop();
						}
					} else if (temp.getParent().getBeta() < currentValue){ //search continues
						temp.getParent().setBeta(currentValue);
					}
				} else {	//min node
					if (temp.getParent().getBeta() > currentValue){// && temp.getParent().getBeta() == StaticVals.MINIMAX_MAX_VALUE){
						temp.getParent().setAlpha(currentValue);
					} else if (tree.get(currentDepth).contains(stack.get(stack.size()-1))){
						stack.pop();
					}
				}
			}
		}
		int[] bestMove = new int[]{tree.get(1).get(0).getBoard().getComputerX(), tree.get(1).get(0).getBoard().getComputerY()};

		return bestMove;
	}

	////generate as many children nodes as possible? or just a certain number of depth, keep in mind exponential growth, log func?
	// generates children for a parent, sets the connections between parents and child for each and adds it to the hashmap, 
	// parent is the parent node to add children to and depth represents the current depth to use as a key to add to hashmap
	public ArrayList<MiniMaxNode> createChildren(MiniMaxNode parent, int depth){		//maybe dont need to return?
		ArrayList<MiniMaxNode> children = new ArrayList<MiniMaxNode>();	//holds children of the root

		MiniMaxNode child;
		for (int i = 0; i < 2; ++i){	///5 children generated, temp
			child = createChild(parent);	//new child board
			parent.addChild(child);		//adds child points to parent node, potential duplicate (can remove with .contains)
			children.add(child);	//not necessary?
		}

		//get optimal player moveset, put elsewhere
		int[] playerMove;
		for (int j = 0; j < children.size(); ++j){	
			playerMove = getOptimalMove(children.get(j).getBoard(), true);
		}
//		System.out.println("depth for tree add child:" + depth);
		tree.put(depth, children);	//add to hashmap
		return children;
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
    0- top, then continues in clockwise
    returns a node with a valid board state of one move difference(non stationary) and opposite maxmin of parent
	 */
	public MiniMaxNode createChild(MiniMaxNode node){
		int curX = node.getBoard().getComputerX();
		int curY = node.getBoard().getComputerY();
		MiniMaxNode child = null;
		//Randomly searches for a child if there is at least one available move to make
		if (node.getBoard().getValue(curX, curY) > 0){

			Board newChildBoard = new Board();
			Random random = new Random();
			int randDirection;

			do {	//get a direction with at least one adjacent spot open
				randDirection = random.nextInt(8);
				//				System.out.println(randDirection);
			}while(!isValidBoundary(node.getBoard(), randDirection, curX, curY));
			//			System.out.println("direction: " + randDirection);

			//get a move in a direction, not guaranteed to be valid
			int[] newMove;
			do{
				newMove = getValidMove(randDirection, curX, curY);
				//				System.out.println("new move is: " + newMove[0] + ", " + newMove[1] + " : cur " + curX + ", " + curY + " : dir" + randDirection);
			} while (newMove[0] == curX && newMove[1] == curY);
			//check if the move is valid, 

			//else move the desired position one step closer to the position of the character, getting the furthest valid location

			//			System.out.println("new moves" + newMove[0] + ", " + newMove[1] + " direction:" + randDirection + "  : old move " + curX + ", " + curY);

			while(!(BoardController.isValidMove(node.getBoard(), newMove[0], newMove[1], curX, curY))){
				newMove = getMove(randDirection, newMove[0], newMove[1]);
				//				System.out.println("get another new move" + newMove[0] + ", " + newMove[1]);
			}

			//			newChildBoard = new Board(node.getBoard(), false);
			//newChildBoard = node.getBoard().boardCopy();	//sep object
			newChildBoard.setBoard(node.getBoard().getBoard());
			newChildBoard.setComputerX(node.getBoard().getComputerX());
			newChildBoard.setComputerY(node.getBoard().getComputerY());

			newChildBoard.setPlayerX(node.getBoard().getPlayerX());
			newChildBoard.setPlayerY(node.getBoard().getPlayerY());


			newChildBoard = BoardController.movePiece(newChildBoard, newMove[0], newMove[1], false);

			child = new MiniMaxNode(newChildBoard, node, !node.isMaxNode());
			child.setAlpha(child.getParent().getAlpha());
			child.setBeta(child.getParent().getBeta());
			node.addChild(child);

			return child;
		}
		System.out.println("hm");
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
			return new int[]{x, random.nextInt(8-y)+y};
		case 3:
			smallerVal = (7-y < 7-x)? 7-y: 7-x;
			randomIncrement = random.nextInt(smallerVal+1);
			return new int[]{x+randomIncrement, y+randomIncrement};
		case 4:
			return new int[]{random.nextInt(8-x)+x, y};
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

	//returns a moveset of the optimal move that can be performed by the user
	public int[] getOptimalMove(Board board, boolean isUser){
		int optimalHeuristic = 0;//board.getValue(board.getPlayerX(), board.getPlayerY());
		int[] optimalMove = new int[2];


		//returns optimal next move, same spot will not be valid through BoardController.isValidMove().
		for (int i = 0; i < StaticVals.BOARD_SIZE; ++i){		//can optimize to not check every tile
			for (int j = 0; j < StaticVals.BOARD_SIZE; ++j){
				if (UserUI.isValidPlayerMove(board, i, j) 
						&& (board.getValue(i, j) >= optimalHeuristic)){

					optimalMove[0] = i;
					optimalMove[1] = j;
					optimalHeuristic = board.getValue(i, j);
					//					System.out.println("goodcoord: " + i + "," + j + " w/ " + optimalHeuristic);
				}
			}
		}
		//potential empty set of [0, 0] if no moves available
		return optimalMove;
	}


	// Initial values of 
	// Aplha and Beta


	// Returns optimal value for
	// current player (Initially called
	// for root and maximizer)
	/*	static MiniMaxNode minimax(int depth, int nodeIndex, boolean maximizingPlayer, MiniMaxNode tree[], int alpha, int beta){
	    // Terminating condition. i.e 
	    // leaf node is reached
	    if (depth == 5)
	        return tree[nodeIndex];
	    if (maximizingPlayer)
	    {
	        int best = MIN;

	        // Recur for left and right children
	        for (int i = 0; i < 5; i++)
	        {
	            int val = minimax(depth + 1, Math.pow(5, nodeIndex) + i, false, tree, alpha, beta);
	            best = Math.max(best, val);
	            alpha = Math.max(alpha, best);

	            // Alpha Beta Pruning
	            if (beta <= alpha)
	                break;
	        }
	        return tree[nodeIndex];
	    }
	    else
	    {
	        int best = MAX;
	        // Recur for left and right children
	        for (int i = 0; i < 5; i++)
	        {
	            int val = minimax(depth + 1, Math.pow(5, nodeIndex) + i, true, tree, alpha, beta);
	            best = Math.min(best, val);
	            beta = Math.min(beta, best);

	            // Alpha Beta Pruning
	            if (beta <= alpha)
	                break;
	        }
	        return best;
	    }
	}*/

}
