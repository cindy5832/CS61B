package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author cindy
 */
public class MyHashMap<K, V> implements Map61B<K, V> {


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int capacity;
    private double loadFactor;
    private int size = 0;

    /**
     * Constructors
     */
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor      maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    private void resizeBuckets() {
        capacity *= 2;
        Collection<Node>[] newBuckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            newBuckets[i] = createBucket();
        }
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                int newIndex = getIndex(node.key);
                newBuckets[newIndex].add(node);
            }
        }
        buckets = newBuckets;
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    private int getIndex(K key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    @Override
    public void put(K key, V value) {
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        buckets[index].add(new Node(key, value));
        size++;
        if (loadFactor <= (double) size / capacity) {
            resizeBuckets();
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (Collection<Node> bucket : buckets) {
            bucket.clear();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                keySet.add(node.key);
            }
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                V value = node.value;
                bucket.remove(node);
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
