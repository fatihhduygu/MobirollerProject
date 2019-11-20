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
import com.fatihduygu.mobirollerproject.viewmodel.AddProductFragmentViewModel;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddProductFragment extends Fragment {
    private static final int STORAGE_REQUEST_CODE=101;
    private static final int GALLERY_REQUEST_CODE=102;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private Uri selectedImageUri;
    private Bitmap selectedImageBitmap;

    private AddProductFragmentViewModel addProductFragmentViewModel;

    //Views
    @BindView(R.id.add_product_fragment_share_btn)
    Button share;

    @BindView(R.id.add_product_fragment_image)
    ImageView addImage;

    @BindView(R.id.add_product_fragment_caption_txt)
    EditText caption;

    @BindView(R.id.add_product_fragment_description_txt)
    EditText description;

    @BindView(R.id.add_product_fragment_category_spinner)
    Spinner categorySpinner;

    @BindView(R.id.add_product_fragment_price_txt)
    EditText price;


    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_product, container, false);
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

        addProductFragmentViewModel= ViewModelProviders.of(this).get(AddProductFragmentViewModel.class);

        addImage.setOnClickListener(this::selectPicture);

        share.setOnClickListener(v1 -> {
            String descriptionText=description.getText().toString().trim();
            String captionText=caption.getText().toString().trim();
            String categoryId=CategoryData.categoryCodes[categorySpinner.getSelectedItemPosition()];
            String priceText=price.getText().toString();
                if (selectedImageUri!=null && !descriptionText.isEmpty() && !captionText.isEmpty() && !categoryId.isEmpty() && !priceText.isEmpty()){
                    int prc;
                    try {
                        prc=Integer.parseInt(priceText);
                    }catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Please enter valid value", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    addProductFragmentViewModel.addDataToRealtimeDatabase(selectedImageUri,descriptionText,captionText,categoryId,prc);
                    observeViewModel();
                }else {
                    Toast.makeText(getContext(), "Please fill all space ", Toast.LENGTH_SHORT).show();
                }

        });
    }

    private void observeViewModel() {
        addProductFragmentViewModel.missionCompleted.observe(this, completed -> {
            if (completed==true){
                NavDirections directions=AddProductFragmentDirections.actionAddProductFragmentToProductFragment();
                Navigation.findNavController(getView()).navigate(directions);
                Toast.makeText(getContext(), "Added new product", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "Error!!!!", Toast.LENGTH_SHORT).show();
            }
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
                addImage.setBackground(null);
                addImage.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
