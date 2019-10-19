// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( element )       --> Insert element
// void remove( element )       --> Remove element (unimplemented)
// boolean contains( element )  --> Return true if element is present
// boolean remove( element )    --> Return true if element was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AVLTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public AVLTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param element the item to insert.
     */
    public void insert( AnyType element )
    {
        root = insert( element, root );
    }

    /**
     * Remove from the tree. Nothing is done if element is not found.
     * @param element the item to remove.
     */
    public void remove( AnyType element )
    {
        root = remove( element, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param deleteNode the item to remove.
     * @param currNode the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> remove( AnyType deleteNode, AvlNode<AnyType> currNode )
    {
        if( currNode == null )
            return currNode;   // Item not found; do nothing

        int compareResult = deleteNode.compareTo( currNode.element );

        if( compareResult < 0 )
            currNode.left = remove( deleteNode, currNode.left );
        else if( compareResult > 0 )
            currNode.right = remove( deleteNode, currNode.right );
        else if( currNode.left != null && currNode.right != null ) // Two children
        {
            currNode.element = findMin( currNode.right ).element;
            currNode.right = remove( currNode.element, currNode.right );
        }
        else
            currNode = ( currNode.left != null ) ? currNode.left : currNode.right;
        return balance( currNode );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMin( root ).element;
    }

    public  void  deleteMin( ){

        root =  deleteMin(root);
     }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param element the item to search for.
     * @return true if element is found.
     */
    public boolean contains( AnyType element )
    {
        return contains( element, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( String label)
    {
        System.out.println(label);
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root,"" );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume currNode is either balanced or within one of being balanced
    private AvlNode<AnyType> balance( AvlNode<AnyType> currNode )
    {
        if( currNode == null )
            return currNode;

        // If the tree is right Heavy
        if( height( currNode.left ) - height( currNode.right ) > ALLOWED_IMBALANCE )
            if( height( currNode.left.left ) >= height( currNode.left.right ) )
                currNode = rightRotation( currNode );
            // If the tree's right subtree is left heave
            else
                currNode = doubleRightRotation( currNode );
        else

        // If the tree is left heavy
        if( height( currNode.right ) - height( currNode.left ) > ALLOWED_IMBALANCE )
            if( height( currNode.right.right ) >= height( currNode.right.left ) )
                currNode = leftRotation( currNode );
            // If the tree's left subtree is right heavy
            else
                currNode = doubleLeftRotation( currNode );

        currNode.height = Math.max( height( currNode.left ), height( currNode.right ) ) + 1;
        return currNode;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    private int checkBalance( AvlNode<AnyType> currNode )
    {
        if( currNode == null )
            return -1;

        if( currNode != null )
        {
            int hl = checkBalance( currNode.left );
            int hr = checkBalance( currNode.right );
            if( Math.abs( height( currNode.left ) - height( currNode.right ) ) > 1 ||
                    height( currNode.left ) != hl || height( currNode.right ) != hr )
                System.out.println( "\n\n***********************OOPS!!" );
        }

        return height( currNode );
    }


    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     * @param element the item to insert.
     * @param currNode the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType element, AvlNode<AnyType> currNode )
    {
        if( currNode == null )
            return new AvlNode<>( element, null, null );

        int compareResult = element.compareTo( currNode.element );

        // element to insert is left put on left subtree else put on the right subtree
        if( compareResult < 0 )
            currNode.left = insert( element, currNode.left );
        else
            currNode.right = insert( element, currNode.right );

        return balance( currNode );
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param currNode the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin( AvlNode<AnyType> currNode )
    {
        if( currNode == null )
            return currNode;

        while( currNode.left != null )
            currNode = currNode.left;
        return currNode;
    }

    private AvlNode<AnyType> deleteMin( AvlNode<AnyType> currNode )
    {
        if (currNode == null) return null;
        if (currNode.left == null){
            if (currNode.right == null)return null;
            else return balance(currNode.right);
        }

        currNode.left = deleteMin(currNode.left);

       return balance(currNode);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param currNode the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> currNode )
    {
        if( currNode == null )
            return currNode;

        while( currNode.right != null )
            currNode = currNode.right;
        return currNode;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param element is item to search for.
     * @param currNode the node that roots the tree.
     * @return true if element is found in subtree.
     */
    private boolean contains( AnyType element, AvlNode<AnyType> currNode )
    {
        while( currNode != null )
        {
            int compareResult = element.compareTo( currNode.element );

            if( compareResult < 0 )
                currNode = currNode.left;
            else if( compareResult > 0 )
                currNode = currNode.right;
            else
                return true;    // Match
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param currNode the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> currNode, String indent )
    {
        if( currNode != null )
        {
            printTree( currNode.right, indent+"   " );
            System.out.println( indent+ currNode.element + "("+ currNode.height  +")" );
            printTree( currNode.left, indent+"   " );
        }
    }

    /**
     * Return the height of node currNode, or -1, if null.
     */
    private int height( AvlNode<AnyType> currNode )
    {   if (currNode==null) return -1;
        return currNode.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rightRotation(AvlNode<AnyType> currNode )
    {
        AvlNode<AnyType> theLeft = currNode.left;
        currNode.left = theLeft.right;
        theLeft.right = currNode;
        currNode.height = Math.max( height( currNode.left ), height( currNode.right ) ) + 1;
        theLeft.height = Math.max( height( theLeft.left ), currNode.height ) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> leftRotation(AvlNode<AnyType> currNode )
    {
        AvlNode<AnyType> theRight = currNode.right;
        currNode.right = theRight.left;
        theRight.left = currNode;
        currNode.height = Math.max( height( currNode.left ), height( currNode.right ) ) + 1;
        theRight.height = Math.max( height( theRight.right ), currNode.height ) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleRightRotation( AvlNode<AnyType> currNode )
    {
        currNode.left = leftRotation( currNode.left );
        return rightRotation( currNode );

    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleLeftRotation(AvlNode<AnyType> currNode )
    {
        currNode.right = rightRotation( currNode.right );
        return leftRotation( currNode );
    }

    private static class AvlNode<AnyType>
    {
        // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    /** The tree root. */
    private AvlNode<AnyType> root;


    // Test program
    public static void main( String [ ] args ) {
        AVLTree<Integer> t = new AVLTree<>();
        AVLTree<Dwarf> t2 = new AVLTree<>();

        String[] nameList = {"Snowflake", "Sneezy", "Doc", "Grumpy", "Bashful", "Dopey", "Happy", "Doc", "Grumpy", "Bashful", "Doc", "Grumpy", "Bashful"};
        for (int i=0; i < nameList.length; i++)
            t2.insert(new Dwarf(nameList[i]));

        t2.printTree( "The Tree" );

        t2.remove(new Dwarf("Bashful"));

        t2.printTree( "The Tree after delete Bashful" );
        for (int i=0; i < 8; i++) {
            t2.deleteMin();
            t2.printTree( "\n\n The Tree after deleteMin" );
        }
    }

}
