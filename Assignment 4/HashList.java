public class HashList{
	// ---------------------- fields ---------------------- 
    private HashListElement first;

	// ---------------------- constructors ----------------------
    public HashList(){
        this.first = null;
    }

	// ---------------------- methods ----------------------
    public boolean isEmpty(){
        return (this.first == null);
    }

    /**
     * @param element
     * Adds element to the beginning of this list
     */
    public void  addFirstElemment(String element){
        // the element will be the first in the link
        if(element == null || !(element instanceof String))
            throw new IllegalArgumentException("Must be string varible");
        this.first = new HashListElement(element, this.first);
    }

    /**
     * @return the first element of the list
     */
    public HashListElement getFirst() {
        return this.first;
    }
}