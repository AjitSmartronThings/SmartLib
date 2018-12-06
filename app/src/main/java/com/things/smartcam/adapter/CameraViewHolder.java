package com.things.smartcam.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.things.smartcam.R;


public class CameraViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView deleteProduct;
    public ImageView editProduct;

    public CameraViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.product_name);
        deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
        editProduct = (ImageView)itemView.findViewById(R.id.edit_product);
    }
}
