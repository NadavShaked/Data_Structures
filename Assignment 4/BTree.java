import java.io.BufferedReader;
import java.io.IOException;

public class BTree {
    private BTreeNode root;
    private int t;


    public BTree(String t){
        this.t = Integer.parseInt(t);
        if (this.t < 2)
            throw new IllegalArgumentException("t must be bigger than 1");
        root = new BTreeNode(this.t);
    }

    public boolean isEmpty(){
        return (root == null);
    }

    /**
     * @param x
     * @param key
     * @return the junction of the key and the index of the key in the junction
     */
    public Object[] Search(BTreeNode x, String key) {
    	if (x != null) {
    		int i = 0;
        	while (i < x.GetN() && (ExternalFunctions.StringCompare(key, x.GetKey(i)) > 0)) {
        		i++;
        	}
        	if (i < x.GetN() & key.equals(x.GetKey(i))) {
        		Object[] o = {x, i};
        		return o;
        	}
        	if (x.IsLeaf())
        		return null;
        	return Search(x.GetJumction(i) , key);
    	}
    	return null;
    }
    
    /**
     * @param key represent the password
     * the method will insert the key into the right place in the BTree by LEX order.
     */
    public void insert(String key) {
        BTreeNode r = this.root;
        if(r.GetN() == 2 * t - 1) { // checks if the root is full, than splits the root to parent and two junctions
            BTreeNode s = new BTreeNode(this.t);
            this.root = s;
            s.SetIsLeaf(false);
            s.SetN(0);
            s.SetJumction(0, r);
            SplitChild(s, 0);
            r = s;
        }
        InsertNonFull(r, key); //the root isn't full so the algo can insert the key to non-full root
    }

    /**
     * @param x: the parent of the junction that will split
     * @param i: the index of the junction that will split in the parent
     * the method split the junction to 2 by taking the middle key in the junction as a parent.
     * the other keys are spilted to 2 child junction of the parent.
     */
    public void SplitChild(BTreeNode x, int i) {
        BTreeNode z = new BTreeNode(this.t); // The right junction of the new inserted key
        BTreeNode y = x.GetJumction(i);// The left junction of the new inserted key
        z.SetIsLeaf(y.IsLeaf());
        z.SetN(this.t - 1);

        for (int j = 0; j < this.t - 1; j++) { // copy all the keys that will be in the right junction from the splited junction
            z.SetKey(j, y.GetKey(j + this.t));
        }

        if (!y.IsLeaf()) {
            for (int j = 0; j < this.t; j++) { // copy all the junctions that will be in the right junction from the splited junction
                z.SetJumction(j, y.GetJumction(j + this.t));
            }
        }

        y.SetN(this.t - 1);

        for (int j = x.GetN() + 1; j > i + 1; j--) { // transfer all the junctions right in one place (makes a place to the inserted junction)
            x.SetJumction(j, x.GetJumction(j - 1));
        }
        x.SetJumction(i + 1, z); // insert the splited junction
        for (int j = x.GetN(); j > i; j--) { // transfer all the junctions right in one place (makes a place to the inserted key)
            x.SetKey(j, x.GetKey(j - 1));
        }
        x.SetKey(i, y.GetKey(t - 1)); // insert the middle key of the splited junction
        x.SetN(x.GetN() + 1); // update the parent junction keys number
    }

    /**
     * @param x
     * @param key
     */
    public void InsertNonFull(BTreeNode x, String key) {
        int i = x.GetN() - 1;
        if (i == -1) {
        	x.SetKey(i + 1, key);
            x.SetN(x.GetN() + 1);
        }
        else {
        	if(x.IsLeaf()) {
                while(i >= 0 && (ExternalFunctions.StringCompare(key, x.GetKey(i)) < 0)) { //find the correct place to inserts the key by iterating the keys
                    x.SetKey(i + 1, x.GetKey(i));
                    i--;
                }
                x.SetKey(i + 1, key);
                x.SetN(x.GetN() + 1);
            }
            else {
                while (i >= 0 && (ExternalFunctions.StringCompare(key, x.GetKey(i)) < 0)) { //find the correct junction to inserts the key by iterating the keys
                    i--;
                }
                i++;
                BTreeNode y = x.GetJumction(i);
                if (y.GetN() == 2 * this.t - 1) { // checks if the junction is full, than splits the junction to two junctions and insert the middle key to the parent junction
                    SplitChild(x, i);
                    if (ExternalFunctions.StringCompare(key, x.GetKey(i)) > 0) {
                        i++;
                    }
                }
                InsertNonFull(x.GetJumction(i), key); //the junction isn't full so the algo can insert the key to non-full junction
            }
        }
    }
    
    //Return inOrder String of the B-Tree
    public String toString() {
    	String str = this.root.ToString(0);
    	//return str.substring(0, str.length() - 1)
        return str.substring(0, str.length() - 1).toLowerCase();
    }
    
    //Return the time that take to search all the password in the text file
    public String getSearchTime(String path) {
    	long start = System.nanoTime();
    	String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);

        try {
            while((str = br.readLine()) != null) { // Insert all the password to the tree by the algo. here the dog buried
                //str = str.toLowerCase();
            	Search(this.root, str);
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("Hash File text is wrong");
        }
        
        long timeElapsed = System.nanoTime() - start;
        return ExternalFunctions.TimeInMiles(timeElapsed);
    }
    
    //Insert all the password to the B-Tree
    public void createFullTree(String path) {
        String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);

        try {
            while((str = br.readLine()) != null) { // Insert all the password to the tree by the algo. here the dog buried
                //str = str.toLowerCase();
            	insert(str);
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("File text is wrong");
        }
    }
    
    public void deleteKeysFromTree(String path) {
    	String str = null;
        BufferedReader br = ExternalFunctions.getBufferedFile(path);

        try {
            while((str = br.readLine()) != null) { // Delete all the password from the tree by the algo. here the dog buried
                //str = str.toLowerCase();
            	delete(str);
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("File text is wrong");
        }
    }
    
    public void delete(String key) {
    	BTreeNode x = this.root;
    	// renew the root if the root have one key and 2 childs with t-1 keys
    	if (x.GetN() == 1 & x.GetJumction(0).GetN() == t - 1 & x.GetJumction(1).GetN() == t - 1) {
    		BTreeNode z = new BTreeNode(t);
    		z.SetN((2 * this.t) - 1);
    		for (int i = 0; i < this.t - 1; i++) {
    			z.SetKey(i, x.GetJumction(0).GetKey(i));
    			z.SetJumction(i, x.GetJumction(0).GetJumction(i));
    			z.SetKey(i + this.t, x.GetJumction(1).GetKey(i));
    			z.SetJumction(i + this.t, x.GetJumction(1).GetJumction(i));
    		}
    		z.SetKey(this.t - 1, x.GetKey(0)); // Insert the root key to the middle of the new root
			z.SetJumction(this.t - 1, x.GetJumction(0).GetJumction(this.t - 1));
			z.SetJumction((2 * this.t) - 1, x.GetJumction(1).GetJumction(this.t - 1));
			z.SetIsLeaf(false);
			
			this.root = z;
    	}
    	
    	BTreeNode.delete(this.root, key);
        }
}