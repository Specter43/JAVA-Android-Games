package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The setting activity.
 */
public class SettingActivity extends AppCompatActivity implements Serializable {
    /**
     * The slidingTile.
     */
    protected SlidingTile slidingTile;
    /**
     * The boardManager.
     */
    private BoardManager boardManager;
    /**
     * Track difficulty selected
     */
    protected int difficulty;
    /**
     * Switch for undo method.
     */
    Switch undoSwitch;
    /**
     * Limited undo or not.
     */
    boolean undoLimited;

    /**
     * The level.
     */
    private int level;

    /**
     * The creator of setting activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingtile_setting);

        Spinner spinner = findViewById(R.id.spinner);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("4x4");
        categories.add("3x3");
        categories.add("5x5");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Set difficulty when selected item.
             *
             * @param arg0 the first item.
             * @param arg1 the second item.
             * @param arg2 the third item.
             * @param arg3 the forth item.
             */
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                String item = (String) arg0.getSelectedItem();
                Toast.makeText(getBaseContext(),
                        "You have selected size : " + item, Toast.LENGTH_SHORT).show();
                switch (item) {
                    case "3x3":
                        difficulty = 3;
                        level = 3;
                        break;
                    case "4x4":
                        difficulty = 4;
                        level = 4;
                        break;
                    default:
                        difficulty = 5;
                        level = 5;
                        break;
                }
            }

            /**
             * When nothing is selected.
             *
             * @param arg0 First argument.
             */
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        addConfirmButtonListener();
        undoSwitch = findViewById(R.id.undoSwitch);
        undoSwitch.setChecked(false);
        undoSwitch.setTextOn("Unlimited");
        undoSwitch.setTextOff("limited");
        undoSwitch.setOnClickListener(view -> {
            undoLimited = undoSwitch.isChecked();
            String statusSwitch;
            if (undoSwitch.isChecked()) {
                statusSwitch = undoSwitch.getTextOn().toString();
            } else {
                statusSwitch = undoSwitch.getTextOff().toString();
            }
            Toast.makeText(getApplicationContext(), "Undo Mode :" +
                    statusSwitch, Toast.LENGTH_LONG).show();
        });
    }


    /**
     * Read the temporary slidingTile from disk.
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
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        boardManager = new BoardManager(this, level, false);
        tmp.putExtra("slidingTileBoardManager", boardManager);
        tmp.putExtra("slidingTile", slidingTile);
        tmp.putExtra("undo", undoLimited);
        startActivity(tmp);
        finish();
    }

    /**
     * Activate the confirm button.
     */
    private void addConfirmButtonListener() {
        Button confirmButton = findViewById(R.id.ConfirmButton);
        confirmButton.setOnClickListener(view -> {
            boardManager = null;
            switchToGame();
            System.out.println(boardManager.getSlidingTile().getTileList().length);
            System.out.println("still numbers mode");
        });
    }
}