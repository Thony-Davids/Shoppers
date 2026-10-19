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
import com.example.e_commerce.seller.Auth.LoginSeller;
import com.example.e_commerce.seller.MainActivity;
import com.example.e_commerce.seller.db.DatabaseHelper;
import com.example.e_commerce.seller.db.ViewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginUser extends AppCompatActivity {
    public TextInputEditText et_email, et_pass;
    public Button btn_login;
    public TextView tv_signup, tv_seller;

    public AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        tv_signup = findViewById(R.id.tv_signup);
        tv_seller = findViewById(R.id.tv_seller);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

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
        authViewModel.getStatusMsg().observe(this, msg -> {
            if (msg != null){
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btn_login.setOnClickListener(v -> {
            String email = et_email.getText().toString().trim();
            String pass =et_pass.getText().toString().trim();
            authViewModel.loginUser(email, pass);
        });

        tv_signup.setOnClickListener(v -> {
            Intent i = new Intent(this, SignupUser.class);
            startActivity(i);
            finish();
        });
        tv_seller.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginSeller.class);
            startActivity(i);
            finish();
        });
    }
}