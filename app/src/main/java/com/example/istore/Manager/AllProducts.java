package com.example.istore.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.example.istore.Adapter.Admin.ViewStockAdapter;
import com.example.istore.Model.ProdModel;
import com.example.istore.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class AllProducts extends AppCompatActivity {

    Toolbar toolbar;
    EditText searchEditText;
    RecyclerView allRecyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference prodReff = db.collection("Products");
    private ViewStockAdapter allStockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        db = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.toolbar_all);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        searchEditText = (EditText) findViewById(R.id.search_all);
        allRecyclerView = findViewById(R.id.allRCV);
        allRecyclerView.setHasFixedSize(true);
        allRecyclerView.setLayoutManager(new LinearLayoutManager(AllProducts.this));

        loadAllProducts();

    }

    private void loadAllProducts() {
        Query query = prodReff
                .whereEqualTo("hasExpiry", false)
                .orderBy("vendor", Query.Direction.ASCENDING)
                .orderBy("quantity", Query.Direction.ASCENDING);
//                .orderBy("quantity", Query.Direction.DESCENDING)
//                .orderBy("expiry", Query.Direction.DESCENDING)
//                .orderBy("quantity", Query.Direction.DESCENDING);
//
//        Query query = prodReff.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ProdModel> options = new FirestoreRecyclerOptions
                .Builder<ProdModel>()
                .setQuery(query, ProdModel.class)
                .build();
        allStockAdapter = new ViewStockAdapter(options, this);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                allRecyclerView.setAdapter(allStockAdapter);


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        allStockAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        allStockAdapter.stopListening();
    }
}

//        BottomNavigationView bottomNavigationView = findViewById(R.id.buttomNavigationId);
//
//        bottomNavigationView.setSelectedItemId(R.id.viewExpired_buttom_nav);
//
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//
//                    case R.id.addItem_buttom_nav:
//
//                        startActivity(new Intent(getApplicationContext(), AddProduct.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.viewExpired_buttom_nav:
//                        // // current page
//                        return true;
//
//                    case R.id.viewStorage_buttom_nav:
//
//                        startActivity(new Intent(getApplicationContext(), ViewStorage.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });