import java.util.*;

public class LinkedList {
    private ListNode head = null;
    private ListNode tail = null;
    private ListNode solution = null;
    private ArrayList<Integer> hashList = new ArrayList<>();
    private int numInList = 0;

    /** The insert functions adds new nodes to the LinkedList
     *  Will check new nodes to add to see if they are a solution
     *
     */
    public void insert(Node value){
        ListNode new_node = new ListNode(value);
        new_node.next = null;

        // Checks to see if the new_node is a solution
        if (new_node.isSolution){
            this.solution = new_node;
        }

        // Adds the new_node in the appropriate place in the linkedList
        if(this.head == null){
            this.head = new_node;
            this.tail = this.head;
        }
        else if (!hashList.contains(new_node.value.hashCode())){
            ListNode last = this.tail;
            while(last.next != null){
                last = last.next;
            }
            last.next = new_node;
            this.tail = last;
        }

        numInList++;
    }

    /** The expandList function will go through all of the puzzles in the linkedList and expand them them.
     *  It will then add the new puzzles to the linkedList
     *  It checks against a list of hashcodes already in the list before expanding any puzzle
     */
    public void expandList(){
        ListNode last = this.head;
        ArrayList<Node[]> nodeList = new ArrayList<>();
        Integer tempHash;


        // Goes through and expands all values (that are not already expanded) adding them to an arrayList
        // It adds the hashcode to an arrayList to add logic to avoid duplicate work

        if(this.head == this.tail){
            Node[] initExpanded = last.value.expand();
            nodeList.add(initExpanded);
            this.hashList.add(initExpanded.hashCode());
        }

        while (last != null){
            tempHash = last.value.hashCode();
            if (!hashList.contains(tempHash)){
                Node[] expanded = last.value.expand();
                nodeList.add(expanded);
                this.hashList.add(tempHash);
            }
            last = last.next;
        }

        // Goes through the ArrayList of new puzzles from the expand function and adds them to the linkedList
        for (int i=0; i<nodeList.size();i++){
            for (int j=0; j<(nodeList.get(i)).length; j++){
                tempHash = nodeList.get(i)[j].hashCode();
                if (!hashList.contains(tempHash)) {
                    this.insert(nodeList.get(i)[j]);
                }
            }
        }
    }

    public boolean isSolved(){
        return this.solution!=null;
    }

    public Node getSolution(){return this.solution.value;}

    public int getNumInList() {return numInList;}

    /** ListNode will hold a Node object as a value as well as a reference to the next ListNode in the list
     *  It will also keep track of if values added are solution
     */
    private class ListNode {
        public Node value;
        ListNode next;
        boolean isSolution;

        public ListNode(){};
        public ListNode(Node value){
            this.value = value;
            this.isSolution = value.isGoal();
        }
    }
}
