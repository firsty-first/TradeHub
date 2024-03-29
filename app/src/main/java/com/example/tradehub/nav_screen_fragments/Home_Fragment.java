package com.example.tradehub.nav_screen_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tradehub.Adapter.AdapterProductListing;
import com.example.tradehub.Adapter.Adapteradvertisement;
import com.example.tradehub.R;
import com.example.tradehub.pojo.adModel;
import com.example.tradehub.pojo.Product;
import com.example.tradehub.sellActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    ArrayList<adModel> adArrayList;
    ArrayList<Product> productArrayList;
    private RecyclerView recyclerViewadvertisement;
    private Adapteradvertisement adapteradvertisement;
    private  RecyclerView recyclerViewProduct;
    private AdapterProductListing adapterProductListing;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Home_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_Fragment newInstance(String param1, String param2) {
        Home_Fragment fragment = new Home_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        adArrayList = new ArrayList<>();
        productArrayList = new ArrayList<>();
        //viewbinding
        view.findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {;

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), sellActivity.class);
                    startActivity(intent);
                }
            });
        initAdvertisementRecyclerView(view);
        initProductRecyclerview(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initAdvertisementRecyclerView(View view) {
        recyclerViewadvertisement = view.findViewById(R.id.rvAdvertisement);
        adModel adModel=new adModel("shoe");
        recyclerViewadvertisement.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adArrayList.add(adModel);
        adArrayList.add(new adModel("Boooks"));
        adArrayList.add(new adModel("Pencil"));
        adapteradvertisement = new Adapteradvertisement(getContext(), adArrayList); // Pass your data list to the adapter
        recyclerViewadvertisement.setAdapter(adapteradvertisement);
    }
    private void initProductRecyclerview(View view) {
        recyclerViewProduct = view.findViewById(R.id.rvProductList);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        // Initialize an empty ArrayList to hold the products
        productArrayList = new ArrayList<>();

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get a reference to the "products" collection
        CollectionReference productsRef = db.collection("products");

        // Query the products collection
        if(productsRef!=null)
        productsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Loop through the documents in the query result
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    // Convert each document to a Product object
                    Product product = document.toObject(Product.class);
                    // Add the product to the ArrayList
                    productArrayList.add(product);
                }

                adapterProductListing = new AdapterProductListing(getContext(), productArrayList);
                // Set the adapter to the RecyclerView
                recyclerViewProduct.setAdapter(adapterProductListing);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle errors
                Log.e("Firestore", "Error getting documents: " + e);
            }
        });
    }

}
