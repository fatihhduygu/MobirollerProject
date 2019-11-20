package com.fatihduygu.mobirollerproject.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.fatihduygu.mobirollerproject.model.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class ProductFragmentViewModel extends AndroidViewModel {
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference myRef=database.getReference();
    public MutableLiveData<ArrayList<Product>> productLiveData=new MutableLiveData<>();
    public MutableLiveData<Boolean> productLoadError=new MutableLiveData<>();
    public MutableLiveData<Boolean> loading=new MutableLiveData<>();


    public ProductFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh(){
        productLoadError.setValue(false);
        loading.setValue(true);
        fetchFromRemote();

    }

    private void fetchFromRemote() {
        ArrayList<Product> products=new ArrayList<>();
        DatabaseReference databaseReference=myRef.child("products");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    String id="",categoryId="",caption="",description="",price="",imageUrl="";
                    if (dataSnapshot.getKey()!=null){
                        id=dataSnapshot.getKey();
                    }
                    if (dataSnapshot.child("imageUrl").getValue()!=null){
                        imageUrl=dataSnapshot.child("imageUrl").getValue().toString();
                    }

                    if (dataSnapshot.child("caption").getValue()!=null){
                        caption=dataSnapshot.child("caption").getValue().toString();
                    }
                    if (dataSnapshot.child("category_id").getValue()!=null){
                        categoryId=dataSnapshot.child("category_id").getValue().toString();
                    }
                    if (dataSnapshot.child("description").getValue()!=null){
                        description=dataSnapshot.child("description").getValue().toString();
                    }
                    if (dataSnapshot.child("price").getValue()!=null){
                        price=dataSnapshot.child("price").getValue().toString()+" TL";
                    }
                    products.add(new Product(id,categoryId,caption,description,price,imageUrl));

                    productLiveData.setValue(products);
                    productLoadError.setValue(false);
                    loading.setValue(false);
                    return;
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }


    public void sortByCategory() {
        ArrayList<Product> products=new ArrayList<>();
        DatabaseReference databaseReference=myRef.child("products");
        databaseReference.orderByChild("category_id").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    String id="",categoryId="",caption="",description="",price="",imageUrl="";
                    if (dataSnapshot.getKey()!=null){
                        id=dataSnapshot.getKey();
                    }
                    if (dataSnapshot.child("imageUrl").getValue()!=null){
                        imageUrl=dataSnapshot.child("imageUrl").getValue().toString();
                    }

                    if (dataSnapshot.child("caption").getValue()!=null){
                        caption=dataSnapshot.child("caption").getValue().toString();
                    }
                    if (dataSnapshot.child("category_id").getValue()!=null){
                        categoryId=dataSnapshot.child("category_id").getValue().toString();
                    }
                    if (dataSnapshot.child("description").getValue()!=null){
                        description=dataSnapshot.child("description").getValue().toString();
                    }
                    if (dataSnapshot.child("price").getValue()!=null){
                        price=dataSnapshot.child("price").getValue().toString()+" TL";
                    }
                    products.add(new Product(id,categoryId,caption,description,price,imageUrl));

                    productLiveData.setValue(products);
                    productLoadError.setValue(false);
                    loading.setValue(false);
                    return;
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void sortByName() {
        ArrayList<Product> products=new ArrayList<>();
        DatabaseReference databaseReference=myRef.child("products");
        databaseReference.orderByChild("caption").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    String id="",categoryId="",caption="",description="",price="",imageUrl="";
                    if (dataSnapshot.getKey()!=null){
                        id=dataSnapshot.getKey();
                    }
                    if (dataSnapshot.child("imageUrl").getValue()!=null){
                        imageUrl=dataSnapshot.child("imageUrl").getValue().toString();
                    }

                    if (dataSnapshot.child("caption").getValue()!=null){
                        caption=dataSnapshot.child("caption").getValue().toString();
                    }
                    if (dataSnapshot.child("category_id").getValue()!=null){
                        categoryId=dataSnapshot.child("category_id").getValue().toString();
                    }
                    if (dataSnapshot.child("description").getValue()!=null){
                        description=dataSnapshot.child("description").getValue().toString();
                    }
                    if (dataSnapshot.child("price").getValue()!=null){
                        price=dataSnapshot.child("price").getValue().toString()+" TL";
                    }
                    products.add(new Product(id,categoryId,caption,description,price,imageUrl));

                    productLiveData.setValue(products);
                    productLoadError.setValue(false);
                    loading.setValue(false);
                    return;
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void sortByPrice() {
        ArrayList<Product> products=new ArrayList<>();
        DatabaseReference databaseReference=myRef.child("products");
        databaseReference.orderByChild("price").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    String id="",categoryId="",caption="",description="",price="",imageUrl="";
                    if (dataSnapshot.getKey()!=null){
                        id=dataSnapshot.getKey();
                    }
                    if (dataSnapshot.child("imageUrl").getValue()!=null){
                        imageUrl=dataSnapshot.child("imageUrl").getValue().toString();
                    }

                    if (dataSnapshot.child("caption").getValue()!=null){
                        caption=dataSnapshot.child("caption").getValue().toString();
                    }
                    if (dataSnapshot.child("category_id").getValue()!=null){
                        categoryId=dataSnapshot.child("category_id").getValue().toString();
                    }
                    if (dataSnapshot.child("description").getValue()!=null){
                        description=dataSnapshot.child("description").getValue().toString();
                    }
                    if (dataSnapshot.child("price").getValue()!=null){
                        price=dataSnapshot.child("price").getValue().toString()+" TL";
                    }
                    products.add(new Product(id,categoryId,caption,description,price,imageUrl));

                    productLiveData.setValue(products);
                    productLoadError.setValue(false);
                    loading.setValue(false);
                    return;
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

}
