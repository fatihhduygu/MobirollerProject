package com.fatihduygu.mobirollerproject.viewmodel;

import android.app.Application;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddProductFragmentViewModel extends AndroidViewModel {
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference myRef=database.getReference();
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    public MutableLiveData<Boolean> missionCompleted=new MutableLiveData<>();


    public AddProductFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void addDataToRealtimeDatabase(Uri imageUri,String description,String caption,String categoryId,int price){
        UUID randomImageId=UUID.randomUUID();
        String imageId="images/"+randomImageId+".jpg";
        StorageReference newReference=storageReference.child(imageId);

        if (imageUri!=null){
            newReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        StorageReference imageRef=FirebaseStorage.getInstance().getReference(imageId);
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String downloadUri=uri.toString();
                            DatabaseReference newRef=myRef.child("products").push();
                            Map info=new HashMap();
                            info.put("description",description);
                            info.put("caption",caption);
                            info.put("category_id",categoryId);
                            info.put("price",price);
                            info.put("imageUrl",downloadUri);
                            info.put("date", ServerValue.TIMESTAMP);
                            newRef.updateChildren(info);
                            missionCompleted.setValue(true);
                        });
                    })
                    .addOnFailureListener(e -> {
                        missionCompleted.setValue(false);
                    });
        }

    }
}
