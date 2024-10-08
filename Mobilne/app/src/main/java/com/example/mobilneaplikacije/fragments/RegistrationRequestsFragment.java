package com.example.mobilneaplikacije.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobilneaplikacije.R;
import com.example.mobilneaplikacije.adapters.RegistrationRequestAdapter;
import com.example.mobilneaplikacije.model.RegistrationRequest;
import com.example.mobilneaplikacije.model.enums.RequestStatus;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationRequestsFragment extends Fragment {

    private static final String TAG = "RegistrationRequests";
    private RecyclerView recyclerView;
    private RegistrationRequestAdapter adapter;
    private List<RegistrationRequest> registrationRequests;
    private FirebaseFirestore db;
    private Spinner spinnerCategory;
    private Spinner spinnerEventType;
    private Spinner spinnerDate;
    private String selectedCategory;
    private String selectedEventType;
    private String selectedDate;

    public RegistrationRequestsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration_requests, container, false);

        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerEventType = view.findViewById(R.id.spinnerEventType);
        spinnerDate = view.findViewById(R.id.spinnerDate);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        registrationRequests = new ArrayList<>();
        adapter = new RegistrationRequestAdapter(registrationRequests, getContext());
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        fetchRegistrationRequests();

        setupSpinners();

        return view;
    }

    private void fetchRegistrationRequests() {
        db.collection("registrationRequests")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registrationRequests.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            RegistrationRequest request = document.toObject(RegistrationRequest.class);
                            // Ensure status is not null
                            if (request.getStatus() == null) {
                                request.setStatus(RequestStatus.PENDING);
                            }
                            registrationRequests.add(request);
                        }
                        adapter.notifyDataSetChanged();
                        setupSpinners(); // Call this method after fetching the requests
                        Log.d(TAG, "Successfully fetched registration requests");
                    } else {
                        Log.e(TAG, "Error fetching registration requests", task.getException());
                    }
                });
    }

    private void setupSpinners() {
        // Setup category spinner
        List<String> categories = registrationRequests.stream()
                .map(RegistrationRequest::getCategory)
                .distinct()
                .collect(Collectors.toList());
        categories.add(0, "All Categories"); // Add a default option for all categories
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Setup event type spinner
        List<String> eventTypes = registrationRequests.stream()
                .map(RegistrationRequest::getEventType)
                .distinct()
                .collect(Collectors.toList());
        eventTypes.add(0, "All Event Types"); // Add a default option for all event types
        ArrayAdapter<String> eventTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, eventTypes);
        eventTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEventType.setAdapter(eventTypeAdapter);

        // Setup date spinner
        List<String> dates = registrationRequests.stream()
                .map(request -> request.getRequestDate()) // assuming requestDate is a Date object
                .distinct()
                .collect(Collectors.toList());
        dates.add(0, "All Dates"); // Add a default option for all dates
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(dateAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories.get(position);
                filterRequests();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = null;
                filterRequests();
            }
        });

        spinnerEventType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEventType = eventTypes.get(position);
                filterRequests();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedEventType = null;
                filterRequests();
            }
        });

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDate = dates.get(position);
                filterRequests();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDate = null;
                filterRequests();
            }
        });
    }

    private void filterRequests() {
        List<RegistrationRequest> filteredRequests = new ArrayList<>(registrationRequests);

        if (selectedCategory != null && !selectedCategory.equals("All Categories")) {
            filteredRequests = filteredRequests.stream()
                    .filter(request -> selectedCategory.equals(request.getCategory()))
                    .collect(Collectors.toList());
        }

        if (selectedEventType != null && !selectedEventType.equals("All Event Types")) {
            filteredRequests = filteredRequests.stream()
                    .filter(request -> selectedEventType.equals(request.getEventType()))
                    .collect(Collectors.toList());
        }

        if (selectedDate != null && !selectedDate.equals("All Dates")) {
            filteredRequests = filteredRequests.stream()
                    .filter(request -> selectedDate.equals(request.getRequestDate().toString()))
                    .collect(Collectors.toList());
        }

        adapter.updateList(filteredRequests);
    }
}