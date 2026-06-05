package com.example.mysubsc.ui.business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysubsc.R;
import com.example.mysubsc.data.api.ApiService;
import com.example.mysubsc.data.api.RetrofitClient;
import com.example.mysubsc.data.model.BusinessRegisterRequest;
import com.example.mysubsc.ui.user.LoginActivity;
import com.example.mysubsc.ui.user.RegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessRegisterActivity extends Activity {

    private EditText companyNameInput;
    private EditText businessLoginInput;
    private EditText businessDescriptionInput;
    private EditText businessEmailInput;
    private EditText businessPasswordInput;

    private Spinner businessTypeSpinner;
    private Button businessSignUpButton;

    private TextView goToClientSignUp;
    private TextView goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_bussiness);

        initViews();
        setupBusinessTypeSpinner();
        setupListeners();
    }

    private void initViews() {
        companyNameInput = findViewById(R.id.companyNameInput);
        businessLoginInput = findViewById(R.id.businessLoginInput);
        businessDescriptionInput = findViewById(R.id.businessDescriptionInput);
        businessEmailInput = findViewById(R.id.businessEmailInput);
        businessPasswordInput = findViewById(R.id.businessPasswordInput);
        businessTypeSpinner = findViewById(R.id.businessTypeSpinner);
        businessSignUpButton = findViewById(R.id.businessSignUpButton);
        goToClientSignUp = findViewById(R.id.goToClientSignUp);
        goToLogin = findViewById(R.id.goToLogin);
    }

    private void setupBusinessTypeSpinner() {
        String[] types = {
                "CAFE",
                "RESTAURANT",
                "GYM",
                "FITNESS_STUDIO",
                "BEAUTY_SALON",
                "BARBERSHOP",
                "SPA",
                "MEDICAL_CLINIC",
                "DENTAL_CLINIC",
                "ONLINE_SCHOOL",
                "LANGUAGE_SCHOOL",
                "MOBILE_OPERATOR",
                "INTERNET_PROVIDER",
                "STREAMING_SERVICE",
                "SOFTWARE_SERVICE",
                "DELIVERY_SERVICE",
                "STORE",
                "OTHER"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                types
        );

        businessTypeSpinner.setAdapter(adapter);
    }

    private void setupListeners() {
        businessSignUpButton.setOnClickListener(v -> registerBusiness());

        goToClientSignUp.setOnClickListener(v -> {
            startActivity(new Intent(
                    BusinessRegisterActivity.this,
                    RegisterActivity.class
            ));
            finish();
        });

        goToLogin.setOnClickListener(v -> {
            startActivity(new Intent(
                    BusinessRegisterActivity.this,
                    LoginActivity.class
            ));
            finish();
        });
    }

    private void registerBusiness() {
        String name = companyNameInput.getText().toString().trim();
        String login = businessLoginInput.getText().toString().trim();
        String description = businessDescriptionInput.getText().toString().trim();
        String email = businessEmailInput.getText().toString().trim();
        String type = businessTypeSpinner.getSelectedItem().toString();
        String password = businessPasswordInput.getText().toString().trim();

        if (name.isEmpty()) {
            companyNameInput.setError("Enter business name");
            return;
        }

        if (login.isEmpty()) {
            businessLoginInput.setError("Enter login");
            return;
        }

        if (description.isEmpty()) {
            businessDescriptionInput.setError("Enter description");
            return;
        }

        if (email.isEmpty()) {
            businessEmailInput.setError("Enter email");
            return;
        }

        if (password.isEmpty()) {
            businessPasswordInput.setError("Enter password");
            return;
        }

        businessSignUpButton.setEnabled(false);

        BusinessRegisterRequest request = new BusinessRegisterRequest(
                name,
                login,
                description,
                email,
                type,
                password
        );

        ApiService apiService = RetrofitClient.getApiService();

        apiService.registerBusiness(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                businessSignUpButton.setEnabled(true);

                if (response.isSuccessful()) {
                    Toast.makeText(
                            BusinessRegisterActivity.this,
                            "Business account created",
                            Toast.LENGTH_SHORT
                    ).show();

                    openBusinessDashboard();

                } else {
                    Toast.makeText(
                            BusinessRegisterActivity.this,
                            "Registration error: " + response.code(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                businessSignUpButton.setEnabled(true);

                Toast.makeText(
                        BusinessRegisterActivity.this,
                        "Backend connection error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void openBusinessDashboard() {
        Intent intent = new Intent(
                BusinessRegisterActivity.this,
                BusinessDashboardActivity.class
        );

        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);
        finish();
    }
}