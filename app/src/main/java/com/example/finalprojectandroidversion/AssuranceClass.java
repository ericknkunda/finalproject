package com.example.finalprojectandroidversion;


import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AssuranceClass extends AppCompatActivity {
    private ActionBar actionBar;
    private Button buttonStartRegistrationForm;
    private TextView assuranceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assurance);
        buttonStartRegistrationForm =(Button) findViewById(R.id.btnStart);
        assuranceTextView=(TextView) findViewById(R.id.assuranceTextView);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        buttonStartRegistrationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSubscription();
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

    public void startSubscription(){
        Intent intent=new Intent(getApplicationContext(),RegistrationHome.class);
        startActivity(intent);

    }
}
