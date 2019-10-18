import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TheMain {
    public static void main(String[] args)throws FileNotFoundException, IOException {
        // 1) Read in a review, converting to lower case
        // 2) Assign each word in the review the score attributed to the review
        // 3) Enter WordInfo object into a hash table. If already exists in the table,
        //    update the score and number of occurences

        String filepath = "movieReviews.txt";
        Review reviews = new Review();

        // This function accomplishes 1-3
        QuadraticProbingHashTable H = reviews.readReviewsFromFile(filepath);
        int reviewCount = reviews.getTotalReviews();


        // 4) Prompts user to input moview review
        Scanner in = new Scanner(System.in);
        System.out.println("Please input your review: ");
        String s = in.nextLine();

        // 5) Automatically scores the review based on the average score of the meaningful words in the review
        //    ** words occuring in less then 10% of all reviews are meaningless
        //    0 - 1.75 is negative
        //    1.75 - 2.24 is neutral
        //    2.25+ is positive
        System.out.println("---CALCULATING REVIEW SCORE---");
        float totalScore = 0;
        int numWords = 0;
        String line;
        String[] list = s.split("\\s+");

        for (int i = 0; i < list.length; i++){
            String word = list[i].trim().toLowerCase();
            if (H.contains(word)) {
                WordInfo w = (WordInfo) H.find(word);
                boolean meaningful = ((float)w.getReviewCount() / (float)reviewCount) <= 0.1;
                if (meaningful){
                    totalScore += w.getCalculatedScore();
                    numWords++;
                }
            }
        }

        System.out.println("The review has an average value of " + (totalScore/numWords));
        if(totalScore/numWords < 1.75) System.out.println("Negative");
        if(totalScore/numWords > 1.75 && totalScore/numWords < 2.25) System.out.println("Neutral");
        if(totalScore/numWords > 2.25) System.out.println("Positive");


    }
}
