package com.pro.powerworkouts.ui;

import static com.pro.powerworkouts.util.Constants.CLIENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.pro.powerworkouts.R;
import com.pro.powerworkouts.adapter.WorkoutListAdapter;
import com.pro.powerworkouts.interfaces.OnClickListener;
import com.pro.powerworkouts.models.Workout;
import com.pro.powerworkouts.util.Constants;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutListActivity extends AppCompatActivity implements OnClickListener {
  @BindView(R.id.workout_list)
  RecyclerView workoutList;

  public static final String TAG = WorkoutListActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_workout_list);
    ButterKnife.bind(this);

    String workoutCategory = getIntent().getStringExtra(Constants.EXTRA_WORKOUT_CATEGORY);
    Objects.requireNonNull(getSupportActionBar()).setTitle(workoutCategory);

    loadWorkouts(workoutCategory);
  }

  // Retrieve workouts based on category
  private void loadWorkouts(String bodyPart){
    CLIENT.getWorkoutsByBodyPart(bodyPart).enqueue(new Callback<List<Workout>>() {
      @Override
      public void onResponse(@NonNull Call<List<Workout>> call, @NonNull Response<List<Workout>> response) {
        if(response.isSuccessful()){
          assert response.body() != null;
          Log.d(TAG, "Retrieved workouts: " + response.body().size());

          WorkoutListAdapter adapter = new WorkoutListAdapter(getApplicationContext(), response.body(), WorkoutListActivity.this);
          setLayoutManager();
          workoutList.setAdapter(adapter);
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<Workout>> call, @NonNull Throwable t) {
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

  @Override
  public void onClick(String pathItem) {
    Intent intent = new Intent(this, WorkoutDetailActivity.class);
    intent.putExtra(Constants.EXTRA_WORKOUT_ID, pathItem);
    Log.d(TAG, "Workout ID: " + pathItem);
    startActivity(intent);
  }
}