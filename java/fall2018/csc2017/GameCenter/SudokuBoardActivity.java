package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

/**
 * The sudoku board game activity.
 */
public class SudokuBoardActivity extends AppCompatActivity implements Observer, Serializable {

    /**
     * The sudoku board manager.
     */
    private SudokuBoardManager sudokuBoardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The GestureDetectGridView of this game.
     */
    private SudokuGestureDetectGridView gridView;

    /**
     * The width and height of column.
     */
    private int columnWidth, columnHeight;

    /**
     * The move of sudoku game.
     */
    private int move;

    /**
     * The timer.
     */
    private transient Timer timer = new Timer();

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    @SuppressLint("SetTextI18n")
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * The creator of sudoku board activity.
     *
     * @param savedInstanceState the savedInstanceState.
     */
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent tmp = getIntent();
        String sudokuDifficulty =
                Objects.requireNonNull(tmp.getExtras()).getString("sudokuDifficulty");
        if (sudokuBoardManager == null) {
            sudokuBoardManager = new SudokuBoardManager(this, sudokuDifficulty);
        }
        if (tmp.getExtras().getBoolean("load")) {
            String username = tmp.getStringExtra("sudokuusername");
            loadFromFile(username + "sudoku_tmp.ser");
            sudokuBoardManager.setTimer(timer);
        }

        createTileButtons(this);
        setContentView(R.layout.activity_sudoku_board);
        addOneButtonListener();
        addTwoButtonListener();
        addThreeButtonListener();
        addFourButtonListener();
        addFiveButtonListener();
        addSixButtonListener();
        addSevenButtonListener();
        addEightButtonListener();
        addNineButtonListener();
        addClearButtonListener();
        addEraserButtonListener();
        addSudokuQuitButtonsListener();
        addSudokuUndoButtonsListener();

        gridView = findViewById(R.id.sudokugrid);
        gridView.setNumColumns(Sudoku.size);
        gridView.setSudokuBoardManager(sudokuBoardManager);
        sudokuBoardManager.getSudoku().addObserver(this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    /**
                     * On global layout.
                     */
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Sudoku.size;
                        columnHeight = displayHeight / Sudoku.size;

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Sudoku sudoku = sudokuBoardManager.getSudoku();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Sudoku.size; row++) {
            for (int col = 0; col != Sudoku.size; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(sudoku.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Sudoku sudoku = sudokuBoardManager.getSudoku();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Sudoku.size;
            int col = nextPos % Sudoku.size;
            b.setBackgroundResource(sudoku.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        sudokuBoardManager.addTime(sudokuBoardManager.getScorer().getTimeScore());
        sudokuBoardManager.getTimer().cancel();
        saveToFile(sudokuBoardManager.getUserName() + "sudoku_tmp.ser");
    }

    /**
     * Load the sudoku board manager from fileName.
     *
     * @param filename the file's name.
     */
    private void loadFromFile(String filename) {

        try {
            InputStream inputStream = this.openFileInput(filename);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                sudokuBoardManager = (SudokuBoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Sudoku Board activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Sudoku Board activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Sudoku Board activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the sudoku board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(sudokuBoardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Activate the number 9 button.
     */
    private void addNineButtonListener() {
        Button nine = findViewById(R.id.nine);
        nine.setOnClickListener(v -> {
            this.move = 109;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the number 8 button.
     */
    private void addEightButtonListener() {
        Button eight = findViewById(R.id.eight);
        eight.setOnClickListener(v -> {
            this.move = 108;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the number 7 button.
     */
    private void addSevenButtonListener() {
        Button seven = findViewById(R.id.seven);
        seven.setOnClickListener(v -> {
            this.move = 107;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the number 6 button.
     */
    private void addSixButtonListener() {
        Button six = findViewById(R.id.six);
        six.setOnClickListener(v -> {
            this.move = 106;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the number 5 button.
     */
    private void addFiveButtonListener() {
        Button five = findViewById(R.id.five);
        five.setOnClickListener(v -> {
            this.move = 105;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the number 4 button.
     */
    private void addFourButtonListener() {
        Button four = findViewById(R.id.four);
        four.setOnClickListener(v -> {
            this.move = 104;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the number 3 button.
     */
    private void addThreeButtonListener() {
        Button three = findViewById(R.id.three);
        three.setOnClickListener(v -> {
            this.move = 103;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the number 2 button.
     */
    private void addTwoButtonListener() {
        Button two = findViewById(R.id.two);
        two.setOnClickListener(v -> {
            this.move = 102;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the number 1 button.
     */
    private void addOneButtonListener() {
        Button one = findViewById(R.id.one);
        one.setOnClickListener(v -> {
            this.move = 101;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the buttons for eraser.
     */
    private void addEraserButtonListener() {
        Button eraser = findViewById(R.id.eraserButton);
        eraser.setOnClickListener(v -> {
            this.move = 0;
            sudokuBoardManager.setMove(move);
        });
    }

    /**
     * Activate the buttons for clear.
     */
    private void addClearButtonListener() {
        Button clear = findViewById(R.id.clearButton);
        clear.setOnClickListener(v -> sudokuBoardManager.clear());
    }

    /**
     * Activate the buttons for Quit.
     */
    private void addSudokuQuitButtonsListener() {
        Button quitButton = findViewById(R.id.sudokuQuitButton);
        quitButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, GameCenterActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Activate the buttons for undo.
     */
    private void addSudokuUndoButtonsListener() {
        Button undoButton = findViewById(R.id.sudokuUndoButton);
        undoButton.setOnClickListener((v) -> sudokuBoardManager.undo());
    }

    /**
     * Update the sudoku game.
     *
     * @param o   the observable object.
     * @param arg the argument.
     */
    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
