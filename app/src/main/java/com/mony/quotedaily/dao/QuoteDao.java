package com.mony.quotedaily.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuoteDao {

    @Insert
    void insert(SaveQuote saveQuote);

    @Delete
    void delete(SaveQuote saveQuote);

    @Query("SELECT * FROM QUOTES_TABLE ORDER BY id DESC")
    LiveData<List<SaveQuote>> getBookMarkList();

}
