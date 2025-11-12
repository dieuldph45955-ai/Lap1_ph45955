package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DpHelper.MyDbHelper;
import com.example.myapplication.Model.ProductDTO;

import java.util.ArrayList;

public class ProductDAO {
    private final MyDbHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new MyDbHelper(context);
    }

    public ArrayList<ProductDTO> getAllProducts() {
        ArrayList<ProductDTO> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Updated query to select id_cat
        Cursor c = db.rawQuery("SELECT id, name, price, id_cat FROM " + MyDbHelper.TB_PRODUCT + " ORDER BY id DESC", null);
        if (c.moveToFirst()) {
            do {
                list.add(new ProductDTO(c.getInt(0), c.getString(1), c.getDouble(2), c.getInt(3)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public long insertProduct(ProductDTO product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", product.getName());
        cv.put("price", product.getPrice());
        cv.put("id_cat", product.getId_cat()); // Changed to id_cat
        long id = db.insert(MyDbHelper.TB_PRODUCT, null, cv);
        db.close();
        return id;
    }

    public int updateProduct(ProductDTO product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", product.getName());
        cv.put("price", product.getPrice());
        cv.put("id_cat", product.getId_cat()); // Changed to id_cat
        int rows = db.update(MyDbHelper.TB_PRODUCT, cv, "id=?", new String[]{String.valueOf(product.getId())});
        db.close();
        return rows;
    }

    public int deleteProduct(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(MyDbHelper.TB_PRODUCT, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
}
