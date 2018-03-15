public class Player extends Person{
    public String hardOrSoft(){
        int handValue = 0;
        int numAces = 0;
        for(int n = 0; n<hand.length; n++){
            handValue = handValue + hand[n].value;
            if(hand[n].value==11){
                numAces++;
            }
        }
        while(handValue>21&&numAces>0){
            handValue-=10;
            numAces--;
        }
        if(numAces>0){
            return "soft";
        }else{
            return "hard";
        }
    }
}