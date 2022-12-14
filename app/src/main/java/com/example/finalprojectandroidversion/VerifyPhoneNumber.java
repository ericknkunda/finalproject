package com.example.finalprojectandroidversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;

public class VerifyPhoneNumber extends AppCompatActivity {
    private Button verifyPhone;
    private EditText codeToVerify;
    private ActionBar actionBar;
    private  String lastVerCodeApi ="http://172.31.101.225/finalproject/apis/VerificationCode.php";
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phone_number);
        this.setTitle("Phone Number Verification");
        codeToVerify=(EditText) findViewById(R.id.txtVerification);
        verifyPhone =(Button) findViewById(R.id.btnVerifyPhone);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String codeString =codeToVerify.getText().toString();
        verificationCode =verifyVerificationCode(lastVerCodeApi);
        Log.d("Verification code: ",verificationCode);
        Log.d("Code String: ",codeString);


        //verifyinh if the code really exists
        verifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(codeString==verificationCode){
                    Toast.makeText(VerifyPhoneNumber.this,"Welcome", Toast.LENGTH_SHORT).show();
                    startPreferencesList();
                }
                else{
                    Toast.makeText(VerifyPhoneNumber.this, "Code Not Valid",Toast.LENGTH_SHORT).show();;
                }

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


    String responseString ="";
    public  String verifyVerificationCode(String url) {

        boolean isPhoneNumberVerified = (false);
        RequestQueue verificationCodeQueue = Volley.newRequestQueue(VerifyPhoneNumber.this);
        StringRequest verificationCodeRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response: ", "" + response);
                try {
                    JSONArray verificationCodeArray = new JSONArray(response);
                    for (int i = 0; i < verificationCodeArray.length(); i++) {
                        JSONObject code = verificationCodeArray.getJSONObject(i);
                        responseString =code.getString("Code").toString();
//                        if (verificationCode == code.getString("Code")) {
//                            isPhoneNumberVerified.set(true);
//                        }
                    }
                    //return response;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //return response;
        }, error -> {
            //error codes;
        });

        verificationCodeQueue.add(verificationCodeRequest);
        return responseString;
    }

    public void startPreferencesList(){
        Intent intent =new Intent(getApplicationContext(), CulturalComponentsList.class);
        startActivity(intent);
    }

}