package com.example.androidlabsfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar tBar = (Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(tBar);

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

}

