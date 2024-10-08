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
import com.example.mobilneaplikacije.model.Users.Worker;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnBuyClickListener {
        void onBuyClick(Product product);

    }

    private List<Product> productList;
    private List<Worker> workerList;
    private OnBuyClickListener onBuyClickListener;

    public ProductAdapter(List<Product> productList, OnBuyClickListener onBuyClickListener) {
        this.productList = productList;
        this.onBuyClickListener = onBuyClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, workerList, onBuyClickListener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductName;
        private TextView textViewProductDescription;
        private TextView textViewProductPrice;
        private Button buttonBuy;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductDescription = itemView.findViewById(R.id.textViewProductDescription);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            buttonBuy = itemView.findViewById(R.id.buttonBuy);
        }

        public void bind(Product product, List<Worker> workerList, OnBuyClickListener onBuyClickListener) {
            textViewProductName.setText(product.getName());
            textViewProductDescription.setText(product.getDescription());
            textViewProductPrice.setText(String.valueOf(product.getPrice()));


            buttonBuy.setOnClickListener(v -> onBuyClickListener.onBuyClick(product));
        }
    }
}
