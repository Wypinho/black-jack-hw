import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;
    private Player dealer;
    private Deck deck;
    private IScorer scorer;

    public Game(Deck deck, IScorer scorer) {
        this.players = new ArrayList<Player>();
        this.deck = deck;
        this.scorer = scorer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int playerCount(){
        return this.players.size();
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public Player getDealer() {
        return this.dealer;
    }

    public void start(int amountOfCards){
        dealer = new Player("Dealer");
        for (int i = 0; i < amountOfCards; i ++){
            Card card = deck.dealOne();
            dealer.takeCard(card);
        }
        for(Player player:this.players){
            for (int i = 0; i < amountOfCards; i ++){
                Card card = deck.dealOne();
                player.takeCard(card);
            }
        }
    }

    public boolean checkDraw(){
        boolean drawgame = true;
        int handTotal = scorer.getScore(this.dealer);
        for(Player player: this.players){
            int currentPlayerScore = scorer.getScore(player);
            if(currentPlayerScore != handTotal){
                drawgame = false;
            }
        }
        return drawgame;
    }

    public Player checkWinner(){
        int highest = 0;
        Player winner = null;
        if(!scorer.isBust(scorer.getScore(this.dealer))) {
            highest = scorer.getScore(this.dealer);
            winner = dealer;
        }
        for(Player player : this.players){
            int currentPlayerScore = scorer.getScore(player);
            if( currentPlayerScore > highest && !scorer.isBust(currentPlayerScore)){
                highest = currentPlayerScore;
                winner = player;
            }
        }
        return winner;
    }
}
