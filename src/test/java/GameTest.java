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
        dealer = new Player("James");
        deck = new Deck();
        scorer = new PlayerScorerByRankOrder();
        game = new Game(deck, scorer);
        game.addPlayer(player1);
        game.addPlayer(dealer);

        faceCard = new Card(Suit.SPADES, Rank.JACK);
        aceCard = new Card(Suit.SPADES, Rank.ACE);
        nineCard = new Card(Suit.HEARTS, Rank.NINE);
        threeCard = new Card(Suit.DIAMONDS, Rank.THREE);
    }


    @Test
    public void gameHasPlayers(){
        assertEquals(2, game.playerCount());
    }

    @Test
    public void gameCanStart(){
        game.start(2);
        assertEquals(2, player1.cardCount());
        assertEquals(2, dealer.cardCount());
    }

    @Test
    public void gameCanDealMultipleCards(){
        game.start(20);
        assertEquals(20, player1.cardCount());
        assertEquals(20, dealer.cardCount());
    }

    @Test
    public void gameCanCheckDraw(){
        player1.takeCard(faceCard);
        player1.takeCard(aceCard);
        dealer.takeCard(faceCard);
        dealer.takeCard(aceCard);
        assertTrue(game.checkDraw());
    }

    @Test
    public void gameCanCheckWinner(){
        player1.takeCard(faceCard);
        player1.takeCard(aceCard);
        dealer.takeCard(faceCard);
        dealer.takeCard(threeCard);
        assertEquals(player1, game.checkWinner());
    }

}
