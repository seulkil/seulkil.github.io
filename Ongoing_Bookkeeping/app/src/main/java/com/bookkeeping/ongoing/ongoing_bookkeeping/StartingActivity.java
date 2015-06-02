package com.bookkeeping.ongoing.ongoing_bookkeeping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Child;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.ChildrenAdapter;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.LoginActivity;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Parent;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Seulki_Lee on 5/4/2015.
 */
public class StartingActivity extends Activity {
    ListView listView ;
    Parent parent;
    List<Child> childrenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starting);
        listView = (ListView) findViewById(R.id.children);
        setArray();

    }
    private void setArray(){
        ParseQuery<Parent> query = ParseQuery.getQuery("Parent");
        query.getInBackground(ParseUser.getCurrentUser().getString("parentObjectId"), new GetCallback<Parent>() {
            public void done(Parent object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    ParseRelation<Child> relation = object.getRelation("Children");
                    ParseQuery<Child> child = relation.getQuery();
                    try {

                        childrenList = child.find();
                        ChildrenAdapter c = new ChildrenAdapter(StartingActivity.this, childrenList);

                        listView.setAdapter(c);
                        c.notifyDataSetChanged();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {


                                Intent intent = new Intent(StartingActivity.this, ViewChildActivity.class);
                                intent.putExtra("id", childrenList.get(position).getObjectId());


                                startActivity(intent);

                            }
                        });

                    } catch (ParseException e1) {
                        Log.d("list", e1.getMessage());
                    }

                } else {
                    // something went wrong
                }
            }
        });
    }
    public void addChild(View v) {
        final Context context = this;

        Intent intent = new Intent(context, AddingchildAcitivity.class);
        startActivity(intent);
        //finish();
    }
    public void logoutButton(View view){
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
