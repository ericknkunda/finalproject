package com.example.finalprojectandroidversion;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
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
    private String server_url ="http://192.168.0.104/finalproject/apis/tableIUsers.php";
    AlertDialog.Builder builder;

    //a linked list to hold users
    private LinkedList<UserAttributesModal> user=new LinkedList<>();


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
        builder = new AlertDialog.Builder(getActivity());

        //taking inserted attributes
        userNames= userName.getText().toString();
        phoneAddress= userPhoneNumber.getText().toString();
        userEmailAddress= userEmail.getText().toString();


         //creating a new user
        UserAttributesModal attributes =updateUserToPost(userNames, phoneAddress, userEmailAddress, userGender, userAgeRange);

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
        sendToDb.setOnClickListener(view -> {
            sendData();
            Toast.makeText(getActivity(),"Congulatulations",Toast.LENGTH_LONG).show();
            selectPreference();
        });

        return view;

    }
//    public void loadFrag(Fragment fragment){
//        FragmentManager manager=getFragmentManager();
//        FragmentTransaction transaction=manager.beginTransaction();
//        transaction.replace(R.id.homeframelayout,fragment);
//        transaction.commit();
//    }
    public UserAttributesModal updateUserToPost(String name, String phone, String email, String gender, String age){
        UserAttributesModal userAttributes =new UserAttributesModal(name, phone, email, gender, age);
        return userAttributes;
    }

    public LinkedList<UserAttributesModal> populateUsersList(UserAttributesModal userAttributes){
        this.user.add(userAttributes);
        return this.user;
    }

    public void sendData() {
        //getting values
        userNames = userName.getText().toString();
        phoneAddress = userPhoneNumber.getText().toString();
        userEmailAddress = userEmail.getText().toString();
        userGender = userGenderSpinner.getSelectedItem().toString();
        userAgeRange = userAgeRangeSpinner.getSelectedItem().toString();

        if (userNames.isEmpty() || phoneAddress.isEmpty()
                || userEmailAddress.isEmpty() || userGender.equals("Select")||userAgeRange.equals("Select")) {
            Toast.makeText(getActivity(), "Some fields are empty", Toast.LENGTH_LONG).show();
            //System.exit(1);
        } else {
            //RequestQueue queue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, response -> {
                builder.setTitle("Server response");
                builder.setMessage("Response " + response);
                builder.setPositiveButton("OK", (dialogInterface, iter) -> {
                    userName.setText("");
                    userPhoneNumber.setText("");
                    userEmail.setText("");
//                        userGender.get;
//                        userAgeRangeSpinner=("";

                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }, Throwable::printStackTrace) {
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_name", userNames);
                    params.put("phone", phoneAddress);
                    params.put("email_address", userEmailAddress);
                    params.put("gender", userGender);
                    params.put("age_range", userAgeRange);
                    Log.i(getActivity().getLocalClassName(), "" + params);
                    return params;
                }
            };
            //queue.add(stringRequest);
            ClassRequestQueue.getInstance(getActivity()).addToRequestQueue(stringRequest);

        }
        //return  void;
    }
    public void selectPreference(){
        Intent intent =new Intent(getActivity(),CulturalComponentsList.class);
        startActivity(intent);

    }
}
