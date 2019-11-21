import java.util.Scanner;

public class DisjointSet {
    private int size;       // Provides the size of the disjoint set
    private Node[] ds;      // The array of the disjoint set

    public DisjointSet(int num){
        size = num;
        ds = new Node[num];

        // Set all values in the ds as -1 (root)
        for (int i = 0; i < ds.length; i++){
            ds[i] = new Node();
        }
    }

    public int find(int x){
        if (ds[x].check_ref() < 0) return x;

        ds[x].set_ref(find(ds[x].check_ref()));
        return ds[x].check_ref();
    }

    public void place_tile(int x, int color){
        // 0 = Neutral, 1 = Blue, 2 = Red

        // Check to see if someone is already there
        if (ds[x-1].check_status() != 0) {
            throw new IllegalArgumentException("A player has already played there.");
        }

        // Make the move
        if (color == 1) ds[x-1].play_blue();
        else if (color == 2) ds[x-1].play_red();
        else throw new IllegalArgumentException("Illegal color played");

        if (x > 121) return;

        // Merge the current tile with any neighbors
        int[] neighbors = getNeighbors(x);

//        // Checks to see if merge to outside
//        if (color == 2 && x < 12) union(x-1, 122-1);      // TOP
//        if (color == 2 && x > 110) union(x-1, 123-1);     // BOTTOM
//        if (color == 1 && x % 11 == 1) union(x-1, 124-1); // LEFT
//        if (color == 1 && x % 11 == 0) union(x-1, 125-1); // RIGHT

        for (int i = 0; i < neighbors.length; i++){
            int amount = neighbors[i];
            if (ds[amount + x-1].check_status() == color){
                union(neighbors[i]+x-1, x-1);
            }
        }
    }

    private int[] getNeighbors(int item){
        // Returns the distance (increment) from neighbors
        if (item > 121){
            return null;
        } else if (item == 1) {
            // Top Left Corner
            int[] increment = {1, 11};
            return increment;
        } else if (item == 11){
            // Top Right Corner
            int[] increment = {-1, 10, 11};
            return increment;
        } else if (item == 111){
            // Bottom Left Corner
            int[] increment = {-11, -10, 1};
            return increment;
        } else if (item == 121){
            // Bottom Right Corner
            int[] increment = {-1, -11};
            return increment;
        } else if (item < 12){
            // Top
            int[] increment = {-1, 1, 10, 11};
            return increment;
        } else if (item % 11 == 1){
            // Left
            int[] increment = {-11, -10, 1, 11};
            return increment;
        } else if (item % 11 == 0){
            // Right
            int[] increment = {-11, -1, 10, 11};
            return increment;
        } else if (item > 110){
            // Bottom
            int[] increment = {-1, -11, -10, 1};
            return increment;
        } else {
            // In the middle
            int[] increment = {-11,-10, -1, 1, 10, 11};
            return increment;
        }
    }


    public void union(int x, int y){
        int xR = find(x);
        int yR = find(y);

        if (xR == yR) return;

        if (-ds[xR].check_ref() > -ds[yR].check_ref()) {
            ds[xR].set_ref((ds[yR].check_ref() + ds[xR].check_ref()));
            ds[yR].set_ref(xR);
        } else {
            ds[yR].set_ref((ds[yR].check_ref() + ds[xR].check_ref()));
            ds[xR].set_ref(yR);
        }
    }

    public void printDs(){
        for (int i = 0; i < size; i++)
            System.out.print(ds[i].check_ref() + ", ");
        System.out.println();
    }

    public void reset(){
        for (int i = 0; i < size; i++)
            ds[i] = new Node();
    }

    private class Node {
        private int ref;        // Negative is root + size, otherwise its reference to parent
        private int move;       // 0 = Neutral, 1 = Blue, 2 = Red

        public Node() {
            ref = -1;
            move = 0;
        }

        public void play_blue(){
            move = 1;
        }
        public void play_red(){
            move =  2;
        }

        public int check_status(){
            return move;
        }

        public int check_ref(){
            return ref;
        }
        public void set_ref(int new_ref){
            ref = new_ref;
        }
    }



    public static void main(String[] args) {
        System.out.println("----TEST 1----");
        DisjointSet test = new DisjointSet(15);
        test.printDs();
        System.out.println("\nFind(2): " + test.find(2));

        System.out.println("union (1, 2)");
        test.union(1, 2);
        test.printDs();

        System.out.println("union (3, 4)");
        test.union(3, 4);
        test.printDs();

        System.out.println("union (5, 6)");
        test.union(5, 6);
        test.printDs();

        System.out.println("union (7, 8)");
        test.union(7,8);
        test.printDs();

        System.out.println("union (5, 7)");
        test.union(5,7);
        test.printDs();

        System.out.println("find(5): " + test.find(5));
        test.printDs();

        System.out.println("union (2, 6)");
        test.union(2,6);
        test.printDs();

        System.out.println("union (1, 8)");
        test.union(1,8);
        test.printDs();


        System.out.println();
        System.out.println("---TEST 2---");
        Scanner scan = new Scanner(System.in);
        char ch;
        DisjointSet ds = new DisjointSet(15);
        ds.printDs();

        System.out.println("DISJOINT SET TEST 2");
        System.out.println("Provide your own inputs");
        /**  Perform Disjoint operations  **/
        do {
            System.out.println("\nDisjoint Operations\n");
            System.out.println("1. find ");
            System.out.println("2. union ");


            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter integer element to Find");
                    ds.find(scan.nextInt());
                    break;
                case 2:
                    System.out.println("Enter x");
                    int x = scan.nextInt();
                    System.out.println("Enter y");
                    int y = scan.nextInt();
                    ds.union(x, y);
                    break;
                default:
                    System.out.println("Wrong Entry \n ");
                    break;
            }
            /** Display heap **/
            ds.printDs();
            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');
    }
}

