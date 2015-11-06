import java.util.Arrays;

public class BurrowsWheeler {
	private static final int R = 256;
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String s = BinaryStdIn.readString();//"ABRACADABRA!"; //
        CircularSuffixArray cirSuf = new CircularSuffixArray(s);
        int len = cirSuf.length();
        int first = 0;
        char[] ch = new char[len];
        for (int i = 0; i < len; i++) {
        	if (cirSuf.index(i) == 0) {
        		first = i;
        		ch[i] = s.charAt(len - 1);
        		continue;
        	}
        	ch[i] = s.charAt(cirSuf.index(i) - 1);
        }
        BinaryStdOut.write(first);
        for (int j = 0; j < len; j++) {
        	BinaryStdOut.write(ch[j]);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
    	int first = BinaryStdIn.readInt();
//    	StdOut.println(first);
    	String lastLetter = BinaryStdIn.readString();
//    	char[] t = lastLetter.toCharArray();
    	char[] firstLetter = lastLetter.toCharArray();//t;
    	Arrays.sort(firstLetter);
    	int[] asciiCount = new int[R+1];
    	int len = lastLetter.length();

    	int[] next = new int[len];
    	for (int i = 0; i < len; i++) { //build the count array for each character, "+1" means count of A should be recorded on B
    		asciiCount[lastLetter.charAt(i)+1] ++;
    	}   	
    	for (int j = 1; j < R+1; j++) { //count the start number for each character. "!" is 0, "A" is 1 in the abra.txt example.
    		asciiCount[j] += asciiCount[j-1];
    	}  	
    	for (int k = 0; k < len; k++) { //compute the next array.
    		next[asciiCount[lastLetter.charAt(k)] ++] = k;
    	}    	
    	int curr = first;
    	for (int m = 0; m < len; m++) {
    		BinaryStdOut.write(firstLetter[curr]);
    		curr = next[curr];
    	}
    	BinaryStdOut.close(); //very important!
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) 
        	encode();
        else if (args[0].equals("+")) 
        	decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}