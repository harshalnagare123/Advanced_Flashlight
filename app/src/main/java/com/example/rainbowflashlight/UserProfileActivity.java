package com.example.rainbowflashlight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.revenuecat.purchases.CustomerInfo;
import com.revenuecat.purchases.EntitlementInfo;
import com.revenuecat.purchases.Purchases;
import com.revenuecat.purchases.PurchasesError;
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback;

public class UserProfileActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView profileImage = findViewById(R.id.user_profile_image);
        TextView userName = findViewById(R.id.user_name);
        TextView userEmail = findViewById(R.id.user_email);
        TextView primeStatus = findViewById(R.id.prime_status);
        Button logoutButton = findViewById(R.id.logout_button);
        ImageView prime_check = findViewById(R.id.prime_check);

        // Configure Google Sign-In client
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Get the currently signed-in account
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // Load circular profile image
            Glide.with(this)
                    .load(account.getPhotoUrl())
                    .placeholder(R.drawable.profile) // Replace with your default profile drawable
                    .circleCrop()
                    .into(profileImage);

            // Display user details
            userName.setText(account.getDisplayName() != null ? account.getDisplayName() : "No Name");
            userEmail.setText(account.getEmail() != null ? account.getEmail() : "No Email");
        } else {
            // Default values for a guest user
            userName.setText("Guest");
            userEmail.setText("Not Logged In");
            profileImage.setImageResource(R.drawable.profile); // Default profile image
        }

        Purchases.getSharedInstance().getCustomerInfo(new ReceiveCustomerInfoCallback() {
            @Override
            public void onReceived(CustomerInfo customerInfo) {
                EntitlementInfo entitlement = customerInfo.getEntitlements().get("prime");
                if (entitlement != null && entitlement.isActive()) {
                    // Prime User
                    primeStatus.setText("Prime User");
                    primeStatus.setTextColor(ContextCompat.getColor(UserProfileActivity.this, R.color.colorPrimary));  // Green color
                    prime_check.setVisibility(View.VISIBLE);
                } else {
                    // Non-Prime User
                    primeStatus.setText("Non-Prime User");
                    primeStatus.setTextColor(ContextCompat.getColor(UserProfileActivity.this, R.color.colorRed));  // Red color
                }
            }

            @Override
            public void onError(PurchasesError error) {
                primeStatus.setText("Error checking status");
                Toast.makeText(UserProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Logout button functionality
        logoutButton.setOnClickListener(v -> {
            // Clear SharedPreferences
            SharedPreferences preferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear(); // Clear all saved data
            editor.apply();

            // Sign out from Firebase
            FirebaseAuth.getInstance().signOut();

            // Sign out from Google
            if (googleSignInClient != null) {
                googleSignInClient.signOut().addOnCompleteListener(task -> {
                    Toast.makeText(UserProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                });
            }

            // Restart app to ensure clean state and redirect to login screen
            Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish(); // Close UserProfileActivity
        });

    }
}
