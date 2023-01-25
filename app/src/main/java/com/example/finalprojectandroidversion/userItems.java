package com.example.finalprojectandroidversion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class userItems extends AppCompatActivity {

    private String user_preferences ="http://localhost/finalproject/apis/user_items.php";
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<preferedItemsModal> preferencesSet;
    private preferencesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_items);

//        comments
        recyclerView =(RecyclerView) findViewById(R.id.user_items);
        recyclerView.setHasFixedSize(true);
        getPreferences(user_preferences);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar =(Toolbar) findViewById(R.id.items_preferred);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
    public void getPreferences(String url){
        preferencesSet =new ArrayList<>();
        RequestQueue userPreferencesQueue = Volley.newRequestQueue(userItems.this);
        StringRequest userPreferencesRequest=new StringRequest(Request.Method.GET, user_preferences, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response:", response);
                try {
                    JSONArray array = new JSONArray(response);
                    Log.d("JSONArray: ", ""+array);
                    for (int index = 0; index < array.length(); index++) {
                        JSONObject preferencesSetObject = array.getJSONObject(index);
                        preferencesSet.add(
                                new preferedItemsModal(
                                        preferencesSetObject.getString("item_class")));
                    }
                    adapter=new preferencesAdapter(preferencesSet);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error message
            }
        });
        userPreferencesQueue.add(userPreferencesRequest);

    }

}
