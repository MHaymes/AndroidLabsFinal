package com.example.androidlabsfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragFrameLayout, new DetailsFragment())
                .commit();
    }
}
