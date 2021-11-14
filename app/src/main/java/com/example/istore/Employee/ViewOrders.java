package com.example.istore.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.istore.Adapter.Admin.ViewOrdersAdapter;
import com.example.istore.Adapter.Emp.ViewOrdersEmpAdapter;
import com.example.istore.Manager.CustomerOrders;
import com.example.istore.Model.CheckoutModel;
import com.example.istore.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewOrders extends AppCompatActivity implements ViewOrdersEmpAdapter.OrderListner1 {

    Toolbar toolbar;
    RecyclerView oRecyclerView;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ViewOrdersEmpAdapter viewOrdersEmpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        toolbar = findViewById(R.id.emp_viewOrdersTb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        oRecyclerView = findViewById(R.id.emp_view_orders_Rv);

        loadOrders();

    }

    private void loadOrders() {

        Query query = FirebaseFirestore.getInstance().collection("Checkout")
                .whereEqualTo("inProcess", true)
                .orderBy("completed", Query.Direction.ASCENDING)
                .orderBy("order_date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<CheckoutModel> options = new FirestoreRecyclerOptions
                .Builder<CheckoutModel>()
                .setQuery(query, CheckoutModel.class)
                .build();
        viewOrdersEmpAdapter = new ViewOrdersEmpAdapter(options, this, this);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                oRecyclerView = findViewById(R.id.emp_view_orders_Rv);
                oRecyclerView.setHasFixedSize(true);
                oRecyclerView.setLayoutManager(new LinearLayoutManager(ViewOrders.this));
                oRecyclerView.setAdapter(viewOrdersEmpAdapter);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewOrdersEmpAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewOrdersEmpAdapter.stopListening();
    }

    @Override
    public void handleCompleteChanged(boolean isComplet, DocumentSnapshot snapshot) {

        Log.i("TAG", "handleCompleteChanged: " + isComplet);
        // update the checkout wethere order status ready for storekepper
        snapshot.getReference().update("completed", isComplet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        Log.i("TAG", "onSuccess: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TAG", "onFailure: " + e.getMessage());
                    }
                });
    }
}