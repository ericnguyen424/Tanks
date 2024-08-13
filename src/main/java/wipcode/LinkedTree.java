package wipcode;
/*
 * LinkedTree.java
 *
 * Computer Science 112
 *
 * Modifications and additions by:
 *     name:eric nguyen
 *     username:ericnguy@Bu.edu
 */

import java.lang.reflect.Array;
import java.util.*;

/*
 * LinkedTree - a class that represents a binary tree containing data
 * items with integer keys.  If the nodes are inserted using the
 * insert method, the result will be a binary search tree.
 */
public class LinkedTree {
    // An inner class for the nodes in the tree
    private class Node {
        private int key;         // the key field
        private TerrainBlock value; // the data value for this key
        private Node left;       // reference to the left child/subtree
        private Node right;      // reference to the right child/subtree
        private Node parent;     // reference to the parent. NOT YET MAINTAINED!

        private Node(int key, TerrainBlock value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    // the root of the tree as a whole
    private Node root;

    public LinkedTree() {
        root = null;
    }

    /*
     * Prints the keys of the tree in the order given by a preorder traversal.
     * Invokes the recursive preorderPrintTree method to do the work.
     */
    public void preorderPrint() {
        if (root != null) {
            preorderPrintTree(root);
        }
        System.out.println();
    }

    /*
     * Recursively performs a preorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the
     * entire tree.
     */
    private static void preorderPrintTree(Node root) {
        System.out.print(root.key + " ");
        if (root.left != null) {
            preorderPrintTree(root.left);
        }
        if (root.right != null) {
            preorderPrintTree(root.right);
        }
    }

    /*
     * Prints the keys of the tree in the order given by a postorder traversal.
     * Invokes the recursive postorderPrintTree method to do the work.
     */
    public void postorderPrint() {
        if (root != null) {
            postorderPrintTree(root);
        }
        System.out.println();
    }

    /*
     * Recursively performs a postorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the
     * entire tree.
     */
    private static void postorderPrintTree(Node root) {
        if (root.left != null) {
            postorderPrintTree(root.left);
        }
        if (root.right != null) {
            postorderPrintTree(root.right);
        }
        System.out.print(root.key + " ");
    }

    /*
     * Prints the keys of the tree in the order given by an inorder traversal.
     * Invokes the recursive inorderPrintTree method to do the work.
     */
    public void inorderPrint() {
        if (root != null) {
            inorderPrintTree(root);
        }
        System.out.println();
    }

    /*
     * Recursively performs an inorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the
     * entire tree.
     */
    private static void inorderPrintTree(Node root) {
        if (root.left != null) {
            inorderPrintTree(root.left);
        }
        System.out.print(root.key + " ");
        if (root.right != null) {
            inorderPrintTree(root.right);
        }
    }

    /*
     * Inner class for temporarily associating a node's depth
     * with the node, so that levelOrderPrint can print the levels
     * of the tree on separate lines.
     */
    private class NodePlusDepth {
        private Node node;
        private int depth;

        private NodePlusDepth(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    /*
     * Prints the keys of the tree in the order given by a
     * level-order traversal.
     */
//    public void levelOrderPrint() {
//        LLQueue<NodePlusDepth> q = new LLQueue<NodePlusDepth>();
//
//        // Insert the root into the queue if the root is not null.
//        if (root != null) {
//            q.insert(new NodePlusDepth(root, 0));
//        }
//
//        // We continue until the queue is empty.  At each step,
//        // we remove an element from the queue, print its value,
//        // and insert its children (if any) into the queue.
//        // We also keep track of the current level, and add a newline
//        // whenever we advance to a new level.
//        int level = 0;
//        while (!q.isEmpty()) {
//            NodePlusDepth item = q.remove();
//
//            if (item.depth > level) {
//                System.out.println();
//                level++;
//            }
//            System.out.print(item.node.key + " ");
//
//            if (item.node.left != null) {
//                q.insert(new NodePlusDepth(item.node.left, item.depth + 1));
//            }
//            if (item.node.right != null) {
//                q.insert(new NodePlusDepth(item.node.right, item.depth + 1));
//            }
//        }
//        System.out.println();
//    }

    /**
     * Searches for the key in the tree
     * Returns height if found, otherwise will return null
     *
     * @param key
     * @return
     */
    public TerrainBlock search(int key) {
        if (key < 0 || key > 1000) {
            throw new IllegalArgumentException("Key out of range!");
        }

        key = key - (key % 100);

        Node n = searchTree(root, key);
        if (n == null) {
            return null;
        } else {
            return n.value;
        }
    }

    /*
     * Recursively searches for the specified key in the tree/subtree
     * whose root is specified. Note that the parameter is *not*
     * necessarily the root of the entire tree.
     */
    private static Node searchTree(Node root, int key) {
        if (root == null) {
            return null;
        } else if (key == root.key) {
            return root;
        } else if (key < root.key) {
            return searchTree(root.left, key);
        } else {
            return searchTree(root.right, key);
        }
    }

    /**
     * Returns an ArrayList containing the 3 nearest TerrainBlocks. [nearestLeft, nearestMiddle, nearestRight]
     * @param key
     * @return
     */
    public TerrainBlock[] findNearest(int key, GUI gui) {
        if (gui == null) {
            throw new IllegalArgumentException("GUI is null!");
        }
        TerrainBlock[] returnList = new TerrainBlock[3];
        key = key - (key % 100);

        //add the middle element to the list
        Node n = searchTree(root, key);
        if (n == null) {
            returnList[1] = null;

        } else {
            returnList[1] = n.value;
        }

        if (key == 0) {
            returnList[0] = null;
        } else {
            returnList[0] = search(key - 1);
        }

        if (key + 100 >= gui.getWidth()) {
            returnList[2] = null;
        } else {
            returnList[2] = search(key + 100);
        }

        return returnList;

    }

    /**
     * The key is the x coordinate, the height is the value of the key
     *
     * @param key,    must be between 0 and 900
     * @param value,  terrain block
     */
    public void insert(int key, TerrainBlock value) {
        //if the key is not between 0 and 900, or if the height isn't between 100 and 600
        //and if the key isn't a multiple of 100, then invalid input
        if (key < 0 || key > 900 || key % 100 != 0
                || value.getHeight() > 600 || value.getHeight() < 100) {
            throw new IllegalArgumentException();
        }
        // Find the parent of the new node.
        Node parent = null;
        Node trav = root;
        while (trav != null) {
            if (trav.key == key) {
                System.out.println("Error! Item already in list");
                return;
            }
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }

        // Insert the new node.
        Node newNode = new Node(key, value);
        if (parent == null) {    // the tree was empty
            root = newNode;
        } else if (key < parent.key) {
            parent.left = newNode;

        } else {
            parent.right = newNode;
        }
        newNode.parent = parent;
        root.parent = null;
    }

//    /*
//     * FOR TESTING: Processes the integer keys in the specified array from
//     * left to right, adding a node for each of them to the tree.
//     * The data associated with each key is a string based on the key.
//     */
//    public void insertKeys(int[] keys) {
//        for (int i = 0; i < keys.length; i++) {
//            insert(keys[i], "data for key " + keys[i]);
//        }
//    }

    /**
     * Deletes the associated key from the list and returns the data value
     * Returns -999 if not found
     *
     * @param key
     * @return
     */
    public TerrainBlock delete(int key) {
        // Find the node to be deleted and its parent.
        Node parent = null;
        Node trav = root;
        while (trav != null && trav.key != key) {
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }

        // Delete the node (if any) and return the removed data item.
        if (trav == null) {   // no such key    
            return null;
        } else {
            TerrainBlock removedData = trav.value;
            deleteNode(trav, parent);
            return removedData;
        }
    }

    /*
     * Deletes the node specified by the parameter toDelete.  parent
     * specifies the parent of the node to be deleted.
     */
    private void deleteNode(Node toDelete, Node parent) {
        if (toDelete.left != null && toDelete.right != null) {
            // Case 3: toDelete has two children.
            // Find a replacement for the item we're deleting -- as well as 
            // the replacement's parent.
            // We use the smallest item in toDelete's right subtree as
            // the replacement.
            Node replaceParent = toDelete;
            Node replace = toDelete.right;
            while (replace.left != null) {
                replaceParent = replace;
                replace = replace.left;
            }

            // Replace toDelete's key and data with those of the 
            // replacement item.
            toDelete.key = replace.key;
            toDelete.value = replace.value;

            // Recursively delete the replacement item's old node.
            // It has at most one child, so we don't have to
            // worry about infinite recursion.
            deleteNode(replace, replaceParent);
        } else {
            // Cases 1 and 2: toDelete has 0 or 1 child
            Node toDeleteChild;
            if (toDelete.left != null) {
                toDeleteChild = toDelete.left;
            } else {
                toDeleteChild = toDelete.right;  // null if it has no children
            }

            if (toDelete == root) {
                root = toDeleteChild;
            } else if (toDelete.key < parent.key) {
                parent.left = toDeleteChild;
                toDeleteChild.parent = parent;
            } else {
                parent.right = toDeleteChild;
                toDeleteChild.parent = parent;
            }
        }
    }

    /*
     * PS 8: Problem 1
     * public "wrapper" method that makes the initial call
     * to the recursive method for testing if there are any
     * keys smaller than v in the tree.
     */
    public boolean anySmaller(int v) {
        // make the first call to the recursive method,
        // passing in the root of the tree as a whole
        return anySmallerInTree(root, v);
    }

    /*
     * PS 8: Problem 1
     * Initial recursive method for testing if there are any
     * keys smaller than v in the tree.
     *
     * In problem 1.3, you will revise this to take advantage
     * of the fact that the tree is a binary search tree.
     */
    private static boolean anySmallerInTree(Node root, int v) {
        if (root == null) {
            return false;
        } else {
            boolean anySmallerInLeft = anySmallerInTree(root.left, v);
            boolean anySmallerInRight = anySmallerInTree(root.right, v);
            return (root.key < v || anySmallerInLeft || anySmallerInRight);
        }
    }



//    /* Returns a preorder iterator for this tree. */
//    public LinkedTreeIterator preorderIterator() {
//        return new PreorderIterator();
//    }
//
//    /**
//     * Returns a postorder iterator for this tree
//     * @return
//     */
//    public LinkedTreeIterator postorderIterator() {
//        return new PostorderIterator();
//    }

//    /*
//     * inner class for a preorder iterator
//     * IMPORTANT: You will not be able to actually use objects from this class
//     * to perform a preorder iteration until you have modified the LinkedTree
//     * methods so that they maintain the parent fields in the Nodes.
//     */
//    private class PreorderIterator implements LinkedTreeIterator {
//        private Node nextNode;
//
//        private PreorderIterator() {
//            // The traversal starts with the root node.
//            nextNode = root;
//        }
//
//        public boolean hasNext() {
//            return (nextNode != null);
//        }
//
//        public int next() {
//            if (nextNode == null) {
//                throw new NoSuchElementException();
//            }
//
//            // Store a copy of the key to be returned.
//            int key = nextNode.key;
//
//            // Advance nextNode.
//            if (nextNode.left != null) {
//                nextNode = nextNode.left;
//            } else if (nextNode.right != null) {
//                nextNode = nextNode.right;
//            } else {
//                // We've just visited a leaf node.
//                // Go back up the tree until we find a node
//                // with a right child that we haven't seen yet.
//                Node parent = nextNode.parent;
//                Node child = nextNode;
//                while (parent != null &&
//                        (parent.right == child || parent.right == null)) {
//                    child = parent;
//                    parent = parent.parent;
//                }
//
//                if (parent == null) {
//                    nextNode = null;  // the traversal is complete
//                } else {
//                    nextNode = parent.right;
//                }
//            }
//            return key;
//        }
//    }
//    public Node lowestElement(Node root) {
//        if (root == null) {
//            throw new NullPointerException();
//        }
//        while (true) {
//            //if there is a left child, go left
//            if (root.left != null) {
//                root = root.left;
//            } else if (root.right != null) {
//                root = root.right;
//            } else { //if this is the bottom most leaf node on the left side, then break
//                return root;
//            }
//        }
//    }
//
//    /**
//     * class within class
//     */
//    private class PostorderIterator implements LinkedTreeIterator {
//        private Node nextNode;
//
//        /**
//         * Constructor
//         */
//        public PostorderIterator() {
//            nextNode = root;
//            nextNode = lowestElement(nextNode);
//        }
//
//
//    @Override
//    public boolean hasNext() {
//        return (nextNode != null);
//    }
//
//    @Override
//    public int next() {
//        if (nextNode == null) {
//            throw new NoSuchElementException();
//        }
//        int key = nextNode.key;
//
//        if (this.nextNode.parent == null) {
//            key = nextNode.key;
//            nextNode = null;
//            return key;
//
//        }
//        //if it is a left child and it has children and it isn't one before the root, then go up the tree
//        if (nextNode.parent.left == nextNode && (nextNode.left != null && nextNode.right != null)
//            && nextNode.parent.parent != null) {
//            nextNode = nextNode.parent;
//        }
//        // else if it is a left child and it has no children or the parent has no parents (root),
//        else if (nextNode.parent.left == nextNode &&
//            ((nextNode.left == null && nextNode.right == null) || nextNode.parent.parent == null)) {
//            // then see if the parent has right children, if yes, then call lowestElement
//            if (nextNode.parent.right != null) {
//                nextNode = lowestElement(nextNode.parent.right);
//            }
//            //if it has no children, then set it to its parent
//            else {
//                nextNode = nextNode.parent;
//            }
//        } else {//else if it is a right child, then go up the tree
//            nextNode = nextNode.parent;
//        }
//
//        return key;
//    }
        public static void main(String[] args) {
        }
    }
