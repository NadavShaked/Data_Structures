public interface DynamicSet {
    int getSize();

    void insert(double key, int arrSize);

    void remove(FloorsArrayLink toRemove);

    FloorsArrayLink lookup(double key);

    double successor(FloorsArrayLink link);

    double predecessor(FloorsArrayLink link);

    double minimum();

    double maximum();
}
