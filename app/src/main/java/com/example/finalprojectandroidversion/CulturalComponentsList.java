package com.example.finalprojectandroidversion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CulturalComponentsList extends AppCompatActivity {
    private List<CulturalComponentModal> modalList;
    private RecyclerView recyclerView;
    private CulturalComponentsAdapter componentsAdapter;
    private String apiUrl ="http://192.168.8.162/finalproject/apis/Select_Cultural_Components.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cultural_components_list);
        recyclerView =(RecyclerView) findViewById(R.id.culturalComponentToInflate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadUrl();
    }
    public void loadUrl(){

        StringRequest stringRequest =new StringRequest(Request.Method.GET,apiUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int index = 0; index < array.length(); index++) {
                        JSONObject componentsObject = array.getJSONObject(index);
                        modalList.add(
                                new CulturalComponentModal(
                                        componentsObject.getInt("componentId"),
                                        componentsObject.getString("ComponentName")));
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}