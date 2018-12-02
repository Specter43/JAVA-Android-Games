package fall2018.csc2017.GameCenter;

/**
 * The Scorer interface.
 */
public interface Calculable {
    /**
     * The default score calculator for a game that needs score.
     * @param variable1 the first variable needed.
     * @param variable2 the second variable needed.
     * @return the score with a specific calculating algorithm of the game.
     */
    int calculateScore(int variable1, int variable2);
}
