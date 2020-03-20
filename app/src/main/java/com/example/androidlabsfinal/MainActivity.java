package com.example.androidlabsfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(ACTIVITY_NAME, "In function: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();


        // load the saved email address.
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String emailToRestore;
        emailToRestore = prefs.getString(getString(R.string.saved_email_key), "");

        //set the value of the email edit text box to the stored email
        EditText emailEditBox = (EditText)findViewById(R.id.emailEditBox);
        emailEditBox.setText(emailToRestore);


        //click listener for login progresses to profile page
        //handler for the profile image button.
        Button login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profilePage);
            }
        });

    }


    public MainActivity() {
        super();
    }

    @Override
    protected void onDestroy() {
        Log.e(ACTIVITY_NAME, "In function: onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.e(ACTIVITY_NAME, "In function: onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.e(ACTIVITY_NAME, "In function: onStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.e(ACTIVITY_NAME, "In function: onPause");
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        //get the value of the email edit text box.
        EditText emailEditBox = (EditText)findViewById(R.id.emailEditBox);
        String emailToSave = emailEditBox.getText().toString();

        //store the email to the shared preferences file.
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(getString(R.string.saved_email_key),emailToSave);
        edit.commit();


    }

    @Override
    protected void onResume() {
        Log.e(ACTIVITY_NAME, "In function: onResume");
        super.onResume();
    }

}
