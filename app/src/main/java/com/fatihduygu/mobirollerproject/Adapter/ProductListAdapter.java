package com.fatihduygu.mobirollerproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fatihduygu.mobirollerproject.R;
import com.fatihduygu.mobirollerproject.model.Product;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>{
    private ArrayList<Product> productArrayList;
    private OnProductListener mOnProductListener;

    public ProductListAdapter(ArrayList<Product> products,OnProductListener onProductListener) {
        this.productArrayList=products;
        this.mOnProductListener=onProductListener;
    }

    public void updateProductList(ArrayList<Product> products){
        productArrayList.clear();
        productArrayList.addAll(products);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view,mOnProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ImageView productImage=holder.view.findViewById(R.id.item_product_image_view);
        TextView productCaption=holder.view.findViewById(R.id.item_product_caption);
        TextView productPrice=holder.view.findViewById(R.id.item_product_price);

        productCaption.setText(productArrayList.get(position).getCaption());
        productPrice.setText(productArrayList.get(position).getPrice());
        //LoadImageWithGlide.LoadImageWithGlide(productImage,productArrayList.get(position).getImageUrl(),LoadImageWithGlide.getProgressDrawable(productImage.getContext()));
        Picasso.get().load(productArrayList.get(position).getImageUrl()).fit().centerCrop().into(productImage);

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View view;
        OnProductListener onProductListener;

        public ProductViewHolder(@NonNull View itemView,OnProductListener onProductListener) {
            super(itemView);
            this.view=itemView;

            this.onProductListener=onProductListener;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            onProductListener.onProductClick(getAdapterPosition());
        }
    }

    public interface OnProductListener{
        void onProductClick(int position);
    }
}
