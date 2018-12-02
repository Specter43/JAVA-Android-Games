package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
 * The GameActivity for Mine.
 */
public class MineGameActivity extends AppCompatActivity implements Observer, Serializable {
    /**
     * The mine game manager.
     */
    MineManager mineManager;
    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;
    /**
     * The GestureDetectGridView of this game.
     */
    private MineGestureDetectGridView gridView;
    /**
     * The width and height of column.
     */
    private int columnWidth, columnHeight;

    /**
     * The timer.
     */
    private transient Timer timer = new Timer();

    /**
     * The username.
     */
    private String username;

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
     * The default creator of mine game activity.
     *
     * @param savedInstanceState the saved instance state of game activity.
     */
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        username = intent.getStringExtra("UserName");
        if (Objects.requireNonNull(intent.getExtras()).getBoolean("load")){
            username = intent.getStringExtra("minename");
            loadFromFile(username + "mine_tmp.ser");
            mineManager.setTimer(timer);
        }
        String level = intent.getStringExtra("level");
        if (mineManager == null) {
            mineManager = new MineManager(this, username, level);
        }
        createTileButtons(this);
        setContentView(R.layout.activity_mine_game);
        addQuitButtonListener();

        gridView = findViewById(R.id.minegrid);
        gridView.setNumColumns(MineBoard.getSize());
        gridView.setMineManager(mineManager);
        mineManager.getMineBoard().addObserver(MineGameActivity.this);
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

                        columnWidth = displayWidth / MineBoard.getSize();
                        columnHeight = displayHeight / MineBoard.getSize();

                        display();
                    }
                });
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Rules:")
                .setMessage("For a number tile, the number on it means the boom tiles " +
                        "number hidden in its surrounding 8 tiles. \n\n" +
                        "Tap only when you consider that tile is not a boom. \n\n" +
                        "Double tap to flag the tile. \n\n" +
                        "You will win after you flagged all the boom tiles! \n\n" +
                        "--Good luck!")
                .setPositiveButton("Let the show start", null)
                .create()
                .show();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        tileButtons = new ArrayList<>();
        for (int row = 0; row < MineBoard.getSize(); row++) {
            for (int col = 0; col < MineBoard.getSize(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(mineManager.getMineTiles().get(row * MineBoard.getSize() +
                        col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        MineBoard mineBoard = mineManager.getMineBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / MineBoard.getSize();
            int col = nextPos % MineBoard.getSize();
            b.setBackgroundResource(mineBoard.getMineTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * The listener of quit button.
     */
    private void addQuitButtonListener() {
        Button mineQuitButton = findViewById(R.id.quit);
        mineQuitButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, GameCenterActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mineManager.addTime(mineManager.getScorer().getTimeScore());
        mineManager.getTimer().cancel();
        saveToFile(username + "mine_tmp.ser");
    }

    /**
     * Save the mine manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(mineManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Update the screen view.
     *
     * @param o   the observable object that need to be updated.
     * @param arg the object that is required for the updating.
     */
    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Load the slidingTile manager from fileName.
     *
     */
    private void loadFromFile(String filename) {

        try {
            InputStream inputStream = this.openFileInput(filename);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                mineManager = (MineManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Mine game activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Mine game activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Mine game activity", "File contained unexpected data type: " + e.toString());
        }
    }
}
