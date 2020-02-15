import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void bustIfScoreOver21(){
        Card jack = new Card(Suit.CLUBS, Rank.JACK);
        Card queen = new Card(Suit.HEARTS, Rank.QUEEN);
        Card king = new Card(Suit.DIAMONDS, Rank.KING);
        Player player = new Player("Andrew");
        player.takeCard(king);
        player.takeCard(jack);
        player.takeCard(queen);
        PlayerScorerByRankOrder scorer = new PlayerScorerByRankOrder();
        int actualScore = scorer.getScore(player);
        assertTrue(scorer.isBust(actualScore));
    }

    @Test
    public void acesRevaluedIfOver21(){
        Card jack = new Card(Suit.CLUBS, Rank.JACK);
        Card nine = new Card(Suit.DIAMONDS, Rank.NINE);
        Card ace = new Card(Suit.SPADES, Rank.ACE);
        Card ace2 = new Card(Suit.HEARTS, Rank.ACE);
        Player player = new Player("Andrew");
        player.takeCard(jack);
        player.takeCard(nine);
        player.takeCard(ace);
        player.takeCard(ace2);
        PlayerScorerByRankOrder scorer = new PlayerScorerByRankOrder();
        int actualScore = scorer.getScore(player);
        assertFalse(scorer.isBust(actualScore));
    }

    @Test
    public void aceRevaluationDoesntAffectScoreOf21Plus(){
        Card jack = new Card(Suit.CLUBS, Rank.JACK);
        Card nine = new Card(Suit.DIAMONDS, Rank.NINE);
        Card ace = new Card(Suit.SPADES, Rank.ACE);
        Card ace2 = new Card(Suit.HEARTS, Rank.ACE);
        Card ace3 = new Card(Suit.DIAMONDS, Rank.ACE);
        Player player = new Player("Andrew");
        player.takeCard(jack);
        player.takeCard(nine);
        player.takeCard(ace);
        player.takeCard(ace2);
        player.takeCard(ace3);
        PlayerScorerByRankOrder scorer = new PlayerScorerByRankOrder();
        int actualScore = scorer.getScore(player);
        assertTrue(scorer.isBust(actualScore));
    }

}
