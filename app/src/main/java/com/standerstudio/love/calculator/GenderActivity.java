package com.standerstudio.love.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

public class GenderActivity extends AppCompatActivity {
    public static InterstitialAd interstitialAd;
    NativeExpressAdView adView;
    String name,gender;
    TextView male,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InterstitialAdmob_Load();
        adView = (NativeExpressAdView)findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
       male=(TextView)findViewById(R.id.txt_male);
        female=(TextView)findViewById(R.id.txt_female);

        male.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        male.setBackgroundResource(R.drawable.male_pre);
                        if(interstitialAd.isLoaded())
                        {
                            interstitialAd.show();
                        }
                        else
                        {
                            Intent in=new Intent(getApplicationContext(), NameActivity.class);
                            startActivity(in);
                        }

                        break;
                    case MotionEvent.ACTION_DOWN:
                        male.setBackgroundResource(R.drawable.male_pre);

                }


                return true;
            }
        });
        female.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        female.setBackgroundResource(R.drawable.female_pre);
                        if(interstitialAd.isLoaded())
                        {
                            interstitialAd.show();
                        }
                        else
                        {
                            Intent in=new Intent(getApplicationContext(), NameActivity.class);
                            startActivity(in);
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        female.setBackgroundResource(R.drawable.female_pre);

                }


                return true;
            }
        });

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent in=new Intent(getApplicationContext(),NameActivity.class);
                startActivity(in);
                super.onAdClosed();
            }
        });

       /* // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
       // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      // Apply the adapter to the spinner
        spinner.setAdapter(adapter);*/



    }

    /*Assign and Load Intercialtial*/
    public void InterstitialAdmob_Load() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);


    }

}
