package com.bookkeeping.ongoing.ongoing_bookkeeping;

/**
 * Created by Seulki_Lee on 4/13/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Child;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.LoginActivity;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Record;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.RecordAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class Statistic_Activity extends Activity{
    Button button;
    RelativeLayout menu;
    ListView listView;
    List<Record> record;
    String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);


        id = getIntent().getStringExtra("id");

        ParseQuery<Child> query = ParseQuery.getQuery(Child.class);
        query.whereEqualTo("objectId", id);
        listView = (ListView) findViewById(R.id.records);
        addListenerOnMenuButton();
        displayList();
    }

//    public static void setTheChild(Child thechild){
//
//            Tchild = thechild;
//
//    }

    private void displayList(){
        if(!ParseUser.getCurrentUser().getBoolean("isChild")){
            ParseQuery<Child> query = ParseQuery.getQuery(Child.class);
            query.getInBackground(id, new GetCallback<Child>() {
                public void done(Child object, ParseException e) {
                    if (e == null) {
                        ParseRelation<Record> relation = object.getRelation("Record");
                        ParseQuery<Record> records = relation.getQuery();
                        try {
                            record = records.find();
                            RecordAdapter c = new RecordAdapter(Statistic_Activity.this, record);
                            listView.setAdapter(c);

                            c.notifyDataSetChanged();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        Log.d("view", "Error: " + e.getMessage());
                    }
                }
            });

        }else{
            ParseQuery<Child> query = ParseQuery.getQuery("Child");
            query.getInBackground(ParseUser.getCurrentUser().getString("childObjectId"), new GetCallback<Child>() {
                public void done(Child object, ParseException e) {
                    if (e == null) {
                        Log.d("id", object.getId());
                        ParseRelation<Record> relation = object.getRelation("Record");
                        ParseQuery<Record> records = relation.getQuery();
                        try {
                            record = records.find();
                            RecordAdapter c = new RecordAdapter(Statistic_Activity.this, record);
                            listView.setAdapter(c);
                            c.notifyDataSetChanged();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        Log.d("wrong", " wrong");
                    }
                }
            });
        }





    }
    public void addListenerOnMenuButton() {

        final Context context = this;

        menu = (RelativeLayout) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent;
                if(!ParseUser.getCurrentUser().getBoolean("isChild")) {
                    intent = new Intent(context, ViewChildActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else {
                    intent = new Intent(context, ChildstartActivity.class);
                }

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
