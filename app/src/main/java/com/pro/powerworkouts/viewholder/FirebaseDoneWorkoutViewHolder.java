package com.pro.powerworkouts.viewholder;

import static com.pro.powerworkouts.util.UIHelpers.capitalize;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.models.Workout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirebaseDoneWorkoutViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.workout_name)
  TextView workoutName;
  @BindView(R.id.workout_gif)
  ShapeableImageView workoutGif;

  private Context context;

  public FirebaseDoneWorkoutViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    context = itemView.getContext();
  }

  public void bindWorkout(Workout workout){
    Glide.with(context).asGif().load(workout.getGifUrl()).placeholder(R.drawable.ic_baseline_fitness_center).into(workoutGif);
    workoutName.setText(capitalize(workout.getName()));
  }
}
