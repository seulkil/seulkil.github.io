package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bookkeeping.ongoing.ongoing_bookkeeping.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Seulki_Lee on 5/4/2015.
 */
public class RegisterActivity extends Activity{
    private EditText userId;
    private EditText password;
    private EditText name;
    private CheckBox isParent;
    private CheckBox isChild;
    private TextView error;
    private boolean atleastCheck = false;
    private boolean child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userId = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        error = (TextView) findViewById(R.id.error);
        isParent = (CheckBox) findViewById(R.id.isYes);
        isParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = false;
                atleastCheck = true;
            }
        });

        isChild = (CheckBox) findViewById(R.id.isNo);
        isChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = true;
                atleastCheck = true;
            }
        });
    }
    public void register(final View v){
        if(!isEmailValid(userId.getText().toString())){
            error.setText("Should be an email Address.");
        }
        if(userId.getText().length() == 0 || password.getText().length() == 0
                || !atleastCheck)
            return;

        v.setEnabled(false);

        final ParseUser user = new ParseUser();
        user.setUsername(userId.getText().toString());
        user.setPassword(password.getText().toString());
        user.put("isChild", child);
        error.setText("");




        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    if (child) {
                        final Child child = new Child();
                        child.setId(userId.getText().toString());
                        child.setPassword(password.getText().toString());
                        child.setIsChild(true);
                        child.setName(name.getText().toString());
                        System.out.println("save");
                        child.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Saved successfully.
                                    String id = child.getObjectId();
                                    user.put("childObjectId", id);
                                    user.saveInBackground();
                                } else {
                                    // The save failed.
                                }
                            }
                        });

                    } else if (!child) {
                        final Parent parent = new Parent();
                        parent.setId(userId.getText().toString());
                        parent.setPassword(password.getText().toString());
                        parent.setIsChild(true);
                        parent.setName(name.getText().toString());
                        parent.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    String id = parent.getObjectId();
                                    user.put("parentObjectId", id);
                                    user.saveInBackground();
                                } else {
                                    Log.d("this", "fail");
                                }
                            }
                        });

                        user.saveInBackground();
                    }
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (!atleastCheck) {
                        error.setText("Please, Check one of option. Are you Parent?");
                    }
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    switch (e.getCode()) {
                        case ParseException.USERNAME_TAKEN:
                            error.setText("Sorry, this username has already been taken.");
                            break;
                        case ParseException.USERNAME_MISSING:
                            error.setText("Sorry, you must supply a username to register.");
                            break;
                        case ParseException.PASSWORD_MISSING:
                            error.setText("Sorry, you must supply a password to register.");
                            break;
                        default:
                            error.setText(e.getLocalizedMessage());
                    }
                    v.setEnabled(true);
                }
            }
        });
    }
    public void showRegistration(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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
    public void showLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
