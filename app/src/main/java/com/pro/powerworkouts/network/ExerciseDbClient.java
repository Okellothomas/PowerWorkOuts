package com.pro.powerworkouts.network;

import com.pro.powerworkouts.BuildConfig;
import com.pro.powerworkouts.util.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExerciseDbClient {
  public static Retrofit retrofit = null;

  public static ExerciseDbApi getClient() {
    if(retrofit == null){
      OkHttpClient httpClient = new OkHttpClient.Builder()
              .addInterceptor(chain -> {
                Request newRequest = chain.request().newBuilder()
                        .addHeader(Constants.HOST_QUERY_PARAMETER, BuildConfig.EXERCISEDB_API_HOST)
                        .addHeader(Constants.KEY_QUERY_PARAMETER, BuildConfig.EXERCISEDB_API_KEY)
                        .build();
                return chain.proceed(newRequest);
              }).build();

      retrofit = new Retrofit.Builder()
              .baseUrl(Constants.BASE_URL)
              .client(httpClient)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
    }

    return retrofit.create(ExerciseDbApi.class);
  }
}
