import java.util.Arrays;
import java.util.HashMap;

public class BaseballElimination {
	private int teamNum;
	private String[] teamName;
	private int loss[];
	private int win[];
	private int remain[];
	private int game[][];
	private HashMap<Integer, Bag<Integer>> certificate;
	
	 // create a baseball division from given filename in format specified below
	public BaseballElimination(String filename) {
		In in = new In(filename);
		this.teamNum = in.readInt();
		teamName = new String[this.teamNum];
		loss = new int[this.teamNum];
		win = new int[this.teamNum];
		remain = new int[this.teamNum];
		game = new int[this.teamNum][this.teamNum];
		certificate = new HashMap<Integer, Bag<Integer>>(teamNum);
		in.readLine();
//		StdOut.println("start "+teamNum);
		for (int i = 0; i < teamNum; i++) {
//			String teamInfo = in.readLine();
//			StdOut.println("count "+i+ teamInfo);
			this.teamName[i] = in.readString();
			win[i] = in.readInt();
			loss[i] = in.readInt();
			remain[i] = in.readInt();
			for (int j = 0; j < this.teamNum; j++) {
				game[i][j] = in.readInt();
			}
//			StdOut.print(teamName[i]+" "+win[i]+" "+loss[i]+" "+remain[i]+" ");
//			for (int m = 0; m < this.teamNum; m++) {
//				StdOut.print(game[i][m]+" ");
//			}
//			StdOut.print("\n");
		}
	} 
	
	// number of teams
	public int numberOfTeams() {
		return this.teamNum;
	}  
	
	// all teams
	public Iterable<String> teams()  {
		return Arrays.asList(teamName);
	}   
	
	// number of wins for given team
	public int wins(String team)  {
		for (int i = 0; i < this.teamNum; i++) {
			if (team.equals(this.teamName[i])) {
				return win[i];
			}
		}
		throw new java.lang.IllegalArgumentException();
	}   
	
	 // number of losses for given team
	public int losses(String team) {
		for (int i = 0; i < this.teamNum; i++) {
			if (team.equals(this.teamName[i])) {
				return loss[i];
			}
		}
		throw new java.lang.IllegalArgumentException();
	}   
	
	 // number of remaining games for given team
	public int remaining(String team) {
		for (int i = 0; i < this.teamNum; i++) {
			if (team.equals(this.teamName[i])) {
				return remain[i];
			}
		}
		throw new java.lang.IllegalArgumentException();
	}    
	
	private int getTeamNum(String teamName) {
		for (int i = 0; i < this.teamNum; i++) {
			if (teamName.equals(this.teamName[i])) {
				return i;
			}
		}
		throw new java.lang.IllegalArgumentException();
	}
	
	// number of remaining games between team1 and team2
	public int against(String team1, String team2) {
		return game[getTeamNum(team1)][getTeamNum(team2)];
	}   
	
	//create FordFulkerson maxflow
	private FordFulkerson createGraph(int winnerTeam) {
		int numOfGames = (this.teamNum-2)*(this.teamNum-1)/2;//the remaining number of games between these teams.
		int verticleNum = numOfGames + this.teamNum + 2;//the number of verticles in the graph, s and t included.
		
		int winnerTolwin = win[winnerTeam] + remain[winnerTeam];
		FlowNetwork graph = new FlowNetwork(verticleNum);
		int sourceV = verticleNum - 2;//define the s and t verticle index.
		int destV = verticleNum - 1;
		
		for (int k = 0; k < this.teamNum; k++) {
			if (k != winnerTeam)
				graph.addEdge(new FlowEdge(k, destV, winnerTolwin - win[k]));
		}
		
		int againstCount = teamNum;
		for (int i = 0; i < this.teamNum - 1; i++) {
			for (int j = i+1; j < this.teamNum; j++) {
				if (i != winnerTeam && j != winnerTeam) {
					graph.addEdge(new FlowEdge(sourceV, againstCount, game[i][j]));
					graph.addEdge(new FlowEdge(againstCount, i, Double.POSITIVE_INFINITY));
					graph.addEdge(new FlowEdge(againstCount, j, Double.POSITIVE_INFINITY));
					againstCount++;
				}
			}
		}
		FordFulkerson maxflow = new FordFulkerson(graph, sourceV, destV);
		return maxflow;
	}
	
	private boolean trivialCheck(int winnerTeam) {
		int winnerTolwin = win[winnerTeam] + remain[winnerTeam];
		boolean flag = false;
		Bag<Integer> overTol= new Bag<Integer>();
		for (int m = 0; m < this.teamNum; m++) {
			if (win[m] > winnerTolwin) {
				overTol.add(m);
				flag = true;
			}
		}
		if(flag) {
			certificate.put(winnerTeam, overTol);
		}
		return flag;
	}
	
	private void nontrivialCheck(int winnerTeam) {
		FordFulkerson maxflow = createGraph(winnerTeam);
		Bag<Integer> teams = new Bag<Integer>();
		for (int n = 0; n < this.teamNum; n++) {
			if(maxflow.inCut(n) && n != winnerTeam) {
				teams.add(n);
			}
		}
		this.certificate.put(winnerTeam, teams);	
	}
	
	// is given team eliminated?
	public boolean isEliminated(String team) {
		int teamNum = getTeamNum(team);
		if (!certificate.containsKey(teamNum)) {
			if (trivialCheck(teamNum))
				return true;
			else {
				nontrivialCheck(teamNum);
			}
		}
		return !certificate.get(teamNum).isEmpty();

	}  
	
	// subset R of teams that eliminates given team; null if not eliminated
	public Iterable<String> certificateOfElimination(String team) {
		int teamNum = getTeamNum(team);
		if (!certificate.containsKey(teamNum)) {
			if (!trivialCheck(teamNum)) {
//				return true;
//			}
//			else {
				nontrivialCheck(teamNum);
			}
		}
		if (certificate.get(teamNum).isEmpty())
			return null;
		else {
			Bag<String> result = new Bag<String>();
			for (int i : certificate.get(teamNum)) {
				result.add(teamName[i]);
			}
			return result;
		}
	} 
	
	public static void main(String[] args) {
	    BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}

}