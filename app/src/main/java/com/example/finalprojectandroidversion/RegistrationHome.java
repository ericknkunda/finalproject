package com.example.finalprojectandroidversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RegistrationHome extends AppCompatActivity {
    private Button registrationButton, login;
    private ActionBar actionBar;
    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_home);

        //changing the title
        setTitle("registration page");
        registrationButton=(Button) findViewById(R.id.btnRegistration);
        login =(Button) findViewById(R.id.btnLogin);
        toolbar=(androidx.appcompat.widget.Toolbar) findViewById(R.id.registrationToolBar) ;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        //saveFormData=(Button)findViewById(R.id.saveFormAndContinue);

        //activating actionbar
//        actionBar=getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRegistrationFragment(new RegistrationForm());

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRegistrationFragment(new LoginPage());
            }
        });

//        saveFormData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendToDatabase();
//            }
//        });

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
    public void loadRegistrationFragment(Fragment fragment){
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.frameLayoutAtRegistration,fragment);
        transaction.commit();

    }

    public void sendToDatabase(){

    }
}