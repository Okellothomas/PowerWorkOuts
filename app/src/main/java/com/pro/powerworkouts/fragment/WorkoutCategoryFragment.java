package com.pro.powerworkouts.fragment;

import static com.pro.powerworkouts.util.Constants.CLIENT;
import static com.pro.powerworkouts.util.UIHelpers.displayData;
import static com.pro.powerworkouts.util.UIHelpers.displayError;
import static com.pro.powerworkouts.util.UIHelpers.hideProgressDialog;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.SplashScreen;
import com.pro.powerworkouts.adapter.WorkoutCategoryAdapter;
import com.pro.powerworkouts.interfaces.OnClickListener;
import com.pro.powerworkouts.ui.WorkoutListActivity;
import com.pro.powerworkouts.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutCategoryFragment extends Fragment implements OnClickListener {
  @BindView(R.id.category_grid)
  RecyclerView categoryGrid;
  @BindView(R.id.screen_title)
  TextView welcomeText;
  @BindView(R.id.error_text_3)
  TextView errorText;
  @BindView(R.id.progress_bar_3)
  ProgressBar progressBar;
  @BindView(R.id.progress_text_3)
  TextView progressText;
  @BindView(R.id.workout_category_group)
  Group workoutCategoryGroup;

  private FirebaseAuth auth;
  private FirebaseAuth.AuthStateListener authListener;

  public static final String TAG = WorkoutCategoryFragment.class.getSimpleName();
  private final List<Integer> categoryImages = new ArrayList<>(
          Arrays.asList(R.drawable.back_workout, R.drawable.cardio_workout, R.drawable.chest_workout,
                  R.drawable.lower_arms_workout, R.drawable.lower_legs_workout, R.drawable.neck_workout, R.drawable.shoulder_workout,
                  R.drawable.upper_arms_workout, R.drawable.upper_legs_workout, R.drawable.waist_workout));

  public WorkoutCategoryFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_workout_category, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    auth = FirebaseAuth.getInstance();
    authListener = firebaseAuth -> {
      if(firebaseAuth.getCurrentUser() != null){
        displayWelcomeText(firebaseAuth.getCurrentUser().getDisplayName());
      }
    };

    loadWorkoutCategories();
  }

  private void displayWelcomeText(String name){
    welcomeText.setText(getString(R.string.welcome, name));
  }

  // Retrieve workout categories
  private void loadWorkoutCategories(){
    // Send request
    CLIENT.getBodyParts().enqueue(new Callback<List<String>>() {
      @Override
      public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
        hideProgressDialog(progressBar, progressText);
        if(response.isSuccessful()){
          assert response.body() != null;
          Log.d(TAG, "Retrieved workout categories: " + response.body().size());
          WorkoutCategoryAdapter adapter = new WorkoutCategoryAdapter(getContext(), response.body(), categoryImages, WorkoutCategoryFragment.this);

          setLayoutManager();
          categoryGrid.setAdapter(adapter);

          if(adapter.getItemCount() < 1){
            displayError(errorText, R.string.no_workout_categories);
          } else {
            displayData(workoutCategoryGroup);
          }
        } else {
          displayError(errorText, R.string.unsuccessful_feeback);
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
        displayError(errorText, R.string.failure_feedback);
        Log.e(TAG, "Error while fetching workout categories: ", t);
      }
    });
  }

  private void setLayoutManager(){
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
      categoryGrid.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.grid_columns)));
    } else {
      categoryGrid.setLayoutManager(new LinearLayoutManager(getContext()));
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    auth.addAuthStateListener(authListener);
  }

  @Override
  public void onStop() {
    super.onStop();
    if(authListener != null){
      auth.removeAuthStateListener(authListener);
    }
  }

  @Override
  public void onClick(String pathItem) {
    Intent intent = new Intent(getContext(), WorkoutListActivity.class);
    intent.putExtra(Constants.EXTRA_WORKOUT_CATEGORY, pathItem);
    startActivity(intent);
  }
}