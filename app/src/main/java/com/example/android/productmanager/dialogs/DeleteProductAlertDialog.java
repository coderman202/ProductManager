package com.example.android.productmanager.dialogs;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.productmanager.MainActivity;
import com.example.android.productmanager.R;
import com.example.android.productmanager.data.ProductManagerContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Reggie on 24/07/2017.
 * Custom dialog class which displays asks the user to confirm the delete of a product beforehand.
 */

public class DeleteProductAlertDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private int productID;
    private String deleteConfirmed;
    private String deleteCancelled;
    private String dialogTitle;

    @BindView(R.id.title_view)
    TextView titleView;
    @BindView(R.id.yes_button)
    TextView yesView;
    @BindView(R.id.no_button)
    TextView noView;


    public DeleteProductAlertDialog(Context context, int productID, String confirmed, String cancelled, String dialogTitle) {
        super(context);
        this.context = context;
        this.productID = productID;
        this.deleteCancelled = cancelled;
        this.deleteConfirmed = confirmed;
        this.dialogTitle = dialogTitle;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_product_yes_no_dialog);

        ButterKnife.bind(this);

        titleView.setText(dialogTitle);
        yesView.setOnClickListener(this);
        noView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_button:
                Uri uri = ContentUris.withAppendedId(ProductManagerContract.ProductEntry.CONTENT_URI, productID);
                context.getContentResolver().delete(uri, null, null);
                Toast.makeText(context, deleteConfirmed, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                break;
            case R.id.no_button:
                Toast.makeText(context, deleteCancelled, Toast.LENGTH_LONG).show();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}