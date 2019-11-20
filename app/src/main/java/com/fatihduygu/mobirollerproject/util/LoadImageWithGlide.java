package com.fatihduygu.mobirollerproject.util;

import android.content.Context;
import android.widget.ImageView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fatihduygu.mobirollerproject.R;

public class LoadImageWithGlide {

    public static void LoadImageWithGlide(ImageView imageView, String url, CircularProgressDrawable progressDrawable) {
        RequestOptions options=new RequestOptions()
                .fitCenter()
                .centerInside()
                .centerCrop()
                .useAnimationPool(true)
                .placeholder(progressDrawable)
                .error(R.drawable.product1);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }

    public static CircularProgressDrawable getProgressDrawable(Context context){
        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(10f);
        cpd.setCenterRadius(25f);
        cpd.start();
        return cpd;
    }
}
