package com.example.mysubsc.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysubsc.R;
import com.example.mysubsc.data.api.RetrofitClient;
import com.example.mysubsc.data.model.UserRegisterRequest;
import com.example.mysubsc.ui.business.BusinessRegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {

    private Button clientSignUpButton;

    private TextView goToBusinessSignUp;
    private TextView goToLogin;
    private EditText fullNameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_ckient);

        initViews();

        clientSignUpButton.setOnClickListener(v -> registerClient());

        goToBusinessSignUp.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            RegisterActivity.this,
                            BusinessRegisterActivity.class
                    )
            );
        });
        goToLogin.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    )
            );
        });
    }

    private void initViews() {

        goToLogin = findViewById(R.id.goToLogin);
        clientSignUpButton =
                findViewById(R.id.clientSignUpButton);

        goToBusinessSignUp =
                findViewById(R.id.goToBusinessSignUp);

        fullNameInput =
                findViewById(R.id.fullNameInput);

        emailInput =
                findViewById(R.id.emailInput);

        passwordInput =
                findViewById(R.id.passwordInput);

        confirmPasswordInput =
                findViewById(R.id.confirmPasswordInput);
    }

    private void registerClient() {

        String name =
                fullNameInput.getText().toString().trim();

        String email =
                emailInput.getText().toString().trim();

        String password =
                passwordInput.getText().toString().trim();

        String confirmPassword =
                confirmPasswordInput.getText().toString().trim();

        if (name.isEmpty()) {
            fullNameInput.setError("Enter username");
            return;
        }

        if (email.isEmpty()) {
            emailInput.setError("Enter email");
            return;
        }

        if (password.isEmpty()) {
            passwordInput.setError("Enter password");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Passwords do not match");
            return;
        }

        UserRegisterRequest request =
                new UserRegisterRequest(
                        name,
                        name,
                        email,
                        password
                );

        RetrofitClient.getApiService()
                .registerClient(request)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response
                    ) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    RegisterActivity.this,
                                    "Registration successful",
                                    Toast.LENGTH_SHORT
                            ).show();

                            startActivity(
                                    new Intent(
                                            RegisterActivity.this,
                                            UserHomeActivity.class
                                    )
                            );

                            finish();

                        } else {

                            Toast.makeText(
                                    RegisterActivity.this,
                                    "Register error: " + response.code(),
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
                                RegisterActivity.this,
                                "Backend error: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}