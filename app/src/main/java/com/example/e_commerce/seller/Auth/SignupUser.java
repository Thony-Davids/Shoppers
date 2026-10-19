package com.example.e_commerce.seller.Auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_commerce.R;
import com.example.e_commerce.seller.MainActivity;
import com.example.e_commerce.seller.db.DatabaseHelper;
import com.example.e_commerce.seller.db.ViewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SignupUser extends AppCompatActivity {
    public TextInputEditText et_email, et_pass, et_name, et_num;
    public Button btn_signup;
    public TextView tv_login, tv_seller;
    public AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        btn_signup = findViewById(R.id.btn_signup);
        tv_login = findViewById(R.id.tv_login);
        tv_seller = findViewById(R.id.tv_seller);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.getStatusMsg().observe(this, msg -> {
            if (msg != null){
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        authViewModel.getStatusSuccess().observe(this, success -> {
            if (success.equals(Boolean.TRUE)){
                String email = et_email.getText().toString().trim();
                int userId = DatabaseHelper.getInstance(this).getUserId(email);
                SharedPreferences pref = getSharedPreferences("session", MODE_PRIVATE);
                pref.edit().putInt("userId",userId).apply();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btn_signup.setOnClickListener(v -> {
            String email = et_email.getText().toString().trim();
            String pass = et_pass.getText().toString().trim();
            String name = et_name.getText().toString().trim();
            String num = et_num.getText().toString().trim();
            authViewModel.signupUser(email, pass, name, num);
        });
        tv_login.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginUser.class);
            startActivity(i);
            finish();
        });
        tv_seller.setOnClickListener(v -> {
            Intent i = new Intent(this, SignupSeller.class);
            startActivity(i);
            finish();
        });
    }
}