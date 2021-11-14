package com.example.istore.Adapter.Admin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istore.Manager.OrderDetails;
import com.example.istore.Model.AddressModel;
import com.example.istore.Model.CheckoutModel;
import com.example.istore.R;
import com.example.istore.User.UserAddress;
import com.example.istore.User.UserCart;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;


public class ViewOrdersAdapter extends FirestoreRecyclerAdapter<CheckoutModel, ViewOrdersAdapter.OrderHolder> {

    final private Context context;
    OrderListner orderListner;
    public ViewOrdersAdapter(@NonNull FirestoreRecyclerOptions<CheckoutModel> options, Context context ,OrderListner orderListner) {

        super(options);
        this.context = context;
        this.orderListner = orderListner;


    }
    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manger_order_row, parent, false));
    }
    @Override
    protected void onBindViewHolder(@NonNull OrderHolder holder, int position, @NonNull CheckoutModel model) {

        String order_id = model.getOrder_id();
        String user_id = model.getUser_id();

        holder.oName.setText(model.getUser_name());
        holder.oTime.setText(model.getOrder_date());
        holder.oAddress.setText(model.getShips_to());
        holder.oTotal.setText(model.getTotal_items());
        holder.oCheckbox.setChecked(model.isInProcess());

        // go to order detail
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send user id and order id
                Intent intent = new Intent(context, OrderDetails.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("user_id", user_id);
                context.startActivity(intent);

            }
        });
//         checkbox isClicked
//        holder.oCheckbox.setOnClickListener(new View.OnClickListener() {
//            final ArrayList<String> orders_id_list = new ArrayList<>();
//            @Override
//            public void onClick(View view) {
//                boolean checked = ((CheckBox) view).isChecked();
//                if (checked){
//                    orders_id_list.add(model.getOrder_id());
//
//                        Toast.makeText(context, ""+orders_id_list+"\n", Toast.LENGTH_SHORT).show();
//                        Log.i("TAG", "onBindViewHolder: \n"+ orders_id_list);
//                }
//                else{
//                    orders_id_list.remove(order_id);
//                    for(String s1: orders_id_list){
//                        Log.i("TAG", "onBindViewHolder: \n"+orders_id_list);
////                        Toast.makeText(context, ""+s1+"\n", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });

    }
    class OrderHolder extends RecyclerView.ViewHolder {

        private TextView oName, oTime , oTotal, oAddress;
        CheckBox oCheckbox;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            // ui init
            oName    = itemView.findViewById(R.id.customer_id);
            oTime    = itemView.findViewById(R.id.order_time);
            oTotal   = itemView.findViewById(R.id.totalItems_ID);
            oAddress = itemView.findViewById(R.id.custAddressTv_id);
            oCheckbox = itemView.findViewById(R.id.checkBox_ordr_id);

            oCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    DocumentSnapshot snapshot = getSnapshots().getSnapshot(getAbsoluteAdapterPosition());
                    CheckoutModel checkout = getItem(getAbsoluteAdapterPosition());

                        orderListner.handleCheckChanged(isChecked, snapshot);


                }
            });

        }
    }

    public  interface OrderListner{
        public  void handleCheckChanged (boolean isChecked, DocumentSnapshot snapshot);
    }
}
