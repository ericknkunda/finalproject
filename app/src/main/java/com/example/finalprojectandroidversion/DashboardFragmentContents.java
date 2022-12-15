package com.example.finalprojectandroidversion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DashboardFragmentContents extends Fragment {
    private TextView dashaboardMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.dashboard_contents, container, false);
        dashaboardMessage =(TextView)view.findViewById(R.id.dashboard);

        return view;
    }
}