package com.bookkeeping.ongoing.ongoing_bookkeeping;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Child;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.LoginActivity;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Record;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by Seulki_Lee on 4/20/2015.
 */
public class Recording_Activity extends Activity{
    Button recordingButton;
    RelativeLayout menu;
    EditText title;
    EditText amount;
    CheckBox debit;
    CheckBox credit;
    TextView error;
    boolean isCrebit;
    boolean atleastCheck = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        addListenerOnMenuButton();
        addListenerOnRecordingButton();
        title = (EditText) findViewById(R.id.recordActivity);
        amount = (EditText) findViewById(R.id.recordAmount);
        error = (TextView) findViewById(R.id.recorderror);
        debit = (CheckBox) findViewById(R.id.checkbox_income);
        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCrebit = false;
                atleastCheck = true;
            }
        });
        credit = (CheckBox) findViewById(R.id.checkbox_bill);
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCrebit = true;
                atleastCheck = true;
            }
        });

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

        recordingButton = (Button) findViewById(R.id.recordingButton);

        recordingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ParseQuery<Child> query = ParseQuery.getQuery("Child");
                query.getInBackground(ParseUser.getCurrentUser().getString("childObjectId"), new GetCallback<Child>() {
                    @Override
                    public void done(Child object, ParseException e) {
                        if (e == null) {
                            ParseRelation<Record> relation = object.getRelation("Record");
                            if (atleastCheck) {
                                Record record = new Record();
                                record.setTitle(title.getText().toString());
                                double money = Double.parseDouble(amount.getText().toString());
                                record.setMoney(money);
                                record.setIsBilled(isCrebit);
                                record.saveInBackground();
                                if(object.calculate(money,isCrebit)){
                                    relation.add(record);
                                    object.saveInBackground();
                                } else{
                                    error.setText("You Dont have enough Money");
                                    record.deleteInBackground();
                                }
                            }




                        } else {
                            // something went wrong
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
