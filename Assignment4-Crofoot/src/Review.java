import java.io.*;
import java.util.*;

public class Review<AnyType> {
    private float score;
    ArrayList<WordInfo> words;
    private int totalReviews;

    Review() {
    }

    ;

    Review(int score, ArrayList<WordInfo> words) {
        this.score = score;
        this.words = words;
    }

    public QuadraticProbingHashTable readReviewsFromFile(String filename)
            throws FileNotFoundException, IOException {

        BufferedReader in = new BufferedReader(new FileReader(filename));

        ArrayList<WordInfo> words = new ArrayList<>();
        QuadraticProbingHashTable H = new QuadraticProbingHashTable<String>();


        String line;
        String[] list = null;

        int line_count = 0;

        while ((line = in.readLine()) != null) {
            line_count++;
            line = line.trim().toLowerCase();
            list = line.split("\\s+");

            // Getting the score
            int score = Integer.parseInt(list[0]);
            ArrayList<String> inReview = new ArrayList<>();

            for (int i = 1; i < list.length; i++){
                // If word is not in hash table, add it
                if (!H.contains(list[i])){
                    WordInfo newWord = new WordInfo(list[i], score);
                    inReview.add(newWord.getWord());
                    H.put(newWord.getWord(), newWord);
                }
                // else update the existing value
                else {
                    WordInfo word = (WordInfo)H.find(list[i]);
                    word.update(score);

                    // check to see if word has already been in this review
                    if (!inReview.contains(list[i])){
                        word.updateReviewCount();
                        inReview.add(list[i]);
                    }
                    H.remove(list[i]);
                    H.put(word.getWord(), word);
                }
            }
        }
        this.totalReviews = line_count;
        return H;
    }

    public int getTotalReviews(){return this.totalReviews;}

    // test main
    public static void main (String[]args) throws FileNotFoundException, IOException{
//        String filepath = "movieReviewsShort.txt";
        String filepath = "movieReviews.txt";
        Review demo = new Review();
        QuadraticProbingHashTable H = demo.readReviewsFromFile(filepath);

        WordInfo word = (WordInfo)H.find("weak");
        System.out.println(word.getWord());
        System.out.println(word.getCalculatedScore());
        System.out.println(word.getReviewCount());
        System.out.println(word.getNumOccurences());
    }
}