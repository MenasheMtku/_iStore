package com.example.istore.Adapter.Emp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istore.Manager.OrderDetails;
import com.example.istore.Model.CheckoutModel;
import com.example.istore.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class ViewOrdersEmpAdapter extends FirestoreRecyclerAdapter<CheckoutModel, ViewOrdersEmpAdapter.OrderHolder> {

    final private Context context;
    OrderListner1 orderListner1;

    public ViewOrdersEmpAdapter(@NonNull FirestoreRecyclerOptions<CheckoutModel> options, Context context, OrderListner1 orderListner1) {

        super(options);
        this.context = context;
        this.orderListner1 = orderListner1;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_order_row, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderHolder holder, int position, @NonNull CheckoutModel model) {

        String order_id = model.getOrder_id();
        String user_id = model.getUser_id();

        holder.oName.setText(model.getUser_name());
        holder.oTime.setText(model.getOrder_date());
        holder.oAddress.setText(model.getShips_to());
        holder.oTotal.setText(model.getTotal_items());
        holder.eCheckbox.setChecked(model.isCompleted());

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
    }

    class OrderHolder extends RecyclerView.ViewHolder {

        private TextView oName, oTime, oTotal, oAddress;
        CheckBox eCheckbox;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            // ui init
            oName = itemView.findViewById(R.id.emp_customer_id);
            oTime = itemView.findViewById(R.id.emp_order_time);
            oTotal = itemView.findViewById(R.id.emp_totalItems_ID);
            oAddress = itemView.findViewById(R.id.emp_custAddressTv_id);
            eCheckbox = itemView.findViewById(R.id.emp_checkBox_ordr_id);

            eCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isComplet) {

                    DocumentSnapshot snapshot = getSnapshots().getSnapshot(getAbsoluteAdapterPosition());
//                    CheckoutModel checkout = getItem(getAbsoluteAdapterPosition());
//                    if (checkout.isCompleted() != isCompleted) {
//
//                    }
                    orderListner1.handleCompleteChanged(isComplet, snapshot);
                }
            });

        }
    }

    public interface OrderListner1 {
        public void handleCompleteChanged(boolean isCompleted, DocumentSnapshot snapshot);
    }
}
