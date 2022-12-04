package com.example.finalprojectandroidversion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class RegistrationForm extends Fragment {
    private Button btnStart;
    private View view;
    private Spinner userGenderSpinner,userAgeRangeSpinner, UserGender, UserAgeRange;
    private EditText userName, userPhoneNumber, userEmail;

    //defining variables that will capture user attributes
    //private int userId;
    private String userNames;
    private String phoneAddress;
    private String userEmailAddress;
    private String userGender;
    private String userAgeRange;
    private Button sendToDb;
    private String server_url ="http://172.31.101.225/finalproject/db_connection.php";
    AlertDialog.Builder builder;

    //a linked list to hold users
    private LinkedList<UserAttributes> user=new LinkedList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.registration_form, container, false);

        //taking user attributes
        userName=(EditText)view.findViewById(R.id.userName);
        userPhoneNumber=(EditText)view.findViewById(R.id.userPhoneNumber);
        userEmail=(EditText)view.findViewById(R.id.userEmailAddress);
        userGenderSpinner=(Spinner) view.findViewById(R.id.userGender);
        userAgeRangeSpinner=(Spinner) view.findViewById(R.id.userAgeRange);
        sendToDb =(Button) view.findViewById(R.id.btnSend);

        //taking inserted attributes
        userNames= userName.getText().toString();
        phoneAddress= userPhoneNumber.getText().toString();
        userEmailAddress= userEmail.getText().toString();


         //creating a new user
        UserAttributes attributes =updateUserToPost(userNames, phoneAddress, userEmailAddress, userGender, userAgeRange);

        //adding this user to the users list
        populateUsersList(attributes);

        //populating gender spinners
        ArrayAdapter<CharSequence> userGender =ArrayAdapter.createFromResource(getActivity(),
                R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        userGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userGenderSpinner.setAdapter(userGender);

        //populating ages spinner
        ArrayAdapter<CharSequence> userAgeRange =ArrayAdapter.createFromResource(getActivity(),
                R.array.age_range, android.R.layout.simple_spinner_dropdown_item);
        userAgeRange.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userAgeRangeSpinner.setAdapter(userAgeRange);

        //Volley
        sendToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
        return view;

    }
//    public void loadFrag(Fragment fragment){
//        FragmentManager manager=getFragmentManager();
//        FragmentTransaction transaction=manager.beginTransaction();
//        transaction.replace(R.id.homeframelayout,fragment);
//        transaction.commit();
//    }
    public UserAttributes updateUserToPost(String name, String phone, String email, String gender, String age){
        UserAttributes userAttributes =new UserAttributes(name, phone, email, gender, age);
        return userAttributes;
    }

    public LinkedList<UserAttributes> populateUsersList(UserAttributes userAttributes){
        this.user.add(userAttributes);
        return this.user;
    }

    public void sendData(){
        userGender= userGenderSpinner.getSelectedItem().toString();
        userAgeRange =userAgeRangeSpinner.getSelectedItem().toString();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                builder.setTitle("Server response");
                builder.setMessage("Response " + response);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int iter) {
                        userName.setText("");
                        userPhoneNumber.setText("");
                        userEmail.setText("");
//                        userGender.get;
//                        userAgeRangeSpinner=("";

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<String,String>();
                params.put("user name", userNames);
                params.put("Phone ", phoneAddress);
                params.put("Email ",userEmailAddress);
                params.put("gender ", userGender);
                params.put("Ages ", userAgeRange);
                return  params;
            }
        };
        ClassRequestQueue.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }
}
