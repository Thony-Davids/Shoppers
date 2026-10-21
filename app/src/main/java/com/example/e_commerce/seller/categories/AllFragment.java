package com.example.e_commerce.seller.categories;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.SearchView;
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

public class AllFragment extends Fragment {

    private GridView all_gv;
    private SellerCategoriesAdapter adapter;
    private ProductViewModel productViewModel;
    private SearchView all_search;
    List<Product> all_products = new ArrayList<>();

    public AllFragment() {
        super(R.layout.fragment_all);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Connect the GridView from fragment_all.xml
        all_gv = view.findViewById(R.id.all_gv);
        all_search = view.findViewById(R.id.all_search);

        // 2. Create and attach the adapter
        adapter = new SellerCategoriesAdapter(requireContext());
        all_gv.setAdapter(adapter);

        // 3. Get the ViewModel
        productViewModel = new ViewModelProvider(this)
                .get(ProductViewModel.class);

        // 4. Get the logged-in seller's ID
        SharedPreferences pref = requireActivity()
                .getSharedPreferences("session", MODE_PRIVATE);

        int sellerId = pref.getInt("sellerId", -1);

        if (sellerId == -1) {
            Toast.makeText(requireContext(),
                    "Seller ID not found. Please log in again.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 5. Observe the products returned by the ViewModel
        productViewModel.getProductList().observe(
                getViewLifecycleOwner(),
                products -> {
                    all_products = products != null ? new ArrayList<>(products) : new ArrayList<>();
                    filterProducts(all_search.getQuery().toString());
                }
        );

        all_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                filterProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String newText) {
                filterProducts(newText);
                return true;
            }
        });

        // 6. Fetch this seller's products from SQLite
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

    public void filterProducts(String query){
        List<Product> filteredProducts = new ArrayList<>();
        for(Product product : all_products){
            if(product.getName() != null && product.getName().toLowerCase().contains(query.trim().toLowerCase())){
                filteredProducts.add(product);
            }
        }adapter.setProducts(filteredProducts);
    }
}