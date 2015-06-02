package com.bookkeeping.ongoing.ongoing_bookkeeping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

/**
 * Created by Seulki_Lee on 5/5/2015.
 */
public class ViewChildWishActivity extends Activity {
    RelativeLayout menu;
    String id;
    ListView listView;
    List<Wish> list = new ArrayList<Wish>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewchildwish);
        addListenerOnMenuButton();

        id = getIntent().getStringExtra("id");
        ParseQuery<Child> query = ParseQuery.getQuery(Child.class);
        query.whereEqualTo("objectId", id);
        
        listView = (ListView) findViewById(R.id.wishs);
        setList();
    }

    private void setList() {

        ParseQuery<Child> query = ParseQuery.getQuery("Child");
        query.whereEqualTo("objectId", id);
        query.getInBackground(id, new GetCallback<Child>() {
            public void done(Child object, ParseException e) {
                if (e == null) {
                    ParseQuery<Wish> query = ParseQuery.getQuery("Wish");
                    query.getInBackground(object.getString("wishObjectId"), new GetCallback<Wish>() {
                        @Override
                        public void done(Wish wish, ParseException e) {
                            if(e == null){
                                list.add(wish);

                                WishAdapter c = new WishAdapter(ViewChildWishActivity.this, list);

                                listView.setAdapter(c);
                                c.notifyDataSetChanged();
                            }else {
                                Log.d("wish", "wrong");
                            }

                        }
                    });

                } else {
                    Log.d("view", "Error: " + e.getMessage());
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

                Intent intent = new Intent(context, ViewChildActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    public void logoutButton(View view){
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

    }
}
