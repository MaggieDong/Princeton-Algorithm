public class Board {
	private int N;
    private int[][] boardArr;
    public Board(int[][] blocks) {
    	N = blocks.length;
    	boardArr = new int[N][N];
//    	StdOut.println(N);
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
//    			StdOut.println(j);
    			boardArr[i][j] = blocks[i][j];
    		}
    	}
    }          // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
    	return N;
    }                // board dimension N
    public int hamming() {
    	int count = 0;
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			if(boardArr[i][j] != N*i+j+1 && boardArr[i][j] != 0) {
    				count++;
    			}
    		}
    	}
    	return count;
    }                  // number of blocks out of place
    public int manhattan() {
    	int manhattanL = 0;
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			if(boardArr[i][j] != 0) {
        	    	if(boardArr[i][j]%N == 0) {
        	    		manhattanL = manhattanL + Math.abs(boardArr[i][j]/N-1-i) + Math.abs(N-1-j);
        	    	}
        	    	else {
        	    		manhattanL = manhattanL + Math.abs(boardArr[i][j]/N-i) + Math.abs(boardArr[i][j]%N -1-j);
        	    	}	
    			}
    		}
    	}
    	return manhattanL;
    }                // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
    	return this.hamming() == 0;
    }               // is this board the goal board?
    public Board twin() {
    	int[][] twinArr = new int[N][N];
    	twinArr = copyArr();
//    	for(int i = 0; i < N; i++) {
//    		for(int j = 0; j < N; j++) {
//    			twinArr[i][j] = boardArr[i][j];
//    		}
//    	}
    	if(twinArr[0][0] * twinArr[0][1] != 0) {
    		int temp = twinArr[0][0];
    		twinArr[0][0] = twinArr[0][1];
    		twinArr[0][1] = temp;
    	}
    	else {
    		int temp = twinArr[1][0];
    		twinArr[1][0] = twinArr[1][1];
    		twinArr[1][1] = temp;
    	}
    	return new Board(twinArr);
    }                   // a boadr that is obtained by exchanging two adjacent blocks in the same row
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
//        Date that = (Date) x;
        Board that = (Board) y;
        if(this.N != that.N)
        	return false;
        else {
        	for(int i = 0; i < this.N; i++) {
        		for(int j = 0; j < this.N; j++) {
        			if(that.boardArr[i][j] != this.boardArr[i][j])
        				return false;
        		}
        	}
        }
        return true;
    }       // does this board equal y?
    public Iterable<Board> neighbors() {
    	int blank_x = 0;
    	int blank_y = 0;
    	for(int i = 0; i < this.N; i++) {
    		for(int j = 0; j < this.N; j++) {
    			if(boardArr[i][j]==0) {
    				blank_x = i;
    				blank_y = j;
    			}
    		}
    	}
        Queue<Board> puzzleQ = new Queue<Board>();

    	if(blank_x > 0) {
    		int neiArr[][] = new int[N][N];
    		neiArr = copyArr();
    		neiArr[blank_x][blank_y] = neiArr[blank_x - 1][blank_y];
    		neiArr[blank_x-1][blank_y] = 0;
    		puzzleQ.enqueue(new Board(neiArr));
    	}
    	if(blank_x < N-1) {
    		int neiArr[][] = new int[N][N];
    		neiArr = copyArr();
    		neiArr[blank_x][blank_y] = neiArr[blank_x + 1][blank_y];
    		neiArr[blank_x+1][blank_y] = 0;
    		puzzleQ.enqueue(new Board(neiArr));
    	}
    	if(blank_y > 0) {
    		int neiArr[][] = new int[N][N];
    		neiArr = copyArr();
    		neiArr[blank_x][blank_y] = neiArr[blank_x][blank_y - 1];
    		neiArr[blank_x][blank_y - 1] = 0;
    		puzzleQ.enqueue(new Board(neiArr));
    	}
    	if(blank_y < N-1) {
    		int neiArr[][] = new int[N][N];
    		neiArr = copyArr();
    		neiArr[blank_x][blank_y] = neiArr[blank_x][blank_y + 1];
    		neiArr[blank_x][blank_y + 1] = 0;
    		puzzleQ.enqueue(new Board(neiArr));
    	}
    	return puzzleQ;
    }    // all neighboring boards
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", boardArr[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }              // string representation of this board (in the output format specified below)

    private int[][] copyArr() {
    	int copyArr[][] = new int[N][N];
    	for(int i = 0; i < N; i++) {
    		for(int j = 0; j < N; j++) {
    			copyArr[i][j] = boardArr[i][j];
    		}
    	}
    	return copyArr;
    }
    public static void main(String[] args) {
        // for each command-line argument

            // read in the board specified in the filename
        String filename = args[0];
        In in = new In(filename);
//        StdOut.println(filename);
        int N = in.readInt();
//        StdOut.println(N);
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            StdOut.println(initial.toString());
            StdOut.println(initial.hamming());
            StdOut.println(initial.manhattan());
    }// unit tests (not graded)
}