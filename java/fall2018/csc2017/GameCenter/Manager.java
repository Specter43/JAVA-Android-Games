package fall2018.csc2017.GameCenter;

import java.io.Serializable;
import java.util.List;

/**
 * The Manager interface for board games.
 */
abstract class Manager implements Serializable {
    /**
     * @return the score of a game play through.
     */
    abstract int getScore();

    /**
     * @return Whether the board game is solved or not.
     */
    abstract boolean puzzleSolved();

    /**
     * Creator of list of tiles for manager.
     *
     * @return the list.
     */
    abstract List createTiles();

    /**
     * Whether the tap is in valid range.
     *
     * @param position tap position.
     * @return whether the tap is valid or not.
     */
    abstract boolean isValidTap(int position);
}
