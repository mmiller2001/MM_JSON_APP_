package com.example.mm_json_app_;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelContent {
//    Context context = App.getContext();
//    Resources res = context.getResources();


    public static final List<Model> ITEMS = new ArrayList<Model>();
    public static final Map<String, Model> ITEM_MAP = new HashMap<String, Model>();
    public static boolean construct = false;

    public void intoGSON(Activity activity) {
        String url = activity.getString(R.string.URL);
        RequestQueue queue = Volley.newRequestQueue(activity);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObjectNode = response.getJSONObject("record");
                    JSONArray jsonArray = jsonObjectNode.getJSONArray("gameCompanies"); // Obtains Array named "gameCompanies"
                    ITEMS.clear();
                    ITEM_MAP.clear();
                    Gson gson = new Gson();
                    String info = jsonArray.toString(); // JSON Array
                    Type listType = new TypeToken<ArrayList<Model>>(){}.getType();
                    List<Model> games = gson.fromJson(info,listType);

                    for(Model model : games) {
                        ITEMS.add(model);
                        ITEM_MAP.put(model.mName,model);
                    }


//                    for(int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        String name = object.getString("name");
//                        int year = object.getInt("year");
//                        String recentConsole = object.getString("recentConsole");
//                        Model newModel = new Model(name,year,recentConsole);
//                        ITEMS.add(newModel);
//                        ITEM_MAP.put(name,newModel);
//                        //mTextView.append(model.name + ", " + model.year + ", " + model.recentConsole + "\n");
//                    }
                    if(!construct) {
                        activity.recreate();
                    }
                    construct = true;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }
}
