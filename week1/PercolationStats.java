public class PercolationStats {
	private double resultArr[];
	private double resMean;
	private double resStd;
	private int Num;
	private int Tum;
   public PercolationStats(int N, int T){ // perform T independent experiments on an N-by-N grid
     if (N <= 0){
    	 throw new IllegalArgumentException();
     }
	 if (T <= 0){
	     throw new IllegalArgumentException();
	 }
	   Num = N;
	   Tum = T;
	   resultArr = new double[T];
	   for(int k = 0; k < T; k++){
		   int count = 0;
		   Percolation perco = new Percolation(Num);
		   while(!perco.percolates()){// if it is still not percolate, then keep opening site.
			   int i = StdRandom.uniform(Num)+1;
			   int j = StdRandom.uniform(Num)+1;
			   while(perco.isOpen(i, j)){// if the site is already open, randomly choose another site until it hasn't been opened
				   i = StdRandom.uniform(Num)+1;
				   j = StdRandom.uniform(Num)+1;			   
			   }
			   count ++;
			   perco.open(i, j);			   
		   }
		   resultArr[k] = (double)count/((double) N* (double) N);
	   }
	   resMean = StdStats.mean(resultArr);
	   resStd = StdStats.stddev(resultArr);
   }     
   public double mean(){// sample mean of percolation threshold
	   return resMean;
   }                      
   public double stddev(){// sample standard deviation of percolation threshold
	   return resStd;
   }                    
   public double confidenceLo(){// low  endpoint of 95% confidence interval
	   return resMean - 1.96*resStd / Math.sqrt(Tum);
   }              
   public double confidenceHi(){// high endpoint of 95% confidence interval
	   return resMean + 1.96*resStd / Math.sqrt(Tum);
   }              

   public static void main(String[] args){// test client (described below)
	   int N = StdIn.readInt();
	   int T = StdIn.readInt();
	   PercolationStats testPer = new PercolationStats(N, T);
	   StdOut.println("mean = " + testPer.mean());
       StdOut.println("stddev = " + testPer.stddev());
       StdOut.println("95% confidence interval " + testPer.confidenceLo() + ", " + testPer.confidenceHi());
   }    
}