package com.example.mm_json_app_;

import com.google.gson.annotations.SerializedName;

public class Aot {

    @SerializedName("name") public String mName;
    @SerializedName("titan") public String mTitan;
    @SerializedName("height") public Integer mHeight;

    public Aot (final String Name, final String Titan, final Integer Height) {
        this.mName = Name;
        this.mTitan = Titan;
        this.mHeight = Height;
    }

    public String getName() {return this.mName;}
    public String getTitan() {return this.mTitan;}
    public Integer getHeight() {return mHeight;}

    public void setName(final String name) {this.mName = name;}
    public void setTitan(final String titan) {this.mTitan = titan;}
    public void setHeight(final int year) {this.mHeight = year;}
}
