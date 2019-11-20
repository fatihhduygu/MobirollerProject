package com.fatihduygu.mobirollerproject.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatihduygu.mobirollerproject.R;
import com.fatihduygu.mobirollerproject.model.Product;
import com.fatihduygu.mobirollerproject.util.LoadImageWithGlide;
import com.fatihduygu.mobirollerproject.viewmodel.DetailFragmentViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {
    //Vars
    DetailFragmentViewModel detailFragmentViewModel;
    private Product product;

    //Ui Component
    @BindView(R.id.detail_fragment_product_image)
    ImageView productImage;

    @BindView(R.id.detail_fragment_caption_txt)
    TextView captionTxt;

    @BindView(R.id.detail_fragment_price_txt)
    TextView priceTxt;

    @BindView(R.id.detail_fragment_description_txt)
    TextView descriptionTxt;

    @BindView(R.id.detail_fragment_update_btn)
    Button updateButton;

    @BindView(R.id.detail_fragment_delete_btn)
    Button deleteButton;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        detailFragmentViewModel= ViewModelProviders.of(this).get(DetailFragmentViewModel.class);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatActivity activity= (AppCompatActivity) getActivity();
        if (activity!=null){
            activity.getSupportActionBar().hide();
        }

        if (getArguments()!=null){
            product=DetailFragmentArgs.fromBundle(getArguments()).getProductInfo();
            captionTxt.setText(product.getCaption());
            priceTxt.setText(product.getPrice());
            descriptionTxt.setText(product.getDescription());
            Picasso.get().load(product.getImageUrl()).fit().centerCrop().into(productImage);
            }

        deleteButton.setOnClickListener(v -> {
            if (product!=null){
                detailFragmentViewModel.deleteDataFromRemote(product.getId());
                observeViewModel();
            }
        });

        updateButton.setOnClickListener(view1 -> {
            NavDirections directions=DetailFragmentDirections.actionDetailFragmentToUpdateProductFragment(product);
            Navigation.findNavController(getView()).navigate(directions);

        });
    }

    private void observeViewModel() {
        detailFragmentViewModel.isDeleted.observe(this, deleted -> {
            if (deleted==true){
                NavDirections directions=DetailFragmentDirections.actionDetailFragmentToProductFragment();
                Navigation.findNavController(getView()).navigate(directions);
            }

        });
    }
}
