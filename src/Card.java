public class Card {
    int suit;
    int face;
    int value;
    String[] suits = new String[] {"♤", "♡", "♢", "♧"};
    String[] faceNames = new String[] {"J", "Q", "K", "A"};
    public Card(int suit, int number){
        this.suit = suit;
        this.face = number;
        if(number>10&&number<14){
            this.value = 10;
        }else if(number==14){
            this.value = 11;
        }else{
            this.value = number;
        }
    }

    public String getCard(){
        if(this.face>10){
            //-11, because that way a Jack ("face" for Jack is 11) will be 0
            return faceNames[this.face-11] + suits[this.suit];
        }else{
            return Integer.toString(face) + suits[this.suit];
        }
    }
}
