public class Person {
    Card[] hand = new Card[0];

    public int valueOfHand(){
        int handValue = 0;
        int numAces = 0;
        for(int n = 0; n<hand.length; n++){
            if(hand[n].value==11){
                numAces++;
            }else{
                handValue = handValue + hand[n].value;
            }
        }
        while(handValue>21&&numAces>0){
            handValue-=10;
            numAces--;
        }
        return handValue;
    }

    public void newCard(Card cardToAdd){
        Card[] newStore = new Card[this.hand.length+1];
        System.arraycopy(this.hand, 0, newStore, 0, hand.length);
        newStore[newStore.length-1] = cardToAdd;
        this.hand = new Card[newStore.length];
        System.arraycopy(newStore, 0, this.hand, 0, newStore.length);
    }

    public void startGame(Deck deckDeal){
        this.hand = new Card[0];
        for(int n = 0; n<2; n++){
            this.newCard(deckDeal.dealCard());
        }
    }

    public String showHand(){
        StringBuilder returnHand = new StringBuilder();
        for(int n = 0; n<this.hand.length; n++){
            returnHand.append(this.hand[n].getCard());
            if(n<this.hand.length-1){
                returnHand.append(", ");
            }
        }
        return returnHand.toString();
    }
}
