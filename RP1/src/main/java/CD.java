/*
 *  COP 3503 Spring 2022 Recitation Project 1: CD Solution
 *  Copyright 2022 Jeanne Claire Moore
 */

import java.io.*;
import java.nio.Buffer;
import java.util.*;

import static java.lang.Integer.parseInt;
//  Create an application that tracks
//  the number of CDs that two people have in common

class CDOwner {
    //  Owner should have a name
    //  Owner should have a TreeMap or a TreeSet that contains all cds they own
    public String name;
    public TreeSet<Integer> cds;

    //  Include a loaded constructor and getters
    public CDOwner(String name) {
        this.name = name;
        this.cds = new TreeSet<>();
    }
}
public class CD {
    public static void main(String[] args) throws IOException {

        //  Create a buffered reader before entering the while loop to read in N and M
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine());

        Integer N = parseInt((String) tokenizer.nextToken());
        Integer M = parseInt((String) tokenizer.nextToken());

        //  Make sure neither N nor M are larger than 1000000
        if ((N > 1000000) || (M > 1000000)) {
            return;
        }

        CDOwner jack = new CDOwner("Jack");
        CDOwner jill = new CDOwner("Jill");

        //  Once we have N and M, perform the rest of the program while both are not 0
        while (N > 0 || M > 0) {

            //      for N lines read each next line and add it to Jack's TreeSet
            for (int i = 0; i < N; i++) {
                jack.cds.add(parseInt(br.readLine()));
            }

            //      for M lines read each next line and add it to Jill's TreeSet
            for (int i = 0; i < M; i++) {
                jill.cds.add(parseInt(br.readLine()));
            }

            //      Create a var Integer common at 0, then go through all items in Jack's TreeSet
            int common = 0;

            //      check if Jill's TreeSet contains a node in the TreeSet belonging to Jack
            while (!jill.cds.isEmpty()) {
                if (jack.cds.contains(jill.cds.pollFirst())) {
                    // In the case of both treesets containing a cd number,
                    // iterate common by one
                    common++;
                }
            }

            //      print out common
            System.out.println(common);

            //      Lastly reread N and M with the buffered reader
            tokenizer = new StringTokenizer(br.readLine());
            N = parseInt((String) tokenizer.nextToken());
            M = parseInt((String) tokenizer.nextToken());
        }
    }
}
