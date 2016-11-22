package com.standerstudio.love.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

import at.grabner.circleprogress.CircleProgressView;

public class ResultActivity extends AppCompatActivity {
    public static InterstitialAd interstitialAd;
    public Random ran;
    int res;
    TextView tv;
    CircleProgressView mCircleView;
    SharedPreferences prefs;
    String me,her;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        prefs=getSharedPreferences(Storeprefrences.PREFS_NAAME, Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InterstitialAdmob_Load();
        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        ran = new Random();
        res=ran.nextInt(100-90)+90;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),ShareActivity.class);
                in.putExtra("percentage",res);
                startActivity(in);
            }
        });


        tv=(TextView)findViewById(R.id.title_result);
        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        mCircleView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(float value) {
               // Log.d(TAG, "Progress Changed: " + value);
            }
        });
        mCircleView.setValueAnimated(res, 4500);
        me=prefs.getString(Storeprefrences.MY_NAME,"");
        her=prefs.getString(Storeprefrences.HER_NAME,"");
        tv.setText(me+" "+"Love's"+" "+her+"\n"+"\n"+"\t"+"\t"+res+"%");

    }

    /*Assign and Load Intercialtial*/
    public void InterstitialAdmob_Load() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);}

    @Override
    public void onBackPressed() {
        if(interstitialAd.isLoaded())
        {
            interstitialAd.show();
        }
        super.onBackPressed();
    }
}
