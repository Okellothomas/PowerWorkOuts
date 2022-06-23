package com.pro.powerworkouts.fragment;

import static com.pro.powerworkouts.util.UIHelpers.displayData;
import static com.pro.powerworkouts.util.UIHelpers.displayError;
import static com.pro.powerworkouts.util.UIHelpers.hideProgressDialog;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.adapter.FirebaseDoneWorkoutsAdapter;
import com.pro.powerworkouts.interfaces.OnClickListener;
import com.pro.powerworkouts.models.Workout;
import com.pro.powerworkouts.ui.WorkoutDetailActivity;
import com.pro.powerworkouts.util.Constants;
import com.pro.powerworkouts.viewholder.FirebaseDoneWorkoutViewHolder;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoneWorkoutFragment extends Fragment implements OnClickListener {
  @BindView(R.id.done_workout_list)
  RecyclerView doneWorkoutList;
  @BindView(R.id.progress_circular_5)
  ProgressBar progressBar;
  @BindView(R.id.progress_text_5)
  TextView progressText;
  @BindView(R.id.error_text_5)
  TextView errorText;

  public static final String TAG = DoneWorkoutFragment.class.getSimpleName();
  private FirebaseRecyclerAdapter<Workout, FirebaseDoneWorkoutViewHolder> firebaseAdapter;

  public DoneWorkoutFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_done_workout, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    setUpAdapter();
    hideProgressDialog(progressBar, progressText);
    displayData(doneWorkoutList);
  }

  private void setUpAdapter(){
    String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    Query query = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_NODE_WORKOUTS).child(userId);
    FirebaseRecyclerOptions<Workout> options =
            new FirebaseRecyclerOptions.Builder<Workout>()
                    .setQuery(query, Workout.class)
                    .build();

    setLayoutManager();
    firebaseAdapter = new FirebaseDoneWorkoutsAdapter(options, getContext(), query, this);

    if (firebaseAdapter.getItemCount() < 1) {
      displayError(errorText, R.string.no_saved_workouts);
    }
    errorText.setVisibility(View.GONE);
    doneWorkoutList.setAdapter(firebaseAdapter);
  }

  private void setLayoutManager(){
    // Set layout manager based on orientation
    if(requireView().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
      doneWorkoutList.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.grid_columns_2)));
    } else {
      doneWorkoutList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
  }

  @Override
  public void onClick(String pathItem) {
    Intent intent = new Intent(getContext(), WorkoutDetailActivity.class);
    intent.putExtra(Constants.EXTRA_WORKOUT_ID, pathItem);
    startActivity(intent);
  }

  @Override
  public void onStart() {
    super.onStart();
    firebaseAdapter.startListening();
  }

  @Override
  public void onStop() {
    super.onStop();
    firebaseAdapter.stopListening();
  }
}