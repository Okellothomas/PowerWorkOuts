package com.pro.powerworkouts.util;

import com.pro.powerworkouts.network.ExerciseDbApi;
import com.pro.powerworkouts.network.ExerciseDbClient;

public class Constants {
  public static final String BASE_URL = "https://exercisedb.p.rapidapi.com/exercises/";
  public static final String HOST_QUERY_PARAMETER = "X-RapidAPI-Host";
  public static final String KEY_QUERY_PARAMETER = "X-RapidAPI-Key";
  public static final String EXTRA_WORKOUT_CATEGORY = "workout_category";
  public static final ExerciseDbApi CLIENT = ExerciseDbClient.getClient();
  public static final String EXTRA_WORKOUT_ID = "workout_id";
  public static final String FIREBASE_NODE_WORKOUTS = "done_workouts";
}
