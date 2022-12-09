package com.example.finalprojectandroidversion;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CulturalComponentsList extends AppCompatActivity {
    private List<CulturalComponentModal> modalList;
    private RecyclerView recyclerView;
    private CulturalComponentsAdapter componentsAdapter;
    private ActionBar actionBar;
    private String apiUrl ="http://172.31.101.225/finalproject/apis/Select_Cultural_Components.php";
    private String preferencesUrl ="http://172.31.101.225/finalproject/apis/save_profile.php";
    private String regIdApi ="http://172.31.101.225/finalproject/apis/last_registration_id.php";
    AlertDialog.Builder builder;
    private Button preferencesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cultural_components_list);
        this.setTitle("Cultural Components");
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        preferencesBtn =(Button)findViewById(R.id.savePreferences);
        modalList =new ArrayList<>();
        recyclerView =(RecyclerView) findViewById(R.id.culturalComponentToInflate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadUrl();
        preferencesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postPreferences();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void loadUrl(){

        StringRequest stringRequest =new StringRequest(Request.Method.GET,apiUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.d("Response:", response);
                try {
                    JSONArray array = new JSONArray(response);
                    Log.d("JSONArray: ", ""+array);
                    for (int index = 0; index < array.length(); index++) {
                        JSONObject componentsObject = array.getJSONObject(index);
                        modalList.add(
                                new CulturalComponentModal(
                                        componentsObject.getInt("classification_id"),
                                        componentsObject.getString("classification_name")));
                    }
                    componentsAdapter=new CulturalComponentsAdapter(modalList);
                    recyclerView.setAdapter(componentsAdapter);

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //error message
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void postPreferences(){
        //RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, preferencesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    builder.setTitle("Server response");
                    builder.setMessage("Response " + response);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //error
                Toast.makeText(CulturalComponentsList.this, "Failed to get response = " + error, Toast.LENGTH_SHORT).show();
            }

        }){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String,String> getParams() {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                Map<String, String> params = new HashMap<>();
                params.put("registration_id", String.valueOf(getRegid()));
                params.put("registration_date", formatter.format(date));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
    int registration;
    public int getRegid() {
        StringRequest request = new StringRequest(Request.Method.GET, regIdApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response: ",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("Array:",""+jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject regId = jsonArray.getJSONObject(i);
                        registration = regId.getInt("registration_id");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Log.d("Registration____id",""+registration);
        Volley.newRequestQueue(this).add(request);
        //Toast.makeText(CulturalComponentsList.this,"Registration: "+registration,Toast.LENGTH_LONG).show();
        return registration;
    }
}