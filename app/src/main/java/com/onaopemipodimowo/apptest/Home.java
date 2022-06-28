package com.onaopemipodimowo.apptest;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;


public class Home  {
    private String Name, City, State_code, Line, Property_type;
    private int Baths_min, Baths_max, Beds_min, Beds_max;

//    private boolean cats_ok;
//    private boolean dogs_ok;


    public Home( String name, String city, String state_code, String line, String property_type, int baths_min, int baths_max, int beds_min, int beds_max) {
        Name = name;
        City = city;
        State_code = state_code;
        Line = line;
        Property_type = property_type;
        Baths_min = baths_min;
        Baths_max = baths_max;
        Beds_min = beds_min;
        Beds_max = beds_max;
//        cats_ok = jsonObject.getBoolean("cats_ok");
//        dogs_ok = jsonObject.getBoolean("dogs_ok");

    }

    public String getName(){return Name;}
    public String getCity(){
        return City;
    }

    public String getState_code(){return State_code;}

    public String getLine(){
        return Line;
    }
    public String getProperty_type(){
        return Property_type;
    }
    public int getBaths_min(){
        return Baths_min;
    }
    public int getBaths_max(){
        return Baths_max;
    }
    public int getBeds_min(){
        return Beds_min;
    }
    public int getBeds_max(){
        return Beds_max;
    }

}
