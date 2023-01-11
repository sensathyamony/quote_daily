package com.mony.quotedaily.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mony.quotedaily.dao.QuoteDao;
import com.mony.quotedaily.dao.QuoteDatabase;
import com.mony.quotedaily.dao.SaveQuote;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuoteRepository {
   private QuoteDao quoteDao;
   private LiveData<List<SaveQuote>> quotes;

   ExecutorService executor = Executors.newSingleThreadExecutor();

   public QuoteRepository(Application application){
      QuoteDatabase database = QuoteDatabase.getInstance(application);
      quoteDao = database.quoteDao();
      quotes = quoteDao.getBookMarkList();
   }

   public void insert(SaveQuote saveQuote){
//      new InsertNotedAsyncTask(noteDao).execute(note);
      executor.execute(new Runnable() {
         @Override
         public void run() {
            quoteDao.insert(saveQuote);
         }
      });
   }

   public void delete(SaveQuote saveQuote){
//      new DeleteNotedAsyncTask(noteDao).execute(note);
      executor.execute(new Runnable() {
         @Override
         public void run() {
            quoteDao.delete(saveQuote);
         }
      });
   }

   public LiveData<List<SaveQuote>> getBookMarkList(){
      return quotes;
   }

}
