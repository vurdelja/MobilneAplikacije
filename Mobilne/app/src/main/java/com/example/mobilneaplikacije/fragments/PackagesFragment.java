package com.example.mobilneaplikacije.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilneaplikacije.R;
import com.example.mobilneaplikacije.adapters.PackageAdapter;
import com.example.mobilneaplikacije.model.Budget;
import com.example.mobilneaplikacije.model.Package;
import com.example.mobilneaplikacije.model.Product;
import com.example.mobilneaplikacije.model.Service;
import com.example.mobilneaplikacije.model.Users.Worker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PackagesFragment extends Fragment {

    private Spinner eventSpinner;
    private RecyclerView recyclerViewPackages;
    private PackageAdapter packageAdapter;
    private List<Package> packageList;
    private List<String> eventNames;
    private List<Worker> workerList;
    private FirebaseFirestore db;
    private String selectedEventName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_packages, container, false);

        eventSpinner = view.findViewById(R.id.spinnerEvents);
        recyclerViewPackages = view.findViewById(R.id.recyclerViewPackages);
        recyclerViewPackages.setLayoutManager(new LinearLayoutManager(getContext()));

        packageList = new ArrayList<>();
        eventNames = new ArrayList<>();
        workerList = new ArrayList<>();

        packageAdapter = new PackageAdapter(packageList, workerList, packageItem -> reservePackage(packageItem));

        recyclerViewPackages.setAdapter(packageAdapter);

        db = FirebaseFirestore.getInstance();

        loadEvents();
        loadWorkers();
        loadPackages();

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEventName = eventNames.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedEventName = null;
            }
        });

        return view;
    }

    private void loadEvents() {
        db.collection("events")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String eventName = document.getString("name");
                            if (eventName != null) {
                                eventNames.add(eventName);
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, eventNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        eventSpinner.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "Failed to load events", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadPackages() {
        db.collection("packages")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        packageList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Package packageItem = document.toObject(Package.class);
                            packageList.add(packageItem);
                        }
                        packageAdapter.notifyDataSetChanged();
                        if (packageList.isEmpty()) {
                            Toast.makeText(getContext(), "No packages found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to load packages", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadWorkers() {
        db.collection("workers")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        workerList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Worker worker = document.toObject(Worker.class);
                            worker.setWorkerId(document.getId()); // Set the document ID as workerId
                            workerList.add(worker);
                        }
                        packageAdapter.notifyDataSetChanged();
                        if (workerList.isEmpty()) {
                            Toast.makeText(getContext(), "No workers found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to load workers", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void reservePackage(Package packageItem) {
        if (selectedEventName == null) {
            Toast.makeText(getContext(), "Please select an event", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reserve each product in the package
        for (Product product : packageItem.getProducts()) {
            addProductToBudget(product, selectedEventName);
        }

        // Reserve each service in the package
        for (Service service : packageItem.getServices()) {
            addServiceToBudget(service, selectedEventName);
        }

        Toast.makeText(getContext(), "Package reserved for event: " + selectedEventName, Toast.LENGTH_SHORT).show();
    }

    private void addProductToBudget(Product product, String eventName) {
        db.collection("budgets")
                .whereEqualTo("eventName", eventName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            Budget budget = document.toObject(Budget.class);
                            if (budget != null) {
                                budget.getProducts().add(product);
                                budget.setTotalCost(budget.getTotalCost() + product.getPrice());

                                db.collection("budgets").document(document.getId()).set(budget)
                                        .addOnSuccessListener(aVoid -> {
                                            Log.d(TAG, "Product added to budget: " + product.getName());
                                            Toast.makeText(getContext(), "Product added to budget", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e(TAG, "Failed to add product to budget", e);
                                            Toast.makeText(getContext(), "Failed to add product to budget", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Log.w(TAG, "Failed to parse budget");
                                Toast.makeText(getContext(), "Failed to parse budget", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(TAG, "Budget not found");
                            Toast.makeText(getContext(), "Budget not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addServiceToBudget(Service service, String eventName) {
        db.collection("budgets")
                .whereEqualTo("eventName", eventName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            Budget budget = document.toObject(Budget.class);
                            if (budget != null) {
                                budget.getServices().add(service);
                                budget.setTotalCost(budget.getTotalCost() + service.getPrice());

                                db.collection("budgets").document(document.getId()).set(budget)
                                        .addOnSuccessListener(aVoid -> {
                                            Log.d(TAG, "Service reserved: " + service.getName());
                                            Toast.makeText(getContext(), "Service reserved", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e(TAG, "Failed to reserve service", e);
                                            Toast.makeText(getContext(), "Failed to reserve service", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Log.w(TAG, "Failed to parse budget");
                                Toast.makeText(getContext(), "Failed to parse budget", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(TAG, "Budget not found");
                            Toast.makeText(getContext(), "Budget not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}