package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.CatDAO;
import com.example.myapplication.DAO.ProductDAO;
import com.example.myapplication.Model.CatDTO;
import com.example.myapplication.Model.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class AddEditProductActivity extends AppCompatActivity {

    private EditText etProductName, etProductPrice;
    private Spinner spinnerCategory;
    private Button btnSaveProduct;

    private ProductDAO productDAO;
    private CatDAO catDAO;
    private List<CatDTO> categoryList;
    private ProductDTO currentProduct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        // Initialize DAOs
        productDAO = new ProductDAO(this);
        catDAO = new CatDAO(this);

        // Initialize Views
        etProductName = findViewById(R.id.et_product_name);
        etProductPrice = findViewById(R.id.et_product_price);
        spinnerCategory = findViewById(R.id.spinner_category);
        btnSaveProduct = findViewById(R.id.btn_save_product);

        // Populate Spinner with categories
        categoryList = catDAO.getAll();
        ArrayAdapter<CatDTO> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Check if we are editing an existing product
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PRODUCT")) {
            currentProduct = (ProductDTO) intent.getSerializableExtra("PRODUCT");
            etProductName.setText(currentProduct.getName());
            etProductPrice.setText(String.valueOf(currentProduct.getPrice()));
            setTitle("Edit Product");

            // Set the selected category in the spinner
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == currentProduct.getId_cat()) {
                    spinnerCategory.setSelection(i);
                    break;
                }
            }
        } else {
            setTitle("Add Product");
        }

        btnSaveProduct.setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {
        String name = etProductName.getText().toString().trim();
        String priceStr = etProductPrice.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerCategory.getSelectedItem() == null) {
            Toast.makeText(this, "Please add a category first", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            CatDTO selectedCategory = (CatDTO) spinnerCategory.getSelectedItem();
            int categoryId = selectedCategory.getId();

            if (currentProduct != null) {
                // Update existing product
                currentProduct.setName(name);
                currentProduct.setPrice(price);
                currentProduct.setId_cat(categoryId);
                productDAO.updateProduct(currentProduct);
                Toast.makeText(this, "Product updated", Toast.LENGTH_SHORT).show();
            } else {
                // Add new product
                ProductDTO newProduct = new ProductDTO(0, name, price, categoryId);
                productDAO.insertProduct(newProduct);
                Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
            }
            finish(); // Go back to the product list
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
        }
    }
}
