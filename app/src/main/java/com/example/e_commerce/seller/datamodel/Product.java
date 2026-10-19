package com.example.e_commerce.seller.datamodel;

import java.io.Serializable;

public class Product implements Serializable {
    //private static final String TABLE_PRODUCT = "products";
    //    private static final String PROD_ID = "id";
    //    private static final String PROD_NAME = "name";
    //    private static final String PROD_DESC = "description";
    //    private static final String PROD_PRICE = "price";
    //    private static final String PROD_QUAN = "quantity";
    //    private static final String PROD_CGY = "category";
    //    private static final String PROD_SELLER = "sellerId";
    int id;
    String name;
    String desc;
    double price;
    int quan;
    String cgy;
    String img;
    int seller;

    public Product(int id, String name, String desc, double price, int quan, String cgy, String img, int seller) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quan = quan;
        this.cgy = cgy;
        this.img = img;
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuan() {
        return quan;
    }

    public void setQuan(int quan) {
        this.quan = quan;
    }

    public String getCgy() {
        return cgy;
    }

    public void setCgy(String cgy) {
        this.cgy = cgy;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSeller() {
        return seller;
    }

    public void setSeller(int seller) {
        this.seller = seller;
    }
}
