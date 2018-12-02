package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

/**
 * The Login Activity.
 */
public class LoginActivity extends AppCompatActivity implements Serializable {

    /**
     * get the singleton accountManager.
     */
    private AccountManager accountManager;

    /**
     * generate buttons when createBooms this activity.
     *
     * @param savedInstanceState the savedInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountManager = new AccountManager(this);
        setupIsLoginButtonListener();
        setupHomeSignUpButtonListener();
    }

    /**
     * Create button to login account.
     */
    @SuppressLint("SetTextI18n")
    private void setupIsLoginButtonListener() {
        Button isLoginButton = findViewById(R.id.login_button);
        isLoginButton.setOnClickListener((v) -> {
            EditText username = findViewById(R.id.username);
            EditText password = findViewById(R.id.password);
            TextView canYouLogin = findViewById(R.id.message_box);
            String username_string = username.getText().toString();
            String password_string = password.getText().toString();

            if (!accountManager.checkUsername(username_string)) {
                canYouLogin.setText("Please go back the home page and sign up!");
            } else {
                if (!accountManager.checkPassword(username_string, password_string)) {
                    canYouLogin.setText("Wrong password!");
                } else {
                    canYouLogin.setText("Welcome back!");
                    accountManager.login(username_string);
                    Intent tmp = new Intent(this, GameCenterActivity.class);
                    tmp.putExtra("userName",username_string);
                    startActivity(tmp);
                }
            }
        });
    }
    /**
     * Create Button for signUp.
     */
    private void setupHomeSignUpButtonListener() {
        Button homeSignUpButton = findViewById(R.id.home_sign_up);
        homeSignUpButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, SignUpActivity.class);
            startActivity(tmp);
        });
    }
}
