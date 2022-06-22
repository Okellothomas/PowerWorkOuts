package com.pro.powerworkouts.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.powerworkouts.R;
import com.pro.powerworkouts.models.Workout;
import com.pro.powerworkouts.network.ExerciseDbApi;
import com.pro.powerworkouts.network.ExerciseDbClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedWorkoutFragment extends Fragment {
  public static final String TAG = SavedWorkoutFragment.class.getSimpleName();
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  private String mParam1;
  private String mParam2;

  private ExerciseDbApi client = ExerciseDbClient.getClient();



  public SavedWorkoutFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_saved_workout, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

}