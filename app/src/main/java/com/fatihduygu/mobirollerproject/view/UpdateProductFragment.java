package com.fatihduygu.mobirollerproject.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.fatihduygu.mobirollerproject.R;
import com.fatihduygu.mobirollerproject.model.CategoryData;
import com.fatihduygu.mobirollerproject.model.Product;
import com.fatihduygu.mobirollerproject.viewmodel.UpdateProductFragmentViewModel;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateProductFragment extends Fragment {
    //Vars
    private Product product;
    private static final int STORAGE_REQUEST_CODE=101;
    private static final int GALLERY_REQUEST_CODE=102;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private Uri selectedImageUri;
    private Bitmap selectedImageBitmap;

    private UpdateProductFragmentViewModel updateProductFragmentViewModel;

    //Ui Components
    @BindView(R.id.update_product_fragment_image)
    ImageView image;

    @BindView(R.id.update_product_fragment_description_txt)
    EditText description;

    @BindView(R.id.update_product_fragment_caption_txt)
    EditText caption;

    @BindView(R.id.update_product_fragment_category_spinner)
    Spinner categorySpinner;

    @BindView(R.id.update_product_fragment_price_txt)
    EditText price;

    @BindView(R.id.update_product_fragment_update_btn)
    Button update;


    public UpdateProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_update_product, container, false);
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

        spinnerArrayAdapter=new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_dropdown_item, CategoryData.categoryNames);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(spinnerArrayAdapter);

        updateProductFragmentViewModel= ViewModelProviders.of(this).get(UpdateProductFragmentViewModel.class);
        if (getArguments()!=null){
            product=UpdateProductFragmentArgs.fromBundle(getArguments()).getProductInfo();

            description.setText(product.getDescription());
            caption.setText(product.getCaption());
            categorySpinner.setSelection(Integer.parseInt(product.getCategoryId())-1);
            price.setText(product.getPrice().split(" ")[0]);
            //Transformation transformation=new RoundedTransformationBuilder().oval(true).build();
            Picasso.get().load(product.getImageUrl()).fit().centerCrop().into(image);
        }

        image.setOnClickListener(this::selectPicture);

        update.setOnClickListener(v -> {
            String uuid=product.getId();

            String descriptionText=description.getText().toString().trim();
            String captionText=caption.getText().toString().trim();
            String categoryId=CategoryData.categoryCodes[categorySpinner.getSelectedItemPosition()];
            String priceText=price.getText().toString();


            if (selectedImageUri!=null){
                if (uuid!=null && selectedImageUri!=null && !descriptionText.isEmpty() && !captionText.isEmpty() && !categoryId.isEmpty() && !priceText.isEmpty()){
                    int prc;
                    try {
                        prc=Integer.parseInt(priceText.trim());
                    }catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Please enter valid value", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    updateProductFragmentViewModel.updateDataToRealtimeDatabase(uuid,selectedImageUri,descriptionText,captionText,categoryId,prc);
                    observeViewModel();
                }else {
                    Toast.makeText(getContext(), "Please fill all space ", Toast.LENGTH_SHORT).show();
                }
                return;
            }


            if (image!=null){
                if (uuid!=null && !descriptionText.isEmpty() && !captionText.isEmpty() && !categoryId.isEmpty() && !priceText.isEmpty()){
                    int prc;
                    try {
                        prc=Integer.parseInt(priceText.trim());
                    }catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Please enter valid value", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    updateProductFragmentViewModel.updateDataToRealtimeDatabaseIfNotSelectedImage(uuid,descriptionText,captionText,categoryId,prc);
                    observeViewModel();
                }else {
                    Toast.makeText(getContext(), "Please fill all space ", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void observeViewModel() {
        updateProductFragmentViewModel.isUpdate.observe(this, isUpdate-> {
            NavDirections directions=UpdateProductFragmentDirections.actionUpdateProductFragmentToProductFragment();
            Navigation.findNavController(getView()).navigate(directions);
            Toast.makeText(getContext(), "Updated product", Toast.LENGTH_SHORT).show();
        });
    }


    private void selectPicture(View view) {
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
        }else {
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,GALLERY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==STORAGE_REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALLERY_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_REQUEST_CODE && resultCode== Activity.RESULT_OK && data!=null){
            selectedImageUri=data.getData();
            try {
                selectedImageBitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImageUri);
                image.setBackground(null);
                image.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
