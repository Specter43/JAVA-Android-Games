package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The sign up activity.
 */
public class SignUpActivity extends AppCompatActivity implements Serializable {
    /**
     * The display for check username and password.
     */
    private TextView meg_box;

    /**
     * The accountManager.
     */
    private AccountManager accountManager;

    /**
     * The creator of sign up activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        accountManager = new AccountManager(this);
        setupCreateUserButtonListener();
    }

    /**
     * Set up the createBooms user button.
     */
    @SuppressLint("SetTextI18n")
    private void setupCreateUserButtonListener() {
        Button createUserButton = findViewById(R.id.create_user);
        createUserButton.setOnClickListener((v) -> {
            meg_box = findViewById(R.id.suc_or_not);
            EditText nameWantToHave = findViewById(R.id.create_name);
            EditText password = findViewById(R.id.new_pw);
            EditText reEnterPassword = findViewById(R.id.re_pw);
            String nameWantToHave_string = nameWantToHave.getText().toString();
            String password_string = password.getText().toString();
            String reEnterPassword_string = reEnterPassword.getText().toString();
            if (checkType(nameWantToHave_string)) {
                meg_box.setText("This name is invalid! The first character shouldn't " +
                        "be number and this name shouldn't contain other things except " +
                        "numbers and characters Please type again!");
            } else if (accountManager.checkUsername(nameWantToHave_string)) {
                meg_box.setText("This name is used!");
            } else {
                helper_check_password_and_sign_up(nameWantToHave_string,
                        password_string, reEnterPassword_string);
            }

        });
    }

    /**
     * helper function for check username and password type.
     *
     * @param string: the username or password
     * @return can the username or password be used
     */
    private boolean checkType(String string) {
        boolean includeNumOrLetter = false;
        boolean includeOther = false;
        boolean firstCharNum = false;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(0) >= '0' && string.charAt(0) <= '9') {
                firstCharNum = true;
            }
            char character = string.charAt(i);
            if (character >= '0' && character <= '9') {
                includeNumOrLetter = true;
            } else if ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z')) {
                includeNumOrLetter = true;
            } else {
                includeOther = true;
            }
        }
        return firstCharNum || includeOther || !includeNumOrLetter;
    }

    /**
     * helper function for check passwords same and sign up.
     *
     * @param password  the password
     * @param password2 re-enter password
     * @param username  the username
     */
    @SuppressLint("SetTextI18n")
    private void helper_check_password_and_sign_up(String username, String password,
                                                   String password2) {
        if (!password.equals(password2)) {
            meg_box.setText("Different password!");
        } else {
            if (checkType(password)) {
                meg_box.setText("This password is invalid! The first character shouldn't " +
                        "be number and this name shouldn't contain other things except " +
                        "numbers and characters Please type again!");
            } else {
                meg_box.setText("Sign up successfully! Welcome!");
                accountManager.setUp(username, password);
                Intent tmp = new Intent(this, GameCenterActivity.class);
                startActivity(tmp);
            }
        }
    }

    /**
     * Save the user account to fileName.
     *
     * @param fileName the save file which contains the dictionary of username and password
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
