import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;

public class Graph {
    int numVertex;  // Number of vertices in the graph.
    GraphNode[] G;  // Adjacency list for graph.
    String graphName;  //The file from which the graph was created.


    public Graph() {
        this.numVertex = 0;
        this.graphName = "";
    }

    public Graph(int numVertex) {
        this.numVertex = numVertex;
        G = new GraphNode[numVertex];
        for (int i = 0; i < numVertex; i++) {
            G[i] = new GraphNode( i );
        }
    }

    public boolean addEdge(int source, int destination) {
        if (source < 0 || source >= numVertex) return false;
        if (destination < 0 || destination >= numVertex) return false;
        //add edge
        G[source].addEdge( source, destination );
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "The Graph " + graphName + " \n" );

        for (int i = 0; i < numVertex; i++) {
            sb.append( G[i].toString() );
        }
        return sb.toString();
    }

    public void makeGraph(String filename) {
        try {
            graphName = filename;
            Scanner reader = new Scanner( new File( filename ) );
            System.out.println( "\n" + filename );
            numVertex = reader.nextInt();
            G = new GraphNode[numVertex];
            for (int i = 0; i < numVertex; i++) {
                G[i] = new GraphNode( i );
            }
            while (reader.hasNextInt()) {
                int v1 = reader.nextInt();
                int v2 = reader.nextInt();
                G[v1].addEdge( v1, v2 );
            }
//            System.out.println("size of 10:" + G[10].succ.size()); //DELETE
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void clearAllPred() {
        for (int i = 0; i < numVertex; i++) {
            G[i].p1.clear();
            G[i].p2.clear();
        }
    }

    /**
     * Find the path from v1 to v2 going through anc.
     *
     * @param v1:  first vertex
     * @param v2:  second vertex
     * @param anc: ancestor of v1 and v2
     * @return the path
     */
    public String reportPath(int v1, int v2, int anc) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> v1_path = new ArrayList<>();
        ArrayList<Integer> v2_path = new ArrayList<>();

        // Get v1 path
        int temp = anc;
        while (G[temp].nodeID != v1){
            v1_path.add(temp);
            temp = G[temp].p1.pred;
        }
        v1_path.add(v1);

        // Get v2 path
        temp = anc;
        while (G[temp].nodeID != v2){
            v2_path.add(temp);
            temp = G[temp].p2.pred;
        }
        v2_path.add(v2);

        // add v1 path to string
        for (int i = v1_path.size()-1; i >= 0; i--){
            sb.append(v1_path.get(i) + " ");
        }

        // add v2 path to string
        for (int i = 1; i < v2_path.size(); i++){
            sb.append(v2_path.get(i) + " ");
        }
        return sb.toString();
    }



    // Implementation 2
    public void createPath(int side, GraphNode node, int dist, GraphNode pred){
        // side==0 is p1; side==1 is p2
        if (side == 0) node.p1.set(pred.nodeID, dist);
        else node.p2.set(pred.nodeID, dist);

        // Check to see if we are at a root node
        if (node.nodeID == 0 || (!node.p1.isdefault() && !node.p2.isdefault())){
            return;
        }

        // Go to the next value in the graph
        for (EdgeInfo edgeInfo : node.succ){
            // p1 : 0
            if (side == 0){
                if (G[edgeInfo.to].p1.dist < dist + 1) return;
            } else {
                if (G[edgeInfo.to].p2.dist < dist + 1) return;
            }
            createPath(side, G[edgeInfo.to], dist + 1, node);
        }
    }

    public int getScaNew(int position, boolean direction){
        // True = Down, False = Up
        if (G[position].p1.dist == -99) return -1;
        if (!G[position].p1.isdefault() && !G[position].p2.isdefault()) {
            // Now lets check to make sure there isnt a common one earlier
            int tempNum = G[position].p1.pred;
            System.out.println("temp num: " + tempNum);
            System.out.println(G[tempNum].p1);
            System.out.println(G[tempNum].p2);
            if (!G[tempNum].p1.isdefault() && !G[tempNum].p2.isdefault()) return tempNum;
            return position;
        }
//        if (position == G[position].p1.pred) return -1;
        int down = -1;
        int up = -1;
        if(G[position].p1.pred != -1 && direction && !(position == G[position].p1.pred)) down = getScaNew(G[position].p1.pred, true);

        for (EdgeInfo edgeInfo : G[position].succ)
            if (G[edgeInfo.to].p1.dist != -99) {
                up = java.lang.Math.max(getScaNew(G[edgeInfo.to].nodeID, false), up);
            }

        return java.lang.Math.max(down, up);
    }

    public int getScaNewNew(){
        ArrayList<Integer> nodes = new ArrayList<>();
        ArrayList<Integer> sums = new ArrayList<>();

        // Lets get all of the nodes which are a common ancestor
        for (int i = 0; i < numVertex; i++){
            if (!G[i].p1.isdefault() && !G[i].p2.isdefault()) nodes.add(i);
        }

        // Now lets get the distances for those nodes
        for (int i = 0; i < nodes.size(); i++){
            int sum = G[nodes.get(i)].p1.dist + G[nodes.get(i)].p2.dist;
            sums.add(sum);
        }

        // now lets get the node with the smallest sum
        if (sums.size() > 0){
            int least = sums.get(0);
            int pos = nodes.get(0);
            for (int i = 0; i < nodes.size(); i++){
                if (sums.get(i) < least) pos = nodes.get(i);
            }
            return pos;
        }

        return -1;

    }


    /**
     * Computes the least common ancestor of v1 and v2, prints the length of the path, the ancestor, and the path itself.
     *
     * @param v1: first vertex
     * @param v2: second vertex
     * @return returns the length of the shortest ancestral path.
     */
    public int lca(int v1, int v2) {
        // Compute lca
//        int sca = get_sca(v1, v2);
//        this.update_PathInfo(v1, 0, sca);
//        this.update_PathInfo(v2, 1, sca);

        this.createPath(0, G[v1], 0, G[v1]);
        this.createPath(1, G[v2], 0, G[v2]);
//        int sca = getScaNew(0, true);
        int sca = getScaNewNew();

        int dist = G[sca].p1.dist + G[sca].p2.dist;

        PathInfo best = new PathInfo();
//        System.out.println("BEST" + best);
        System.out.println( graphName + " Best lca " + v1 + " " + v2 + " Distance: " + dist + " Ancestor " + sca + " Path:" + reportPath( v1, v2, sca ) );

        clearAllPred();
        return dist;
    }

    // LCA with optional printing
    public int lca(int v1, int v2, boolean verbose) {
        // Compute lca
//        int sca = get_sca(v1, v2);
//        this.update_PathInfo(v1, 0, sca);
//        this.update_PathInfo(v2, 1, sca);

        this.createPath(0, G[v1], 0, G[v1]);
        this.createPath(1, G[v2], 0, G[v2]);
//        int sca = getScaNew(0, true);
        int sca = getScaNewNew();

        int dist = G[sca].p1.dist + G[sca].p2.dist;

        PathInfo best = new PathInfo();
//        System.out.println("BEST" + best);
        if (verbose)System.out.println( graphName + " Best lca " + v1 + " " + v2 + " Distance: " + dist + " Ancestor " + sca + " Path:" + reportPath( v1, v2, sca ) );

        clearAllPred();
        return dist;
    }

    // CURRENTLY ONLY WORKS FOR FIRST GRAPH
    public int get_sca(int a, int b){
        ArrayList<Integer> possible_paths_a = new ArrayList<>();
        possible_paths_a.add(a);
        int temp = a;
        while (G[temp].succ.size() > 0){
            temp = G[temp].succ.getFirst().to;
            possible_paths_a.add(temp);
        }

        ArrayList<Integer> possible_paths_b = new ArrayList<>();
        possible_paths_b.add(b);
        temp = b;
        while (G[temp].succ.size() > 0){
            temp = G[temp].succ.getFirst().to;
            possible_paths_b.add(temp);
        }

        for (int i = 0; i < possible_paths_b.size(); i++){
            if (possible_paths_a.contains(possible_paths_b.get(i))){
                return possible_paths_b.get(i);
            }
        }
        return -1;
    }

    public void update_PathInfo(int a, int side, int sca){
        // side==0 p1, side==1 p2
        if (side==0){
            G[a].p1.set(a, 0);
        } else {
            G[a].p2.set(a, 0);
        }
        int pred = a;
        a = G[a].succ.getFirst().to;
        this.update_PathInfo(a, side, sca, pred, 1);
    }

    // Recursively set the rest
    public void update_PathInfo(int a, int side, int sca, int pred, int count){
        if (side==0){
            G[a].p1.set(pred, count);
        } else {
            G[a].p2.set(pred, count);
        }

        if (a == sca) return;

        pred = a;
        if (G[a].succ.size() > 0){
            a = G[a].succ.getFirst().to;
            this.update_PathInfo(a, side, sca, pred,  count + 1);
        }
    }

    public int outcast(int[] v) {
//        int outcast = -1;
        ArrayList<Integer> sums = new ArrayList<>();

        for (int i = 0; i < v.length; i++){
            int tempSum = 0;
            for (int j = 0; j < v.length; j++)
                tempSum += lca(v[i], v[j], false);
            sums.add(tempSum);
        }

//        for(int i = 0; i < v.length; i++){
//            System.out.println("V: " + v[i] + "  SUM: " + sums.get(i));
//        }

        int outcast = Collections.max(sums);
        int index = sums.get(sums.indexOf(outcast));

        System.out.println( "The outcast of " + Arrays.toString( v ) + " is " + outcast + " with distance sum of " + index);
        return outcast;

    }

    public static void main(String[] args) {
        Graph graph1 = new Graph();
        graph1.makeGraph( "digraph1.txt" );
        System.out.println(graph1.toString());
        int[] set1 = {7, 10, 2};
        int[] set2 = {7, 17, 5, 11, 4, 23};
        int[] set3 = {10, 17, 13};
        graph1.lca( 3, 7 );
        graph1.lca( 5, 6 );
        graph1.lca( 9, 1 );
        graph1.outcast( set1 );

        Graph graph2 = new Graph();
        graph2.makeGraph( "digraph2.txt" );
        System.out.println(graph2.toString());
        System.out.println(graph2.G);

        graph2.lca( 3, 24 );

        graph2.outcast( set3 );
        graph2.outcast( set2 );
        graph2.outcast( set1 );
    }
}