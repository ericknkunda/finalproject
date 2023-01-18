package com.example.finalprojectandroidversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class DashboardFragmentPage extends AppCompatActivity {
    private Toolbar dashboardTitle, dashboardMenus;
    private ActionBar actionBar;
    private DashboardFragmentContents dashboardFragmentContents;
    private HelpFragment helpFragment;
    private MenuView.ItemView help;
    private View view;
    private MenuView.ItemView helpView;

    private class StartActivityTask extends AsyncTask<Void, Void, Void> {
        private Intent intent;

        StartActivityTask(Intent intent) {
            this.intent = intent;
        }

        @Override
        protected Void doInBackground(Void... params) {
            startActivity(intent);
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_fragment);

        dashboardMenus = (Toolbar) findViewById(R.id.dashboardmenus);
        dashboardTitle = (Toolbar) findViewById(R.id.dashboardTitle);
        setSupportActionBar(dashboardTitle);
        //setSupportActionBar(dashboardMenus);
        dashboardFragmentContents =new DashboardFragmentContents();
        helpFragment =new HelpFragment();
        dashboardMenus.inflateMenu(R.menu.dashboardmenus);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        dashboardMenus.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // code to handle menu item click
                switch (item.getItemId()) {
                    case R.id.viewNotifications:
                        loadRegistrationFragment(new preferences_fragment());
                        break;
                    case R.id.help:
                        loadRegistrationFragment(new HelpFragment());
                        break;
                    case R.id.aboutUser:
                        loadRegistrationFragment(new aboutApplication());
                        break;
                    case R.id.logout:
                        Intent intent = new Intent(getApplicationContext(), RegistrationHome.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

//        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboardtitle, menu);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // code to handle menu item click
                    switch (item.getItemId()) {
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
                    }
                    return true;
                }
            });
        }

        return true;
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
        transaction.replace(R.id.dashboardFrame,fragment);
        transaction.commit();

    }
    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = null;
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.incorrect_password, null);

        // create the popup window
        int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 10, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}


