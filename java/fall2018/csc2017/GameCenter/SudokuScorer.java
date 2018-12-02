package fall2018.csc2017.GameCenter;

import java.io.Serializable;

import static java.lang.Math.pow;

/**
 * The Sudoku scorer for sudoku game.
 */
public class SudokuScorer extends Scorer implements Serializable {

    /**
     * calculate score with given level and time.
     *
     * @param level level of difficulty
     * @param timeScore number of move.
     * @return return the calculate result
     */
    @Override
    public int calculateScore(int level, int timeScore) {
        return (int) ((500 * level * pow(0.99, timeScore)));
    }
}
