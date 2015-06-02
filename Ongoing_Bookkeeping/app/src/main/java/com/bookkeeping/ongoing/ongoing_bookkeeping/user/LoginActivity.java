package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bookkeeping.ongoing.ongoing_bookkeeping.ChildstartActivity;
import com.bookkeeping.ongoing.ongoing_bookkeeping.R;
import com.bookkeeping.ongoing.ongoing_bookkeeping.StartingActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Seulki_Lee on 5/4/2015.
 */
public class LoginActivity extends Activity {

    private EditText userName;
    private EditText password;
    private TextView error;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        error = (TextView) findViewById(R.id.error);
        button = (Button) findViewById(R.id.login);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        int[] locationInWindow = new int[2];
        button.getLocationInWindow(locationInWindow);

        Log.v("TAG","getLocationInWindow() - "+ locationInWindow[0] + " : " + locationInWindow[1]);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.login, menu);
//        return true;
//    }

    public void signIn(final View v){
        v.setEnabled(false);
        ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(!isEmailValid(userName.getText().toString())){
                    error.setText("This is not a Valid Id. Should be Email Address");
                }

                if (user != null) {
                    if(!user.getBoolean("isChild")) {
                        Intent intent = new Intent(LoginActivity.this, StartingActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, ChildstartActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    switch(e.getCode()){
                        case ParseException.USERNAME_MISSING:
                            error.setText("Sorry, you must write a username to register.");
                            break;
                        case ParseException.PASSWORD_MISSING:
                            error.setText("Sorry, you must write a password to register.");
                            break;
                        case ParseException.OBJECT_NOT_FOUND:
                            error.setText("Sorry, Doesnt Match.");
                            break;
                        default:
                            error.setText(e.getLocalizedMessage());
                            break;
                    }
                    v.setEnabled(true);
                }
            }
        });
    }


    private boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    public void showRegistration(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    public void forget(View view){
        Intent intent = new Intent(this, forgetActivity.class);
        startActivity(intent);
    }
}
