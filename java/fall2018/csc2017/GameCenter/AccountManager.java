package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * a singleton Manage all users' username and password.
 */
public class AccountManager implements Serializable {
    /**
     * The serialVersionUID.
     */
    public static final long serialVersionUID = -1529656476951534478L;
    /**
     * a Map of username and password.
     */
    private Map<String, String> map = new HashMap<>();

    /**
     * Current user's user name.
     */
    private String userName = null;

    /**
     * The context used to connect to activity.
     */
    transient Context context;

    /**
     * The save file which contains the dictionary of username and password.
     */
    private static final String SAVE_FILENAME = "save_file.ser";

    /**
     * Init AccountManager.
     *
     * @param context the context.
     */
    AccountManager(Context context) {
        this.context = context;
        loadFromFile();
        loadName();
    }

    /**
     * record userName and password.
     *
     * @param userName the name the user input
     * @param password the password the user input
     */
    public void setUp(String userName, String password) {
        this.userName = userName;
        saveName();
        map.put(userName, password);
        saveToFile();
    }

    /**
     * login current player.
     *
     * @param userName current player
     */
    public void login(String userName) {
        this.userName = userName;
        saveName();
    }

    /**
     * check does username exist in the recording map.
     *
     * @param userName the name the user input
     * @return True iff username is in the file
     */
    boolean checkUsername(String userName) {
        loadFromFile();
        return map.containsKey(userName);
    }

    /**
     * check password with given username and password.
     *
     * @param userName the name the user input
     * @param password the password the user input
     * @return True iff the password is correct
     */
    boolean checkPassword(String userName, String password) {
        loadFromFile();
        String result = map.get(userName);
        return result.equals(password);
    }

    /**
     * Load the user account from fileName.
     */
    private void loadFromFile() {
        try {
            InputStream inputStream = this.context.openFileInput(AccountManager.SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                map = cast(input.readObject());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Account manager", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Account manager", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Account manager", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Casting.
     *
     * @param obj the object needs casting.
     * @param <T> the type to cast to.
     * @return the casted object.
     */
    @SuppressWarnings("unchecked")
    private static  <T> T cast(Object obj){
        return (T)obj;
    }

    /**
     * Return the account username and password
     *
     * @return the account username and password
     */
    Map<String, String> getMap(){
        return map;
    }

    /**
     * Save the user account to fileName.
     */
    private void saveToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.context.openFileOutput(AccountManager.SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(map);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the user account from fileName.
     */
    private void loadName() {
        try {
            InputStream inputStream = this.context.openFileInput("currentPlayer.ser");
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.userName = (String) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }

    }

    /**
     * Save the user account to fileName.
     */
    private void saveName() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.context.openFileOutput("currentPlayer.ser", MODE_PRIVATE));
            outputStream.writeObject(userName);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Getter for user name.
     *
     * @return the user name.
     */
    String getUserName() {
        return userName;
    }

    /**
     * @param userName the name of user.
     *
     * Setter for user name.
     */
    void setUserName(String userName){
        this.userName = userName;
    }
}
