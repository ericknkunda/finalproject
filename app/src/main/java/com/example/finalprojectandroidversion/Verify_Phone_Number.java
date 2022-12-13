package com.example.finalprojectandroidversion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Verify_Phone_Number extends AppCompatActivity {
    private Button verifyPhone;
    private EditText codeToVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phone_number);
        codeToVerify=(EditText) findViewById(R.id.txtVerification);
        verifyPhone =(Button) findViewById(R.id.btnVerifyPhone);

        //verifyinh if the code really exists
        verifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyVerificationCode(codeToVerify.toString());
            }
        });
    }
    public void verifyVerificationCode(String verificationCode){
        RequestQueue verificationCodeQueue =Volley.newRequestQueue(Verify_Phone_Number.this);
        StringRequest verificationCodeRequest =new StringRequest(Request.Method.GET, new Response.Listener<>(){

            @Override
            public void onResponse(Object response) {
                //
            }
        })

    }
}