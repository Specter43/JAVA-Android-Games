package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Activity of choosing the game score.
 */
public class ChooseGameScoreActivity extends AppCompatActivity {

    /**
     * The creator for choose game score activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_score);

        addSTButtonListener();
        addSDButtonListener();
        addMGButtonListener();
    }

    /**
     * Add MG button for mine game scoreboard
     */
    private void addMGButtonListener() {
        Button mgButton = findViewById(R.id.minescore);
        mgButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, DetailScoreBoardActivity.class);
            tmp.putExtra("gameTypeWantedToSee", "Mine");
            startActivity(tmp);
        });
    }

    /**
     * Add Sudoku scoreboard button
     */
    private void addSDButtonListener() {
        Button sdButton = findViewById(R.id.sudoukuscore);
        sdButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, DetailScoreBoardActivity.class);
            tmp.putExtra("gameTypeWantedToSee", "Sudoku");
            startActivity(tmp);
        });
    }

    /**
     * Add Sliding tile scoreboard button
     */
    private void addSTButtonListener() {
        Button stButton = findViewById(R.id.slidingscore);
        stButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, DetailScoreBoardActivity.class);
            tmp.putExtra("gameTypeWantedToSee", "SlidingTile");
            startActivity(tmp);
        });
    }

    /**
     * Resume this activity
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Pause this activity
     */
    @Override
    protected void onPause() {
        super.onPause();
    }
}
