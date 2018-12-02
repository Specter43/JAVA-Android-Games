package fall2018.csc2017.GameCenter;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

/**
 * The Mine game manager.
 */
public class MineManager extends Manager implements Serializable {
    /**
     * The Mine board.
     */
    private MineBoard mineBoard;
    /**
     * The mine tiles.
     */
    private List<MineTile> mineTiles;
    /**
     * The user's name.
     */
    private String userName;
    /**
     * The context.
     */
    private transient Context context;
    /**
     * The mine game's difficulty.
     */
    private String mineDifficulty;
    /**
     * The number of booms in one game play.
     */
    private int numBoom;
    /**
     * The score after the user find out all the booms.
     */
    private int score;
    /**
     * The time passed as seconds.
     */
    private int time;
    /**
     * The timer.
     */
    private transient Timer timer = new Timer();
    /**
     * The scorer for Mine game.
     */
    MineScorer scorer = new MineScorer();

    /**
     * Status of losing.
     */
    private boolean lose = false;

    /**
     * Status of winning.
     */
    private boolean win = false;

    /**
     * Getter for losing status.
     *
     * @return the status of losing.
     */
    boolean getLose(){return this.lose;}

    /**
     * Setter for status of losing.
     */
    void setLose(){this.lose = true;}

    /**
     * Getter for losing status.
     *
     * @return the status of losing.
     */
    boolean getWin(){return this.win;}

    /**
     * Setter for status of losing.
     */
    void setWin(){this.win = true;}

    /**
     * Getter for the time passed.
     *
     * @return The time.
     */
    public int getTime() {
        return time;
    }

    /**
     * Setter for the time passed.
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Getter for the score.
     *
     * @return score.
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Setter for the score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter for mine board.
     *
     * @return the mine board.
     */
    MineBoard getMineBoard() {
        return mineBoard;
    }

    /**
     * Getter for the user name.
     *
     * @return the user name of who logged in.
     */
    String getUserName() {
        return userName;
    }
    /**
     * Getter for the context.
     *
     * @return the context.
     */
    public Context getContext() {
        return context;
    }

    /**
     * Getter for the mine game's difficulty.
     *
     * @return the mine game's difficulty.
     */
    String getMineDifficulty() {
        return mineDifficulty;
    }

    /**
     * Getter for mine tiles.
     *
     * @return the list of mine tiles.
     */
    List<MineTile> getMineTiles() {
        return mineTiles;
    }

    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    List<MineTile> createTiles() {
        List<MineTile> mineTiles = new ArrayList<>();
        int numTiles = MineBoard.getSize() * MineBoard.getSize();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            mineTiles.add(new MineTile(0, false));
        }
        return mineTiles;
    }

    /**
     * The constructor for mine manager.
     *
     * @param context  the context.
     * @param userName the user Name.
     * @param level    the level.
     */
    MineManager(Context context, String userName, String level){
        this.context = context;
        this.userName = userName;
        this.mineTiles = createTiles();
        this.mineDifficulty = level;
        this.mineBoard = generateMineBoardByLevel(level);
        this.numBoom = this.mineBoard.getNumBoom();
        timer.schedule(scorer, 0, 1000);
    }

    /**
     * Generate mine board by level input.
     *
     * @param level the level.
     * @return the generated mine board.
     */
    private MineBoard generateMineBoardByLevel(String level){
        switch (level) {
            case "Easy":
                return new MineBoard(mineTiles, 26, new Random());
            case "Medium":
                return new MineBoard(mineTiles, 40, new Random());
            case "Hard":
                return new MineBoard(mineTiles, 52, new Random());
        }
        return new MineBoard(mineTiles, numBoom, new Random());
    }

    /**
     * Return true if puzzle is solved, false otherwise.
     *
     * @return is the puzzle solved.
     */
    @Override
    public boolean puzzleSolved() {
        int count = 0;
        for (int row = 0; row < MineBoard.getSize(); row++) {
            for (int col = 0; col < MineBoard.getSize(); col++) {
                if (!mineBoard.getMineTile(row, col).getIsOpened() &&
                        mineBoard.getMineTile(row, col).getBackground() ==
                                R.drawable.tile_flagged) {
                    count++;
                }
                if (!mineBoard.getMineTile(row, col).getIsOpened() &&
                        mineBoard.getMineTile(row, col).getValue() != -1) {
                    return false;
                }
            }
        }
        return count == numBoom;
    }

    /**
     * Check touch event is in the board range.
     * Set firstTap to false if this is the first tap.
     *
     * @return True if x and y are in the range, false otherwise.
     */
    boolean isValidTap(int position) {
        int row = position / MineBoard.getSize();
        int col = position % MineBoard.getSize();
        return !mineBoard.getMineTile(row, col).getIsOpened();
    }

    /**
     * Getter for timer.
     *
     * @return the timer.
     */
    Timer getTimer(){return timer;}

    /**
     * Add time.
     *
     * @param time the time passed.
     */
    void addTime(int time){
        this.time += time;
    }

    /**
     * Getter for scorer.
     *
     * @return the scorer.
     */
    Scorer getScorer(){
        return scorer;
    }

    /**
     * Setter for timer.
     *
     * @param timer the timer.
     */
    void setTimer(Timer timer){
        this.timer = timer;
    }

    /**
     * Game failing logic.
     */
    void failing() {
        time = scorer.getTimeScore();
        score = -1;
        timer.cancel();
    }

    /**
     * Game winning logic.
     */
    void winning() {
        time = scorer.getTimeScore();
        score = scorer.calculateScore(mineBoard.getNumBoom(), scorer.getTimeScore());
        timer.cancel();
    }

    /**
     * Setter for user name.
     *
     * @param userName the user's name.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
