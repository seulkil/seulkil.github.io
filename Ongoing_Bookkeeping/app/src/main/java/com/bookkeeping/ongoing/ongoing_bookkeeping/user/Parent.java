package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;

/**
 * Created by Seulki_Lee on 5/4/2015.
 */
@ParseClassName("Parent")
public class Parent extends ParseObject {

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

    public String getPassword(){
        return getString("Password");
    }

    public void setPassword(String password) {
        put("Password", password);
    }


    public String getIsChild(){
        return getString("Who");
    }

    public void setIsChild(boolean isChild) {
        put("Who", isChild);
    }


    public ParseRelation<Child> getChildren(){
        return getRelation("Children");
    }

    public void addChild(Child child){
        getChildren().add(child);
        saveInBackground();
    }
    public void removeChild(Child child){
        getChildren().remove(child);
        saveInBackground();
    }
}
