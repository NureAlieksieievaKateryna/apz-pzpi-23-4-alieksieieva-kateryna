package com.example.mysubsc.ui.business;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mysubsc.R;

import java.util.ArrayList;
import java.util.List;

public class BusinessSearchActivity extends Activity {

    private EditText searchInput;
    private Button searchButton;
    private LinearLayout businessContainer;

    private final List<BusinessMock> businesses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_search);

        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        businessContainer = findViewById(R.id.businessContainer);

        initMockBusinesses();

        searchButton.setOnClickListener(v -> searchBusinesses());

        showBusinesses(businesses);
    }

    private void initMockBusinesses() {
        businesses.add(new BusinessMock(
                "FitLife Gym",
                "Flexible gym subscriptions",
                "Flexible Gym Plan\n5 visits, 10 visits, locker access"
        ));

        businesses.add(new BusinessMock(
                "Coffee Time",
                "Coffee subscription service",
                "Coffee Plan\n5 coffees, 10 coffees, desserts"
        ));

        businesses.add(new BusinessMock(
                "Beauty Studio",
                "Flexible beauty subscriptions",
                "Beauty Plan\nHaircut, manicure, SPA"
        ));
    }

    private void searchBusinesses() {
        String query = searchInput.getText().toString().trim().toLowerCase();

        if (query.isEmpty()) {
            showBusinesses(businesses);
            return;
        }

        List<BusinessMock> filtered = new ArrayList<>();

        for (BusinessMock business : businesses) {
            if (business.name.toLowerCase().contains(query)
                    || business.description.toLowerCase().contains(query)
                    || business.plan.toLowerCase().contains(query)) {
                filtered.add(business);
            }
        }

        showBusinesses(filtered);
    }

    private void showBusinesses(List<BusinessMock> list) {
        businessContainer.removeAllViews();

        if (list.isEmpty()) {
            TextView emptyText = new TextView(this);
            emptyText.setText("No businesses found");
            emptyText.setTextSize(16);
            emptyText.setTextColor(0xFF6B7280);
            businessContainer.addView(emptyText);
            return;
        }

        for (BusinessMock business : list) {
            addBusinessCard(
                    business.name,
                    business.description,
                    business.plan
            );
        }
    }

    private void addBusinessCard(
            String businessName,
            String description,
            String plan
    ) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(28, 28, 28, 28);
        card.setBackgroundColor(0xFFFFFFFF);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(0, 0, 0, 22);
        card.setLayoutParams(cardParams);

        TextView businessNameText = new TextView(this);
        businessNameText.setText(businessName);
        businessNameText.setTextSize(20);
        businessNameText.setTextColor(0xFF111827);
        businessNameText.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView descriptionText = new TextView(this);
        descriptionText.setText(description);
        descriptionText.setTextSize(14);
        descriptionText.setTextColor(0xFF6B7280);
        descriptionText.setPadding(0, 8, 0, 14);

        TextView planText = new TextView(this);
        planText.setText(plan);
        planText.setTextSize(15);
        planText.setTextColor(0xFF111827);

        Button chooseButton = new Button(this);
        chooseButton.setText("Choose subscription");
        chooseButton.setTransformationMethod(null);

        card.addView(businessNameText);
        card.addView(descriptionText);
        card.addView(planText);
        card.addView(chooseButton);

        businessContainer.addView(card);
    }

    private static class BusinessMock {
        String name;
        String description;
        String plan;

        BusinessMock(String name, String description, String plan) {
            this.name = name;
            this.description = description;
            this.plan = plan;
        }
    }
}