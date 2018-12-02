package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity implements Serializable {
    /**
     * The sliding tile board manager.
     */
    private BoardManager boardManager;

    /**
     * The mine manager.
     */
    private MineManager mineManager;

    /**
     * The sudoku manager.
     */
    private SudokuBoardManager sudokuBoardManager;

    /**
     * The game type.
     */
    private String gameType;

    /**
     * The player username.
     */
    private String userName;

    /**
     * A temporary save file for sliding tile.
     */
    String slidingFile;
    /**
     * A temporary save file for mine.
     */
    String mineFile;
    /**
     * A temporary save file for sudoku.
     */
    String sudokuFile;


    /**
     * Creator of of starting activity.
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        gameType = intent.getStringExtra("gameType");
        userName = intent.getStringExtra("userName");
        slidingFile = userName + "sliding_tmp.ser";
        mineFile = userName + "mine_tmp.ser";
        sudokuFile = userName + "sudoku_tmp.ser";


        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addScoreboardButtonListener();
        addMyScoreButtonListener();
        addLogoutButtonListener();
        addProfileButtonListener();
        addCenterButtonListener();
    }

    private void addCenterButtonListener() {
        Button gcButton = findViewById(R.id.gamecenterbutton);
        gcButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, GameCenterActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(v -> {
            switch (gameType){
                case "SlidingTile":
                    Intent slidingTile = new Intent(this, SettingActivity.class);
                    slidingTile.putExtra("userName", userName);
                    startActivity(slidingTile);
                    break;
                case "Mine":
                    Intent mine = new Intent(this, MineSettingActivity.class);
                    mine.putExtra("userName", userName);
                    startActivity(mine);
                    break;
                case "Sudoku":
                    Intent sudoku = new Intent(this, SudokuSettingActivity.class);
                    sudoku.putExtra("userName", userName);
                    startActivity(sudoku);
                    break;
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(v -> {
            switch (gameType){
                case "SlidingTile":
                    loadFromFile(slidingFile);
                    if(boardManager == null){
                        Toast.makeText(this, "You haven't play this game before", Toast.LENGTH_SHORT).show();
                    }else if (boardManager.puzzleSolved()){
                        Toast.makeText(this, "You finish your previous game, please start another one", Toast.LENGTH_SHORT).show();
                    }else {
                        makeToastLoadedText();
                        switchToGame();
                    }
                    break;
                case "Mine":
                    loadFromFile(mineFile);
                    if(mineManager == null){
                        Toast.makeText(this, "You haven't play this game before", Toast.LENGTH_SHORT).show();
                    }else if (mineManager.puzzleSolved() || mineManager.getLose()){
                        Toast.makeText(this, "You finish your previous game, please start another one", Toast.LENGTH_SHORT).show();
                    }else {
                        makeToastLoadedText();
                        switchToGame();
                    }
                    break;
                case "Sudoku":
                    loadFromFile(sudokuFile);
                    if(sudokuBoardManager == null){
                        Toast.makeText(this, "You haven't play this game before", Toast.LENGTH_SHORT).show();
                    }else if (sudokuBoardManager.puzzleSolved()){
                        Toast.makeText(this, "You finish your previous game, please start another one", Toast.LENGTH_SHORT).show();
                    }else {
                        makeToastLoadedText();
                        switchToGame();
                    }
                    break;
            }
        });
    }

    /**
     * Activate the scoreboard button.
     */
    private void addScoreboardButtonListener() {
        Button scoreboardButton = findViewById(R.id.scoreButton);
        scoreboardButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, ChooseGameScoreActivity.class);
            tmp.putExtra("userName", userName);
            startActivity(tmp);
        });
    }

    /**
     * Activate the MyScore button.
     */
    private void addMyScoreButtonListener(){
        Button myScoreButton = findViewById(R.id.MyScore);
        myScoreButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, PersonalScoreboardActivity.class);
            tmp.putExtra("userName", userName);
            startActivity(tmp);
        });
    }


    /**
     * Activate the profile button.
     */
    private void addProfileButtonListener() {
        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, ProfileActivity.class);
            tmp.putExtra("userName", userName);
            startActivity(tmp);
        });
    }

    /**
     * Activate the logout button.
     */
    private void addLogoutButtonListener() {
        Button logoutButton = findViewById(R.id.LogoutButton);
        logoutButton.setOnClickListener(view -> {
            boardManager = null;
            sudokuBoardManager = null;
            mineManager = null;
            Intent temp = new Intent(this, LoginActivity.class);
            startActivity(temp);
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary slidingTile from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        switch (gameType){
            case "SlidingTile":
                Intent slidingTile = new Intent(this, GameActivity.class);
                slidingTile.putExtra("load", true);
                slidingTile.putExtra("slidingname", userName);
                startActivity(slidingTile);
                break;
            case "Mine":
                Intent mine = new Intent(this, MineGameActivity.class);
                mine.putExtra("load", true);
                mine.putExtra("minename", userName);
                startActivity(mine);
                break;
            case "Sudoku":
                Intent sudoku = new Intent(this, SudokuBoardActivity.class);
                sudoku.putExtra("sudokuusername", userName);
                sudoku.putExtra("load", true);
                startActivity(sudoku);
                break;
        }
    }

    /**
     * Load manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                switch (gameType){
                    case "SlidingTile":
                        boardManager = (BoardManager) input.readObject();
                        break;
                    case "Mine":
                        mineManager = (MineManager) input.readObject();
                        break;
                    case "Sudoku":
                        sudokuBoardManager = (SudokuBoardManager) input.readObject();
                        break;
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Starting activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Starting activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Starting activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            switch (gameType){
                case "SlidingTile":
                    outputStream.writeObject(boardManager);
                    break;
                case "Mine":
                    outputStream.writeObject(mineManager);
                    break;
                case "Sudoku":
                    outputStream.writeObject(sudokuBoardManager);
                    break;
            }
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
