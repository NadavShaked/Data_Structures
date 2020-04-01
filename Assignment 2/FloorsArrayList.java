public class FloorsArrayList implements DynamicSet {
    private FloorsArrayLink positiveInfintyLink; // declare positiveInfintyLink
    private FloorsArrayLink negativeInfintyLink; // declare negativeInfintyLink
    private int maxKey;
    private int listSize;
    private int maxArrSize;

    public FloorsArrayList(int N){
        // builder, initiate FloorsArrayList
        positiveInfintyLink = new FloorsArrayLink(Double.POSITIVE_INFINITY, N+1);
        negativeInfintyLink = new FloorsArrayLink(Double.NEGATIVE_INFINITY, N+1);
        maxKey = N;
        listSize = 0;
        maxArrSize = 0;

        for(int i = 1; i <= N+1; i++) {
            // initiate the pointers of the arrays inside positiveInfintyLink and  negativeInfintyLink
            positiveInfintyLink.setPrev(i, negativeInfintyLink);
            negativeInfintyLink.setNext(i, positiveInfintyLink);
        }
    }

    @Override
    public int getSize(){
        return listSize;
    }

    @Override
    public void insert(double key, int arrSize) {
        // function get key and arrSize and search the right place to insert it and insert it inside.
        // starts the right place to insert the link, from negativeInfintyLink in the insex of arraySize.
        FloorsArrayLink curLink = negativeInfintyLink;

        if(maxArrSize < arrSize){
            maxArrSize = arrSize;
        }
        int i = maxArrSize;

        // Search predecessor of curLink to insert FloorsArrayLink
        while (i > 0) {
            Double nextLinkKey = curLink.getNext(i).getKey();
            if(nextLinkKey.doubleValue() < key)
                curLink = curLink.getNext(i);
            else
                i--;
        }

        FloorsArrayLink newLink = new FloorsArrayLink(key, arrSize);

        //Set predecessor link to the inserted link and set successor link to the predecessor link
        FloorsArrayLink predecessorLink = curLink;
        FloorsArrayLink successorLink = curLink.getNext(1);

        int j = 1;
        while(j <= arrSize) { // iterate the array
            if(predecessorLink.getArrSize() >= j) {
                newLink.setPrev(j, predecessorLink);
                predecessorLink.setNext(j, newLink);
                j++;
            }
            else
                predecessorLink = predecessorLink.getPrev(j-1);
        }

        //Set successor link to the inserted link and set predecessor link to the successor link
        j = 1;
        while(j <= arrSize) {
            if(successorLink.getArrSize() >= j) {
                newLink.setNext(j, successorLink);
                successorLink.setPrev(j, newLink);
                j++;
            }
            else
                successorLink = successorLink.getNext(j-1);
        }
        listSize++; // increase the counter
    }

    @Override
    public void remove(FloorsArrayLink toRemove) {
        // the function should remove specific link
        int toRemoveArrSize = toRemove.getArrSize();
        for(int i = 1; i <= toRemoveArrSize; i++) { // switch the pointers between the arrays
            toRemove.getPrev(i).setNext(i, toRemove.getNext(i));
            toRemove.getNext(i).setPrev(i, toRemove.getPrev(i));
        }
        listSize--; // decrease the counter in one
    }

    @Override
    public FloorsArrayLink lookup(double key) {
        // the function looks for a specific key, starting search the key from the biggest index in negativeInfintyLink.

        FloorsArrayLink curLink = negativeInfintyLink;
        int i = maxArrSize;

        while (i > 0) { // running the index inside a specific array
            Double nextLinkKey = curLink.getNext(i).getKey();
            if(nextLinkKey.equals(key))
                return curLink.getNext(i); // if it found, return the link
            else if(nextLinkKey.doubleValue() < key)
                curLink = curLink.getNext(i);
            else
                i--; // decrease in 1
        }
        return null; // if the key didnt found, we'll return null
    }

    @Override
    public double successor(FloorsArrayLink link) {
        FloorsArrayLink successor = link.getNext(1); // return the 'next' building
        if (successor.getKey() == Double.POSITIVE_INFINITY)
            return Double.NEGATIVE_INFINITY;
        return successor.getKey();
    }

    @Override
    public double predecessor(FloorsArrayLink link) {
        FloorsArrayLink predecessor = link.getPrev(1); // return the previous building
        if (predecessor.getKey() == Double.NEGATIVE_INFINITY)
            return Double.POSITIVE_INFINITY;
        return predecessor.getKey();
    }

    @Override
    public double minimum() {
        return negativeInfintyLink.getNext(1).getKey();
    }

    @Override
    public double maximum() {
        return positiveInfintyLink.getPrev(1).getKey();
    }
}