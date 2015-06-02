package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;

/**
 * Created by Seulki_Lee on 5/4/2015.
 */
@ParseClassName("Child")
public class Child extends ParseObject {

    public String getId(){
        return getString("username");
    }

    public void setId(String id) {
        put("username", id);
    }

    public String getName(){
        return getString("Name");
    }

    public void setName(String id) {
        put("Name", id);
    }

    public String getWishObjectId(){
        return getString("wishObjectId");
    }

    public void setWishObjectId(String id) {
        put("wishObjectId", id);
    }

    public Double getMoney(){
        return getDouble("Money");
    }

    public void setMoney(double money) {
        put("Money", money);
    }

    public String getPassword(){
        return getString("Password");
    }

    public void setPassword(String password) {
        put("Password", password);
        System.out.println(password);
    }

    public boolean getIsChild(){
        return getBoolean("Who");
    }

    public void setIsChild(boolean isChild) {
        put("Who", isChild);
    }

    public ParseRelation<Record> getRecord(){
        return getRelation("Record");
    }

    public void addRecord(Record record){
        getRecord().add(record);
        saveInBackground();
    }
    public void removeRecord(Record record){
        getRecord().remove(record);
        saveInBackground();
    }

    public boolean calculate(double amount, boolean isCredit){
        double money = getMoney();
        if(money == 0 && isCredit){
            return false;
        }
        if(isCredit){
            money -= amount;
            if(money < 0){
                return false;
            }
            setMoney(money);
            saveInBackground();
        } else {
            money += amount;
            setMoney(money);
            saveInBackground();
            return true;
        }
        return true;
    }

}

