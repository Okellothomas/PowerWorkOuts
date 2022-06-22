package com.pro.powerworkouts.ui;

import static com.pro.powerworkouts.util.Constants.CLIENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.models.Workout;
import com.pro.powerworkouts.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutDetailActivity extends AppCompatActivity {
  @BindView(R.id.workout_detail_gif)
  ShapeableImageView workoutGif;
  @BindView(R.id.workout_detail_name)
  TextView workoutDetailName;
  @BindView(R.id.body_part_chip)
  Chip bodyPartChip;
  @BindView(R.id.equipment_chip)
  Chip equipmentChip;
  @BindView(R.id.target_chip)
  Chip targetChip;

  public static final String TAG = WorkoutDetailActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_workout_detail);
    ButterKnife.bind(this);

    String workoutId = getIntent().getStringExtra(Constants.EXTRA_WORKOUT_ID);
    loadWorkout(workoutId);
  }

  // Retrieve workout detail
  private void loadWorkout(String id){
    CLIENT.getExerciseById(id).enqueue(new Callback<Workout>() {
      @Override
      public void onResponse(@NonNull Call<Workout> call, @NonNull Response<Workout> response) {
        if(response.isSuccessful()){
          assert response.body() != null;
          Log.d(TAG, "Retrieved workout: " + response.body().getName());
          setWorkoutDetails(response.body());
        }
      }

      @Override
      public void onFailure(@NonNull Call<Workout> call, @NonNull Throwable t) {
        Log.e(TAG, "Error while fetching workout: " + id, t);
      }
    });
  }

  private void setWorkoutDetails(Workout workout){
    Glide.with(this).asGif().load(workout.getGifUrl()).into(workoutGif);
    workoutDetailName.setText(workout.getName());
    bodyPartChip.setText(workout.getBodyPart());
    equipmentChip.setText(workout.getEquipment());
    targetChip.setText(workout.getTarget());
  }
}