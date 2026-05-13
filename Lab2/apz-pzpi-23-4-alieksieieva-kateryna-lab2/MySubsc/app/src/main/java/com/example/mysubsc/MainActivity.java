package com.example.mysubsc;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import com.example.mysubsc.ui.business.BusinessDashboardActivity;
import com.example.mysubsc.ui.user.UserHomeActivity;

public class MainActivity extends Activity {

    private static final String USER_TYPE = "USER";
    // потом это будет приходить с backend: USER или BUSINESS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (USER_TYPE.equals("BUSINESS")) {
            startActivity(new Intent(this, BusinessDashboardActivity.class));
        } else {
            startActivity(new Intent(this, UserHomeActivity.class));
        }

        finish();
    }
}