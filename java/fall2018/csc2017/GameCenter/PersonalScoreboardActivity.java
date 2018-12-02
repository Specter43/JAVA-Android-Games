package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * The personal score board activity.
 */
public class PersonalScoreboardActivity extends AppCompatActivity {

    /**
     * The current user name.
     */
    private String userName;

    /**
     * the detailScoreBoard.
     */
    private DetailScoreBoard detailScoreBoard;

    /**
     * Create this activity
     * @param savedInstanceState SavedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_scoreboard);
        AccountManager accountManager = new AccountManager(this);
        this.userName = accountManager.getUserName();
        setUserName();
        setSlidingTile();
        setMine();
        setSudoku();
    }

    /**
     * set the user name
     */
    private void setUserName(){
        TextView nameView = findViewById(R.id.nameData);
        nameView.setText(userName);
    }

    /**
     * Set the slidingTile score.
     */
    @SuppressLint("SetTextI18n")
    private void setSlidingTile(){
        String gameType = "SlidingTile";
        loadFromFile(gameType + "DetailScoreBoard.ser");
        if (detailScoreBoard != null) {
            int highestScore = detailScoreBoard.getHighestScoreByUser(userName);
            TextView soreView = findViewById(R.id.slidingTileHighestScore);
            soreView.setText(Integer.toString(highestScore));
        }
    }

    /**
     * Set the mine score.
     */
    @SuppressLint("SetTextI18n")
    private void setMine() {
        String gameType = "Mine";
        loadFromFile(gameType + "DetailScoreBoard.ser");
        if (detailScoreBoard != null) {
            int highestScore = detailScoreBoard.getHighestScoreByUser(userName);
            TextView soreView = findViewById(R.id.mineHighestScore);
            soreView.setText(Integer.toString(highestScore));
        }
    }

    /**
     * Set the Sudoku score.
     */
    @SuppressLint("SetTextI18n")
    private void setSudoku(){
        String gameType = "Sudoku";
        loadFromFile(gameType + "DetailScoreBoard.ser");
        if (detailScoreBoard != null) {
            int highestScore = detailScoreBoard.getHighestScoreByUser(userName);
            TextView soreView = findViewById(R.id.sudokuHighestScore);
            soreView.setText(Integer.toString(highestScore));
        }
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
                detailScoreBoard = (DetailScoreBoard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Personal score board activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Personal score board activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Personal score board activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }
}
