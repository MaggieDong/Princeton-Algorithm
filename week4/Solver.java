public class Solver {
	private boolean isSolved = true;
	private int moveNum = -1;
	private SearchNode boardNode, boardNodeTwin;
	private Stack<Board> solutionBoard = new Stack<Board>();
//	private SearchNode parent;
	private class SearchNode implements Comparable<SearchNode>{
		private Board searchBoard;
		private int moveN;
		private int priority;
		private SearchNode parent;
		
		public SearchNode(Board searchBoard, int moveN, SearchNode parent) {
			this.searchBoard = searchBoard;
			this.moveN = moveN;
			this.priority = searchBoard.manhattan() + moveN;
			this.parent = parent;
		}
		public boolean isGoal() {
			return searchBoard.isGoal();
		}
		public Board reBoard() {
			return searchBoard;
		}
		public int compareTo(SearchNode that) {
			return this.priority - that.priority;
//			if(this.priority == that.priority) {
//				return 0;
//			}
//			else if(this.priority > that.priority) {
//				return 1;
//			}
//			else return -1;
		}
	}

	
    public Solver(Board initial) {
    	MinPQ<SearchNode> searchNPQ = new MinPQ<SearchNode>();
//    	Stack<Board> solutionBoard = new Stack<Board>();
    	
    	boardNode = new SearchNode(initial, 0, null);
    	boardNodeTwin = new SearchNode(initial.twin(), 0, null);
    	searchNPQ.insert(boardNode);
    	searchNPQ.insert(boardNodeTwin);
    	boardNode = searchNPQ.delMin();
    	while(!boardNode.isGoal()) {
    		for(Board neighbor: boardNode.reBoard().neighbors()) {
    			Board preBoard = null;
    			if(boardNode.parent != null)
    				preBoard = boardNode.parent.reBoard();
    			if(!neighbor.equals(preBoard)) {
    				SearchNode newNode = new SearchNode(neighbor, boardNode.moveN + 1, boardNode);
    				searchNPQ.insert(newNode);
    			}
    		}
    		boardNode = searchNPQ.delMin();
    	}
    	SearchNode firstNode = boardNode;
		solutionBoard.push(firstNode.reBoard());
    	while(firstNode.parent != null) {
    		firstNode = firstNode.parent;
    		solutionBoard.push(firstNode.reBoard());
    	}
    	if(firstNode.reBoard().equals(boardNodeTwin.reBoard())) {
    		isSolved = false;
    		moveNum = -1;
    	}
    	else 
    		moveNum = boardNode.moveN;
    	
    }          // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable() {
    	return isSolved;
    }           // is the initial board solvable?
    public int moves() {
    	return moveNum;
    }                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
    	if(this.isSolved)
    		return this.solutionBoard;
    	else 
    		return null;
    }     // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {
    	
    }// solve a slider puzzle (given below)
}