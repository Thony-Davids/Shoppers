package com.example.e_commerce.seller.db;

import android.content.Context;

import com.example.e_commerce.seller.datamodel.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    public final DatabaseHelper dbHelper;
    public ExecutorService executor = Executors.newSingleThreadExecutor();
    public interface Callback {
        void onResult(boolean success, String msg);
    }
    public interface ProductCallback {
        void onFetch(List<Product> products);
    }
    public Repository(Context context){
        dbHelper = DatabaseHelper.getInstance(context.getApplicationContext());
    }
    public void loginUser(String email, String pass, Callback callback){
        executor.execute(() ->{
            boolean result = dbHelper.checkUserCredentials(email, pass);
            if (result){
                callback.onResult(true, "Successfully Login.");
            } else {
                callback.onResult(false, "Failed to Login.");
            }
        });
    }
    public void signupUser(String email, String pass, String name, int num, Callback callback){
        executor.execute(() -> {
            if (dbHelper.checkUserEmail(email)){
                callback.onResult(false, "Email already Exist");
                return;
            }
            boolean result = dbHelper.addUser(email, pass, name, num);
            if (result){
                callback.onResult(true, "Successfully Signup");
            } else {
                callback.onResult(false, "Failed to Signup");
            }
        });

    }
    public void loginSeller(String email, String pass, Callback callback){
        executor.execute(() ->{
            boolean result = dbHelper.checkSellerCredentials(email, pass);
            if (result){
                callback.onResult(true, "Successfully Login.");
            } else {
                callback.onResult(false, "Failed to Login.");
            }
        });
    }
    public void signupSeller(String email, String pass, String name, int num, Callback callback){
        executor.execute(() -> {
            if (dbHelper.checkSellerEmail(email)){
                callback.onResult(false, "Email already Exist");
                return;
            }
            boolean result = dbHelper.addSeller(email, pass, name, num);
            if (result){
                callback.onResult(true, "Successfully Signup");
            } else {
                callback.onResult(false, "Failed to Signup");
            }
        });
    }
    public void addProduct(String name, String desc, double price, int quan, String cgy, String img, int seller, Callback callback){
        executor.execute(() -> {
            boolean result = dbHelper.addProducts(name, desc, price, quan, cgy, img, seller);
            if (result){
                callback.onResult(true, "Successfully Added");
            } else {
                callback.onResult(false, "Failed to Add.");
            }
        });
    }
    public void getAllProducts(ProductCallback callback){
        executor.execute(() -> {
            List<Product> products = dbHelper.getAllProducts();
            callback.onFetch(products);
        });
    }
    public void getAllSellerProducts(int sellerId, ProductCallback callback){
        executor.execute(() -> {
            List<Product> products = dbHelper.getAllSellerProducts(sellerId);
            callback.onFetch(products);
        });
    }
}