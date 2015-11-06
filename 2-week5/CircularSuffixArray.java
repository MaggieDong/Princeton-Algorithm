import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
	
	private String suffix;
	private Integer [] index;
	private Integer sufLen;
	
	// circular suffix array of s
    public CircularSuffixArray(String s) {
    	if (s == null)
    		throw new java.lang.NullPointerException();
    	this.suffix = s;
    	this.sufLen = s.length();
    	this.index = new Integer[this.sufLen];
    	for (int i = 0; i < this.sufLen; i++) {
    		this.index[i] = i;
    	}
    	Arrays.sort(index, new Comparator<Integer>() {
    		@Override
    		public int compare(Integer ind1, Integer ind2) {
    			int indFirst = ind1;
    			int indSecond = ind2;
    			for (int j = 0; j < suffix.length(); j++) {
    				if (indFirst == suffix.length())
    					indFirst = 0;
    				if (indSecond == suffix.length())
    					indSecond = 0;
    				if (suffix.charAt(indFirst) < suffix.charAt(indSecond))
    					return -1;
    				if (suffix.charAt(indFirst) > suffix.charAt(indSecond))
    					return 1;
    				
    				indFirst ++;
    				indSecond ++;
    			}
    			return 0;
    		}
    	});    	
    } 
    
    // length of s
    public int length() {
    	return this.sufLen;
    }   
    
    // returns index of ith sorted suffix
    public int index(int i) {
    	return this.index[i];
    }   
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
    	String s = "ABRACADABRA!";
    	CircularSuffixArray testArr = new CircularSuffixArray(s);
    	int len = testArr.length();
    	for (int i = 0; i < len; i++) {
    		StdOut.println(testArr.index(i));
    	}
    }
}