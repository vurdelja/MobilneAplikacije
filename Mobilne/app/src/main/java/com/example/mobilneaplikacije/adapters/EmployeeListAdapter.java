package com.example.mobilneaplikacije.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobilneaplikacije.model.Users.Employee;
import com.example.mobilneaplikacije.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EmployeeListAdapter extends ArrayAdapter<Employee> {
    private ArrayList<Employee> aEmployees;
    private FirebaseFirestore db;
    private OnMoreButtonClickListener moreButtonClickListener;

    public EmployeeListAdapter(Context context, ArrayList<Employee> employees) {
        super(context, R.layout.employee_car, employees);
        aEmployees = employees;
    }

    public interface OnMoreButtonClickListener {
        void onMoreButtonClick(Employee employee);
    }

    public void setOnMoreButtonClickListener(OnMoreButtonClickListener listener) {
        this.moreButtonClickListener = listener;
    }

    @Override
    public int getCount() {
        return aEmployees.size();
    }

    @Nullable
    @Override
    public Employee getItem(int position) {
        return aEmployees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Employee employee = getItem(position);
        db = FirebaseFirestore.getInstance();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.employee_car,
                    parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.employeeImage);
        TextView employeeName = convertView.findViewById(R.id.employeeName);
        TextView employeeSurname = convertView.findViewById(R.id.employeeSurname);
        TextView employeeAddress = convertView.findViewById(R.id.employeeAdress);
        TextView employeePhone = convertView.findViewById(R.id.employeePhone);
        TextView employeeEmail = convertView.findViewById(R.id.employeeEmail);
        Button activateButton = convertView.findViewById(R.id.activateButton);
        Button deactivateButton = convertView.findViewById(R.id.deactivateButton);
        Button moreButton = convertView.findViewById(R.id.moreButton);

        if (employee != null) {
            // Use Picasso to load image from URL asynchronously
            Picasso.get().load(employee.getProfilePicture()).placeholder(R.drawable.ic_launcher_foreground).into(imageView);

            employeeName.setText(employee.getFirstName());
            employeeSurname.setText(employee.getLastName());
            employeeAddress.setText(employee.getAddress());
            employeePhone.setText(employee.getPhoneNumber());
            employeeEmail.setText(employee.getEmail());

            activateButton.setOnClickListener(v -> {
                // Handle activate button click
                employee.setActive(true);
                Toast.makeText(getContext(), "Activate clicked for " + employee.getFirstName() +
                        " " + employee.getLastName(), Toast.LENGTH_SHORT).show();
                updateEmployeeInFirestore(employee);
            });

            deactivateButton.setOnClickListener(v -> {
                // Handle deactivate button click
                employee.setActive(false);
                Toast.makeText(getContext(), "Deactivate clicked for " + employee.getFirstName() +
                        " " + employee.getLastName(), Toast.LENGTH_SHORT).show();
                updateEmployeeInFirestore(employee);
            });

            moreButton.setOnClickListener(v -> {
                if (moreButtonClickListener != null) {
                    moreButtonClickListener.onMoreButtonClick(employee);
                }
            });
        }

        return convertView;
    }

    private void updateEmployeeInFirestore(Employee employee) {
        // Assuming phone number is unique for each employee
        String documentId = employee.getPhoneNumber();
        Log.d(TAG, "Updating employee with phone number: " + documentId); // Log documentId for debugging

        db.collection("employees").document(documentId)
                .update("active", employee.getActive())
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Employee updated successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating employee", e);
                });
    }



}
