package com.example.e_commerce.seller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_commerce.R;
import com.example.e_commerce.seller.datamodel.Product;
import com.example.e_commerce.seller.db.DatabaseHelper;
import com.example.e_commerce.seller.db.ViewModels.ProductViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddProduct extends AppCompatActivity {

    private EditText et_name;
    private EditText et_description;
    private EditText et_price;
    private EditText et_quantity;
    private Spinner product_category;
    MaterialButton button_save;
    MaterialButton button_back;
    MaterialButton button_select;
    ProductViewModel productViewModel;
    private ImageView iv_product_preview;
    private String imagePath = "";
    private ActivityResultLauncher<String> imagePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        et_name = findViewById(R.id.et_product_name);
        et_description = findViewById(R.id.et_product_description);
        et_price = findViewById(R.id.et_product_price);
        et_quantity = findViewById(R.id.et_product_quantity);
        product_category = findViewById(R.id.spinner_product_category);
        button_save = findViewById(R.id.btn_save_product);
        button_back = findViewById(R.id.btn_back);
        iv_product_preview = findViewById(R.id.iv_product_preview);
        button_select = findViewById(R.id.btn_select_image);

        imagePicker = registerForActivityResult(new ActivityResultContracts.GetContent(), uri->{
            if(uri != null){
                try {
                    imagePath = copyImageToInternalStorage(uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                    if(bitmap != null){
                        iv_product_preview.setImageBitmap(bitmap);
                    }
                    else{
                        imagePath = "";
                        Toast.makeText(this, "unable to load image", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (IOException e){
                    imagePath = "";
                    Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_select.setOnClickListener(v->{
            imagePicker.launch("image/*");
        });

        String[] categories = {
                "Food",
                "Beverages",
                "Clothes",
                "Electronics"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_category.setAdapter(adapter);

        button_save.setOnClickListener(v->{
            String name = et_name.getText().toString().trim();
            String description = et_description.getText().toString().trim();
            String price = et_price.getText().toString().trim();
            String quantity = et_quantity.getText().toString().trim();
            String category = product_category.getSelectedItem().toString();

            int sellerId = getIntent().getIntExtra("sellerId", -1);

            if(sellerId < 0 ){
                Toast.makeText(this, "Please login with correct login credentials", Toast.LENGTH_SHORT).show();
                return;
            }

            if(imagePath.isEmpty()){
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
                return;
            }

            productViewModel.addProduct(
                    name,
                    description,
                    price,
                    quantity,
                    category,
                    imagePath,
                    sellerId);
        });

        button_back.setOnClickListener(v->{
            Intent i = new Intent(AddProduct.this, MainActivity.class);
            startActivity(i);
        });

        productViewModel.getStatusMsg().observe(this, msg -> {
            if (msg != null) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        productViewModel.getStatusSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                setResult(RESULT_OK);
                finish();
            }
        });


    }

    private String copyImageToInternalStorage(Uri uri)throws IOException {
        File imageDirectory = new File(getFilesDir(), "product_images");

        if(!imageDirectory.exists() && !imageDirectory.mkdirs()){
            throw new IOException("could not create image directory");
        }

        File destination = new File(imageDirectory, "product_"+System.currentTimeMillis()+".jpg");

        try (InputStream input = getContentResolver().openInputStream(uri);
             OutputStream output = new FileOutputStream(destination)){

            if(input == null){
                throw new IOException("could not open image");
            }
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1){
                output.write(buffer, 0, bytesRead);
            }
        }

        return destination.getAbsolutePath();
    }

}