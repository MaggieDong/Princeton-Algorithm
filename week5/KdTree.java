
public class KdTree {		
		private static class kdNode {
		   private final Point2D point;      // the point
//		   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		   private kdNode lfNode;        // the left/bottom subtree
		   private kdNode rtNode;        // the right/top subtree
		   private final boolean isVertical;
//		   private final int NodeL;
		   
		   public kdNode (final Point2D p, kdNode lfNode, kdNode rtNode, final boolean isVertical/*, final int NodeL*/) {
			   this.point  = p;
			   this.lfNode = lfNode;
			   this.rtNode = rtNode;
			   this.isVertical = isVertical;
//			   this.NodeL = NodeL;
		   }
		   public int compareTo(RectHV rec) {
			   double x = this.point.x();
			   double y = this.point.y();
			   if(this.isVertical) {
				   if(x > rec.xmax())
					   return 1;
				   if(x < rec.xmin())
					   return -1;
			   }
			   else {
				   if(y > rec.ymax())
					   return 1;
				   if(y < rec.ymin()) 
					   return -1;
			   }
			   return 0;
		   }
		}
		private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);
		private kdNode rootNode;
		private int treeSize;

	   public KdTree() {
		   this.rootNode = null;
		   this.treeSize = 0;
	   }                              // construct an empty set of points 
	   public boolean isEmpty() {
		   return rootNode == null;
	   }                     // is the set empty? 
	   public int size() {
		   return treeSize;
	   }                        // number of points in the set 
	   public void insert(Point2D p) {
		   this.rootNode = insert(this.rootNode, p, true);
	   }             // add the point to the set (if it is not already in the set)
	   private kdNode insert(kdNode currentNode, Point2D p, boolean isVertical) {
		   if (currentNode == null) {
			   this.treeSize++;
			   return new kdNode(p, null, null, isVertical);
		   }
		   else {
			   if (currentNode.point.x() == p.x() && currentNode.point.y() == p.y())
				   return currentNode;
			   else {
				   if((currentNode.isVertical && p.x()<currentNode.point.x()) || (!currentNode.isVertical && p.y() < currentNode.point.y())) {
					   currentNode.lfNode = insert(currentNode.lfNode, p, !currentNode.isVertical);
				   }
				   else 
					   currentNode.rtNode = insert(currentNode.rtNode, p, !currentNode.isVertical);
			   }
			   return currentNode;
		   }
	   }
	   public boolean contains(Point2D p) {
		   return contains(this.rootNode, p);
	   }
	   private boolean contains(kdNode currentNode, Point2D p) {
		   if(currentNode == null)
			   return false;
		   else {
			   if (currentNode.point.x() == p.x() && currentNode.point.y() == p.y())
				   return true;
			   else {
				   if((currentNode.isVertical && p.x()<currentNode.point.x()) || (!currentNode.isVertical && p.y() < currentNode.point.y())) {
					   return contains(currentNode.lfNode, p);
				   }
				   else 
					   return contains(currentNode.rtNode, p);
			   }
		   }
	   }           // does the set contain point p? 
	   public void draw() {
		   StdDraw.setScale(0, 1);
		   StdDraw.setPenColor(StdDraw.BLACK);
		   StdDraw.setPenRadius(.01);
		   CONTAINER.draw();
		   draw(this.rootNode, CONTAINER);
	   }                        // draw all points to standard draw 
	   
	   private void draw(final kdNode currentNode, final RectHV rect) {
		   if (currentNode == null)
			   return;
		   else {
			   StdDraw.setPenColor(StdDraw.BLACK);
			   StdDraw.setPenRadius(.01);
			   currentNode.point.draw();
			   if (currentNode.isVertical) {
				   StdDraw.setPenColor(StdDraw.RED);
				   StdDraw.setPenRadius();
				   StdDraw.line(currentNode.point.x(), rect.ymin(), currentNode.point.x(), rect.ymax());
			   }
			   else {
				   StdDraw.setPenColor(StdDraw.BLUE);
				   StdDraw.setPenRadius();
				   StdDraw.line(rect.xmin(), currentNode.point.y(), rect.xmax(), currentNode.point.y());
			   }
		   }
		   draw(currentNode.lfNode,leftRec(currentNode, rect));
		   draw(currentNode.rtNode, rightRec(currentNode, rect));
	   }
	   
	   private RectHV leftRec(kdNode currentNode, final RectHV rect) {
		   if (currentNode.isVertical) {
			   return new RectHV(rect.xmin(), rect.ymin(), currentNode.point.x(), rect.ymax());
		   }
		   else 
			   return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), currentNode.point.y());
	   }
	   
	   private RectHV rightRec(kdNode currentNode, final RectHV rect) {
		   if (currentNode.isVertical) {
			   return new RectHV(currentNode.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
		   }
		   else 
			   return new RectHV(rect.xmin(), currentNode.point.y(), rect.xmax(), rect.ymax());
	   }
	   
	   public Iterable<Point2D> range(RectHV rect) {
		   Stack<Point2D> pointInRec = new Stack<Point2D>();
		   range(this.rootNode, rect, pointInRec);
		   return pointInRec;
	   }            // all points that are inside the rectangle 
	   private void range(kdNode currentNode, RectHV rect, Stack<Point2D> pointInRec) {
		   if(currentNode == null)
			   return;
		   else {
			   if(currentNode.compareTo(rect) > 0)
				   range(currentNode.lfNode, rect, pointInRec);
			   else if (currentNode.compareTo(rect) < 0) 
				   range(currentNode.rtNode, rect, pointInRec);
			   else {
				   if(rect.contains(currentNode.point))
					   pointInRec.push(currentNode.point);
				   range(currentNode.lfNode, rect, pointInRec);
				   range(currentNode.rtNode, rect, pointInRec);
			   }
		   }
	   }
	   
	   public Point2D nearest(Point2D p) {
		   return searchNearest(this.rootNode, CONTAINER, p, null);
	   }            // a nearest neighbor in the set to point p; null if the set is empty 

	   private Point2D searchNearest(kdNode currentNode, RectHV rect, Point2D p, Point2D nearest) {
		   if (currentNode == null) {
			   return nearest;
		   }
		   double disToNode = 0.0;
		   double disToRec = 0.0;
		   RectHV left = null;
		   RectHV right = null;
		   
		   if(nearest != null) {
			   disToNode = nearest.distanceSquaredTo(p);
			   disToRec = rect.distanceSquaredTo(p);
		   }
		   if(nearest == null || disToRec < disToNode) {
			   if (nearest == null || disToNode > currentNode.point.distanceSquaredTo(p))
				   nearest = currentNode.point;
			   if (currentNode.isVertical) {
				   left = new RectHV(rect.xmin(), rect.ymin(), currentNode.point.x(), rect.ymax());
				   right = new RectHV(currentNode.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
				   
				   if (p.x() < currentNode.point.x()) {
					   nearest = searchNearest(currentNode.lfNode, left, p, nearest);
					   nearest = searchNearest(currentNode.rtNode, right, p, nearest);
				   }
				   else {
					   nearest = searchNearest(currentNode.rtNode, right, p, nearest);
					   nearest = searchNearest(currentNode.lfNode, left, p, nearest);
				   }
			   }
			   else {
				   left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), currentNode.point.y());
				   right = new RectHV(rect.xmin(), currentNode.point.y(), rect.xmax(), rect.ymax());
				   
				   if(p.y() < currentNode.point.y()) {
					   nearest = searchNearest(currentNode.lfNode, left, p, nearest);
					   nearest = searchNearest(currentNode.rtNode, right, p, nearest);
				   }
				   else {
					   nearest = searchNearest(currentNode.rtNode, right, p, nearest);
					   nearest = searchNearest(currentNode.lfNode, left, p, nearest);
				   }	   
			   }
		   }
		   return nearest;
	   }
	   public static void main(String[] args) {
	        String filename = args[0];
	        In in = new In(filename);


//	        StdDraw.show(0);

	        // initialize the data structures with N points from standard input
	        KdTree kdtree = new KdTree();
	        while (!in.isEmpty()) {
	            double x = in.readDouble();
	            double y = in.readDouble();
	            Point2D p = new Point2D(x, y);
	            kdtree.insert(p);
		        StdOut.println(kdtree.size());
	        }
	        StdOut.println(kdtree.size());
	   }                 // unit testing of the methods (optional) 
}
