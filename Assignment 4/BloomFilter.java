import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BloomFilter {
	// ---------------------- fields ---------------------- 
	private final static int p = 15486907;
	boolean[] bloomFilterArr;
    int[][] multyFancArr; // matrix that respresent the hash functions params a=[0], b=[1]
    int size; // the size of bloomFilterArr

	// ---------------------- constructors ----------------------
    public BloomFilter(String m1, String path) {
        this.size = Integer.parseInt(m1);
        this.bloomFilterArr = new boolean[this.size];
        this.multyFancArr = new int[TxtFileLineCounter(path)][2];
        InsertHashFunction(multyFancArr, path);
    }

	// ---------------------- methods ----------------------
    /**
     * @param path
     * mark the cells of the bad password with '1'.
     */
    public void updateTable(String path) {
        String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);

        try {
            while((str = br.readLine()) != null) {
            	//str = str.toLowerCase();
                int k = ExternalFunctions.HornerRuleConverter(str);

                int multyFancArrLength = multyFancArr.length;
                for(int i = 0; i < multyFancArrLength; i++) { // calculate CalcHashKey and intialize the cell with true
                    bloomFilterArr[CalcHashKey(multyFancArr[i][0], multyFancArr[i][1], k)] = true;
                }
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("Hash File text is wrong");
        }
    }

    /**
     * @param a: a parameter that represent hash function
     * @param b: a parameter that represent hash function
     * @param k: a key, calculated by Horner rule
     * @return the hash function key for specific k value
     */
    private int CalcHashKey(int a, int b, int k) {
        return (((a * k + b) % p) % this.size);
    	//return (((a * k) + b % p) % this.size);
    }

    /**
     * @param path
     * @return the number of lines in the txt file
     */
    private static int TxtFileLineCounter(String path) {
        String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);
        int rowsCounter = 0;

        try {
            while((str = br.readLine()) != null) {
            	//str = str.toLowerCase();
                rowsCounter++;
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("Hash File text is wrong");
        }
        return rowsCounter;
    }

    /**
     * @param multyFancArr - get the func matrix
     * @param path
     * initialize the multyFancArr from the file params
     */
    private void InsertHashFunction(int[][] multyFancArr, String path) {
        String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);

        //Insert the hash functions into the matrix
        try {
            int counter = 0;
            while((str = br.readLine()) != null) {
            	//str = str.toLowerCase();
                int i = str.indexOf('_');
                multyFancArr[counter][0] = Integer.parseInt(str.substring(0, i)); // a param
                multyFancArr[counter][1] = Integer.parseInt(str.substring(i+1)); // b param
                counter++;
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("Hash File text is wrong");
        }
    }

    /**
     * @param hashTable
     * @param path
     * @return the false positive percentage by counting the password that marks as bad in the bloom filter
     * 		   and by counting the good passwords
     */
    public String getFalsePositivePercentage(HashTable hashTable, String path) {
        String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);
        double falsePosiveC = 0, goodPassC = 0; // the passwords that tought to be bad, but actually there good beacause they not in the chaining hash table
        
        try {
            while((str = br.readLine()) != null) {
            	//str = str.toLowerCase();
                if(isBadPassword(str)){
                    if(!hashTable.isBadPassExist(str)){
                        // will check if its bad also in the chaining
                    	goodPassC++;
                        falsePosiveC++;
                    }
                }
                else {
                	goodPassC++;
                }
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("Hash File text is wrong");
        }
        return ((Double)(falsePosiveC / goodPassC)).toString();
    }

    /**
     * @param pass
     * @return true if pass marked as bad password in the bloom filter
     * 		   false if pass does't mark as bad password in the bloom filter
     */
    public boolean isBadPassword(String pass) {
        int k = ExternalFunctions.HornerRuleConverter(pass);

        int multyFancArrLength = multyFancArr.length;
        boolean isBad = true;
        for(int i = 0; i < multyFancArrLength; i++) { // calculate CalcHashKey and intialize the cell with true
            if (bloomFilterArr[CalcHashKey(multyFancArr[i][0], multyFancArr[i][1], k)] == false) {
                isBad = false;
                break;
            }
        }

        return isBad;
    }

    /**
     * @param pass
     * @return the number of the passwords that marks as bad in the bloom filter
     */
    public String getRejectedPasswordsAmount(String path) {
        String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);
        Integer bloomBadPassC = 0; // all the bad passwords from bloom filter

        try {
            while((str = br.readLine()) != null) {
            	//str = str.toLowerCase();
                if(isBadPassword(str)) {
                    bloomBadPassC++;
                }
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("Hash File text is wrong");
        }
        return bloomBadPassC.toString();
    }
}