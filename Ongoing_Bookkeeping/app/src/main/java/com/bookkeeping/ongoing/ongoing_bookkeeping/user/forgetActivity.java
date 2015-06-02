package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.bookkeeping.ongoing.ongoing_bookkeeping.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Seulki_Lee on 5/7/2015.
 */
public class forgetActivity extends Activity{
    private EditText userName;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        userName = (EditText) findViewById(R.id.id);
        error = (TextView) findViewById(R.id.error);


    }

    public void sendEmail(final View v){
        v.setEnabled(false);
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereEqualTo("username", userName.getText().toString());
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {

                if (!isEmailValid(userName.getText().toString())) {
                    error.setText("This is not a Valid Id. Should be Email Address");
                }

                if (objects != null) {

                    if(!objects.get(0).getBoolean("isChild")){
                        ParseQuery<Parent> query = ParseQuery.getQuery(Parent.class);
                        query.getInBackground(objects.get(0).getString("parentObjectId"), new GetCallback<Parent>() {
                            public void done(Parent object, ParseException e) {
                                if (e == null) {
                                    sendingEmail(object.getId(),object.getPassword());

                                    // Toast.makeText(forgetActivity.this, "password  : " + object.get("Password"), Toast.LENGTH_LONG).show();
                                } else {
                                    Log.d("view", "Error: " + e.getMessage());
                                }
                            }
                        });

                    }else{
                        ParseQuery<Child> query = ParseQuery.getQuery("Child");
                        query.getInBackground(objects.get(0).getString("childObjectId"), new GetCallback<Child>() {
                            public void done(Child object, ParseException e) {
                                if (e == null) {

                                    sendingEmail(object.getId(), object.getPassword());
                                    // Toast.makeText(forgetActivity.this, "password  : " +  object.get("Password"), Toast.LENGTH_LONG).show();
                                } else {
                                    Log.d("wrong", " wrong");
                                }
                            }
                        });
                    }
                   // String password = objects.get(0).getString("password");
                    //sendingEmail(userName.getText().toString(), password);

                    Intent intent = new Intent(forgetActivity.this, LoginActivity.class);

                    startActivity(intent);
                    finish();

                } else {
                    if(objects == null){
                        error.setText("Sorry, No Id.");
                    }
                    switch (e.getCode()) {
                        case ParseException.USERNAME_MISSING:
                            error.setText("Sorry, you must write a username to register.");
                            break;
                        case ParseException.OBJECT_NOT_FOUND:
                            error.setText("Sorry, No Id.");
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

    public void goBack(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void sendingEmail(String userName, String passwords) {

//        Toast.makeText(forgetActivity.this, userName + password, Toast.LENGTH_LONG).show();
        String title= "Ongoing Bookkeeping Service, Password";
        String messages = "Hi, You request to find your password. \n This is your Password\n" + passwords
        + "\nThank you for using my app.\nOnging.";
        try {
            GMailSender sender = new GMailSender("seulkile@gmail.com", "");
            sender.sendMail(title,
                    messages,
                    "seulkile@gmail.com",
                    userName);
            Toast.makeText(forgetActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }



//
//        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:seulkile@gmail.com?subject=" +
//                Uri.encode("my subject") + "&body=" +
//                "My big long body with spaces, new lines, and all sorts of invalid URI characters"));
//        startActivity(intent);


//       Intent i = new Intent(Intent.ACTION_SEND);
  //      i.setData(Uri.parse("mailto:"));

//
//        i.putExtra(Intent.EXTRA_EMAIL, userName);
//        i.putExtra(Intent.EXTRA_SUBJECT, "Ongoing Bookkeeping Service, Password");
//        i.putExtra(Intent.EXTRA_TEXT, "Hi, You request to find your password. \n This is your Password\n" + passwords
//                + "\nThank you for using my app.\nOnging.");
//
//        i.setType("message/rfc822");
//        //i.setType("text/plain");
//
//        try {
//            if (i.resolveActivity(getPackageManager()) != null) {
//                startActivity(i);
//            }
//            startActivity(Intent.createChooser(i, "Send mail..."));
//            finish();
////            Log.i("Finished sendind", " ");
//
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(forgetActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//        }


    }

}
