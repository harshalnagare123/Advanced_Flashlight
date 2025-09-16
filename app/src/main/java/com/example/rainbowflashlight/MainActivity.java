package com.example.rainbowflashlight;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.revenuecat.purchases.CustomerInfo;
import com.revenuecat.purchases.EntitlementInfo;
import com.revenuecat.purchases.Purchases;
import com.revenuecat.purchases.PurchasesConfiguration;
import com.revenuecat.purchases.PurchasesError;
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback;
import com.revenuecat.purchases.ui.revenuecatui.activity.PaywallActivityLauncher;
import com.revenuecat.purchases.ui.revenuecatui.activity.PaywallResult;

import java.util.concurrent.Semaphore;

public class MainActivity extends AppCompatActivity {

    private ImageView flashlightButton;
    private CardView mSosButton;
    private CardView AboutButton;
    private CardView mStrobeButton;
    private CardView mBrightScreenButton;

    private boolean isSosEnabled = false;
    private boolean isSosLooping = false;
    private Thread sosThread;


    private CameraManager cameraManager;
    private String cameraId;
    private CameraManager mCameraManager;
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);

    private boolean isStrobeEnabled = false;
    private boolean isStrobeLooping = false;
    private int strobeDuration = 100;
    private Thread strobeThread;
    private SeekBar strobeSeekBar;


    private MediaActionSound mMediaActionSound;

    private boolean isFlashlightOn = false;

    private FirebaseAnalytics mFirebaseAnalytics;

    private AdView mAdView;

    private InterstitialAd mInterstitialAd;
    private boolean isAdLoaded = false;
    private boolean isFirstTimeCreate = true;

    private static final String REQUIRED_ENTITLEMENT_IDENTIFIER = "prime"; // Replace with your entitlement ID
    public static boolean isPrimeUser = false;
    private PaywallActivityLauncher launcher;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashlightButton = findViewById(R.id.powerbutton);
        mSosButton = findViewById(R.id.soscard);
        AboutButton = findViewById(R.id.nvcard);
        mStrobeButton = findViewById(R.id.strobecard);
        mBrightScreenButton = findViewById(R.id.bscard);
        strobeSeekBar = findViewById(R.id.strobe_seekbar);

        ImageView profileImage = findViewById(R.id.profileIcon);
        ImageView noAdsImage = findViewById(R.id.premiumIcon);

        // Set up profile image click
        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });

        // Set up paywall for premium users
        noAdsImage.setOnClickListener(v -> launchPaywallActivity());

        // Google Sign-In
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String userEmail = (account != null) ? account.getEmail() : "guest_user";

        // Configure RevenueCat
        Purchases.configure(new PurchasesConfiguration.Builder(this, "Revenuecat_api_key")
                .appUserID(userEmail)
                .build());

        // Initialize PaywallActivityLauncher
        launcher = new PaywallActivityLauncher((ActivityResultCaller) this, this::onActivityResult);

        // Launch paywall if needed
        launchPaywallActivity();

        // Update UI with Google profile info
        updateUI(account, profileImage);

        // Check prime status using RevenueCat
        checkPrimeStatus(userEmail);

        if (isPrimeUser) {
            Log.d("AdStatus", "Prime user, ads disabled.");
            noAdsImage.setVisibility(View.INVISIBLE);  // Hide the "No Ads" icon for prime users
        } else {
            Log.d("AdStatus", "Non-prime user, ads enabled.");
            showAds(); // Uncomment to enable ads
        }


        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        mMediaActionSound = new MediaActionSound();
        mMediaActionSound.load(MediaActionSound.SHUTTER_CLICK);

        strobeSeekBar.setVisibility(View.GONE);

        // Initialize AdMob and load ads
        MobileAds.initialize(this, initializationStatus -> {});




        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        strobeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update strobeDuration based on seekBar progress
                strobeDuration = progress;
                setProgress(250);
                strobeSeekBar.setMax(500);
                strobeSeekBar.setMin(1);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Set up click listeners for buttons
        mBrightScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Brightscreenactivity.class);
                startActivity(intent);
            }
        });

        AboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://flashmateprivacypolicy.blogspot.com/p/privacy-policy-for-flashmate-welcome-to.html";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        mStrobeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Disable other modes if they're enabled
                if (isSosEnabled) {
                    toggleSos();

                }
                if (isFlashlightOn) {
                    turnOffFlashlight();
                }

                // Stop any ongoing strobe loop
                isStrobeLooping = false;

                // Toggle isStrobeEnabled variable
                isStrobeEnabled = !isStrobeEnabled;


                if (isStrobeEnabled) {
                    // Show strobeSeekBar when strobe is enabled
                    strobeSeekBar.setVisibility(View.VISIBLE);


                    // Start strobe loop
                    isStrobeLooping = true;
                    strobeThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isStrobeLooping) {
                                try {
                                    // Turn on flashlight
                                    cameraManager.setTorchMode(cameraId, true);
                                    // Sleep for half the strobe duration
                                    Thread.sleep(strobeDuration / 2);
                                    // Turn off flashlight
                                    cameraManager.setTorchMode(cameraId, false);
                                    // Sleep for half the strobe duration
                                    Thread.sleep(strobeDuration / 2);
                                } catch (InterruptedException | CameraAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    strobeThread.start();

                } else {
                    // Hide strobeSeekBar when strobe is disabled
                    strobeSeekBar.setVisibility(View.GONE);

                    // Turn off flashlight
                    isFlashlightOn = false;
                    try {
                        cameraManager.setTorchMode(cameraId, isFlashlightOn);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mSosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSos();
                if (isStrobeEnabled) {
                    isStrobeEnabled = false;
                    isStrobeLooping = false;
                    strobeSeekBar.setVisibility(View.GONE);
                }
                if (isFlashlightOn) {
                    turnOffFlashlight();
                }
            }
        });

        // check if the device has flash
        boolean hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // if the device doesn't have flash, display a message and exit the app
            Toast.makeText(MainActivity.this, "Sorry, your device doesn't support flash!", Toast.LENGTH_LONG).show();
            finish();
        }


        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSosEnabled) {
                    // SOS effect is running, stop it before turning on flashlight
                    sosThread.interrupt();
                    isSosEnabled = false;
                    isSosLooping = false;
                }
                if (isStrobeEnabled) {
                    isStrobeLooping = false;
                    isStrobeEnabled = false;
                    strobeSeekBar.setVisibility(View.GONE);
                }
                if (!isFlashlightOn) {
                    turnOnFlashlight();
                    isFlashlightOn = true;
                    vibrator.vibrate(100);
                } else {
                    turnOffFlashlight();
                    isFlashlightOn = false;
                    vibrator.vibrate(100);
                }
            }
        });
    }


    private void turnOnFlashlight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOffFlashlight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void toggleSos() {
        if (isFlashlightOn) {
            // Flashlight is on, turn it off before starting SOS effect
            turnOffFlashlight();
            isFlashlightOn = false;
        }

        if (isSosEnabled) {
            turnOffFlashlight();
            isSosEnabled = false;
            if (isSosLooping) {
                sosThread.interrupt();
                isSosLooping = false;
            }
            return;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            // Device doesn't have a flashlight, show error message
            Toast.makeText(this, "No flashlight found on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        final long dotDuration = 200; // duration of a dot in milliseconds
        final long dashDuration = dotDuration * 3; // duration of a dash in milliseconds
        final long spaceDuration = dotDuration; // duration of a space in milliseconds

        sosThread = new Thread(new Runnable() {
            @Override
            public void run() {
                isSosLooping = true;
                while (isSosLooping) {
                    try {
                        turnOnFlashlight();
                        Thread.sleep(dotDuration);
                        turnOffFlashlight();
                        Thread.sleep(spaceDuration);

                        turnOnFlashlight();
                        Thread.sleep(dotDuration);
                        turnOffFlashlight();
                        Thread.sleep(spaceDuration);

                        turnOnFlashlight();
                        Thread.sleep(dotDuration);
                        turnOffFlashlight();
                        Thread.sleep(spaceDuration);

                        turnOnFlashlight();
                        Thread.sleep(dashDuration);
                        turnOffFlashlight();
                        Thread.sleep(spaceDuration);

                        turnOnFlashlight();
                        Thread.sleep(dashDuration);
                        turnOffFlashlight();
                        Thread.sleep(spaceDuration);

                        turnOnFlashlight();
                        Thread.sleep(dashDuration);
                        turnOffFlashlight();
                        Thread.sleep(spaceDuration);

                        turnOnFlashlight();
                        Thread.sleep(dotDuration);
                        turnOffFlashlight();
                        Thread.sleep(spaceDuration);

                        turnOnFlashlight();
                        Thread.sleep(dotDuration);
                        turnOffFlashlight();
                        Thread.sleep(spaceDuration);

                        turnOnFlashlight();
                        Thread.sleep(dotDuration);
                        turnOffFlashlight();
                        Thread.sleep(spaceDuration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        isSosLooping = false;
                    }
                }
            }
        });

        sosThread.start();
        isSosEnabled = true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (isFlashlightOn) {
            turnOnFlashlight();
        }
        if (isStrobeEnabled) {
            isStrobeEnabled = false;
            isStrobeLooping = false;
        }
        if (isSosEnabled) {
            sosThread.interrupt();
            isSosEnabled = false;
            isSosLooping = false;
        }
    }

    private void showAds() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        InterstitialAd.load(this, "Interstitial_ad_id", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        isAdLoaded = true;

                        Log.i("MainActivity", "onAdLoaded");
                        if (isFirstTimeCreate && mInterstitialAd != null) {
                            mInterstitialAd.show(MainActivity.this);
                            isFirstTimeCreate = false;
                        } else {
                            Log.d("MainActivity", "The interstitial ad wasn't ready yet.");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("MainActivity", loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
    }

    private void launchPaywallActivity() {
        // Launch the paywall activity if the user doesn't have the required entitlement
        launcher.launchIfNeeded(REQUIRED_ENTITLEMENT_IDENTIFIER);
    }

    private void checkPrimeStatus(String userEmail) {
        Purchases.getSharedInstance().getCustomerInfo(new ReceiveCustomerInfoCallback() {
            @Override
            public void onReceived(@NonNull CustomerInfo customerInfo) {
                EntitlementInfo entitlement = customerInfo.getEntitlements().get("prime");
                if (entitlement != null && entitlement.isActive()) {
                    isPrimeUser = true;
                    Log.d("RevenueCat", "Prime active for: " + userEmail);
                } else {
                    isPrimeUser = false;
                    Log.d("RevenueCat", "No prime entitlement for: " + userEmail);
                }
            }

            @Override
            public void onError(PurchasesError error) {
                isPrimeUser = false;
                Log.e("RevenueCat", "Error checking entitlement for: " + userEmail);
            }
        });
    }


    private void updateUI(GoogleSignInAccount account, ImageView profileImageView) {
        if (account != null) {
            Glide.with(this)
                    .load(account.getPhotoUrl())
                    .placeholder(R.drawable.profile)
                    .circleCrop()
                    .into(profileImageView);
        } else {
            Glide.with(this)
                    .load(R.drawable.profile)
                    .circleCrop()
                    .into(profileImageView);
        }
    }

    protected void onResume() {
        super.onResume();
        // Show the interstitial ad when the app opens or reopens
        if (isAdLoaded && !isFirstTimeCreate) {
            mInterstitialAd.show(MainActivity.this);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFlashlightOn) {
            turnOnFlashlight();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        strobeSeekBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (isFlashlightOn)
            turnOnFlashlight();
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFlashlightOn) {
            turnOffFlashlight();
            super.onDestroy();
        }
    }

    public void onActivityResult(PaywallResult result) {
        // Retrieve customer info to check entitlement after successful purchase
        Purchases.getSharedInstance().getCustomerInfo(new ReceiveCustomerInfoCallback() {
            @Override
            public void onReceived(@NonNull CustomerInfo customerInfo) {
                // Check if the prime entitlement is active after the purchase
                EntitlementInfo entitlement = customerInfo.getEntitlements().get("prime");
                if (entitlement != null && entitlement.isActive()) {
                    // User has successfully subscribed, handle the successful purchase

                    // Log success
                    Log.d("Paywall", "Payment successful and prime entitlement active!");

                    // Restart the app to apply the changes
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Close current activity
                } else {
                    // Handle the case where the entitlement is not active or the payment failed
                    Log.d("Paywall", "Payment failed or prime entitlement is not active.");
                }
            }

            @Override
            public void onError(@NonNull PurchasesError error) {
                // Handle any errors while retrieving the customer info
                Log.e("Paywall", "Error retrieving customer info: " + error.getMessage());
            }
        });
    }
}
