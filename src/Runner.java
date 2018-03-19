import java.util.Scanner;

public class Runner {

    public static void main(String[] args){
        int playingGame = 1;
        double bettingMoney = 1000.00;
        double debt = 0;
        double roundBet = 0;
        boolean takingLoan = false;
        boolean error;
        int handValue;
        Player you;
        Dealer croupier;
        Scanner scan;

        Deck blackjackDeck;
        you = new Player();
        croupier = new Dealer();
        scan = new Scanner(System.in);

        while(playingGame>0){
            System.out.println("New round!");
            //determine bet
            while(!(roundBet>0&&(((roundBet<=bettingMoney&&bettingMoney>0)||(bettingMoney==0))||takingLoan))){
                System.out.println("How much would you like to bet on this round? You have $"
                        + Double.toString(bettingMoney) + "0 available.");
                if(debt>0){
                    System.out.println("You owe the house $"+ Double.toString(debt) + "0.");
                }
                //make sure there's a Double next
                error = true;
                while(error){
                    if(scan.hasNextDouble()){
                        error = false;
                    }else{
                        System.out.println("Please enter a number.");
                        scan.next();
                    }
                }
                roundBet = scan.nextDouble();
                roundBet = Math.round(roundBet);
                //roundBet = Math.round(roundBet*10.0)/10.0;
                //above line rounds the bet value the user inputs to one decimal

                if(roundBet<=0){
                    System.out.println("You must bet more than 0 dollars.");
                }else if(roundBet>bettingMoney){
                    System.out.println("You would like to bet $" + Double.toString(roundBet)
                            + "0 and you currently have $" + Double.toString(bettingMoney)
                            + "0. Confirm that you would like to borrow the difference from the house. " +
                            "(Enter true or false)");

                    //make sure there's a bool next
                    error = true;
                    while(error){
                        if(scan.hasNextBoolean()){
                            error = false;
                        }else{
                            System.out.println("Please enter either 'true' or 'false.'");
                            scan.next();
                        }
                    }
                    takingLoan = scan.nextBoolean();
                    if(!takingLoan){
                        roundBet = 0;
                    }
                }
            }
            System.out.println("You are betting $" + Double.toString(roundBet) + "0 on this round.");

            //determine number of decks
            System.out.println("How many decks would you like to use? (Enter an integer from 1-8)");
            int decks = 0;
            //make sure there's an int next
            while(!(decks>0&&decks<=8)){
                error = true;
                while(error){
                    if(scan.hasNextInt()){
                        error = false;
                    }else{
                        System.out.println("Please enter an integer from 1-8.");
                        scan.next();
                    }
                }
                decks = scan.nextInt();
                if(!(decks>0&&decks<=8)){
                    System.out.println("Illegal number of decks. Please enter an integer from 1 to 8.");
                }
            }
            System.out.println("Shuffling " + Integer.toString(decks) + " decks.");
            blackjackDeck = new Deck(decks);

            //round start!
            you.startGame(blackjackDeck);
            croupier.startGame(blackjackDeck);
            playingGame = 2;

            System.out.println("The round has begun.");
            System.out.println("Your starting hand is " + you.showHand() + ", for a value of "
                    + Integer.toString(you.valueOfHand()) + ".");
            //check for naturals
            if(you.valueOfHand()==21||croupier.valueOfHand()==21){
                if(croupier.valueOfHand()!=21){
                    System.out.println("You were dealt a natural blackjack! You win.");
                    roundBet = 1.5*roundBet;
                    System.out.println("The dealer pays you 1.5 times your bet, or $" + Double.toString(roundBet) + "0.");
                    if(debt>0){
                        if(debt-roundBet<0){
                            bettingMoney+=roundBet-debt;
                            debt = 0;
                        }else{
                            debt-=roundBet;
                        }
                    }else{
                        bettingMoney+=roundBet;
                    }
                }else if(you.valueOfHand()!=21){
                    System.out.println("The dealer was dealt a natural blackjack! You lose this round.");
                    System.out.println("The dealer collects your bet.");
                    if(roundBet>bettingMoney){
                        debt+=(roundBet-bettingMoney);
                        bettingMoney = 0;
                    }else{
                        bettingMoney-=roundBet;
                    }
                }else{
                    System.out.println("Both you and the dealer were dealt a natural blackjack! This round is a standoff.");
                    System.out.println("The dealer returns your bet.");
                }
                roundBet = 0;
                playingGame = 1;
            }

            if(playingGame>1){
                System.out.println("The dealer's upcard is " + croupier.showFirstCard());
            }

            String userInput;
            while(playingGame>1){
                System.out.println("What would you like to do? Hit [H], Stand [S], Surrender [U], Check your hand [C]," +
                        " or View the dealer's card [V]. (Enter one letter)");
                userInput = "";
                while(!(userInput.equals("h")||userInput.equals("s")||userInput.equals("u")
                        ||userInput.equals("v")||userInput.equals("c"))){
                    userInput = scan.nextLine().toLowerCase();

                    if(!(userInput.equals("h")||userInput.equals("s")||userInput.equals("u")
                            ||userInput.equals("v")||userInput.equals("c"))){
                        System.out.println("Enter one letter: either H, S, U, C, or V. (It can be lower case)");
                    }
                }
                if(userInput.equals("h")){
                    System.out.println("You choose to hit. Dealing you a new card...");
                    you.newCard(blackjackDeck.dealCard());
                    handValue = you.valueOfHand();
                    System.out.println("Your hand is now " + you.showHand() + ", for a " + you.hardOrSoft() +
                            " value of " + Integer.toString(handValue) + ".");

                    if(handValue>21){
                        System.out.println("Your hand value has exceeded 21! Bust. You lose this round.");
                        System.out.println("The dealer collects your bet.");
                        if(roundBet>bettingMoney){
                            debt+=(roundBet-bettingMoney);
                            bettingMoney = 0;
                        }else{
                            bettingMoney-=roundBet;
                        }
                        roundBet = 0; //if roundBet is set to 0, the dealer doesn't have to go
                        playingGame = 1;
                    }
                }else if(userInput.equals("s")){
                    System.out.println("You decide to stand. Your turn ends.");
                    playingGame = 1;
                }else if(userInput.equals("v")){
                    System.out.println("The dealer's upcard is " + croupier.showFirstCard());
                }else if(userInput.equals("c")){
                    System.out.println("Your hand is " + you.showHand() + ", for a " + you.hardOrSoft() +
                            " value of " + Integer.toString(you.valueOfHand()) + ".");
                }else if(userInput.equals("u")){
                    System.out.println("You choose to surrender. The round ends.");
                    roundBet = 0.5*roundBet;
                    System.out.println("The dealer returns half your bet, or $" + Double.toString(roundBet) + "0.");
                    //The bet is never subtracted from your betting money at the start, so you need to subtract half now.
                    if(roundBet>bettingMoney){
                        debt+=(roundBet-bettingMoney);
                        bettingMoney = 0;
                    }else{
                        bettingMoney-=roundBet;
                    }
                    roundBet = 0;
                    playingGame = 1;
                }

            }

            if(roundBet>0){
                System.out.println("It is now the dealer's turn.");
                int drawnCards = 0;
                while(croupier.valueOfHand()<17){
                    drawnCards++;
                    croupier.newCard(blackjackDeck.dealCard());
                }
                if(drawnCards>1){
                    System.out.println("The dealer draws " + Integer.toString(drawnCards) + " cards.");
                }else if(drawnCards==1){
                    System.out.println("The dealer draws " + Integer.toString(drawnCards) + " card.");
                }else{
                    System.out.println("The dealer does not draw cards.");
                }
                System.out.println("The dealer's hand is " + croupier.showHand() + ", with a value of "
                        + Integer.toString(croupier.valueOfHand()) + ".");
                int dealerValue = croupier.valueOfHand();
                handValue = you.valueOfHand();
                if(dealerValue>21){
                    System.out.println("The dealer has busted. You win the round.");
                }else if(dealerValue>handValue){
                    System.out.println("The dealer's hand is higher than your hand. You lose this round.");
                    System.out.println("The dealer collects your bet.");
                    if(roundBet>bettingMoney){
                        debt+=(roundBet-bettingMoney);
                        bettingMoney = 0;
                    }else{
                        bettingMoney-=roundBet;
                    }
                    roundBet = 0;
                }else if(dealerValue==handValue){
                    System.out.println("Your hands are equal in value. This round is a standoff.");
                    System.out.println("The dealer returns your bet.");
                }else{
                    System.out.println("Your hand is higher than the dealer's hand. You win this round.");
                }
                if(roundBet>0){
                    System.out.println("The dealer pays you $" + Double.toString(roundBet) + "0.");
                    if(debt>0){
                        if(debt-roundBet<0){
                            bettingMoney+=roundBet-debt;
                            debt = 0;
                        }else{
                            debt-=roundBet;
                        }
                    }else{
                        bettingMoney+=roundBet;
                    }
                }
            }
            roundBet = 0;
            System.out.println("The round is complete. Would you like to play another round? (Enter 'true' or 'false'.)");
            error = true;
            while(error){
                if(scan.hasNextBoolean()){
                    error = false;
                    if(!scan.nextBoolean()){
                        playingGame = 0;
                    }
                }else{
                    System.out.println("Please enter either 'true' or 'false.'");
                    scan.next();
                }
            }
        }

        if(bettingMoney>1000){
            System.out.println("You leave the blackjack table. You now have $" + Double.toString(bettingMoney)
                    + "0, which is $" + Double.toString(bettingMoney-1000) + "0 more than you started with. " +
                    "Congratulations!");
        }else if(bettingMoney>=0){
            System.out.println("You leave the blackjack table. You now have $" + Double.toString(bettingMoney)
                    + "0, which is $" + Double.toString(1000-bettingMoney) + "0 less than you started with. " +
                    "Well, at least you didn't lose everything.");
        }else if(debt>0){
            System.out.println("You leave the blackjack table. You now owe the house $" + Double.toString(debt)
                    + "0. With dread in your heart, you wonder how you're going to pay it off.");
        }

    }
}
