package com.example.mm_json_app_;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    @SerializedName("name") public String mName;
    @SerializedName("year") public int mYear;
    @SerializedName("recentConsole") public String mRecentConsole;

    public Model (String name, int year, String recentConsole) {

        this.mName = name;
        this.mYear = year;
        this.mRecentConsole = recentConsole;
    }

    public String getName() {return this.mName;}
    public int getYear() {return this.mYear;}
    public String getRecentConsole() {return this.mRecentConsole;}

    public void setName(final String name) {this.mName = name;}
    public void setYear(final int year) {this.mYear = year;}
    public void setRecentConsole(final String recentConsole) {this.mRecentConsole = recentConsole;}
}


