package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DpHelper.MyDbHelper;
import com.example.myapplication.Model.CatDTO;

import java.util.ArrayList;
import java.util.List;

public class CatDAO {
    private MyDbHelper dbHelper;

    public CatDAO(Context context){
        dbHelper = new MyDbHelper(context);
    }

    public long insert(CatDTO cat){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", cat.getName());
        long id = db.insert(MyDbHelper.TB_CAT, null, cv);
        db.close();
        return id;
    }

    public int update(CatDTO cat){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", cat.getName());
        int rows = db.update(MyDbHelper.TB_CAT, cv, "id=?", new String[]{String.valueOf(cat.getId())});
        db.close();
        return rows;
    }

    public int delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(MyDbHelper.TB_CAT, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public List<CatDTO> getAll(){
        List<CatDTO> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name FROM " + MyDbHelper.TB_CAT + " ORDER BY id", null);
        if(c.moveToFirst()){
            do {
                list.add(new CatDTO(c.getInt(0), c.getString(1)));
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public CatDTO getById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name FROM " + MyDbHelper.TB_CAT + " WHERE id=?", new String[]{String.valueOf(id)});
        CatDTO cat = null;
        if(c.moveToFirst()){
            cat = new CatDTO(c.getInt(0), c.getString(1));
        }
        c.close();
        db.close();
        return cat;
    }

}
