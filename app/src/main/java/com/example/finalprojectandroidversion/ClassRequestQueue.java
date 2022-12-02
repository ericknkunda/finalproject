package com.example.finalprojectandroidversion;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ClassRequestQueue {
    private static  ClassRequestQueue classRequestQueue;
    private RequestQueue requestQueue;
    private Context context;
    private ClassRequestQueue(Context myContext){
        context=myContext;
        requestQueue =getRequestQueue();
    }

    public static synchronized ClassRequestQueue getInstance(Context context){
        if(classRequestQueue==null){
            classRequestQueue=new ClassRequestQueue(context);
        }
        return classRequestQueue;

    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());

        }
        return  requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);

    }

}
