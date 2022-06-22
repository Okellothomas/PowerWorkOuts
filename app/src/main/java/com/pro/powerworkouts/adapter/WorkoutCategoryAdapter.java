package com.pro.powerworkouts.adapter;

import static com.pro.powerworkouts.util.UIHelpers.capitalize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.interfaces.OnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutCategoryAdapter extends RecyclerView.Adapter<WorkoutCategoryAdapter.WorkoutCategoryViewHolder> {
  private final Context context;
  private final List<String> categoryLabels;
  private final List<Integer> categoryImages;
  private final OnClickListener listener;

  public WorkoutCategoryAdapter(Context context, List<String> categoryLabels, List<Integer> categoryImages, OnClickListener listener) {
    this.context = context;
    this.categoryLabels = categoryLabels;
    this.categoryImages = categoryImages;
    this.listener = listener;
  }

  @NonNull
  @Override
  public WorkoutCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_category, parent, false);
    return new WorkoutCategoryViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull WorkoutCategoryViewHolder holder, int position) {
    String category = categoryLabels.get(position);
    holder.bindWorkoutCategory(category, categoryImages.get(0));
    holder.itemView.setOnClickListener(view -> listener.onClick(category));
  }

  @Override
  public int getItemCount() {
    return categoryLabels.size();
  }

  public static class WorkoutCategoryViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.category_image)
    ShapeableImageView categoryImage;
    @BindView(R.id.category_label)
    TextView categoryLabel;

    private Context context;

    public WorkoutCategoryViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      context = itemView.getContext();
    }

    public void bindWorkoutCategory(String category, @DrawableRes int image){
      Picasso.get().load(image).placeholder(R.drawable.ic_baseline_fitness_center).into(categoryImage);
      categoryLabel.setText(capitalize(category));
    }
  }
}
