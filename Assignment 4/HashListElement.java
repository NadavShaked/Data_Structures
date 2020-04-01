public class HashListElement {
	// ---------------------- fields ---------------------- 
    private String password;
    private HashListElement next;

	// ---------------------- constructors ----------------------
    public HashListElement(String password, HashListElement next) {
        this.password = password;
        this.next = next;
    }

	// ---------------------- methods ----------------------
    public HashListElement(String password) {
        this(password, null);
    }

    public String getPassword(){
        return this.password;
    }

    public HashListElement getNext(){
        return this.next;
    }

}