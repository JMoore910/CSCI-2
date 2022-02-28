/*
 *  COP 3503 Spring 2022 Programming Assignment P2: Tentaizu
 *  Copyright 2022 Jeanne Claire Moore
 */

//  Full credit for disjoint set's original design goes to Arup Guha with the
//  Computer Science department at UCF.

//  Create an application that runs a modified disjoint set, making connections
//  between computer units through unions of sets. The connectivity of the initial
//  network is printed, then a series of cuts are made between connections. The
//  program must make these cuts, and after each must print the new connectivity.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

public class destroy {
    //  Make a global array tracking connections

    public static void main(String[] args) throws IOException {
        //  First and foremost, retrieve input from user
        //  Use a buffered reader to take in n m and d
        //  where n is the number of computers, m is the number of unions,
        //  and d is the number of disconnections to make

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        int n;
        int m;
        int d;

        String input = br.readLine();
        n = parseInt(Arrays.asList(input.split(" ")).get(0));
        m = parseInt(Arrays.asList(input.split(" ")).get(1));
        d = parseInt(Arrays.asList(input.split(" ")).get(2));

        //  Create a djset of size n for enemy computers
        djset enemyComputers = new djset(n);

        //  Then put all unions into a temp array list
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < m; i ++){
            temp.add(br.readLine());
        }

        //  Lastly, read in which disconnects to make, and move them to the disconnects array list
        ArrayList<Integer> inArray = new ArrayList<>();
        ArrayList<String> disconnects = new ArrayList<>();
        for (int i = 0; i < d; i++){
            inArray.add(parseInt(br.readLine())-1);
            disconnects.add(temp.get(inArray.get(i)));
        }

        //  Push all items in unions array list to a new array
        ArrayList<String> unions = new ArrayList<>();
        for (int i = 0; i < m; i++){
            if (inArray.contains(i)) {
                continue;
            }
            unions.add(temp.get(i));
        }


        //  Move through the unions array and perform each union
        for (int i = 0; i < m-d; i++) {
            enemyComputers = unionInput(enemyComputers, n,
                    Arrays.asList(unions.get(i).split(" ")).get(0),
                    Arrays.asList(unions.get(i).split(" ")).get(1));
        }

        //  Create an array that will hold all connectivities and an arraylist to hold all roots
        int[] connections = new int[n];
        for (int i = 0; i < n; i++) {
            connections[i] = 1;
        }

        connections = findConnectivity(enemyComputers, n, connections);

        int[] res = new int[d+1];
        //  Move through connections and sum the square of all nums and add to sum
        int sum = 0;
        for (int j = 0; j < n; j++) {
            sum += (connections[j] * connections[j]);
        }
        res[d] = sum;

        //  After initial connectivity is found, move through disconnections and union each element
        for (int i = d-1; i >= 0; i--) {
            sum = 0;

            enemyComputers = unionInput(enemyComputers, n,
                    Arrays.asList(disconnects.get(i).split(" ")).get(0),
                    Arrays.asList(disconnects.get(i).split(" ")).get(1));

            for (int j = 0; j < n; j++) {
                connections[j] = 1;
            }

            connections = findConnectivity(enemyComputers, n, connections);


            //  Now do the operation to find connectivity
            for (int j = 0; j < n; j++) {
                sum += (connections[j] * connections[j]);
            }
            res[i] = sum;
        }

        //  Lastly, move backwards through the result array and print the connectivities.
        for (int i = 0; i < d+1; i++){
            System.out.println(res[i]);
        }
    }

    public static djset unionInput(djset dj, int n, String a, String b){
        int n1;
        int n2;

        //  Turn strings into integers for union
        n1 = parseInt(a)-1;
        n2 = parseInt(b)-1;

        dj.union(n1,n2,n);
        return dj;
    }

    public static int[] findConnectivity(djset dj, int n, int[] connections) {
        for (int i = 0; i < n; i++){
            connections[dj.parent[i]]++;
            connections[i]--;
        }

        return connections;
    }

}


//  THE FOLLOWING WAS WRITTEN BY ARUP GUHA for the UCF 2022 Spring Semester Computer Science 2 course

// Arup Guha
// 1/23/2018
// Disjoint Set Implementation with Path Compression
// Written in COP 3503 class
class djset {

    final public static int N = 10;

    public int[] parent;

    // Creates a disjoint set of size n (0, 1, ..., n-1)
    public djset(int n) {
        parent = new int[n];
        for (int i=0; i<n; i++)
            parent[i] = i;
    }

    public int find(int v) {

        // I am the club president!!! (root of the tree)
        if (parent[v] == v) return v;

        // Find my parent's root.
        int res = find(parent[v]);

        // Attach me directly to the root of my tree.
        parent[v] = res;
        return res;
    }

    public boolean union(int v1, int v2, int n) {

        // Find respective roots.
        int rootv1 = find(v1);
        int rootv2 = find(v2);

        // No union done, v1, v2 already together.
        if (rootv1 == rootv2) return false;

        // Attach tree of v2 to tree of v1.
        for (int i = 0; i < n; i++) {
            if ((parent[i] == rootv2) && i != rootv2) {
                parent[i] = rootv1;
            }
        }
        parent[rootv2] = rootv1;
        return true;
    }
}