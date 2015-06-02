package com.bookkeeping.ongoing.ongoing_bookkeeping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Child;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.LoginActivity;
import com.bookkeeping.ongoing.ongoing_bookkeeping.user.Wish;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Seulki_Lee on 4/20/2015.
 */
public class Wish_Activity extends Activity {
    Button button;
    RelativeLayout menu;

    EditText wish;
    EditText wishAmount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
        addListenerOnMenuButton();
        wish = (EditText) findViewById(R.id.wish);
        wishAmount = (EditText) findViewById(R.id.wishAmount);

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

    public void addWish(View view){

        ParseQuery<Child> query = ParseQuery.getQuery("Child");
        query.getInBackground(ParseUser.getCurrentUser().getString("childObjectId"), new GetCallback<Child>() {

            @Override
            public void done(final Child object, ParseException e) {
                if (e == null) {

                    final Wish wishr = new Wish();
                    wishr.setTitle(wish.getText().toString());
                    double money = Double.parseDouble(wishAmount.getText().toString());
                    wishr.setMoney(money);
                    wishr.setRemain(money);
                    wishr.setsaved(0);
                    wishr.saveInBackground();
                    wishr.saveInBackground(new SaveCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Saved successfully.
                                String id = wishr.getObjectId();
                                object.put("wishObjectId", id);
                                object.saveInBackground();
                            } else {
                                Log.d("wish", "fail");
                            }
                        }
                    });


                } else {
                    Log.d("Wish" , "dont exist");
                }
            }

        });
        Intent intent = new Intent(this, ChildstartActivity.class);
        startActivity(intent);
    }
    public void logoutButton(View view){
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}



