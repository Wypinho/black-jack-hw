public interface IScorer {

    int getScore(Player player);

    boolean isBust(int score);
}
