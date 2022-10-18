package com.example.mm_json_app_;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.mm_json_app_.databinding.FragmentItemDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {

    private FloatingActionButton testingFab;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    //private PlaceholderContent.PlaceholderItem mItem;
    private Aot mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTextView;

    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = AotContent.ITEM_MAP.get(clipDataItem.getText().toString());
            updateContent();
        }
        return true;
    };
    private FragmentItemDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = AotContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemDetailBinding.inflate(inflater,container,false);
        View rootView = binding.getRoot();

        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        mTextView = binding.itemDetail;
        testingFab = rootView.findViewById(R.id.fab);

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        rootView.setOnDragListener(dragListener);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if (mItem != null) {
            mTextView.setText(mItem.getTitan());
            if (mToolbarLayout != null) {
                mToolbarLayout.setTitle(mItem.getName());
            }
        }

        if (testingFab != null)
        {
            testingFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    //testAllThatJazz();
                    //intoJSON();
                    intoGSON();
                }
            });
        }
    }

    private void intoGSON() {
        String url = "https://api.jsonbin.io/v3/b/634eac2d0e6a79321e2c9bf6";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObjectNode = response.getJSONObject("record");
                    JSONArray jsonArray = jsonObjectNode.getJSONArray("AOT"); // Obtains Array named "gameCompanies"
                    Gson gson = new Gson();
                    String info = jsonArray.toString(); // JSON Array

                    Type listType = new TypeToken<ArrayList<Model>>(){}.getType();
                    List<Model> games = gson.fromJson(info,listType);

                    for(Model model : games) {
                        String name = model.mName;

                        mTextView.append(model.mName + ", " + model.mYear + ", " + model.mRecentConsole + "\n");
                    }
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

//    private void intoJSON() {
//        String url = "https://api.jsonbin.io/v3/b/5f726a107243cd7e8245d58b";
//        RequestQueue queue = Volley.newRequestQueue(getActivity());
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
////                            mTextView.setText(response.toString());
//                              JSONObject jsonObjectNode = response.getJSONObject("record"); // Obtains object named "Records"
//                              JSONArray jsonArray = jsonObjectNode.getJSONArray("gameCompanies"); // Obtains Array named "gameCompanies"
//                            //mTextView.setText(jsonArray.toString());
//                            for(int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject games = jsonArray.getJSONObject(i);
//
//                                String name = games.getString("name");
//                                int year = games.getInt("year");
//                                String recentConsole = games.getString("recentConsole");
//
//                                mTextView.append("--" + name + ", " + String.valueOf(year) + "++ " + recentConsole + "\n");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        queue.add(request);
//    }
//
//    private void testAllThatJazz() {
//        String url = "https://api.jsonbin.io/v3/b/5f726a107243cd7e8245d58b";  // THAT should be in a strings.xml file!
//
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(getActivity());
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the response string in our convenient existing text view
//                        mTextView.setText("Response is: "+ response);
//                        // NEXT, we need to use GSON to turn that JSON into a model
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // you should drop a breakpoint RIGHT HERE if you need to see the error coming back
//                mTextView.setText("That didn't work!");
//            }
//        });
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//    }
}