package com.example.mysubsc.ui.business;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysubsc.R;
import com.example.mysubsc.data.api.RetrofitClient;
import com.example.mysubsc.data.model.BusinessDashboardResponse;
import com.example.mysubsc.ui.user.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessDashboardActivity extends Activity {

    private Button createSubscriptionBtn;
    private Button clientsBtn;
    private Button paymentsBtn;
    private Button analyticsBtn;

    private TextView businessNameText;
    private TextView roleText;

    private ImageButton logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);

        initViews();
        setupListeners();
        loadDashboard();
    }

    private void initViews() {
        createSubscriptionBtn = findViewById(R.id.createSubscriptionBtn);
        clientsBtn = findViewById(R.id.clientsBtn);
        paymentsBtn = findViewById(R.id.paymentsBtn);
        analyticsBtn = findViewById(R.id.analyticsBtn);

        logoutBtn = findViewById(R.id.logoutBtn);

        businessNameText = findViewById(R.id.businessNameText);
        roleText = findViewById(R.id.roleText);
    }

    private void setupListeners() {

        createSubscriptionBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Create Subscription", Toast.LENGTH_SHORT).show();
        });

        clientsBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Clients", Toast.LENGTH_SHORT).show();
        });

        paymentsBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Payments", Toast.LENGTH_SHORT).show();
        });

        analyticsBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Analytics", Toast.LENGTH_SHORT).show();
        });

        logoutBtn.setOnClickListener(v -> logout());
    }

    private void loadDashboard() {

        SharedPreferences preferences =
                getSharedPreferences("auth", MODE_PRIVATE);

        long businessId =
                preferences.getLong("business_id", -1);

        if (businessId == -1) {
            Toast.makeText(
                    this,
                    "Business id not found",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        RetrofitClient.getApiService()
                .getBusinessDashboard(businessId)
                .enqueue(new Callback<BusinessDashboardResponse>() {

                    @Override
                    public void onResponse(
                            Call<BusinessDashboardResponse> call,
                            Response<BusinessDashboardResponse> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            showDashboard(response.body());

                        } else {

                            Toast.makeText(
                                    BusinessDashboardActivity.this,
                                    "Dashboard error: " + response.code(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BusinessDashboardResponse> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                BusinessDashboardActivity.this,
                                "Backend error: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void showDashboard(BusinessDashboardResponse dashboard) {

        businessNameText.setText(dashboard.getBusinessName());

        String verifiedText;

        if (Boolean.TRUE.equals(dashboard.getVerified())) {
            verifiedText = "Verified";
        } else {
            verifiedText = "Not verified";
        }

        roleText.setText(
                dashboard.getType() + " • " + verifiedText
        );
    }

    private void logout() {

        SharedPreferences preferences =
                getSharedPreferences("auth", MODE_PRIVATE);

        preferences.edit().clear().apply();

        Intent intent = new Intent(
                BusinessDashboardActivity.this,
                LoginActivity.class
        );

        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);

        finish();
    }
}