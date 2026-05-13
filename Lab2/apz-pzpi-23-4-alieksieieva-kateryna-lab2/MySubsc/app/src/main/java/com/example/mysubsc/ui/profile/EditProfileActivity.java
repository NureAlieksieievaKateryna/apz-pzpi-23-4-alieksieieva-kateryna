package com.example.mysubsc.ui.profile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysubsc.R;

public class EditProfileActivity extends Activity {

    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;
    private Button saveProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initViews();
        loadUserData();
        setupListeners();
    }

    private void initViews() {
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        saveProfileBtn = findViewById(R.id.saveProfileBtn);
    }

    private void loadUserData() {
        editName.setText("John Smith");
        editEmail.setText("john.smith@gmail.com");
    }

    private void setupListeners() {
        saveProfileBtn.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {

        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Enter name");
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Enter email");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter valid email");
            return;
        }

        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();

        finish();
    }
}