package com.example.finalprojectandroidversion;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class preferences_fragment extends Fragment {
    private  View view;
    private String user_preferences ="http://192.168.43.217/finalproject/apis/user_items.php";
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<preferedItemsModal> preferencesSet;
    private preferencesAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.preferences_fragment, container, false);
        recyclerView =(RecyclerView)view. findViewById(R.id.user_items);
        recyclerView.setHasFixedSize(true);
        getPreferences(user_preferences);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        toolbar =(Toolbar)view. findViewById(R.id.items_preferred);
        return  view;
    }
    public void getPreferences(String url){
        preferencesSet =new ArrayList<>();
        RequestQueue userPreferencesQueue = Volley.newRequestQueue(getActivity());
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