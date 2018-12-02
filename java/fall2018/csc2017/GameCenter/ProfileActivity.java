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
 * The profile Activity.
 */
public class ProfileActivity extends AppCompatActivity {
    /**
     * username information.
     */
    TextView usernameInfo;
    /**
     * password information.
     */
    TextView passwordInfo;
    /**
     * record information.
     */
    TextView recordInfo;

    /**
     * The AccountManager.
     */
    AccountManager accountManager;

    private DetailScoreBoard detailScoreBoard;

    private String userName;

    /**
     * generate information when createBooms this activity.
     *
     * @param savedInstanceState the savedInstanceState.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        accountManager = new AccountManager(this);
        userName = accountManager.getUserName();
        usernameInfo = findViewById(R.id.usernameInfo);
        passwordInfo = findViewById(R.id.passwordInfo);
        recordInfo = findViewById(R.id.recordInfo);

        usernameInfo.setText("Username: " + userName);
        passwordInfo.setText("Password: " + accountManager.getMap().get(userName));
        String record = "Mine Game Highest Score: " + setMine() + "\n" + "\n" +
                "Sudoku Game Highest Score: " + setSudoku() + "\n" + "\n" +
                "SlidingTile Game Highest Score: " + setSlidingTile() + "\n";
        recordInfo.setText(record);
    }

    /**
     * Set the slidingTile score.
     */
    @SuppressLint("SetTextI18n")
    private String setSlidingTile(){
        String gameType = "SlidingTile";
        String highestScore = "No data";
        loadFromFile(gameType + "DetailScoreBoard.ser");
        if (detailScoreBoard != null) {
            int temp = detailScoreBoard.getHighestScoreByUser(userName);
            highestScore = temp + "";
        }
        return highestScore;
    }

    /**
     * Set the mine score.
     */
    @SuppressLint("SetTextI18n")
    private String setMine() {
        String gameType = "Mine";
        String highestScore = "No data";
        loadFromFile(gameType + "DetailScoreBoard.ser");
        if (detailScoreBoard != null) {
            int temp = detailScoreBoard.getHighestScoreByUser(userName);
            highestScore = temp + "";
        }
        return highestScore;
    }

    /**
     * Set the Sudoku score.
     */
    @SuppressLint("SetTextI18n")
    private String setSudoku(){
        String gameType = "Sudoku";
        String highestScore = "No data";
        loadFromFile(gameType + "DetailScoreBoard.ser");
        if (detailScoreBoard != null) {
            int temp = detailScoreBoard.getHighestScoreByUser(userName);
            highestScore = temp + "";
        }
        return highestScore;
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
            Log.e("Profile activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Profile activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Profile activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }

}
