package com.example.e_commerce.seller.categories;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.seller.adapters.SellerCategoriesAdapter;
import com.example.e_commerce.seller.datamodel.Product;
import com.example.e_commerce.seller.db.ViewModels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;


public class FoodFragment extends Fragment {

    private GridView food_gridView;
    ProductViewModel productViewModel;
    SellerCategoriesAdapter adapter;

    public FoodFragment() {
        // Required empty public constructor
        super(R.layout.fragment_food);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        food_gridView = view.findViewById(R.id.food_gv);
        adapter = new SellerCategoriesAdapter(requireContext());
        food_gridView.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        SharedPreferences pref = requireActivity().getSharedPreferences("session", MODE_PRIVATE);

        int sellerId = pref.getInt("sellerId", -1);
        if(sellerId == -1){
            Toast.makeText(requireContext(),"seller ID not found", Toast.LENGTH_SHORT).show();
            return;
        }

        productViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            List<Product> food = new ArrayList<>();

            if(products != null){
                for(Product product : products){
                    if("Food".equalsIgnoreCase(product.getCgy())){
                        food.add(product);
                    }
                }
            }
            adapter.setProducts(food);
        });
    }

    public void onResume(){
        super.onResume();

        if(productViewModel != null){
            SharedPreferences pref = requireActivity().getSharedPreferences("session", MODE_PRIVATE);
            int sellerId = pref.getInt("sellerId", -1);

            if(sellerId != -1){
                productViewModel.getAllSellerProduct(sellerId);
            }
        }
    }
}