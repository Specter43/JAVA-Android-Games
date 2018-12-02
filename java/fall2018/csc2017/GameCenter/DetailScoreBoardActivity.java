package fall2018.csc2017.GameCenter;

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
import java.util.List;

/**
 * The detail score board activity.
 */
public class DetailScoreBoardActivity extends AppCompatActivity implements Serializable {

    /**
     * The boardManager that is used in that round.
     */
    private DetailScoreBoard detailScoreBoard;

    /**
     * The creator for detail score board activity.
     *
     * @param savedInstanceState the saved instance State.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_score_board);
        Intent intent = getIntent();
        String gameType = intent.getStringExtra("gameTypeWantedToSee");

        boolean played = true;
        String filename = gameType + "DetailScoreBoard.ser";
        loadFromFile(filename);
        if (detailScoreBoard == null){
            detailScoreBoard = new DetailScoreBoard(gameType, this);
            played =false;
        }
        detailScoreBoard.setContext(this);

        if (!played) {
            detailScoreBoard.collectScoreLevel();
            detailScoreBoard.createSortedList();
            detailScoreBoard.modifyEasyTopOne();
            detailScoreBoard.modifyMediumTopOne();
            detailScoreBoard.modifyHardTopOne();
        }

        TextView gameView = findViewById(R.id.GameView);
        gameView.setText(gameType);
        setTopOnes(detailScoreBoard);
        setModeData(detailScoreBoard);

        addScoreQuitButtonsListener();
        saveToFile(filename);
        detailScoreBoard.destroyAllManager();
        detailScoreBoard = null;
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
            Log.e("detail score board activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("detail score board activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("detail score board activity", "File contained unexpected data type: "
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
     * Set the top ranks.
     *
     * @param scoreBoard the scoreboard needs to be set.
     */
    private void setTopOnes(DetailScoreBoard scoreBoard){
        TextView easyTopOne = findViewById(R.id.easyTopOne);
        TextView mediumTopOne = findViewById(R.id.mediumTopOne);
        TextView hardTopOne = findViewById(R.id.hardTopOne);

        easyTopOne.setText(scoreBoard.getEasyTopOne());
        mediumTopOne.setText(scoreBoard.getMediumTopOne());
        hardTopOne.setText(scoreBoard.getHardTopOne());
    }

    /**
     * Set the mode data.
     *
     * @param scoreBoard the scoreboard needs to be set.
     */
    private void setModeData(DetailScoreBoard scoreBoard){
        TextView easyData = findViewById(R.id.easyTextView);
        TextView mediumData = findViewById(R.id.mediumTextView);
        TextView hardData = findViewById(R.id.hardTextView);

        easyData.setText(othersToString(scoreBoard.getEasySortedList()));
        mediumData.setText(othersToString(scoreBoard.getMediumSortedList()));
        hardData.setText(othersToString(scoreBoard.getHardSortedList()));
    }

    /**
     * Convert those that are not the first to string.
     *
     * @param sortedOthers the sorted other places.
     * @return the string representation of other places.
     */
    private String othersToString(List<String> sortedOthers){
        StringBuilder result = new StringBuilder();
        if (sortedOthers.size() > 0) {
            for (int i = 0; i < sortedOthers.size(); i++) {
                result.append(sortedOthers.get(i));
                result.append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Activate the buttons for Quit.
     */
    private void addScoreQuitButtonsListener() {
        Button quitButton = findViewById(R.id.scoreBoardQuitbutton);
        quitButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, GameCenterActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Dispatch onStop() to fragments.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
