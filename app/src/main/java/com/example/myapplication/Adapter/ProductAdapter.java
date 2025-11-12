package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.ProductDTO;
import com.example.myapplication.R;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<ProductDTO> {

    public ProductAdapter(@NonNull Context context, @NonNull List<ProductDTO> productList) {
        super(context, 0, productList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        ProductDTO product = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        // Lookup view for data population
        TextView tvProductId = convertView.findViewById(R.id.tv_product_id);
        TextView tvProductName = convertView.findViewById(R.id.tv_product_name);
        TextView tvProductPrice = convertView.findViewById(R.id.tv_product_price);
        TextView tvProductCategoryId = convertView.findViewById(R.id.tv_product_category_id);

        // Populate the data into the template view using the data object
        if (product != null) {
            tvProductId.setText("ID: " + product.getId());
            tvProductName.setText("Name: " + product.getName());
            tvProductPrice.setText("Price: " + String.format("%.2f", product.getPrice()));
            tvProductCategoryId.setText("Category ID: " + product.getId_cat());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
