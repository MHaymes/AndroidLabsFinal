package com.example.androidlabsfinal;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton mImageButton;

    private void dispatchTakePictureIntent() {
        Log.e(ACTIVITY_NAME, "In function: dispatchTakePictureIntent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onPause() {
        Log.e(ACTIVITY_NAME, "In function: onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e(ACTIVITY_NAME, "In function: onResume");
        super.onResume();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(ACTIVITY_NAME, "In function: onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(ACTIVITY_NAME, "In function: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        //click listener for login progresses to profile page
        //handler for the profile image button.
        Button goToChat = (Button)findViewById(R.id.goToChatButton);
        goToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatPage = new Intent(getApplicationContext(), ChatRoomActivity.class);
                startActivity(chatPage);
            }
        });


        mImageButton = (ImageButton)findViewById(R.id.profilePicture);


        //handler for the profile image button.
        ImageButton imgButton = (ImageButton)findViewById(R.id.profilePicture);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

}
