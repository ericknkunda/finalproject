package com.example.finalprojectandroidversion;

import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginPage extends Fragment {
    private View view;
    private Button login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.login_page, container, false);
        login =(Button) view.findViewById(R.id.login);
         //new activity

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDashBoard();
            }
        });


        return view;
    }

    //start dashboard
    public  void startDashBoard(){
        Intent dashBoard =new Intent(getActivity(),DashboardFragmentPage.class);
        startActivity(dashBoard);
    }
}