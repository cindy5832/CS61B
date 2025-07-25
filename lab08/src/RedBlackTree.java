public class RedBlackTree<T extends Comparable<T>> {

    /**
     * Left-Leaning Red-Black Trees (LLRB Trees)
     * 1. the root node must be colored black
     * 2. If a node has one red child, it must be on the left.
     * 3. No node can have two red children.
     * 4. No red node can have a red parent (every red node’s parent is black)
     * 5. In a balanced LLRB tree, every path to a null reference goes through the same number of black nodes.
     **/

    /* Root of the tree. */
    RBTreeNode<T> root;
    private final boolean BLACK = true;
    private final boolean RED = false;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given 2-3 TREE. */
    public RedBlackTree(TwoThreeTree<T> tree) {
        Node<T> ttTreeRoot = tree.root;
        root = buildRedBlackTree(ttTreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }

        if (r.getItemCount() == 1) {
            RBTreeNode<T> left = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode<T> right = buildRedBlackTree(r.getChildAt(1));
            return new RBTreeNode<T>(true, r.getItemAt(0), left, right);
        } else {
            RBTreeNode<T> subRoot = new RBTreeNode<T>(true, r.getItemAt(1));
            subRoot.left = new RBTreeNode<T>(false, r.getItemAt(0));
            subRoot.left.left = buildRedBlackTree(r.getChildAt(0));
            subRoot.left.right = buildRedBlackTree(r.getChildAt(1));
            subRoot.right = buildRedBlackTree(r.getChildAt(2));
            return subRoot;
        }
    }

    /* Flips the color of node and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = BLACK;
        if (node.left != null) {
            node.left.isBlack = RED;
        }
        if (node.right != null) {
            node.right.isBlack = BLACK;
        }
    }

    /* Rotates the given node to the right. Returns the new root node of
       this subtree. For this implementation, make sure to swap the colors
       of the new root and the old root!*/
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> x = node.left;
        node.left = x.right;
        x.right = node;
        x.isBlack = node.isBlack;
        node.isBlack = RED;
        return x;
    }

    /* Rotates the given node to the left. Returns the new root node of
       this subtree. For this implementation, make sure to swap the colors
       of the new root and the old root! */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> x = node.right;
        node.right = x.left;
        x.left = node;
        x.isBlack = node.isBlack;
        node.isBlack = RED;
        return x;
    }

    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /* Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class! */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }

        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }
        if (isRed(node.right)) {
            rotateLeft(node);
        } else if (isRed(node.left) && isRed(node.left.left)) {
            rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    /* Returns whether the given node is red. Null nodes (children of leaf
       nodes) are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

}
