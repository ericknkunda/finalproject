package com.example.finalprojectandroidversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnNext1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNext1=(Button) findViewById(R.id.btnNext1);
        btnNext1.setOnClickListener(new View.OnClickListener() {
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