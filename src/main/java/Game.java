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

    public boolean checkDraw(Player player){
        boolean drawgame = true;
        int dealerTotal = scorer.getScore(this.dealer);
        int currentPlayerScore = scorer.getScore(player);
        if (scorer.isBust(dealerTotal) && scorer.isBust(currentPlayerScore)) {
            return drawgame;
        }
        if(currentPlayerScore != dealerTotal){
            drawgame = false;
        } else if (dealerTotal == 21 && currentPlayerScore == 21) {
            boolean dealerBlackjack = this.isBlackJack(dealer);
            boolean playerBlackjack = this.isBlackJack(player);
            if (!dealerBlackjack || !playerBlackjack) {
                drawgame = false;
            }
        }
        return drawgame;
    }

    public boolean isBlackJack(Player player) {
        int score = scorer.getScore(player);
        int noOfCards = player.cardCount();
        if (score == 21 && noOfCards == 2) {
            return true;
        }
        return false;
    }

    public Player checkWinner(Player player){
        int highest = 0;
        Player winner = null;
        if(!scorer.isBust(scorer.getScore(this.dealer))) {
            highest = scorer.getScore(this.dealer);
            winner = dealer;
        }
        boolean dealerBlackjack = this.isBlackJack(dealer);
        if (!dealerBlackjack) {
            int currentPlayerScore = scorer.getScore(player);
            boolean playerBlackjack = this.isBlackJack(player);
            if (playerBlackjack || (currentPlayerScore > highest && !scorer.isBust(currentPlayerScore))) {
                highest = currentPlayerScore;
                winner = player;
            }
        }
        return winner;
    }
}
