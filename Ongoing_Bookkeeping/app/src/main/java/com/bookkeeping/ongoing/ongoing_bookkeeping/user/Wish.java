package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Seulki_Lee on 5/5/2015.
 */
@ParseClassName("Wish")
public class Wish extends ParseObject {
    public Double getMoney(){
        return getDouble("Amount");
    }

    public void setMoney(double money) {
        put("Amount", money);
    }
    public String getTitle() {
        return getString("Title");
    }

    public void setTitle(String title) {
        put("Title", title);
    }

    public String getDate() {
        Date date = getCreatedAt();
        return date.toString();
    }

    public Double getRemain(){
        return getDouble("remain");
    }

    public void setRemain(double money) {
        put("remain", money);
    }

    public Double getsaved(){
        return getDouble("saved");
    }

    public void setsaved(double money) {
        put("saved", money);
    }

    public void saveMoney(double amount){
        double saved = getsaved();
        double remain = getRemain();
        saved += amount;
        remain -= amount;
        if(remain > 0) {
            setsaved(saved);
            setRemain(remain);
            saveInBackground();
        } else {
            saved = getMoney();
            remain = 0;
            setsaved(saved);
            setRemain(remain);
            saveInBackground();
        }

    }


}
