package com.bookkeeping.ongoing.ongoing_bookkeeping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Child;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.LoginActivity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Seulki_Lee on 5/5/2015.
 */
public class ViewChildActivity extends Activity {
    String id;
    RelativeLayout menu;
    TextView childId;
    TextView balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra("id");

        ParseQuery<Child> query = ParseQuery.getQuery(Child.class);
        query.whereEqualTo("objectId", id);
        query.findInBackground(new FindCallback<Child>() {
            @Override
            public void done(List<Child> list, ParseException e) {
                //

                setContentView(R.layout.activity_viewchild);
                childId = (TextView) findViewById(R.id.childId);
                balance = (TextView) findViewById(R.id.childbalance);
                addListenerOnStatisticButton();
                addListenerOnPlanButton();
                addListenerOnMenuButton();
                setIdAndBalance();

            }
        });


    }

    private void setIdAndBalance() {
        ParseQuery<Child> query = ParseQuery.getQuery(Child.class);
        query.whereEqualTo("objectId", id);
        query.getInBackground(id, new GetCallback<Child>() {
            public void done(Child object, ParseException e) {
                if (e == null) {
                    childId.setText(object.getName());
                    balance.setText(object.getMoney().toString());

                } else {
                    Log.d("view", "Error: " + e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void addListenerOnStatisticButton() {

        final Context context = this;

        Button statisticButton = (Button) findViewById(R.id.statisticButton);

        statisticButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Statistic_Activity.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });
    }

    public void addListenerOnPlanButton() {

        final Context context = this;

        Button statisticButton = (Button) findViewById(R.id.planButton);

        statisticButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ViewChildWishActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });
    }
    public void logoutButton(View view) {
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

}
