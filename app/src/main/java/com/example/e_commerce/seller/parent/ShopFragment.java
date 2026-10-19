package com.example.e_commerce.seller.parent;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.seller.AddProduct;
import com.example.e_commerce.seller.adapters.ViewPagerAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ShopFragment extends Fragment {

    public ShopFragment() {
        // Required empty public constructor
        super(R.layout.fragment_shop);
    }

    ViewPager2 seller_viewpager;
    TabLayout seller_tabLayout;
    MaterialButton product_add;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seller_viewpager = view.findViewById(R.id.seller_viewpager);
        seller_tabLayout = view.findViewById(R.id.seller_tabLayout);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        seller_viewpager.setAdapter(adapter);

        new TabLayoutMediator(seller_tabLayout, seller_viewpager, (tab, position)->{
            if(position == 0)tab.setText("All");
            if(position == 1)tab.setText("Food");
            if(position == 2)tab.setText("Beverages");
            if(position == 3)tab.setText("Clothes");
            if(position == 4)tab.setText("Electronic");
        }).attach();

        product_add = view.findViewById(R.id.seller_add);
        product_add.setOnClickListener(v->{
            SharedPreferences pref = requireActivity().getSharedPreferences("session", MODE_PRIVATE);
            int sellerId = pref.getInt("sellerId", -1);
            if(sellerId < 0){
                Toast.makeText(requireContext(), "Seller id not found, please log in again", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(requireContext(), AddProduct.class);
            intent.putExtra("sellerId", sellerId);
            startActivity(intent);
        });
    }
}