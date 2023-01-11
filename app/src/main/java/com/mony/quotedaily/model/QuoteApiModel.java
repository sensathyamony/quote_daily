
package com.mony.quotedaily.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuoteApiModel {

    @SerializedName("qotd_date")
    @Expose
    private String qotdDate;
    @SerializedName("quote")
    @Expose
    private Quote quote;

    public String getQotdDate() {
        return qotdDate;
    }

    public void setQotdDate(String qotdDate) {
        this.qotdDate = qotdDate;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

}
