package com.example.rainbowflashlight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class Brightscreenactivity extends AppCompatActivity {

    private ImageView[] imageViews = new ImageView[7];
    private RelativeLayout Fullscreenlayout;
    private ImageView Backbtn;
    private ImageView Backbtnbrightscreen;
    private TextView BrightTv, ChooseTv;
    private boolean imageViewSelected = false;

//    private InterstitialAd mInterstitialAd;
//    private boolean isadloaded = false;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_brightscreenactivity);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        InterstitialAd.load(this,"ca-app-pub-4238387201988261/8610100028", adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                mInterstitialAd = interstitialAd;
//                isadloaded = true;
//                Log.i(TAG, "onAdLoaded");
//            }
//
//            @Override public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                // Handle the errorLog.d(TAG, loadAdError.toString());mInterstitialAd = null;
//            }
//        });

        //screen full bright code
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;


        imageViews[0] = findViewById(R.id.centergreenimg);
        imageViews[1] = findViewById(R.id.redimg);
        imageViews[2] = findViewById(R.id.purpleimg);
        imageViews[3] = findViewById(R.id.brownimg);
        imageViews[4] = findViewById(R.id.yellowimg);
        imageViews[5] = findViewById(R.id.blueimg);
        imageViews[6] = findViewById(R.id.lightblueimg);
        Fullscreenlayout = findViewById(R.id.fullscreen);
        Backbtn = findViewById(R.id.backbtn);
        Backbtnbrightscreen = findViewById(R.id.backbtnbrightscreen);
        BrightTv = findViewById(R.id.brightscreentv);
        ChooseTv = findViewById(R.id.choosetv);

        Backbtn.setVisibility(View.VISIBLE);
        Backbtnbrightscreen.setVisibility(View.GONE);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewSelected) {
                    for (ImageView imageView : imageViews) {
                        imageView.setVisibility(View.VISIBLE);
                        BrightTv.setVisibility(View.VISIBLE);
                        ChooseTv.setVisibility(View.VISIBLE);
                    }
                    Fullscreenlayout.setVisibility(View.GONE);
                    imageViewSelected = false;
                } else {
                    Intent intent = new Intent(Brightscreenactivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        Backbtnbrightscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed2();
            }
        });


        for (int i = 0; i < imageViews.length; i++) {
            final int position = i;
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bgColor = 0;
                    switch (position) {
                        case 0:
                            bgColor = ContextCompat.getColor(Brightscreenactivity.this, R.color.green);
                            break;
                        case 1:
                            bgColor = ContextCompat.getColor(Brightscreenactivity.this, R.color.red);
                            break;
                        case 2:
                            bgColor = ContextCompat.getColor(Brightscreenactivity.this, R.color.purple);
                            break;
                        case 3:
                            bgColor = ContextCompat.getColor(Brightscreenactivity.this, R.color.brown);
                            break;
                        case 4:
                            bgColor = ContextCompat.getColor(Brightscreenactivity.this, R.color.yellow);
                            break;
                        case 5:
                            bgColor = ContextCompat.getColor(Brightscreenactivity.this, R.color.blue);
                            break;
                        case 6:
                            bgColor = ContextCompat.getColor(Brightscreenactivity.this, R.color.lightblue);
                            break;
                    }

                    // set the background color of fullscreen layout to selected color
                    Fullscreenlayout.setBackgroundColor(bgColor);

                    // hide all the other imageviews except for the back button
                    for (int j = 0; j < imageViews.length; j++) {
                        if (j != position) {
                            imageViews[j].setVisibility(View.GONE);
                            BrightTv.setVisibility(View.GONE);
                            ChooseTv.setVisibility(View.GONE);
                            Backbtn.setVisibility(View.GONE);
                            Backbtnbrightscreen.setVisibility(View.VISIBLE);
                        }
                    }
                    imageViews[position].setVisibility(View.GONE);

                    // animate the spread-out effect
                    int width = Fullscreenlayout.getWidth();
                    int height = Fullscreenlayout.getHeight();
                    float radius = (float) Math.sqrt(width * width + height * height);

                    Animator animator = ViewAnimationUtils.createCircularReveal(
                            Fullscreenlayout,
                            width / 2,
                            height / 2,
                            0,
                            radius);

                    animator.setDuration(500);
                    animator.start();
                    Fullscreenlayout.setVisibility(View.VISIBLE);
                }
            });
        }
    }
    public void onBackPressed2() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (ImageView imageView : imageViews) {
                    imageView.setVisibility(View.VISIBLE);
                    BrightTv.setVisibility(View.VISIBLE);
                    ChooseTv.setVisibility(View.VISIBLE);
                    Backbtn.setVisibility(View.VISIBLE);
                    Backbtnbrightscreen.setVisibility(View.GONE);
                }
            }
        }, 600);

        boolean imageViewSelected = false;
        for (int i = 0; i < imageViews.length; i++) {
            if (imageViews[i].getVisibility() == View.GONE) {
                imageViewSelected = true;
                break;
            }
        }

    // animate the collapse effect
        final int width = Fullscreenlayout.getWidth();
        final int height = Fullscreenlayout.getHeight();
        final float radius = (float) Math.sqrt(width * width + height * height);

        final Animator animator = ViewAnimationUtils.createCircularReveal(
                Fullscreenlayout,
                width / 2,
                height / 2,
                radius,
                0);

        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // reset the background color of fullscreen layout to transparent
                Fullscreenlayout.setBackgroundColor(Color.WHITE);
            }
        });
        animator.start();
    }

    @Override
    public void onBackPressed() {
        boolean imageViewSelected = false;
        for (int i = 0; i < imageViews.length; i++) {
            if (imageViews[i].getVisibility() == View.GONE) {
                imageViewSelected = true;
                break;
            }
        }



        if (imageViewSelected) {
            // show all the image views and text views
            for (ImageView imageView : imageViews) {
                imageView.setVisibility(View.VISIBLE);
            }
            BrightTv.setVisibility(View.VISIBLE);
            ChooseTv.setVisibility(View.VISIBLE);
            Backbtn.setVisibility(View.VISIBLE);
            Backbtnbrightscreen.setVisibility(View.GONE);


            // animate the collapse effect
            int width = Fullscreenlayout.getWidth();
            int height = Fullscreenlayout.getHeight();
            float radius = (float) Math.sqrt(width * width + height * height);

            Animator animator = ViewAnimationUtils.createCircularReveal(
                    Fullscreenlayout,
                    width / 2,
                    height / 2,
                    radius,
                    0);

            animator.setDuration(500);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // reset the background color of fullscreen layout to transparent
                    Fullscreenlayout.setBackgroundColor(Color.WHITE);
                    Fullscreenlayout.setVisibility(View.GONE);
                    Backbtn.setVisibility(View.VISIBLE);
                    Backbtnbrightscreen.setVisibility(View.GONE);
                }
            });
            animator.start();
        } else {
            Intent intent = new Intent();
            if (imageViewSelected) {
                intent = new Intent(Brightscreenactivity.this, Brightscreenactivity.class);
            } else {
                intent = new Intent(Brightscreenactivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onDestroy() {
//        if (isadloaded) {
//            mInterstitialAd.show(Brightscreenactivity.this);
//            super.onDestroy();
//        } else {
            super.onDestroy();

//        }
    }
}