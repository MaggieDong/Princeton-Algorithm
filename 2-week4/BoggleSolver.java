public class BoggleSolver
{
	private TrieSET dic;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
    	this.dic = new TrieSET();
    	for (String word : dictionary) {
    		this.dic.add(word);
    	}
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
    	int row = board.rows();
    	int col = board.cols();
    	SET<String> result = new SET<String>();
    	for (int i = 0; i < row; i++) {
    		for (int j = 0; j < col; j++) {
    			String letters = "";
//    			letters += board.getLetter(i, j);
    			boolean marked[][] = new boolean[row][col];
    			dfs(marked, i, j, letters, result, board);
 //   	    	StdOut.println("mark3!");
    		}
    	}
//    	StdOut.println(result.size());
    	return result;
    }

    private void dfs(boolean[][] marked, int i, int j, String letters, SET<String> result, BoggleBoard board) {
    	String letter1;
    	char oneLetter = board.getLetter(i, j);
    	
    	if (oneLetter == 'Q')
    		letter1 = letters + "QU";
    	else 
    		letter1 = letters + oneLetter;
    	
    	if (marked[i][j])
    		return;
    	if (dic.contains(letter1) && letter1.length() > 2) {
//    		StdOut.println("addddd " + letter1);
    		result.add(letter1);
    	}
    	if (!dic.hasPrefix(letter1))
    		return;
    	
    	marked[i][j] = true;
    	
    	int iStart = -1;
    	int iEnd = 1;
    	int jStart = -1;
    	int jEnd = 1;
    	if (i == 0)
    		iStart = 0;
    	else if (i == board.rows() - 1)
    		iEnd = 0;
    	if (j == 0)
    		jStart = 0;
    	else if (j == board.cols() - 1)
    		jEnd = 0;
    	
    	for (int in = iStart;  in <= iEnd; in++) {
    		for (int jn = jStart; jn <= jEnd; jn++) {
    			if (in == 0 && jn == 0)
    				continue;
    			if (i + in >=0 && i + in < board.rows() && j + jn >= 0 && j + jn < board.cols()) {   				
    				if (!marked[i+in][j+jn]) {
    					dfs(marked, i+in, j+jn, letter1, result, board);
    				}
    			}
    		}
    	}
    	marked[i][j] = false;
    }
    
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
    	if (dic.contains(word)) {
    		int len = word.length();
    		if (len <= 2)
    			return 0;
    		else if (len <= 4)
    			return 1;
    		else if (len == 5)
    			return 2;
    		else if (len == 6)
    			return 3;
    		else if (len == 7)
    			return 5;
    		else 
    			return 11;
    	}
    	else 
    		return 0;
    }
    

	public static void main(String[] args)
	{
	    In in = new In(args[0]);
	    String[] dictionary = in.readAllStrings();
	    BoggleSolver solver = new BoggleSolver(dictionary);
	    BoggleBoard board = new BoggleBoard(args[1]);
	    int score = 0;
	    for (String word : solver.getAllValidWords(board))
	    {
	        StdOut.println(word);
	        score += solver.scoreOf(word);
	    }
	    StdOut.println("Score = " + score);
	}
}
