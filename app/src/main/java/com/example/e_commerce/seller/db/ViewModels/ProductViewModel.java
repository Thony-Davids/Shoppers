package com.example.e_commerce.seller.db.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.e_commerce.seller.datamodel.Product;
import com.example.e_commerce.seller.db.Repository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    public Repository repository;
    public MutableLiveData<Boolean> statusSuccess = new MutableLiveData<>();
    public MutableLiveData<String> statusMsg = new MutableLiveData<>();
    public MutableLiveData<List<Product>> productList = new MutableLiveData<>();
    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }
    public void addProduct(String name, String desc, String price, String quan, String cgy, String img, int seller){
        if (name.isEmpty() || desc.isEmpty() || price.isEmpty() || quan.isEmpty() || cgy.isEmpty() || img.isEmpty()){
            statusMsg.postValue("Fields must not be Empty");
            return;
        }

        repository.addProduct(name, desc, Double.parseDouble(price), Integer.parseInt(quan), cgy, img, seller, (success, msg) -> {
            statusMsg.postValue(msg);
            statusSuccess.postValue(success);
        });
    }
    public void getAllProduct(){
        repository.getAllProducts(productList::postValue);
    }
    public void getAllSellerProduct(int sellerId){
        repository.getAllSellerProducts(sellerId, productList::postValue);
    }

    public LiveData<Boolean> getStatusSuccess() {
        return statusSuccess;
    }

    public LiveData<String> getStatusMsg() {
        return statusMsg;
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }
}