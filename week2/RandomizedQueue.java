import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
//    private int first = 0;       // index of first element of queue
//    private int last  = 0;       // index of next available slot
    
   public RandomizedQueue()  {
      q = (Item[]) new Object[1];
   }               // construct an empty randomized queue
   public boolean isEmpty()  {
	  return N==0; 
   }               // is the queue empty?
   public int size()  {
	   return N;
   } // return the number of items on the queue
   // resize the underlying array
   
   private void resize(int max) {
       assert max >= N;
       Item[] temp = (Item[]) new Object[max];
       for (int i = 0; i < N; i++) {
           temp[i] = q[i % q.length];
       }
       q = temp;
   }
   
   public void enqueue(Item item) {
//       StdOut.println(q.length + " is length, N is: "+N);
	   if(item == null) throw new NullPointerException();
       // double size of array if necessary and recopy to front of array
       if (N == q.length) {
//    	   StdOut.println("N==q.length!");
    	   resize(2*q.length);   // double size of array if necessary 
       }
       q[N++] = item;                        // add item
//       for(int i = 0; i < N; i++){
//    	   StdOut.println("q["+i+"]= "+q[i]);
//       }
   }          // add the item
   
   public Item dequeue() {
       if (isEmpty()) throw new NoSuchElementException();
       int k = StdRandom.uniform(N);
       Item item = q[k];
       q[k] = q[N-1];
//       StdRandom.shuffle(q, 0, N-1);
//       Item item = q[N-1];
       q[N-1] = null;                            // to avoid loitering
       N--;
       // shrink size of array if necessary
       if (N > 0 && N == q.length/4) resize(q.length/2);
//       for(int i = 0; i < N; i++){
//    	   StdOut.println("q["+i+"]= "+q[i]);
//       }
       return item;
   }                   // remove and return a random item
   
   public Item sample() {
	   if(isEmpty()) throw new NoSuchElementException();
	   int i = StdRandom.uniform(N);
//	   StdOut.println("i= "+ i+"q(i)= "+ q[i]);
	   return q[i];
   }                    // return (but do not remove) a random item
   
   public Iterator<Item> iterator()  {
       return new ArrayIterator();
   }       // return an independent iterator over items in random order
   
   // an iterator, doesn't implement remove() since it's optional
   private class ArrayIterator implements Iterator<Item> {
//       private int i = 0;
	   private Item[] anotherq = (Item[]) new Object[N];
       private int lastM = N;
       public ArrayIterator() {
    	   for(int p = 0; p < N; p++) {
    		   anotherq[p] = q[p];
    	   }
       }
       public boolean hasNext()  { return lastM>0/*i < N*/;                               }
       public void remove()      { throw new UnsupportedOperationException();  }

       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           int l = StdRandom.uniform(lastM);
           Item item = anotherq[l];
           anotherq[l] = anotherq[lastM-1];
           anotherq[lastM-1] = item;
           lastM--;
//           i++;
           return item;
       }
   }
   
   public static void main(String[] args) {
//       RandomizedQueue<String> q = new RandomizedQueue<String>();
//       StdOut.println("Type yes to start: ");
//       String yes = StdIn.readString();
//       while (yes.equals("yes")) {
//           StdOut.println("you choice: 1.enqueue; 2.dequeue; 3.is empty? 4.size; 5.sample: ");
//           int ch = StdIn.readInt();       
//           switch (ch) {
//		case 1://add first
//		    StdOut.println("Type a string to add first: ");
//	        String item = StdIn.readString();
//	        q.enqueue(item);
//			break;
//		case 2:
//			q.dequeue();
//			break;
//		case 3:
//			StdOut.println(q.isEmpty());
//			break;
//		case 4:
//			StdOut.println(q.size());
//			break;
//		case 5:
//			StdOut.println(q.sample());
////			StdOut.println(q.iterator());
//			break;
//			}
//           StdOut.println("Type yes to start: ");
//           yes = StdIn.readString();
//       }
//       StdOut.println("(" + q.size() + " left on queue)");
       
   }  // unit testing
}