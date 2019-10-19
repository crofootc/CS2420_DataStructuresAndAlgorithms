// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree<E extends Comparable<? super E>> {


        final String ENDLINE = "\n";
    private BinaryNode<E> root;  // Root of tree
    private BinaryNode<E> curr;  // Last node accessed in tree
    private String treeName;     // Name of tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create non ordered tree from list in preorder
     * @param arr    List of elements
     * @param label  Name of tree
     * @param height Maximum height of tree
     */
    public Tree(ArrayList<E> arr, String label, int height) {
        this.treeName = label;
        root = buildTree(arr, height, null);
    }

    /**
     * Create BST
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public Tree(ArrayList<E> arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.size(); i++) {
            bstInsert(arr.get(i));
        }
    }


    /**
     * Create BST from Array
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.length; i++) {
            bstInsert(arr[i]);
        }
    }

    /**
     * Change name of tree
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a tree with one node per line
     */


    // This is my answer for Problem 1 Order of Complexity: O (n)
    public String toString() {
        if (root == null)
            return (treeName + " Empty tree\n");
        else
            return this.treeName + "\n" + toString(this.root, 0);
    }

    public String toString(BinaryNode<E> value, int depth){
        String parent = "";
        if (value == null) return "";
        if (value.parent == null){
            parent = " [no parent]";
        } else {parent = " [" + value.parent.element + "]";}

        String returnString = "";

        returnString += toString(value.right, depth + 1);
        returnString += " ".repeat(depth) + value.element + parent + "\n";
        returnString += toString(value.left, depth + 1);
        return "" + returnString + "";
    }


    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }


    // This is my answer for problem 2 Order of Complexity: O (n)
    /**
     * reverse left and right children recursively
     */
    public void flip() {
         flip(root);
    }

    public void flip(BinaryNode<E> root){
        if (root == null){return;}
        if (root.right == null && root.left == null) {return;}

        BinaryNode<E> temp = root.left;
        root.left = root.right;
        root.right = temp;

        flip(root.right);
        flip(root.left);
    }

    // This is my Answer for Problem 3 Order of complexity: O(n)
    /**
     * Find successor of "curr" node in tree
     * @return String representation of the successor
     */
    public String successor() {
        if (curr == null) curr = root;
        curr = successor(curr);
        if (curr == null) return "null";
        else return curr.toString();
    }

    // This is the solution without recursion
    public BinaryNode successor(BinaryNode<E> curr) {
        // Check to see if right node is not null
        if (curr.right != null) {
            BinaryNode<E> leftMost = curr.right;
            while (leftMost != null && leftMost.left != null) {
                leftMost = leftMost.left;
            }
            return leftMost;
        }
        BinaryNode<E> parent = curr.parent;
        while (parent != null && curr == parent.right){
            curr = parent;
            parent = parent.parent;
        }
        return parent;

    }
    // this.compareTo(that)
    // returns a negative int if this < that
    // 0 if this == that
    // a positive int if this > that


    // This is my Answer for #4 Order of Complexity: O(n)

    /**
     * Counts number of nodes in specifed level
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */

    public int nodesInLevel(int level) {
        return nodesInLevel(root, level);
    }

    public int nodesInLevel(BinaryNode<E> root, int level){
        if (root == null){return 0;}
        if (level == 0){ return 1;}
        int right = nodesInLevel(root.right, level-1);
        int left = nodesInLevel(root.left, level-1);

        return right + left;
    }


    // This is my answer for Number 5 Order of Complexity: O(n)

    /**
     * Print all paths from root to leaves
     */
    public void printAllPaths() {
        String allPaths = printAllPaths(root, "");
        System.out.print(allPaths);
    }

    public String printAllPaths(BinaryNode<E> currNode, String currStr){
        if (currNode == null){return "";}

        currStr += currNode.element + " ";

        if (currNode.left == null && currNode.right == null){return currStr + "\n";}

        String left = printAllPaths(currNode.left, currStr);
        String right = printAllPaths(currNode.right, currStr);

        return left + right;
    }


    // This is my answer for #6 Order of complexity : O (log n)

    /**
     * Print contents of levels in zig zag order starting at maxLevel
     * @param maxLevel
     */

    public String printNodesInLevel(int maxLevel) {
        String result = printNodesInLevel(root, maxLevel);
        return result;
    }
    public String printNodesInLevel(BinaryNode<E> root, int level){
        if (root == null){return "";}
        if (level == 0){ return root.element + " ";}
        String right = printNodesInLevel(root.right, level-1);
        String left = printNodesInLevel(root.left, level-1);

        return left + right;
    }
    public String printNodesInLevelReverse(int maxLevel) {
        String result = printNodesInLevelReverse(root, maxLevel);
        return result;
    }
    public String printNodesInLevelReverse(BinaryNode<E> root, int level){
        if (root == null){return "";}
        if (level == 0){ return root.element + " ";}
        String right = printNodesInLevelReverse(root.right, level-1);
        String left = printNodesInLevelReverse(root.left, level-1);

        return right + left;
    }


    public void byLevelZigZag(int maxLevel) {
        String output = byLevelZigZag(root, maxLevel);
        System.out.print(output + " ");
    }
    public String byLevelZigZag(BinaryNode<E> currNode, int maxLevel){
        if (maxLevel == 0) {return currNode.element + "";}

        String returnString = "";
        // even right -> left
        if (maxLevel % 2 == 0){
            returnString += printNodesInLevelReverse(maxLevel);
        } else{
            returnString += printNodesInLevel(maxLevel);
        }

        String nextLevel = byLevelZigZag(currNode, maxLevel-1);

        return returnString + nextLevel;

    }


    // This is my answer for #7 Order of Complexity: O(n)
    /**
     * Counts all non-null binary search trees embedded in tree
     * @return Count of embedded binary search trees
     */
    public Integer countBST() {
        if (root == null) return 0;
        Integer bst_count = countBST(root);
        return bst_count;
    }


    public Integer countBST(BinaryNode<E> currNode){
        if (currNode == null) return 0;

        Integer bst_count = 1;
        if ((currNode.left != null && currNode.left.element.compareTo(currNode.element) > 0) ||
                (currNode.right != null && currNode.right.element.compareTo(currNode.element) < 0)) bst_count -= 1;

        bst_count += countBST(currNode.left);
        bst_count += countBST(currNode.right);

        return bst_count;
    }


    /**
     * Insert into a bst tree; duplicates are allowed
     * @param x the item to insert.
     */
    public void bstInsert(E x) {

        root = bstInsert(x, root, null);
    }

    /**
     * Determines if item is in tree
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(E item) {

        return bstContains(item, root);
    }


    // This is my answer for #8 Order of complexity: O(n)
    /**
     * Remove all paths from tree that sum to less than given value
     * @param k: minimum path sum allowed in final tree
     */

    public void pruneK(Integer k){
        root = pruneK(root, k);
    }
    public BinaryNode<E> pruneK(BinaryNode<E> currNode, Integer k){
        if (currNode == null) return null;
        Integer element = (Integer)currNode.element;
        if (element > k) return currNode;

        currNode.left = pruneK(currNode.left, k - element);
        currNode.right = pruneK(currNode.right, k - element);

        if (currNode.left == null && currNode.right == null) return null;
        return currNode;

    }

//    // This was my initial solution. It is much more complicated but I am keeping it for reference
//    public void pruneK1(Integer sum) {
//        root = pruneKrecurs1(root, root, sum);
//    }
//
//    public BinaryNode<E> pruneKrecurs1(BinaryNode<E> root, BinaryNode<E> currNode, Integer sum){
//        if (currNode != null) {
//            Integer nodeSum = greatestSum(root, currNode);
//            if (nodeSum < sum){
//                return null;
//            } else {
//                currNode.left = pruneKrecurs1(root, currNode.left, sum);
//                currNode.right = pruneKrecurs1(root, currNode.right, sum);
//            }
//        }
//        return currNode;
//    }
//
//    public Integer greatestSum(BinaryNode<E> root, BinaryNode<E> currNode){
//
//        // Breaks the string returned from printAllPaths by newlines into a list
//        String allPaths = printAllPaths(root, "");
//        String[] splitByLines = allPaths.split("\\r?\\n");
//
////        // Make sure it splits in by newline
////        for (String line : splitByLines){
////            System.out.println(line);
////        }
//
//        // Breaks each item in the splitByLines list into another list of the the items broken by space
//        // Converts it from a string type to an Integer type in the process
//        ArrayList<Integer[]> splitBySpaces = new ArrayList<Integer[]>();
//        for (int i = 0; i < splitByLines.length; i++){
//            String[] row = splitByLines[i].split("\\s+");
//            Integer[] convertedRow;
//            convertedRow = new Integer[row.length];
//            for (int j = 0; j < row.length; j++){
//                convertedRow[j] = Integer.valueOf(row[j]);
//            }
//            splitBySpaces.add(convertedRow);
//        }
//
//        // Creates a new ArrayList containing only lists with that particular node
//        Integer reference = (Integer)currNode.element;
//        ArrayList<Integer[]> splitBySpacesAdjusted = new ArrayList<>();
//
//        for (int i = 0; i < splitBySpaces.size(); i++){
//            if (checkList(splitBySpaces.get(i), reference)){
//                splitBySpacesAdjusted.add(splitBySpaces.get(i));
//            }
//        }
//
//        // creates a new array of the sums of the rows from splitBySpacesAdjusted
//        ArrayList<Integer> sumList = new ArrayList<>();
//        for (int i = 0; i <splitBySpacesAdjusted.size(); i++){
//            Integer rowSum = 0;
//            for (int j = 0; j < splitBySpacesAdjusted.get(i).length; j++){
//                rowSum += splitBySpacesAdjusted.get(i)[j];
//            }
//            sumList.add(rowSum);
//        }
//
//        // returns the greatest sum
//        Integer greatestSum = 0;
//        for (int i = 0; i < sumList.size(); i ++){
//            if (sumList.get(i) > greatestSum){
//                greatestSum = sumList.get(i);
//            }
//        }
//
////        // TEST TO SEE IF IT WORKS PROPERLY
////        System.out.println("TESTING SPACE BREAK ======");
////        System.out.println("Reference: " + reference);
////        for (int i = 0; i <splitBySpaces.size(); i++){
////            for (int j = 0; j < splitBySpaces.get(i).length; j++){
////                System.out.print(splitBySpaces.get(i)[j]+ " ");
////            }
////            System.out.println();
////        }
////
////        System.out.println("Greatest Sum:: " + greatestSum);
//        return greatestSum;
//    }
//
//
//
//    // Function to see if a certain Integer is contained in an array
//    public boolean checkList(Integer[] array, Integer checkValue){
//        boolean test = false;
//        for (Integer value : array) {
//            if ((int)value == (int)checkValue){
//                test = true;
//            }
//        }
//        return test;
//    }



    // This is my Answer for number 9. Order of Complexity: O (N Log N)
    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     */
    public void buildTreeTraversals(E[] inOrder, E[] preOrder) {
        root = null;
        root = buildTreeTraversals(inOrder, preOrder, 0, preOrder.length-1, 0, inOrder.length-1);
    }

    public BinaryNode<E> buildTreeTraversals(E[] inOrder, E[] preOrder, int preStart, int preEnd, int inStart, int inEnd){
        if (inStart > inEnd || preStart > preEnd) return null;

        E value = preOrder[preStart];
        BinaryNode currNode = new BinaryNode(value);

        // Find value in the inOrder list
        // All values before will be on left side of tree
        // All values after will be on right side of tree
        int index = 0;
        for (int i = 0; i < inOrder.length; i++){
            if (value == inOrder[i]){
                index = i;
                break;
            }
        }

        currNode.left = buildTreeTraversals(inOrder, preOrder, preStart +1, preStart+(index-inStart), inStart, index-1 );
        if (currNode.left != null) currNode.left.parent = currNode;

        currNode.right = buildTreeTraversals(inOrder, preOrder, preStart + (index-inStart)+1, preEnd, index+1, inEnd );
        if (currNode.right != null) currNode.right.parent = currNode;

        return currNode;
    }

    // This is my answer for #10 Order of Complexity: O (log N)
    /**
     * Find the least common ancestor of two nodes
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    public String lca(E a, E b) {
        BinaryNode<E> ancestor = null;
        if (a.compareTo(b) < 0) {
            ancestor = lca(root, a, b);
        } else {
            ancestor = lca(root, b, a);
        }
        if (ancestor == null) return "none";
        else return ancestor.toString();
    }

    public BinaryNode<E> lca(BinaryNode<E> currNode, E a, E b){
        if(currNode==null) return null;

        // Right
        if(currNode.element.compareTo(a) < 0 && currNode.element.compareTo(b) < 0)
            return lca(currNode.right, a, b);

        // Left
        if (currNode.element.compareTo(a) > 0 && currNode.element.compareTo(b) > 0)
            return lca(currNode.left, a, b);

        return currNode;
    }


    // This is my answer for #11. Time Complexity: O (n)
    /**
     * Balance the tree
     */
    public void balanceTree() {
        ArrayList<BinaryNode<E>> treeList = getTreeArray();
        root = balanceTree(treeList, 0, treeList.size()-1);
    }
    public BinaryNode<E> balanceTree(ArrayList<BinaryNode<E>> treeList, int startIndex, int endIndex){
        if (startIndex > endIndex) return null;
        if (startIndex == endIndex) {
            BinaryNode<E> middleNode = new BinaryNode(treeList.get(startIndex).element);
            return middleNode;
        }
        int middle = getMiddle(startIndex, endIndex);
        BinaryNode<E> newNode = new BinaryNode(treeList.get(middle).element);

        // Do it for the left side
            newNode.left = balanceTree(treeList, startIndex, middle - 1);
            if (newNode.left != null) newNode.left.parent = newNode;

        // Do it for the right side
            newNode.right = balanceTree(treeList, middle+1, endIndex);
            if (newNode.right != null) newNode.right.parent = newNode;


        return newNode;
    }

    // Helper Functions:
    // getTreeArray -- returns the values in the bst in an array (uses inorder sort)
    // getMiddle -- returns the middle of an array list (rounds down)
    //      ex) 7 --> 3
    //      ex) 6 --> 2
    public ArrayList<BinaryNode<E>> getTreeArray(){
        ArrayList<BinaryNode<E>> treeList = new ArrayList<BinaryNode<E>>();
        getTreeArray(root, treeList);
        return treeList;
    }
    public void getTreeArray(BinaryNode<E> currNode, ArrayList<BinaryNode<E>> treeList){
        if (currNode != null) {

            // Will go through the bst in an inorder sort: left-curr-right
            getTreeArray(currNode.left, treeList);
            treeList.add(currNode);
            getTreeArray(currNode.right, treeList);
        }

    }
    public int getMiddle(int startIndex, int endIndex){
        return ((endIndex - startIndex) / 2) + startIndex;
    }


    // This is my answer for #12 Order of Complexity: O (n)

    /**
     * In a BST, keep only nodes between range
     * @param a lowest value
     * @param b highest value
     */
    public void keepRange(E a, E b) {
        root = keepRange(root, a, b);
     }
     public BinaryNode<E> keepRange(BinaryNode<E> currNode, E a, E b){
        if (currNode == null) return null;

        boolean withinRange = (currNode.element.compareTo(a) >= 0 && currNode.element.compareTo(b) <= 0);

        currNode.left = keepRange(currNode.left, a, b);
        currNode.right = keepRange(currNode.right, a, b);

        if(!withinRange) currNode = deleteNode(currNode, currNode);

        return currNode;
     }

     // Helper Functions
//    public void deleteNode(BinaryNode<E> value){
//        root = deleteNode(root, value);
//    }
    public BinaryNode<E> deleteNode(BinaryNode<E> currNode, BinaryNode<E> value){
        if (currNode == null) return currNode;

        if (value.element.compareTo(currNode.element)<0){
            currNode.left = deleteNode(currNode.left, value);
        } else if (value.element.compareTo(currNode.element)>0){
            currNode.right = deleteNode(currNode.right, value);
        } else {
            if (currNode.left != null && currNode.right != null) {
                BinaryNode<E> tmp = currNode;
                BinaryNode<E> rightMin = minElement(tmp.right);
                currNode = rightMin;
                deleteNode(currNode.right, rightMin);
            } else if (currNode.left != null){
                currNode = currNode.left;
            } else if (currNode.right != null){
                currNode = currNode.right;
            } else currNode = null;
        }
        return currNode;
    }
    public BinaryNode<E> minElement(BinaryNode<E> currNode){
        if (currNode.left == null) return currNode;
        return minElement(currNode.left);
    }

    //PRIVATE

     /**
     * Build a NON BST tree by preorder
     *
     * @param arr    nodes to be added
     * @param height maximum height of tree
     * @param parent parent of subtree to be created
     * @return new tree
     */
    private BinaryNode<E> buildTree(ArrayList<E> arr, int height, BinaryNode<E> parent) {
        if (arr.isEmpty()) return null;
        BinaryNode<E> curr = new BinaryNode<>(arr.remove(0), null, null, parent);
        if (height > 0) {
            curr.left = buildTree(arr, height - 1, curr);
            curr.right = buildTree(arr, height - 1, curr);
        }
        return curr;
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<E> bstInsert(E x, BinaryNode<E> t, BinaryNode<E> parent) {
        if (t == null)
            return new BinaryNode<>(x, null, null, parent);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = bstInsert(x, t.left, t);
        } else {
            t.right = bstInsert(x, t.right, t);
        }

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean bstContains(E x, BinaryNode<E> t) {
        curr = null;
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return bstContains(x, t.left);
        else if (compareResult > 0)
            return bstContains(x, t.right);
        else {
            curr = t;
            return true;    // Match
        }
    }


    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     * @param t the node that roots the subtree.
     */
    private String toString2(BinaryNode<E> t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }

    // Basic node stored in unbalanced binary  trees
    private static class BinaryNode<AnyType> {
        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
        BinaryNode<AnyType> parent; //  Parent node

        // Constructors
        BinaryNode(AnyType theElement) {
            this(theElement, null, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt, BinaryNode<AnyType> pt) {
            element = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            if (parent == null) {
                sb.append("<>");
            } else {
                sb.append("<");
                sb.append(parent.element);
                sb.append(">");
            }
            return sb.toString();
        }
    }


    // Test program
    public static void main(String[] args) {
        long seed = 436543;
        Random generator = new Random(seed);  // Don't use a seed if you want the numbers to be different each time
        final String ENDLINE = "\n";

        int val = 60;
        final int SIZE = 8;

        Integer[] v1 = {25, 10, 60, 55, 58, 56, 14, 63, 8, 50, 6, 9};
        ArrayList<Integer> v2 = new ArrayList<Integer>();
        for (int i = 0; i < SIZE * 2; i++) {
            int t = generator.nextInt(200);
            v2.add(t);
        }
        v2.add(val);
        Integer[] v3 = {200, 15, 3, 65, 83, 70, 90};
        ArrayList<Integer> v4 = new ArrayList<Integer>(Arrays.asList(v3));
        Integer[] v = {21, 8, 5, 6, 7, 19, 10, 40, 43, 52, 12, 60};
        ArrayList<Integer> v5 = new ArrayList<Integer>(Arrays.asList(v));
        Integer[] inorder = {4, 2, 1, 7, 5, 8, 3, 6};
        Integer[] preorder = {1, 2, 4, 3, 5, 7, 8, 6};


        Tree<Integer> tree1 = new Tree<Integer>(v1, "Tree1:");
        Tree<Integer> tree2 = new Tree<Integer>(v2, "Tree2:");
        Tree<Integer> tree3 = new Tree<Integer>(v3, "Tree3:");
        Tree<Integer> treeA = new Tree<Integer>(v4, "TreeA:", 2);
        Tree<Integer> treeB = new Tree<Integer>(v5, "TreeB", 3);
        Tree<Integer> treeC = new Tree<Integer>("TreeC");
        System.out.println(tree1.toString());
        System.out.println(tree1.toString2());

        System.out.println(treeA.toString());

        treeA.flip();
        System.out.println("Now flipped" + treeA.toString());

        System.out.println(tree2.toString());
        tree2.contains(val);  //Sets the current node inside the tree6 class.
        int succCount = 5;  // how many successors do you want to see?
        System.out.println("In Tree2, starting at " + val + ENDLINE);
        for (int i = 0; i < succCount; i++) {
            System.out.println("The next successor is " + tree2.successor());
        }

        System.out.println(tree1.toString());
        for (int mylevel = 0; mylevel < SIZE; mylevel += 2) {
            System.out.println("Number nodes at level " + mylevel + " is " + tree1.nodesInLevel(mylevel));
        }
        System.out.println("All paths from tree1");
        tree1.printAllPaths();

        System.out.print("Tree1 byLevelZigZag: ");
        tree1.byLevelZigZag(5);
        System.out.print("Tree2 byLevelZigZag (3): ");
        tree2.byLevelZigZag(3);
        treeA.flip();
        System.out.println(treeA.toString());
        System.out.println("treeA Contains BST: " + treeA.countBST());

        System.out.println(treeB.toString());
        System.out.println("treeB Contains BST: " + treeB.countBST());

        treeB.pruneK(60);
        treeB.changeName("treeB after pruning 60");
        System.out.println(treeB.toString());
        treeA.pruneK(220);
        treeA.changeName("treeA after pruning 220");
        System.out.println(treeA.toString());

        treeC.buildTreeTraversals(inorder, preorder);
        treeC.changeName("Tree C built from inorder and preorder traversals");
        System.out.println(treeC.toString());

        System.out.println(tree1.toString());
        System.out.println("tree1 Least Common Ancestor of (56,61) " + tree1.lca(56, 61) + ENDLINE);

        System.out.println("tree1 Least Common Ancestor of (6,25) " + tree1.lca(6, 25) + ENDLINE);
        System.out.println(tree3.toString());
        tree3.balanceTree();
        tree3.changeName("tree3 after balancing");
        System.out.println(tree3.toString());

        System.out.println(tree1.toString());
        tree1.keepRange(10, 50);
        tree1.changeName("tree1 after keeping only nodes between 10 and 50");
        System.out.println(tree1.toString());
        tree3.keepRange(3, 85);
        tree3.changeName("tree3 after keeping only nodes between 3  and 85");
        System.out.println(tree3.toString());
    }

}
