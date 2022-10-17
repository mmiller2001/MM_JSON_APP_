package com.example.mm_json_app_.placeholder;

import android.widget.TextView;
import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    public static final List<GameCompanies> ITEM_MAP = new ArrayList<GameCompanies>();
    public static final Map<String,GameCompanies> MAP = new HashMap<String,GameCompanies>();
    //private RequestQueue mQueue;


    public static class GameCompanies {
        public final String name;
        public final int year;
        public final String recentConsole;
        private List<GameCompanies> gamesList;

        public GameCompanies(String name, int year, String recentConsole) {
            this.name = name;
            this.year = year;
            this.recentConsole = recentConsole;
            //this.gamesList = gamesList;
        }

//        @Override
//        public String toString() {
//            return name;
//        }
    }
}
