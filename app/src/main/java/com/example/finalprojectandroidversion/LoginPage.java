package com.example.finalprojectandroidversion;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


public class LoginPage extends Fragment {
    private View view;
    private Button login;
    private EditText userEmail;
    private EditText userPasswod;
    private String email ="", password ="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.login_layout, container, false);
        login =(Button) view.findViewById(R.id.cirLoginButton);
        userEmail =(EditText) view.findViewById(R.id.editTextEmail);
        userPasswod =(EditText)view.findViewById(R.id.editTextPassword);
         //new activity

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email =userEmail.getText().toString();
                password =userPasswod.getText().toString();
                if(email.equals("macnkunda@gmail.com") && password.equals("0780087529")) {
                    startDashBoard();
                }
                else{
                    onButtonShowPopupWindowClick(view);
                }
            }
        });


        return view;
    }

    //start dashboard
    public  void startDashBoard(){
        Intent dashBoard =new Intent(getActivity(),DashboardFragmentPage.class);
        startActivity(dashBoard);
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = null;
        inflater = (LayoutInflater) getActivity(). getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.incorrect_password, null);

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