package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> comparator) {
        if (isEmpty()) {
            return null;
        }
        if (size() == 1) {
            return get(0);
        }
        T maxItem = get(0);
        for (int i = 1; i < size(); i++) {
            if (comparator.compare(maxItem, get(i)) < 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }

}
