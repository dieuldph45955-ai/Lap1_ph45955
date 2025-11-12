package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapter.ProductAdapter;
import com.example.myapplication.DAO.ProductDAO;
import com.example.myapplication.Model.ProductDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private ListView lvProducts;
    private FloatingActionButton fabAddProduct;
    private ProductDAO productDAO;
    private ArrayList<ProductDTO> productList;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        lvProducts = findViewById(R.id.lv_products);
        fabAddProduct = findViewById(R.id.fab_add_product);
        productDAO = new ProductDAO(this);

        // Set up the adapter
        productList = productDAO.getAllProducts();
        adapter = new ProductAdapter(this, productList);
        lvProducts.setAdapter(adapter);

        // Add Product button
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, AddEditProductActivity.class);
                startActivity(intent);
            }
        });

        // Edit Product on item click
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDTO selectedProduct = productList.get(position);
                Intent intent = new Intent(ProductActivity.this, AddEditProductActivity.class);
                intent.putExtra("PRODUCT", selectedProduct);
                startActivity(intent);
            }
        });

        // Delete Product on item long click
        lvProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final ProductDTO selectedProduct = productList.get(position);

                new AlertDialog.Builder(ProductActivity.this)
                        .setTitle("Delete Product")
                        .setMessage("Are you sure you want to delete '" + selectedProduct.getName() + "'?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                productDAO.deleteProduct(selectedProduct.getId());
                                // Refresh the list
                                productList.remove(selectedProduct);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ProductActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true; // Consume the long click event
            }
        });
    }

    // Refresh the list when returning to the activity
    @Override
    protected void onResume() {
        super.onResume();
        refreshProductList();
    }

    private void refreshProductList() {
        productList.clear();
        productList.addAll(productDAO.getAllProducts());
        adapter.notifyDataSetChanged();
    }
}
