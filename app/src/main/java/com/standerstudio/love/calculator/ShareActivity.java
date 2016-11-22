package com.standerstudio.love.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShareActivity extends AppCompatActivity {

    SharedPreferences prefs;
    ImageView img_my,img_her;
    TextView lovey,partner;
    Button share,save,rate,more;
    String name,hernname,yourdp,herdp;
    LinearLayout photage;
    RelativeLayout mRootLayout;
    int prsnt;
    public static InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InterstitialAdmob_Load();
        prsnt=getIntent().getIntExtra("percentage",0);
        prefs=getSharedPreferences(Storeprefrences.PREFS_NAAME, Context.MODE_PRIVATE);
        share=(Button)findViewById(R.id.btn_share);
        save=(Button)findViewById(R.id.btn_save);
        more=(Button)findViewById(R.id.btn_more);
        rate=(Button)findViewById(R.id.btn_rate);
        img_my=(ImageView)findViewById(R.id.your_img1) ;
        img_her=(ImageView)findViewById(R.id.part_img1);
        lovey=(TextView)findViewById(R.id.nametag);
        partner=(TextView)findViewById(R.id.persentage);
        photage=(LinearLayout) findViewById(R.id.photage);
        mRootLayout=(RelativeLayout)findViewById(R.id.mRootLayout);
        name=prefs.getString(Storeprefrences.MY_NAME,"");
        hernname=prefs.getString(Storeprefrences.HER_NAME,"");
        yourdp=prefs.getString(Storeprefrences.MY_DP,"");
        herdp=prefs.getString(Storeprefrences.HER_DP,"");
        img_my.setImageBitmap(BitmapFactory
                .decodeFile(yourdp));
        img_her.setImageBitmap(BitmapFactory
                .decodeFile(herdp));
        lovey.setText(""+name+" Love's"+" "+hernname);
        partner.setText("   "+prsnt+"%");
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                share_image();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_image();
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }

            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            Intent in=new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Standard+Studio"));
                startActivity(in);

            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.standerstudio.love.calculator"));
                startActivity(in);


            }
        });
    }

    //save image
    public void save_image()
    {
        photage.setBackgroundColor(getResources().getColor(R.color.white));
        photage.setDrawingCacheEnabled(true);

        Bitmap bitmap;

        bitmap=photage.getDrawingCache();
        try {
            File file, f = null;

            file = new File(Environment.getExternalStorageDirectory() + "/Love Calculator");
            if (!file.exists()) {
                file.mkdirs();
            }

            String str = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            str = "love_" + str + ".jpg";
            f = new File(file.getAbsolutePath() + "/" + str);
            Log.e("path...", f.getAbsolutePath() + "");

            FileOutputStream ostream = new FileOutputStream(f);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);

            ostream.close();

            MediaScannerConnection.scanFile(ShareActivity.this,
                    new String[]{f.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);

                        }
                    });
            Snackbar snackbar = Snackbar.make(mRootLayout, "Image Saved Successfully", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
            photage.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //share image
    public  void share_image()
    {
        photage.setBackgroundColor(getResources().getColor(R.color.white));
        photage.setDrawingCacheEnabled(true);
        Bitmap bitmap;
        bitmap=photage.getDrawingCache();
        Uri imageUri = getImageUri(bitmap);

        photage.setDrawingCacheEnabled(false);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(shareIntent);

    }
    //get URI
    public Uri getImageUri( Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /*Assign and Load Intercialtial*/
    public void InterstitialAdmob_Load() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.intercitial_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);}

}
