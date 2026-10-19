
package com.example.e_commerce.seller.categories;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_commerce.R;
import com.example.e_commerce.seller.adapters.SellerCategoriesAdapter;
import com.example.e_commerce.seller.datamodel.Product;
import com.example.e_commerce.seller.db.ViewModels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class BeveragesFragment extends Fragment {

    private GridView beverages_gv;
    private SellerCategoriesAdapter adapter;
    private ProductViewModel productViewModel;

    public BeveragesFragment() {
        super(R.layout.fragment_beverages);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        beverages_gv = view.findViewById(R.id.beverages_gv);
        adapter = new SellerCategoriesAdapter(requireContext());
        beverages_gv.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this)
                .get(ProductViewModel.class);

        SharedPreferences pref = requireActivity()
                .getSharedPreferences("session", MODE_PRIVATE);

        int sellerId = pref.getInt("sellerId", -1);

        if (sellerId == -1) {
            Toast.makeText(requireContext(),
                    "Seller ID not found",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        productViewModel.getProductList().observe(
                getViewLifecycleOwner(), products -> {

                    List<Product> beverages = new ArrayList<>();

                    if (products != null) {
                        for (Product product : products) {
                            if ("Beverages".equalsIgnoreCase(
                                    product.getCgy())) {
                                beverages.add(product);
                            }
                        }
                    }

                    adapter.setProducts(beverages);
                });

        productViewModel.getAllSellerProduct(sellerId);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (productViewModel != null) {
            SharedPreferences pref = requireActivity()
                    .getSharedPreferences("session", MODE_PRIVATE);

            int sellerId = pref.getInt("sellerId", -1);

            if (sellerId != -1) {
                productViewModel.getAllSellerProduct(sellerId);
            }
        }
    }
}
