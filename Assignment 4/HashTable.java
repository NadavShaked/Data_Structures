import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HashTable{
	// ---------------------- fields ---------------------- 
    private HashList[] hashTable;
    private int size;

	// ---------------------- constructors ----------------------
    public HashTable(String m) {
        try {
            this.size = Integer.parseInt(m);
            this.hashTable = new HashList[this.size];
            // initialize the hashTable with empty lists
            for(int i = 0; i < this.size; i++){
                hashTable[i] = new HashList();
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("input isn't Integer type");
        }
    }

	// ---------------------- methods ----------------------
    /**
     * @param path
     * Inserts the bad passwords to the hash table array
     */
    public void updateTable(String path) {
        String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);

        try {
            while((str = br.readLine()) != null) {
            	//str = str.toLowerCase();
                int k = ExternalFunctions.HornerRuleConverter(str);
                hashTable[hashFunction(k)].addFirstElemment(str);
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("Hash File text is wrong");
        }
    }

    /**
     * @param k
     * @return the hash function key
     */
    public int hashFunction(int k) {
        return k % this.size;
    }

    /**
     * @param str
     * @return true if str is exist in the hash table
     * 		   false if str isn't exist in  the hash table
     */
    public boolean isBadPassExist(String str){
        int k = ExternalFunctions.HornerRuleConverter(str);
        int hashIndex = hashFunction(k);
        HashListElement curElement = hashTable[hashIndex].getFirst();

        while(curElement != null) {
            if(curElement.getPassword().equals(str)) {
                return true;
            }
            curElement = curElement.getNext();
        }
        return false;
    }
    
    //Return the time that take to search all the password in the text file
    public String getSearchTime(String path) {
    	long start = System.nanoTime();
    	String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);

        try {
            while((str = br.readLine()) != null) {
            	//str = str.toLowerCase();
            	isBadPassExist(str);
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("Hash File text is wrong");
        }
        
        long timeElapsed = System.nanoTime() - start;
        return ExternalFunctions.TimeInMiles(timeElapsed);
    }
}