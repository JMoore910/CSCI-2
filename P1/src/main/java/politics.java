import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.lang.String;

public class politics {
    //  Create a class to hold the data for a supporter


    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int nCandidates = stdin.nextInt();
        int nSupporters = stdin.nextInt();


        //  App essentially reads all input until a set of 0 candidates is provided, and the program stops running.
        while (nCandidates != 0) {
            //  Create an arrayList of Strings candidates and read the next three lines of input into it as elements
            String temp;
            List candidates = new ArrayList<String>();

            //  Use a for loop to add each element
            for (int i = 0; i < nCandidates; i++) {
                temp = stdin.nextLine();
                candidates.add(temp);
            }

            //  Now that we have a list of candidates we must list the supporters
            List supporters = new ArrayList<String>();
            List sorted = new ArrayList<String>();

            for (int i = 0; i < nSupporters; i++) {
                //  Read in a supporter line and into the list
                supporters.add(stdin.nextLine());
            }


            //  The list of supporters is filled now time to create a sorted list by the candidate names
            for (int i = 0; i < nCandidates; i++) {
                for (int j = 0; j < nSupporters; j++) {
                    if (supporters.get(j).toString().contains(candidates.get(i).toString())) {
                        sorted.add(supporters.remove(j));
                        continue;
                    }
                }
            }

            //  Put remaining supporters into the array if they have an unmatched candidate
            while (supporters.size() != 0) {
                sorted.add(supporters.remove(0));
            }

            //  Print out the complete sorted list of supporters
            for (int i = 0; i < sorted.size(); i++) {
                System.out.println(sorted.get(i));
            }

            //  Read the next couple lines
            nCandidates = stdin.nextInt();
            nSupporters = stdin.nextInt();
        }
    }
}


