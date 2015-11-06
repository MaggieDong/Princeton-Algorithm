import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class WordNet {
	private Hashtable<String, Bag<Integer>> words;
	private ArrayList<String> synsetsList;
	private Digraph G;
	private SAP sap;
	// constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
	   this.words = new Hashtable<String, Bag<Integer>>();
	   this.synsetsList = new ArrayList<String>();
	   int synsetsNum = parseSynsets(synsets);
//	   synsetsNum = 6;////////////////////////////set a value to test
	   this.G = new Digraph(synsetsNum);
	   parseHypernyms(synsetsNum, hypernyms);
	   this.sap = new SAP(this.G);
   }

   private int parseSynsets (String synsets) {
	   In synsetsIn = new In(synsets);
	   int synsetsNum = 0;
	   
	   while(!synsetsIn.isEmpty()) {
		   String []fields = synsetsIn.readLine().split(",");
		   if (fields.length < 3)
			   throw new java.lang.IllegalArgumentException();
		   
		   int synsetID = Integer.parseInt(fields[0]);
		   synsetsList.add(synsetID, fields[1]);// add the second field and the corresponding synsetid to arraylist;
		   synsetsNum++;
//		   StdOut.println(fields[1]);
		   
		   String []fields1 = fields[1].split(" ");//split the synsets to each synword and store them in fields1;
		   int synsetNum = fields1.length;
		   for (int i = 0; i < synsetNum; i++) {
			   if(this.words.containsKey(fields1[i])) { //if the synword has already been in the hashtable, then just add its int id;
				   this.words.get(fields1[i]).add(synsetID);
			   }
			   else {
				   Bag newIntList = new Bag<Integer>();
				   newIntList.add(synsetID);
				   words.put(fields1[i], newIntList);
			   }
		   }
//		   StdOut.println("length "+fields1.length);	   
//		   StdOut.println(fields[0]);
//		   StdOut.println(fields[2]);
	   }
	   synsetsIn.close();
	   return synsetsNum;
   }
   
   private void parseHypernyms(int synsetsNum, String hypernyms) {
	   boolean [] notRoot = new boolean[synsetsNum];	//initial values are false, means all of them are roots;
	   In hypernymsIn = new In(hypernyms);
	   while(!hypernymsIn.isEmpty()) { 	//add the relationships to Digraph G;
		   String []fields = hypernymsIn.readLine().split(",");
//		   StdOut.print(fields.length+ " length\n");///////////////////////////
//		   if(fields.length < 2) //38003 has only one num =.=
//			   throw new java.lang.IllegalArgumentException();
		   int sourceNode = Integer.parseInt(fields[0]);
		   notRoot[sourceNode] = true;	//mark this node as noRoot;
//		   StdOut.print("sourceNode: "+ sourceNode+" others are: ");
		   for (int j = 1; j < fields.length; j++) {
//			   StdOut.print(Integer.parseInt(fields[j])+" ");
			   this.G.addEdge(sourceNode, Integer.parseInt(fields[j]));
		   }
//		   StdOut.print("\n");
	   }
	   hypernymsIn.close();
	   
	   DirectedCycle cycle = new DirectedCycle(this.G);//check if there are any cycle;
	   if (cycle.hasCycle())
		   throw new java.lang.IllegalArgumentException("Cycle detected!");
	   
	   int rootN = 0;
	   for (boolean noRoot : notRoot) {
		   if (!noRoot)
			   rootN++;
	   }
	   if(rootN > 1)
		   throw new java.lang.IllegalArgumentException("Multiple roots detected!");
   }
   
   // returns all WordNet nouns
   public Iterable<String> nouns() {
	   return Collections.list(this.words.keys());
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
	   return this.words.containsKey(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
	   if(!isNoun(nounA) || !isNoun(nounB))
		   throw new java.lang.IllegalArgumentException();//("noun is an invalid WordNet noun");
//	   Bag nounAid = new Bag<Integer>();
//	   Bag nounBid = new Bag<Integer>();
	   Bag<Integer> nounAid = words.get(nounA);
	   Bag<Integer> nounBid = words.get(nounB);
	   return this.sap.length(nounAid, nounBid);
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
	   if(!isNoun(nounA) || !isNoun(nounB))
		   throw new java.lang.IllegalArgumentException();//("noun is an invalid WordNet noun");
	   Bag<Integer> nounAid = words.get(nounA);
	   Bag<Integer> nounBid = words.get(nounB);
	   int ancestorId = this.sap.ancestor(nounAid, nounBid);
	   return synsetsList.get(ancestorId);
   }

   // do unit testing of this class
   public static void main(String[] args) {
	   String synsets = args[0];
	   String hypernyms = args[1];
	   WordNet net = new WordNet(synsets, hypernyms);
	    while (!StdIn.isEmpty()) {
	        String v = StdIn.readString();
	        String w = StdIn.readString();
	        int length   = net.distance(v, w);
	        String ancestor = net.sap(v, w);
	        StdOut.printf("length = %d, ancestor = %s\n", length, ancestor);
	    }
   }
}