package com.bookkeeping.ongoing.ongoing_bookkeeping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Child;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.LoginActivity;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Record;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Wish;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Seulki_Lee on 4/15/2015.
 */
public class Record_Activity extends Activity{
    Button recordButton;
    Button saveButton;
    RelativeLayout menu;
    TextView error;
    TextView current;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        error = (TextView) findViewById(R.id.error);
        current = (TextView) findViewById(R.id.currentBalance);
        addListenerOnMenuButton();
        addListenerOnRecordingButton();
        addListenerOnSavingButton();
        setCurrent();
    }
    public void addListenerOnMenuButton() {

        final Context context = this;

        menu = (RelativeLayout) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ChildstartActivity.class);
                startActivity(intent);
            }
        });
    }
    public void addListenerOnRecordingButton() {

        final Context context = this;

        recordButton = (Button) findViewById(R.id.recordActivityButton);

        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, Recording_Activity.class);
                startActivity(intent);
            }
        });
    }
    public void setCurrent(){
        ParseQuery<Child> query = ParseQuery.getQuery("Child");
        query.getInBackground(ParseUser.getCurrentUser().getString("childObjectId"), new GetCallback<Child>() {
            @Override
            public void done(Child object, ParseException e) {
                if (e == null) {
                    current.setText(object.getMoney().toString());
                } else {
                    Log.d("record", e.getMessage());
                }
            }
        });

    }
    public void addListenerOnSavingButton() {

        final Context context = this;

        saveButton = (Button) findViewById(R.id.saveActivityButton);

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ParseQuery<Child> query = ParseQuery.getQuery("Child");
                query.getInBackground(ParseUser.getCurrentUser().getString("childObjectId"), new GetCallback<Child>() {
                    @Override
                    public void done(final Child object, ParseException e) {
                        if (e == null) {
                            ParseQuery<Wish> wishquery = ParseQuery.getQuery(Wish.class);
                            wishquery.getInBackground(object.getString("wishObjectId"),new GetCallback<Wish>() {
                                @Override
                                public void done(Wish object1, ParseException e) {
                                    if (e == null) {
                                        double money = object.getMoney();
                                        double remain = object1.getRemain();
                                        double checkremain =0;
                                        double biggeremain=0;
                                        checkremain = remain - money;
                                        if(checkremain >= 0){
                                            boolean done = object.calculate(remain, true);
                                            Record record = new Record();
                                            record.setTitle("Saving For the Wish Item");
                                            record.setMoney(money);
                                            record.setIsBilled(true);
                                            record.saveInBackground();
                                            object1.saveMoney(money);
                                            object1.saveInBackground();
                                            object.getRecord().add(record);
                                            object.saveInBackground();
                                        }else if(remain == 0){
                                          // error.setText("You Don't need to save money");
                                            Toast.makeText(Record_Activity.this, "You Don't need to save money.", Toast.LENGTH_LONG).show();
                                        }  else{
                                            object1.saveMoney(remain);
                                            boolean done = object.calculate(remain, true);
                                            Record record = new Record();
                                            record.setTitle("Saving For the Wish Item");
                                            record.setMoney(remain);
                                            record.setIsBilled(true);
                                            record.saveInBackground();
                                            object.getRecord().add(record);
                                            object.saveInBackground();
                                            object1.saveInBackground();
                                        }
                                    } else {
                                        Log.d("record", e.getMessage());
                                    }
                                }
                            });


                        } else {
                            Log.d("record", e.getMessage());
                        }
                    }
                });


                Intent intent = new Intent(context, ChildstartActivity.class);
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
