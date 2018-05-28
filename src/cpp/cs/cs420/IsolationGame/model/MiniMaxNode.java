package cpp.cs.cs420.IsolationGame.model;

import java.util.ArrayList;

/**
 * Created by mayalake on 5/27/18.
 */
public class MiniMaxNode {
    private int alpha;
    private int beta;
    private MiniMaxNode parent;
    private ArrayList<MiniMaxNode> children;

    private boolean isMaxNode;

    private Board board;

    public MiniMaxNode(Board board, boolean isMaxNode){
        this.board = board;
        this.isMaxNode = isMaxNode;
    }

    public Board getBoard(){
        return board;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public boolean isMaxNode() {
        return isMaxNode;
    }

    public void setParent(MiniMaxNode parentIdx) {
        this.parent = parentIdx;
    }

}
