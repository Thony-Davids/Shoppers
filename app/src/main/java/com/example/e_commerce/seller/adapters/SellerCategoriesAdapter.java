package com.example.e_commerce.seller.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.e_commerce.R;
import com.example.e_commerce.seller.datamodel.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SellerCategoriesAdapter extends ArrayAdapter<Product> {
    public SellerCategoriesAdapter(@NonNull Context context){
        super(context, R.layout.product_item_layout, new ArrayList<>());
    }

    public void setProducts(List<Product> productArrayList){
        clear();
        if(productArrayList != null){
            addAll(productArrayList);
        }
        notifyDataSetChanged();
    }

    public static class MyViewHolder{
        ImageView product_image;
        TextView product_name;
        TextView product_price;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        MyViewHolder myViewHolder;

        if(convertView == null){
            myViewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.product_item_layout,
                    parent,
                    false);

            myViewHolder.product_image = convertView.findViewById(R.id.product_image);
            myViewHolder.product_name = convertView.findViewById(R.id.product_name);
            myViewHolder.product_price = convertView.findViewById(R.id.product_price);
            convertView.setTag(myViewHolder);
        }
        else{
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        String imagePath = product.getImg();

        if (imagePath != null
                && !imagePath.isEmpty()
                && new File(imagePath).exists()) {

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            if (bitmap != null) {
                myViewHolder.product_image.setImageBitmap(bitmap);
            } else {
                myViewHolder.product_image.setImageResource(
                        R.drawable.ic_launcher_background
                );
            }

        } else {
            myViewHolder.product_image.setImageResource(
                    R.drawable.ic_launcher_background
            );
        }

        myViewHolder.product_name.setText(product.getName());
        myViewHolder.product_price.setText(String.valueOf(product.getPrice()));
        return convertView;
    }
}
