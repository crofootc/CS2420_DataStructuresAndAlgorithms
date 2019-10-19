import java.io.*;

/**
 * The main begins by reading in
 * all of the puzzles described in a file named jams.txt.
 * It then proceeds to run a brute force solution., In each case, it prints out the solution
 * path that was computed.
 */

public class TheMain {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // read all the puzzles in file.  Only the first few are solvable without additional strategies
        Puzzle[] puzzles = Puzzle.readPuzzlesFromFile("jamsAll.txt");
        //int num_puzzles = puzzles.length;
        int num_puzzles = 40;

        boolean doPrint = true;
        // 1) Show the output from both methods for the first six puzzles in jamAll.txt
        System.out.println("1: Show the output from both methods for the first six puzzles in jamAll.txt");
        System.out.println("=====SOLUTION USING LINKED LIST========");
        for (int i = 0; i < 6; i++){
            puzzles[i].solve(doPrint);
        }

        System.out.println("\n\n\n=====SOLUTION USING AVL + A*SEARCH ALGORITHM========");
        for (int i = 0; i < 6; i++){
            puzzles[i].aStarSolve(doPrint);
        }


        // 2) Highlight an example for which you method saves significant time over the brute force method
        System.out.println("2: Highlight an example for which you method saves significant time over the brute force method");
        System.out.println("\n\n\nExample of AVL +A*Search algorith saving significant time");
        System.out.println("PUZZLE 1 adds 751 states to the queue using the linked list");
        System.out.println("It only adds 116 states to the queue using my more intelligent algorithm");
        System.out.println("See below:\n");

        System.out.println("Linked List:\n");
        puzzles[1].solve(doPrint);
        System.out.println("AVL + A*Search\n");
        puzzles[1].aStarSolve(doPrint);

        // 3) Use the "more intelligent" method to solve all the problems. Show the history, final board, and number of
        //    states put on the queue
        System.out.println("\n\n\n3: Use the \"more intelligent\" method to solve all the problems. Show the history, " +
                "final board, and number of states put on the queue ");
        System.out.println("======SOLVING ALL METHODS USING AVL +A*SEARCH======");
        for (int i = 0; i < num_puzzles; i++) {
            puzzles[i].aStarSolve(doPrint);
        }
}

}
