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

import java.util.*;

public class destroy {
    public static void main(String[] args) {
        //  First and foremost, retrieve input from user
        //

    }
}

//  A network will have all computers connected. Allows to return num of computers as connectivity
class Network {
    //  declare vars
    public ArrayList<Integer> computers;
    public int connectivity;

    //  Make a constructor
    public Network(int k){
        computers = new ArrayList<>();
        connectivity = 0;
    }

    public void addComputer(int computer){
        computers.add(computer);
        connectivity ++;
    }

    public void netContains(int computer) {

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

    public boolean union(int v1, int v2) {

        // Find respective roots.
        int rootv1 = find(v1);
        int rootv2 = find(v2);

        // No union done, v1, v2 already together.
        if (rootv1 == rootv2) return false;

        // Attach tree of v2 to tree of v1.
        parent[rootv2] = rootv1;
        return true;
    }
}