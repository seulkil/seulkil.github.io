package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Seulki_Lee on 5/4/2015.
 */
@ParseClassName("Record")
public class Record extends ParseObject{

    public String getTitle() {
        return getString("Title");
    }
    public String getDate() {
        Date date = getCreatedAt();
        return date.toString();
    }
    public void setTitle(String title) {
        put("Title", title);
    }

    public Double getMoney(){
        return getDouble("Amount");
    }

    public void setMoney(double money) {
        put("Amount", money);
    }


    public boolean getIsBilled(){
        return getBoolean("isBilled");
    }

    public void setIsBilled(boolean isBilled) {
        put("isBilled", isBilled);
    }
}
