package com.example.mysubsc.ui.user;

import android.app.Activity;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mysubsc.R;


import android.content.Intent;

import com.example.mysubsc.data.api.RetrofitClient;
import com.example.mysubsc.data.model.LoginRequest;
import com.example.mysubsc.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.SharedPreferences;
import android.widget.TextView;
import android.content.Intent;
import android.widget.ImageButton;

import com.example.mysubsc.ui.admin.AdminActivity;
import com.example.mysubsc.ui.business.BusinessDashboardActivity;


public class LoginActivity extends Activity {

    private EditText usernameInput;
    private EditText passwordInput;

    private Button loginButton;
    private ImageButton logoutBtn;
    private ProgressBar loading;

    private TextView goToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        loginButton.setOnClickListener(v -> loginUser());

        goToSignUp.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            LoginActivity.this,
                            RegisterActivity.class
                    )
            );
        });
    }

    private void initViews() {

        usernameInput =
                findViewById(R.id.username);

        passwordInput =
                findViewById(R.id.password);

        loginButton =
                findViewById(R.id.login);

        loading =
                findViewById(R.id.loading);

        goToSignUp =
                findViewById(R.id.goToSignUp);

        logoutBtn = findViewById(R.id.logoutBtn);
    }

    private void loginUser() {

        String login =
                usernameInput.getText().toString().trim();

        String password =
                passwordInput.getText().toString().trim();

        if (login.isEmpty()) {

            usernameInput.setError("Enter login");

            return;
        }

        if (password.isEmpty()) {

            passwordInput.setError("Enter password");

            return;
        }

        loading.setVisibility(View.VISIBLE);

        loginButton.setEnabled(false);

        LoginRequest request =
                new LoginRequest(
                        login,
                        password
                );

        RetrofitClient.getApiService()
                .login(request)
                .enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(
                            Call<LoginResponse> call,
                            Response<LoginResponse> response
                    ) {

                        loading.setVisibility(View.GONE);

                        loginButton.setEnabled(true);

                        if (response.isSuccessful()
                                && response.body() != null) {

                            LoginResponse loginResponse =
                                    response.body();

                            saveAuthData(loginResponse);

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Login successful",
                                    Toast.LENGTH_SHORT
                            ).show();

                            openScreenByRole(loginResponse);

                        } else {

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Login error: " + response.code(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<LoginResponse> call,
                            Throwable t
                    ) {

                        loading.setVisibility(View.GONE);

                        loginButton.setEnabled(true);

                        Toast.makeText(
                                LoginActivity.this,
                                "Backend error: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void saveAuthData(LoginResponse response) {

        SharedPreferences preferences =
                getSharedPreferences(
                        "auth",
                        MODE_PRIVATE
                );

        preferences.edit()
                .putString(
                        "jwt_token",
                        response.getToken()
                )
                .putLong(
                        "user_id",
                        response.getUserId()
                )
                .putString(
                        "username",
                        response.getUserName()
                )
                .putString(
                        "role",
                        response.getRole().name()
                )
                .apply();
    }

    private void openScreenByRole(LoginResponse response) {

        Intent intent;

        if ("ADMIN".equals(response.getRole().name())) {

            intent = new Intent(
                    LoginActivity.this,
                    AdminActivity.class
            );

        } else if ("BUSINESS".equals(response.getRole().name())) {

            intent = new Intent(
                    LoginActivity.this,
                    BusinessDashboardActivity.class
            );

        } else {

            intent = new Intent(
                    LoginActivity.this,
                    UserHomeActivity.class
            );
        }

        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);

        finish();
    }
}