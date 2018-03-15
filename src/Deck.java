import java.util.Random;

public class Deck {
    Card[] allCards;
    public Deck(int numDecks){
        allCards = new Card[52*numDecks];
        //most casinos use 6 or 8 decks at one time: most basic games use just two
        for(int decks = 0; decks<numDecks; decks++){
            for(int n = 0; n<4; n++){
                for(int val = 2; val<15; val++){
                    this.allCards[(52*decks) + (13*n) + val-2] = new Card(n, val);
                }
            }
        }
    }

    public Card dealCard(){
        Random randCardIndex = new Random();
        int returnIndex = randCardIndex.nextInt(allCards.length);
        Card returnCard = this.allCards[returnIndex];
        Card[] newStore = new Card[this.allCards.length-1];
        System.arraycopy(this.allCards, 0, newStore, 0, returnIndex);
        System.arraycopy(this.allCards, returnIndex+1, newStore, returnIndex,
                this.allCards.length-returnIndex-1);
        this.allCards = new Card[this.allCards.length-1];
        System.arraycopy(newStore, 0, this.allCards, 0, newStore.length);
        return returnCard;
    }
}
