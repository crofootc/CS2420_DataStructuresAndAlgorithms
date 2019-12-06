import java.util.ArrayList;

public class experiment {
    public static void main(String[] args) {
        ArrayList<Integer> test = new ArrayList<>();

        test.add(1);
        test.add(2);
        test.add(3);
        System.out.println(test);

        ArrayList<Integer> test_copy = new ArrayList<>();
        for (int i = 0; i < test.size(); i++){
            test_copy.add(test.get(i));
        }
        test_copy.add(777);
        System.out.println("TEST:" + test);
        System.out.println("TEST_COPY" +test_copy);
    }
}
