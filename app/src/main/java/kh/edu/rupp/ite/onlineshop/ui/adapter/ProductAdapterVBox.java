package kh.edu.rupp.ite.onlineshop.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DecimalFormat;

import kh.edu.rupp.ite.onlineshop.api.model.Product;
import kh.edu.rupp.ite.onlineshop.databinding.ViewHolderProductVboxBinding;
import kh.edu.rupp.ite.onlineshop.databinding.ViewHolderProfileVboxBinding;

public class ProductAdapterVBox extends ListAdapter<Product, ProductAdapterVBox.ProductViewHolder> {

    public ProductAdapterVBox() {
        super(new DiffUtil.ItemCallback<Product>() {
            @Override
            public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return oldItem.getId() == newItem.getId();
            }
        });
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderProductVboxBinding binding = ViewHolderProductVboxBinding.inflate(layoutInflater, parent, false);

        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product item = getItem(position);
        try {
            holder.bind(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ViewHolderProductVboxBinding itemBinding;


        public ProductViewHolder(ViewHolderProductVboxBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Product product) throws IOException {
            // bind image to recycle view
            Picasso.get().load(product.getImgUrl()).into(itemBinding.imgProduct);

            // bind product name to recycle view
            itemBinding.nameProduct.setText(product.getName());

            // bind product price to recycle view
            DecimalFormat df = new DecimalFormat("$#.00");
            itemBinding.priceProduct.setText(df.format(product.getPrice()));

            // bind product rating to recycle view
            itemBinding.ratingProduct.setText(product.getRating());
        }

    }
}
