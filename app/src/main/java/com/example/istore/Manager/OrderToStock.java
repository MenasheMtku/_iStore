package com.example.istore.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.istore.Adapter.Admin.VendorAdapter;
import com.example.istore.Model.MyApplication;
import com.example.istore.Model.Vendor;
import com.example.istore.R;
import com.example.istore.databinding.UpdatePopupBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OrderToStock extends AppCompatActivity {
    private static final String TAG = "OrderToStock";

    Toolbar toolbar;
    Button addNew, confirmBtn, dissAllow;
    //    private RecyclerView.LayoutManager layoutManager;
    LinearLayout popLayout;
    EditText id, v_name, v_image, v_contact;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    FirebaseFirestore db;
    MyApplication myApplication = (MyApplication) this.getApplication();
    List<Vendor> vendorList;
    private RecyclerView vRecyclerView;
    private RecyclerView.Adapter vAdapter;
    private VendorAdapter vendorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_to_stock);
        vendorList = MyApplication.getVendorList();
        db = FirebaseFirestore.getInstance();
//        myApplication.fillVendorsList();
//        Log.i(TAG, "onCreate: \n" + vendorList.toString());


        toolbar = findViewById(R.id.vtoolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addNew = findViewById(R.id.addNewVenBtn);

        loadVendors();
        // Add/Edit layout
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(OrderToStock.this);
                view = getLayoutInflater().inflate(R.layout.update_popup, null);
                // popUp layout init
                popLayout = view.findViewById(R.id.v_layout_id);
//                id = view.findViewById(R.id.v_id);
                v_name = view.findViewById(R.id.v_name_id);
                v_image = view.findViewById(R.id.v_image_et);
                v_contact = view.findViewById(R.id.v_contact_id);
                confirmBtn = view.findViewById(R.id.vAdd_btn);
                dissAllow = view.findViewById(R.id.vCancel_btn);

                builder.setView(view);
                alertDialog = builder.create();
                alertDialog.show();
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!v_name.getText().toString().isEmpty()
                                && !v_image.getText().toString().isEmpty()) {

                            saveVendor();

                        } else {
                            Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                                    .show();
                        }

                    }
                });
                dissAllow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
    }

    private void loadVendors() {
        Query query = FirebaseFirestore.getInstance().collection("Vendors")
                .orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Vendor> options = new FirestoreRecyclerOptions
                .Builder<Vendor>()
                .setQuery(query, Vendor.class)
                .build();
        vendorAdapter = new VendorAdapter(options, this);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                vRecyclerView = findViewById(R.id.v_rec);
                vRecyclerView.setHasFixedSize(true);
                vRecyclerView.setLayoutManager(new LinearLayoutManager(OrderToStock.this));
                vRecyclerView.setAdapter(vendorAdapter);

                Toast.makeText(OrderToStock.this, "Total Vendors: = " + vendorAdapter.getItemCount(), Toast.LENGTH_LONG).show();

            }
        });
    }


    private void saveVendor() {
        /// TODO: 10/31/2021
        HashMap<String, Object> vendor = new HashMap<>();
        vendor.put("name", "" + v_name.getText().toString());
        vendor.put("imgUrl", "" + v_image.getText().toString());
        vendor.put("contact", "" + v_contact.getText().toString());

        db.collection("Vendors")
                .add(vendor)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            v_name.setText("");
                            v_contact.setText("");
                            v_image.setText("");
                            Toast.makeText(OrderToStock.this, "Vendor Added successfuly", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
//                            Snackbar.make(OrderToStock.this, "", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OrderToStock.this, "Something went wrong\n" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        vendorAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vendorAdapter.stopListening();
    }
}