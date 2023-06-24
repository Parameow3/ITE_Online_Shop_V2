package kh.edu.rupp.ite.onlineshop.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import kh.edu.rupp.ite.onlineshop.api.model.Product;
import kh.edu.rupp.ite.onlineshop.api.service.ApiService;
import kh.edu.rupp.ite.onlineshop.databinding.FragmentProductBinding;
import kh.edu.rupp.ite.onlineshop.ui.adapter.ProductAdapter;
import kh.edu.rupp.ite.onlineshop.ui.adapter.ProductAdapterVBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductFragment extends Fragment {

    private FragmentProductBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // load list products from server (api)
        loadProductListFromServer();
    }

    private void loadProductListFromServer() {

        // create retrofit client
        Retrofit httpClient = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create service object
        ApiService apiService = httpClient.create(ApiService.class);

        // load product list from server
        Call<List<Product>> task = apiService.loadProductList();

        task.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Load Products list Successful!", Toast.LENGTH_LONG).show();
                    showProductList(response.body());

                } else {
                    Toast.makeText(getContext(), "Load Products list failed!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Load Products list failed!", Toast.LENGTH_LONG).show();
                Log.e("[ProductFragment]", "Load Products failed: " + t.getMessage());
            }
        });
    }

    private void showProductList(List<Product> productList) {

        // Create Layout Manager
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recycleViewProduct.setLayoutManager(layoutManager);

        // Create Adapter
        ProductAdapterVBox adapter = new ProductAdapterVBox();
        adapter.submitList(productList);
        binding.recycleViewProduct.setAdapter(adapter);
    }
}
