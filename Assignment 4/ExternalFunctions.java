import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class ExternalFunctions{
	// ---------------------- fields ---------------------- 
	private final static int p = 15486907;
	
	// ---------------------- methods ----------------------
    /**
     * @param path
     * @return the content of the file
     */
    public static BufferedReader getBufferedFile(String path) {
        BufferedReader br = null;

        try {
            FileReader fr = new FileReader(path);
            br = new BufferedReader(fr);
        }
        catch (IOException e) {
            System.out.println("File not found");
        }

        return br;
    }

    /**	
     * @param password
     * @return return the string value by Horner Rule
     */
    public static int HornerRuleConverter(String password) {
        long k = 0;
        for(int i = 0; i < password.length(); i++) {
            k = password.charAt(i) + (256 * k) % p;	
        }

        return (int)k;
    }

    /**	
     * @param str1
     * @param str2
     * @return positive number if str1 is after str2 in lexicographic order
     * 		   negative number if str2 is after str1 in lexicographic order
     * 		   0 if the strings are equals
     */
    public static int StringCompare(String str1, String str2) {
    	String str3 = str1.toLowerCase();
    	String str4 = str2.toLowerCase();
    	int str3Len = str3.length();
    	int str4Len = str4.length();
    	
    	for(int i = 0; i < str3Len & i < str4Len; i++) {
    		if((int)str3.charAt(i) != (int)str4.charAt(i)) {
    			return str3.charAt(i) - str4.charAt(i);
    		}
    	}
    	if(str3Len != str4Len) {
    		return str3Len - str4Len;
    	}
    	return 0;
    }
    
    /**
     * @param t
     * @return the convertion from nano to sec and milisec
     */
    public static String TimeInMiles(Long t) {
    	Double Miles = t / 1000000.0;
    	return String.format("%.4f", Miles);
    }
}