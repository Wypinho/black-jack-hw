public class PlayerScorerByRankOrder implements IScorer {

    public int getScore(Player player){
        int score = 0;
        for(Card card : player.getHand()){
            if (card.getRank().ordinal() == 0){
                if (score + 11 > 21) {
                    score += 1;
                } else {
                    score += 11;
                }
            } else if (card.isFaceCard()) {
                score += 10;
            } else {
                score += card.getRank().ordinal() + 1;
            }
        }
        return score;
    }

    public boolean isBust(int score){
        return score > 21;
    }

}
