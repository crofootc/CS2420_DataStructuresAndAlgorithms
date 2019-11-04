import java.util.Scanner;
import java.io.*;
import java.util.*;


public class ReadCode {
    public static void main(String[] args) {

        // 1. Read in the file and store it in an array of Terms
        try {
            ArrayList<Term> terms = new ArrayList<>();
            String filename = "SortedWords.txt";
            Scanner reader = new Scanner( new File( filename ) );
            while ((reader.hasNext())) {
                String word = reader.next();
                long freq = reader.nextInt();
                Term temp = new Term(word, freq);
                terms.add(temp);
            }
            reader.close();

            // 2. For each target word (or length r), provide the top "count matches"

            // 2a) get substring and count
            Scanner in = new Scanner(System.in);
            System.out.println("Please input the substring: ");
            String s = in.nextLine();

            System.out.println("Please enter the count: ");
            int count = in.nextInt();

            // Create a skew heap with all words that start with the substring
            SkewHeap<Term> h = new SkewHeap<>();

            int index = termBinarySearch(terms, s);

            while (index != -1 && index < terms.size() && s.length() <= terms.get(index).word.length()
            && s.equals(terms.get(index).word.substring(0, s.length()))){
                h.insert(terms.get(index));
                index++;
            }

            // Using weight as a priority, output the top "count" terms
            System.out.println("Substring: " + s + "   count: " + count);
            System.out.println();
            for (int i = 0; i < count; i++){
                if (h.isEmpty()) break;
                System.out.println( "\t " + h.deleteMax().word);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int termBinarySearch(ArrayList<Term> arr, String key){
        int first = 0;
        int last = arr.size();
        while (first < last){
            int mid = (first + last) / 2;
            int sub = (arr.get(mid).word.length() >= key.length()) ? key.length() : arr.get(mid).word.length();
            if (key.compareTo(arr.get(mid).word.substring(0,sub)) < 0){
                last = mid; // search again in the bottom half
            } else if (key.compareTo(arr.get(mid).word.substring(0,sub)) > 0){
                first = mid + 1;  // Search again in the top half
            } else if(key.equals(arr.get(mid).word.substring(0,sub))){
                while (mid-1>=0 && key.equals(arr.get(mid-1).word.substring(0,sub))){
                    mid -= 1;
                }
                return mid; // Return position
            } else {
                first = mid + 1;
            }
        }
        return -1; // Did not find any matches
    }

}

