package com.example.finalprojectandroidversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class VerifyPhoneNumber extends AppCompatActivity {
    private Button verifyPhone;
    private EditText codeToVerify;
    private ActionBar actionBar;
    private  String lastVerCodeApi ="http://172.31.101.225/finalproject/apis/VerificationCode";
    private String verificationCode[]={""};
    private  List<String> responseString;
    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phone_number);


        this.setTitle("Phone Number Verification");
        codeToVerify=(EditText) findViewById(R.id.txtVerification);
        verifyPhone =(Button) findViewById(R.id.btnVerifyPhone);
        toolbar=(androidx.appcompat.widget.Toolbar) findViewById(R.id.codeVerificationToolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


//        actionBar=getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        verifyVerificationCode(lastVerCodeApi);
//        Log.d("Verification code: ",""+responseString);

            responseString =new ArrayList<>();
            RequestQueue verificationCodeQueue = Volley.newRequestQueue(VerifyPhoneNumber.this);
            StringRequest verificationCodeRequest = new StringRequest(Request.Method.GET, lastVerCodeApi, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response: ", "" + response);
                    try {
                        JSONArray verificationCodeArray = new JSONArray(response);
                        for (int i = 0; i < verificationCodeArray.length(); i++) {
                            JSONObject code = verificationCodeArray.getJSONObject(i);
                            responseString.add(code.getString("Code"));
                            Log.d("Code Internally", code.getString("Code"));

                            verifyPhone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String codeString =codeToVerify.getText().toString();
                                    try {
                                        if(codeString.contains(code.getString("Code"))){
                                            Log.d("Code String: ",codeString);
                                            Toast.makeText(VerifyPhoneNumber.this,"Welcome", Toast.LENGTH_SHORT).show();
                                            startPreferencesList();
                                        }
                                        else{
                                            Toast.makeText(VerifyPhoneNumber.this, "Code Not Valid",Toast.LENGTH_SHORT).show();;
                                            //startPreferencesList();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
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



//    public  void verifyVerificationCode(String url) {
//        responseString=new ArrayList<>();
//        boolean isPhoneNumberVerified = (false);
//
////        Log.d("Code Internally above return", responseString[0]);
////        return responseString[0];
//    }

    public void startPreferencesList(){
        Intent intent =new Intent(this, CulturalComponentsList.class);
        startActivity(intent);
    }

}