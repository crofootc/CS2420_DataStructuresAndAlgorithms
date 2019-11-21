import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Read in the moves from both players
        try {
            // Read in the different games
            ArrayList<Integer> moves1 = readMoves("moves.txt");
            ArrayList<Integer> moves2 = readMoves("moves2.txt");

            ////////////
            // GAME 1 //
            ////////////

            // Create the game board
            DisjointSet gameboard = new DisjointSet(121);
            // RED: 122 = TOP; 123 = BOTTOM; || BLUE: 124 = LEFT; 125 = RIGHT
//            gameboard.place_tile(124, 1);
//            gameboard.place_tile(125, 1);
//            gameboard.place_tile(122, 2);
//            gameboard.place_tile(123, 2);

            // Blue always goes first
            int blue_moves = 0;
            int red_moves = 0;
            int color;

            for (int i = 1; i <= moves1.size(); i++){
                if (i % 2 == 1) color = 1;
                else color = 2;

                int turn = moves1.get(i-1);
                gameboard.place_tile(turn, color);

                // Update moves
                if (color == 1) blue_moves++;
                else red_moves++;

                // Check for Blue victory
                if (color == 1){
                    if (check_win_blue(gameboard)){
                        System.out.println("BLUE WINS WITH " + blue_moves +" MOVES!");
                        break;
                    }
                } else {
                    if (check_win_red(gameboard)){
                        System.out.println("RED WINS WITH " + red_moves +" MOVES!");
                        break;
                    }
                }
            }

            ////////////
            // Game 2 //
            ////////////

            gameboard.reset();
            // RED: 122 = TOP; 123 = BOTTOM; || BLUE: 124 = LEFT; 125 = RIGHT
//            gameboard.place_tile(124, 1);
//            gameboard.place_tile(125, 1);
//            gameboard.place_tile(122, 2);
//            gameboard.place_tile(123, 2);

            // Blue always goes first
            blue_moves = 0;
            red_moves = 0;

            for (int i = 1; i <= moves2.size(); i++){
                if (i % 2 == 1) color = 1;
                else color = 2;

                int turn = moves2.get(i-1);
                gameboard.place_tile(turn, color);

                // Update moves
                if (color == 1) blue_moves++;
                else red_moves++;

                // Check for Blue victory
                if (color == 1){
                    if (check_win_blue(gameboard)){
                        System.out.println("BLUE WINS WITH " + blue_moves +" MOVES!");
                        break;
                    }
                } else {
                    if (check_win_red(gameboard)){
                        System.out.println("RED WINS WITH " + red_moves +" MOVES!");
                        break;
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the gameboard
//        DisjointSet gameboard = new DisjointSet(125);
//        gameboard.printDs();

    }



    public static ArrayList<Integer> readMoves(String filename)
            throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        ArrayList<Integer> moves = new ArrayList<>();

        String line;
        while ((line = in.readLine()) != null){
            int element = Integer.parseInt(line);
            moves.add(element);
        }
        return moves;
    }

    public static boolean check_win_red(DisjointSet ds){
        // Bottom = 1 - 11
        // Top = 111 - 121
        ArrayList<Integer> top = new ArrayList<>();
        ArrayList<Integer> bottom = new ArrayList<>();

        for (int i = 0; i < 11; i++){
            top.add(ds.find(i));
        }
        for (int i = 110; i < 121; i++){
            bottom.add(ds.find(i));
        }
        // Check for winner
        for (int i = 0; i < top.size(); i++){
            if (bottom.contains(top.get(i))) return true;
        }
        return false;
    }

    public static boolean check_win_blue(DisjointSet ds){
        // Left = % 11 = 1
        // Right = % 11 = 0
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        for (int i = 0; i < 111; i += 11){
            left.add(ds.find(i));
        }
        for (int i = 10; i < 121; i += 11){
            right.add(ds.find(i));
        }
        // Check for winner
        for (int i = 0; i < left.size(); i++){
            if (right.contains(left.get(i))) return true;
        }
        return false;
    }
}
