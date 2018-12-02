package fall2018.csc2017.GameCenter;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;

/**
 * The sudoku board manager.
 */
public class SudokuBoardManager extends Manager implements Serializable {

    /**
     * The Sudoku Board being managing
     */
    private Sudoku sudoku;

    /**
     * The list of storing Boards.
     */
    private List<Integer> listOfPosition;

    /**
     * The number of sudoku tiles.
     */
    private int[] sudokuNum = new int[81];

    /**
     * The randomization of tiles.
     */
    private Random random = new Random();

    /**
     * The user's name.
     */
    private String userName;

    /**
     * The list of undo.
     */
    private List<Integer> undoList = new ArrayList<>();

    /**
     * the difficulty of sudoku.
     */
    private String sudokuDifficulty;

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
     * the difficulty number of sudoku.
     */
    private int difficulty;

    /**
     * The scorer for Sudoku game.
     */
    private SudokuScorer scorer = new SudokuScorer();

    /**
     * Getter for the time passed.
     *
     * @return The time.
     */
    public int getTime() {
        return time;
    }

    /**
     * Getter for the score.
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
     * @return the Sudoku Difficulty.
     */
    String getSudokuDifficulty() {
        return sudokuDifficulty;
    }

    /**
     * The setter for time.
     *
     * @param time the time passed.
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Getter for slidingTile.
     *
     * @return the last slidingTile get stored.
     */
    Sudoku getSudoku() {
        return this.sudoku;
    }

    /**
     * The move.
     */
    private int move;

    /**
     * Getter for userName.
     *
     * @return userName.
     */
    String getUserName() {
        return userName;
    }

    /**
     * Setter for userName.
     *
     * @param userName given userName.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for difficulty of sudoku game.
     *
     * @return the difficulty of sudoku game.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    List<Tile> createTiles() {
        createRandomSudoku();
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Sudoku.size * Sudoku.size;
        for (int i = 0; i != numTiles; i++) {
            tiles.add(new Tile(sudokuNum[i]));
        }
        return tiles;
    }

    /**
     * Getter of timer.
     *
     * @return the timer.
     */
    Timer getTimer() {
        return timer;
    }

    /**
     * Add time.
     *
     * @param time the time needs to be added.
     */
    void addTime(int time) {
        this.time += time;
    }

    /**
     * Getter for scorer.
     *
     * @return the scorer.
     */
    Scorer getScorer() {
        return scorer;
    }

    /**
     * Setter for timer.
     *
     * @param timer the timer.
     */
    void setTimer(Timer timer) {
        this.timer = timer;
    }

    /**
     * Constructor for BoardManager.
     *
     * @param context the context.
     * @param sudokuDifficulty the sudoku difficulty.
     */
    SudokuBoardManager(Context context, String sudokuDifficulty) {
        this.sudokuDifficulty = sudokuDifficulty;
        AccountManager accountManager = new AccountManager(context);
        this.userName = accountManager.getUserName();
        if (this.listOfPosition == null) {
            this.listOfPosition = new ArrayList<>();
            List<Tile> tiles = createTiles();
            this.sudoku = new Sudoku(tiles);
            timer.schedule(scorer, 0, 1000);
        }
    }

    /**
     * Return whether every column has 1 to 9, every row has 1 to 9,
     * and every 3x3 square has 1 to 9
     *
     * @return whether every column has 1 to 9, every row has 1 to 9,
     * and every 3x3 square has 1 to 9
     */
    boolean puzzleSolved() {
        int[] sudokuNum = new int[Sudoku.size * Sudoku.size];
        Iterator<Tile> iter = sudoku.iterator();
        int acc = 0;
        while (iter.hasNext()) {
            Tile next = iter.next();
            sudokuNum[acc] = next.getId();
            acc++;
        }
        return checkCol(sudokuNum) && checkRow(sudokuNum) && checkSquare(sudokuNum);
    }


    /**
     * When the player win, calculate score and cancel timer.
     */
    void wining() {
        if (puzzleSolved()) {
            this.time += scorer.getTimeScore();
            this.score = scorer.calculateScore(difficulty, time);
            timer.cancel();
        }
    }

    /**
     * Return whether the tile tap is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile tap is the blank tile
     */
    boolean isValidTap(int position) {
        int row = position / Sudoku.size;
        int col = position % Sudoku.size;
        return sudoku.getTile(row, col).getId() == 0 || sudoku.getTile(row, col).getId() > 100;
    }

    /**
     * Process a touch at position in the sudoku, change to the number the player want to add
     *
     * @param position the position
     */
    void makeMove(int position) {

        int row = position / Sudoku.size;
        int col = position % Sudoku.size;
        if (isValidTap(position)) {
            sudoku.writeNum(row, col, move);
        }
        undoList.add(position);
    }

    /**
     * check sudoku board's every column has 1 to 9
     *
     * @param sudokuNum the sudoku board in 2d array
     * @return whether every column have 1 to 9
     */
    boolean checkCol(int[] sudokuNum) {
        for (int i = 0; i < Sudoku.size; i++) {
            List<Integer> col = new ArrayList<>();
            for (int j = 0; j < Sudoku.size; j++) {
                if (sudokuNum[i * Sudoku.size + j] > 100) {
                    col.add(sudokuNum[i * Sudoku.size + j] - 100);
                } else {
                    col.add(sudokuNum[i * Sudoku.size + j]);
                }
            }
            for (int check = 1; check <= Sudoku.size; check++) {
                if (!col.contains(check)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check sudoku board's every row has 1 to 9
     *
     * @param sudokuNum the sudoku board in 2d array
     * @return when every row have 1 to 9
     */
    boolean checkRow(int[] sudokuNum) {
        for (int i = 0; i < Sudoku.size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < Sudoku.size; j++) {
                if (sudokuNum[j * Sudoku.size + i] > 100) {
                    row.add(sudokuNum[j * Sudoku.size + i] - 100);
                } else {
                    row.add(sudokuNum[j * Sudoku.size + i]);
                }
            }
            for (int check = 1; check <= Sudoku.size; check++) {
                if (!row.contains(check)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check sudoku board's every 3x3 square has 1 to 9
     *
     * @param sudokuNum the sudoku board in 2d array
     * @return when every 3x3 square have 1 to 9
     */
    boolean checkSquare(int[] sudokuNum) {
        for (int position = 0; position < Sudoku.size * Sudoku.size; position++) {
            List<Integer> square = new ArrayList<>();
            int row = position / Sudoku.size;
            int col = position % Sudoku.size;
            int xOff = row / 3 * 3;
            int yOff = col / 3 * 3;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (sudokuNum[(xOff + i) * 9 + yOff + j] > 100) {
                        square.add(sudokuNum[(xOff + i) * 9 + yOff + j] - 100);
                    } else {
                        square.add(sudokuNum[(xOff + i) * 9 + yOff + j]);
                    }
                }
            }
            if (position % 9 == 8) {
                for (int check = 1; check <= Sudoku.size; check++) {
                    if (!square.contains(check)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Generate a random sudoku board.
     */
    private void createRandomSudoku() {
        randomChoose(0);
        if (sudokuDifficulty != null) {
            switch (sudokuDifficulty) {
                case "Easy":
                    difficulty = 30;
                    break;
                case "Medium":
                    difficulty = 40;
                    break;
                case "Hard":
                    difficulty = 50;
                    break;
                default:
                    difficulty = 50;
                    break;
            }
        }
        int[] x = new int[Sudoku.size * Sudoku.size];
        for (int i = 0; i < Sudoku.size * Sudoku.size; i++) {
            x[i] = i;
        }
        for (int i = 0; i < difficulty; i++) {
            int randomNum = random.nextInt(Sudoku.size * Sudoku.size - i) + i;
            int temp = x[i];
            x[i] = x[randomNum];
            x[randomNum] = temp;
            sudokuNum[x[i]] = 0;
        }
    }

    /**
     * @param difficulty the difficulty of sudoku.
     *
     * Setter for difficulty
     */
    protected void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }

    /**
     * Return whether random choose all 81 positions
     *
     * @param position random choose possible number for this position
     * @return whether random choose all 81 positions
     */
    private boolean randomChoose(int position) {
        if (position == Sudoku.size * Sudoku.size) {
            return true;
        } else if (sudokuNum[position] != 0) {
            return randomChoose(position + 1);
        } else {
            int[] randOrder = new int[Sudoku.size];
            for (int val = 0; val < Sudoku.size; val++) {
                randOrder[val] = val + 1;
            }
            for (int val = 0; val < Sudoku.size; val++) {
                int rand = random.nextInt(Sudoku.size);
                int tmp = randOrder[rand];
                randOrder[rand] = randOrder[val];
                randOrder[val] = tmp;
            }

            for (int val = 0; val < Sudoku.size; val++) {
                if (isLegal(position, randOrder[val])) {
                    sudokuNum[position] = randOrder[val];
                    if (randomChoose(position + 1)) {
                        return true;
                    }
                }
            }
        }
        sudokuNum[position] = 0;
        return false;
    }

    /**
     * Return whether the value of position is legal.
     *
     * @param position the position want to check
     * @param value check the value whether legal or not
     * @return whether the value of position is legal
     */
    private boolean isLegal(int position, int value) {
        int row = position / Sudoku.size;
        int col = position % Sudoku.size;
        int xOff = row / 3 * 3;
        int yOff = col / 3 * 3;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (value == sudokuNum[(xOff + x) * 9 + yOff + y]) {
                    return false;
                }
            }
        }
        for (int val = 0; val < Sudoku.size; val++) {
            if (value == sudokuNum[row * Sudoku.size + val]) {
                return false;
            }
        }
        for (int val = 0; val < Sudoku.size; val++) {
            if (value == sudokuNum[val * Sudoku.size + col]) {
                return false;
            }
        }

        return true;
    }


    /**
     * Clear all the number the player write
     */
    void clear() {
        for (int i = 0; i < Sudoku.size; i++) {
            for (int j = 0; j < Sudoku.size; j++) {
                if (sudoku.getTile(i, j).getId() > 100) {
                    sudoku.writeNum(i, j, 0);
                }
            }
        }
    }

    /**
     * Set move to be the number the player want.
     *
     * @param move the next move
     */
    void setMove(int move) {
        this.move = move;
    }

    /**
     * Undo to the previous move.
     */
    void undo() {
        if (!undoList.isEmpty()) {
            int undoPosition = undoList.remove(undoList.size() - 1);
            sudoku.writeNum(undoPosition / Sudoku.size, undoPosition % Sudoku.size, 0);
        }
    }
}
