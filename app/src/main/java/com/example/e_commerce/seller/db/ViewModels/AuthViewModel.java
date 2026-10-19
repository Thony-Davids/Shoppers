package com.example.e_commerce.seller.db.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.seller.db.Repository;


public class AuthViewModel extends AndroidViewModel {
    public final Repository repository;
    public MutableLiveData<Boolean> statusSuccess = new MutableLiveData<>();
    public MutableLiveData<String> statusMsg = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }
    public void loginUser(String email , String pass){
        if (email.isEmpty() || pass.isEmpty()){
            statusMsg.postValue("Fields must not be Empty");
            return;
        }
        repository.loginUser(email, pass, (success, msg) -> {
            statusSuccess.postValue(success);
            statusMsg.postValue(msg);
        });
    }
    public void signupUser(String email, String pass, String name, String num){
        if (email.isEmpty() || pass.isEmpty() || name.isEmpty() || num.isEmpty()){
            statusMsg.postValue("Fields must not be Empty");
            return;
        }
        repository.signupUser(email, pass, name, Integer.parseInt(num), (success, msg) -> {
            statusSuccess.postValue(success);
            statusMsg.postValue(msg);
        });
    }
    public void loginSeller(String email , String pass){
        if (email.isEmpty() || pass.isEmpty()){
            statusMsg.postValue("Fields must not be Empty");
            return;
        }
        repository.loginSeller(email, pass, (success, msg) -> {
            statusSuccess.postValue(success);
            statusMsg.postValue(msg);
        });
    }
    public void signupSeller(String email, String pass, String name, String num){
        if (email.isEmpty() || pass.isEmpty() || name.isEmpty() || num.isEmpty()){
            statusMsg.postValue("Fields must not be Empty");
            return;
        }
        repository.signupSeller(email, pass, name, Integer.parseInt(num), (success, msg) -> {
            statusSuccess.postValue(success);
            statusMsg.postValue(msg);
        });
    }

    public LiveData<Boolean> getStatusSuccess() {
        return statusSuccess;
    }

    public LiveData<String> getStatusMsg() {
        return statusMsg;
    }
}