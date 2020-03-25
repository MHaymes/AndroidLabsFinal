package com.example.androidlabsfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        //get the data that was passed
        Bundle dataToPass = getIntent().getExtras();

        //pass data and set the fragment content.
        DetailsFragment dFragment = new DetailsFragment();
        dFragment.setArguments( dataToPass );
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragFrameLayout, dFragment)
                .commit();


/*        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragFrameLayout, new DetailsFragment())
                .commit(); */
    }
}
