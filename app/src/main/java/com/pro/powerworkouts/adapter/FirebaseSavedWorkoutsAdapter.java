package com.pro.powerworkouts.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.interfaces.OnClickListener;
import com.pro.powerworkouts.models.Workout;
import com.pro.powerworkouts.viewholder.FirebaseDoneWorkoutViewHolder;

public class FirebaseSavedWorkoutsAdapter extends FirebaseRecyclerAdapter<Workout, FirebaseDoneWorkoutViewHolder> {
  public static final String TAG = FirebaseSavedWorkoutsAdapter.class.getSimpleName();
  private final Context context;
  private final DatabaseReference databaseReference;
  private final OnClickListener listener;

  public FirebaseSavedWorkoutsAdapter(@NonNull FirebaseRecyclerOptions<Workout> options, Context context, DatabaseReference databaseReference, OnClickListener listener) {
    super(options);
    this.context = context;
    this.databaseReference = databaseReference;
    this.listener = listener;
  }

  @Override
  protected void onBindViewHolder(@NonNull FirebaseDoneWorkoutViewHolder holder, int position, @NonNull Workout workout) {
    holder.bindWorkout(workout);
    holder.itemView.setOnClickListener(view -> {
      Log.d(TAG, String.format("Done workout with id '%s'  clicked: ", workout.getId()));
      listener.onClick(workout.getId());
    });
  }

  @NonNull
  @Override
  public FirebaseDoneWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_list, parent, false);
    return new FirebaseDoneWorkoutViewHolder(view);
  }
}
