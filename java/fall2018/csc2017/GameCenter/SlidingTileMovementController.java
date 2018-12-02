package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

/**
 * The movement controller of game.
 */
public class SlidingTileMovementController implements Serializable {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The constructor of SlidingTileMovementController.
     */
    SlidingTileMovementController() {
    }

    /**
     * Set a SlidingTileMovementController.
     *
     * @param boardManager The board manager that is being set.
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process a tapping movement.
     *
     * @param context  The context.
     * @param position The position that is tapped.
     */
    void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                Intent tmp = new Intent(context, YouWinActivity.class);
                tmp.putExtra("gameType", "SlidingTile");
                tmp.putExtra("slidingTileBoardManager", boardManager);
                saveToFile(boardManager.getUserName() + "sliding_tmp.ser", context);
                context.startActivity(tmp);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Save the slidingTile manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName, Context context) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));

            outputStream.writeObject(boardManager);

            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
