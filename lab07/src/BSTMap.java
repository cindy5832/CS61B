import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {

    }

    @Override
    public void put(K key, V value) {
        Node p = root;
        Node parent = null;
        while (p != null) {
            parent = p;
            if (key.compareTo(p.key) < 0) {
                p = p.left;
            } else if (key.compareTo(p.key) > 0) {
                p = p.right;
            } else {
                p.value = value;
                return;
            }
        }
        if (parent == null) {
            root = new Node(key, value);
            size++;
            return;
        }
        if (key.compareTo(parent.key) < 0) {
            parent.left = new Node(key, value);
        } else {
            parent.right = new Node(key, value);
        }
        size++;
    }

    @Override
    public V get(K key) {
        Node p = root;
        while (p != null) {
            if (key.compareTo(p.key) < 0) {
                p = p.left;
            } else if (key.compareTo(p.key) > 0) {
                p = p.right;
            } else {
                return p.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        Node p = root;
        while (p != null) {
            if (key.compareTo(p.key) < 0) {
                p = p.left;
            } else if (key.compareTo(p.key) > 0) {
                p = p.right;
            } else {
                return true;
            }
        }
        return false;
    }

    // Returns the number of key-value mappings.
    @Override
    public int size() {
        return size;
    }

    // Removes every mapping from this map.
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        return getKeySet(root, set);
    }

    private Set<K> getKeySet(Node n, Set<K> set) {
        if (n == null) return set;
        set = getKeySet(n.left, set);
        set.add(n.key);
        set = getKeySet(n.right, set);
        return set;
    }

    @Override
    public V remove(K key) {
        Node p = root;
        Node parent = null;
        while (p != null) {
            if (key.compareTo(p.key) < 0) {
                p = p.left;
            } else if (key.compareTo(p.key) > 0) {
                p = p.right;
            } else {
                break;
            }
            parent = p;
        }
        if (p == null) return null;
        if (p.left == null) { // no left child
            shift(parent, p, p.right);
        } else if (p.right == null) { // no right child
            shift(parent, p, p.left);
        } else { // have two children
            Node s = p.left;
            Node sParent = p;
            while (s.right != null) {
                sParent = s;
                s = s.right;
            }
            if (sParent != p) {
                shift(sParent, s, s.left);
                s.left = p.left;
            }
            shift(parent, p, s);
            s.right = p.right;
        }
        size--;
        return p.value;
    }

    private void shift(Node parent, Node removed, Node child) {
        if (parent == null) {
            root = child;
        } else if (removed == parent.right) {
            parent.right = child;
        } else if (removed == parent.left) {
            parent.left = child;
        }
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Not supported");
    }

    public void printInOrder() {
        printPreOrder(root);
    }

    private void printPreOrder(Node n) {
        if (n == null) return;
        printPreOrder(n.left);
        System.out.println(n.key + " -> " + n.value);
        printPreOrder(n.right);
    }
}
