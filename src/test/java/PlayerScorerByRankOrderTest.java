import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerScorerByRankOrderTest {


    @Test
    public void testScoreForFaceCards(){
        Card jack = new Card(Suit.CLUBS, Rank.JACK);
        Card queen = new Card(Suit.HEARTS, Rank.QUEEN);
        Card king = new Card(Suit.DIAMONDS, Rank.KING);
        Player player = new Player("Andrew");
        player.takeCard(jack);
        player.takeCard(queen);
        player.takeCard(king);
        IScorer scorer = new PlayerScorerByRankOrder();
        int actualScore = scorer.getScore(player);
        assertEquals(30, actualScore);
    }

    @Test
    public void testScoreForAcesHigh(){
        Card ace = new Card(Suit.DIAMONDS, Rank.ACE);
        Player player = new Player("Andrew");
        player.takeCard(ace);
        IScorer scorer = new PlayerScorerByRankOrder();
        int actualScore = scorer.getScore(player);
        assertEquals(11, actualScore);
    }

    @Test
    public void testScoreForAcesLowAfterFirst(){
        Card ace = new Card(Suit.SPADES, Rank.ACE);
        Card ace2 = new Card(Suit.HEARTS, Rank.ACE);
        Player player = new Player("Andrew");
        player.takeCard(ace);
        player.takeCard(ace2);
        IScorer scorer = new PlayerScorerByRankOrder();
        int actualScore = scorer.getScore(player);
        assertEquals(12, actualScore);
    }


}
