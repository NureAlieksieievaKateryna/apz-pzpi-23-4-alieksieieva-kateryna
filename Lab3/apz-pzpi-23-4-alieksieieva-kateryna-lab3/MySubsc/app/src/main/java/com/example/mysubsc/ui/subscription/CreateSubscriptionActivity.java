package com.example.mysubsc.ui.subscription;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysubsc.R;
import com.example.mysubsc.data.api.RetrofitClient;
import com.example.mysubsc.data.model.ComponentRequest;
import com.example.mysubsc.data.model.CreateSubscriptionRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSubscriptionActivity extends Activity {

    private EditText planName;
    private EditText planDescription;
    private EditText planPrice;

    private Spinner billingSpinner;

    private LinearLayout componentsContainer;

    private Button addComponentBtn;
    private Button createSubscriptionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ПРАВИЛЬНЫЙ XML
        setContentView(R.layout.business_subscription_page);

        initViews();

        setupBillingSpinner();

        addComponentView();

        addComponentBtn.setOnClickListener(v -> addComponentView());

        createSubscriptionBtn.setOnClickListener(v -> sendToBackend());
    }

    private void initViews() {

        planName = findViewById(R.id.planName);

        planDescription = findViewById(R.id.planDescription);

        planPrice = findViewById(R.id.planPrice);

        billingSpinner = findViewById(R.id.billingSpinner);

        componentsContainer = findViewById(R.id.componentsContainer);

        addComponentBtn = findViewById(R.id.addComponentBtn);

        createSubscriptionBtn = findViewById(R.id.createSubscriptionBtn);
    }

    private void setupBillingSpinner() {

        String[] periods = {
                "MONTHLY",
                "YEARLY"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        periods
                );

        billingSpinner.setAdapter(adapter);
    }

    private void addComponentView() {

        View view = getLayoutInflater().inflate(
                R.layout.item_subscription_component,
                componentsContainer,
                false
        );

        Spinner typeSpinner =
                view.findViewById(R.id.componentType);

        String[] types = {
                "FEATURE",
                "LIMITED_USAGE",
                "ADDON"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        types
                );

        typeSpinner.setAdapter(adapter);

        Button removeBtn =
                view.findViewById(R.id.removeComponentBtn);

        removeBtn.setOnClickListener(v ->
                componentsContainer.removeView(view)
        );

        componentsContainer.addView(view);
    }

    private void sendToBackend() {

        String name =
                planName.getText().toString().trim();

        String description =
                planDescription.getText().toString().trim();

        String priceText =
                planPrice.getText().toString().trim();

        if (name.isEmpty()) {
            planName.setError("Enter plan name");
            return;
        }

        if (priceText.isEmpty()) {
            planPrice.setError("Enter price");
            return;
        }

        BigDecimal basePrice =
                new BigDecimal(priceText);

        String billingPeriod =
                billingSpinner.getSelectedItem().toString();

        List<ComponentRequest> components =
                new ArrayList<>();

        for (int i = 0; i < componentsContainer.getChildCount(); i++) {

            View view =
                    componentsContainer.getChildAt(i);

            EditText componentName =
                    view.findViewById(R.id.componentName);

            Spinner componentType =
                    view.findViewById(R.id.componentType);

            EditText usageLimit =
                    view.findViewById(R.id.usageLimit);

            EditText priceModifier =
                    view.findViewById(R.id.priceModifier);

            String compName =
                    componentName.getText().toString().trim();

            if (compName.isEmpty()) {
                componentName.setError("Enter component name");
                return;
            }

            Integer limit = null;

            if (!usageLimit.getText().toString().trim().isEmpty()) {

                limit = Integer.parseInt(
                        usageLimit.getText().toString().trim()
                );
            }

            BigDecimal modifier = BigDecimal.ZERO;

            if (!priceModifier.getText().toString().trim().isEmpty()) {

                modifier = new BigDecimal(
                        priceModifier.getText().toString().trim()
                );
            }

            components.add(
                    new ComponentRequest(
                            compName,
                            componentType.getSelectedItem().toString(),
                            limit,
                            modifier
                    )
            );
        }

        CreateSubscriptionRequest request =
                new CreateSubscriptionRequest(
                        name,
                        description,
                        basePrice,
                        billingPeriod,
                        components
                );

        RetrofitClient
                .getApiService()
                .createSubscriptionTemplate(request)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response
                    ) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    CreateSubscriptionActivity.this,
                                    "Plan created",
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();

                        } else {

                            Toast.makeText(
                                    CreateSubscriptionActivity.this,
                                    "Backend error: " + response.code(),
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
                                CreateSubscriptionActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}