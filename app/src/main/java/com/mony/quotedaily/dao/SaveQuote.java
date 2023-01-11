package com.mony.quotedaily.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quotes_table")
public class SaveQuote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String quoteMsg;
    private String quoteMood;
    private String quoteAuthor;

    public SaveQuote(String quoteMsg, String quoteMood, String quoteAuthor) {
        this.quoteMsg = quoteMsg;
        this.quoteMood = quoteMood;
        this.quoteAuthor = quoteAuthor;
    }

    public int getId() {
        return id;
    }

    public String getQuoteMsg() {
        return quoteMsg;
    }

    public String getQuoteMood() {
        return quoteMood;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setId(int id) {
        this.id = id;
    }
}
