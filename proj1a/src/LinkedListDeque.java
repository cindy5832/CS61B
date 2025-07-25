import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {

    private class Node<E> {

        private Node<E> next;
        private T data;
        private Node<E> prev;

        Node(Node<E> prev, T data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> sentinel = new Node<>(null, null, null);
    private int size;

    public LinkedListDeque() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }


    @Override
    public void addFirst(T x) {
        Node<T> a = sentinel;
        Node<T> b = sentinel.next;
        Node<T> addNode = new Node<>(a, x, b);
        a.next = addNode;
        b.prev = addNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node<T> a = sentinel;
        Node<T> b = sentinel.prev;
        Node<T> addNode = new Node<>(b, x, a);
        b.next = addNode;
        a.prev = addNode;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        Node<T> p = sentinel.next;
        while (p != sentinel) {
            result.add(p.data);
            p = p.next;
        }
        return result;
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
        Node<T> a = sentinel;
        Node<T> removed = sentinel.next;
        Node<T> b = removed.next;
        a.next = b;
        b.prev = a;
        size--;
        return removed.data;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<T> a = sentinel;
        Node<T> removed = sentinel.prev;
        Node<T> b = removed.prev;
        b.next = a;
        a.prev = b;
        size--;
        return removed.data;
    }

    @Override
    public T get(int index) {
        Node<T> a = sentinel.next;
        if (index >= size) {
            return null;
        }
        for (int i = 0; i < index; i++) {
            a = a.next;
        }
        return a.data;
    }

    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursive(0, index, sentinel.next);
    }

    private T getRecursive(int temp, int index, Node<T> x) {
        if (temp == index) {
            return x.data;
        }
        return getRecursive(temp + 1, index, x.next);
    }
}
