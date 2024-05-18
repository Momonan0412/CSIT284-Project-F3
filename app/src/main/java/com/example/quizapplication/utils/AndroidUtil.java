package com.example.quizapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.quizapplication.designpattern.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AndroidUtil {
    private AndroidUtil(){}
    public static void setProfilePicture(Context context, Uri selectedImageUri,
                                         ImageView imageView){
        User user = User.getInstance();
        Glide.with(context)
                .asBitmap()
                .load(selectedImageUri)
                .apply(RequestOptions.circleCropTransform())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                        DatabaseUtilities.uploadImageToStorage(resource, user.getUsername(), context);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Handle the case when Glide clears the resource
                    }
                });
    }
    public static String currentDateGetter(){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
}
