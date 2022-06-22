package com.pro.powerworkouts.fragment;

import static com.pro.powerworkouts.util.Constants.CLIENT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pro.powerworkouts.R;
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

  public static final String TAG = WorkoutCategoryFragment.class.getSimpleName();
  private final List<Integer> categoryImages = new ArrayList<>(Arrays.asList(R.drawable.ic_baseline_fitness_center));

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

    displayWelcomeText();
    loadWorkoutCategories();
  }

  private void displayWelcomeText(){
    welcomeText.setText(getString(R.string.welcome, "John"));
  }

  // Retrieve workout categories
  private void loadWorkoutCategories(){
    // Send request
    CLIENT.getBodyParts().enqueue(new Callback<List<String>>() {
      @Override
      public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
        if(response.isSuccessful()){
          assert response.body() != null;
          Log.d(TAG, "Retrieved workout categories: " + response.body().size());
          WorkoutCategoryAdapter adapter = new WorkoutCategoryAdapter(getContext(), response.body(), categoryImages, WorkoutCategoryFragment.this);
          categoryGrid.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.grid_columns)));
          categoryGrid.setAdapter(adapter);
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
        Log.e(TAG, "Error while fetching workout categories: ", t);
      }
    });
  }

  @Override
  public void onClick(String pathItem) {
    Intent intent = new Intent(getContext(), WorkoutListActivity.class);
    intent.putExtra(Constants.EXTRA_WORKOUT_CATEGORY, pathItem);
    startActivity(intent);
  }
}