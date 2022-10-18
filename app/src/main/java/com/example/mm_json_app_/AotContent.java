package com.example.mm_json_app_;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AotContent {
    public static final List<Aot> ITEMS = new ArrayList<Aot>();
    public static final Map<String, Aot> ITEM_MAP = new HashMap<String, Aot>();
    public static boolean construct = false;

    public void intoGSON(Activity activity) {
        String url = "https://api.jsonbin.io/v3/b/634eac2d0e6a79321e2c9bf6";
        RequestQueue queue = Volley.newRequestQueue(activity);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObjectNode = response.getJSONObject("record");
                    JSONArray jsonArray = jsonObjectNode.getJSONArray("AOT"); // Obtains Array named "gameCompanies"
                    ITEMS.clear();
                    ITEM_MAP.clear();
//                    Gson gson = new Gson();
//                    String info = jsonArray.toString(); // JSON Array
//                    Type listType = new TypeToken<ArrayList<Model>>(){}.getType();
//                    List<Model> games = gson.fromJson(info,listType);


                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("name");
                        String titan = object.getString("titan");
                        int height = object.getInt("height");


                        Aot newAot = new Aot(name,titan,height);
                        ITEMS.add(newAot);
                        ITEM_MAP.put(name,newAot);
                        //mTextView.append(model.name + ", " + model.year + ", " + model.recentConsole + "\n");
                    }
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
