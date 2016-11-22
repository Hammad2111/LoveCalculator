package com.standerstudio.love.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

public class NameActivity extends AppCompatActivity {
    EditText ed_your,eda_partner;
    String name,partner;
    SharedPreferences prefs;

    public static InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        InterstitialAdmob_Load();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               check_data();
            }
        });
        ed_your=(EditText)findViewById(R.id.editText);
        eda_partner=(EditText)findViewById(R.id.editTex1);
        prefs=getSharedPreferences(Storeprefrences.PREFS_NAAME, Context.MODE_PRIVATE);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                prefs.edit().putString(Storeprefrences.MY_NAME,name).commit();
                prefs.edit().putString(Storeprefrences.HER_NAME,partner).commit();
                Intent in=new Intent(getApplicationContext(),ImageActivity.class);
                startActivity(in);
                super.onAdClosed();
            }
        });
    }

    public void check_data()
    {
        name=ed_your.getText().toString();
        partner=eda_partner.getText().toString();
        if(name.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(),"Enter You Name",Toast.LENGTH_SHORT).show();

        }
         else if(partner.equalsIgnoreCase(""))
        {
            Toast.makeText(getApplicationContext(),"Enter You  partner Name",Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(interstitialAd.isLoaded())
            {
                interstitialAd.show();
            }
            else
            {
                prefs.edit().putString(Storeprefrences.MY_NAME,name).commit();
                prefs.edit().putString(Storeprefrences.HER_NAME,partner).commit();
                Intent in=new Intent(getApplicationContext(),ImageActivity.class);
                startActivity(in);
            }

        }

    }


    /*Assign and Load Intercialtial*/
    public void InterstitialAdmob_Load() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);


    }
}
