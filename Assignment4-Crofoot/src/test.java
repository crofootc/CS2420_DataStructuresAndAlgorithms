public class test {
    public static void main(String[] args) {
        String[] colors = {"blue", "red", "green", "yellow", "black", "brown", "purple", "white", "grey", "yellow"};
        QuadraticProbingHashTable H = new QuadraticProbingHashTable<String>();


        System.out.println("TESTING INSERT");
        for (int j=0; j<1; j++) {
            for (int i = 0; i < colors.length; i++) {
                H.insert(colors[i]);
            }
        }

        System.out.println(H.toString());
        System.out.println(H.contains("red"));
        System.out.println(H.contains("diamond"));

        System.out.println("TESTING DELETE");
        System.out.println(H.remove("red"));
        System.out.println(H.contains("red"));
        System.out.println(H.remove("diamond"));

        System.out.println("\nTESTING FIND");
        System.out.println(H.find("red"));
        System.out.println(H.find("black"));
        System.out.println(H.contains("black"));

        System.out.println("\nTESTING PRINTING CONTENTS OF THE HASH TABlE");
        System.out.println(H.toString(4));

        System.out.println("\nTESTING CONTROL SIZE");
        QuadraticProbingHashTable H2 = new QuadraticProbingHashTable<String>();

        System.out.println("H2 size: " + H2.size());
        System.out.println("H2 capacity: " + H2.capacity());
        System.out.println("Adding 40");

        for (int i =0 ; i <40; i++){
            H2.insert(i);
        }

        System.out.println("H2 size: " + H2.size());
        System.out.println("H2 capacity: " + H2.capacity());
        System.out.println("Adding 60 (total 100)");

        for (int i =39 ; i <100; i++){
            H2.insert(i);
        }

        System.out.println("H2 size: " + H2.size());
        System.out.println("H2 capacity: " + H2.capacity());
        System.out.println("Adding 600 (total 700)");

        for (int i =99 ; i <700; i++){
            H2.insert(i);
        }

        System.out.println("H2 size: " + H2.size());
        System.out.println("H2 capacity: " + H2.capacity());


        System.out.println("\n\nTESTING MY PUT METHOD");
        QuadraticProbingHashTable H3 = new QuadraticProbingHashTable<String>();
        QuadraticProbingHashTable H4 = new QuadraticProbingHashTable<String>();


        for (int i = 0; i < colors.length; i++) {
            H3.put(colors[i], i);
        }
//        for (int i = 0; i < colors.length; i++) {
//            H4.insert(colors[i]);
//        }
        System.out.println(H3.toString(100));
        System.out.println(H3.contains(1));
        System.out.println(H3.contains("blue"));
        System.out.println(H3.find(2));
        System.out.println(H3.find("green"));
    }

}
