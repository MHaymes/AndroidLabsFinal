package com.example.androidlabsfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final int RESPONSE_RETURN_TO_LOGIN = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar tBar = (Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(tBar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu items
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message ="";
        switch (item.getItemId()) {
            case R.id.choice1:
                //toast saying You clicked on item1 .
                message = "You Clicked on Item 1";
                break;
            case R.id.choice2:
                message = "You Clicked on Item 2";
                break;
            case R.id.choice3:
                message = "You Clicked on Item 3";
                break;
            case R.id.choice4:
                message = "You Clicked on The Overflow Menu";
                break;
        }

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT)
                .show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String message = null;

        Intent nextActivity = null;
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        switch(item.getItemId())
        {
            case R.id.drawerChatButton:
                message = "You clicked on Chat";
                nextActivity = new Intent(TestToolbar.this, ChatRoomActivity.class);
                break;
            case R.id.drawerWeatherButton:
                message = "You clicked on Weather";
                nextActivity = new Intent(TestToolbar.this, WeatherActivity.class);
                break;
            case R.id.drawerLoginButton:
                message = "You clicked on Login";
                //fix this to work with the startactivityforresult code.

                //put the extra in.

                setResult(RESPONSE_RETURN_TO_LOGIN, new Intent());
                finish();
                break;
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_SHORT).show();

        //launch the intent.
        if(nextActivity != null) {startActivity(nextActivity);}

        return false;

    }
}

