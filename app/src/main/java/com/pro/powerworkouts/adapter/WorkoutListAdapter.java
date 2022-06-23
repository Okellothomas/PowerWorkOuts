package com.pro.powerworkouts.adapter;

import static com.pro.powerworkouts.util.UIHelpers.capitalize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.interfaces.OnClickListener;
import com.pro.powerworkouts.models.Workout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.WorkoutListViewHolder> {
  private final Context context;
  private final List<Workout> workouts;
  private final OnClickListener listener;

  public WorkoutListAdapter(Context context, List<Workout> workouts, OnClickListener listener) {
    this.context = context;
    this.workouts = workouts;
    this.listener = listener;
  }

  @NonNull
  @Override
  public WorkoutListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_list, parent, false);
    return new WorkoutListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull WorkoutListViewHolder holder, int position) {
    Workout workout = workouts.get(position);
    holder.bindWorkout(workout);
    holder.itemView.setOnClickListener(view -> listener.onClick(workout.getId()));
  }

  @Override
  public int getItemCount() {
    return workouts.size();
  }

  public static class WorkoutListViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.workout_gif)
    ShapeableImageView workoutGif;
    @BindView(R.id.workout_name)
    TextView workoutName;

    private final Context context;

    public WorkoutListViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      context = itemView.getContext();
    }

    public void bindWorkout(Workout workout){
      Glide.with(context).asGif().load(workout.getGifUrl()).placeholder(R.drawable.ic_baseline_fitness_center).into(workoutGif);
      workoutName.setText(capitalize(workout.getName()));
    }
  }
}
