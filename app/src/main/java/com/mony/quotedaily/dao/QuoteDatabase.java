package com.mony.quotedaily.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SaveQuote.class}, version = 1)
public abstract class QuoteDatabase extends RoomDatabase {

   private static QuoteDatabase instance;

   public abstract QuoteDao quoteDao();

   public static synchronized QuoteDatabase getInstance(Context context){
      if(instance == null){
         instance = Room.databaseBuilder(
                         context.getApplicationContext(),
                         QuoteDatabase.class, "quotes_table")
                 .fallbackToDestructiveMigration()
                 .addCallback(roomCallBack)
                 .build();
      }
      return instance;
   }


   private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db) {
         super.onCreate(db);
//         new PopulateDbAsynTask(instance).execute();
         ExecutorService executors = Executors.newSingleThreadExecutor();
         executors.execute(new Runnable() {
            @Override
            public void run() {
            }
         });
      }
   };
}
