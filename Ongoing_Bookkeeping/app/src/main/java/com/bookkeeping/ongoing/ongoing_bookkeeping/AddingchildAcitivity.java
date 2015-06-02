package com.bookkeeping.ongoing.ongoing_bookkeeping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Child;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.LoginActivity;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Parent;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Seulki_Lee on 5/4/2015.
 */
public class AddingchildAcitivity extends Activity {
    private EditText kidId;
    private EditText kidName;
    private TextView error;
    private Parent parent;
    RelativeLayout menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addchild);
        kidId = (EditText) findViewById(R.id.userName);
        kidName = (EditText) findViewById(R.id.kidName);
        error = (TextView) findViewById(R.id.error);
    }
//    private boolean isEmailValid(String email) {
//        boolean isValid = false;
//
//        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//        CharSequence inputStr = email;
//
//        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(inputStr);
//        if (matcher.matches()) {
//            isValid = true;
//        }
//        return isValid;
//    }


    public void addChild(View v){
//        if(!isEmailValid(kidId.getText().toString())){
//            error.setText("Should be an email Address.");
//            return;
//        }
        final Child[] child = new Child[1];
        String id =  kidId.getText().toString();
        ParseQuery<Child> query1 = ParseQuery.getQuery("Child");
        query1.whereEqualTo("username", id);
        query1.findInBackground(new FindCallback<Child>() {
            public void done(List<Child> children, ParseException e) {
                if (e == null) {
                    child[0] = children.get(0);

                    ParseQuery<Parent> query = ParseQuery.getQuery("Parent");
                    query.getInBackground(ParseUser.getCurrentUser().getString("parentObjectId"), new GetCallback<Parent>() {
                        public void done(Parent object, ParseException e) {
                            if (e == null) {

                                // object will be your game
                                ParseRelation<Child> relation = object.getRelation("Children");
                                if (child[0] != null && child[0].getName().equals(kidName.getText().toString())) {
                                    relation.add(child[0]);
                                    object.saveInBackground();
                                    Intent intent = new Intent(AddingchildAcitivity.this, StartingActivity.class);
                                    startActivity(intent);

                                } else if (child[0] != null && !child[0].getName().equals(kidName.getText().toString())) {
                                    error.setText("Name or ID are not match");

                                } else if (child[0] == null) {
                                    error.setText("ID doesnt Exist");
                                } else {
                                    error.setText(e.getLocalizedMessage());
                                }

                            } else {
                                // something went wrong
                                error.setText(e.getLocalizedMessage());
                            }
                        }
                    });
                } else {
                    Log.d("ef", "Empty");
                    error.setText(e.getLocalizedMessage());
                }
            }
        });



    }


    public void addListenerOnMenuButton() {

        final Context context = this;

        menu = (RelativeLayout) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, StartingActivity.class);
                startActivity(intent);
            }
        });
    }
    public void logoutButton(View view){
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
