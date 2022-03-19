/*
 *  COP 3503 Spring 2022 Programming Assignment P1: Politics
 *  Copyright 2022 Jeanne Claire Moore
 */


import java.util.*;
import java.lang.String;
import static java.lang.Integer.parseInt;


class Supporter {
    //  Create a separate class to abstract supporters into as garbage
    public String name;
    public String candidate;

    public Supporter(String name, String candidate){
        //  Constructor for supporters
        this.name = name;
        this.candidate = candidate;
    }
}

public class politics {
    public static void main(String[] args) {
        //  Declare essential variables
        Scanner stdin = new Scanner(System.in);
        StringTokenizer tokenizer = new StringTokenizer(stdin.nextLine());
        int nCandidates = parseInt(tokenizer.nextToken());
        int nSupporters = parseInt(tokenizer.nextToken());
        List candidates;
        
        //  App essentially reads all input until a set of 0 candidates is provided, and the program stops running.
        while ((nCandidates != 0) || (nSupporters != 0)) {
            //  Create an arrayList of Strings candidates and read the next three lines of input into it as elements
            candidates = new ArrayList<String>();

            //  Read candidates in as a list of Strings
            //  Use a for loop to add each element
            for (int i = 0; i < nCandidates; i++) {
                candidates.add(stdin.nextLine());
            }

            //  Now that we have a list of candidates we must list the supporters
            List<Supporter> supporters = new ArrayList<>();
            List<String> sorted = new ArrayList<>();

            //  Read supporters in
            for (int i = 0; i < nSupporters; i++) {
                //  Read in a supporter line and into the list
                tokenizer = new StringTokenizer(stdin.nextLine());
                supporters.add(new Supporter(tokenizer.nextToken(),tokenizer.nextToken()));
            }

            //  Create a tree set to store the list of candidates and fill them into it
            TreeSet<String> candTree = new TreeSet<>();
            candTree.addAll(candidates);

            //  Loop through the candidate tree and add write in candidates not originally listed
            for (int i = 0; i < nSupporters; i++){
                //  Write in candidates are not listed with main candidates, they are found with individual supporters
                if (!candTree.contains(supporters.get(i).candidate)) {
                    candTree.add(supporters.get(i).candidate);
                    candidates.add(supporters.get(i).candidate);
                    nCandidates++;
                }
            }


            //  The list of supporters is filled now time to create a sorted list by the candidate names
            for (int i = 0; i < nCandidates; i++) {
                for (int j = 0; j < nSupporters; j++) {
                    
                    if (supporters.get(j).candidate.equals((CharSequence) candidates.get(i))) {
                        //  Move the sorter to the sorted list
                        sorted.add(supporters.remove(j).name);
                        nSupporters --;
                        j--;
                    }
                }
            }

            //  Put remaining supporters into the array if they have an unmatched candidate
            while (nSupporters != 0) {
                sorted.add(supporters.remove(0).name);
                nSupporters --;
            }

            //  Print out the complete sorted list of supporters
            for (int i = 0; i < sorted.size(); i++) {
                System.out.println(sorted.get(i));
            }

            //  Clear all lists, and redeclare tokenizer to take in more input
            supporters.clear();
            candidates.clear();
            sorted.clear();
            tokenizer = new StringTokenizer(stdin.nextLine());
            
            //  Read the next couple lines
            nCandidates = parseInt(tokenizer.nextToken());
            nSupporters = parseInt(tokenizer.nextToken());
        }
    }
}
