package com.example.mysubsc.ui.admin;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysubsc.R;
import com.example.mysubsc.data.api.RetrofitClient;
import com.example.mysubsc.data.model.BusinessResponse;
import com.example.mysubsc.data.model.ModerateBusinessRequest;
import com.example.mysubsc.ui.user.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends Activity {

    private ImageButton logoutBtn;
    private Button refreshButton;

    private TextView pendingBusinessesText;

    private LinearLayout businessContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initViews();
        initClicks();

        loadBusinesses();
    }

    private void initViews() {

        logoutBtn = findViewById(R.id.logoutBtn);

        refreshButton = findViewById(R.id.refreshButton);

        pendingBusinessesText =
                findViewById(R.id.pendingBusinessesText);

        businessContainer =
                findViewById(R.id.businessContainer);
    }

    private void initClicks() {

        refreshButton.setOnClickListener(v ->
                loadBusinesses()
        );

        logoutBtn.setOnClickListener(v ->
                logout()
        );
    }

    private void loadBusinesses() {

        RetrofitClient.getApiService()
                .getAllNotVerifiedBusiness()
                .enqueue(new Callback<List<BusinessResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<BusinessResponse>> call,
                            Response<List<BusinessResponse>> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            List<BusinessResponse> businesses =
                                    response.body();

                            pendingBusinessesText.setText(
                                    businesses.size() + " Businesses"
                            );

                            showBusinesses(businesses);

                        } else {

                            Toast.makeText(
                                    AdminActivity.this,
                                    "Error: " + response.code(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<BusinessResponse>> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                AdminActivity.this,
                                "Backend error: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void showBusinesses(List<BusinessResponse> businesses) {

        businessContainer.removeAllViews();

        for (BusinessResponse business : businesses) {
            addBusinessCard(business);
        }
    }

    private void addBusinessCard(BusinessResponse business) {

        LinearLayout card = new LinearLayout(this);

        card.setOrientation(LinearLayout.VERTICAL);

        card.setPadding(28, 28, 28, 28);

        card.setBackgroundColor(0xFFFFFFFF);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, 0, 0, 24);

        card.setLayoutParams(params);

        TextView name = new TextView(this);

        name.setText(business.getName());

        name.setTextSize(20);

        name.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView description = new TextView(this);

        description.setText(business.getDescription());

        description.setTextSize(14);

        description.setPadding(0, 10, 0, 12);

        Button approve = new Button(this);

        approve.setText("Approve");

        approve.setOnClickListener(v ->
                moderateBusiness(
                        business.getId(),
                        true
                )
        );

        Button reject = new Button(this);

        reject.setText("Reject");

        reject.setOnClickListener(v ->
                moderateBusiness(
                        business.getId(),
                        false
                )
        );

        card.addView(name);
        card.addView(description);
        card.addView(approve);
        card.addView(reject);

        businessContainer.addView(card);
    }

    private void moderateBusiness(
            Long businessId,
            Boolean businessValue
    ) {

        ModerateBusinessRequest request =
                new ModerateBusinessRequest(
                        businessId,
                        businessValue
                );

        RetrofitClient.getApiService()
                .moderateBusiness(request)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response
                    ) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    AdminActivity.this,
                                    "Business updated",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadBusinesses();

                        } else {

                            Toast.makeText(
                                    AdminActivity.this,
                                    "Moderation error",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                AdminActivity.this,
                                "Backend error: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void logout() {

        SharedPreferences preferences =
                getSharedPreferences("auth", MODE_PRIVATE);

        preferences.edit().clear().apply();

        Intent intent = new Intent(
                AdminActivity.this,
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