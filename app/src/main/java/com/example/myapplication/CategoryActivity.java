package com.example.myapplication;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.DAO.CatDAO;
import com.example.myapplication.Model.CatDTO;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private ListView lvCategories;
    private CatDAO catDAO;
    private List<CatDTO> categoryList;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        lvCategories = findViewById(R.id.lv_categories);
        catDAO = new CatDAO(this);
        categoryList = catDAO.getAll();

        adapter = new CategoryAdapter(this, categoryList);
        lvCategories.setAdapter(adapter);
    }
}
