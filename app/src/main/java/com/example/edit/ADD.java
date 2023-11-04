package com.example.edit;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;

import java.io.IOException;

public class ADD extends AppCompatActivity {

    Bitmap bitmap;

    DataBaseHelper db;
    private static final int REQUEST_PERMISSION = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private Button selectImageButton, share, homes;
    EditText text;
    Button logoutButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db=new DataBaseHelper(this);
        selectImageButton = findViewById(R.id.selectImageButton);
        share = findViewById(R.id.sharebtn);
        text = findViewById(R.id.postText);
        homes = findViewById(R.id.homesbtn);
         logoutButton = findViewById(R.id.logoutButton);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToOtherActivity();
            }
        });





        homes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomes();
            }
        });




        share.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create model
                Post post = null;
                try {
                    post = new Post(-1,bitmap,
                            text.getText().toString()
                    );
                    Toast.makeText(ADD.this,
                            post.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ADD.this, "Enter Valid input",
                            Toast.LENGTH_SHORT).show();
                }
                if (db.addOne(post))  Toast.makeText(ADD.this, "added to database",
                        Toast.LENGTH_LONG).show();
                else Toast.makeText(ADD.this, " not added to database :(",
                        Toast.LENGTH_LONG).show();
            }});


        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGalleryIntent();
            }
        });




        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }


    }



    private void launchGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed to access the gallery
            } else {
                // Permission denied, handle accordingly
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    System.out.println("yes");
                    // Use the bitmap as needed
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void openHomes(){

        Intent intent=new Intent(this,home.class);
        startActivity(intent);
    }



    private void navigateToOtherActivity() {
        // Create an Intent to specify the target activity
        Intent intent = new Intent(ADD.this, MainActivity.class);

        // Optionally, pass any extra data to the target activity
        intent.putExtra("key", "value");

        // Start the target activity
        startActivity(intent);

        // Finish the current activity
        finish();
    }}
