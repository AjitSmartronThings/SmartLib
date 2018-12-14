package com.things.smartcam.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.things.smartcam.DbCamera;
import com.things.smartcam.R;
import com.things.smartcam.TronXCamera;

import java.util.List;

public class CameraAdapter extends RecyclerView.Adapter<CameraViewHolder> {

    private Context context;
    private List<TronXCamera> listProducts;

    private DbCamera mDatabase;

    ItemClickListener itemClickListener;

    public CameraAdapter(Context context, List<TronXCamera> listProducts,ItemClickListener clickListener) {
        this.context = context;
        this.listProducts = listProducts;
        this.itemClickListener=clickListener;
        mDatabase = new DbCamera(context);
    }

    @Override
    public CameraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.camera_list_layout, parent, false);
        return new CameraViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CameraViewHolder holder, int position) {
        final TronXCamera singleProduct = listProducts.get(position);

        holder.name.setText(singleProduct.getName());

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteCamera(String.valueOf(singleProduct.getId()));

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos, TronXCamera tronXCamera) {
                TronXCamera tronXCamera1 = mDatabase.getCamera(singleProduct.getId());
                //Toast.makeText(context, ""+tronXCamera1.getInternalHost()+" "+tronXCamera1.getInternalHttp(), Toast.LENGTH_SHORT).show();
                itemClickListener.onItemClick(v,pos,tronXCamera1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }


   /* private void editTaskDialog(final TronXCamera product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);

        if(product != null){
            nameField.setText(product.getName());
            quantityField.setText(String.valueOf(product.getInternalHost()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final int quantity = Integer.parseInt(quantityField.getText().toString());

                if(TextUtils.isEmpty(name) || quantity <= 0){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateCamera(product);
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }*/
}
