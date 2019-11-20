package com.fatihduygu.mobirollerproject.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fatihduygu.mobirollerproject.Adapter.ProductListAdapter;
import com.fatihduygu.mobirollerproject.R;
import com.fatihduygu.mobirollerproject.model.Product;
import com.fatihduygu.mobirollerproject.viewmodel.ProductFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductFragment extends Fragment implements ProductListAdapter.OnProductListener {
    //Vars
    public static ProductFragmentViewModel productFragmentViewModel;
    private ProductListAdapter productListAdapter=new ProductListAdapter(new ArrayList<>(),this::onProductClick);
    private ArrayList<Product> productsList;

    //Ui Components
    @BindView(R.id.product_fragment_floating_action_button)
    FloatingActionButton fab;

    @BindView(R.id.product_fragment_list_error)
    TextView errorTxt;

    @BindView(R.id.product_fragment_loading_view)
    ProgressBar loading;

    @BindView(R.id.product_fragment_product_list_recycler_view)
    RecyclerView productList;

    @BindView(R.id.product_fragment_refresh_layout)
    SwipeRefreshLayout refreshLayout;



    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatActivity activity= (AppCompatActivity) getActivity();
        if (activity!=null){
            activity.getSupportActionBar().show();
        }

        productFragmentViewModel= ViewModelProviders.of(this).get(ProductFragmentViewModel.class);
        productFragmentViewModel.refresh();

        //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        productList.setLayoutManager(gridLayoutManager);
        productList.setAdapter(productListAdapter);

        fab.setOnClickListener(view1 -> { onGotoAdd();});

        refreshLayout.setOnRefreshListener(() -> {
            productList.setVisibility(View.GONE);
            errorTxt.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            productFragmentViewModel.refresh();
            refreshLayout.setRefreshing(false);
        });
        observeViewModel();

    }

    private void observeViewModel() {
        productFragmentViewModel.productLiveData.observe(this, products -> {
            if (products!=null && products instanceof ArrayList){
                productList.setVisibility(View.VISIBLE);
                productsList=products;
                productListAdapter.updateProductList(products);
            }
        });

        productFragmentViewModel.productLoadError.observe(this, error -> {
            if (error!=null && error instanceof Boolean){
                errorTxt.setVisibility(error ? View.VISIBLE : View.INVISIBLE);
            }
        });

        productFragmentViewModel.loading.observe(this, isLoading -> {
            if (isLoading!=null && isLoading instanceof Boolean){
                loading.setVisibility(isLoading ? View.VISIBLE:View.INVISIBLE);
                if (isLoading){
                    productList.setVisibility(View.INVISIBLE);
                    errorTxt.setVisibility(View.INVISIBLE);
                }
            }

        });

    }
    private void onGotoAdd() {
        NavDirections directions=ProductFragmentDirections.actionProductFragmentToAddProductFragment();
        Navigation.findNavController(fab).navigate(directions);
    }

    @Override
    public void onProductClick(int position) {
        NavDirections directions=ProductFragmentDirections.actionProductFragmentToDetailFragment(productsList.get(position));
        Navigation.findNavController(getView()).navigate(directions);
    }
}
