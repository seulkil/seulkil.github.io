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

import com.bookkeeping.ongoing.ongoing_bookkeeping.user.LoginActivity;
import com.parse.ParseUser;

/**
 * Created by Seulki_Lee on 5/5/2015.
 */
public class ChildstartActivity extends Activity {

    private Button statisticButton;
    private Button recordButton;
    private TextView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childstart);
        recordButton = (Button) findViewById(R.id.recordButton);
        statisticButton = (Button) findViewById(R.id.statisticButton);
        logout =(TextView) findViewById(R.id.logout);
        addListenerOnStatisticButton();
        addListenerOnRecordButton();
        addListenerOnPlanButton();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        int[] locationInWindow = new int[2];
        logout.getLocationInWindow(locationInWindow);

        Log.v("TAG", "getLocationInWindow() - logout" + locationInWindow[0] + " : " + locationInWindow[1]);

        int[] locationInWindow1 = new int[2];
        statisticButton.getLocationInWindow(locationInWindow1);

        Log.v("TAG", "getLocationInWindow() - stat" + locationInWindow1[0] + " : " + locationInWindow1[1]);
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
    public void addListenerOnStatisticButton() {

        final Context context = this;


        statisticButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Statistic_Activity.class);
                startActivity(intent);
            }
        });
    }

    public void addListenerOnRecordButton() {

        final Context context = this;



        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Record_Activity.class);
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

                Intent intent = new Intent(context, Plan_Activity.class);
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
