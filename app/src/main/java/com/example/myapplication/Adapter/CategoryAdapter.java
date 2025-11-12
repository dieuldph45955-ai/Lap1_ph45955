package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.Model.CatDTO;
import com.example.myapplication.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CatDTO> {

    public CategoryAdapter(Context context, List<CatDTO> categoryList) {
        super(context, 0, categoryList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CatDTO category = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }

        TextView tvCatId = convertView.findViewById(R.id.tv_cat_id);
        TextView tvCatName = convertView.findViewById(R.id.tv_cat_name);

        if (category != null) {
            tvCatId.setText("ID: " + category.getId());
            tvCatName.setText("Name: " + category.getName());
        }

        return convertView;
    }
}
