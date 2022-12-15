package com.example.finalprojectandroidversion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class intelligentRecommenderSystem extends AppCompatActivity {
    private Button btnNextToAssurancePage;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNextToAssurancePage =(Button) findViewById(R.id.btnNext1);
        toolbar=(Toolbar) findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);
        btnNextToAssurancePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragmentPages();
            }
        });
    }

    public void loadFragmentPages() {

        Intent intent =new Intent(getApplicationContext(), AssuranceClass.class);
        startActivity(intent);
    }

}