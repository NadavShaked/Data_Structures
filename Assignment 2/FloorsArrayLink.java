public class FloorsArrayLink {
    //@TODO: add fields
    Double key;
    FloorsArrayLink [] nextArray;
    FloorsArrayLink [] prevArray;
    int arrSize;

    public FloorsArrayLink(double key, int arrSize){
        this.key = key;
        nextArray = new FloorsArrayLink[arrSize];
        prevArray = new FloorsArrayLink[arrSize];
        this.arrSize = arrSize;
    }

    public double getKey() {
        return key;
    }

    public FloorsArrayLink getNext(int i) {
        FloorsArrayLink output;
        if(i>0 & i <= arrSize){
            output = nextArray[i-1];
        }
        else{
            output = null;
        }
        return output;
    }

    public FloorsArrayLink getPrev(int i) {
        FloorsArrayLink output;
        if(i>0 & i <= arrSize){
            output = prevArray[i-1];
        }
        else{
            output = null;
        }
        return output;
    }

    public void setNext(int i, FloorsArrayLink next) {
        if(i>0 & i <= arrSize){
            nextArray[i-1] = next;
        }
    }

    public void setPrev(int i, FloorsArrayLink prev) {
        if(i > 0 & i <= arrSize){
            prevArray[i-1] = prev;
        }    }

    public int getArrSize(){
        return arrSize;
    }
}

