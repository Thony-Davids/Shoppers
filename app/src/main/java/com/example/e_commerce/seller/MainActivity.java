package com.example.e_commerce.seller;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;
import com.example.e_commerce.seller.parent.OrdersFragment;
import com.example.e_commerce.seller.parent.ProfileFragment;
import com.example.e_commerce.seller.parent.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.seller_bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item->{
            int id = item.getItemId();

            if(id == R.id.shop_seller_navigation){
                getSupportFragmentManager().beginTransaction().replace(R.id.seller_fragment_container, new ShopFragment()).commit();
            }
            else if(id == R.id.orders_seller_navigation){
                getSupportFragmentManager().beginTransaction().replace(R.id.seller_fragment_container, new OrdersFragment()).commit();
            }
            else if(id == R.id.profile_seller_navigation){
                getSupportFragmentManager().beginTransaction().replace(R.id.seller_fragment_container, new ProfileFragment()).commit();
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.shop_seller_navigation);

    }
}