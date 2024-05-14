package com.example.quizapplication.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class AndroidUtil {
    private AndroidUtil(){}
    public static void setProfilePicture(Context context, Uri selectedImageUri, ImageView imageView){
        Glide.with(context).load(selectedImageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}
