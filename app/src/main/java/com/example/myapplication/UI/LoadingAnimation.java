package com.example.myapplication.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.myapplication.R;

public class LoadingAnimation {
    private static AlertDialog dialog;

    public static void startLoadingAnimation(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_animation, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public static void dismissLoadingAnimation(){
        dialog.dismiss();
    }
}
