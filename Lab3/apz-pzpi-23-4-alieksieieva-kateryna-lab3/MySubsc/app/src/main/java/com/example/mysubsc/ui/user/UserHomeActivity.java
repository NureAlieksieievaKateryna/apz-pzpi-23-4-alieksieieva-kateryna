package com.example.mysubsc.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysubsc.R;
import com.example.mysubsc.data.api.ApiService;
import com.example.mysubsc.data.api.RetrofitClient;
import com.example.mysubsc.data.model.UserHomeResponse;
import com.example.mysubsc.data.model.UserSubscriptionView;
import com.example.mysubsc.ui.business.BusinessSearchActivity;
import com.example.mysubsc.ui.screens.ChangePlanActivity;
import com.example.mysubsc.ui.screens.PaymentsActivity;
import com.example.mysubsc.ui.screens.SupportActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends Activity {

    private Button mySubscriptionsButton;
    private Button changePlanButton;
    private Button paymentsButton;
    private Button supportButton;

    private TextView userNameText;
    private TextView currentPlanText;
    private TextView priceText;
    private TextView nextBillingText;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_activity);

        apiService = RetrofitClient.getApiService();

        initViews();
        initClicks();

        loadUserHome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserHome();
    }

    private void initViews() {

        mySubscriptionsButton =
                findViewById(R.id.mySubscriptionsButton);

        changePlanButton =
                findViewById(R.id.changePlanButton);

        paymentsButton =
                findViewById(R.id.paymentsButton);

        supportButton =
                findViewById(R.id.supportButton);

        userNameText =
                findViewById(R.id.userNameText);

        currentPlanText =
                findViewById(R.id.currentPlanText);

        priceText =
                findViewById(R.id.priceText);

        nextBillingText =
                findViewById(R.id.nextBillingText);
    }

    private void initClicks() {

        mySubscriptionsButton.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                this,
                                BusinessSearchActivity.class
                        )
                )
        );

        changePlanButton.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                this,
                                ChangePlanActivity.class
                        )
                )
        );

        paymentsButton.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                this,
                                PaymentsActivity.class
                        )
                )
        );

        supportButton.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                this,
                                SupportActivity.class
                        )
                )
        );
    }

    private void loadUserHome() {

        Long userId = getUserId();

        if (userId == null) {

            Toast.makeText(
                    this,
                    "User id not found",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        apiService.getUserHome(userId)
                .enqueue(new Callback<UserHomeResponse>() {

                    @Override
                    public void onResponse(
                            Call<UserHomeResponse> call,
                            Response<UserHomeResponse> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            showUserHome(response.body());

                        } else {

                            Toast.makeText(
                                    UserHomeActivity.this,
                                    "Home error: " + response.code(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<UserHomeResponse> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                UserHomeActivity.this,
                                "Backend error: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void showUserHome(UserHomeResponse home) {

        userNameText.setText(home.getUsername());

        UserSubscriptionView activeSubscription =
                home.getActiveSubscription();

        if (activeSubscription == null) {

            currentPlanText.setText("No active plan");

            priceText.setText(
                    "Available plans: "
                            + home.getAvailableTemplates().size()
            );

            nextBillingText.setText(
                    "Choose your first subscription"
            );

            return;
        }

        currentPlanText.setText(
                activeSubscription.getTemplateName()
        );

        priceText.setText(
                "$" + activeSubscription.getFinalPrice()
        );

        if (activeSubscription.getEndDate() != null) {

            nextBillingText.setText(
                    "Until: "
                            + activeSubscription.getEndDate()
            );

        } else {

            nextBillingText.setText(
                    "Status: "
                            + activeSubscription.getStatus()
            );
        }
    }

    private Long getUserId() {

        SharedPreferences preferences =
                getSharedPreferences(
                        "auth",
                        MODE_PRIVATE
                );

        long userId =
                preferences.getLong(
                        "user_id",
                        -1
                );

        if (userId == -1) {
            return null;
        }

        return userId;
    }
}