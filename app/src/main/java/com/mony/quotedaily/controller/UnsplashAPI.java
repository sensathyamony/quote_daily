package com.mony.quotedaily.controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnsplashAPI {
   private static Retrofit retrofit;

   public static Retrofit getImage(){

      if (retrofit == null){
         retrofit = new Retrofit.Builder()
                 .baseUrl("https://api.unsplash.com/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
      }

      return retrofit;
   }
}
