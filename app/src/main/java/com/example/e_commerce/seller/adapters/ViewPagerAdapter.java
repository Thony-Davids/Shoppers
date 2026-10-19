package com.example.e_commerce.seller.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.e_commerce.seller.categories.AllFragment;
import com.example.e_commerce.seller.categories.BeveragesFragment;
import com.example.e_commerce.seller.categories.ClothesFragment;
import com.example.e_commerce.seller.categories.ElectronicsFragment;
import com.example.e_commerce.seller.categories.FoodFragment;
import com.example.e_commerce.seller.parent.ShopFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) return new AllFragment();
        if(position == 1) return new FoodFragment();
        if(position == 2) return new BeveragesFragment();
        if(position == 3) return new ClothesFragment();
        return new ElectronicsFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
