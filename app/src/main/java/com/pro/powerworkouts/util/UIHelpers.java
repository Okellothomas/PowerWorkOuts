package com.pro.powerworkouts.util;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

public class UIHelpers {
  public static String capitalize(String text){
    return text.substring(0,1).toUpperCase() + text.substring(1);
  }

  public static void hideProgressDialog(ProgressBar progressBar, TextView textView){
    progressBar.setVisibility(View.GONE);
    textView.setVisibility(View.GONE);
  }

  public static void displayData(RecyclerView recyclerView){
    recyclerView.setVisibility(View.VISIBLE);
  }

  public static void displayData(Group group){
    group.setVisibility(View.VISIBLE);
  }

  public static void displayError(TextView textView, @StringRes int message){
    textView.setText(message);
    textView.setVisibility(View.VISIBLE);
  }

  public static void displayError(TextView textView, String message){
    textView.setText(message);
    textView.setVisibility(View.VISIBLE);
  }
}
