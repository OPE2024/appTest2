package com.onaopemipodimowo.apptest;

public class ReadWriteUserDetails {
    public String doB , gender, college;

    //Constructor
    public ReadWriteUserDetails(){};

    public ReadWriteUserDetails( String textDoB, String textGender, String textCollege){
        this.doB = textDoB;
        this.gender = textGender;
        this.college = textCollege;
    }
}
