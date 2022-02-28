/*
    This program is a personal project of Jeanne Moore
    Copyright 2022 2/13/22
 */



import java.util.*;
import static java.lang.Integer.parseInt;




public class Uno {
    public static Scanner input = new Scanner(System.in);
    public static boolean run = true;

    public static void main(String[] args) {
        loopGame();
    }


    public static void loopGame(){
        while(run) {
            runGame();

            System.out.print("Do you want to play again? (Y/N) : ");
            String in = input.nextLine();
            boolean looping = true;

            while (looping) {
                //  Error Handling: User must enter a valid answer
                if (in.matches("[nyNY]")) {
                    if (in.equals("Y") || (in.equals("y"))) {
                        System.out.println("The game will start again\n");
                        looping = false;

                    } else if (in.equals("N") || (in.equals("n"))) {
                        System.out.println("Thank you for playing!");
                        run = false;
                        looping = false;

                    }
                } else {
                    System.out.print("Do you want to play again? (Y/N) : ");
                    in = input.nextLine();
                }
            }
        }
    }



    public static void runGame(){
        //  Run the game!
        //  At the start, user must input number of players
        //  Player count must be between 2 and 10 inclusive
        System.out.println("Welcome to uno!");
        System.out.print("Please enter num of players between 2 and 10 inclusive: ");

        int numPlayers = parseInt(input.nextLine());
        int victory = -1;

        while (numPlayers < 2 && numPlayers > 10) {
            //  Error handling: User must ensure to input a valid player count between 2 and 10
            System.out.print("Please enter num of players between 2 and 10 inclusive: ");
            numPlayers = parseInt(input.nextLine());
        }

        //  Construct array of players
        Player[] players = new Player[numPlayers];
        Deck deck = new Deck();


        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i);
            players[i].playerHand.drawFirstHand(deck);
        }




    }



}



class Deck {
    public ArrayList<Integer> deck;
    int lastCard;

    public Deck() {
        for (int i = 0; i < 108; i++){
            //  Initialize Deck with Deck constructor
            deck.add(i);
        }

        //  Then shuffle the deck
        shuffle();

        lastCard = removeCard();
    }


    private void shuffle(){
        int j;
        Random rand = new Random();

        for (int i = 0; i < 108; i++) {
            j = rand.nextInt(108);
            Collections.swap(deck, i , j);
        }

        Collections.shuffle(deck);
    }

    public int removeCard() {
        return deck.remove(0);
    }
}



class Player {
    public int id;
    public Hand playerHand;

    public Player(int id) {
        this.id = id;
        playerHand = new Hand(7);
    }
}



class Hand {
    public int handSize;
    public ArrayList<Integer> cardsInHand;

    public Hand(int handSize) {
        this.handSize = handSize;
        cardsInHand = new ArrayList<>();
    }

    public Deck drawFirstHand(Deck deck){
        for (int i = 0; i < 7; i++) {
            deck = drawCard(deck);
        }

        //  After drawing cards return deck
        return deck;
    }


    public Deck drawCard(Deck deck){
        cardsInHand.add(deck.deck.remove(0));
        return deck;
    }

}



