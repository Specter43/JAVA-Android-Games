package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Activity used to show "you win".
 */
public class YouWinActivity extends AppCompatActivity implements Serializable {

    /**
     * The detail score board.
     */
    private DetailScoreBoard detailScoreBoard;

    /**
     * The game type.
     */
    private String gameType;

    /**
     * The Creator for you win activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_win);
        TextView scoreBox = findViewById(R.id.scoreViewData);
        Intent intent = getIntent();
        gameType = intent.getStringExtra("gameType");
        SudokuBoardManager sudokuBoardManager;
        MineManager mineManager;
        BoardManager boardManager;

        switch (gameType){
            case "SlidingTile":
                boardManager = (BoardManager)
                        Objects.requireNonNull(intent.getExtras()).get("slidingTileBoardManager");
                scoreBox.setText("Your Score: " +
                        (Integer.toString(Objects.requireNonNull(boardManager).getScore())));
                break;
            case "Mine":
                mineManager = (MineManager)
                        Objects.requireNonNull(intent.getExtras()).get("mineManager");
                TextView youWinView = findViewById(R.id.finishView);
                if (Objects.requireNonNull(mineManager).puzzleSolved()) {
                    youWinView.setText("Victory!");
                }
                else {
                    youWinView.setText("Failed");
                }
                scoreBox.setText("Your Score: " + (Integer.toString(mineManager.getScore())) +
                        "\n\r" + "Time: "
                        + (Integer.toString(mineManager.getTime())) + " Seconds");
                break;
            case "Sudoku":
                sudokuBoardManager =
                        (SudokuBoardManager)Objects.requireNonNull(intent.getExtras()).
                                get("sudokuGameBoard");
                scoreBox.setText("Your Score: " +
                        (Integer.toString(Objects.requireNonNull(sudokuBoardManager).getScore())) +
                        "\n\r" + "Time: "
                        + (Integer.toString(sudokuBoardManager.getTime())) + " Seconds");
                break;
        }
        String filename = gameType + "DetailScoreBoard.ser";
        loadFromFile(filename);
        if (detailScoreBoard == null){
            detailScoreBoard = new DetailScoreBoard(gameType, this);
        }
        detailScoreBoard.setContext(this);

        detailScoreBoard.collectScoreLevel();
        detailScoreBoard.createSortedList();
        detailScoreBoard.modifyEasyTopOne();
        detailScoreBoard.modifyMediumTopOne();
        detailScoreBoard.modifyHardTopOne();

        saveToFile(filename);
        setUpSeeScoreButtonListener();
        setUpBackHButtonListener();
        setUpPlayAgainButtonListener();
    }

    /**
     * Load the user account from fileName.
     *
     * @param fileName the save file which contains the dictionary of username and password
     */
    void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.detailScoreBoard = (DetailScoreBoard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("You win activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("You win activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("You win activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }

    /**
     * Save the user account to fileName.
     *
     * @param fileName the save file which contains the dictionary of username and password
     */
    private void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(this.detailScoreBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Set up the "see score" button.
     */
    private void setUpSeeScoreButtonListener() {
        Button seeScoreButton = findViewById(R.id.seeScoreButton);

        seeScoreButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, ChooseGameScoreActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Set up the "Back" button.
     */
    private void setUpBackHButtonListener() {
        Button backToHomeButton = findViewById(R.id.backHomeButton);

        backToHomeButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, GameCenterActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Set up the "Play again" button.
     */
    private void setUpPlayAgainButtonListener() {
        Button backToHomeButton = findViewById(R.id.playAgainButton);

        backToHomeButton.setOnClickListener((v) -> {
            switch (gameType){
                case "SlidingTile":
                    Intent SlidingTileTmp = new Intent(this, SettingActivity.class);
                    startActivity(SlidingTileTmp);
                    break;
                case "Mine":
                    Intent MineTmp = new Intent(this, MineSettingActivity.class);
                    startActivity(MineTmp);
                    break;
                case "Sudoku":
                    Intent SudokuTmp = new Intent(this, SudokuSettingActivity.class);
                    startActivity(SudokuTmp);
                    break;
            }
        });
    }
}
