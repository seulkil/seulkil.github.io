package com.bookkeeping.ongoing.ongoing_bookkeeping;

/**
 * Created by Seulki_Lee on 4/15/2015.
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
import android.widget.TextView;

import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Child;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.LoginActivity;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Wish;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.WishAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Plan_Activity extends Activity {
    Button button;
    RelativeLayout menu;
    Button makeWish;
    Button deleteWish;
    TextView mess;

    ListView listView;
    List<Wish> list = new ArrayList<Wish>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        mess = (TextView) findViewById(R.id.mess);
        listView = (ListView) findViewById(R.id.wishs);
        addListenerOnMenuButton();
        addListenerOnDeleteButton();
        addListenerOnWishButton();
        setList();
        setMessage();
    }

    private void setMessage() {
        ParseQuery<Child> query = ParseQuery.getQuery("Child");
        query.getInBackground(ParseUser.getCurrentUser().getString("childObjectId"), new GetCallback<Child>() {
            public void done(Child object, ParseException e) {
                if (e == null) {
                    ParseQuery<Wish> query = ParseQuery.getQuery("Wish");
                    query.getInBackground(object.getString("wishObjectId"), new GetCallback<Wish>() {
                        @Override
                        public void done(Wish wish, ParseException e) {
                            if(e == null){
                                if(wish.getRemain() == 0){
                                    mess.setText("You Can Buy The Item");
                                }else{
                                    mess.setText("You need to save money");
                                }

                            }else {
                                Log.d("wish", "wrong");
                            }

                        }
                    });
                } else {
                    Log.d("wrong", " wrong");
                }
            }
        });
    }

    private void setList() {
        ParseQuery<Child> query = ParseQuery.getQuery("Child");
        query.getInBackground(ParseUser.getCurrentUser().getString("childObjectId"), new GetCallback<Child>() {
            public void done(Child object, ParseException e) {
                if (e == null) {
                    ParseQuery<Wish> query = ParseQuery.getQuery("Wish");
                    query.getInBackground(object.getString("wishObjectId"), new GetCallback<Wish>() {
                        @Override
                        public void done(Wish wish, ParseException e) {
                            if(e == null){
                                list.add(wish);

                                final WishAdapter c = new WishAdapter(Plan_Activity.this, list);

                                listView.setAdapter(c);
                                c.notifyDataSetChanged();
                            }else {
                                Log.d("wish", "wrong");
                            }

                        }
                    });
                } else {
                    Log.d("wrong", " wrong");
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

                Intent intent = new Intent(context, ChildstartActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addListenerOnWishButton() {

        final Context context = this;

        makeWish = (Button) findViewById(R.id.makeWishActivityButton);

        makeWish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Wish_Activity.class);
                startActivity(intent);
            }
        });
    }

    public void addListenerOnDeleteButton() {

        final Context context = this;

        deleteWish = (Button) findViewById(R.id.deleteWishActivityButton);

        deleteWish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ParseQuery<Child> query = ParseQuery.getQuery("Child");
                query.getInBackground(ParseUser.getCurrentUser().getString("childObjectId"), new GetCallback<Child>() {

                    @Override
                    public void done(final Child object, ParseException e) {
                        if (e == null) {

                            ParseQuery<Wish> query = ParseQuery.getQuery("Wish");
                            query.getInBackground(object.getString("wishObjectId"), new GetCallback<Wish>() {
                                @Override
                                public void done(Wish object1, ParseException e) {
                                    if (e == null) {
                                        object.remove("wishObjectId");
                                        object1.deleteInBackground();
                                    } else {

                                    }
                                }

                            });


                        } else {


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