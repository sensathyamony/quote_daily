package com.mony.quotedaily.controller;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
   public static Retrofit retrofit;

   public static Retrofit getClient(){
      if (retrofit == null){
         retrofit = new Retrofit.Builder()
                 .baseUrl("https://favqs.com/api/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .build();
      }

      return retrofit;
   }
}
