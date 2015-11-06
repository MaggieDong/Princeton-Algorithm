public class SAP {
	
	private Digraph Gra;
	private int shorestDis;
	private int shorestAnc;
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
	   this.Gra = new Digraph(G);
   }

   private void findShortest(int v, int w) {
//	   int [] shorest = new int[2];
	   shorestDis = Integer.MAX_VALUE;
	   shorestAnc = -1;
	   DeluxeBFS vBFS = new DeluxeBFS(Gra, v);
	   DeluxeBFS wBFS = new DeluxeBFS(Gra, w);
	   boolean[] vMarked = vBFS.getMarkedArr();
	   boolean[] wMarked = wBFS.getMarkedArr();
//	   int shorestDis = Integer.MAX_VALUE;
//	   int shorestAnc = -1;
	   for (int i = 0; i < vMarked.length; i++) {
		   if (vMarked[i] && wMarked[i]) {
			   int dis = vBFS.distTo(i) + wBFS.distTo(i);
			   if (dis < shorestDis) {
				   this.shorestDis = dis;
				   this.shorestAnc = i;
			   }
		   }
	   }
	   if (this.shorestDis == Integer.MAX_VALUE) {
		   this.shorestDis = -1;
	   }
//	   shorest[0] = shorestDis;
//	   shorest[1] = shorestAnc;
//	   return shorest;
   }
//   private boolean isValid(int v) {
//	   return (v >= 0 && v < this.Gra.V());
//   }
   
   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
	   findShortest(v, w);
	   return this.shorestDis;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
//	   if (!isValid(v) || !isValid(w))
//		   throw new java.lang.NullPointerException();
	   findShortest(v, w);
	   return this.shorestAnc;
   }
   
   private void findShorest(Iterable<Integer> v, Iterable<Integer> w) {
	   if (v == null || w == null)
		   throw new java.lang.NullPointerException();
	   DeluxeBFS vBFS = new DeluxeBFS(Gra, v);
	   DeluxeBFS wBFS = new DeluxeBFS(Gra, w);

	   this.shorestDis = Integer.MAX_VALUE;
	   this.shorestAnc = -1;
	   int disTemp = Integer.MAX_VALUE;
//	   int AncTemp = -1;
	   for (int i = 0; i < this.Gra.V(); i++) {
		   if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i) && vBFS.distTo(i) + wBFS.distTo(i) < disTemp) {
			   this.shorestAnc = i;
			   disTemp = vBFS.distTo(i) + wBFS.distTo(i);
		   }
	   }
//	   for (int vVertex : v) {
//		   for (int wVertex : w) {
//			   findShortest(vVertex, wVertex);
//			   if (this.shorestDis < disTemp && this.shorestDis != -1) {
//				   disTemp = this.shorestDis;
//				   AncTemp = this.shorestAnc;
//			   }
//		   }
//	   }
	   if (disTemp == Integer.MAX_VALUE) {
		   disTemp = -1;
	   }
	   this.shorestDis = disTemp;
//	   this.shorestAnc = AncTemp;
   }
   
   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
	   findShorest(v, w);
	   return this.shorestDis;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
	   findShorest(v, w);
	   return this.shorestAnc;
   }

   // do unit testing of this class
	public static void main(String[] args) {
	    In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
	}
}