package com.pro.powerworkouts.network;

import com.pro.powerworkouts.models.Workout;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExerciseDbApi {
  @GET("bodyPart/{bodyPart}")
  Call<List<Workout>> getWorkoutsByBodyPart(@Path("bodyPart") String bodyPart);

  @GET("bodyPartList")
  Call<List<String>> getBodyParts();

  @GET("exercise/{exerciseId}")
  Call<Workout> getExerciseById(@Path("exerciseId") String exerciseId);
}
