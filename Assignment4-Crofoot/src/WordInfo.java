public class WordInfo {
    private String word;
    private int totalScore;
    private int numOccurences;
    private int reviewCount;

    WordInfo(){};
    WordInfo(String word, int score){
        this.word = word;
        this.totalScore = score;
        this.numOccurences = 1;
        this.reviewCount = 1;
    }

    public boolean update(int newScore){
        this.totalScore += newScore;
        this.numOccurences++;
        return true;
    }

    public float getTotalScore() {return this.totalScore;}
    public int getNumOccurences() {return this.numOccurences;}
    public float getCalculatedScore(){return (float)this.totalScore/(float)this.numOccurences;}
    public String getWord() {return this.word;}
    public int getReviewCount(){return this.reviewCount;}
    public void updateReviewCount(){this.reviewCount++;}

}
