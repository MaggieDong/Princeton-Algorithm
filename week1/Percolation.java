public class Percolation {
	private WeightedQuickUnionUF percUF;
	private WeightedQuickUnionUF UFperc;
	private boolean[] status;// true mean open, false means close;
	private int Num = 0;
   public Percolation(int N) {// create N-by-N grid, with all sites blocked
	     if (N <= 0){
	    	 throw new IllegalArgumentException();
	     }
	   Num = N;
	   percUF = new WeightedQuickUnionUF(N*(N+1));
	   UFperc = new WeightedQuickUnionUF(N*(N+2));
	   status = new boolean[N*(N+1)];
	   for (int i = 0; i < N; i++) {
		percUF.union(0, i);
		UFperc.union(0, i);
		UFperc.union(N*(N+1), N*(N+1)+i);
		status[i] = true;
	  }
   }            
   public void open(int i, int j) { // open site (row i, column j) if it is not open already
	   if(i < 1 || i > Num){
		   throw new IndexOutOfBoundsException("row index" + i + "out of bound!");
	   }
	   if(j < 1 || j > Num){
		   throw new IndexOutOfBoundsException("row index" + j + "out of bound!");
	   }
	   else{// if both i and j are not out of boundary.
		   status[i*Num+j-1] = true;
		   if(status[(i-1)*Num+j-1] == true){//if the upper site is open, then connect them.
			   percUF.union(i*Num+j-1, (i-1)*Num+j-1);
			   UFperc.union(i*Num+j-1, (i-1)*Num+j-1);
	//		   StdOut.println("connect to upper site!");
		   }
		   if(i < Num && (status[(i+1)*Num+j-1] == true)){//if the lower site is open, then connect them.
			   percUF.union(i*Num+j-1, (i+1)*Num+j-1);
			   UFperc.union(i*Num+j-1, (i+1)*Num+j-1);
		   }
		   if(j > 1 && (status[i*Num+j-2]) == true){// if the left site is open, then connect them.
			   percUF.union(i*Num+j-1, i*Num+j-2);
			   UFperc.union(i*Num+j-1, i*Num+j-2);
		   }
		   if(j < Num && (status[i*Num+j]) == true){// if the right site is open, then connect them.
			   percUF.union(i*Num+j-1, i*Num+j);
			   UFperc.union(i*Num+j-1, i*Num+j);
		   }
		   if(i == Num){
			   UFperc.union(i*Num+j-1, (i+1)*Num+j-1);   
		   }
	   }
   }        
   public boolean isOpen(int i, int j) {// is site (row i, column j) open?
	   if(i < 1 || i > Num){
		   throw new IndexOutOfBoundsException("row index" + i + "out of bound!");
	   }
	   if(j < 1 || j > Num){
		   throw new IndexOutOfBoundsException("row index" + j + "out of bound!");
	   }
	   else
		   return status[i*Num+j-1];
   }    
   public boolean isFull(int i, int j) {// is site (row i, column j) full?
//	   StdOut.println("Full or not? "+ percUF.connected(i*Num+j-1, 0));
	   if(i < 1 || i > Num){
		   throw new IndexOutOfBoundsException("row index" + i + "out of bound!");
	   }
	   if(j < 1 || j > Num){
		   throw new IndexOutOfBoundsException("row index" + j + "out of bound!");
	   }
	   return percUF.connected(i*Num+j-1, 0);
   }    
   public boolean percolates() {// does the system percolate?
//	   for(int k = 0; k < Num; k++){
//		   if(percUF.connected(Num*Num+k, 0)){
//			   return true;
//		   }
//	   }
//	   return false;
	   return UFperc.connected(0, Num*(Num+1));
   }            

   public static void main(String[] args){ // test client (optional)
   }  
}
