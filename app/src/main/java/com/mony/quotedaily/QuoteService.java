package com.mony.quotedaily;

import com.mony.quotedaily.model.ImageModel;
import com.mony.quotedaily.model.QuoteApiModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuoteService {

    @GET("qotd")
    Call<QuoteApiModel> getDailyQuote();

    @GET("search/photos")
    Call<ImageModel> getImage(@Query("client_id") String clientId,
                              @Query("query") String tag,
                              @Query("orientation") String orientation,
                              @Query("order_by")String orderBy,
                              @Query("per_page")int perPage,
                              @Query("page")int page);
}
