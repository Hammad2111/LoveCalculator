package com.standerstudio.love.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.splash.SplashConfig;

public class MainActivity extends AppCompatActivity {
    Animation zoomin, zoomout; //declared as public
    public static InterstitialAd interstitialAd;

    ImageView img_heart;
    Button start;
    StartAppAd startappad = new StartAppAd(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StartAppAd.showSplash(this, savedInstanceState,
                new SplashConfig()
                        .setTheme(SplashConfig.Theme.ASHEN_SKY)
                        .setLogo(R.drawable.icon)
                        .setAppName(getResources().getString(R.string.app_name))

        );

        InterstitialAdmob_Load();
        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        //Intlize widgets
        img_heart=(ImageView)findViewById(R.id.loveimg);
        start=(Button)findViewById(R.id.button_start);
        // animation
        zoomin = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        img_heart.setAnimation(zoomin);
        img_heart.setAnimation(zoomout);
        zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img_heart.startAnimation(zoomout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                img_heart.startAnimation(zoomin);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(interstitialAd.isLoaded())
                {

                    interstitialAd.show();
                }
                else
                {
                    Intent in=new Intent(getApplicationContext(),GenderActivity.class);
                    startActivity(in);
                }
            }
        });
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent in=new Intent(getApplicationContext(),GenderActivity.class);
                startActivity(in);
                super.onAdClosed();
            }
        });
    }

    /*Assign and Load Intercialtial*/
    public void InterstitialAdmob_Load() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);


    }

}
