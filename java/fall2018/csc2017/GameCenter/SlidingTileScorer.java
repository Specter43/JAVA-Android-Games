package fall2018.csc2017.GameCenter;

import java.io.Serializable;

import static java.lang.Math.pow;

/**
 * Calculate a score that round of game when game is finished.
 */
class SlidingTileScorer implements Serializable, Calculable {

    /**
     * calculate score with given level and moves.
     *
     * @param level level of difficulty
     * @param moves number of move.
     * @return return the calculate result
     */
    @Override
    public int calculateScore(int level, int moves) {
        int power = level - 3;
        if (moves > 0) {
            return (int) ((10000 * pow(100, power)) * (1.0 / moves));
        }
        return (int) (10000 * pow(100, power));
    }
}