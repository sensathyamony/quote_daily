package com.mony.quotedaily.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mony.quotedaily.dao.SaveQuote;
import com.mony.quotedaily.repository.QuoteRepository;

import java.util.List;

public class QuoteViewModel extends AndroidViewModel {

   private QuoteRepository quoteRepository;
   private LiveData<List<SaveQuote>> quotes;

   public QuoteViewModel(@NonNull Application application) {
      super(application);

      quoteRepository = new QuoteRepository(application);
      quotes = quoteRepository.getBookMarkList();
   }

   public void insert(SaveQuote saveQuote){
      quoteRepository.insert(saveQuote);
   }

   public void delete(SaveQuote saveQuote){
      quoteRepository.delete(saveQuote);
   }

   public LiveData<List<SaveQuote>> getBookMarkList(){
      return quotes;
   }
}
