package com.fatihduygu.mobirollerproject.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailFragmentViewModel extends AndroidViewModel {
    public MutableLiveData<Boolean> isDeleted=new MutableLiveData<>();

    public DetailFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void deleteDataFromRemote(String uuId){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("products").child(uuId);
        databaseReference.removeValue();
        isDeleted.setValue(true);
    }
}
