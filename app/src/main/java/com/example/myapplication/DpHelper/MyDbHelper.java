package com.example.myapplication.DpHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "lab1.db";
    // Incremented DB version to trigger onUpgrade
    private static final int DB_VERSION = 3;

    public static final String TB_CAT = "tb_cat";
    public static final String TB_PRODUCT = "tb_product";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyDbHelper", "onCreate is called.");
        // Table for Categories
        String sqlCat = "CREATE TABLE " + TB_CAT + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL" +
                ")";
        db.execSQL(sqlCat);

        // Table for Products with id_cat as a foreign key
        String sqlProduct = "CREATE TABLE " + TB_PRODUCT + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "id_cat INTEGER NOT NULL, " +
                "FOREIGN KEY(id_cat) REFERENCES " + TB_CAT + "(id)" +
                ")";
        db.execSQL(sqlProduct);

        // Sample data for categories
        db.execSQL("INSERT INTO " + TB_CAT + " (name) VALUES ('Electronics')"); // id = 1
        db.execSQL("INSERT INTO " + TB_CAT + " (name) VALUES ('Books')");       // id = 2
        db.execSQL("INSERT INTO " + TB_CAT + " (name) VALUES ('Clothing')");    // id = 3

        // Sample data for products
        db.execSQL("INSERT INTO " + TB_PRODUCT + " (name, price, id_cat) VALUES ('Laptop Pro', 1200.0, 1)");
        db.execSQL("INSERT INTO " + TB_PRODUCT + " (name, price, id_cat) VALUES ('Android Programming Guide', 55.5, 2)");
        db.execSQL("INSERT INTO " + TB_PRODUCT + " (name, price, id_cat) VALUES ('T-Shirt', 25.0, 3)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyDbHelper", "onUpgrade is called from version " + oldVersion + " to " + newVersion);
        // Drop tables and recreate them if schema is upgraded
        db.execSQL("DROP TABLE IF EXISTS " + TB_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TB_CAT);
        onCreate(db);
    }
}
