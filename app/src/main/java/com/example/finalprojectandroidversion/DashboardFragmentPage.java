package com.example.finalprojectandroidversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DashboardFragmentPage extends AppCompatActivity {
    private Toolbar dashboardTitle, dashboardMenus;
    private ActionBar actionBar;
    private DashboardFragmentContents dashboardFragmentContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_fragment);

        dashboardMenus = (Toolbar) findViewById(R.id.dashboardmenus);
        dashboardTitle = (Toolbar) findViewById(R.id.dashboardTitle);
        setSupportActionBar(dashboardTitle);
        dashboardFragmentContents =new DashboardFragmentContents();
        dashboardMenus.inflateMenu(R.menu.dashboardmenus);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboardtitle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.preferences:
                loadRegistrationFragment(new DashboardFragmentContents());
                break;
            case R.id.userHistory:
                loadRegistrationFragment(new DashboardFragmentContents());
                break;
            case R.id.userRequest:
                loadRegistrationFragment(new DashboardFragmentContents());
                break;
            case R.id.accountSettings:
                loadRegistrationFragment(new DashboardFragmentContents());
                break;
            case R.id.viewNotifications:
                loadRegistrationFragment(new DashboardFragmentContents());
                break;case R.id.help:
                loadRegistrationFragment(new DashboardFragmentContents());
                break;
            case R.id.aboutUser:
                loadRegistrationFragment(new DashboardFragmentContents());
                break;
        }
        return super.onOptionsItemSelected(item);

    }



    public void loadRegistrationFragment(Fragment fragment){
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.dashboardFrame,fragment);
        transaction.commit();

    }
}