package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The setting activity for Mine game.
 */
public class MineSettingActivity extends AppCompatActivity implements Serializable {
    /**
     * The user's name.
     */
    private String userName;

    /**
     * The level.
     */
    private String level;

    /**
     * The default creator for Mine Setting Activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_setting);
        Spinner boomDifficulty = findViewById(R.id.Difficulty_numBooms);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Easy");
        categories.add("Medium");
        categories.add("Hard");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boomDifficulty.setAdapter(dataAdapter);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        boomDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Perform actions when an item is selected.
             *
             * @param arg0 the first argument.
             * @param arg1 the second argument.
             * @param arg2 the third argument.
             * @param arg3 the forth argument.
             */
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String item = (String) arg0.getSelectedItem();
                Toast.makeText(getBaseContext(),
                        "You have selected difficulty : " + item, Toast.LENGTH_SHORT).show();
                level = item;
            }

            /**
             * default method when nothing selected.
             *
             * @param arg0 the argument view.
             */
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        addMineConfirmButtonListener();
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Dispatch onStop() to fragments.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Switch to the MineGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, MineGameActivity.class);
        tmp.putExtra("UserName", userName);
        tmp.putExtra("level", level);
        startActivity(tmp);
        finish();
    }

    /**
     * Activate the confirm button.
     */
    private void addMineConfirmButtonListener() {
        Button mineConfirmButton = findViewById(R.id.MineConfirmButton);
        mineConfirmButton.setOnClickListener(v -> switchToGame());
    }
}
