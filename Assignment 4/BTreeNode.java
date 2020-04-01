public class BTreeNode {
	// ---------------------- fields ---------------------- 
	private int n;
	private int t;
    private  boolean isLeaf;
    private String[] keys;
    private BTreeNode[] junctions;

 // ---------------------- constructors ----------------------
    public BTreeNode(int t){
    	this.n = 0;
    	this.t = t;
        this.isLeaf = true;
        this.keys= new String[2 * t - 1];
        this.junctions = new BTreeNode[2 * t];
    }

 // ---------------------- methods ----------------------
    public int GetN() {
        return this.n;
    }
    public void SetN(int n) {
        this.n = n;
    }

    public boolean IsLeaf() {
        return this.isLeaf;
    }
    public void SetIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String GetKey(int i) {
        return keys[i];
    }
    public void SetKey(int i, String key) {
        this.keys[i] = key;
    }

    public BTreeNode GetJumction(int i) {
        return this.junctions[i];
    }
    public void SetJumction(int i, BTreeNode junction) {
        this.junctions[i] = junction;
    }
    
  //Return inOrder String of the B-Tree
    public String ToString(int depth) {
        String str = "";

        for (int i = 0; i < this.n; i++) {
            if (!isLeaf)
                str += this.junctions[i].ToString(depth + 1);
            str += this.keys[i] + "_" + depth + ",";
        }
        if (!isLeaf)
            str += this.junctions[this.n].ToString(depth + 1);
        
        return str;
    }
     
    public static void delete(BTreeNode x, String key) {
    	int index = IndexFinder(x, key);
    	if (index >= 0) {
    		KeyInJunction(x, index, key);
    	}
    	else {
    		int i = FindIndex(x, key);
       		BTreeNode p = x.GetJumction(i);
    		if (p.GetN() == p.t - 1) {
    			if (i == 0) {
    				MoveToLeftJunction(x, p, i, key);
    			}
    			else if (i == x.GetN()) {
    				MoveToRightJunction(x, p, i, key);
    			}
    			else {
    				MoveToMidJunction(x, p, i, key);
    			}
    		}
    		else {
    			delete(p , key);
    		}
    	}
    }
    
    public static void KeyInJunction(BTreeNode x, int index, String key) {
    	if (x.isLeaf) {
			Delete_Leaf(x, key);
    	}
    	else if (x.GetJumction(index).GetN() >= x.t) {
    		Case2(x, index);
    	}
    	else if (x.GetJumction(index + 1).GetN() >= x.t) {
    		Case3(x, index);
    	}
    	else {
    		Case4(x, index, key);
    	}
    }
    
    public static void MoveToLeftJunction(BTreeNode x, BTreeNode p, int i, String key) {
    	if (x.GetJumction(1).GetN() >= x.GetJumction(1).t) {
//    		3a1
    		Case5(x, p, i, key);
    	}
    	else {
//    		3b1
    		Case7(x, p, i, key);
    	}
    }
    
    public static void MoveToRightJunction(BTreeNode x, BTreeNode p, int i, String key) {
		if (x.GetJumction(i - 1).GetN() >= x.GetJumction(i - 1).t) {
//			3a2
			Case6(x, p, i, key);
			}
			else {
//			3b2
				Case8(x, p, i, key);
		}
    }
    
    public static void MoveToMidJunction(BTreeNode x, BTreeNode p, int i, String key) {
		if (x.GetJumction(i - 1).GetN() >= x.GetJumction(i - 1).t) {
//			3a2
			Case6(x, p, i, key);
			}
		else if (x.GetJumction(i + 1).GetN() >= x.GetJumction(i + 1).t) {
//			3a1
			Case5(x, p, i, key);
		}
		else {
//			3b1
			Case7(x, p, i, key);
		}
    }

    //if k is in X and X is a leaf node
    public static void Delete_Leaf(BTreeNode x, String key) {
    	int i = 0;
    	while (i < x.GetN() && !x.GetKey(i).equals(key)) {
    		i++;
    	}
    	if (i < x.GetN()) {
    		for (int j = i; j < x.GetN() - 1; j++) {
    			x.SetKey(j, x.GetKey(j + 1));
    		}
    	}
    	x.SetN(x.GetN() - 1);
    }
	
    //If node X has a sibling Y(left one) with t keys: lend one key from it (key k goes to the father node of X and Y,
    //		replaces key k' such that X and Y are its child pointers and the key k' is added to X).
    public static void Case2(BTreeNode x, int index) {
    	// save the max key from the predeccesor subtree
    	String tmp = x.GetJumction(index).GetKey(x.GetJumction(index).n - 1);
    	delete(x.GetJumction(index), tmp);
    	x.SetKey(index, tmp);
    }
    
    //If node X has a sibling Y(right one) with t keys: lend one key from it (key k goes to the father node of X and Y,
    //		replaces key k' such that X and Y are its child pointers and the key k' is added to X).
    public static void Case3(BTreeNode x, int index) {
    	// save the max key from the succesesor subtree
    	String tmp = x.GetJumction(index + 1).GetKey(0);
    	delete(x.GetJumction(index + 1), tmp);
    	x.SetKey(index, tmp);
    }
    
    //node X has both siblings with only t-1 keys: merge X and one of its siblings Y, while adding the key k from
    //the father node as a median key (X and Y are child pointers of k), into new node W, remove k and pointers newly created node W to the father node of X and Y.
    public static void Case4(BTreeNode x, int index, String key) {
    	BTreeNode y = x.GetJumction(index);
    	BTreeNode z = x.GetJumction(index + 1);
    	y.SetKey(y.t - 1, key);
    	for (int i = 0; i < x.t - 1; i++) {
    		y.SetKey(y.t + i, z.GetKey(i));
    		y.SetJumction(y.t + i, z.GetJumction(i));
    	}
    	y.SetJumction(2 * y.t - 1, z.GetJumction(z.t - 1));
    	y.SetN(2 * y.t - 1);
    	
    	delete(y, key);
    	
    	fixArray(x, index);
    	fixArrayChildern(x, index);
    	x.SetN(x.GetN() - 1);
    }
    
    //if the child node Y(the left) that precedes k in X has at least t keys
    public static void Case5(BTreeNode x, BTreeNode p, int i, String key) {
		BTreeNode z = x.GetJumction(i + 1);
		p.SetKey(p.t - 1, x.GetKey(i));
		p.SetJumction(p.t, z.GetJumction(0));
		x.SetKey(i, z.GetKey(0));
		
		for (int j = 0; j < z.GetN() - 1; j++) {
			z.SetKey(j, z.GetKey(j + 1));
			z.SetJumction(j, z.GetJumction(j + 1));
		}
		z.SetJumction(z.GetN() - 1,  z.GetJumction(z.GetN()));
		z.SetN(z.GetN() - 1);
		p.SetN(p.GetN() + 1);
		delete(p, key);
    }
    
    //if the child node Y(the right) that follows k in X has at least t keys
    public static void Case6(BTreeNode x, BTreeNode p, int i, String key) {
		BTreeNode z = x.GetJumction(i - 1);
			p.SetJumction(p.GetN() + 1, p.GetJumction(p.GetN()));
			for (int j = p.GetN() - 1; j >= 0; j--) {
				p.SetKey(j + 1, p.GetKey(j));
				p.SetJumction(j + 1, p.GetJumction(j));
		}
		p.SetKey(0, x.GetKey(i - 1));
		p.SetJumction(0, z.GetJumction(z.GetN()));
		x.SetKey(i - 1, z.GetKey(z.GetN() - 1));
		z.SetN(z.GetN() - 1);
		p.SetN(p.GetN() + 1);
			
		delete(p, key);
    }
    
    //both Y and Z have t-1 keys, takes from the left sibling
    public static void Case7(BTreeNode x, BTreeNode p, int i, String key) {
		BTreeNode z = x.GetJumction(i + 1);
		p.SetKey(p.GetN(), x.GetKey(i));
		for (int j = 0; j < z.GetN(); j++) {
			p.SetKey(p.t + j, z.GetKey(i));
			p.SetJumction(p.t + j, z.GetJumction(i));
		}
		p.SetJumction((2 * p.t) - 1, z.GetJumction(z.t - 1));
		p.SetN((2 * p.t) - 1);
		for (int j = i; j < x.GetN() - 1; j++) {
			x.SetKey(j, x.GetKey(j + 1));
			x.SetJumction(j + 1, x.GetJumction(j + 2));
		}
		x.SetN(x.GetN() - 1);
		delete(p, key);
    }
    
    //both Y and Z have t-1 keys, takes from the right sibling
    public static void Case8(BTreeNode x, BTreeNode p, int i, String key) {
			BTreeNode z = x.GetJumction(i - 1);
			z.SetKey(z.GetN(), x.GetKey(i - 1));
			
			for (int j = 0; j < p.GetN(); j++) {
				z.SetKey(z.t + j, p.GetKey(j));
				z.SetJumction(z.t + j, p.GetJumction(j));
			}
			z.SetJumction((2 * z.t) - 1, p.GetJumction(p.t - 1));
			z.SetN((2 * z.t) - 1);
			x.SetN(x.GetN() - 1);
			
			delete(z, key);
    }
    
    public static int FindIndex(BTreeNode x, String key) {
    	int i = 0;
		while (i < x.n && ExternalFunctions.StringCompare(key, x.GetKey(i)) >= 0) {
			i++;
		}
		
		return i;
    }
    
	// shift left all the keys from the index "index"
    public static void fixArray(BTreeNode x, int index) {
    	for(int i = index; i < x.n - 1; i++) {
    		x.SetKey(i, x.GetKey(i + 1));
    	}
    }
    
	// shift left all the junctions from the index "index"
    public static void fixArrayChildern (BTreeNode x, int index) {
    	for(int i = index + 1; i < x.n; i++) {
    		x.SetJumction(i, x.GetJumction(i + 1));
    	}
    }
    
    public static int IndexFinder (BTreeNode x, String key) {
    	for (int i = 0; i < x.GetN(); i++) {
    		if (x.GetKey(i).equals(key)) {
    			return i;
    		}
    	}
    	return -1;
    }
}