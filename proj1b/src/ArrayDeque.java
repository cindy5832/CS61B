import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;
    private int capacity;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        this.capacity = items.length;
        nextFirst = capacity >> 1;
        nextLast = nextFirst + 1;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        if (isFull()) {
            resize(capacity << 1);
        }
        items[nextFirst] = x;
        nextFirst = (nextFirst - 1) & (capacity - 1);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (isFull()) {
            resize(capacity << 1);
        }
        items[nextLast] = x;
        nextLast = (nextLast + 1) & (capacity - 1);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<T>();
        int end = (nextLast - 1) & (capacity - 1);
        for (int i = (nextFirst + 1) % capacity; i != end; i = (i + 1) % capacity) {
            list.add(items[i]);
        }
        list.add(items[end]);
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = (nextFirst + 1) & (capacity - 1);
        T removed = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if (capacity >= 16 && size < (capacity >> 2)) {
            resize(capacity >> 1);
        }
        return removed;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = (nextLast - 1) & (capacity - 1);
        T removed = items[nextLast];
        items[nextLast] = null;
        size--;
        if (capacity >= 16 && size < (capacity >> 2)) {
            resize(capacity >> 1);
        }
        return removed;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[(nextFirst + index + 1) % capacity];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private boolean isFull() {
        return size == capacity;
    }

    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        items = newArray;
        this.capacity = capacity;
        nextFirst = capacity - 1;
        nextLast = size;
    }
}
