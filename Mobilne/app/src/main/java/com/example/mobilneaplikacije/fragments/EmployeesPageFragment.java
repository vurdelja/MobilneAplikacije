package com.example.mobilneaplikacije.fragments;
import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mobilneaplikacije.R;
import com.example.mobilneaplikacije.adapters.EmployeeListAdapter;
import com.example.mobilneaplikacije.model.Users.Employee;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeesPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeesPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseFirestore db;

    public EmployeesPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeesPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeesPageFragment newInstance(String param1, String param2) {
        EmployeesPageFragment fragment = new EmployeesPageFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment_employees_page layout
        View view = inflater.inflate(R.layout.fragment_employees_page, container, false);
        db = FirebaseFirestore.getInstance();

        // Dodavanje OnClickListener za FloatingActionButton
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zamjena sadržaja trenutnog fragmenta s EmployeeRegisterFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EmployeeRegisterFragment employeeRegisterFragment = EmployeeRegisterFragment.newInstance(null, null);
                fragmentTransaction.replace(R.id.employeeConstraint, employeeRegisterFragment);
                fragmentTransaction.addToBackStack(null); // Dodaj u backstack
                fragmentTransaction.commit();
            }
        });

        // Povezivanje adaptera sa ListView
        ListView listView = view.findViewById(android.R.id.list);


        ArrayList<Employee> employees = new ArrayList<>();
        EmployeeListAdapter adapter = new EmployeeListAdapter(getContext(), employees);
        db.collection("employees")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Employee employee = document.toObject(Employee.class);
                            employees.add(employee);
                        }
                        // Now, you can pass this list of employees to your adapter

                        listView.setAdapter(adapter);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });


        // Pretpostavljamo da imate ovu funkciju
       // EmployeeListAdapter adapter = new EmployeeListAdapter(getContext(), employees);
        //listView.setAdapter(adapter);

        // Set click listener for the moreButton within the adapter
        adapter.setOnMoreButtonClickListener(new EmployeeListAdapter.OnMoreButtonClickListener() {
            @Override
            public void onMoreButtonClick(Employee employee) {
                // Zamjena sadržaja trenutnog fragmenta s EmployeeUpdateWorkTimeFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EmployeeUpdateWorkTimeFragment employeeUpdateWorkTimeFragment = EmployeeUpdateWorkTimeFragment.newInstance(null, null);
                fragmentTransaction.replace(R.id.employeeConstraint, employeeUpdateWorkTimeFragment);
                fragmentTransaction.addToBackStack(null); // Dodaj u backstack
                fragmentTransaction.commit();
            }
        });

        return view;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


}