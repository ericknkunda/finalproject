package com.example.finalprojectandroidversion;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WIFI_SERVICE;

import static androidx.core.app.AppOpsManagerCompat.*;
import static androidx.core.app.AppOpsManagerCompat.*;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.app.Fragment;

import android.text.format.Formatter;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


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
    private View popupView;
    private String server_url="http://192.168.43.217/finalproject/apis/tableIUsers.php";;
    private String getRegisteredUsers="http://192.168.43.217/finalproject/apis/registered_users.php";;
    AlertDialog.Builder builder;
    private final AtomicBoolean isPhoneUnique =new AtomicBoolean(true);
    boolean placeHolder;
    static boolean isUnique;
    boolean takeUniqueVal;
    boolean isAllFilled=true;
   static JSONArray registeredUsersArray;

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
        System.out.println("About to load users...");
        loadUsers();

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

        sendToDb.setOnClickListener(view1 -> {
            System.out.println("Clicked...");
            try {
                sendData();
                TimeUnit.SECONDS.sleep(2);
                verifyPhoneNumber();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return view;
    }

    public UserAttributesModal updateUserToPost(String name, String phone, String email, String gender, String age){
        UserAttributesModal userAttributes =new UserAttributesModal(name, phone, email, gender, age);
        return userAttributes;
    }

    public LinkedList<UserAttributesModal> populateUsersList(UserAttributesModal userAttributes){
        this.user.add(userAttributes);
        return this.user;
    }

    private void loadUsers(){
        System.out.println("Loading users...");
        RequestQueue registeredUsersQueue = Volley.newRequestQueue(getContext());
        StringRequest requestRegisteredUsers = new StringRequest(Request.Method.GET, getRegisteredUsers, response -> {
            System.out.println("Response "+response);
            try {
                registeredUsersArray = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            System.err.println("Error here "+error);
            //some codes

        });
        registeredUsersQueue.add(requestRegisteredUsers);

    }
    public boolean sendData() throws JSONException {
        //tomicBoolean isAllFilled newfalse;
        userNames = userName.getText().toString();
        phoneAddress = userPhoneNumber.getText().toString();
        userEmailAddress = userEmail.getText().toString();
        userGender = userGenderSpinner.getSelectedItem().toString();
        userAgeRange = userAgeRangeSpinner.getSelectedItem().toString();

//        if (userNames.isEmpty() || phoneAddress.isEmpty()
//                || userEmailAddress.isEmpty() || userGender.contains("Select")
//                ||userAgeRange.contains("Select")) {
//            //Toast.makeText(getActivity(), "Some fields are empty", Toast.LENGTH_LONG).show();
//            isAllFilled =false;
//        }
//        if(userNames.length()<5 || userNames.contains(" ")){
//            isAllFilled=false;
//
//        }
//         if(phoneAddress.length()<10 || phoneAddress.length()>13){
//            isAllFilled=false;
//        }
//        if(!(userEmailAddress.contains("@"))){
//            isAllFilled=false;
//        }
//        System.out.println("Is all filled "+isAllFilled);
//        if(isAllFilled){
//            for(int i=0;i<registeredUsersArray.length();i++){
//                JSONObject object = registeredUsersArray.getJSONObject(i);
//                System.out.println(object);
//            if (object.getString("phone") .contains( phoneAddress) || object.getString("email_address") .contains( String.valueOf( userEmailAddress))) {
//                isUnique=false;
//            }
//            else{
//                isUnique=true;
//            }
//                System.out.println("is unique "+isUnique);
//            }
//            if(isUnique==false) {
//                Log.d("==========================================Value of is unique",""+isUnique);
//                onButtonShowPopupWindowClick(view);
//                return  false;
//            }
//            else{

                //isAllFilled =true;
                RequestQueue queue =Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, response -> {
                    builder.setTitle("Server response");
                    builder.setMessage("Response Were fetched" );
                    builder.setPositiveButton("OK", (dialogInterface, iter) -> {
                        userName.setText("");
                        userPhoneNumber.setText("");
                        userEmail.setText("");


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
                queue.add(stringRequest);
                return isAllFilled;
            }
       // }
//        return false;
//
//    }



    //verifying phone number
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

    public void onphoneNotUnique(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = null;
        inflater = (LayoutInflater) getActivity(). getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.phonenotunique, null);

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
