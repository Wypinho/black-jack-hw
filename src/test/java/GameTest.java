import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    Player player1;
    Player dealer;
    Deck deck;
    Game game;
    Card faceCard;
    Card aceCard;
    Card nineCard;
    Card threeCard;
    IScorer scorer;

    @Before
    public void setup(){
        player1 = new Player("Steve");
        deck = new Deck();
        scorer = new PlayerScorerByRankOrder();
        game = new Game(deck, scorer);
        game.addPlayer(player1);

        faceCard = new Card(Suit.SPADES, Rank.JACK);
        aceCard = new Card(Suit.SPADES, Rank.ACE);
        nineCard = new Card(Suit.HEARTS, Rank.NINE);
        threeCard = new Card(Suit.DIAMONDS, Rank.THREE);
    }

    @Test
    public void gameHasPlayers(){
        assertEquals(1, game.playerCount());
    }

    @Test
    public void gameCanStart(){
        game.start(2);
        assertEquals(2, player1.cardCount());
        assertEquals(2, game.getDealer().cardCount());
    }

    @Test
    public void newGameAutomaticallyHasDealerWithCards(){
        game.start(2);
        assertEquals("Dealer", game.getDealer().getName());
        assertEquals(2, game.getDealer().cardCount());
    }

    @Test
    public void gameCanDealMultipleCards(){
        game.start(20);
        assertEquals(20, player1.cardCount());
        assertEquals(20, game.getDealer().cardCount());
    }

    @Test
    public void gameCanCheckDraw(){
        game.start(0);
        player1.takeCard(faceCard);
        player1.takeCard(aceCard);
        dealer = game.getDealer();
        dealer.takeCard(faceCard);
        dealer.takeCard(aceCard);
        assertTrue(game.checkDraw());
    }

    @Test
    public void gameCanCheckWinnerByValue(){
        game.start(0);
        player1.takeCard(faceCard);
        player1.takeCard(aceCard);
        dealer = game.getDealer();
        dealer.takeCard(faceCard);
        dealer.takeCard(threeCard);
        assertEquals(player1, game.checkWinner());
    }

    @Test
    public void playersAutomaticallyLoseIfBust(){
        game.start(0);
        player1.takeCard(faceCard);
        player1.takeCard(aceCard);
        player1.takeCard(nineCard);
        player1.takeCard(threeCard);
        dealer = game.getDealer();
        dealer.takeCard(faceCard);
        dealer.takeCard(threeCard);
        assertEquals(dealer, game.checkWinner());
    }

    @Test
    public void dealerAutomaticallyLosesIfBust(){
        game.start(0);
        player1.takeCard(faceCard);
        player1.takeCard(aceCard);
        dealer = game.getDealer();
        dealer.takeCard(faceCard);
        dealer.takeCard(threeCard);
        dealer.takeCard(nineCard);
        dealer.takeCard(threeCard);
        assertEquals(player1, game.checkWinner());
    }

}
