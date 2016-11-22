package com.standerstudio.love.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ImageActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString,mystring,herstring;
    Button your,her;
    int checkno;
    SharedPreferences prefs;
    Bitmap bm_your,bm_her;
    ImageView img_my,img_her;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        mystring="";
        herstring="";
        prefs=getSharedPreferences(Storeprefrences.PREFS_NAAME, Context.MODE_PRIVATE);
        your=(Button)findViewById(R.id.button_dp);
        her=(Button)findViewById(R.id.button_dp1);
        img_my=(ImageView)findViewById(R.id.your_img) ;
        img_her=(ImageView)findViewById(R.id.part_img);
        prefs=getSharedPreferences(Storeprefrences.PREFS_NAAME, Context.MODE_PRIVATE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            if(mystring.equalsIgnoreCase("")) {

                Toast.makeText(getApplicationContext(),"Select your  photo First",Toast.LENGTH_SHORT).show();
            }
                else if(herstring.equalsIgnoreCase("")) {

                    Toast.makeText(getApplicationContext(),"Select your partner photo",Toast.LENGTH_SHORT).show();
                }
                else
            {
                Intent in=new Intent(getApplicationContext(),ResultActivity.class);
                startActivity(in);
            }


            }

        });
        your.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkno=1;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });
        her.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkno=2;

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
               if(checkno==1)
               {
                   // Set the Image in ImageView after decoding the String
                   img_my.setImageBitmap(BitmapFactory
                           .decodeFile(imgDecodableString));
                   mystring=imgDecodableString;
                   prefs.edit().putString(Storeprefrences.MY_DP,mystring).commit();
               }
                else  if(checkno==2)
               {
                   // Set the Image in ImageView after decoding the String
                   img_her.setImageBitmap(BitmapFactory
                           .decodeFile(imgDecodableString));
                   herstring=imgDecodableString;
                   prefs.edit().putString(Storeprefrences.HER_DP,herstring).commit();
               }


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
