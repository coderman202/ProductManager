package com.example.android.productmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.productmanager.R;
import com.example.android.productmanager.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Reggie on 24/07/2017.
 * Custom dialog class
 */

public class AddProductConfirmDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private Product product;

    @BindView(R.id.product_name)
    TextView nameView;
    @BindView(R.id.product_image)
    ImageView imageView;
    @BindView(R.id.product_quantity)
    TextView quantityView;
    @BindView(R.id.product_price)
    TextView priceView;
    @BindView(R.id.product_supplier)
    TextView supplierView;
    @BindView(R.id.product_category)
    TextView categoryView;
    @BindView(R.id.yes_button)
    TextView yesView;
    @BindView(R.id.no_button)
    TextView noView;


    public AddProductConfirmDialog(Context context, Product product) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.product = product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_confirm_dialog);

        ButterKnife.bind(this);

        yesView.setOnClickListener(this);
        noView.setOnClickListener(this);

        String productName = product.getProductName();
        float price = product.getSalePrice();
        int quantity = product.getQuantity();
        String quantityUnit = product.getQuantityUnit();

        String supplierName = product.getSupplier().getSupplierName();
        String categoryName = product.getCategory().getCategoryName();

        int productImageResID = product.getPicID();

        String productQuantity = context.getString(R.string.product_quantity, quantity, quantityUnit);
        String productPrice = context.getString(R.string.product_price, price, quantityUnit);
        String categoryText = context.getString(R.string.product_category, categoryName);
        String supplierText = context.getString(R.string.product_supplier, supplierName);

        nameView.setText(productName);
        imageView.setImageResource(productImageResID);
        priceView.setText(productPrice);
        quantityView.setText(productQuantity);
        categoryView.setText(categoryText);
        supplierView.setText(supplierText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_button:
                break;
            case R.id.no_button:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
