public class Outcast {
	private WordNet wordNet;
	
   public Outcast(WordNet wordnet) { // constructor takes a WordNet object 
	   this.wordNet = wordnet;
   }        
   
   public String outcast(String[] nouns) { // given an array of WordNet nouns, return an outcast
	   int nounsNum = nouns.length;
	   int[] dis = new int[nounsNum];	//default value is 0;
	   for (int i = 0; i < nounsNum-1; i++) {
		   for (int j = i+1; j < nounsNum; j++) {
			   int tempDis = this.wordNet.distance(nouns[i], nouns[j]);
			   dis[i] += tempDis;
			   dis[j] += tempDis;
		   }
	   }
	   int maxDis = 0;
	   int maxIndex = 0;
	   for (int k = 0; k < nounsNum; k++) {
		   if (dis[k] > maxDis) {
			   maxDis = dis[k];
			   maxIndex = k;
		   }
	   }
	   return nouns[maxIndex];
   }  
   
   public static void main(String[] args) {
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	} // see test client below
}