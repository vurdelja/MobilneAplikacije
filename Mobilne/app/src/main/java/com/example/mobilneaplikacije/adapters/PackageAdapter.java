package com.example.mobilneaplikacije.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilneaplikacije.R;
import com.example.mobilneaplikacije.model.Availability;
import com.example.mobilneaplikacije.model.Package;
import com.example.mobilneaplikacije.model.Product;
import com.example.mobilneaplikacije.model.Service;
import com.example.mobilneaplikacije.model.Users.Worker;

import java.util.Date;
import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {

    private List<Package> packageList;
    private List<Worker> workerList;
    private OnReservePackageClickListener onReservePackageClickListener;

    public interface OnReservePackageClickListener {
        void onReservePackageClick(Package packageItem);
    }

    public PackageAdapter(List<Package> packageList, List<Worker> workerList, OnReservePackageClickListener onReservePackageClickListener) {
        this.packageList = packageList;
        this.workerList = workerList;
        this.onReservePackageClickListener = onReservePackageClickListener;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        Package packageItem = packageList.get(position);
        holder.bind(packageItem, workerList, onReservePackageClickListener);
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public static class PackageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewPackageName;
        private TextView textViewPackageDescription;
        private RecyclerView recyclerViewServicesFromPackage;
        private RecyclerView recyclerViewProductsFromPackage;
        private Button buttonReservePackage;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPackageName = itemView.findViewById(R.id.textViewPackageName);
            textViewPackageDescription = itemView.findViewById(R.id.textViewPackageDescription);
            recyclerViewServicesFromPackage = itemView.findViewById(R.id.recyclerViewServicesFromPackage);
            recyclerViewProductsFromPackage = itemView.findViewById(R.id.recyclerViewProductsFromPackage);
            buttonReservePackage = itemView.findViewById(R.id.buttonReservePackage);
        }

        public void bind(Package packageItem, List<Worker> workerList, OnReservePackageClickListener onReservePackageClickListener) {
            textViewPackageName.setText(packageItem.getName());
            textViewPackageDescription.setText(packageItem.getDescription());

            recyclerViewServicesFromPackage.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            ServiceFromPackageAdapter serviceAdapter = new ServiceFromPackageAdapter(packageItem.getServices(), workerList);
            recyclerViewServicesFromPackage.setAdapter(serviceAdapter);

            recyclerViewProductsFromPackage.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            ProductFromPackageAdapter productFromPackageAdapterAdapter = new ProductFromPackageAdapter(packageItem.getProducts()
            );
            recyclerViewProductsFromPackage.setAdapter(productFromPackageAdapterAdapter);

            buttonReservePackage.setOnClickListener(v -> onReservePackageClickListener.onReservePackageClick(packageItem));
        }
    }
}
