package com.pro.powerworkouts.ui;

import static com.pro.powerworkouts.util.Constants.CLIENT;
import static com.pro.powerworkouts.util.UIHelpers.capitalize;
import static com.pro.powerworkouts.util.UIHelpers.displayData;
import static com.pro.powerworkouts.util.UIHelpers.displayError;
import static com.pro.powerworkouts.util.UIHelpers.hideProgressDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.models.Workout;
import com.pro.powerworkouts.util.Constants;

import java.util.Objects;

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
  @BindView(R.id.progress_circular_2)
  ProgressBar progressBar;
  @BindView(R.id.progress_text_2)
  TextView progressText;
  @BindView(R.id.workout_detail_group)
  Group workoutDetailGroup;
  @BindView(R.id.error_text_2)
  TextView errorText;
  @BindView(R.id.btn_save)
  Button saveButton;

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
        hideProgressDialog(progressBar, progressText);
        if(response.isSuccessful()){
          assert response.body() != null;
          Log.d(TAG, "Retrieved workout: " + response.body().getName());
          setWorkoutDetails(response.body());
          displayData(workoutDetailGroup);
        } else {
          displayError(errorText, R.string.unsuccessful_feeback);
        }
      }

      @Override
      public void onFailure(@NonNull Call<Workout> call, @NonNull Throwable t) {
        displayError(errorText, t.getMessage());
        Log.e(TAG, "Error while fetching workout: " + id, t);
      }
    });
  }

  private void setWorkoutDetails(Workout workout){
    Glide.with(this).asGif().load(workout.getGifUrl()).into(workoutGif);
    workoutDetailName.setText(capitalize(workout.getName()));
    bodyPartChip.setText(workout.getBodyPart());
    equipmentChip.setText(workout.getEquipment());
    targetChip.setText(workout.getTarget());

    // Save done workout upon click
    saveButton.setOnClickListener(view -> {
      saveWorkout(workout);
    });
  }

  private void saveWorkout(Workout workout){
    // Get user id
    String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    DatabaseReference reference = FirebaseDatabase.getInstance()
            .getReference(Constants.FIREBASE_NODE_WORKOUTS)
            .child(userId)
            .child(workout.getId());

    // Save done workout and display feedback to user
    reference.setValue(workout).addOnCompleteListener(this, saveTask -> {
      if (saveTask.isSuccessful()){
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(this, Objects.requireNonNull(saveTask.getException()).getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}