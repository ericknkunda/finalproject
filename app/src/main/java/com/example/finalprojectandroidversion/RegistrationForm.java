package com.example.finalprojectandroidversion;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import static androidx.core.app.AppOpsManagerCompat.*;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


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
    private String server_url ="http://172.17.22.37/finalproject/apis/tableIUsers.php";
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
        sendToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkFilleds =sendData();
                if(checkFilleds){
                    Toast.makeText(getActivity(),"Congulatulations",Toast.LENGTH_LONG).show();
                    //wait a little bit to fetch last verification code
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    verifyPhoneNumber();
                }else{
                    onButtonShowPopupWindowClick(view);
                    //getActivity().dismiss
                }
            }
        } );

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

    public boolean sendData() {
        boolean isAllFilled=false;
        userNames = userName.getText().toString();
        phoneAddress = userPhoneNumber.getText().toString();
        userEmailAddress = userEmail.getText().toString();
        userGender = userGenderSpinner.getSelectedItem().toString();
        userAgeRange = userAgeRangeSpinner.getSelectedItem().toString();

        if (userNames.isEmpty() || phoneAddress.isEmpty()
                || userEmailAddress.isEmpty() || userGender.contains("Select")
                ||userAgeRange.contains("Select")) {
            //Toast.makeText(getActivity(), "Some fields are empty", Toast.LENGTH_LONG).show();
            isAllFilled =false;
        } else if(userNames.length()<5 || userNames.contains(" ")){
            isAllFilled=false;

        }
        else if(phoneAddress.length()<10 || phoneAddress.length()>13){
            isAllFilled=false;
        }
        else if(!(userEmailAddress.contains("@"))){
            isAllFilled=false;
        }
        else {
            //RequestQueue queue = Volley.newRequestQueue(getActivity());
            isAllFilled =true;
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
                    params.put("code", String.valueOf(new Random().nextInt(999999)));
                    Log.i(getActivity().getLocalClassName(), "" + params);
                    return params;
                }
            };
            //queue.add(stringRequest);
            ClassRequestQueue.getInstance(getActivity()).addToRequestQueue(stringRequest);

        }
        return  isAllFilled;
    }
    public void verifyPhoneNumber(){
        Intent intent =new Intent(getActivity(), VerifyPhoneNumber.class);
        startActivity(intent);

    }

    public void retainActivity(){
        Intent intent =new Intent(getActivity(), RegistrationHome.class);
        startActivity(intent);
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = null;
            inflater = (LayoutInflater) getActivity(). getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popupmesage, null);

        // create the popup window
        int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 10, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

}
