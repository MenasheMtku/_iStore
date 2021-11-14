package com.example.istore.Adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.istore.Model.Vendor;
import com.example.istore.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class VendorAdapter extends FirestoreRecyclerAdapter<Vendor,VendorAdapter.VendorHolder> {

   final Context context;

    public VendorAdapter(@NonNull FirestoreRecyclerOptions<Vendor> options, Context context) {
        super(options);
        this.context = context;
    }
//    public VendorAdapter(List<Vendor> vendorList, Context context) {
//        this.vendorList = vendorList;
//        this.context = context;
//    }

    @NonNull
    @Override
    public VendorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_row,null,false);
        return new VendorHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_row,null,false));

    }

    @Override
    public void onBindViewHolder(@NonNull VendorHolder holder, int position, Vendor model) {
        holder.v_name.setText(model.getName());
        holder.v_contact.setText(model.getContact());
        try {
            Glide.with(this.context).load(model.getImgUrl()).into(holder.v_image);
        } catch (Exception e) {
            holder.v_image.setImageResource(R.drawable.ic_outline_no_image_24);
        }

//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                context.startActivity(this);
//                Snackbar.make(view, "holder has been clicked", Snackbar.LENGTH_SHORT)
//                        .show();
//            }
//        });
    }

    public class VendorHolder extends RecyclerView.ViewHolder{
        ImageView v_image;
        TextView v_name;
        TextView v_contact;
//        LinearLayout parentLayout;
        public VendorHolder(@NonNull View itemView) {
            super(itemView);
            v_image = itemView.findViewById(R.id.vendor_img_iv);
            v_name = itemView.findViewById(R.id.vendor_name_tv);
            v_contact = itemView.findViewById(R.id.vendor_contact_tv);
//            parentLayout = itemView.findViewById(R.id.v_layout_id);
        }
    }
}
