package com.example.finalprojectandroidversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CulturalComponentsList extends AppCompatActivity {
    private List<CulturalComponentModal> modalList;
    private RecyclerView recyclerView;
    private CulturalComponentsAdapter componentsAdapter;
    private  boolean isRequestEmpty, isPreferenceRequestEmpty;
    private ActionBar actionBar;
    private String culturalComponentsApi ="http://1172.17.22.37/finalproject/apis/Select_Cultural_Components.php";
    private String saveUserProfileApi ="http://172.17.22.37/finalproject/apis/save_profile.php";
    private String lastRegistrationIdApi ="http://1172.17.22.37/finalproject/apis/last_registration_id.php";
    private String saveUserPreferencesApi ="http://172.17.22.37/finalproject/apis/save_preferences.php";
    private String lastRegistrationProfileId ="http://172.17.22.37/finalproject/apis/last_profile_id.php";
    private AlertDialog.Builder builder;
    private Button preferencesBtn;
<<<<<<< HEAD
    private Button myButton;
=======
    private int lastRegistrationId;
    private List<Integer> registrationId,profileId;
>>>>>>> master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cultural_components_list);
        this.setTitle("Cultural Components");
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        preferencesBtn =(Button)findViewById(R.id.savePreferences);
        recyclerView =(RecyclerView) findViewById(R.id.culturalComponentToInflate);
        recyclerView.setHasFixedSize(true);
        loadCulturalComponentsUrl();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //calling requestqueue to list our cultiral component
        getLastRegistrationId();
        preferencesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getLastRegistrationId();
                isRequestEmpty= recordUserProfile();
                if(isRequestEmpty) {
                    Toast.makeText(CulturalComponentsList.this, "Profile Recorded", Toast.LENGTH_SHORT).show();
                }

                returnLastProfileId();
                isPreferenceRequestEmpty =recordUserPreferences();
                if(isPreferenceRequestEmpty){
                    Toast.makeText(CulturalComponentsList.this, "Preferences Were Recorded ", Toast.LENGTH_SHORT).show();
                }
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
    public void loadCulturalComponentsUrl(){
        modalList =new ArrayList<>();
        //requesting cultural components from databse
        StringRequest stringRequest =new StringRequest(Request.Method.GET, culturalComponentsApi, new Response.Listener<String>(){
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

    //searching last registrtion id
    public int getLastRegistrationId() {
        //int reg;
        registrationId=new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, lastRegistrationIdApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response: ",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("Array:",""+jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject regId = jsonArray.getJSONObject(i);
                        lastRegistrationId = regId.getInt("registration_id");
                        Log.d("Registration____id",""+ lastRegistrationId);
                        registrationId.add(regId.getInt("registration_id"));

                        //reg =registration;

                    }
                    for(int i=0; i<jsonArray.length();i++){
                        Log.d("List id",""+registrationId.get(i));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error message
                    }
                });
        //Log.d("Registration____id",""+ lastRegistrationId);
        Volley.newRequestQueue(this).add(request);
        //Toast.makeText(CulturalComponentsList.this,"Registration: "+registration,Toast.LENGTH_LONG).show();
        return lastRegistrationId;
    }



        //recording user profile id
    public boolean recordUserProfile(){
        RequestQueue profileQueue = Volley.newRequestQueue(CulturalComponentsList.this);
//        StringRequest lastRegistrationIdRequest = new StringRequest(Request.Method.GET, lastRegistrationIdApi, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("Response: ",response);
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    Log.d("Array:",""+jsonArray);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject regId = jsonArray.getJSONObject(i);
//                        lastRegistrationId = regId.getInt("registration_id");
//                        Log.d("Registration____id",""+ lastRegistrationId);
//                        //reg =registration;
//
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //error message
//                    }
//                });


       StringRequest userProfileIdRequest=new StringRequest(Request.Method.POST, saveUserProfileApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    builder.setTitle("Server response");
//                    builder.setMessage("Response " + response);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();

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
            @Override
            protected Map<String,String> getParams() {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                Map<String, String> params = new HashMap<>();
                params.put("registration_id", String.valueOf(registrationId.get(0)));
                params.put("registration_date", formatter.format(date));
                return params;
            }
        };


        //Volley.newRequestQueue(this).add(lastRegistrationIdRequest);
        //Volley.newRequestQueue(this).add(lastRegistrationIdRequest);
       boolean isRequestEmpty =(profileQueue.add(userProfileIdRequest))!=null;
       return isRequestEmpty;
    }


    //selecting the last registration to use it in linking user
    //profiles with registration

    public void returnLastProfileId(){
        profileId=new ArrayList<>();
        RequestQueue lastProfileIdQueue =Volley.newRequestQueue(CulturalComponentsList.this);
        StringRequest lastUserProfileIdRequest=new StringRequest(Request.Method.GET, lastRegistrationProfileId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray lastProfileIdArray = new JSONArray(response);
                    for (int index = 0; index < lastProfileIdArray.length(); index++) {
                        JSONObject jsonObject = lastProfileIdArray.getJSONObject(index);
                        profileId.add(jsonObject.getInt("profile_id"));
                    }
                    for (int index = 0; index < lastProfileIdArray.length(); index++) {
                        Log.d("Profile Id ",""+profileId.get(index));
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error message
        }
    });
        lastProfileIdQueue.add(lastUserProfileIdRequest);
    }

    //recording user preferences
    public boolean recordUserPreferences() {
        boolean isRequestEmpty;
        RequestQueue preferencesQueue =Volley.newRequestQueue(CulturalComponentsList.this);
        StringRequest userPreferencesRequest = new StringRequest(Request.Method.POST, saveUserPreferencesApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //some code here in further analysis
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Error message
                Toast.makeText(CulturalComponentsList.this, "Error occured", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params=null;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                List<String> preferences =  CulturalComponentsAdapter.CulturalComponentsHolder.preferencesList();
                Log.d("Preferences Array",""+preferences);
                    params =new HashMap<>();
                    params.put("profile_id","1");
                    params.put("item_class", Arrays.toString(preferences.toArray()));
                    params.put("date_of_addition",formatter.format(date));
                    //checking id preferences list is not empty
                    //return params;
                return params;
            }
        };
        isRequestEmpty=((preferencesQueue.add(userPreferencesRequest))!=null);
        return isRequestEmpty;
    }
}