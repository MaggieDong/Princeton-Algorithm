import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;         // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue
    
    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }
    
   public Deque() {
	   first = null;
	   last = null;
	   N = 0;
   }                          // construct an empty deque 
   public boolean isEmpty() {
	   return N==0 ;
   }                // is the deque empty?
   
   public int size()  {
	   return N;
   }                      // return the number of items on the deque
   
   public void addFirst(Item item)  {
	   if(item == null) throw new NullPointerException();
	   Node newfirst = new Node();
	   newfirst.item = item;
	   newfirst.previous = null;
	   if(isEmpty()) {
		   first = newfirst;
		   first.next = null;
		   last = newfirst;
//		   StdOut.println("firstime item is: "+first.item);
	   }
	   else {
		   first.previous = newfirst;
		   newfirst.next = first;
		   first = newfirst;
//		   StdOut.println("first item is: "+first.item);
	   }
	   N++;
   }        // add the item to the front
   public void addLast(Item item)  {
	   if(item == null) throw new NullPointerException();
	   Node newlast = new Node();
	   newlast.item = item;
	   newlast.next = null;
	   if(isEmpty()) {
		   first = newlast;
		   last = newlast;
		   last.previous = null;
//		   StdOut.println("firstime lastitem is: "+last.item);
	   }
	   else {
		   last.next = newlast;
		   newlast.previous = last;
		   last = newlast;
//		   StdOut.println("last item is: "+last.item);
	   }
	   N++;
   }         // add the item to the end
   
   public Item removeFirst()  {
       if (first == null) throw new NoSuchElementException("Queue underflow");
	   Item rmitem = first.item;
       N--;
       if(isEmpty()) {
    	   first = null;
    	   last = null;
       }
       else {
    	   first = first.next;
    	   first.previous = null;
       }
//	   StdOut.println("After remove first item is: "+first.item);
       return rmitem;
   }              // remove and return the item from the front
   
   public Item removeLast() {
       if (last == null) throw new NoSuchElementException("Queue underflow");
	   Item rmitem = last.item;
       N--;
       if(isEmpty()) {
    	   first = null;
    	   last = null;
       }
       else {
           last = last.previous;
    	   last.next = null;
       }
//	   StdOut.println("After remove last item is: "+last.item);
       return rmitem;
   }                // remove and return the item from the end
   
   public Iterator<Item> iterator()  {
       return new ListIterator();  	   
   }       // return an iterator over items in order from front to end
   
   private class ListIterator implements Iterator<Item> {
       private Node current = first;
       public boolean hasNext()  { return current != null;                     }
       public void remove()      { throw new UnsupportedOperationException();  }

       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           Item item = current.item;
           current = current.next; 
//           StdOut.print("cout: "+item);
           return item;
       }
   }
   
   public static void main(String[] args) {
//       Deque<String> q = new Deque<String>();
////       StdOut.println("first step!");
//       System.out.println("Type yes to start: ");
//       String s = StdIn.readString();
//       while (s.equals("yes")) {
//           StdOut.println("you choice: 1.add first; 2.add last; 3.is empty? 4.size; 5.remove first; 6. remove last");
//           int ch = StdIn.readInt();
//           
//           switch (ch) {
//		case 1://add first
//		    System.out.println("Type a string to add first: ");
//	        String item = StdIn.readString();
//	        q.addFirst(item);
//	        StdOut.println(q.iterator());
//			break;
//		case 2:
//		    System.out.println("Type a string to add last: ");
//			String itema = StdIn.readString();
//			q.addLast(itema);
//	        StdOut.println(q.iterator());
//			break;
//		case 3:
//			StdOut.println(q.isEmpty());
//	        StdOut.println(q.iterator());
//			break;
//		case 4:
//			StdOut.println(q.size());
//			break;
//		case 5:
//			q.removeFirst();
//	        StdOut.println(q.iterator());
//			break;
//		case 6:
//			q.removeLast();
//	        StdOut.println(q.iterator());
//			break;
//		}
//           System.out.println("Type yes to go on: ");
//           s = StdIn.readString();
//       }
//       StdOut.println("(" + q.size() + " left on queue)");
   }  // unit testing
}