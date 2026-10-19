package com.example.e_commerce.seller.db;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.e_commerce.seller.datamodel.Product;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    private static final String DB_NAME = "db";
    private static final int DB_VER = 1;

    //Users
    private static final String TABLE_USERS = "users";
    private static final String USERS_ID = "id";
    private static final String USERS_EMAIL = "email";
    private static final String USERS_PASS = "password";
    private static final String USERS_NAME = "username";
    private static final String USERS_NUM = "number";

    //seller
    private static final String TABLE_SELLER = "sellers";
    private static final String SELLER_ID = "id";
    private static final String SELLER_EMAIL = "email";
    private static final String SELLER_PASS = "password";
    private static final String SELLER_NAME = "username";
    private static final String SELLER_NUM = "number";

    //products
    private static final String TABLE_PRODUCT = "products";
    private static final String PROD_ID = "id";
    private static final String PROD_NAME = "name";
    private static final String PROD_DESC = "description";
    private static final String PROD_PRICE = "price";
    private static final String PROD_QUAN = "quantity";
    private static final String PROD_CGY = "category";
    private static final String PROD_IMG = "image";
    private static final String PROD_SELLER = "sellerId";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VER);

    }
    public static DatabaseHelper getInstance(Context context){
        if (instance== null){
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    //private static final String TABLE_PRODUCT = "products";
    //    private static final String PROD_ID = "id";
    //    private static final String PROD_NAME = "name";
    //    private static final String PROD_DESC = "description";
    //    private static final String PROD_PRICE = "price";
    //    private static final String PROD_QUAN = "quantity";
    //    private static final String PROD_CGY = "category";
    //    private static final String PROD_SELLER = "sellerId";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ( "+
                USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                USERS_EMAIL +" TEXT UNIQUE NOT NULL, " +
                USERS_PASS + " TEXT NOT NULL, " +
                USERS_NAME + " TEXT NOT NULL, " +
                USERS_NUM + " INTEGER NOT NULL)";
        String CREATE_TABLE_SELLERS = "CREATE TABLE " + TABLE_SELLER + " ( "+
                SELLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                SELLER_EMAIL +" TEXT UNIQUE NOT NULL, " +
                SELLER_PASS + " TEXT NOT NULL, " +
                SELLER_NAME + " TEXT NOT NULL, " +
                SELLER_NUM + " INTEGER NOT NULL)";
        String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCT + " ( " +
                PROD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PROD_NAME + " TEXT NOT NULL, " +
                PROD_DESC + " TEXT NOT NULL, " +
                PROD_PRICE + " REAL NOT NULL, " +
                PROD_QUAN + " INTEGER NOT NULL, " +
                PROD_CGY + " TEXT NOT NULL, " +
                PROD_IMG + " TEXT NOT NULL," +
                PROD_SELLER + " INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_SELLERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.equals("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.equals("DROP TABLE IF EXISTS " + TABLE_SELLER);
        sqLiteDatabase.equals("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
    }
    public boolean checkUserCredentials(String email, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                USERS_EMAIL + " =? AND " + USERS_PASS + " =?",  new String[]{email, pass});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean checkUserEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                USERS_EMAIL + " =?" ,  new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean addUser(String email, String pass, String name, int num){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_EMAIL, email);
        values.put(USERS_PASS, pass);
        values.put(USERS_NAME, name);
        values.put(USERS_NUM, num);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }
    public int getUserId(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;
        Cursor cursor = db.rawQuery("SELECT " + USERS_ID + " FROM " + TABLE_USERS + " WHERE "+
                USERS_EMAIL + " =?", new String[]{email});
        if (cursor.moveToFirst()){
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }
    public int getSellerId(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;
        Cursor cursor = db.rawQuery("SELECT " + SELLER_ID + " FROM " + TABLE_SELLER + " WHERE "+
                SELLER_EMAIL + " =?", new String[]{email});
        if (cursor.moveToFirst()){
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }
    public boolean checkSellerCredentials(String email, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SELLER + " WHERE " +
                SELLER_EMAIL + " =? AND " + SELLER_PASS + " =?",  new String[]{email, pass});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean checkSellerEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SELLER + " WHERE " +
                SELLER_EMAIL + " =?" ,  new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean addSeller(String email, String pass, String name, int num){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(SELLER_EMAIL, email);
        values.put(SELLER_PASS, pass);
        values.put(SELLER_NAME, name);
        values.put(SELLER_NUM, num);
        long result = db.insert(TABLE_SELLER, null, values);
        return result != -1;
    }

    // products
    // private static final String TABLE_PRODUCT = "products";
    //    private static final String PROD_ID = "id";
    //    private static final String PROD_NAME = "name";
    //    private static final String PROD_DESC = "description";
    //    private static final String PROD_PRICE = "price";
    //    private static final String PROD_QUAN = "quantity";
    //    private static final String PROD_CGY = "category";
    //    private static final String PROD_SELLER = "sellerId";
    public boolean addProducts(String name, String desc, double price, int quan, String cgy, String img, int seller){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROD_NAME, name);
        values.put(PROD_DESC, desc);
        values.put(PROD_PRICE, price);
        values.put(PROD_QUAN, quan);
        values.put(PROD_CGY, cgy);
        values.put(PROD_IMG, img);
        values.put(PROD_SELLER, seller);
        long result = db.insert(TABLE_PRODUCT, null, values);
        return result != -1;
    }
    public List<Product> getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> products = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT, new String[]{});
        while (cursor.moveToNext()){
            int idIndex = cursor.getColumnIndexOrThrow(PROD_ID);
            int nameIndex = cursor.getColumnIndexOrThrow(PROD_NAME);
            int descIndex = cursor.getColumnIndexOrThrow(PROD_DESC);
            int priceIndex = cursor.getColumnIndexOrThrow(PROD_PRICE);
            int quanIndex = cursor.getColumnIndexOrThrow(PROD_QUAN);
            int cgyIndex = cursor.getColumnIndexOrThrow(PROD_CGY);
            int imgIndex = cursor.getColumnIndexOrThrow(PROD_IMG);
            int sellerIndex = cursor.getColumnIndexOrThrow(PROD_SELLER);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String desc = cursor.getString(descIndex);
            double price = cursor.getDouble(priceIndex);
            int quan = cursor.getInt(quanIndex);
            String cgy = cursor.getString(cgyIndex);
            String img = cursor.getString(imgIndex);
            int seller = cursor.getInt(sellerIndex);
            products.add(new Product(id, name, desc, price, quan, cgy, img, seller));
        }
        cursor.close();
        return products;
    }
    public List<Product> getAllSellerProducts(int sellerId){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> products = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT +
                " WHERE " + PROD_SELLER + " =? ", new String[]{String.valueOf(sellerId)});
        while (cursor.moveToNext()){
            int idIndex = cursor.getColumnIndexOrThrow(PROD_ID);
            int nameIndex = cursor.getColumnIndexOrThrow(PROD_NAME);
            int descIndex = cursor.getColumnIndexOrThrow(PROD_DESC);
            int priceIndex = cursor.getColumnIndexOrThrow(PROD_PRICE);
            int quanIndex = cursor.getColumnIndexOrThrow(PROD_QUAN);
            int cgyIndex = cursor.getColumnIndexOrThrow(PROD_CGY);
            int imgIndex = cursor.getColumnIndexOrThrow(PROD_IMG);
            int sellerIndex = cursor.getColumnIndexOrThrow(PROD_SELLER);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String desc = cursor.getString(descIndex);
            double price = cursor.getDouble(priceIndex);
            int quan = cursor.getInt(quanIndex);
            String cgy = cursor.getString(cgyIndex);
            String img = cursor.getString(imgIndex);
            int seller = cursor.getInt(sellerIndex);
            products.add(new Product(id, name, desc, price, quan, cgy, img, seller));
        }
        cursor.close();
        return products;
    }
}
