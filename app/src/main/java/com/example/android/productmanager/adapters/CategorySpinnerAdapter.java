package com.example.android.productmanager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.productmanager.R;
import com.example.android.productmanager.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Reggie on 23/07/2017.
 * Custom spinner adapter for the category selector in the AddNewProductActivity
 */

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {

    static class ViewHolder {
        @BindView(R.id.spinner_item_view)
        TextView spinnerItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public CategorySpinnerAdapter(Context context, List<Category> categoryList) {
        super(context, R.layout.spinner_item, categoryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        View spinnerItemView = convertView;

        if (spinnerItemView == null) {
            spinnerItemView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
            holder = new ViewHolder(spinnerItemView);
            spinnerItemView.setTag(holder);
        } else {
            holder = (ViewHolder) spinnerItemView.getTag();
        }

        Category currentCategory = getItem(position);

        holder.spinnerItem.setText(currentCategory.getCategoryName());

        return spinnerItemView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(this.getItem(position).getCategoryName());
        return label;
    }
}
