public class MoveToFront {
	private static final int R = 256;
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // read the input
    	char[] asciiArr = new char[R];
    	for (char j = 0; j < R; j++) {
    		asciiArr[j] = j;
    	}
    	
        String s = BinaryStdIn.readString();
        int len = s.length();
        char[] input = s.toCharArray();
        char temp;
        char inputTem;
        for (int i = 0; i < len; i++) {
        	inputTem = input[i];
        	char j = 0;
        	while(input[i] != asciiArr[j]) {
        		temp = asciiArr[j];
        		asciiArr[j] = inputTem;
        		inputTem = temp;
        		j++;
        	}
        	asciiArr[j] = inputTem;
        	BinaryStdOut.write(j);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	char[] asciiArr = new char[R];
    	for (char j = 0; j < R; j++) {
    		asciiArr[j] = j;
    	}
    	char num;
    	while(!BinaryStdIn.isEmpty()) {
    		num = BinaryStdIn.readChar();
    		BinaryStdOut.write(asciiArr[num]);
    		char tem = asciiArr[num];
    		for (int i = num; i > 0; i--) {
    			asciiArr[i] = asciiArr[i - 1];
    		}
    		asciiArr[0] = tem;
    	}
    	BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}