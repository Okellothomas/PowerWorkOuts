package com.pro.powerworkouts.ui;

import static com.pro.powerworkouts.util.Constants.CLIENT;
import static com.pro.powerworkouts.util.UIHelpers.capitalize;
import static com.pro.powerworkouts.util.UIHelpers.displayData;
import static com.pro.powerworkouts.util.UIHelpers.displayError;
import static com.pro.powerworkouts.util.UIHelpers.hideProgressDialog;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.adapter.WorkoutListAdapter;
import com.pro.powerworkouts.interfaces.OnClickListener;
import com.pro.powerworkouts.models.Workout;
import com.pro.powerworkouts.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutListActivity extends AppCompatActivity implements OnClickListener {
  @BindView(R.id.workout_list)
  RecyclerView workoutList;
  @BindView(R.id.progress_circular)
  ProgressBar progressBar;
  @BindView(R.id.progress_text)
  TextView progressText;
  @BindView(R.id.error_text)
  TextView errorText;
  @BindView(R.id.toolbar)
  CollapsingToolbarLayout toolbar;
  @BindView(R.id.workout_category_image)
  ImageView categoryImage;

  public static final String TAG = WorkoutListActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_workout_list);
    ButterKnife.bind(this);

    String workoutCategory = getIntent().getStringExtra(Constants.EXTRA_WORKOUT_CATEGORY);
    toolbar.setTitle(String.format("%s workouts", capitalize(workoutCategory)));

    setCategoryImage(workoutCategory);
    loadWorkouts(workoutCategory);
  }

  // Retrieve workouts based on category
  private void loadWorkouts(String bodyPart){
    CLIENT.getWorkoutsByBodyPart(bodyPart).enqueue(new Callback<List<Workout>>() {
      @Override
      public void onResponse(@NonNull Call<List<Workout>> call, @NonNull Response<List<Workout>> response) {
        hideProgressDialog(progressBar, progressText);
        if(response.isSuccessful()){
          assert response.body() != null;
          Log.d(TAG, "Retrieved workouts: " + response.body().size());

          WorkoutListAdapter adapter = new WorkoutListAdapter(getApplicationContext(), response.body(), WorkoutListActivity.this);
          setLayoutManager();
          workoutList.setAdapter(adapter);

          if(adapter.getItemCount() < 1){
            displayError(errorText, R.string.no_workouts);
          } else {
            displayData(workoutList);
          }
        } else {
          displayError(errorText, R.string.unsuccessful_feeback);
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<Workout>> call, @NonNull Throwable t) {
        displayError(errorText, R.string.failure_feedback);
        Log.e(TAG, "Error while fetching workouts: ", t);
      }
    });
  }

  private void setLayoutManager(){
    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
      workoutList.setLayoutManager(new GridLayoutManager(getApplicationContext(), getResources().getInteger(R.integer.grid_columns_2)));
    } else {
      workoutList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
  }

  private void setCategoryImage(String category){
    switch (category){
      case "back":
        Glide.with(this).asBitmap().load(R.drawable.back_workout).into(categoryImage);
        break;
      case "cardio":
        Glide.with(this).asBitmap().load(R.drawable.cardio_workout).into(categoryImage);
        break;
      case "chest":
        Glide.with(this).asBitmap().load(R.drawable.chest_workout).into(categoryImage);
        break;
      case "lower arms":
        Glide.with(this).asBitmap().load(R.drawable.lower_arms_workout).into(categoryImage);
        break;
      case "lower legs":
        Glide.with(this).asBitmap().load(R.drawable.lower_legs_workout).into(categoryImage);
        break;
      case "neck":
        Glide.with(this).asBitmap().load(R.drawable.neck_workout).into(categoryImage);
        break;
      case "shoulders":
        Glide.with(this).asBitmap().load(R.drawable.shoulder_workout).into(categoryImage);
        break;
      case "upper arms":
        Glide.with(this).asBitmap().load(R.drawable.upper_arms_workout).into(categoryImage);
        break;
      case "upper legs":
        Glide.with(this).asBitmap().load(R.drawable.upper_legs_workout).into(categoryImage);
        break;
      case "waist":
        Glide.with(this).asBitmap().load(R.drawable.waist_workout).into(categoryImage);
        break;
      default:
        Glide.with(this).asBitmap().load(R.drawable.ic_baseline_fitness_center).into(categoryImage);
        break;
    }
  }

  @Override
  public void onClick(String pathItem) {
    Intent intent = new Intent(this, WorkoutDetailActivity.class);
    intent.putExtra(Constants.EXTRA_WORKOUT_ID, pathItem);
    Log.d(TAG, "Workout ID: " + pathItem);
    startActivity(intent);
  }
}