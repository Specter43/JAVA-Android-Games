package fall2018.csc2017.GameCenter;

import java.io.Serializable;
import java.util.TimerTask;

/**
 * Calculate a score that round of game when game is finished.
 */
public abstract class Scorer extends TimerTask implements Serializable, Calculable {
    /**
     * The time score for one game play.
     */
    private int timeScore;

    /**
     * calculate score with given level and moves.
     *
     * @param level level of difficulty
     * @param time number of move.
     * @return return the calculate result
     */
    public abstract int calculateScore(int level, int time);

    /**
     * Getter for time score.
     * @return the Time score.
     */
    int getTimeScore() { return timeScore; }

    /**
     * Run the timer.
     */
    @Override
    public void run() { timeScore++; }
}

