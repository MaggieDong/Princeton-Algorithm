import java.util.Arrays;


public class Brute {
    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.005);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point pArr[] = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            pArr[i] = p;
            p.draw();
        }
		Arrays.sort(pArr);
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLUE);
        if(N >= 4) {
	        for(int p = 0; p < N-3; p ++) {
	        	for(int q = p+1; q < N-2; q++) {
	        		double slop_pq = pArr[p].slopeTo(pArr[q]);
	        		for(int r = q+1; r < N-1; r++) {
	        			double slop_pr = pArr[p].slopeTo(pArr[r]);
	        			for(int s = r+1; s < N; s++) {
	        				double slop_ps = pArr[p].slopeTo(pArr[s]);
	        				if(slop_pq == slop_pr && slop_pq == slop_ps) {
	        					StdOut.println(pArr[p].toString() + " -> " + pArr[q].toString() + " -> "+ pArr[r].toString() + " -> "+ pArr[s].toString());	        					
	        					pArr[p].drawTo(pArr[s]);
	        				}
	        			}
	        		}
	        	}
	        }
        }
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
        
    }
}
