package com.pro.powerworkouts.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.powerworkouts.R;
import com.pro.powerworkouts.interfaces.OnClickListener;
import com.pro.powerworkouts.models.Workout;
import com.pro.powerworkouts.network.ExerciseDbApi;
import com.pro.powerworkouts.network.ExerciseDbClient;
import com.pro.powerworkouts.ui.WorkoutDetailActivity;
import com.pro.powerworkouts.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedWorkoutFragment extends Fragment implements OnClickListener {
  public static final String TAG = SavedWorkoutFragment.class.getSimpleName();

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

  @Override
  public void onClick(String pathItem) {
    Intent intent = new Intent(getContext(), WorkoutDetailActivity.class);
    intent.putExtra(Constants.EXTRA_WORKOUT_ID, pathItem);
    startActivity(intent);
  }
}