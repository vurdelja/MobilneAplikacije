package com.example.mobilneaplikacije.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilneaplikacije.R;
import com.example.mobilneaplikacije.model.Product;

import java.util.List;

public class ProductFromPackageAdapter extends RecyclerView.Adapter<ProductFromPackageAdapter.ProductFromPackageViewHolder> {

    private List<Product> productList;


    public ProductFromPackageAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductFromPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_package, parent, false);
        return new ProductFromPackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductFromPackageViewHolder holder, int position) {
        Product product = productList.get(position);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductFromPackageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductName;
        private TextView textViewProductDescription;
        private TextView textViewProductPrice;


        public ProductFromPackageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductDescription = itemView.findViewById(R.id.textViewProductDescription);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
        }

        public void bind(Product product) {
            textViewProductName.setText(product.getName());
            textViewProductDescription.setText(product.getDescription());
            textViewProductPrice.setText(String.valueOf(product.getPrice()));

        }
    }
}
