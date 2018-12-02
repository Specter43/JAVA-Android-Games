package fall2018.csc2017.GameCenter;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Manage a slidingTile, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager extends Manager implements Serializable, Undoable {
    /**
     * The serialVersionUID.
     */
    public static final long serialVersionUID = 7738996747003692034L;

    /**
     * The slidingTile being managing
     */
    private SlidingTile slidingTile;

    /**
     * The number of moves taken by the users.
     */
    private int numMoves;

    /**
     * The list of storing Boards.
     */
    private List<Integer> listOfPosition;

    /**
     * The user's name.
     */
    private String userName;

    /**
     * After score for one game round.
     */
    private int score;

    /**
     * The scorer for sliding tile.
     */
    private SlidingTileScorer scorer = new SlidingTileScorer();

    /**
     * The sliding tile Difficulty
     */
    private String slidingTileDifficulty;


    /**
     * The undo limitation.
     */
    private int undoLimit;

    /**
     * The undo limitation (Only 3 times allowed)
     */
    private int undoLimit3;

    /**
     * The size of the board.
     */
    private int level;

    /**
     * The row of position.
     */
    private int row;
    /**
     * The col of position.
     */
    private int col;
    /**
     * The blank id.
     */
    private int blankId;
    /**
     * Getter for slidingTile.
     *
     * @return the last slidingTile get stored.
     */
    SlidingTile getSlidingTile() {
        return this.slidingTile;
    }

    /**
     * @return The undoLimit of this game for unlimited method.
     */
    int getUndoLimit() {
        return undoLimit;
    }

    /**
     * @return The undoLimit of this game for limited method.
     */
    int getUndoLimit3() {
        return undoLimit3;
    }


    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    List<Tile> createTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = this.level * this.level;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (tileNum == numTiles - 1) {
                tiles.add(new Tile(0));
            } else {
                tiles.add(new Tile(tileNum + 1));
            }
        }
        return tiles;
    }

    /**
     * Getter for Level.
     *
     * @return the size of the board
     */
    public int getLevel() {
        return level;
    }

    /**
     * Constructor for BoardManager.
     *
     * @param context the context.
     * @param level the level.
     * @param test the test.
     */
    BoardManager(Context context, int level, boolean test) {
        this.level = level;
        if (level == 3){
            slidingTileDifficulty = "Easy";
        } else if (level == 4){
            slidingTileDifficulty = "Medium";
        } else if(level == 5){
            slidingTileDifficulty = "Hard";
        }
        AccountManager accountManager = new AccountManager(context);
        this.userName = accountManager.getUserName();
        if (this.listOfPosition == null) {
            this.undoLimit = 0;
            this.undoLimit3 = 3;
            this.numMoves = 0;
            this.listOfPosition = new ArrayList<>();
            List<Tile> tiles = createTiles();
            this.slidingTile = new SlidingTile(tiles, level);
            if (!test) {
                solvableShuffle();
            }
        }
    }

    /**
     * Getter for numMoves.
     *
     * @return numMoves.
     */
    int getNumMoves() {
        return numMoves;
    }

    /**
     * Getter for score.
     *
     * @return score.
     */
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
     * Getter for userName.
     *
     * @return userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the userName.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for sliding tile's difficulty.
     *
     * @return the sliding tile's difficulty.
     */
    String getSlidingTileDifficulty() {
        return slidingTileDifficulty;
    }

    /**
     * Shuffle tiles while guarantees a solution.
     */
    private void solvableShuffle() {
        // Constant for swapping directions.
        int level = this.level;
        int left = -1;
        int right = 1;
        int up = -(level);

        int k = 0;
        int bPosition = (level) * (level) - 1;
        // Random choose i
        Random r1 = new Random();
        int i = 50 + r1.nextInt(50);
        while (k <= i) {
            ArrayList<Integer> swapChoices = new ArrayList<>();
            int row = bPosition / this.level;
            int col = bPosition % this.level;
            Tile above = row == 0 ? null : slidingTile.getTile(row - 1, col);
            Tile below = row == this.level - 1 ? null : slidingTile.getTile(row + 1, col);
            Tile lefT = col == 0 ? null : slidingTile.getTile(row, col - 1);
            Tile richT = col == this.level - 1 ? null : slidingTile.getTile(row, col + 1);
            if (above != null) {
                swapChoices.add(up); }
            if (below != null) {
                swapChoices.add(level); }
            if (lefT != null) {
                swapChoices.add(left); }
            if (richT != null) {
                swapChoices.add(right); }
            // Random choose an element from swapChoices, then swap them.
            Random r2 = new Random();
            int c = (swapChoices.get(r2.nextInt(swapChoices.size())));
            int d = bPosition + c;
            this.slidingTile.swapTiles(row, col, d / this.level, d % this.level);
            bPosition = d;
            k++;
        }
    }

    /**
     * Add a modified SlidingTile in the list of boards.
     *
     * @param position The position that was a blank tile.
     */
    private void addPosition(int position) {
        listOfPosition.add(position);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;
        Iterator<Tile> iter = slidingTile.iterator();
        int acc = 1;
        int countTrue = 0;
        while (iter.hasNext()) {
            Tile next = iter.next();
            if (next.getId() != acc) {
                solved = false;
            } else {
                countTrue += 1;
            }
            if (next.getId() == 0 && acc == slidingTile.numTiles() && countTrue ==
                    slidingTile.numTiles() - 1) {
                solved = true;
            }
            acc++;
        }
        if (solved) {
            score = scorer.calculateScore(slidingTile.getLevel(), getNumMoves());
            undoLimit = 0;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / this.level;
        int col = position % this.level;
        int blankId = 0;
        Tile above = row == 0 ? null : slidingTile.getTile(row - 1, col);
        Tile below = row == this.level - 1 ? null : slidingTile.getTile(row + 1, col);
        Tile left = col == 0 ? null : slidingTile.getTile(row, col - 1);
        Tile right = col == this.level - 1 ? null : slidingTile.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }


    /**
     * Process a touch at position in the slidingTile, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        row = position / this.level;
        col = position % this.level;
        blankId = 0;
        if (isValidTap(position)) {
            makeMove();
        }
    }

    /**
     * Move maker of sliding tile game.
     */
    private void makeMove() {
        numMoves++;
        undoLimit++;
        if (row != this.level - 1 && (slidingTile.getTile(row + 1, col)).getId() ==
                blankId) {
            slidingTile.swapTiles(row, col, row + 1, col);
            addPosition((row + 1) * this.level + col);
        }
        if (row != 0 && (slidingTile.getTile(row - 1, col)).getId() == blankId) {
            slidingTile.swapTiles(row, col, row - 1, col);
            addPosition((row - 1) * this.level + col);
        }
        if (col != 0 && (slidingTile.getTile(row, col - 1)).getId() == blankId) {
            slidingTile.swapTiles(row, col, row, col - 1);
            addPosition((row) * this.level + col - 1);
        }
        if (col != this.level - 1 && (slidingTile.getTile(row, col + 1)).getId() ==
                blankId) {
            slidingTile.swapTiles(row, col, row, col + 1);
            addPosition((row) * this.level + col + 1);
        }
    }

    /**
     * Undo the previous moves as required properly.
     */
    @Override
    public void undo() {
        if (undoLimit > 0) {
            touchMove(listOfPosition.remove(listOfPosition.size() - 1));
            numMoves -= 2;
            undoLimit -= 2;
            listOfPosition.remove(listOfPosition.size() - 1);
        }
    }

    /**
     * Undo the previous move(can only be used 3 times).
     */
    void undo3() {
        if (undoLimit3 > 0) {
            numMoves -= 2;
            undoLimit3 -= 1;
            touchMove(listOfPosition.remove(listOfPosition.size() - 1));
            listOfPosition.remove(listOfPosition.size() - 1);
        }
    }
}
