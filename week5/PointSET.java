
public class PointSET {
   private SET<Point2D> Pset;

   public PointSET() {
	   Pset = new SET<Point2D>();
   }                              // construct an empty set of points 
   public boolean isEmpty() {
	   return Pset.isEmpty();
   }                     // is the set empty? 
   public int size() {
	   return Pset.size();
   }                        // number of points in the set 
   public void insert(Point2D p) {
	   if(!Pset.contains(p))
		   Pset.add(p);
   }             // add the point to the set (if it is not already in the set)
   public boolean contains(Point2D p) {
	   return Pset.contains(p);
   }           // does the set contain point p? 
   public void draw() {
	   for(Point2D p : Pset)
		   p.draw();
   }                        // draw all points to standard draw 
   public Iterable<Point2D> range(RectHV rect) {
	   Stack<Point2D> inRecPoint = new Stack<Point2D>();
	   for(Point2D p : Pset) {
		   if(rect.contains(p))
			   inRecPoint.push(p);
	   }
	   return inRecPoint;
   }            // all points that are inside the rectangle 
   
   public Point2D nearest(Point2D p) {
	   Point2D lastP = null;
	   for(Point2D otherP : Pset) {
		   if (lastP == null) 
			   lastP = otherP;
		   if(otherP.distanceSquaredTo(p) < lastP.distanceSquaredTo(p))
			   lastP = otherP;	   
	   }
	   return lastP;
   }            // a nearest neighbor in the set to point p; null if the set is empty 

   public static void main(String[] args) {
	   
   }                 // unit testing of the methods (optional) 
}