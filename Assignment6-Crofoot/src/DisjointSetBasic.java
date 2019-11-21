import java.util.Scanner;

public class DisjointSetBasic {
    private int size;    // Provides the size of the disjoint set
    private int[] ds;   // The array of the disjoint set

    public DisjointSetBasic(int num) {
        size = num;
        ds = new int[num];

        // Set all values in the disjoint set as -1 (root)
        for (int i = 0; i < ds.length; i++) {
            ds[i] = -1;
        }
    }

    public int find(int x) {
        if (ds[x] < 0) return x;

        return ds[x] = find(ds[x]);
    }

    public void union(int x, int y) {
        int xR = find(x);
        int yR = find(y);
//        System.out.println("xR: " + xR);
//        System.out.println("yR: " + yR);
//        System.out.println("ds[xR]: " + ds[xR]);
//        System.out.println("ds[yR]: " + ds[yR]);
//        System.out.println();

        if (xR == yR) return;

        if (-ds[xR] > -ds[yR]) {
            ds[xR] = ds[yR] +ds[xR];
            ds[yR] = xR;
        } else {
            ds[yR] = ds[yR] +ds[xR];
            ds[xR] = yR;

        }
//        System.out.println();
//        System.out.println("xR: " + xR);
//        System.out.println("yR: " + yR);
//        System.out.println("ds[xR]: " + ds[xR]);
//        System.out.println("ds[yR]: " + ds[yR]);

    }

    public void printDs() {
        for (int i = 0; i < size; i++)
            System.out.print(ds[i] + ", ");
    }

    public static void main(String[] args) {
        DisjointSetBasic test = new DisjointSetBasic(15);
        test.printDs();
        System.out.println("\n" + test.find(2));
        test.union(1, 2);
        test.union(3, 4);
        test.union(4, 5);
        test.union(5,1);
        test.printDs();
        Scanner scan = new Scanner(System.in);
        char ch;
        DisjointSetBasic ds = new DisjointSetBasic(15);
        ds.printDs();
        /**  Perform Disjoint operations  **/
        do {
            System.out.println("\nDisjoint Operations\n");
            System.out.println("1. find ");
            System.out.println("2. union ");


            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter integer element to find");
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



