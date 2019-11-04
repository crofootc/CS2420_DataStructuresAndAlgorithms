import java.util.NoSuchElementException;
import java.util.*;

public class SkewHeap<AnyType extends Comparable<? super AnyType>> {
    // Variables
    private SkewNode<AnyType> root;  // The root of the leftist heap
    private int size;

    // Constructor
    public SkewHeap() {
        root = null;
        size = 0;
    }


    // Tests to see if the heap is empty
    public boolean isEmpty() {
        return root == null;
    }

    // Clear the heap
    public void clear() {
        root = null;
        size = 0;
    }

    // Return the size of the heap
    public int getSize(){
        return size;
    }

    // Insert an element into the SkewHeap
    public void insert(AnyType element){
        root = merge(root, new SkewNode(element));
        size++;
    }

    // Remove an element from the heap (the root)
    public AnyType deleteMax(){
        if (isEmpty())
            throw new NoSuchElementException("No Element in the Heap");
        AnyType maxItem = root.element;
        root = merge(root.left, root.right);
        size--;
        return maxItem;
    }

    // Merge two heaps
    private SkewNode merge(SkewNode<AnyType> lh, SkewNode<AnyType> rh){
        if (lh == null) return rh;
        if (rh == null) return lh;

        if (lh.element.compareTo(rh.element) > 0){
            SkewNode temp = lh.left;
            lh.left = merge(lh.right, rh);
            lh.right = temp;
            return lh;
        } else {
            SkewNode temp = rh.right;
            rh.right = merge(rh.left, lh);
            rh.left = temp;
            return rh;
        }
    }

    // Finds the smallest item in the queue (the root is the smallest)
    public AnyType findMax() {
        if (isEmpty())
            throw new NoSuchElementException("No Element in the Heap");
        return root.element;
    }


    /** Function to display heap **/
    public void displayHeap()
    {
        System.out.print("\nHeap : ");
        displayHeap(root);
        System.out.println("\n");
    }
    private void displayHeap(SkewNode r)
    {
        if (r != null)
        {
            displayHeap(r.left);
            System.out.print(r.element +" ");
            displayHeap(r.right);
        }
    }


    //////////////////////////////
    // Inner Class: SkewNode //
    //////////////////////////////
    private static class SkewNode<AnyType> {
        // Variables
        AnyType element;    // the data
        SkewNode<AnyType> left;       // the left child
        SkewNode<AnyType> right;      // the right child

        // Constructors
        SkewNode(AnyType element) {
            this(element, null, null);
        }

        SkewNode(AnyType element, SkewNode left, SkewNode right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Skew Heap Test\n\n");

        /** Make object of SkewHeap **/
        SkewHeap sh = new SkewHeap();

        char ch;
        /**  Perform SkewHeap operations  **/
        do {
            System.out.println("\nSkewHeap Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. deleteMax ");
            System.out.println("3. size");
            System.out.println("4. check empty");
            System.out.println("5. clear");
            System.out.println("6. find max");


            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter integer element to insert");
                    sh.insert(scan.nextInt());
                    break;
                case 2:
                    sh.deleteMax();
                    break;
                case 3:
                    System.out.println("Size = " + sh.getSize());
                    break;
                case 4:
                    System.out.println("Empty status = " + sh.isEmpty());
                    break;
                case 5:
                    sh.clear();
                    System.out.println("Heap Cleared\n");
                    break;
                case 6:
                    System.out.println(sh.findMax());
                    break;

                default:
                    System.out.println("Wrong Entry \n ");
                    break;
            }
            /** Display heap **/
            sh.displayHeap();

            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');
    }
}
